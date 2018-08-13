package com.lesson.shopService.provider;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lesson.api.shopApi.ShopService;
import com.lesson.commons.Checker;
import com.lesson.commons.ObjectCloneHelper;
import com.lesson.commons.RedisHelper;
import com.lesson.modelInfo.base.ApiDataResponse;
import com.lesson.modelInfo.base.BaseResponse;
import com.lesson.modelInfo.base.PageDataResponse;
import com.lesson.modelInfo.shop.ShopBase;
import com.lesson.modelInfo.shop.ShopInfo;
import com.lesson.modelInfo.shop.ShopReq;
import com.lesson.shopService.common.redis.cluster.RedisCacheManager;
import com.lesson.shopService.mapper.lesson.shop.ShopMapper;
import com.lesson.shopService.mapper.lesson.user.UserMapper;
import com.lesson.shopService.model.lesson.shop.Shop;
import com.lesson.shopService.model.lesson.shop.ShopExample;
import com.lesson.shopService.model.lesson.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {
    private final static Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);
    @Resource
    private ShopMapper shopMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisCacheManager cacheManager;

    @Override
    public PageDataResponse<ShopInfo> selectAllShop() {
        PageDataResponse<ShopInfo> response = new PageDataResponse<ShopInfo>();
        try {
            List<ShopInfo> data = cacheManager.getList("ALLSHOP",ShopInfo.class);
            response.setInfo("from cache");
            if (Checker.isNone(data)) {
                data = new ArrayList<>();
                List<Shop> list = shopMapper.selectByExample(new ShopExample());
                if (!Checker.isNone(list)) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) != null) {
                            ShopInfo info = new ShopInfo();
                            ObjectCloneHelper.map(list.get(i),info);
                            data.add(info);
                        }
                    }
                }
                if (!Checker.isNone(data)) {
                    response.setInfo("from db");
                    cacheManager.setList("ALLSHOP",data,30*60,ShopInfo.class);
                } else {
                    response.setInfo("数据为空");
                }
            }
            response.setCode(BaseResponse.SUCCEED);
            response.setPageData(data);
        } catch (Exception e) {
            logger.error("selectAllShop is error",e);
            response.setCode(BaseResponse.APP_EXCEPTION);
            response.setInfo("获取全部店铺列表异常");
        }
        return response;
    }

    @Override
    public PageDataResponse<ShopInfo> selectShopList(ShopReq req) {
        PageDataResponse<ShopInfo> response = null;
        try {
            if (req == null) {
                response = new PageDataResponse<ShopInfo>();
                response.setCode(BaseResponse.PARAMETER_ERROR);
                response.setInfo("参数异常，请重新检查");
                return response;
            }
            ShopExample example = new ShopExample();
            ShopExample.Criteria criteria = example.createCriteria();
            if (req.getId() != null && req.getId() > 0) {
                criteria.andIdEqualTo(req.getId());
            }
            if (!Checker.isNone(req.getName())) {
                criteria.andNameLike("%"+req.getName()+"%");
            }
            if (!Checker.isNone(req.getPhone())) {
                criteria.andPhoneEqualTo(req.getPhone());
            }
            if (!Checker.isNone(req.getUserId()) && req.getUserId() > 0) {
                criteria.andUserIdEqualTo(req.getUserId());
            }
            if (!Checker.isNone(req.getAddress())) {
                criteria.andAddressLike("%"+req.getAddress()+"%");
            }
            if (req.getPageIndex() == null || req.getPageIndex() < 1) {
                req.setPageIndex(1);
            }
            if (req.getPageSize() == null || req.getPageSize() < 1) {
                req.setPageSize(10);
            }
            if (req.getOrderBy() == null || "".equals(req.getOrderBy())) {
                req.setOrderBy(" id desc");
            }
            String key = RedisHelper.getRedisKey("shopPage",req);
            response = cacheManager.get(key,PageDataResponse.class);
            if (response == null) {
                response = new PageDataResponse<>();
                Page page = PageHelper.startPage(req.getPageIndex(),req.getPageSize(),req.getOrderBy());
                List<Shop> list = shopMapper.selectByExample(example);
                PageInfo<Shop> pageInfo = new PageInfo<Shop>(list);
                List<ShopInfo> data = new ArrayList<>();
                if (!Checker.isNone(list)) {
                    for (Shop shop : list) {
                        if (shop != null) {
                            ShopInfo shopInfo = new ShopInfo();
                            ObjectCloneHelper.map(shop,shopInfo);
                            data.add(shopInfo);
                        }
                    }
                }
                response.setPageData(data);
                response.setCount(pageInfo.getPages());
                response.setPageIndex(req.getPageIndex());
                response.setTotal(pageInfo.getTotal());
                response.setHasNextPage(pageInfo.isHasNextPage());
                response.setHasPreviousPage(pageInfo.isHasPreviousPage());
                response.setCode(BaseResponse.SUCCEED);
                if (!Checker.isNone(data)) {
                    response.setInfo("from db");
                    cacheManager.set(key,response,10);
                } else {
                    response.setInfo("数据为空");
                }
            } else {
                response.setInfo("from cache");
            }

        } catch (Exception e) {
            logger.error("获取店铺列表出错，req={}",JSON.toJSONString(req));
            response.setCode(BaseResponse.APP_EXCEPTION);
            response.setInfo(String.format("获取店铺列表出错，req={}",JSON.toJSONString(req)));
        }
        return response;
    }

    @Override
    public ApiDataResponse saveShop(ShopBase model) {
        ApiDataResponse apiDataResponse = new ApiDataResponse();
        if (model == null) {
            apiDataResponse.setCode(BaseResponse.PARAMETER_ERROR);
            apiDataResponse.setInfo("参数异常，请重新检查");
            return apiDataResponse;
        }
        if (model.getId() == null || model.getId() < 1) {
            model.setCreateTime(new Date());
            Shop shop = new Shop();
            ObjectCloneHelper.map(model,shop);
            shop.setUpdateTime(new Date());
            if (Checker.isNone(shop.getUserId())) {
                apiDataResponse.setInfo(String.format("店铺绑定的用户id不能为空"));
                apiDataResponse.setCode(BaseResponse.PARAMETER_ERROR);
                return apiDataResponse;
            }
            User user = userMapper.selectByPrimaryKey(shop.getUserId());
            if (user == null) {
                apiDataResponse.setInfo(String.format("店铺绑定的用户不存在,userId=%s",shop.getUserId()));
                apiDataResponse.setCode(BaseResponse.PARAMETER_ERROR);
                return apiDataResponse;
            }
            int i = shopMapper.insertSelective(shop);
            apiDataResponse.setInfo(String.format("成功插入 %s 条数据",i));
            apiDataResponse.setCode(BaseResponse.SUCCEED);
        } else {
            Shop temp = shopMapper.selectByPrimaryKey(model.getId());
            if (temp != null) {
                Shop shop = new Shop();
                ObjectCloneHelper.map(model,shop);
                shop.setUpdateTime(new Date());
                int i = shopMapper.updateByPrimaryKeySelective(shop);
                apiDataResponse.setInfo(String.format("成功修改 %s 条数据",i));
                apiDataResponse.setCode(BaseResponse.SUCCEED);
            } else {
                apiDataResponse.setInfo(String.format("找不到相应的数据，id=%s",model.getId()));
                apiDataResponse.setCode(BaseResponse.PARAMETER_ERROR);
            }
        }
        return apiDataResponse;
    }

    @Override
    public ApiDataResponse deleteShop(Integer id) {
        ApiDataResponse apiDataResponse = new ApiDataResponse();
        if (id == null || id < 1) {
            apiDataResponse.setCode(BaseResponse.PARAMETER_ERROR);
            apiDataResponse.setInfo("参数异常，请重新检查");
            return apiDataResponse;
        }
        int i = shopMapper.deleteByPrimaryKey(id);
        apiDataResponse.setInfo(String.format("成功删除 %s 条店铺数据",i));
        apiDataResponse.setCode(BaseResponse.SUCCEED);
        return apiDataResponse;
    }
}
