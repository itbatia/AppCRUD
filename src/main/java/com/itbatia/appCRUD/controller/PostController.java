package com.itbatia.appCRUD.controller;

import com.itbatia.appCRUD.model.*;
import com.itbatia.appCRUD.repository.PostRepository;
import com.itbatia.appCRUD.repository.gson.GsonPostRepositoryImpl;

import java.util.List;

public class PostController {
    private final PostRepository postRepository = new GsonPostRepositoryImpl();

    public Post createPost(String content, List<Tag> tags, PostStatus postStatus) {
        Post post = new Post(null, content, tags, postStatus);
        return postRepository.save(post);
    }

    public Post getPost(Integer id) {
        return postRepository.getById(id);
    }

    public Post changePost(Integer id, Post newPost) {
        return postRepository.update(newPost);
    }

    public Post updatePostContent(Integer id, String newContent) {
        Post post = postRepository.getById(id);
        post.setContent(newContent);
        return postRepository.update(post);
    }

    public Post replacePostTags(Integer id, List<Tag> tags) {
        Post post = postRepository.getById(id);
        post.setTags(tags);
        return postRepository.update(post);
    }

    public Post addTagToPost(Integer id, Tag tag) {
        Post post = postRepository.getById(id);
        post.getTags().add(tag);
        return postRepository.update(post);
    }

    public boolean deleteTagFromPost(Integer postId, Integer tagId) {
        Post post = postRepository.getById(postId);
        List<Tag> tags = post.getTags();
        boolean result = tags.removeIf(tag -> tag.getId().equals(tagId));
        postRepository.update(post);
        return result;
    }

    public Post updatePostStatus(Integer id, PostStatus postStatus) {
        Post post = postRepository.getById(id);
        post.setStatus(postStatus);
        return postRepository.update(post);
    }

    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    public List<Post> getAllPosts() {
        return postRepository.getAll();
    }
}
