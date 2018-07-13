package com.breeze.mapper;

import com.breeze.model.UserEntity;

public interface UserMapper {

    int insertOne(UserEntity user);

    UserEntity selectByPk(int id);
}
