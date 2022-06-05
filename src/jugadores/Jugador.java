package jugadores;

import java.util.ArrayList;
import java.io.Serializable;

public class Jugador implements Serializable{

    private String nombre;
    private String apellido;
    private float ppp; // Puntos Por Partido [PPP]
    private float nota;
    private float salario;

    // Full construct
    public Jugador(final String nombre,
                   final String apellido,
                   final float ppp,
                   final float nota,
                   final float salario) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.ppp = ppp;
        this.nota = nota;
        this.salario = salario;
    }

    // Setters
    public void setNombre(String nombre){ this.nombre = nombre; }
    public void setApellido(String apellido){this.apellido = apellido; }
    public void setPPP(float ppp){this.ppp = ppp; }
    public void setNota(float nota){this.nota = nota;}
    public void setSalario(float salario){this.salario = salario;}
    // Getters
    public String getNombre(){return this.nombre;}
    public String getApellido(){return this.apellido;}
    public float getPPP(){return this.ppp;}
    public float getNota(){return this.nota;}
    public float getSalario(){return this.salario;}

    @Override
    public String toString(){
        return "Nombre: " + nombre + apellido + "\n"
                + ", PPP: " + ppp + ", Nota: " + nota + ", Salario: " + salario
                + "\n";
    }

    public static String jugadoresToString(final ArrayList<Jugador> jugadores) {

        String stringJugadores = "Lista de jugadores";
        for(Jugador jugador : jugadores) { stringJugadores += jugador.toString();}

        return stringJugadores;
    }
}
