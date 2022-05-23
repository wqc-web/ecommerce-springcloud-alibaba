package com.self.ecommerce.dao;

import com.self.ecommerce.entity.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangqichao
 * 用户dao
 */
public interface EcommerceUserDao extends JpaRepository<EcommerceUser, Long> {

    /**
     * select * from t_ecommerce_user where username = ?
     */
    EcommerceUser findByUsername(String username);

    /**
     * select * from t_ecommerce_user where username = ? and password = ?
     */
    EcommerceUser findByUsernameAndPassword(String username, String password);

}
