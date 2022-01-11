package com.itbatia.appCRUD;

import com.itbatia.appCRUD.model.*;

import java.util.Scanner;

public class MainView {
    private final TagView tagView = new TagView();
    private final PostView postView = new PostView();
    private final WriterView writerView = new WriterView();
    Scanner scanner = new Scanner(System.in);

    public void mainMenu() {
        System.out.println("\nMain menu:\n1 - Tags\n2 - Posts\n3 - Writers\n0 - Exit the program");
        switch (userInput()) {
            case 1:
                tagMenu();
            case 2:
                postMenu();
            case 3:
                writerMenu();
            case 0:
                System.out.println("Exit the program.");
                System.exit(0);
            default:
                System.out.println("Incorrect data.");
                mainMenu();
        }
    }

    private void tagMenu() {
        System.out.println("\nTags menu:\n1 - Create tag\n2 - Get tag by id\n3 - Update tag\n4 - Delete tag" +
                "\n5 - Get all tags\n6 - Back to main menu\n0 - Exit the program");
        switch (userInput()) {
            case 1:
                tagView.createTag();
                tagMenu();
            case 2:
                Tag tag = tagView.getTag();
                if (tag != null) {
                    menuAfterGetTag(tag);
                } else {
                    tagMenu();
                }
            case 3:
                tagView.updateTagById();
                tagMenu();
            case 4:
                tagView.deleteTagById();
                tagMenu();
            case 5:
                tagView.getAllTags();
                tagMenu();
            case 6:
                mainMenu();
            case 0:
                System.out.println("\nExit the program.");
                System.exit(0);
            default:
                System.out.println("Incorrect data.");
                tagMenu();
        }
    }

    private void menuAfterGetTag(Tag tag) {
        System.out.println("What do you want to do with this tag?\n1 - Update tag\n2 - Delete tag\n3 - Nothing. Back to Tag menu");
        switch (userInput()) {
            case 1:
                tagView.updateTagByTag(tag);
                tagMenu();
            case 2:
                tagView.deleteTagByTag(tag);
                tagMenu();
            case 3:
                tagMenu();
            default:
                System.out.println("Incorrect data.");
                menuAfterGetTag(tag);
        }
    }

    private void postMenu() {
        System.out.println("\nPosts Menu:\n1 - Create post\n2 - Get post by id\n3 - Update post\n4 - Delete post" +
                "\n5 - Get all posts\n6 - Back to main menu\n0 - Exit the program");
        switch (userInput()) {
            case 1:
                postView.createPost();
                postMenu();
            case 2:
                Post post = postView.getPost();
                if (post != null) {
                    menuAfterGetPost(post);
                } else postMenu();
            case 3:
                postView.updatePostById();
                postMenu();
            case 4:
                postView.deletePostById();
                postMenu();
            case 5:
                postView.getAllPosts();
                postMenu();
            case 6:
                mainMenu();
            case 0:
                System.out.println("\nExit the program.");
                System.exit(0);
            default:
                System.out.println("Incorrect data.");
                postMenu();
        }
    }

    private void menuAfterGetPost(Post post) {
        System.out.println("What do you want to do with this post?\n1 - Update post\n2 - Delete post\n3 - Nothing. Back to Post menu");
        switch (userInput()) {
            case 1:
                postView.updatePostByPost(post);
                postMenu();
            case 2:
                postView.deletePostByPost(post);
                postMenu();
            case 3:
                postMenu();
            default:
                System.out.println("Incorrect data.");
                menuAfterGetPost(post);
        }
    }

    private void writerMenu() {
        System.out.println("\nWriters menu:\n1 - Create writer\n2 - Get writer by id\n3 - Update writer\n4 - Delete writer" +
                "\n5 - Get all writers\n6 - Back to main menu\n0 - Exit the program");
        switch (userInput()) {
            case 1:
                writerView.createWriter();
                writerMenu();
            case 2:
                Writer writer = writerView.getWriter();
                if (writer != null) {
                    menuAfterGetWriter(writer);
                } else {
                    writerMenu();
                }
            case 3:
                writerView.updateWriterById();
                writerMenu();
            case 4:
                writerView.deleteWriterById();
                writerMenu();
            case 5:
                writerView.getAllWriters();
                writerMenu();
            case 6:
                mainMenu();
            case 0:
                System.out.println("\nExit the program.");
                System.exit(0);
            default:
                System.out.println("Incorrect data.");
                writerMenu();
        }
    }

    private void menuAfterGetWriter(Writer writer) {
        System.out.println("What do you want to do with this writer?\n1 - Update writer\n2 - Delete writer" +
                "\n3 - Get all posts this writer\n4 - Nothing. Back to Writer menu");
        switch (userInput()) {
            case 1:
                writerView.updateWriter(writer);
                writerMenu();
            case 2:
                writerView.deleteWriter(writer);
                writerMenu();
            case 3:
                postView.allPostsToString(writerView.getAllWriterPosts(writer));
                writerMenu();
            case 4:
                writerMenu();
            default:
                System.out.println("Incorrect data.");
                menuAfterGetWriter(writer);
        }
    }

    private Integer userInput() {
        System.out.println("-----------------\nMake your choice:");
        String stringUserInput = scanner.nextLine();
        if (!stringUserInput.matches("\\d+")) {
            System.out.println("Incorrect data. Enter number:");
            stringUserInput = scanner.nextLine();
            if (!stringUserInput.matches("\\d+")) {
                System.out.println("Incorrect data.\nExit!");
                System.exit(0);
                return 0;
            } else return Integer.parseInt(stringUserInput);
        } else return Integer.parseInt(stringUserInput);
    }
}
