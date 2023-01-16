import java.util.ArrayList;
import java.util.List;

public class Taula {
    //atr
    private int id;
    private int numCad;
    private int reserva;
    private boolean terrasa;
    private double benifici;

    //Constructor
    public Taula(int id, int numCad, boolean terrasa) {
        this.id = id;
        this.numCad = numCad;
        this.terrasa = terrasa;
        if (terrasa){this.benifici=10.5;}else{this.benifici=9.5;}//en base de la taxes de terrasa o no
    }


    public double obtenerBeneficios (){
        return this.getReserva()*getBenifici();
    }

    //GETTER AND SETTERS
    public int getId() {
        return id;
    }

    public int getNumCad() {
        return numCad;
    }


    public boolean isTerrasa() {
        return terrasa;
    }


    public double getBenifici() {
        return benifici;
    }



    public int getReserva() {
        return reserva;
    }

    protected void setReserva(int reserva) {
        this.reserva = reserva;
    }



}
