package com.dians.hotelmanagement.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;
    @ManyToOne
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JsonManagedReference
    private Hotel hotel;

    private String reviewText;
    private int stars = 0;

    public Feedback(String reviewText, int stars) {
        this.reviewText = reviewText;
        this.stars = stars;
    }

    public Feedback() {
    }
}
