package org.example.dataloom.messaging.dto;

import lombok.Getter;

@Getter
public enum QualityGrade {
    A_PLUS("A+"),
    A("A"),
    B_PLUS("B+"),
    B("B"),
    C_PLUS("C+"),
    C("C"),
    D("D");

    private final String grade;

    QualityGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return this.grade;
    }
}
