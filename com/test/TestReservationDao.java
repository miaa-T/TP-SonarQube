package com.test;

import com.example.dao.*;
import com.example.entity.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestReservationDao {

    static DatabaseConnection databaseConnection;
    static Connection connection;
    static ReservationDao reservationDao ;


    @BeforeAll
    public static void init () {
        databaseConnection = new DatabaseConnection("root","","org.h2.Driver","jdbc:h2:mem:test");
        connection = databaseConnection.connect();
        databaseConnection.createDb(connection);
        // insert Parking
        Parking parking = new Parking();
        parking.setName("Parking 1");
        parking.setAddress("Alger");
        parking.setCapacity(30);
        ParkingDao parkingDao = new ParkingDao();
        parkingDao.setConn(connection);
        parkingDao.insertParking(parking);
        // insert Parking Place
        ParkingPlaceDao parkingPlaceDao = new ParkingPlaceDao();
        parkingPlaceDao.setConn(connection);
        ParkingPlace parkingPlace = new ParkingPlace();
        parking.setParkingId(1);
        parkingPlace.setParking(parking);
        parkingPlace.setPlaceName("F5");
        parkingPlace.setPlaceStatus(PlaceStatus.AVAILABLE);
        parkingPlaceDao.insertParkingPlace(parkingPlace);
        // insert user
        UserDao userDao = new UserDao();
        userDao.setConn(connection);
        User user = new User();
        user.setName("Ali");
        user.setPhone("055555555");
        user.setEmail("ali@gmail.com");
        userDao.insertUser(user);
    }


    @Test
    @Order(1)
    void testAddAndGet() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        // insert Reservation
         reservationDao = new ReservationDao();
        reservationDao.setConn(connection);
        Reservation reservation = new Reservation();
        ParkingPlace parkingPlace = new ParkingPlace();
        parkingPlace.setIdPlace(1);
        User user = new User();
        user.setUserId(1);
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setUser(user);
        reservation.setParkingPlace(parkingPlace);
        reservation.setStartTime(dateFormat.parse("22-10-2023 10:00"));
        reservation.setEndTime(dateFormat.parse("22-10-2023 11:45"));
        reservationDao.insertReservation(reservation);
        // get Reservation
        Reservation expectedReservation = reservationDao.getReservationById(1);
        assertEquals(1,expectedReservation.getReservationId());


    }


    @Test
    @Order(2)
    void testUpdateStatus() throws ParseException {
        reservationDao.updateReservationStatus(1,ReservationStatus.CONFIRMED);
        // get Reservation
        Reservation expectedReservation = reservationDao.getReservationById(1);
        assertEquals("CONFIRMED",expectedReservation.getStatus().name());
    }


/*
    @Test
    @Order(3)
    void testRemove()  {
        reservationDao.removeReservationById(1);
        // get Reservation
        Reservation expectedReservation = reservationDao.getReservationById(1);
        assertNull(expectedReservation);

    }
*/
    @AfterAll
    public static void close() {
        databaseConnection.disconnect(connection);
    }


}