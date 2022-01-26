package com.ianhein.immiblog2.Service;

import com.ianhein.immiblog2.dao.CommentRepository;
import com.ianhein.immiblog2.domain.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentsByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.ASC,"initialTime");
        List<Comment> comments = commentRepository.findByBlogId(blogId,sort);
        return eachComment(comments);
    }

    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId!=-1) {
            comment.setParentComment(commentRepository.getById(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setInitialTime(new Date());
        return commentRepository.save(comment);
    }

    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> newComments = new ArrayList<>();

        for(Comment comment : comments){
            if(comment.getParentComment()==null){
                Comment c = new Comment();
                BeanUtils.copyProperties(comment,c);
                newComments.add(c);
            }
        }

        mergeChildren(newComments);
        return newComments;
    }

    private void mergeChildren(List<Comment> comments){
        for(Comment comment:comments){
            List<Comment> replys = comment.getReplyCommentList();
            for(Comment reply:replys){
                recur(reply);
            }
            comment.setReplyCommentList(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    private List<Comment> tempReplys = new ArrayList<>();

    private void recur(Comment comment){
        tempReplys.add(comment);
        if(comment.getReplyCommentList().size()>0){
            List<Comment> replys = comment.getReplyCommentList();
            for(Comment reply:replys){
                tempReplys.add(reply);
                if(reply.getReplyCommentList().size()>0) recur(reply);
            }
        }
    }
}
