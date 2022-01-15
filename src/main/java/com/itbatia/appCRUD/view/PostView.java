package com.itbatia.appCRUD.view;

import com.itbatia.appCRUD.controller.PostController;
import com.itbatia.appCRUD.model.*;

import static com.itbatia.appCRUD.utils.Messages.*;

import java.util.*;

public class PostView {
    private final PostController postController = new PostController();
    private final TagView tagView = new TagView();
    private final Scanner scanner = new Scanner(System.in);

    public Post createPost() {
        System.out.println(ENTER_POST.getMessage());
        String postContent = scanner.nextLine();
        System.out.println("The post is created. Add tags to it?\n1 - YES\n2 - NO");
        List<Tag> tags = new ArrayList<>();
        if (yesFromUser()) {
            tags = createPostTags();
        }
        Post createdPost = postController.createPost(postContent, tags, PostStatus.ACTIVE);
        System.out.println("Created post:");
        postToString(createdPost);
        return createdPost;
    }

    public List<Tag> createPostTags() {
        List<Tag> tags = new ArrayList<>();
        String userInput;
        do {
            tags.add(tagView.createTag());
            System.out.println("Add another tag?\n1 - YES\n2 - NO");
            userInput = scanner.nextLine();
        } while (userInput.equals("1"));
        System.out.println("Tags added!");
        return tags;
    }

    public Post getPost() {
        int id = idFromUser();
        if (id != 0) {
            System.out.println("Found Post:");
            postToString(postController.getPost(id));
            return postController.getPost(id);
        } else return null;
    }

    public void updatePostById() {
        int id = idFromUser();
        if (id != 0) {
            System.out.println("Found post:");
            postToString(postController.getPost(id));
            System.out.println(UPDATE_POST_SELECTION_1.getMessage());
            updatePost(id);
        }
    }

    public Post updatePostByPost(Post post) {
        System.out.println(UPDATE_POST_SELECTION_1.getMessage());
        return updatePost(post.getId());
    }

    private Post updatePost(Integer id) {
        String userInput = scanner.nextLine();
        if (userInput.matches("[1-5]")) {
            while (userInput.matches("[1-5]")) {
                switch (Integer.parseInt(userInput)) {
                    case 1:
                        System.out.println(ENTER_POST.getMessage());
                        postController.updatePostContent(id, scanner.nextLine());
                        System.out.println("Content is changed!");
                        break;
                    case 2:
                        postController.addTagToPost(id, tagView.createTag());
                        break;
                    case 3:
                        System.out.println("Enter tag id:");
                        String tagId = scanner.nextLine();
                        if (tagId.matches("\\d+")) {
                            boolean result = postController.deleteTagFromPost(id, Integer.parseInt(tagId));
                            if (result) {
                                System.out.println("Tag deleted!");
                            } else {
                                System.out.println("No such tag exists.");
                            }
                        }
                        break;
                    case 4:
                        postController.replacePostTags(id, createPostTags());
                        System.out.println("Done!");
                        break;
                    case 5:
                        System.out.println("Make your choice:\n1 - ACTIVE\n2 - DELETED");
                        userInput = scanner.nextLine();
                        if (userInput.equals("1")) {
                            postController.updatePostStatus(id, PostStatus.ACTIVE);
                            System.out.println("Done!");
                        } else if (userInput.equals("2")) {
                            postController.updatePostStatus(id, PostStatus.DELETED);
                            System.out.println("Done!");
                        } else {
                            System.out.println(INCORRECT_DATA.getMessage());
                        }
                        break;
                }
                System.out.println(UPDATE_POST_SELECTION_2.getMessage());
                userInput = scanner.nextLine();
            }
            System.out.println(DONE.getMessage());
        } else System.out.println(INCORRECT_DATA.getMessage());
        return postController.getPost(id);
    }

    public void deletePostById() {
        int id = idFromUser();
        if (id != 0) {
            System.out.println("Found post. " + CONFIRMATION_1.getMessage());
            deletePost(id);
        }
    }

    public void deletePostByPost(Post post) {
        System.out.println(CONFIRMATION_2.getMessage());
        deletePost(post.getId());
    }

    private void deletePost(Integer id) {
        String userChoice = scanner.nextLine();
        if (userChoice.equals("1")) {
            postController.deletePost(id);
            System.out.println("Post successfully deleted!");
        } else {
            System.out.println("Back to Post menu.");
        }
    }

    public List<Post> getAllPosts() {
        List<Post> posts = postController.getAllPosts();
        allPostsToString(posts);
        return posts;
    }

    public void allPostsToString(List<Post> posts) {
        System.out.printf("\n%d posts available:\n", posts.size());
        for (Post post : posts) {
            System.out.printf("Post id: %-2d status: %-7s content: \"%s\"\n", post.getId(), post.getStatus(), post.getContent());
            List<Tag> tags = post.getTags();
            for (Tag tag : tags) {
                System.out.printf("\ttag: id-%-3d name-%s\n", tag.getId(), tag.getName());
            }
        }
    }

    public void postToString(Post post) {
        System.out.printf("\tid\t\t: %d\n\tstatus\t: %s\n\tcontent : \"%s\"\n", post.getId(), post.getStatus(), post.getContent());
        List<Tag> tags = post.getTags();
        for (Tag tag : tags) {
            System.out.printf("\ttag\t\t: id-%-3d name-%s\n", tag.getId(), tag.getName());
        }
    }

    private Integer idFromUser() {
        System.out.println("Enter post ID:");
        String stringID = scanner.nextLine();
        if (!stringID.matches("\\d+")) {
            System.out.println(INCORRECT_DATA.getMessage());
            return 0;
        }
        int intID = Integer.parseInt(stringID);
        if (postController.getPost(intID) == null) {
            System.out.printf("Post number №%s doesn't exist\n", intID);
            return 0;
        } else return intID;
    }

    private boolean yesFromUser() {
        String userInput = scanner.nextLine();
        return userInput.equals("1");
    }
}
