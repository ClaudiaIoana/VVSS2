package com.ssvv;

import com.ssvv.domain.Tema;
import com.ssvv.repository.TemaXMLRepo;
import com.ssvv.service.Service;
import com.ssvv.validation.TemaValidator;
import com.ssvv.validation.ValidationException;
import junit.framework.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAddTema {
    private TemaXMLRepo temaFileRepository;
    private TemaValidator temaValidator;
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/teme_test.xml");
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
    void setUp() {
        this.temaFileRepository = new TemaXMLRepo("fisiere/teme_test.xml");
        this.temaValidator = new TemaValidator();
        this.service = new Service(null, null, this.temaFileRepository, this.temaValidator, null, null);
    }

    @AfterEach
    void tearDown() {
        new File("fisiere/teme_test.xml").delete();
    }

    @Test
    void test_add_tema_with_empty_id() {
        Tema testing_tema = new Tema("1", "desc", 10, 9);
        Tema testing_tema2 = new Tema("", "desc", 10, 9);
        this.service.addTema(testing_tema);
        java.util.Iterator<Tema> students = this.service.getAllTeme().iterator();
        assertEquals(students.next(), testing_tema);
        try{
            this.service.addTema(testing_tema2);
        } catch (ValidationException e){
            Assert.assertEquals(e.toString(), new ValidationException("Numar tema invalid!").toString());
        }
    }

    @Test
    void test_add_tema_with_null_id() {
        Tema testing_tema = new Tema("1", "desc", 10, 9);
        Tema testing_tema2 = new Tema(new String(), "desc", 10, 9);
        this.service.addTema(testing_tema);
        java.util.Iterator<Tema> students = this.service.getAllTeme().iterator();
        assertEquals(students.next(), testing_tema);
        try{
            this.service.addTema(testing_tema2);
        } catch (ValidationException e){
            Assert.assertEquals(e.toString(), new ValidationException("Numar tema invalid!").toString());
        }
    }

    @Test
    void test_add_tema_with_null_desc() {
        Tema testing_tema = new Tema("1", "desc", 10, 9);
        Tema testing_tema2 = new Tema("2", "", 10, 9);
        this.service.addTema(testing_tema);
        java.util.Iterator<Tema> students = this.service.getAllTeme().iterator();
        assertEquals(students.next(), testing_tema);
        try{
            this.service.addTema(testing_tema2);
        } catch (ValidationException e){
            Assert.assertEquals(e.toString(), new ValidationException("Descriere invalida!").toString());
        }
    }

    @Test
    void test_add_tema_with_deadlin_over_limit() {
        Tema testing_tema = new Tema("1", "desc", 10, 9);
        Tema testing_tema2 = new Tema("2", "desc", 15, 9);
        this.service.addTema(testing_tema);
        java.util.Iterator<Tema> students = this.service.getAllTeme().iterator();
        assertEquals(students.next(), testing_tema);
        try{
            this.service.addTema(testing_tema2);
        } catch (ValidationException e){
            Assert.assertEquals(e.toString(), new ValidationException("Deadlineul trebuie sa fie intre 1-14.").toString());
        }
    }

    @Test
    void test_add_tema_deadline_under_limit() {
        Tema testing_tema = new Tema("1", "desc", 10, 9);
        Tema testing_tema2 = new Tema("2", "desc", 0, 9);
        this.service.addTema(testing_tema);
        java.util.Iterator<Tema> students = this.service.getAllTeme().iterator();
        assertEquals(students.next(), testing_tema);
        try{
            this.service.addTema(testing_tema2);
        } catch (ValidationException e){
            Assert.assertEquals(e.toString(), new ValidationException("Deadlineul trebuie sa fie intre 1-14.").toString());
        }
    }

    @Test
    void test_add_tema_with_primire_over_limit() {
        Tema testing_tema = new Tema("1", "desc", 10, 9);
        Tema testing_tema2 = new Tema("2", "desc", 10, 15);
        this.service.addTema(testing_tema);
        java.util.Iterator<Tema> students = this.service.getAllTeme().iterator();
        assertEquals(students.next(), testing_tema);
        try{
            this.service.addTema(testing_tema2);
        } catch (ValidationException e){
            Assert.assertEquals(e.toString(), new ValidationException("Saptamana primirii trebuie sa fie intre 1-14.").toString());
        }
    }

    @Test
    void test_add_tema_with_primire_under_limit() {
        Tema testing_tema = new Tema("1", "desc", 10, 9);
        Tema testing_tema2 = new Tema("2", "desc", 10, 0);
        this.service.addTema(testing_tema);
        java.util.Iterator<Tema> students = this.service.getAllTeme().iterator();
        assertEquals(students.next(), testing_tema);
        try{
            this.service.addTema(testing_tema2);
        } catch (ValidationException e){
            Assert.assertEquals(e.toString(), new ValidationException("Saptamana primirii trebuie sa fie intre 1-14.").toString());
        }
    }

    @Test
    void test_add_tema_all_good() {
        Tema testing_tema = new Tema("1", "desc", 10, 9);
        Tema testing_tema2 = new Tema("2", "desc", 11, 8);
        this.service.addTema(testing_tema);
        java.util.Iterator<Tema> students = this.service.getAllTeme().iterator();
        assertEquals(students.next(), testing_tema);
        this.service.deleteTema("1");
        this.service.addTema(testing_tema2);
        students = this.service.getAllTeme().iterator();
        assertEquals(students.next(), testing_tema2);
    }

}
