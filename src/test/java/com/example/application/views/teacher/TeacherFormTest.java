package com.example.application.views.teacher;




import com.example.application.data.entity.School;
import com.example.application.data.entity.Teacher;
import com.example.application.data.entity.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TeacherFormTest {
    private List<School> schools;
    private List<Type> types;
    private Teacher marcUsher;
    private School school1;
    private School school2;
    private Type type1;
    private Type type2;

    @Before
    public void setupData() {
        schools = new ArrayList<>();
        school1 = new School();
        school1.setName("Vaadin Ltd");
        school2 = new School();
        school2.setName("IT Mill");
        schools.add(school1);
        schools.add(school2);

        types = new ArrayList<>();
        type1 = new Type();
        type1.setName("Type 1");
        type2 = new Type();
        type2.setName("Type 2");
        types.add(type1);
        types.add(type2);

        marcUsher = new Teacher();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");
        marcUsher.setEmail("marc@usher.com");
        marcUsher.setType(type1);
        marcUsher.setSchool(school2);
    }

    @Test
    public void formFieldsPopulated() {
        TeacherForm form = new TeacherForm(schools, types);
        form.setTeacher(marcUsher);
        Assert.assertEquals("Marc", form.firstName.getValue());
        Assert.assertEquals("Usher", form.lastName.getValue());
        Assert.assertEquals("marc@usher.com", form.email.getValue());
        Assert.assertEquals(school2, form.school.getValue());
        Assert.assertEquals(type1, form.type.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        TeacherForm form = new TeacherForm(schools, types);
        Teacher teacher = new Teacher();
        form.setTeacher(teacher);
        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        form.school.setValue(school1);
        form.email.setValue("john@doe.com");
        form.type.setValue(type2);

        AtomicReference<Teacher> savedTeacherRef = new AtomicReference<>(null);
        form.addListener(TeacherForm.SaveEvent.class, e -> {
            savedTeacherRef.set(e.getTeacher());
        });
        form.save.click();
        Teacher savedTeacher = savedTeacherRef.get();

        Assert.assertEquals("John", savedTeacher.getFirstName());
        Assert.assertEquals("Doe", savedTeacher.getLastName());
        Assert.assertEquals("john@doe.com", savedTeacher.getEmail());
        Assert.assertEquals(school1, savedTeacher.getSchool());
        Assert.assertEquals(type2, savedTeacher.getType());
    }
}