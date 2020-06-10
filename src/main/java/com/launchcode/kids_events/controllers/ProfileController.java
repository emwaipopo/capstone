package com.launchcode.kids_events.controllers;

import com.launchcode.kids_events.models.Profile;
import com.launchcode.kids_events.models.User;
import com.launchcode.kids_events.models.data.ProfileRepository;
import com.launchcode.kids_events.models.data.UserRepository;
import com.launchcode.kids_events.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String displayUserProfile(Model model) {
        model.addAttribute("title", "My Profile");
        model.addAttribute("profile", new Profile());
        model.addAttribute(new RegisterFormDTO());
        return "profile";
    }

    @GetMapping("{profileId}")
    public String displayUserProfileForm(Model model, @PathVariable int profileId) {
        Optional currentProfile = profileRepository.findById(profileId);

        if (currentProfile.isPresent()) {
            Profile profile = (Profile) currentProfile.get();
            model.addAttribute("profile", profile);
        } else {
            model.addAttribute("title", "My Profile");
            model.addAttribute("profile", new Profile());
            model.addAttribute(new RegisterFormDTO());
        }
        return "profile";
    }

    @PostMapping
    public String processUserProfileForm(@ModelAttribute @Valid Profile profile, @ModelAttribute @Valid RegisterFormDTO registerFormDTO, Errors errors, Model model) {

        if(errors.hasErrors()) {
            model.addAttribute("title", "My Profile");
            model.addAttribute("message","Please review all fields before submitting.");
            return "profile";
        }
        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyExists", "A user with that username already exists");
            model.addAttribute("title", "My Profile");
            return "profile";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "My Profile");
            return "profile";
        }


        User newUser = new User(registerFormDTO.getUsername(), registerFormDTO.getPassword());
        profile.setUser(newUser);
        profileRepository.save(profile);
        model.addAttribute("message","Profile saved.");
        return "redirect:";
    }
}
