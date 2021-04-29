package com.alextim;

import com.alextim.domain.relational.AuthorRelation;
import com.alextim.repository.relation.AuthorRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableJpaRepositories
@EnableMongoRepositories
public class Main {

    @Autowired
    private AuthorRelationRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }


    @PostConstruct
    public void initRelationDb() {
        IntStream.range(0, 149).forEach(i -> repository.save(new AuthorRelation("Alex" + i, "Pushkin" + i)));
    }
}
