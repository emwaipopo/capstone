package com.launchcode.kids_events.controllers;

import com.launchcode.kids_events.models.data.EventsRepository;
import com.launchcode.kids_events.models.data.ProfileRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    private EventsRepository eventsRepository;

    private ProfileRepository profileRepository;

    @RequestMapping("selected")
    public String displaySelectEvents(Model model) {
        model.addAttribute("title", "Family Events");
        model.addAttribute("events",eventsRepository.findAll());
        return "index";
    }

    @GetMapping
    public String displayAllEvents(Model model){
        model.addAttribute("title", "My Family Events");
        //model.addAttribute("events",eventsRepository.findAll());
        return "index";
    }

    @GetMapping("{eventId}")
    public String displaySingleEvent(@PathVariable Integer eventId, Model model) {
        model.addAttribute("title", "Selected Event: " + eventsRepository.findById(eventId));
        model.addAttribute("events",eventsRepository.findById(eventId));
        return "index";
    }
}
