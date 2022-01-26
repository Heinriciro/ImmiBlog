package com.ianhein.immiblog2.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name="t_user")
@Table
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String nickname;
    private String password;


    private String email;
    private String avatar;
    private Integer type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date initialTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    //实体类成员

    @OneToMany(mappedBy = "user")
    private List<Blog> blogList;


    //方法

    public User() {
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(Date initialTime) {
        this.initialTime = initialTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", initialTime=" + initialTime +
                ", updateTime=" + updateTime +
                ", blogList=" + blogList +
                '}';
    }
}
