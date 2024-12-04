package org.example.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImmutableExample {
    public static void main(String[] args) {
        List<String> mutableList = new ArrayList<>();
        mutableList.add("apple");
        mutableList.add("banana");
        mutableList.add("cherry");

        List<String> immutableList = Collections.unmodifiableList(mutableList);

        //It will throw UnsupportedOperationException, because we try to modify unmodifiable collection
        immutableList.add("date");
    }
}
