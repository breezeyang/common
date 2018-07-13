package com.breeze.main;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.breeze.mapper.UserMapper;
import com.breeze.model.UserEntity;
 
public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
 
        UserMapper mapper = context.getBean(UserMapper.class);
        mapper.insertOne(new UserEntity(1, "xx", 11));
        mapper.insertOne(new UserEntity(2, "xxzzz", 11));
 
        System.out.println(mapper.selectByPk(1));
        System.out.println(mapper.selectByPk(2));
    }
}
