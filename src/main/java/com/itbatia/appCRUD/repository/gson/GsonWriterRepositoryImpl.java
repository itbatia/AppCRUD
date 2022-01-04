package com.itbatia.appCRUD.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itbatia.appCRUD.model.Writer;
import com.itbatia.appCRUD.repository.WriterRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class GsonWriterRepositoryImpl implements WriterRepository {

    private static final String FILE_PATH = "src/main/resources/writers.json";
    private final Gson gson = new Gson();

    @Override
    public List<Writer> getAll() {
        return getAllWritersInternal();
    }

    private List<Writer> getAllWritersInternal() {
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
                Type targetClassType = new TypeToken<ArrayList<Writer>>() {
                }.getType();
                return gson.fromJson(json, targetClassType);
            } else {
                return new ArrayList<Writer>();
            }
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
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(gson.toJson(writers));
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
            }
        });
        writeToFile(currentWriters);
        return w;
    }

    @Override
    public void deleteById(Integer id) {
        List<Writer> currentWriters = getAllWritersInternal();
        currentWriters.removeIf(writer -> writer.getId().equals(id));
        for (int i = 0; i < currentWriters.size(); i++) {
            currentWriters.get(i).setId(i + 1);
        }
        writeToFile(currentWriters);
    }
}
