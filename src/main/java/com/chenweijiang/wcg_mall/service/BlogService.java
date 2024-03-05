package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.Blog;
import com.chenweijiang.wcg_mall.pojo.vo.BlogDetailVO;

import java.util.List;

public interface BlogService {
    void addBlog(Blog blog);

    List<Blog> userGetList();

    List<Blog> userGetListByUserId(Long userId);

    Blog findById(Long id);

    void deleteBlog(Long id);

    void updateBlog(Blog blog);

    List<Blog> getLatestBlog();

    List<Blog> getRelatedBlog(Long userId);

    BlogDetailVO getBlogDetail(Long id);
}
