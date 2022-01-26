package com.ianhein.immiblog2.Service;

import com.ianhein.immiblog2.domain.Comment;
import java.util.List;

public interface CommentService {

    List<Comment> listCommentsByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
