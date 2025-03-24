package com.example.pr1;

import com.example.pr1.models.QuestRoom;
import com.example.pr1.repositories.ReservationRepository;
import com.example.pr1.models.Reservation;
import com.example.pr1.services.QuestRoomService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
class Pr1ApplicationTests {

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
			"postgres:16-alpine"
	);

	@BeforeAll
	static void beforeAll() {
		postgres.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	QuestRoomService questRoomService;


	@Test
	void testSaveReservation() {
		QuestRoom questRoom = new QuestRoom();
		questRoom.setQuestName("Quest");
		questRoom.setQuestAddress("123 st");
		questRoom.setRoomCapacity("5");
		questRoom.setPrice(500f);
		questRoom.setServiceable(true);
		questRoomService.create(questRoom);

		Reservation r1 = new Reservation();
		r1.setQuestName("John Doe");
		r1.setPhoneNumber("1234567890");
		r1.setQuestDate(LocalDate.now());
		r1.setStartTime(LocalTime.of(10, 0));
		r1.setEndTime(LocalTime.of(12, 0));
		r1.setQuestRoom(questRoom);

		Reservation r1saved = reservationRepository.save(r1);

		assertThat(r1saved).isNotNull();
		assertThat(r1saved.getReservId()).isNotNull();
		assertThat(reservationRepository.findById(r1saved.getReservId())).isPresent();
	}

	@Test
	void testDeleteAllReservation() {
		QuestRoom questRoom = new QuestRoom();
		questRoom.setQuestName("Quest");
		questRoom.setQuestAddress("123 st");
		questRoom.setRoomCapacity("5");
		questRoom.setPrice(500f);
		questRoom.setServiceable(true);
		questRoomService.create(questRoom);

		Reservation r1 = new Reservation();
		r1.setQuestName("John Doe");
		r1.setPhoneNumber("1234567890");
		r1.setQuestDate(LocalDate.now());
		r1.setStartTime(LocalTime.of(10, 0));
		r1.setEndTime(LocalTime.of(12, 0));
		r1.setQuestRoom(questRoom);

		Reservation r1saved = reservationRepository.save(r1);

		assertThat(r1saved).isNotNull();
		assertThat(r1saved.getReservId()).isNotNull();

		Reservation r2 = new Reservation();
		r2.setQuestName("Jane Doe");
		r2.setPhoneNumber("0000000");
		r2.setQuestDate(LocalDate.now());
		r2.setStartTime(LocalTime.of(17, 0));
		r2.setEndTime(LocalTime.of(18, 0));
		r2.setQuestRoom(questRoom);

		Reservation r2saved = reservationRepository.save(r1);

		assertThat(r2saved).isNotNull();
		assertThat(r2saved.getReservId()).isNotNull();

		assertEquals(reservationRepository.findAll().size(), 2);

		reservationRepository.deleteAll();

		assertThat(reservationRepository.findAll()).isEmpty();
	}

}
