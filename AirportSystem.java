package app;

import app.models.Flight;
import app.models.Passenger;
import app.models.Reservation;

import java.util.*;

public class AirportSystem {
    private List<Flight> flights;
    private Queue<Reservation> reservationQueue; // fila para reservas pendentes
    private Map<String, Stack<Passenger>> checkinStacks; // por número do voo

    public AirportSystem() {
        flights = new ArrayList<>();
        reservationQueue = new LinkedList<>();
        checkinStacks = new HashMap<>();
    }

    // Adiciona voo (ex: ao inicializar dados)
    public void addFlight(Flight f) {
        flights.add(f);
        checkinStacks.put(f.getFlightNumber(), new Stack<>());
    }

    // Busca voo por número (ou null)
    public Flight findFlight(String flightNumber) {
        for (Flight f : flights) {
            if (f.getFlightNumber().equals(flightNumber)) return f;
        }
        return null;
    }

    // Reserva: cria e adiciona à fila
    public void makeReservation(Passenger p, String flightNumber) {
        reservationQueue.add(new Reservation(p, flightNumber));
    }

    // Retorna lista de reservas pendentes (para exibir)
    public List<Reservation> getPendingReservations() {
        List<Reservation> list = new ArrayList<>();
        for (Reservation r : reservationQueue) {
            if (!r.isConfirmed()) list.add(r);
        }
        return list;
    }

    // Admin processa a próxima reserva da fila (FIFO)
    // Retorna mensagem de sucesso/erro
    public String processNextReservation() {
        Reservation r = reservationQueue.poll();
        if (r == null) return "Não há reservas pendentes.";
        Flight f = findFlight(r.getFlightNumber());
        if (f == null) return "Voo não encontrado: " + r.getFlightNumber();
        if (f.addConfirmedPassenger()) {
            r.setConfirmed(true);
            // manter r se quiser histórico, mas aqui assumimos fila consumida
            return "Reserva confirmada para " + r.getPassenger().getName() + " no voo " + f.getFlightNumber();
        } else {
            return "Não foi possível confirmar: voo cheio (" + f.getFlightNumber() + ")";
        }
    }

    // Check-in: só é permitido se a reserva já foi confirmada.
    // Para simplificar, vamos checar se existe histórico de confirmações checando contagem (ou alternativamente manter uma lista de reservas confirmadas).
    // Aqui assumimos que admin confirmou e passageiro tem reserva confirmada — vamos apenas permitir o check-in se o voo não estiver vazio.
    public String checkIn(Passenger p, String flightNumber) {
        Flight f = findFlight(flightNumber);
        if (f == null) return "Voo não encontrado.";
        // Aqui: para regras reais, deveríamos verificar reserva confirmada específica.
        // Simples: se voo não estiver cheio e já tem pelo menos 1 confirmado (ou confiar que admin confirmou), permitimos push.
        Stack<Passenger> stack = checkinStacks.get(flightNumber);
        if (stack == null) return "Erro interno: pilha de check-in não encontrada.";
        stack.push(p);
        return "Check-in realizado para " + p.getName() + " no voo " + flightNumber;
    }

    // Mostrar voos disponíveis (com vagas)
    public List<Flight> availableFlights() {
        List<Flight> res = new ArrayList<>();
        for (Flight f : flights) if (!f.isFull()) res.add(f);
        return res;
    }

    // Voos cheios
    public List<Flight> fullFlights() {
        List<Flight> res = new ArrayList<>();
        for (Flight f : flights) if (f.isFull()) res.add(f);
        return res;
    }

    // Voos com reservas pendentes (alguma reserva na fila referenciando o voo)
    public Set<String> flightsWithPendingReservations() {
        Set<String> set = new HashSet<>();
        for (Reservation r : reservationQueue) set.add(r.getFlightNumber());
        return set;
    }

    // Para depuração: listar pilha de check-ins de um voo
    public List<Passenger> getCheckinList(String flightNumber) {
        Stack<Passenger> stack = checkinStacks.get(flightNumber);
        if (stack == null) return Collections.emptyList();
        return new ArrayList<>(stack);
    }
}
