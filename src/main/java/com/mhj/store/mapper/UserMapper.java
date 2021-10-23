package com.mhj.store.mapper;

import com.mhj.store.element.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface UserMapper {
    Integer insert(User user);

    User findByUsername(String username);

    User findByUid(Integer uid);

    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                Date modifiedTime);

    Integer updateInfoByUid(User user);

    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              String avatar,
                              String modifiedUser,
                              Date modifiedTime);


}
