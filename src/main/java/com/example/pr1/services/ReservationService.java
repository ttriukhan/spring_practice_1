package com.example.pr1.services;

import com.example.pr1.models.QuestRoom;
import com.example.pr1.models.Reservation;
import com.example.pr1.repositories.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReservationService {
    private ReservationRepository reservationRepository;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation get(UUID id) throws Exception {
        return reservationRepository.findById(id).orElseThrow(() -> new Exception("Not found"));
    }

    public Reservation create(Reservation reservation) throws Exception {
        QuestRoom questRoom = reservation.getQuestRoom();
        if (!questRoom.isServiceable()) {
            throw new Exception("Quest Room is not serviceable");
        }
        List<Reservation> existingReservations = questRoom.getReservations();
        for (Reservation res : existingReservations) {
            if (isOverlapping(res, reservation)) {
                throw new Exception("Time slot is already reserved");
            }
        }
        return reservationRepository.save(reservation);
    }

    private boolean isOverlapping(Reservation res1, Reservation res2) {
        if (! res1.getQuestDate().equals(res2.getQuestDate()))
            return false;
        if (res1.getStartTime().isAfter(res2.getStartTime()) && res1.getStartTime().isBefore(res2.getEndTime())) {
            return true;
        }
        return (res2.getStartTime().isAfter(res1.getStartTime()) && res2.getStartTime().isBefore(res1.getEndTime()));
    }

    public Reservation update(Reservation reservation) throws Exception {
        reservationRepository.findById(reservation.getReservId())
                .orElseThrow(() -> new Exception("Not found"));
        return reservationRepository.save(reservation);
    }

    public void delete(UUID id) throws Exception {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new Exception("Not found"));
        reservationRepository.delete(reservation);
    }
}
