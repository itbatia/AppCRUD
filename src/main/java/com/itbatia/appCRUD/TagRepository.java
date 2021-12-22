package com.itbatia.appCRUD;

import com.google.gson.Gson;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class TagRepository {
    public Tag getById(int id) {

        List<Tag> list = getAll().stream().filter(e -> e.getId() == id).collect(Collectors.toList());
        return list.get(0);

//        return null;
    }

    public List<Tag> getAll() {
        List<Tag> list = null;
        try (Reader reader = new FileReader("src/main/resources/tags.json")) {
            list = new Gson().fromJson(reader, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Tag save(Tag t) {
        List<Tag> list = getAll();
        list.add(t);
        try (Writer writer = new FileWriter("src/main/resources/tags.json")) {
            writer.write(new Gson().toJson(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public Tag update(Tag t) {
        return null;
    }

    void deleteById(int id) {
        List<Tag> list = getAll().stream().peek(System.out::println).collect(Collectors.toList());
        try (Writer writer = new FileWriter("src/main/resources/tags.json")) {
            writer.write(new Gson().toJson(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}