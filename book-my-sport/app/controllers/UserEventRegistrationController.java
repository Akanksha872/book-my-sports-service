package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import io.ebean.Ebean;

import models.User;
import models.Event;
import models.dto.EventDTO;
import models.UserEventRegistration;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import play.mvc.Http.Request;
import play.libs.Json;
import io.ebean.Query;

import io.ebean.Transaction;

import utils.AppUtil;

import com.fasterxml.jackson.databind.JsonNode;
import utils.EventConverter;

public class UserEventRegistrationController extends Controller {

    public Result getAllRegisteredEvents(String userId) {
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

            List<EventDTO> eventDtos = EventConverter.convertToEventDTOList(events);
            return ok(Json.toJson(eventDtos));

        } catch (NumberFormatException e) {
            return badRequest(AppUtil.createMessageNode("Invalid user ID format."));
        }
    }

    public Result registerEvent(Request request) {
        if(request.body() == null){
            return badRequest(AppUtil.createMessageNode("Missing JSON body."));
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
            UserEventRegistration eventRegistration = Ebean.find(UserEventRegistration.class)
                .where()
                .eq("userId", userId)
                .eq("eventId", eventId)
                .findOne();

            if (eventRegistration != null) {
                return badRequest("Same event cannot be registered by the user twice.");
            }   

            UserEventRegistration registration = new UserEventRegistration(eventId, userId);
            registration.setCreatedAt(new Date());
            registration.save();
            return ok(AppUtil.createMessageNode("Event registration saved successfully."));
        } catch (Exception e) {
            return internalServerError(AppUtil.createMessageNode("Failed to register event."));
        }

    }

    public Result unregisterEvent(Request request) {

        if(request.body() == null){
            return badRequest(AppUtil.createMessageNode("Missing JSON body."));
        }
        JsonNode body = request.body().asJson();
        try {
            int userId = AppUtil.getIntField(body, "user_id", true);
            int eventId = AppUtil.getIntField(body, "event_id", true);
            User user = Ebean.find(User.class).where().eq("id", userId).findOne(); 
            Event event = Ebean.find(Event.class).where().eq("id", eventId).findOne(); 
            if(user == null) {
                return badRequest(AppUtil.createMessageNode("Invalid user id"));
            }
            if(event == null) {
                return badRequest(AppUtil.createMessageNode("Invalid event id"));
            }
            UserEventRegistration existingRegistration = Ebean.find(UserEventRegistration.class)
            .where()
            .eq("user_id", userId)
            .eq("event_id", eventId).findOne();

            if (existingRegistration == null) {
             return badRequest(AppUtil.createMessageNode("No matching registration found."));
            }
            Ebean.delete(existingRegistration);
            return ok(AppUtil.createMessageNode("Event unregistration successfully."));
        } catch (Exception e) {
            return internalServerError(AppUtil.createMessageNode("Failed to unregister event."));
        }
    }

}
