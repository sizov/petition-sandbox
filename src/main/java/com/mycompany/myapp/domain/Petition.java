package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Petition.
 */
@Entity
@Table(name = "petition")
public class Petition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "creation_time")
    private ZonedDateTime creationTime;

    @Column(name = "creater")
    private String creater;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "petition")
    @JsonIgnore
    private Set<Subscriptions> subscriptionss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Subscriptions> getSubscriptionss() {
        return subscriptionss;
    }

    public void setSubscriptionss(Set<Subscriptions> subscriptionss) {
        this.subscriptionss = subscriptionss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Petition petition = (Petition) o;
        return Objects.equals(id, petition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Petition{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", body='" + body + "'" +
            ", creationTime='" + creationTime + "'" +
            ", creater='" + creater + "'" +
            '}';
    }
}
