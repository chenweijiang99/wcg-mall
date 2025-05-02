package com.chenweijiang.wcg_mall.service.impl;

import com.chenweijiang.wcg_mall.mapper.BlogMapper;
import com.chenweijiang.wcg_mall.pojo.Blog;
import com.chenweijiang.wcg_mall.pojo.Comments;
import com.chenweijiang.wcg_mall.pojo.Product;
import com.chenweijiang.wcg_mall.pojo.dto.BlogPageDTO;
import com.chenweijiang.wcg_mall.pojo.vo.BlogDetailVO;
import com.chenweijiang.wcg_mall.pojo.vo.CommentsVO;
import com.chenweijiang.wcg_mall.service.BlogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
   
    private final BlogMapper blogMapper;

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
    public List<Blog> getRelatedBlog(Long userId,Long id) {
        return blogMapper.getRelatedBlog(userId,id);
    }

    @Override
    public BlogDetailVO getBlogDetail(Long id) {
        return blogMapper.getBlogDetail(id);
    }



    @Override
    public List<Blog> getHotBlogWithComment() {
        return blogMapper.getHotBlogWithComment();
    }

    @Override
    public List<BlogDetailVO> getBlogList() {
        return blogMapper.getBlogDetailList();
    }

    @Override
    public PageInfo<Blog> selectPage(Integer pageNum, Integer pageSize, BlogPageDTO blogPageDTO) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogList = blogMapper.selectAll(blogPageDTO);
        return PageInfo.of(blogList);
    }
}
