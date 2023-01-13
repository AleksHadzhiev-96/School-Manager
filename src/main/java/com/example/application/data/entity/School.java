package com.example.application.data.entity;

import org.hibernate.annotations.Formula;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Entity
public class School extends AbstractEntity {
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "school")
    @Nullable
    private List<Teacher> employees = new LinkedList<>();

    @Formula("(select count(c.id) from Teacher c where c.school_id = id)")
    private int employeeCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Teacher> employees) {
        this.employees = employees;
    }

    public int getEmployeeCount(){
        return employeeCount;
    }
}
