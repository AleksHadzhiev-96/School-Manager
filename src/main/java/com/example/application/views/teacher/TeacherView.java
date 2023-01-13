package com.example.application.views.teacher;



import com.example.application.data.entity.Teacher;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;


@Component
@Scope("prototype")
@Route(value="", layout = MainLayout.class)
@PageTitle("Teachers | Vaadin CRM")
@PermitAll
public class TeacherView extends VerticalLayout {
    Grid<Teacher> grid = new Grid<>(Teacher.class);
    TextField filterText = new TextField();
    TeacherForm form;
    CrmService service;

    public TeacherView(CrmService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new TeacherForm(service.findAllSchools(), service.findAllTypes());
        form.setWidth("25em");
        form.addListener(TeacherForm.SaveEvent.class, this::saveTeacher);
        form.addListener(TeacherForm.DeleteEvent.class, this::deleteTeacher);
        form.addListener(TeacherForm.CloseEvent.class, e -> closeEditor());

        FlexLayout content = new FlexLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setFlexShrink(0, form);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
        grid.asSingleSelect().addValueChangeListener(event ->
            editTeacher(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("teacher-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");
        grid.addColumn(teacher -> teacher.getType().getName()).setHeader("Type");
        grid.addColumn(teacher -> teacher.getSchool().getName()).setHeader("School");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addTeacherButton = new Button("Add teacher");
        addTeacherButton.addClickListener(click -> addTeacher());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addTeacherButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveTeacher(TeacherForm.SaveEvent event) {
        service.saveTeacher(event.getTeacher());
        updateList();
        closeEditor();
    }

    private void deleteTeacher(TeacherForm.DeleteEvent event) {
        service.deleteTeacher(event.getTeacher());
        updateList();
        closeEditor();
    }

    public void editTeacher(Teacher teacher) {
        if (teacher == null) {
            closeEditor();
        } else {
            form.setTeacher(teacher);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addTeacher() {
        grid.asSingleSelect().clear();
        editTeacher(new Teacher());
    }

    private void closeEditor() {
        form.setTeacher(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllTeachers(filterText.getValue()));
    }


}
