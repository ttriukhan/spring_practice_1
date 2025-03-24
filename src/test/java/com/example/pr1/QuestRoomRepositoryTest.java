package com.example.pr1;

import com.example.pr1.models.QuestRoom;
import com.example.pr1.repositories.QuestRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class QuestRoomRepositoryTest {

    @Autowired
    private QuestRoomRepository questRoomRepository;

    @Test
    void testSaveQuestRoom() {
        QuestRoom questRoom = new QuestRoom();
        questRoom.setQuestName("Escape Room");
        questRoom.setQuestAddress("123 Main St");
        questRoom.setRoomCapacity("5");
        questRoom.setPrice(100.0f);
        questRoom.setServiceable(true);

        QuestRoom savedRoom = questRoomRepository.save(questRoom);

        assertThat(savedRoom).isNotNull();
        assertThat(savedRoom.getQuestId()).isNotNull();
        assertEquals(questRoomRepository.findAll().size(), 1);
    }

    @Test
    void testFindAll() {
        QuestRoom questRoom = new QuestRoom();
        questRoom.setQuestName("Escape Room");
        questRoom.setQuestAddress("123 Main St");
        questRoom.setRoomCapacity("5");
        questRoom.setPrice(100.0f);
        questRoom.setServiceable(true);

        assertEquals(questRoomRepository.findAll().size(), 0);

        QuestRoom savedRoom = questRoomRepository.save(questRoom);

        assertThat(savedRoom).isNotNull();
        assertThat(savedRoom.getQuestId()).isNotNull();
        assertEquals(questRoomRepository.findAll().size(), 1);
    }

    @Test
    void testFindById() {
        QuestRoom questRoom = new QuestRoom();
        questRoom.setQuestName("Horror Room");
        questRoom.setQuestAddress("456 Elm St");
        questRoom.setRoomCapacity("4");
        questRoom.setPrice(80.0f);
        questRoom.setServiceable(true);

        QuestRoom savedRoom = questRoomRepository.save(questRoom);
        Optional<QuestRoom> foundRoom = questRoomRepository.findById(savedRoom.getQuestId());

        assertThat(foundRoom).isPresent();
        assertThat(foundRoom.get().getQuestName()).isEqualTo("Horror Room");
    }

    @Test
    void testDeleteQuestRoom() {
        QuestRoom questRoom = new QuestRoom();
        questRoom.setQuestName("Mystery Room");
        questRoom.setQuestAddress("789 Oak St");
        questRoom.setRoomCapacity("6");
        questRoom.setPrice(120.0f);
        questRoom.setServiceable(true);

        QuestRoom savedRoom = questRoomRepository.save(questRoom);
        questRoomRepository.delete(savedRoom);

        Optional<QuestRoom> deletedRoom = questRoomRepository.findById(savedRoom.getQuestId());
        assertThat(deletedRoom).isEmpty();
    }
}
