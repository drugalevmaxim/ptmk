package com.drugalevmaxim.model;

import java.time.LocalDate;
import java.time.Period;

public class Employee {

    public enum Gender {
        MALE, FEMALE;

        @Override
        public String toString() {
            return this == MALE ? "Male" : "Female";
        }
    }
    private final String fullName;
    private final LocalDate birthDate;
    private final Gender gender;

    public Employee(String fullName, LocalDate birthDate, Gender gender) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }


}
