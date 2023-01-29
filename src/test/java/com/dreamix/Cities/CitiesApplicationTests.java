package com.dreamix.Cities;

import com.dreamix.Cities.model.City;
import com.dreamix.Cities.repository.CityRepository;
import com.dreamix.Cities.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;


@SpringBootTest
class CitiesApplicationTests {

    @Autowired
    private CityService cityService;

    @Autowired
	private CityRepository cityRepository;

    @Test
    void cityUpdateTest() {
		//setting the id to Long max knowing that it is not present in the database because city addition is not in the scope
		//otherwise we would use reflection to set a non-existant negative id
        City city = new City(Long.MAX_VALUE, "TestCity", "http://website.com/image.png");
        assert cityService.updateCity(city).isEmpty();
    }

}
