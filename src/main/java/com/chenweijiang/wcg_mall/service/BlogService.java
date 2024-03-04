package com.chenweijiang.wcg_mall.service;

import com.chenweijiang.wcg_mall.pojo.Blog;

import java.util.List;

public interface BlogService {
    void addBlog(Blog blog);

    List<Blog> userGetList();
}
