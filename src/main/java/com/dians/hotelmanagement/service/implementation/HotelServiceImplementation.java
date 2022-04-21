package com.dians.hotelmanagement.service.implementation;

import com.dians.hotelmanagement.model.Feedback;
import com.dians.hotelmanagement.model.Hotel;
import com.dians.hotelmanagement.repository.HotelRepository;
import com.dians.hotelmanagement.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

@Service
public class HotelServiceImplementation implements HotelService {
    private final HotelRepository hotelRepository;

    public HotelServiceImplementation(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> findAll() {
        return this.hotelRepository.findAll();
    }

    @Override
    public Page<Hotel> findAllHotelsInCity(String cityName, Pageable pageable) {
        return this.hotelRepository.findAllByCityName("%"+cityName+"%", pageable);
    }

    @Override
    public List<Hotel> findAllHotelsInCity(String cityName) {
        return this.hotelRepository.findAllByCityName("%"+cityName+"%");
    }

    @Override
    public List<Hotel> findMostPopularHotels() {
        return this.hotelRepository.findAll().stream()
                .sorted((k, v) -> v.getFeedbacks().stream()
                        .map(Feedback::getStars)
                        .reduce(0, Integer::sum)
                        .compareTo(k.getFeedbacks().stream()
                                .map(Feedback::getStars)
                                .reduce(0, Integer::sum)))
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public Hotel findHotelByCityNameAndHotelName(String cityName, String hotelName) {
        return this.hotelRepository.findHotelByCityNameAndName(cityName, hotelName);
    }

    @Override
    public void save(Hotel hotel) {
        this.hotelRepository.save(hotel);
    }

    @Override
    public Optional<Hotel> findById(Long id) {
        return hotelRepository.findById(id);
    }

}
