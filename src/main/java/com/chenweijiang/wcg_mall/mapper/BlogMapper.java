package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.annotation.AutoFill;
import com.chenweijiang.wcg_mall.enumeration.OperationType;
import com.chenweijiang.wcg_mall.pojo.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogMapper {
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into blog(create_time,update_time,create_user,update_user,title,content,image) " +
            "values(#{createTime},#{updateTime},#{createUser},#{updateUser},#{title},#{content},#{image})")
    void addBlog(Blog blog);

    @Select("select * from blog")
    List<Blog> userGetList();
}
