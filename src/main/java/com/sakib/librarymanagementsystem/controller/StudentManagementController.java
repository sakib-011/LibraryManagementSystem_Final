package com.sakib.librarymanagementsystem.controller;

import com.sakib.librarymanagementsystem.dto.StudentDto;
import com.sakib.librarymanagementsystem.modal.Student;
import com.sakib.librarymanagementsystem.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentManagementController {

    private final StudentService studentService;

    public StudentManagementController(StudentService studentService){
        this.studentService = studentService;
    }


    @GetMapping("/student_management")
    public String studentManagement(
            @RequestParam(value = "searchQuery" , required = false)  String searchInput ,
            @RequestParam(value = "deleteStudentId" , required = false) Long deleteStudentId ,
            @RequestParam(value = "editStudentId" , required = false)  Long editStudentId,
            @RequestParam(value = "viewStudentId" , required = false) Long viewStudentId,
            Model model , HttpSession httpSession){

        System.out.println(viewStudentId);

        ArrayList<Student> students = studentService.getAllStudent();

        model.addAttribute("student" ,  new StudentDto("" ,"" , "" ,"" , null,null));

            String addStudentSuccess = (String) httpSession.getAttribute("addStudentSuccessFully");
            String addStudentFailed = (String)  httpSession.getAttribute("addStudentFailed");

            if(addStudentSuccess!=null && addStudentSuccess.equals("true")){
                model.addAttribute("alertMessage", "Student add successfully!");
                model.addAttribute("alertType", "success");
                httpSession.removeAttribute("addStudentSuccessFully");
            } else if(addStudentFailed !=null && addStudentFailed.equals("true")){
                model.addAttribute("alertMessage", "Failed to add Student!");
                model.addAttribute("alertType", "error");
                httpSession.removeAttribute("addStudentFailed");
            }


            if(viewStudentId != null){
                model.addAttribute("viewMode" , true);
                Student viewStudent = studentService.getStudent(viewStudentId.intValue());
                model.addAttribute("name" , viewStudent.getFullName());
                model.addAttribute("email" , viewStudent.getEmail());
                model.addAttribute("phoneNumber" , viewStudent.getPhoneNumber());
                model.addAttribute("identityType" , viewStudent.getIdentity());
            }


            if(editStudentId != null){
                Student student = studentService.getStudent(editStudentId.intValue());
                httpSession.setAttribute("editStudentId" , editStudentId);
                model.addAttribute("editStudent"  , student);
                model.addAttribute("editMode" , true);
            }


        String updateStudentSuccess = (String) httpSession.getAttribute("updateStudentSuccessfully");
        String updateStudentFailed = (String)  httpSession.getAttribute("updateStudentFailed");

        if(updateStudentSuccess!=null && updateStudentSuccess.equals("true")){
            model.addAttribute("alertMessage", "Student update successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("updateStudentSuccessfully");
        } else if(updateStudentFailed !=null && updateStudentFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to update Student!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("updateStudentFailed");
        }


        if(deleteStudentId != null){
            model.addAttribute("deleteStudentId" , deleteStudentId);
            httpSession.setAttribute("deleteStudentId" , deleteStudentId);
            model.addAttribute("deleteMode"  , true);
        }

        String deleteStudentSuccess = (String) httpSession.getAttribute("deleteStudentSuccessfully");
        String deleteStudentFailed = (String)  httpSession.getAttribute("deleteStudentFailed");

        if(deleteStudentSuccess!=null && deleteStudentSuccess.equals("true")){
            model.addAttribute("alertMessage", "Student delete successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("deleteStudentSuccessfully");
        } else if(deleteStudentFailed !=null && deleteStudentFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to delete Student!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("deleteStudentFailed");
        }

        model.addAttribute("students" , students);

        if(searchInput!=null){
            model.addAttribute("students" , searchInput(searchInput));
        }



        return "student_management";
    }




    @PostMapping("/student_management")
    public String studentManagement(
            @RequestParam("action") String action,
            @ModelAttribute StudentDto studentDto ,
            Model model , HttpSession httpSession) throws IOException {


        switch (action){
            case "add" :
                        Student student = studentDto.toStudent();
                        if(addStudent(student)){
                            httpSession.setAttribute("addStudentSuccessFully" , "true");
                        } else{
                            httpSession.setAttribute("addStudentFailed" , "true");
                        }
                        break;

            case "edit" :
                        Long id =(Long) httpSession.getAttribute("editStudentId");
                        Student updateStudent = studentDto.toStudent();
                        updateStudent.setId(id.intValue());

//                MultipartFile file = studentDto.identityFile();
//
//                if(file != null && !file.isEmpty()){
//                    String filename = file.getOriginalFilename();
//                    Path path = Paths.get("uploads/" + filename);
//                    Files.createDirectories(path.getParent());
//                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//                    updateStudent.setIdentityFileName(filename);
//                }


                        if(updateStudent(updateStudent)){
                            httpSession.setAttribute("updateStudentSuccessfully" , "true");
                        } else{
                            httpSession.setAttribute("updateStudentFailed" , "true");
                        }
                        break;

            case "delete" :
                            Long deleteStudentId = (Long) httpSession.getAttribute("deleteStudentId");

                            if(deleteStudent(deleteStudentId.intValue())){
                                httpSession.setAttribute("deleteStudentSuccessfully" , "true");
                            } else{
                                httpSession.setAttribute("deleteStudentFailed" , "true");
                            }
                            break;



        }


        return "redirect:/student_management";
    }

    private boolean addStudent(Student student){
        return studentService.addNewStudent(student);
    }
    private boolean updateStudent(Student student){

        if(student.getIdentityFileName() == null ||student.getIdentityFileName().isEmpty()){
            student.setIdentityFileName(studentService.getStudent(student.getId()).getIdentityFileName());
        }

        return studentService.updateStudent(student);
    }


    private boolean deleteStudent(int studentId){
       return studentService.deleteStudent(studentId);
    }

    private String registrationDateFormate(LocalDate registrationDate){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

       return registrationDate.format(formatter);

    }


    private List<Student> searchInput(String text){
        ArrayList<Student> students = studentService.getAllStudent();
        if(text == null || text.isEmpty()){
            return students;
        }
        final String input = text.trim().toLowerCase();

        return students.stream()
                .filter(b ->
                                b.getFullName().trim().toLowerCase().contains(input) ||
                                b.getIdentity().trim().toLowerCase().contains(input) ||
                                b.getEmail().trim().toLowerCase().contains(input) ||
                                b.getPhoneNumber().trim().toLowerCase().contains(input) ||
                                String.valueOf(b.getRegistrationDate()).contains(input)
                ).toList();
    }

}
