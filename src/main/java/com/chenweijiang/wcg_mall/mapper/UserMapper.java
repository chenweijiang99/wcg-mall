package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User getById(Long id);

    @Select("select * from user where email = #{email}")
    User findUserByEmail(String email);

    void saveUser(User user);
    @Update("update user set state = 1 where email = #{email}")
    void activateUser(String email);
}
