package com.example.pr1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class QuestRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID questId;

    @Column(nullable = false)
    private String questName;

    @Column(nullable = false)
    private String questAddress;

    @Column(nullable = false)
    private String roomCapacity;

    @Column
    private String description;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private boolean isServiceable;

    @OneToMany(mappedBy = "questRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("startTime DESC")
    private List<Reservation> reservations;

}
