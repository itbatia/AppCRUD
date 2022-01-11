package com.itbatia.appCRUD.controller;

import com.itbatia.appCRUD.model.*;
import com.itbatia.appCRUD.repository.WriterRepository;
import com.itbatia.appCRUD.repository.gson.GsonWriterRepositoryImpl;

import java.util.List;

public class WriterController {
    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();

    public Writer createWriter(String name, List<Post> posts) {
        Writer writer = new Writer(null, name, posts);
        return writerRepository.save(writer);
    }

    public Writer getWriter(Integer id) {
        return writerRepository.getById(id);
    }

    public void updateWriterName(Integer id, String newName) {
        Writer writer = writerRepository.getById(id);
        writer.setName(newName);
        writerRepository.update(writer);
    }

    public void updateAllWriterPosts(Integer id, List<Post> newPosts) {
        Writer writer = writerRepository.getById(id);
        writer.setPosts(newPosts);
        writerRepository.update(writer);
    }

    public void updateWriterPost(Integer id, Post newPosts) {
        Writer writer = writerRepository.getById(id);
        writer.getPosts().stream().forEach(post -> {
            if (post.getId().equals(newPosts.getId())) {
                post.setTags(newPosts.getTags());
                post.setContent(newPosts.getContent());
                post.setStatus(newPosts.getStatus());
            }
        });
        writerRepository.update(writer);
    }

    public void deleteWriter(Integer id) {
        writerRepository.deleteById(id);
    }

    public List<Writer> getAllWriters() {
        return writerRepository.getAll();
    }
}
