package com.self.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * java8 Predicate 使用方法与思想
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PredicateTest {

    List<String> list = Arrays.asList(
            "nacos", "authority", "gateway", "ribbon", "feign", "hystrix", "ecommerce"
    );

    /**
     * 验证参数是否符合规则
     */
    @Test
    public void testPredicate() {
        Predicate<String> letterLengthLimit = s -> StringUtils.isNotBlank(s) && s.length() > 5;
        List<String> collect = list.stream().filter(letterLengthLimit).collect(Collectors.toList());
        System.out.println(collect.toString());
    }

    /**
     * 逻辑非 !
     */
    @Test
    public void testPredicateNegate() {
        Predicate<String> letterLengthLimit = s -> StringUtils.isNotBlank(s) && s.length() > 5;
        List<String> collect = list.stream().filter(letterLengthLimit.negate()).collect(Collectors.toList());
        System.out.println(collect.toString());
    }

    /**
     * isEqual，类似equals()，区别在于：先判断对象是否为null，不为null再使用
     */
    @Test
    public void testPredicateIsEqual() {
        Predicate<String> letterLengthLimit = s -> Predicate.isEqual("gateway").test(s);
        List<String> collect = list.stream().filter(letterLengthLimit).collect(Collectors.toList());
        System.out.println(collect.toString());
    }

}
