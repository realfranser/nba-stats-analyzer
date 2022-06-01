package Jugadores;

public class Jugador {

    String nombre;
    String apellido;
    float ppp; // Puntos Por Partido [PPP]
    float nota;

    // Empty construct
    public Jugador(){
    }
    // Full construct
    public Jugador(String nombre, String apellido, float ppp, float media){

        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setPPP(ppp);
        this.setNota(media);
    }

    // Setters
    public void setNombre(String nombre){ this.nombre = nombre; }
    public void setApellido(String apellido){this.apellido = apellido; }
    public void setPPP(float ppp){this.ppp = ppp; }
    public void setNota(float nota){this.nota = nota;}
    // Getters
    public String getNombre(){return this.nombre;}
    public String getApellido(){return this.apellido;}
    public float getPPP(){return this.ppp;}
    public float getNota(){return this.nota;}

    @Override
    public String toString(){
        return "Nombre: "+nombre+", Apellido: "+ apellido + ", PPP: "+ ppp+ ", Nota: "+ nota;
    }
}
