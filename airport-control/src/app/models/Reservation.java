package app.models;

import java.time.LocalDateTime;

public class Reservation {
    private Passenger passenger;
    private String flightNumber; // referenciamos apenas o número do voo
    private LocalDateTime requestTime;
    private boolean confirmed; // se o admin confirmou

    public Reservation(Passenger passenger, String flightNumber) {
        this.passenger = passenger;
        this.flightNumber = flightNumber;
        this.requestTime = LocalDateTime.now();
        this.confirmed = false;
    }

    public Passenger getPassenger() { return passenger; }
    public String getFlightNumber() { return flightNumber; }
    public LocalDateTime getRequestTime() { return requestTime; }
    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }

    @Override
    public String toString() {
        return String.format("Reserva: %s | Voo: %s | Pendente: %s",
                passenger.getName(), flightNumber, !confirmed);
    }
}
