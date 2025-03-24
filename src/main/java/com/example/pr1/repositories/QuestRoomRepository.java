package com.example.pr1.repositories;

import com.example.pr1.models.QuestRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestRoomRepository extends JpaRepository<QuestRoom, UUID> {}
