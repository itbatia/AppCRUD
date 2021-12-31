package com.itbatia.appCRUD;

import com.itbatia.appCRUD.controller.TagController;
import com.itbatia.appCRUD.model.Tag;

import java.util.Scanner;

public class TagView {
    private final TagController tagController = new TagController();
    private final Scanner scanner = new Scanner(System.in);

    public void mainMenu() {
        createTag();
    }

    public void createTag() {
        System.out.println("Enter new name:");
        String name = scanner.nextLine();
        Tag createdTag = tagController.createTag(name);
        System.out.println("Created tag: " + createdTag);
    }

    public void getTag() {
        System.out.println("Enter name of Tag:");
        String name = scanner.nextLine();



    }
}
