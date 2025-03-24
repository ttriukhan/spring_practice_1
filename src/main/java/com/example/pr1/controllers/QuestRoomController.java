package com.example.pr1.controllers;

import com.example.pr1.models.QuestRoom;
import com.example.pr1.services.QuestRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pr1/questRooms")
@AllArgsConstructor
public class QuestRoomController {

    private final QuestRoomService questRoomService;

    @GetMapping
    public List<QuestRoom> getAll() {
        return questRoomService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestRoom> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(questRoomService.get(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<QuestRoom> create(@RequestBody QuestRoom questRoom) {
        return ResponseEntity.ok(questRoomService.create(questRoom));
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<QuestRoom> update(@PathVariable UUID id, @RequestBody QuestRoom updatedQuestRoom) {
        try {
            updatedQuestRoom.setQuestId(id);
            return ResponseEntity.ok(questRoomService.update(updatedQuestRoom));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            questRoomService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/withReservations")
    public List<QuestRoom> getAllWithReservations() {
        return questRoomService.getAllWithReservations();
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<QuestRoom> getWithReservations(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(questRoomService.getWithReservations(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
