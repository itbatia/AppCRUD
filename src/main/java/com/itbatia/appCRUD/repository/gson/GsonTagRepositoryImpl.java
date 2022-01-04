package com.itbatia.appCRUD.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itbatia.appCRUD.model.Tag;
import com.itbatia.appCRUD.repository.TagRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class GsonTagRepositoryImpl implements TagRepository {

    private static final String FILE_PATH = "src/main/resources/tags.json";
    private final Gson gson = new Gson();

    public Tag getById(Integer id) {
        return getAllTagsInternal().stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Tag> getAll() {
        return getAllTagsInternal();
    }

    private List<Tag> getAllTagsInternal() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            if (reader.read() != -1) {
                char[] charArray = new char[1024];
                StringBuilder sb = new StringBuilder("[");
                int i = reader.read(charArray);
                while (i > 0) {
                    sb.append(charArray, 0, i);
                    i = reader.read(charArray);
                }
                String json = new String(sb);
                Type targetClassType = new TypeToken<ArrayList<Tag>>() {
                }.getType();
                return gson.fromJson(json, targetClassType);
            } else {
                return new ArrayList<Tag>();
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public Tag save(Tag t) {
        List<Tag> currentTags = getAllTagsInternal();
        Integer id = generateID(currentTags);
        t.setId(id);
        currentTags.add(t);
        writeToFile(currentTags);
        return t;
    }

    public Tag update(Tag t) {
        List<Tag> currentTags = getAllTagsInternal();
        currentTags.forEach(tag -> {
            if (tag.getId().equals(t.getId())) {
                tag.setName(t.getName());
            }
        });
        writeToFile(currentTags);
        return t;
    }

    public void deleteById(Integer id) {
        List<Tag> currentTags = getAllTagsInternal();
        currentTags.removeIf(tag -> tag.getId().equals(id));
        for (int i = 0; i < currentTags.size(); i++) {
            currentTags.get(i).setId(i + 1);
        }
        writeToFile(currentTags);
    }

    private Integer generateID(List<Tag> tags) {
        Tag tagWithMaxID = tags.stream().max(Comparator.comparing(Tag::getId)).orElse(null);
        return Objects.nonNull(tagWithMaxID) ? tagWithMaxID.getId() + 1 : 1;
    }

    private void writeToFile(List<Tag> tags) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            writer.write(gson.toJson(tags));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}