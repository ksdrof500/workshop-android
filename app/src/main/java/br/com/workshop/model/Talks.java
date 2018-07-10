package br.com.workshop.model;

import java.io.Serializable;

public class Talks implements Serializable {

    public String name;
    public String description;
    public String image;

    public Talks(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
