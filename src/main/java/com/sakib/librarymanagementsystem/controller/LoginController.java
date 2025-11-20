package com.sakib.librarymanagementsystem.controller;


import com.sakib.librarymanagementsystem.dto.LoginDto;
import com.sakib.librarymanagementsystem.modal.Admin;
import com.sakib.librarymanagementsystem.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final AdminService adminService;

    private LoginController(AdminService adminService){
        this.adminService = adminService;
        adminService.addNewAdmin(new Admin("admin" , "pass" , "Admin" , 11));
    }

    @GetMapping("/login")
    public String login(Model model){

        model.addAttribute("dto" , new LoginDto("" ,""));

        return "login";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto dto , Model model , HttpSession httpSession){

        if(adminService.isExist(dto.identity(), dto.password()) != null){
            System.out.println("Login Success");
            httpSession.setAttribute("name" , adminService.getAdmin(dto.identity()).getName());
            return "redirect:/dashboard";
        } else{
            System.out.println("Login Failed");
            return "redirect:/login";
        }
    }

}
