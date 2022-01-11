package com.itbatia.appCRUD.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itbatia.appCRUD.model.Writer;
import com.itbatia.appCRUD.repository.WriterRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;

public class GsonWriterRepositoryImpl implements WriterRepository {

    private static final String FILE_PATH = "src/main/resources/writers.json";
    private final Gson gson = new Gson();

    @Override
    public List<Writer> getAll() {
        return getAllWritersInternal();
    }

    private List<Writer> getAllWritersInternal() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            if (content.length() == 0){
                return new ArrayList<Writer>();
            }
            Type targetClassType = new TypeToken<ArrayList<Writer>>() {
            }.getType();
            return gson.fromJson(content, targetClassType);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Writer getById(Integer id) {
        return getAllWritersInternal().stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Writer save(Writer w) {
        List<Writer> currentWriters = getAllWritersInternal();
        Integer id = generateID(currentWriters);
        w.setId(id);
        currentWriters.add(w);
        writeToFile(currentWriters);
        return w;
    }

    private Integer generateID(List<Writer> writers) {
        Writer writerWithMaxID = writers.stream().max(Comparator.comparing(Writer::getId)).orElse(null);
        return Objects.nonNull(writerWithMaxID) ? writerWithMaxID.getId() + 1 : 1;
    }

    private void writeToFile(List<Writer> writers) {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            fileWriter.write(gson.toJson(writers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Writer update(Writer w) {
        List<Writer> currentWriters = getAllWritersInternal();
        currentWriters.forEach(writer -> {
            if (writer.getId().equals(w.getId())) {
                writer.setName(w.getName());
                writer.setPosts(w.getPosts());
            }
        });
        writeToFile(currentWriters);
        return w;
    }

    @Override
    public void deleteById(Integer id) {
        List<Writer> currentWriters = getAllWritersInternal();
        currentWriters.removeIf(writer -> writer.getId().equals(id));
        writeToFile(currentWriters);
    }
}
