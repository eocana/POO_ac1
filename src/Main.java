import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static void main (String[] args){

        /*Creación de variables*/
        boolean menu = false;
        boolean isValid = false;
        int option = 0;
        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner

        RestaurantManager.generarRestaurant(); //Llegim i guardem totes les taules per a que ja podem operar amb elles

        /*MENU Y OPCIONS*/

        do {
            //ens assegurem que l'usuari sempre escrigui un numero entre el 1~4
            while (!isValid) {
                System.out.println("--------");
                System.out.println("1. Mostra restaurant");
                System.out.println("2. Fes reserva");
                System.out.println("3. Mostra beneficis");
                System.out.println("4. Sortir");
                System.out.println("--------");

                try {
                    option = entradaEscaner.nextInt();
                    isValid = true;
                }
                catch (InputMismatchException ex) {
                    System.out.println("Error: ingresa un numero, entre 1~4!");
                }
            }

            isValid=false;

            if (option >= 1 && option <=4){

                //funcions de la classe RestaurantManager
                switch (option) {
                    case 1 -> RestaurantManager.dibujarRestaurante();
                    case 2 -> RestaurantManager.reservar();
                    case 3 -> RestaurantManager.mostrarBeneficios();
                    case 4 -> menu = true;
                    default -> System.out.println("ERROR: Revisar el If option");
                }
            }else{
                System.out.println("Error: escriu un numero entre 1~4!");
            }

        }while(!menu);
    }





}
