package com.ssvv;

import com.ssvv.domain.Student;
import com.ssvv.repository.StudentXMLRepo;
import com.ssvv.service.Service;
import com.ssvv.validation.StudentValidator;
import com.ssvv.validation.ValidationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import org.junit.jupiter.api.*;

public class TestAddStudent {
    private StudentXMLRepo studentFileRepository;
    private StudentValidator studentValidator;
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/test_Studenti.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp(){
        this.studentFileRepository = new StudentXMLRepo("fisiere/test_Studenti.xml");
        this.studentValidator = new StudentValidator();
        this.service = new Service(this.studentFileRepository, this.studentValidator, null, null, null, null);
    }

    @AfterEach
    void tearDown() {
        new File("fisiere/studentiTest.xml").delete();
    }

    @Test
    public void test_add_student_id_not_null() {
        Student testing_student = new Student("1", "Name Test", 3, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_student_id_empty_string_lower_boundery() {
        Student testing_student = new Student("", "Name Test", 3, "email");
        try{
            this.service.addStudent(testing_student);
        } catch (ValidationException e){
            assertEquals(e.toString(), new ValidationException("Id incorect!").toString());
        }
    }

    @Test
    public void test_add_student_id_small_number_lower_boundary() {
        Student testing_student = new Student("1", "Name Test", 3, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_student_id_bigger_number_lower_boundary() {
        Student testing_student = new Student("12", "Name Test", 3, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_student_id_smaller_number_upper_boundary() {
        Student testing_student = new Student("9999", "Name Test", 3, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_student_id_big_number_upper_boundary() {
        Student testing_student = new Student("10000", "Name Test", 3, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_student_id_bigger_number_upper_boundary() {
        Student testing_student = new Student("10001", "Name Test", 3, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }


    @Test
    public void test_add_student_group_number(){
        Student testing_student = new Student("df", "Name Test", 3, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_student_group_negative_number_lower_boundary(){
        Student testing_student = new Student("dg", "Name Test", -1, "email");
        try{
            this.service.addStudent(testing_student);
        } catch (ValidationException e){
            assertEquals(e.toString(), new ValidationException("Grupa incorecta!").toString());
        }
    }

    @Test
    public void test_add_student_group_positive_number_lower_boundary(){
        Student testing_student = new Student("dg", "Name Test", 0, "email");
        try{
            service.addStudent(testing_student);
        } catch (ValidationException e){
            assertEquals(e.toString(), new ValidationException("Grupa incorecta!").toString());
        }
    }

    @Test
    public void test_add_student_group_bigger_number_lower_boundary(){
        Student testing_student = new Student("df", "Name Test", 2, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_student_group_lower_upper_boundary(){
        Student testing_student = new Student("df", "Name Test", 99, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_student_group_equal_upper_boundary(){
        Student testing_student = new Student("df", "Name Test", 100, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_student_group_bigger_upper_boundary(){
        Student testing_student = new Student("df", "Name Test", 101, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_student_name_with_special_ch(){
        Student testing_student = new Student("1", "Name_Test#6", 100, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_student_name_with_empty_string(){
        Student testing_student = new Student("1", "", 100, "email");
        try{
            service.addStudent(testing_student);
        } catch (ValidationException e){
            assertEquals(e.toString(), new ValidationException("Nume incorect!").toString());
        }
    }

    @Test
    public void test_add_student_name_with_null(){
        Student testing_student = new Student("1", null, 100, "email");
        try{
            service.addStudent(testing_student);
        } catch (ValidationException e){
            assertEquals(e.toString(), new ValidationException("Nume incorect!").toString());
        }
    }

    @Test
    public void test_add_student_email_with_arond(){
        Student testing_student = new Student("1", "Name_Test#6", 100, "email@gmail.com");
        Student response = service.addStudent(testing_student);
        assertEquals(testing_student, response);
    }

    @Test
    public void test_add_student_email_with_empty_string(){
        Student testing_student = new Student("1", "ds", 100, "");
        try{
            service.addStudent(testing_student);
        } catch (ValidationException e){
            assertEquals(e.toString(), new ValidationException("Email incorect!").toString());
        }
    }

    @Test
    public void test_add_student_email_with_null(){
        Student testing_student = new Student("1", "fsj", 100, null);
        try{
            service.addStudent(testing_student);
        } catch (ValidationException e){
            assertEquals(e.toString(), new ValidationException("Email incorect!").toString());
        }
    }

    @Test
    public void test_add_student_id_duplicate() {
        Student testing_student = new Student("11", "Name Test1", 3, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
        Student testing_student2 = new Student("11", "Name Test", 3, "email");

        try{
            this.service.addStudent(testing_student2);
        } catch (ValidationException e){
            assertEquals(e.toString(), new ValidationException("Id incorect!").toString());
        }
    }

    @Test
    public void test_add_student_id_float_number() {
        Student testing_student = new Student("11.45", "Name Test1", 3, "email");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

}