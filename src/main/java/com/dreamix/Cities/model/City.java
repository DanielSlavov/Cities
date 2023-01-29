package com.dreamix.Cities.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cities")
public class City {
    private static final String REGEX_PATTERN_LINK = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]" +
            "{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";


    @Id
    @Positive
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Size(message="name is too short", min=3)
    private String name;

    @Column(name="image_link", length = 1024)
    @Pattern(message="link is not valid", regexp = REGEX_PATTERN_LINK)
    private String imageLink;

    public City() {

    }

    public City(long id, String name, String imageLink) {
        this.id = id;
        this.name = name;
        this.imageLink = imageLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
