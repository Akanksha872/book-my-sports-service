package models;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.ebean.Finder;
import io.ebean.Model;

import java.util.Date;

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

    @JsonProperty("created_at")
    private Date createdAt;


    public UserEventRegistration() {
    }

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public static final Finder<Long, UserEventRegistration> find = new Finder<>(UserEventRegistration.class);
}
