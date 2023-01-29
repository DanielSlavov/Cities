package com.dreamix.Cities.api.controller;

import com.dreamix.Cities.model.City;
import com.dreamix.Cities.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("")
    public List<City> searchCities(@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
        return cityService.searchCities(searchText);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<City> updateCity(@Valid @RequestBody City city) {
        return ResponseEntity.of(cityService.updateCity(city));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.ok().build();
    }
}
