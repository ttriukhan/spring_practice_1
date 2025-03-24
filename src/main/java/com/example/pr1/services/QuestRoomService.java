package com.example.pr1.services;

import com.example.pr1.models.QuestRoom;
import com.example.pr1.repositories.QuestRoomRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class QuestRoomService {

    private QuestRoomRepository questRoomRepository;

    public List<QuestRoom> getAll() {
        return questRoomRepository.findAll();
    }

    public QuestRoom get(UUID id) throws Exception {
        return questRoomRepository.findById(id).orElseThrow(() -> new Exception("Not found"));
    }

    public QuestRoom create(QuestRoom questRoom) {
        return questRoomRepository.save(questRoom);
    }

    public QuestRoom update(QuestRoom questRoom) throws Exception {
        questRoomRepository.findById(questRoom.getQuestId())
                .orElseThrow(() -> new Exception("Not found"));
        return questRoomRepository.save(questRoom);
    }

    public void delete(UUID id) throws Exception {
         QuestRoom questRoom = questRoomRepository.findById(id)
                .orElseThrow(() -> new Exception("Not found"));
         questRoomRepository.delete(questRoom);
    }

    public List<QuestRoom> getAllWithReservations() {
        List<QuestRoom> questRooms = questRoomRepository.findAll();
        questRooms.forEach(questRoom -> Hibernate.initialize(questRoom.getReservations()));
        return questRooms;
    }

    public QuestRoom getWithReservations(UUID id) throws Exception {
        QuestRoom questRoom = questRoomRepository.findById(id).orElseThrow(() -> new Exception("Not found"));
        Hibernate.initialize(questRoom.getReservations());
        return questRoom;
    }

}
