package com.ssvv;

import com.ssvv.domain.Nota;
import com.ssvv.domain.Student;
import com.ssvv.domain.Tema;
import com.ssvv.repository.NotaXMLRepo;
import com.ssvv.repository.StudentXMLRepo;
import com.ssvv.repository.TemaXMLRepo;
import com.ssvv.service.Service;
import com.ssvv.validation.NotaValidator;
import com.ssvv.validation.StudentValidator;
import com.ssvv.validation.TemaValidator;
import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAddGrade {
    private StudentXMLRepo studentFileRepository;
    private StudentValidator studentValidator;
    private TemaXMLRepo temaFileRepository;
    private TemaValidator temaValidator;
    private NotaXMLRepo gradeFileRepository;
    private NotaValidator gradeValidator;
    private Service service;

    @BeforeEach
    void createXML() {
        File xml_student = new File("fisiere/test_Studenti.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml_student))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        File xml_teme = new File("fisiere/teme_test.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml_teme))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        File xml_grade = new File("fisiere/grade_test.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml_grade))) {
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
        this.temaFileRepository = new TemaXMLRepo("fisiere/teme_test.xml");
        this.temaValidator = new TemaValidator();
        this.gradeFileRepository = new NotaXMLRepo("fisiere/grade_test.xml");
        this.gradeValidator = new NotaValidator(this.studentFileRepository, this.temaFileRepository);
        this.service = new Service(this.studentFileRepository, this.studentValidator, this.temaFileRepository, this.temaValidator, this.gradeFileRepository, this.gradeValidator);
    }

    @AfterEach
    void tearDown() {
        new File("fisiere/test_Studenti.xml").delete();
        new File("fisiere/teme_test.xml").delete();
        new File("fisiere/grade_test.xml").delete();
    }

    @Test
    public void test_add_student_working(){
        Student testing_student = new Student("1", "Name Test", 3, "email@g");
        this.service.addStudent(testing_student);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        Assertions.assertEquals(students.next(), testing_student);
    }

    @Test
    public void test_add_tema_works(){
        Tema testing_tema = new Tema("2", "desc", 2, 1);
        this.service.addTema(testing_tema);
        java.util.Iterator<Tema> students = this.service.getAllTeme().iterator();
        assertEquals(students.next(), testing_tema);
    }

    @Test
    public void test_add_grade(){
        test_add_student_working();
        test_add_tema_works();
        Nota testing_grade = new Nota("3", "1", "2", 7, LocalDate.of(2018, 10, 20));
        this.service.addNota(testing_grade, "Good.");
        java.util.Iterator<Nota> note = this.service.getAllNote().iterator();
        assertEquals(note.next(), testing_grade);
    }

}
