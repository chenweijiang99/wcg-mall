package com.chenweijiang.wcg_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenweijiang.wcg_mall.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where id = #{id}")
    User getById(Long id);

    @Select("select * from user where email = #{email}")
    User findUserByEmail(String email);

    void saveUser(User user);
    @Update("update user set state = 1 where email = #{email}")
    void activateUser(String email);
    @Update("update user set username = #{username},phone = #{phone},avatar = #{avatar},state = 1 where email = #{email}")
    void updateUserByEmail(String email, String username, String phone, String avatar);

    void updateUser(User user);
    @Update("update user set password = #{rsaPassword} where id = #{currentId}")
    void updateUserPassword(String rsaPassword, Long currentId);

    @Select("select * from user")
    List<User> getUserList();

    @Delete("delete from user where id = #{id}")
    void deleteById(Long id);
}
