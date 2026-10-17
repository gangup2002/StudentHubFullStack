
package com.example.student_manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("student", new Student());
        return "students";
    }

    @PostMapping("/students")
    public String saveStudent(
            @ModelAttribute("student") Student student,
            RedirectAttributes redirectAttributes) {

        studentRepository.save(student);
        redirectAttributes.addFlashAttribute(
                "message", "Student saved successfully!");

        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid student ID: " + id));

        model.addAttribute("student", student);
        model.addAttribute("students", studentRepository.findAll());

        return "students";
    }

    @PostMapping("/students/delete/{id}")
    public String deleteStudent(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        studentRepository.deleteById(id);
        redirectAttributes.addFlashAttribute(
                "message", "Student deleted successfully!");

        return "redirect:/students";
    }
}
