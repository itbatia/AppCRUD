package com.itbatia.appCRUD;

import com.itbatia.appCRUD.controller.TagController;
import com.itbatia.appCRUD.model.Tag;

import java.util.List;
import java.util.Scanner;

public class TagView {
    private final TagController tagController = new TagController();
    private final Scanner scanner = new Scanner(System.in);

    public Tag createTag() {
        System.out.println("Enter new Tag name:");
        String name = scanner.nextLine();
        Tag createdTag = tagController.createTag(name);
        System.out.println("Created: " + createdTag.toString());
        return createdTag;
    }

    public Tag getTag() {
        int id = idFromUser();
        if (id != 0) {
            System.out.printf("Found the Tag with name \"%s\". ", tagController.getTag(id).getName());
            return tagController.getTag(id);
        } else return null;
    }

    public void updateTagById() {
        int id = idFromUser();
        if (id != 0) {
            System.out.println("Found the tag with name: \"" + tagController.getTag(id).getName() + "\". Enter a new tag name:");
            String newName = scanner.nextLine();
            tagController.updateTag(id, newName);
            System.out.printf("Tag %s updated to \"%s\"\n", id, newName);
        }
    }

    public void updateTagByTag(Tag tag) {
        if (tag != null) {
            System.out.println("Enter a new Tag name:");
            String newName = scanner.nextLine();
            tagController.updateTag(tag.getId(), newName);
            System.out.printf("Tag %s updated to \"%s\"\n", tag.getId(), newName);
        }
    }

    public void deleteTagById() {
        int id = idFromUser();
        if (id != 0) {
            System.out.println("Found tag with name: \"" + tagController.getTag(id).getName()+ "\".");
            System.out.println("Do you confirm the deletion?\n1 - Yes\n2 - No");
            String userChoice = scanner.nextLine();
            if (userChoice.equals("1")) {
                System.out.println("Tag deleted successfully!\n");
                tagController.deleteTag(id);
            } else {
                System.out.println("Back to Tag menu.");
            }
        }
    }

    public void deleteTagByTag(Tag tag) {
        if (tag != null) {
            System.out.println("Are you sure?\n1 - Yes\n2 - No");
            String userChoice = scanner.nextLine();
            if (userChoice.equals("1")) {
                System.out.println("Tag deleted successfully!");
                tagController.deleteTag(tag.getId());
            } else {
                System.out.println("Back to Tag menu.");
            }
        }
    }

    public List<Tag> getAllTags() {
        List<Tag> tags = tagController.getAllTags();
        tagsToString(tags);
        return tags;
    }

    private void tagsToString(List<Tag> tags) {
        System.out.printf("\n%d tags available:\n", tags.size());
        for (Tag tag : tags) {
            System.out.printf("id: %-3d\t name: %s\n", tag.getId(), tag.getName());
        }
    }

    private Integer idFromUser() {
        System.out.println("Enter Tag ID:");
        String stringID = scanner.nextLine();
        if (!stringID.matches("\\d+")) {
            System.out.println("Incorrect data.");
            return 0;
        }
        int intID = Integer.parseInt(stringID);
        if (tagController.getTag(intID) == null) {
            System.out.printf("Tag number №%s doesn't exist.\n", intID);
            return 0;
        } else return intID;
    }
}