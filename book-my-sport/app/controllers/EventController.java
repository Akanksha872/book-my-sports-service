package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import io.ebean.Ebean;
import models.Event;
import models.dto.EventDTO;
import java.util.List;
import play.libs.Json;
import utils.EventConverter;

public class EventController extends Controller {

    public Result getAllEvents() {
        List<Event> events = Ebean.find(Event.class).findList();
        List<EventDTO> eventDtos = EventConverter.convertToEventDTOList(events);
        return ok(Json.toJson(eventDtos));
    }
}
