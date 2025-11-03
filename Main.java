package app;

import app.models.Flight;
import app.models.Passenger;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final String ADMIN_PASSWORD = "admin123"; // alterar para produção

    public static void main(String[] args) {
        AirportSystem system = new AirportSystem();
        seedFlights(system);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Sistema de Controle de Aeroporto ===");
            System.out.println("1 - Reservar voo");
            System.out.println("2 - Fazer check-in");
            System.out.println("3 - Mostrar voos disponíveis");
            System.out.println("4 - Mostrar voos com reservas pendentes");
            System.out.println("5 - Mostrar voos cheios");
            System.out.println("6 - Área do administrador (processar reservas)");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            String opt = sc.nextLine().trim();

            switch (opt) {
                case "1":
                    handleReservation(system, sc);
                    break;
                case "2":
                    handleCheckin(system, sc);
                    break;
                case "3":
                    List<Flight> available = system.availableFlights();
                    if (available.isEmpty()) System.out.println("Nenhum voo com vagas.");
                    else available.forEach(System.out::println);
                    break;
                case "4":
                    Set<String> pending = system.flightsWithPendingReservations();
                    if (pending.isEmpty()) System.out.println("Nenhum voo com reservas pendentes.");
                    else {
                        System.out.println("Voos com reservas pendentes: " + pending);
                    }
                    break;
                case "5":
                    List<Flight> full = system.fullFlights();
                    if (full.isEmpty()) System.out.println("Nenhum voo cheio.");
                    else full.forEach(System.out::println);
                    break;
                case "6":
                    System.out.print("Senha do administrador: ");
                    String pass = sc.nextLine().trim();
                    if (!pass.equals(ADMIN_PASSWORD)) {
                        System.out.println("Senha incorreta.");
                    } else {
                        System.out.println(system.processNextReservation());
                    }
                    break;
                case "0":
                    System.out.println("Saindo...");
                    sc.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void seedFlights(AirportSystem system) {
        system.addFlight(new Flight("2904", "São Paulo", "Rio de Janeiro", "09:00", "10:00", 3));
        system.addFlight(new Flight("1234", "Brasília", "Fortaleza", "11:30", "14:00", 2));
        system.addFlight(new Flight("4321", "Recife", "Salvador", "15:00", "16:30", 1));
    }

    private static void handleReservation(AirportSystem system, Scanner sc) {
        System.out.print("Número do voo (4 dígitos): ");
        String num = sc.nextLine().trim();
        if (!num.matches("\\d{4}")) {
            System.out.println("Número inválido. Deve ter 4 dígitos.");
            return;
        }
        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        System.out.print("Idade: ");
        int idade;
        try { idade = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Idade inválida."); return; }
        System.out.print("CPF (apenas números): ");
        String cpf = sc.nextLine().trim();
        System.out.print("E-mail: ");
        String email = sc.nextLine().trim();
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("E-mail inválido.");
            return;
        }
        // Cria passageiro e adiciona reserva na fila
        Passenger p = new Passenger(nome, idade, cpf, email);
        system.makeReservation(p, num);
        System.out.println("Reserva registrada e adicionada à fila de pendentes.");
    }

    private static void handleCheckin(AirportSystem system, Scanner sc) {
        System.out.print("Número do voo para check-in: ");
        String num = sc.nextLine().trim();
        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        System.out.print("Idade: ");
        int idade;
        try { idade = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Idade inválida."); return; }
        System.out.print("CPF: ");
        String cpf = sc.nextLine().trim();
        System.out.print("E-mail: ");
        String email = sc.nextLine().trim();
        Passenger p = new Passenger(nome, idade, cpf, email);
        System.out.println(system.checkIn(p, num));
    }
}
