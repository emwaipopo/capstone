package com.launchcode.kids_events.controllers;

import com.launchcode.kids_events.models.Profile;
import com.launchcode.kids_events.models.User;
import com.launchcode.kids_events.models.data.ProfileRepository;
import com.launchcode.kids_events.models.data.UserRepository;
import com.launchcode.kids_events.models.dto.ButtonActionDTO;
import com.launchcode.kids_events.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("index")
    public String displayProfiles(Model model) {
        model.addAttribute("title", "All Families");
        model.addAttribute("profiles", profileRepository.findAll());
//        model.addAttribute(new RegisterFormDTO());
        return "profiles/index";
    }

    @GetMapping("profile")
    public String displayProfileForm(@RequestParam(required = false) Integer profileId, Model model) {
        if(profileId != null) {
            Profile profile = profileRepository.findById(profileId).get();
            model.addAttribute("title", "Edit Profile");
            model.addAttribute("profile", profile);
            model.addAttribute("restricted", "true");
            model.addAttribute("registerFormDTO",userRepository.findById(profile.getUser().getId()));
            return "profiles/profile";
        }
        model.addAttribute("title", "Create User");
        model.addAttribute("profile", new Profile());
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("buttonActionDTO", new ButtonActionDTO());
        return "profiles/profile";
    }

    @PostMapping("profile")
    public String processProfileForm(@RequestParam(required = false) Integer profileId, @ModelAttribute @Valid Profile profile,
                                         @ModelAttribute @Valid RegisterFormDTO registerFormDTO, Errors errors, Model model,
                                         @ModelAttribute @Valid ButtonActionDTO buttonActionDTO) {

        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Profile");
            model.addAttribute("message","Please review all fields before submitting.");
            return "profiles/profile";
        }

        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());
        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyExists", "A user with that username already exists");
            model.addAttribute("title", "Create Profile");
            return "profiles/profile";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Create Profile");
            return "profiles/profile";
        }

        if(profileId != null) {
            if(buttonActionDTO.getUserAction().equals("Save User")) {
                Profile editProfile = profileRepository.findById(profileId).get();
                editProfile.setFirstName(profile.getFirstName());
                editProfile.setLastName(profile.getLastName());
//                editProfile.setUser(profile.getUser());
                editProfile.setGender(profile.getGender());
                editProfile.setDob(profile.getDob());
                editProfile.setEmail(profile.getEmail());
                editProfile.setAddress(profile.getAddress());
                editProfile.setPhoneNumber(profile.getPhoneNumber());
                profileRepository.save(editProfile);
                model.addAttribute("title", "User Modified");
            }else if(buttonActionDTO.getUserAction().equals("Delete User")) {
                model.addAttribute("title", "User Deleted");
                profileRepository.deleteById(profileId);
            }
            return "events/index";
        }

        User newUser = new User(registerFormDTO.getUsername(), registerFormDTO.getPassword(), registerFormDTO.getRole());
        profile.setUser(newUser);
        profileRepository.save(profile);
        model.addAttribute("title", profile.getFirstName() + "'s Profile.");
        model.addAttribute("message","Profile saved.");
        return "profiles/profile";
    }
}
