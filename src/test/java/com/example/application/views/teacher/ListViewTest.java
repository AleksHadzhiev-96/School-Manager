package com.example.application.views.teacher;



import com.example.application.data.entity.Teacher;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

    @Autowired
    private TeacherView listView;

    @Test
    public void formShownWhenDoctorSelected() {
        Grid<Teacher> grid = listView.grid;
        Teacher firstTeacher = getFirstItem(grid);

        TeacherForm form = listView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstTeacher);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstTeacher.getFirstName(), form.firstName.getValue());
    }
    private Teacher getFirstItem(Grid<Teacher> grid) {
        return( (ListDataProvider<Teacher>) grid.getDataProvider()).getItems().iterator().next();
    }
}