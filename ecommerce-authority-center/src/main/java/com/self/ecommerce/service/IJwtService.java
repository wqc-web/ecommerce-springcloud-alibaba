package com.self.ecommerce.service;

import com.self.ecommerce.vo.UsernameAndPasswordVO;

/**
 * Jwt 服务接口
 *
 * @author wangqichao
 */
public interface IJwtService {

    /**
     * 生成Jwt token，使用默认时间
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     * @throws Exception
     */
    String generateToken(String username, String password) throws Exception;

    /**
     * 生成指定超时时间的Jwt token
     *
     * @param username 用户名
     * @param password 密码
     * @param expire   天
     * @return token
     * @throws Exception
     */
    String generateToken(String username, String password, int expire) throws Exception;

    /**
     * 用户注册并生成token
     *
     * @param usernameAndPasswordVO 用户名和密码
     * @return token
     * @throws Exception
     */
    String registerUserAndGenerateToken(UsernameAndPasswordVO usernameAndPasswordVO) throws Exception;

}
