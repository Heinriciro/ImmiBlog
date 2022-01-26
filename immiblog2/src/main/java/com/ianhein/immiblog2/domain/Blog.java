package com.ianhein.immiblog2.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity(name = "t_blog")
@Table
public class Blog implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message="文章标题不能为空")
    private String title;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    private String coverPicture;
    private String flag;
    private String description;



    private Integer views;
    private boolean appreciationEnabled;
    private boolean sharedEnabled;
    private boolean commentEnabled;
    private boolean published;
    private boolean recommended;

    @Temporal(TemporalType.TIMESTAMP)
    private Date initialTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;




    //实体类成员
    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tagList;

    @Transient
    private String tagIds;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> commentList;

    //方法

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Blog() {
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciationEnabled() {
        return appreciationEnabled;
    }

    public void setAppreciationEnabled(boolean appreciationEnabled) {
        this.appreciationEnabled = appreciationEnabled;
    }

    public boolean isSharedEnabled() {
        return sharedEnabled;
    }

    public void setSharedEnabled(boolean sharedEnabled) {
        this.sharedEnabled = sharedEnabled;
    }

    public boolean isCommentEnabled() {
        return commentEnabled;
    }

    public void setCommentEnabled(boolean commentEnabled) {
        this.commentEnabled = commentEnabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommended() {
        return this.recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public Date getInitialTime() {
        return this.initialTime;
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

    public void init() {
        this.tagIds = tagsToIds(this.getTagList());
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", coverPicture='" + coverPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", description='" + description + '\'' +
                ", views=" + views +
                ", appreciationEnabled=" + appreciationEnabled +
                ", sharedEnabled=" + sharedEnabled +
                ", commentEnabled=" + commentEnabled +
                ", published=" + published +
                ", recommended=" + recommended +
                ", initialTime=" + initialTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tagList=" + tagList +
                ", tagIds='" + tagIds + '\'' +
                ", user=" + user +
                ", commentList=" + commentList +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    private String tagsToIds(List<Tag> tags) {
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(Tag tag : tags){
                if(flag) {
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }

            return ids.toString();
        } else {
            return tagIds;
        }
    }
}
