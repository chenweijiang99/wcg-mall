package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.BlogMapper;
import com.chenweijiang.wcg_mall.pojo.Blog;
import com.chenweijiang.wcg_mall.pojo.vo.BlogDetailVO;
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

    @Override
    public List<Blog> userGetListByUserId(Long userId) {
        return blogMapper.userGetListByUserId(userId);
    }

    @Override
    public Blog findById(Long id) {
        return blogMapper.findById(id);
    }

    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
    }

    @Override
    public void updateBlog(Blog blog) {
        blogMapper.updateBlog(blog);
    }

    @Override
    public List<Blog> getLatestBlog() {
        return blogMapper.getLatestBlog();
    }

    @Override
    public List<Blog> getRelatedBlog(Long userId) {
        return blogMapper.getRelatedBlog(userId);
    }

    @Override
    public BlogDetailVO getBlogDetail(Long id) {
        return blogMapper.getBlogDetail(id);
    }
}
