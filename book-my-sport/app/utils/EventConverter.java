package utils;

import models.Event;
import models.dto.EventDTO;
import java.util.List;
import java.util.stream.Collectors;

public class EventConverter {

    public static List<EventDTO> convertToEventDTOList(List<Event> events) {
        return events.stream().map(event -> {
            EventDTO dto = new EventDTO();
            dto.setId(event.getId());
            dto.setEventName(event.getEventName());
            dto.setStartTime(event.getStartTime());
            dto.setEndTime(event.getEndTime());
            dto.setCategory(event.getEventType().getCategory());
            dto.setImage(event.getEventType().getImage());
            return dto;
        }).collect(Collectors.toList());
    }
}
