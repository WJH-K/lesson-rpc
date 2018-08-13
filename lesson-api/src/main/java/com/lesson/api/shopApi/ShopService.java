package com.lesson.api.shopApi;

import com.lesson.modelInfo.base.ApiDataResponse;
import com.lesson.modelInfo.base.PageDataResponse;
import com.lesson.modelInfo.shop.ShopBase;
import com.lesson.modelInfo.shop.ShopInfo;
import com.lesson.modelInfo.shop.ShopReq;

public interface ShopService {

    /**
     * 查询所有店铺
     * @return
     */
    PageDataResponse<ShopInfo> selectAllShop();
    /**
     * 通过shopReq查询店铺列表
     * @param req
     * @return
     */
    PageDataResponse<ShopInfo> selectShopList(ShopReq req);

    /**
     * 保存店铺信息
     * @param shop
     * @return
     */
    ApiDataResponse saveShop(ShopBase shop);

    /**
     * 删除店铺信息
     * @param id
     * @return
     */
    ApiDataResponse deleteShop(Integer id); 
}
