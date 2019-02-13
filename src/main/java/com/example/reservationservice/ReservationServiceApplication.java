/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2019 VTB Group. All rights reserved.
 */

package com.example.reservationservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@RepositoryRestResource interface ReservationRepository extends JpaRepository<Reservation, Long> {
  @RestResource(path = "by-name")
  Collection<Reservation> findByReservationName(@Param("rn") String rn);

}

//
@SpringBootApplication
public class ReservationServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReservationServiceApplication.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner(ReservationRepository reservationRepository) {
    return args -> Stream.of("Ignat", "Josh", "Uhvat", "Kolovrat", "Korneliy")
            .forEach(n -> reservationRepository.save(new Reservation(n)));
  }

}

@RefreshScope
@RestController
class MessageRestController {

  @Value("${message}")
  private String message;

  @RequestMapping("/message")
  String message() {
    return this.message;
  }

}

@Entity class Reservation {
  @Id
  @GeneratedValue
  private Long id;

  private String reservationName;

  public Reservation(String reservationName) {
    this.reservationName = reservationName;
  }

  public Reservation() {
  }

  @Override public String toString() {
    return "Reservation{" +
            "id=" + id +
            ", reservationName='" + reservationName + '\'' +
            '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReservationName() {
    return reservationName;
  }

  public void setReservationName(String reservationName) {
    this.reservationName = reservationName;
  }
}

