package com.example.studentv1.controller;

import com.example.studentv1.model.Student;
import com.example.studentv1.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public class StudRESTController
{
    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/stud1/{name}")
    public Student getstud1byname(@PathVariable String name)
    {
        return new Student(name);
    }

    @GetMapping("/stud2{name}")
    public Student getstud2byname(@PathVariable Optional<String> name)
    {
        if (name.isPresent())
        {
            return new Student(name.get());
        }
        else
        {
            return new Student("");
        }
    }

    @GetMapping("/stud3{name}")
    public Student getstud3byname(@PathVariable String name)
    {
        return studentRepository.findByName(name).orElse(new Student(name + " not found"));
    }

   /* @GetMapping("/stud4{name}")
    public Student getstud4byname(@PathVariable String name)
    {
        return studentRepository.findByName(name).orElseThrow(() -> new RuntimeException("student not found")); //indholdet af orELseThrow er en lambda metode altså en metode uden navn (anonym metode)
    }*/

    @GetMapping("/stud4{name}")
    public Student getstud4byname(@PathVariable String name)
    {
        return studentRepository.findByName(name).orElseThrow(() -> new StudentNotFoundException(name)); //bruger en costum lavet runtime exception
    }

    @GetMapping("/stud5{name}")
    public ResponseEntity<Student> getstud5byname(@PathVariable String name)
    {
        var stud = studentRepository.findByName(name);
        if(stud.isPresent())
        {
            return new ResponseEntity<>(stud.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    public Student postStudent(@RequestBody Student student)
    {
        System.out.println(student);
        return studentRepository.save(student);
    }

}