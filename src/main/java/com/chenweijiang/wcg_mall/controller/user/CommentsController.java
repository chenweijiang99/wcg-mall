package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.Comments;
import com.chenweijiang.wcg_mall.pojo.vo.CommentsVO;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.CommentsService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 韋
 * 2025/4/23
 */
@RestController
@RequestMapping("/user/comments")
@Tag(name = "用户评论相关接口")
@Slf4j
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;

    @GetMapping("/getComments")
    @Operation(summary = "获取评论")
    public Result<List<CommentsVO>> getComments(Long blogId){
        log.info("获取评论{}",blogId);
        List<CommentsVO> commentsList = commentsService.getComments(blogId);
        return Result.success(commentsList);
    }
    @GetMapping("/selectCount/{fid}/{module}")
    @Operation(summary = "查询评论数量")
    public Result<Integer> selectCount(@PathVariable Integer fid, @PathVariable String module) {
        Integer count = commentsService.selectCount(fid, module);
        return Result.success(count);
    }

    @PostMapping("/addComments")
    @Operation(summary = "发表评论")
    public Result<String> addComments(@RequestBody Comments comments){
        log.info("发表评论{}",comments);
        commentsService.addComments(comments);
        return Result.success(MessageConstant.SUCCESS);
    }
    @DeleteMapping("/deleteComments")
    @Operation(summary = "删除评论")
    public Result<String> deleteComments(Long id){
        log.info("删除评论{}",id);
        commentsService.deleteById(id);
        return Result.success(MessageConstant.SUCCESS);
    }


    @GetMapping("/selectTree")
    @Operation(summary = "查询评论树")
    public Result<PageInfo<Comments>> selectTree(Comments comments,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "8") Integer pageSize) {
        PageInfo<Comments> pageInfo = commentsService.selectTree(comments, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}
