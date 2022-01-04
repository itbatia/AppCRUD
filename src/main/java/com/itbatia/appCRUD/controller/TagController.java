package com.itbatia.appCRUD.controller;

import com.itbatia.appCRUD.model.Tag;
import com.itbatia.appCRUD.repository.TagRepository;
import com.itbatia.appCRUD.repository.gson.GsonTagRepositoryImpl;

import java.util.List;

public class TagController {
    private final TagRepository tagRepository = new GsonTagRepositoryImpl();

    public Tag createTag(String name) {
        Tag tag = new Tag(null, name);
        return tagRepository.save(tag);
    }

    public Tag getTag(Integer id) {
        return tagRepository.getById(id);
    }

    public Tag updateTag(Integer tagChange, String newName) {
        Tag newTag = new Tag(tagChange, newName);
        return tagRepository.update(newTag);
    }

    public void deleteTag(Integer id) {
        tagRepository.deleteById(id);
    }

    public List<Tag> getAllTags() {
        return tagRepository.getAll();
    }
}
