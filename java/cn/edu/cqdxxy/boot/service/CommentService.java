package cn.edu.cqdxxy.boot.service;

import cn.edu.cqdxxy.boot.entity.Batch;
import cn.edu.cqdxxy.boot.entity.Comment;
import com.github.pagehelper.PageInfo;

public interface CommentService {
    void addComment(Comment comment);

    PageInfo<Batch> pageComment(Integer page, Integer pageSize);//分页查询评论

    void deleteComment(Integer id);
}
