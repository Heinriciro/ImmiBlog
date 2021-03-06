package com.ianhein.immiblog2.dao;

import com.ianhein.immiblog2.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment>  findByBlogId(Long blogId, Sort sort);
}
