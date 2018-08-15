package com.example.SongFinder.Repositories;

import com.example.SongFinder.Entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepo extends JpaRepository<Artist, Long> {
}
