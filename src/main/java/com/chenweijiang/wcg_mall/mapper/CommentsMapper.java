package com.chenweijiang.wcg_mall.mapper;

import com.chenweijiang.wcg_mall.annotation.AutoFill;
import com.chenweijiang.wcg_mall.enumeration.OperationType;
import com.chenweijiang.wcg_mall.pojo.Comments;
import com.chenweijiang.wcg_mall.pojo.vo.CommentsVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 韋
 * 2025/4/23
 */
@Mapper
public interface CommentsMapper {


    int addComments(Comments comments);

    @Select("select count(*) from comments where fid = #{fid} and module = #{module}")
    Integer selectCount(@Param("fid") Integer fid, @Param("module") String module);

    @Select("select b.id as id,b.blog_id as blog_id,u.id as create_user,u.username as create_user_name,u.avatar as user_image,b.create_time as createTime,b.comment as comment from blog_comments as b,`user` as u where b.blog_id = #{blogId} and b.create_user = u.id order by b.create_time")
    List<CommentsVO> getComments(Long blogId);

    List<Comments> selectRoot(Comments comments);

    List<Comments> selectByRootId(Long rootId);



    @Delete("delete from blog_comments where id = #{id} and blog_id=#{blogId}")
    void deleteComments(Long blogId,Long id);


    void updateById(Comments comment);

    @Select("select * from comments where id = #{id}")
    Comments selectById(Long id);
    @Select("select * from comments where pid = #{pid}")
    List<Comments> selectByPid(Long pid);
    @Delete("delete from comments where id = #{id}")
    void deleteById(Long id);
}
