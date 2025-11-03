package app.models;

public class Flight {
    private String flightNumber; // deve ter exatamente 4 dígitos
    private String origin;
    private String destination;
    private String departureTime; // formato simples (ex: "10:30")
    private String arrivalTime;
    private int capacity;
    private int confirmedPassengers; // número atual de passageiros confirmados

    public Flight(String flightNumber, String origin, String destination,
                  String departureTime, String arrivalTime, int capacity) {
        if (!flightNumber.matches("\\d{4}")) {
            throw new IllegalArgumentException("Número do voo deve ter exatamente 4 dígitos.");
        }
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.capacity = capacity;
        this.confirmedPassengers = 0;
    }

    // Getters e setters essenciais
    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public int getCapacity() { return capacity; }
    public int getConfirmedPassengers() { return confirmedPassengers; }

    // Aumenta o contador de passageiros confirmados (retorna true se teve sucesso)
    public boolean addConfirmedPassenger() {
        if (confirmedPassengers < capacity) {
            confirmedPassengers++;
            return true;
        }
        return false; // voo cheio
    }

    // Remove um passageiro confirmado (útil se cancelar)
    public boolean removeConfirmedPassenger() {
        if (confirmedPassengers > 0) {
            confirmedPassengers--;
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return confirmedPassengers >= capacity;
    }

    @Override
    public String toString() {
        return String.format("Voo %s | %s -> %s | Partida: %s | Chegada: %s | %d/%d",
                flightNumber, origin, destination, departureTime, arrivalTime,
                confirmedPassengers, capacity);
    }
}
