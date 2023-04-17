package ru.costa.nasa_picture_of_the_day.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.costa.nasa_picture_of_the_day.NASA;

import java.util.Optional;

@Repository
public interface NasaRepository extends JpaRepository<NASA, Long> {
     boolean existsByTitle(String title);
}