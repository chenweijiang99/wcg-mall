package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User getById(Long id);

    @Select("select * from user where email = #{email}")
    User findUserByEmail(String email);

    void saveUser(User user);
}
