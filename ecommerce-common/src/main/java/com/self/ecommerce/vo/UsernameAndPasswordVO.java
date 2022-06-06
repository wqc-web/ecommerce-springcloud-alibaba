package com.self.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户名和密码
 *
 * @author wangqichao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameAndPasswordVO {

    private String username;
    private String password;

}
