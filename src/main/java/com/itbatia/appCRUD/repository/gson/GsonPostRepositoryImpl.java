package com.itbatia.appCRUD.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itbatia.appCRUD.model.Post;
import com.itbatia.appCRUD.repository.PostRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;

public class GsonPostRepositoryImpl implements PostRepository {

    private static final String FILE_PATH = "src/main/resources/posts.json";
    private final Gson gson = new Gson();

    @Override
    public List<Post> getAll() {
        return getAllPostsInternal();
    }

    private List<Post> getAllPostsInternal() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            if (content.length() == 0){
                return new ArrayList<Post>();
            }
            Type targetClassType = new TypeToken<ArrayList<Post>>() {
            }.getType();
            return gson.fromJson(content, targetClassType);
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
                post.setStatus(p.getStatus());
                post.setTags(p.getTags());
            }
        });
        writeToFile(currentPosts);
        return p;
    }

    @Override
    public void deleteById(Integer id) {
        List<Post> currentPosts = getAllPostsInternal();
        currentPosts.removeIf(post -> post.getId().equals(id));
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
