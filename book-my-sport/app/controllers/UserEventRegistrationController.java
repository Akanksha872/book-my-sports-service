package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import io.ebean.Ebean;

import models.User;
import models.Event;
import models.dto.EventDTO;
import models.UserEventRegistration;
import java.util.List;
import java.util.stream.Collectors;

import play.mvc.Http.Request;
import play.libs.Json;
import io.ebean.Query;

import io.ebean.Transaction;

import utils.AppUtil;

import com.fasterxml.jackson.databind.JsonNode;

public class UserEventRegistrationController extends Controller {

    public Result getAllRegisteredEvents(String userId) {
        // Parse the userId string into an integer
        try {
            int userIdInt = Integer.parseInt(userId);

            List<UserEventRegistration> userEvents = Ebean.find(UserEventRegistration.class)
                .where()
                .eq("user_id", userIdInt)
                .findList();

            // Extract event IDs from userEvents
            List<Integer> eventIds = userEvents.stream()
                .map(UserEventRegistration::getEventId)
                .collect(Collectors.toList());

            // Query the events table to get the details of the events with matching IDs
            List<Event> events = Ebean.find(Event.class)
                .where()
                .in("id", eventIds)
                .findList();
            
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

        } catch (NumberFormatException e) {
            return badRequest("Invalid user ID format.");
        }
    }

    public Result registerEvent(Request request) {
        if(request.body() == null){
            return badRequest("Missing JSON body.");
        }
        JsonNode body = request.body().asJson();
        try {
            int userId = AppUtil.getIntField(body, "user_id", true);
            int eventId = AppUtil.getIntField(body, "event_id", true);
            User user = Ebean.find(User.class).where().eq("id", userId).findOne(); 
            Event event = Ebean.find(Event.class).where().eq("id", eventId).findOne(); 
            if(user == null) {
                return badRequest("Invalid user id");
            }
            if(event == null) {
                return badRequest("Invalid event id");
            }
            System.out.println("-------------------");
            System.out.println(userId);
            System.out.println(eventId);
            System.out.println("-------------------");
            UserEventRegistration registration = new UserEventRegistration(eventId, userId);
            registration.save();
            return ok("Event registration saved successfully.");
        } catch (Exception e) {
            return internalServerError("Failed to register event.");
        }

    }

    public Result unregisterEvent(Request request) {

        if(request.body() == null){
            return badRequest("Missing JSON body.");
        }
        JsonNode body = request.body().asJson();
        try {
            int userId = AppUtil.getIntField(body, "user_id", true);
            int eventId = AppUtil.getIntField(body, "event_id", true);
            User user = Ebean.find(User.class).where().eq("id", userId).findOne(); 
            Event event = Ebean.find(Event.class).where().eq("id", eventId).findOne(); 
            if(user == null) {
                return badRequest("Invalid user id");
            }
            if(event == null) {
                return badRequest("Invalid event id");
            }
            UserEventRegistration existingRegistration = Ebean.find(UserEventRegistration.class)
            .where()
            .eq("user_id", userId)
            .eq("event_id", eventId).findOne();

            if (existingRegistration == null) {
             return badRequest("No matching registration found.");
            }
            Ebean.delete(existingRegistration);
            return ok("Event unregistration successfully.");
        } catch (Exception e) {
            return internalServerError("Failed to unregister event.");
        }
    }

}
