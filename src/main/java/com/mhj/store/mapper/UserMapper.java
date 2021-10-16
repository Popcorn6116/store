package com.mhj.store.mapper;

import com.mhj.store.element.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    Integer insert(User user);

    User findByUsername(String username);
}
