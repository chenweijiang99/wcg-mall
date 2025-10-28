package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.Blog;
import com.chenweijiang.wcg_mall.pojo.Comments;
import com.chenweijiang.wcg_mall.pojo.dto.BlogPageDTO;
import com.chenweijiang.wcg_mall.pojo.vo.BlogDetailVO;
import com.chenweijiang.wcg_mall.pojo.vo.CommentsVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BlogService {
    void addBlog(Blog blog);

    List<Blog> userGetList();

    List<Blog> userGetListByUserId(Long userId);

    Blog findById(Long id);

    void deleteBlog(Long id);

    void updateBlog(Blog blog);

    List<Blog> getLatestBlog();

    List<Blog> getRelatedBlog(Long userId,Long id);

    BlogDetailVO getBlogDetail(Long id);



    List<Blog> getHotBlogWithComment();

    List<BlogDetailVO> getBlogList();

    PageInfo<Blog> selectPage(Integer pageNum, Integer pageSize, BlogPageDTO blogPageDTO);
}
