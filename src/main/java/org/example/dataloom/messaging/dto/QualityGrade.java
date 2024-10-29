package org.example.dataloom.messaging.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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

//    @JsonValue
//    public String getValue() {
//        return grade;
//    }
//
//    @JsonCreator
//    public static QualityGrade fromValue(String value) {
//        for (QualityGrade grade : QualityGrade.values()) {
//            if (grade.grade.equals(value)) {
//                return grade;
//            }
//        }
//        throw new IllegalArgumentException("Unknown enum value: " + value);
//    }

//    @Override
//    public String toString() {
//        return this.grade;
//    }
}
