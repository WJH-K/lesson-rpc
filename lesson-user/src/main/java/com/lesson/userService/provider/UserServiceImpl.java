package com.lesson.userService.provider;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lesson.api.userApi.UserService;
import com.lesson.commons.Checker;
import com.lesson.commons.ObjectCloneHelper;
import com.lesson.commons.RedisHelper;
import com.lesson.modelInfo.base.ApiDataResponse;
import com.lesson.modelInfo.base.BaseResponse;
import com.lesson.modelInfo.base.PageDataResponse;
import com.lesson.modelInfo.user.UserBase;
import com.lesson.modelInfo.user.UserInfo;
import com.lesson.modelInfo.user.UserReq;
import com.lesson.userService.common.redis.cluster.RedisCacheManager;
import com.lesson.userService.mapper.lesson.user.UserMapper;
import com.lesson.userService.model.lesson.user.User;
import com.lesson.userService.model.lesson.user.UserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisCacheManager cacheManager;

    @Override
    public String helloWord() {
        return "helloWord";
    }

    @Override
    public PageDataResponse<UserInfo> selectAllUser() {
        PageDataResponse<UserInfo> response = new PageDataResponse<UserInfo>();
        try {
            List<UserInfo> data = cacheManager.getList("ALLUSER",UserInfo.class);
            response.setInfo("from cache");
            if (Checker.isNone(data)) {
                data = new ArrayList<>();
                List<User> list = userMapper.selectByExample(new UserExample());
                if (!Checker.isNone(list)) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) != null) {
                            UserInfo userInfo = new UserInfo();
                            ObjectCloneHelper.map(list.get(i),userInfo);
                            data.add(userInfo);
                        }
                    }
                }
                if (!Checker.isNone(data)) {
                    response.setInfo("from db");
                    cacheManager.setList("ALLUSER",data,30*60,UserInfo.class);
                } else {
                    response.setInfo("数据为空");
                }
            }
            response.setCode(BaseResponse.SUCCEED);
            response.setPageData(data);
        } catch (Exception e) {
            logger.error("selectAllUser is error",e);
            response.setCode(BaseResponse.APP_EXCEPTION);
            response.setInfo("获取用户列表异常");
        }
        return response;
    }

    @Override
    public PageDataResponse<UserInfo> selectUserList(UserReq req) {
        PageDataResponse<UserInfo> response = null;
        try {
            if (req == null) {
                response = new PageDataResponse<UserInfo>();
                response.setCode(BaseResponse.PARAMETER_ERROR);
                response.setInfo("参数异常，请重新检查");
                return response;
            }
            UserExample example = new UserExample();
            UserExample.Criteria criteria = example.createCriteria();
            if (req.getId() != null && req.getId() > 0) {
                criteria.andIdEqualTo(req.getId());
            }
            if (!Checker.isNone(req.getUsername())) {
                criteria.andUsernameLike("%"+req.getUsername()+"%");
            }
            if (!Checker.isNone(req.getPassword())) {
                criteria.andPasswordEqualTo(req.getPassword());
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
            String key = RedisHelper.getRedisKey("userPage",req);
            response = cacheManager.get(key,PageDataResponse.class);
            if (response == null) {
                response = new PageDataResponse<>();
                Page page = PageHelper.startPage(req.getPageIndex(),req.getPageSize(),req.getOrderBy());
                List<User> list = userMapper.selectByExample(example);
                PageInfo<User> pageInfo = new PageInfo<User>(list);
                List<UserInfo> data = new ArrayList<>();
                if (!Checker.isNone(list)) {
                    for (User user : list) {
                        if (user != null) {
                            UserInfo userInfo = new UserInfo();
                            ObjectCloneHelper.map(user,userInfo);
                            data.add(userInfo);
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
            logger.error("获取用户列表出错，req={}",JSON.toJSONString(req));
            response.setCode(BaseResponse.APP_EXCEPTION);
            response.setInfo(String.format("获取用户列表出错，req={}",JSON.toJSONString(req)));
        }
        return response;
    }

    @Override
    public ApiDataResponse saveUser(UserBase model) {
        ApiDataResponse apiDataResponse = new ApiDataResponse();
        if (model == null) {
            apiDataResponse.setCode(BaseResponse.PARAMETER_ERROR);
            apiDataResponse.setInfo("参数异常，请重新检查");
            return apiDataResponse;
        }
        if (model.getId() == null || model.getId() < 1) {
            model.setCreateTime(new Date());
            User user = new User();
            ObjectCloneHelper.map(model,user);
            int i = userMapper.insertSelective(user);
            apiDataResponse.setInfo(String.format("成功插入 %s 条数据",i));
            apiDataResponse.setCode(BaseResponse.SUCCEED);
        } else {
            User temp = userMapper.selectByPrimaryKey(model.getId());
            if (temp != null) {
                User user = new User();
                ObjectCloneHelper.map(model,user);
                int i = userMapper.updateByPrimaryKeySelective(user);
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
    public ApiDataResponse deleteUser(Integer id) {
        ApiDataResponse apiDataResponse = new ApiDataResponse();
        if (id == null || id < 1) {
            apiDataResponse.setCode(BaseResponse.PARAMETER_ERROR);
            apiDataResponse.setInfo("参数异常，请重新检查");
            return apiDataResponse;
        }
        int i = userMapper.deleteByPrimaryKey(id);
        apiDataResponse.setInfo(String.format("成功删除 %s 条数据",i));
        apiDataResponse.setCode(BaseResponse.SUCCEED);
        return apiDataResponse;
    }
}
