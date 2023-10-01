package models;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.ebean.Finder;
import io.ebean.Model;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="user_event_registration")
public class UserEventRegistration extends Model {

    @Id
    private Long id;
    
    @ManyToOne
    @JsonProperty("event_id")
    private int eventId;

    @ManyToOne
    @JsonProperty("user_id")
    private int userId;

    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private User user;

    // @ManyToOne
    // @JoinColumn(name = "event_id")
    // private Event event;

    public UserEventRegistration() {
    }

    // public UserEventRegistration(User user, Event event) {
    //     this.user = user;
    //     this.event = event;
    // }

    public UserEventRegistration(int eventId, int userId) {
        this.eventId = eventId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // public Long getId() {
    //     return id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }

    // public Event getEvent() {
    //     return event;
    // }

    // public void setEvent(Event event) {
    //     this.event = event;
    // }

    public static final Finder<Long, UserEventRegistration> find = new Finder<>(UserEventRegistration.class);

}
