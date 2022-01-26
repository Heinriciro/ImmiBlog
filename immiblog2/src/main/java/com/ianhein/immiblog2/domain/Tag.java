package com.ianhein.immiblog2.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity(name="t_tag")
@Table
public class Tag implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message="标签名称不能为空")
    private String name;
    //实体类对象
    @ManyToMany(mappedBy = "tagList")
    private List<Blog> blogList;

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }



    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }



}
