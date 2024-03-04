package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.BlogMapper;
import com.chenweijiang.wcg_mall.pojo.Blog;
import com.chenweijiang.wcg_mall.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public void addBlog(Blog blog) {
        blogMapper.addBlog(blog);
    }

    @Override
    public List<Blog> userGetList() {
        return blogMapper.userGetList();
    }
}
