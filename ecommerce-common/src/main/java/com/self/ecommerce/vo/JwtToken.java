package com.self.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 授权中心鉴权后生成的Token
 *
 * @author wangqichao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

    private String token;

}
