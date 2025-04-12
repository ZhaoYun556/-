package cn.edu.cqdxxy.boot.controller;

import cn.edu.cqdxxy.boot.entity.Admin;
import cn.edu.cqdxxy.boot.entity.Batch;
import cn.edu.cqdxxy.boot.entity.Comment;
import cn.edu.cqdxxy.boot.entity.Employee;
import cn.edu.cqdxxy.boot.service.AdminService;
import cn.edu.cqdxxy.boot.service.CommentService;
import cn.edu.cqdxxy.boot.service.EmpService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private EmpService empService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AdminService adminService;

    /*1.1(获取页面)评论区页面评论区页面评论区页面评论区页面评论区页面评论区页面*/
    @GetMapping("/toComment")
    public String toComment(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "5") Integer pageSize,
                            Model model){
        //将查询出的评论转发到页面
        PageInfo<Batch> pageInfo = commentService.pageComment(page,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        for (Batch batch : pageInfo.getList()) {
            System.out.println(batch);
        }
        //记录当前页面是第几页
        model.addAttribute("page",page);
        return "otherPage/comment";
    }
    /*1.2(请求处理)评论区页面评论区页面评论区页面评论区页面评论区页面评论区页面*/
    @PostMapping("/publish")
    public String publish(Comment comment){
        if(comment.getContent()==null || "".equals(comment.getContent().trim())){
            System.out.println("评论内容不能为空");
            return "redirect:toComment";
        }
        //把开头和结尾的空字符串过滤掉
        comment.setContent(comment.getContent().trim());
        Date date = new Date();
        Timestamp createTime = new Timestamp(date.getTime());
        comment.setCreateTime(createTime);
        commentService.addComment(comment);
        return "redirect:toComment";
    }
    /*1.2(请求处理)评论区页面评论区页面评论区页面评论区页面评论区页面评论区页面*/


    /*1.1(请求处理)删除评论删除评论删除评论删除评论删除评论删除评论删除评论删除评论删除评论*/
    @GetMapping("/deleteComment")
    public String deleteComment(Integer id,Integer page){
        commentService.deleteComment(id);
        return "redirect:toComment?page="+page;
    }
    /*1.1(请求处理)删除评论删除评论删除评论删除评论删除评论删除评论删除评论删除评论删除评论*/





}
