package com.lesson.api.userApi;

import com.lesson.modelInfo.base.ApiDataResponse;
import com.lesson.modelInfo.base.PageDataResponse;
import com.lesson.modelInfo.user.UserBase;
import com.lesson.modelInfo.user.UserInfo;
import com.lesson.modelInfo.user.UserReq;

public interface UserService {
    public String helloWord();

    /**
     * 查询所有user
     * @return
     */
    PageDataResponse<UserInfo> selectAllUser();
    /**
     * 通过userReq查询user列表
     * @param req
     * @return
     */
    PageDataResponse<UserInfo> selectUserList(UserReq req);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    ApiDataResponse saveUser(UserBase user);

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    ApiDataResponse deleteUser(Integer id);
}
