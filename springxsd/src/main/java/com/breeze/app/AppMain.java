package com.breeze.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.breeze.model.User;

public class AppMain {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        User p = (User) ctx.getBean(User.class);
        System.out.println(p.getId());
        System.out.println(p.getName());
        System.out.println(p.getAge());

    }

}
