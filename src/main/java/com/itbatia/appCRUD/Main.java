package com.itbatia.appCRUD;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        TagRepository tagRep = new TagRepository();

        Tag tag1 = new Tag(10, "test10");
        Tag tag2 = new Tag(20, "test20");

//        tagRep.save(tag1);

        System.out.println(tagRep.getAll());
//        tagRep.save(tag1);
//        System.out.println("------------------------------");
        System.out.println(tagRep.getAll());
        System.out.println("------------------------------");
//        System.out.println(tagRep.getById(2));
        tagRep.deleteById(10);
    }
}

/*
[
  {
    "id": 1,
    "name": "TEST"
  },
  {
    "id": 2,
    "name": "TEST2"
  }
]
 */