package com.chenweijiang.wcg_mall.service;


import com.chenweijiang.wcg_mall.pojo.Comments;
import com.chenweijiang.wcg_mall.pojo.vo.CommentsVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 韋
 * 2025/4/23
 */
public interface CommentsService {
    void addComments(Comments comments);
    Integer selectCount(Integer fid, String module);
    List<CommentsVO> getComments(Long blogId);

    void deleteComments(Long blogId,Long id);
    void updateById(Comments comment);
    Comments selectById(Long id);
    PageInfo<Comments> selectTree(Comments comments, Integer pageNum, Integer pageSize);

    void deleteById(Long id);
}
