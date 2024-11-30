package com;

import com.example.dao.*;
import com.example.entity.*;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;

public class DataInserter {
    public static void insertParking(Connection connection, String name, String address, int capacity) throws Exception {
        Parking parking = new Parking();
        parking.setName(name);
        parking.setAddress(address);
        parking.setCapacity(capacity);
        ParkingDao parkingDao = new ParkingDao();
        parkingDao.setConn(connection);
        parkingDao.insertParking(parking);
    }

    public static void insertParkingPlace(Connection connection, int idPlace, int parkingId, String placeName, PlaceStatus status) throws Exception {
        ParkingPlace parkingPlace = new ParkingPlace();
        parkingPlace.setIdPlace(idPlace);
        Parking parking = new Parking();
        parking.setParkingId(parkingId);
        parkingPlace.setParking(parking);
        parkingPlace.setPlaceName(placeName);
        parkingPlace.setPlaceStatus(status);
        ParkingPlaceDao parkingPlaceDao = new ParkingPlaceDao();
        parkingPlaceDao.setConn(connection);
        parkingPlaceDao.insertParkingPlace(parkingPlace);
    }

    public static void insertUser(Connection connection, int userId, String name, String phone, String email) throws Exception {
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        UserDao userDao = new UserDao();
        userDao.setConn(connection);
        userDao.insertUser(user);
    }

    public static void insertReservation(Connection connection, int userId, int placeId, String startTime, String endTime, ReservationStatus status) throws Exception {
        Reservation reservation = new Reservation();
        UserDao userDao = new UserDao();
        ParkingPlaceDao placeDao = new ParkingPlaceDao();
        userDao.setConn(connection);
        placeDao.setConn(connection);

        reservation.setUser(userDao.getUserById(userId));
        reservation.setParkingPlace(placeDao.getParkingPlaceById(placeId));
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        reservation.setStartTime(dateFormat.parse(startTime));
        reservation.setEndTime(dateFormat.parse(endTime));
        reservation.setStatus(status);

        ReservationDao reservationDao = new ReservationDao();
        reservationDao.setConn(connection);
        reservationDao.insertReservation(reservation);
    }
}
