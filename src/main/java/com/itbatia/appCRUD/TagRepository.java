package com.itbatia.appCRUD;

import com.google.gson.Gson;   // Женя, мне кажется сам подход к реализации методов у меня неправильный.
                               // Просто я что-то ещё не знаю. А что не знаю, сам не знаю :)
import java.io.*;              // На нюансы не обращай внимание, это черновой вариант.
import java.util.List;         // Почему ClassCastException в стриме? Проблема в методе getAll?
import java.util.stream.Collectors;

public class TagRepository {
    public Tag getById(int id) {
        List<Tag> list = getAll().stream().filter(e -> e.getId() == id).collect(Collectors.toList());
        return list.get(0);
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
        List<Tag> list = getAll().stream().filter(e -> e.getId() != id).collect(Collectors.toList());
        try (Writer writer = new FileWriter("src/main/resources/tags.json")) {
            writer.write(new Gson().toJson(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}