package com.itbatia.appCRUD;

import com.itbatia.appCRUD.controller.TagController;
import com.itbatia.appCRUD.model.Tag;

import java.util.Comparator;
import java.util.List;
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
        System.out.println("Created tag: " + createdTag.toString());
    }

    public void getTag() {
        int id = idFromUser();
        if (id != 0) {
            System.out.println("Found the tag with name \"" + tagController.getTag(id).getName() + "\"");

        }
    }

    public void updateTag() {
        int id = idFromUser();
        if (id != 0) {
            System.out.println("Enter new name of Tag:");
            String newName = scanner.nextLine();
            tagController.updateTag(id, newName);
            System.out.println("Name of tag number " + id + " changed to \"" + newName + "\"");
        }
    }

    public void deleteTag() {
        int id = idFromUser();
        if (id != 0) {
            System.out.println("Tag with name \"" + tagController.getTag(id).getName() + "\" deleted successfully");
            tagController.deleteTag(id);
        }
    }

    private Integer countTagsInFile() {
        List<Tag> currentTagList = tagController.getAllTags();
        Tag tagWithMaxID = currentTagList.stream().max(Comparator.comparingInt(Tag::getId)).orElse(null);
        if (tagWithMaxID == null) {
            System.out.println("No tags available");
            return 0;
        } else return tagWithMaxID.getId();
    }

    private Integer idFromUser() {
        int countTags = countTagsInFile();
        if (countTags > 0) {
            System.out.println("Enter ID of Tag:");
            String stringID = scanner.nextLine();
            while (!stringID.matches("\\d+")) {
                System.out.println(countTags + " tags are available. Insert the number:");
                stringID = scanner.nextLine();
            }
            int intID = Integer.parseInt(stringID);
            if (intID <= 0 || intID > countTags) {
                System.out.println("Tag number " + intID + " does not exist");
                return 0;
            } else return intID;
        } else return 0;
    }
}
