package org.example.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleList {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        //Adding names to collection
        Collections.addAll(names,
                "Kacper", "Karol", "Wojtek");
        //Checking collection size
        System.out.println("Collection size: " + names.size());
        //print names from collection
        for(String name : names){
            System.out.println(name);
        }
    }
}
