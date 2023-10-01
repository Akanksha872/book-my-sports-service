package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import io.ebean.Ebean;

import models.Event;
import models.dto.EventDTO;
import java.util.List;

import java.util.stream.Collectors;

import play.libs.Json;

public class EventController extends Controller {

    public Result getAllEvents() {
        List<Event> events = Ebean.find(Event.class).findList();

        List<EventDTO> eventDtos = events.stream().map(event -> {
            EventDTO dto = new EventDTO();
            dto.setId(event.getId());
            dto.setEventName(event.getEventName());
            dto.setStartTime(event.getStartTime());
            dto.setEndTime(event.getEndTime());
            dto.setCategory(event.getEventType().getCategory());
            dto.setImage(event.getEventType().getImage());
            return dto;
        }).collect(Collectors.toList());
    
        return ok(Json.toJson(eventDtos));
    }
}
