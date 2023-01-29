package com.dreamix.Cities.data;

import com.dreamix.Cities.api.controller.CityController;
import com.dreamix.Cities.model.City;
import com.dreamix.Cities.repository.CityRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
@ConditionalOnProperty(name = "data.load.on.start")
public class DataLoader implements ApplicationRunner {

    @Autowired
    private CityRepository cityRepository;

    public void run(ApplicationArguments args) throws IOException {
        Reader in = new InputStreamReader(CityController.class.getResourceAsStream("/data/cities.csv"));
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            int id = Integer.parseInt(record.get("id"));
            String name = record.get("name");
            String link = record.get("photo");
            cityRepository.save(new City(id, name, link));
        }
    }
}