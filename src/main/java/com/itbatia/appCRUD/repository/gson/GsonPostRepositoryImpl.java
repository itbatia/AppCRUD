package com.itbatia.appCRUD.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itbatia.appCRUD.model.Post;
import com.itbatia.appCRUD.repository.PostRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class GsonPostRepositoryImpl implements PostRepository {

    private static final String FILE_PATH = "src/main/resources/posts.json";
    private final Gson gson = new Gson();

    @Override
    public List<Post> getAll() {
        return getAllPostsInternal();
    }

    private List<Post> getAllPostsInternal() {
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
                Type targetClassType = new TypeToken<ArrayList<Post>>() {
                }.getType();
                return gson.fromJson(json, targetClassType);
            } else {
                return new ArrayList<Post>();
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Post getById(Integer id) {
        return getAllPostsInternal().stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Post save(Post p) {
        List<Post> currentPosts = getAllPostsInternal();
        Integer id = generateID(currentPosts);
        p.setId(id);
        currentPosts.add(p);
        writeToFile(currentPosts);
        return p;
    }

    @Override
    public Post update(Post p) {
        List<Post> currentPosts = getAllPostsInternal();
        currentPosts.forEach(post -> {
            if (post.getId().equals(p.getId())) {
                post.setContent(p.getContent());
            }
        });
        writeToFile(currentPosts);
        return p;
    }

    @Override
    public void deleteById(Integer id) {
        List<Post> currentPosts = getAllPostsInternal();
        currentPosts.removeIf(post -> post.getId().equals(id));
        for (int i = 0; i < currentPosts.size(); i++) {
            currentPosts.get(i).setId(i + 1);
        }
        writeToFile(currentPosts);
    }

    private Integer generateID(List<Post> posts) {
        Post postWithMaxID = posts.stream().max(Comparator.comparing(Post::getId)).orElse(null);
        return Objects.nonNull(postWithMaxID) ? postWithMaxID.getId() + 1 : 1;
    }

    private void writeToFile(List<Post> posts) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            writer.write(gson.toJson(posts));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
