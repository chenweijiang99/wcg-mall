package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.annotation.AutoFill;
import com.chenweijiang.wcg_mall.enumeration.OperationType;
import com.chenweijiang.wcg_mall.pojo.Blog;
import com.chenweijiang.wcg_mall.pojo.Comments;
import com.chenweijiang.wcg_mall.pojo.dto.BlogPageDTO;
import com.chenweijiang.wcg_mall.pojo.vo.BlogDetailVO;
import com.chenweijiang.wcg_mall.pojo.vo.CommentsVO;
import org.apache.ibatis.annotations.Delete;
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

    @Select("select * from blog order by create_time desc")
    List<Blog> userGetList();

    @Select("select * from blog where create_user= #{userId}")
    List<Blog> userGetListByUserId(Long userId);

    @Select("select * from blog where id = #{id}")
    Blog findById(Long id);

    @Delete("delete from blog where id = #{id}")
    void deleteBlog(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void updateBlog(Blog blog);

    List<Blog> getLatestBlog();
    @Select("select * from blog where create_user = #{userId} and id !=#{id} order by create_time desc limit 2 ")
    List<Blog> getRelatedBlog(Long userId,Long id);

    BlogDetailVO getBlogDetail(Long id);

    @Insert("insert into blog_comments(blog_id,create_user,create_time,comment) values(#{blogId},#{createUser},#{createTime},#{comment})")
    void addComments(Comments comments);

    @Select("select b.id as id,b.blog_id as blog_id,u.id as create_user,u.username as create_user_name,u.avatar as user_image,b.create_time as createTime,b.comment as comment from blog_comments as b,`user` as u where b.blog_id = #{blogId} and b.create_user = u.id order by b.create_time")
    List<CommentsVO> getComments(Long blogId);
    @Delete("delete from blog_comments where id = #{id} and blog_id=#{blogId}")
    void deleteComments(Long blogId,Long id);
    @Select("SELECT c.blog_id, COUNT(c.blog_id) AS COUNT, b.* " +
            "FROM blog_comments as c " +
            "JOIN blog as b ON c.blog_id = b.id " +
            "GROUP BY c.blog_id " +
            "ORDER BY count DESC " +
            "LIMIT 3")
    List<Blog> getHotBlogWithComment();

    List<BlogDetailVO> getBlogDetailList();

    List<Blog> selectAll(BlogPageDTO blogPageDTO);
}
