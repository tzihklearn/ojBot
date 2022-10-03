package com.example.ojbot.common;

import java.util.HashSet;
import java.util.Set;

public enum StudentReum {

    Cat(new HashSet<>());

    private Set<String> students;

    private StudentReum(Set<String> students) {
        this.students = students;
    }

}
