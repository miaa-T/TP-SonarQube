package com.example.service;

import com.example.dao.PaymentDao;
import com.example.entity.Payment;
import com.example.entity.Reservation;
import com.example.entity.ReservationStatus;

public class PaymentManager {

    PaymentDao paymentDao;
    ReservationManager reservationManager;

    public void addPayment(Payment payment) {
        int reservationId = payment.getReservation().getReservationId();
        if(payment.getReservation().getStatus() == ReservationStatus.PENDING) {
            paymentDao.insertPayment(payment);
            reservationManager.updateReservationStatus(reservationId,ReservationStatus.CONFIRMED);
        }
    }



}
