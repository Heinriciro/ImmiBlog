package com.ianhein.immiblog2.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity(name="t_type")
@Table
public class Type implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message="分类名称不能为空")
    private String name;

    public Type() {
    }


    //实体类成员
    @OneToMany(mappedBy = "type")
    private List<Blog> blogList;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}
