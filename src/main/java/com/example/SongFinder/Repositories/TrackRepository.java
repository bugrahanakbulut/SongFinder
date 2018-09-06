package com.example.SongFinder.Repositories;

import com.example.SongFinder.Entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    public List<Track> findByCountry(String country);
    public List<Track> findByIdSpotify(String idSpotify);
}
