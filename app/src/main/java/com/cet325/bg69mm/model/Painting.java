package com.cet325.bg69mm.model;

/**
 * Created by jamessunley on 08/01/2018.
 */

//painting model class
public class Painting {

    //create variables
    private int id;
    private String artistName;
    private String title;
    private String room;
    private String description;
    private String rank;
    private String image;
    private String year;

    public Painting() {

    }

    // takes in paramaters for all aspects of the painting
    public Painting(int id, String artistName, String title, String room, String description, String image, String rank, String year) {
        //super();
        this.id = id;
        this.artistName = artistName;
        this.title = title;
        this.description = description;
        this.rank = rank;
        this.room = room;
        this.year = year;
        this.image = image;
    }

    //getters & setters
    @Override
    public String toString() {
        return "Painting [id=" + id + ", title=" + title + ", artist=" + artistName
                + ", description=" + description + ", rank=" + rank + ", room=" + room +
                ", year=" + year + ", image= " +image +"]";
    }

    //getters and setters for paintings
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
