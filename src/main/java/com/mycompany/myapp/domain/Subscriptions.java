package com.mycompany.myapp.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Subscriptions.
 */
@Entity
@Table(name = "subscriptions")
public class Subscriptions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "subscribtion_date_time")
    private ZonedDateTime subscribtionDateTime;

    @Column(name = "subscriber")
    private String subscriber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "petition_id")
    private Petition petition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getSubscribtionDateTime() {
        return subscribtionDateTime;
    }

    public void setSubscribtionDateTime(ZonedDateTime subscribtionDateTime) {
        this.subscribtionDateTime = subscribtionDateTime;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Petition getPetition() {
        return petition;
    }

    public void setPetition(Petition petition) {
        this.petition = petition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subscriptions subscriptions = (Subscriptions) o;
        return Objects.equals(id, subscriptions.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Subscriptions{" +
            "id=" + id +
            ", subscribtionDateTime='" + subscribtionDateTime + "'" +
            ", subscriber='" + subscriber + "'" +
            '}';
    }
}
