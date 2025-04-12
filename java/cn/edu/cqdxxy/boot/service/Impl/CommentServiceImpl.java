package cn.edu.cqdxxy.boot.service.Impl;

import cn.edu.cqdxxy.boot.entity.Batch;
import cn.edu.cqdxxy.boot.entity.Comment;
import cn.edu.cqdxxy.boot.mapper.CommentMapper;
import cn.edu.cqdxxy.boot.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public PageInfo<Batch> pageComment(Integer page,Integer pageSize) {;//分页查询评论
        PageHelper.startPage(page,pageSize);
        List<Batch> BatchList = commentMapper.findAllComment();
        PageInfo<Batch> pageInfo = new PageInfo<>(BatchList,5);
        return pageInfo;
    }

    @Override
    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }

    @Override
    public void deleteComment(Integer id) {
        commentMapper.deleteComment(id);
    }
}
