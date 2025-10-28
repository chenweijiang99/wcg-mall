package com.chenweijiang.wcg_mall.service.impl;

import cn.hutool.core.date.DateUtil;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.mapper.CommentsMapper;
import com.chenweijiang.wcg_mall.pojo.Comments;
import com.chenweijiang.wcg_mall.pojo.vo.CommentsVO;
import com.chenweijiang.wcg_mall.service.CommentsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 韋
 * 2025/4/23
 */
@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsMapper commentsMapper;
    @Override
    public void addComments(Comments comments) {
        comments.setCreateUser(BaseContext.getCurrentId());
        comments.setCreateTime(LocalDateTime.now());
        commentsMapper.addComments(comments); // 插入到数据库的数据会出现自增id，这个id就可以作为自己的root_id

        if (comments.getPid() != null) {  // 有pid说明它是子评论  子评论必须有root_id
            Comments parentComment = this.selectById(comments.getPid());
            comments.setRootId(parentComment.getRootId());  // 如果父级没有root_id 怎么办？
        } else {  // 根节点，给根节点设置root_id
            comments.setRootId(comments.getId());
        }
        this.updateById(comments);

    }

    @Override
    public Integer selectCount(Integer fid, String module) {
        return commentsMapper.selectCount(fid, module);
    }
    @Override
    public void updateById(Comments comment) {

        commentsMapper.updateById(comment);
    }
    @Override
    public Comments selectById(Long id) {
        return commentsMapper.selectById(id);
    }

    @Override
    public List<CommentsVO> getComments(Long blogId) {
        return commentsMapper.getComments(blogId);
    }

    @Override
    public void deleteComments(Long blogId,Long id) {
        commentsMapper.deleteComments(blogId,id);
    }

    @Override
    public PageInfo<Comments> selectTree(Comments comments, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comments> list = commentsMapper.selectRoot(comments);  // 查询一级节点
        for (Comments root : list) {
            List<Comments> children = commentsMapper.selectByRootId(root.getId());
            root.setChildren(children);
        }
        return PageInfo.of(list);
    }

    @Override
    public void deleteById(Long id) {
        this.deepDelete(id);
    }

    private void deepDelete(Long pid) {
        List<Comments> children = commentsMapper.selectByPid(pid);
        commentsMapper.deleteById(pid);
        for (Comments child : children) {
            deepDelete(child.getId());
        }
    }
}
