package app.models;

public class Passenger {
    private String name;
    private int age;
    private String cpf;
    private String email;

    public Passenger(String name, int age, String cpf, String email) {
        this.name = name;
        this.age = age;
        this.cpf = cpf;
        this.email = email;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("%s (idade: %d, CPF: %s, e-mail: %s)", name, age, cpf, email);
    }
}
