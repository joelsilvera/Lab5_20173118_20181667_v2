package com.example.lab5_20173118_20181667_v2;

public class Usuario {
    private String email;
    private String name;
    private String password;
    private String telefono;


    public Usuario(String email, String name, String password, String telefono) {
        this.setEmail(email);
        this.setName(name);
        this.setPassword(password);
        this.setTelefono(telefono);
    }

    public Usuario() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
