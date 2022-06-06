package com.self.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录用户信息
 *
 * @author wangqichao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

}
