package ua.apparatus.catalogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.apparatus.catalogue.repository.VerbRepository;

@SpringBootApplication
public class CatalogueServiceETA {

    @Autowired
    public VerbRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(CatalogueServiceETA.class, args);
    }

}
