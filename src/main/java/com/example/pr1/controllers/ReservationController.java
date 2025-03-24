package com.example.pr1.controllers;

import com.example.pr1.models.Reservation;
import com.example.pr1.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pr1/reservations")
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(reservationService.get(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        try {
            return ResponseEntity.ok(reservationService.create(reservation));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<Reservation> update(@PathVariable UUID id, @RequestBody Reservation updatedReservation) {
        try {
            updatedReservation.setReservId(id);
            return ResponseEntity.ok(reservationService.update(updatedReservation));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            reservationService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
