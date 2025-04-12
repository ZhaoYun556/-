package cn.edu.cqdxxy.boot.mapper;

import cn.edu.cqdxxy.boot.entity.Batch;
import cn.edu.cqdxxy.boot.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(user_id,content,create_time) values(#{userId},#{content},#{createTime})")
    void addComment(Comment comment);
@Select("select " +
        "c.id,c.content," +
        "c.love,c.create_time," +
        "a.id_admin,a.username,a.image_path as image_patha,a.is_admin," +
        "e.id_emp,e.name,e.image_path as image_pathe,e.is_admin " +
        "from comment as c left join admin as a on c.user_id=a.id_admin " +
        "left join test as e on c.user_id=e.id_emp order by create_time desc")
    List<Batch> findAllComment();
@Delete("delete from comment where id=#{id}")
    void deleteComment(Integer id);
}
