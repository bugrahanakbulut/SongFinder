package com.example.SongFinder.Entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long dbId;
    private String idSpotify;
    private String artistName;
    private String trackName;
    private String country;
    private String spotifyUri;
}
