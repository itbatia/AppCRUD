package com.itbatia.appCRUD.view;

import com.itbatia.appCRUD.controller.*;
import com.itbatia.appCRUD.model.*;

import static com.itbatia.appCRUD.utils.Messages.*;

import java.util.*;
import java.util.stream.Collectors;

public class WriterView {
    private final WriterController writerController = new WriterController();
    private final PostView postView = new PostView();
    private final Scanner scanner = new Scanner(System.in);

    public void createWriter() {
        System.out.println(ENTER_WRITER.getMessage());
        String writerName = scanner.nextLine();
        List<Post> posts = new ArrayList<>();
        System.out.println("The writer is created. Add post to him?\n1 - YES\n2 - NO");
        boolean result = answerFromUser();
        while (result) {
            posts.add(postView.createPost());
            System.out.println("Add another post to him?\n1 - YES\n2 - NO");
            result = answerFromUser();
        }
        Writer newWriter = writerController.createWriter(writerName, posts);
        System.out.println("Created writer:");
        writerToString(newWriter);
    }

    public Writer getWriter() {
        int id = idFromUser();
        if (id != 0) {
            System.out.print("Found Writer: ");
            writerToString(writerController.getWriter(id));
            return writerController.getWriter(id);
        } else return null;
    }

    public void updateWriterById() {
        Writer writer = getWriter();
        if (writer != null) {
            updateWriter(writer);
        }
    }

    public void updateWriter(Writer writer) {
        System.out.println(UPDATE_WRITER_SELECTION_1.getMessage());
        String userInput = scanner.nextLine();
        if (userInput.matches("[1-3]")) {
            while (userInput.matches("[1-3]")) {
                switch (Integer.parseInt(userInput)) {
                    case 1:
                        System.out.println(ENTER_WRITER.getMessage());
                        writerController.updateWriterName(writer.getId(), scanner.nextLine());
                        break;
                    case 2:
                        System.out.println("Enter post id:");
                        String stringPostId = scanner.nextLine();
                        if (stringPostId.matches("\\d+")) {
                            int postId = Integer.parseInt(stringPostId);
                            List<Post> posts = writer.getPosts();
                            List<Integer> idOfPosts = posts.stream().map(Post::getId).collect(Collectors.toList());
                            if (idOfPosts.contains(postId)) {
                                writer.getPosts().forEach(post -> {
                                    if (post.getId().equals(postId)) {
                                        Post updatedPost = postView.updatePostByPost(post);
                                        writerController.updateWriterPost(writer.getId(), updatedPost);
                                    }
                                });
                            } else {
                                System.out.println("Writer doesn't have post with this id.");
                            }
                        }
                        break;
                    case 3:
                        Post newPost = postView.createPost();
                        List<Post> posts = writer.getPosts();
                        posts.add(newPost);
                        writerController.updateAllWriterPosts(writer.getId(), posts);
                        break;
                }
                System.out.println(UPDATE_WRITER_SELECTION_2.getMessage());
                userInput = scanner.nextLine();
            }
            System.out.println(DONE.getMessage());
        }
    }

    public void deleteWriterById() {
        Writer writer = getWriter();
        if (writer != null) {
            deleteWriter(writer);
        } else {
            System.out.println("Back to Writer menu.");
        }
    }

    public void deleteWriter(Writer writer) {
        System.out.println(CONFIRMATION_1.getMessage());
        String userChoice = scanner.nextLine();
        if (userChoice.equals("1")) {
            System.out.printf("Writer %s successfully deleted!\n", writer.getName());
            writerController.deleteWriter(writer.getId());
        }
    }

    public void getAllWriters() {
        List<Writer> writers = writerController.getAllWriters();
        allWritersToString(writers);
    }

    public List<Post> getAllWriterPosts(Writer writer) {
        return writer.getPosts();
    }

    private void allWritersToString(List<Writer> writers) {
        System.out.printf("\n%d writers available:\n", writers.size());
        for (Writer writer : writers) {
            writerToString(writer);
        }
    }

    private void writerToString(Writer writer) {
        System.out.printf("id: %-3d\tname: %-8s\n", writer.getId(), writer.getName());
        List<Post> posts = writer.getPosts();
        for (Post post : posts) {
            System.out.printf("        post -> id: %-3d status: %-7s content: \"%s\"\n",
                    post.getId(), post.getStatus(), post.getContent());
            List<Tag> tags = post.getTags();
            for (Tag tag : tags) {
                System.out.printf("\t\t\ttag id: %-3d name: %s\n", tag.getId(), tag.getName());
            }
        }
    }

    private Integer idFromUser() {
        System.out.println(WRITER_ID.getMessage());
        String stringID = scanner.nextLine();
        if (!stringID.matches("\\d+")) {
            System.out.println(INCORRECT.getMessage());
            return 0;
        }
        int intID = Integer.parseInt(stringID);
        if (writerController.getWriter(intID) == null) {
            System.out.printf("Writer number №%s doesn't exist\n", intID);
            return 0;
        } else return intID;
    }

    private boolean answerFromUser() {
        String userInput = scanner.nextLine();
        return userInput.equals("1");
    }
}
