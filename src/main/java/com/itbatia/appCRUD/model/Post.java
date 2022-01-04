package com.itbatia.appCRUD.model;

import java.util.List;

public class Post {
    private Integer id;
    private String content;
    private List<Tag> tags;
    private PostStatus status;

    public Post(Integer id, String content, List<Tag> tags, PostStatus status) {
        this.id = id;
        this.content = content;
        this.tags = tags;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", status=" + status +
                '}';
    }
}
