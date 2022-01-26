package com.ianhein.immiblog2.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name="t_comment")
@Table
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String email;
    private String content;

    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date initialTime;



    //实体类对象
    @ManyToOne
    private Blog blog;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyCommentList = new ArrayList<>();

    @ManyToOne
    private Comment parentComment;


//方法
    public Comment() {
    }


    public List<Comment> getReplyCommentList() {
        return replyCommentList;
    }

    public void setReplyCommentList(List<Comment> replyCommentList) {
        this.replyCommentList = replyCommentList;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(Date initialTime) {
        this.initialTime = initialTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", initialTime=" + initialTime +
                ", admin=" + admin +
                ", blog=" + blog +
                ", replyCommentList=" + replyCommentList +
                ", parentComment=" + parentComment +
                '}';
    }

    private boolean admin;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
