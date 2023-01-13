package com.example.application.views.teacher;




import com.example.application.data.entity.School;

import com.example.application.data.entity.Teacher;
import com.example.application.data.entity.Type;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class TeacherForm extends FormLayout {
  private Teacher teacher;

  TextField firstName = new TextField("First name");
  TextField lastName = new TextField("Last name");
  EmailField email = new EmailField("Email");
  ComboBox<Type> type = new ComboBox<>("Type");
  ComboBox<School> school = new ComboBox<>("School");
  Binder<Teacher> binder = new BeanValidationBinder<>(Teacher.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public TeacherForm(List<School> schools, List<Type> types) {
    addClassName("teacher-form");
    binder.bindInstanceFields(this);

    school.setItems(schools);
    school.setItemLabelGenerator(School::getName);
    type.setItems(types);
    type.setItemLabelGenerator(Type::getName);
    add(firstName,
        lastName,
        email,
        school,
        type,
        createButtonsLayout()); 
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, teacher)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));


    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

 // private void fireEvent(CloseEvent closeEvent) {
 // }

 // private void fireEvent(DeleteEvent deleteEvent) {
 // }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
    binder.readBean(teacher);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(teacher);
      fireEvent(new SaveEvent(this, teacher));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

 // private void fireEvent(SaveEvent saveEvent) {
  //}

  // Events
  public static abstract class TeacherFormEvent extends ComponentEvent<TeacherForm> {
    private Teacher teacher;

    protected TeacherFormEvent(TeacherForm source, Teacher teacher) {
      super(source, false);
      this.teacher = teacher;
    }

    public Teacher getTeacher() {
      return teacher;
    }
  }

  public static class SaveEvent extends TeacherFormEvent {
    SaveEvent(TeacherForm source, Teacher teacher) {
      super(source, teacher);
    }
  }

  public static class DeleteEvent extends TeacherFormEvent {
    DeleteEvent(TeacherForm source, Teacher teacher) {
      super(source, teacher);
    }

  }

  public static class CloseEvent extends TeacherFormEvent {
    CloseEvent(TeacherForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}