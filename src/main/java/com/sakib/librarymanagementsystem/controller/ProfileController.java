package com.sakib.librarymanagementsystem.controller;

import com.sakib.librarymanagementsystem.modal.Profile;
import com.sakib.librarymanagementsystem.service.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public String profile(Model model){
        Profile profile = profileService.getProfile(1);
        model.addAttribute("name" , profile.getName());
        model.addAttribute("phoneNumber" , profile.getPhoneNumber() );
        model.addAttribute("email" , profile.getEmail());
        model.addAttribute("registrationDate" , profile.getRegistrationDate());
        return "profile";
    }
}
