package ru.costa.nasa_picture_of_the_day.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.costa.nasa_picture_of_the_day.NASA;
import ru.costa.nasa_picture_of_the_day.repository.NasaRepository;

import java.util.List;

@Service
public class NasaService {
    private NasaRepository repository;

    @Autowired
    public NasaService(NasaRepository repository) {
        this.repository = repository;
    }
    public List<NASA> get() {
        return repository.findAll();
    }
    public void save(NASA nasa) {
        repository.save(nasa);
    }
}
