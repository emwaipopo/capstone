package com.launchcode.kids_events.controllers;

import com.launchcode.kids_events.models.Events;
import com.launchcode.kids_events.models.data.EventsRepository;
import com.launchcode.kids_events.models.dto.ButtonActionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("events")
public class EventsController {

    @Autowired
    private EventsRepository eventsRepository;

    @GetMapping("index")
    public String displayEvents(Model model){
        model.addAttribute("title", "All Events");
        model.addAttribute("events", eventsRepository.findAll());
        return "events/index";
    }

    @GetMapping("event")
    public String displayEventForm(@RequestParam(required = false) Integer eventId, Model model){
        if(eventId != null) {
            model.addAttribute("title", "Edit Event");
            model.addAttribute("events", eventsRepository.findById(eventId));
            return "events/event";
        }
        model.addAttribute("title", "Create Event");
        model.addAttribute("events", new Events());
        model.addAttribute("buttonActionDTO", new ButtonActionDTO());
        return"events/event";
    }

    @PostMapping("event")
    public String processEventForm(@RequestParam(required = false) Integer eventId, @ModelAttribute @Valid Events events,
                                   @ModelAttribute @Valid ButtonActionDTO buttonActionDTO, Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            return "events/event";
        }
        if(eventId != null) {
            if(buttonActionDTO.getUserAction().equals("Save Event")) {
                Events editEvent = eventsRepository.findById(eventId).get();
                editEvent.setName(events.getName());
                editEvent.setDescription(events.getDescription());
                editEvent.setDate(events.getDate());
                editEvent.setAddress(events.getAddress());
                eventsRepository.save(editEvent);
                model.addAttribute("title", "Event Modified");
            }else if(buttonActionDTO.getUserAction().equals("Delete Event")) {
                model.addAttribute("title", "Event Deleted");
                eventsRepository.deleteById(eventId);
            }
            return "events/index";
        }
        eventsRepository.save(events);
        model.addAttribute("title", "Success! New Event Created.");
        return "events/index";
    }
}
