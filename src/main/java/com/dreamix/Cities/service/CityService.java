package com.dreamix.Cities.service;

import com.dreamix.Cities.model.City;
import com.dreamix.Cities.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> searchCities(String searchText) {
        if (!StringUtils.trimAllWhitespace(searchText).equals("")) {
            return cityRepository.findAll().stream()
                    .filter(c -> c.getName().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return cityRepository.findAll();
    }

    public Optional<City> updateCity(City city) {
        return cityRepository.existsById(city.getId()) ? Optional.of(cityRepository.save(city)) : Optional.empty();
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }
}
