import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class RestaurantManager {

    private static final List<Taula> listTaules = new ArrayList<Taula>();

    /**
     * Funció que demana a l'usuari que digui el nom del restaurant i el busca dins de la carpeta restaurants
     * @return retorna una array de strings amb totes les taules ja separades correctament
     */
    private static String[] llegirArxiu(){

        String restaurantRAW = "";
        File file;
        Scanner inPath = new Scanner (System.in); //Creación de un objeto Scanner
        boolean isValid = false;
        String path = "../ac1_ericocana/restaurants/"; //Ja posem per defecte el principi de la ruta

        while(!isValid){
            try {
                System.out.println("Especifica el nom del resturant: ");

                path += inPath.nextLine ();

                file = new File(path+=".txt");
                Scanner obj = new Scanner(file);
                while (obj.hasNextLine()) restaurantRAW=obj.nextLine(); //Guardem en un string per després tractarlo

                isValid=true;

            } catch (FileNotFoundException e) {

                path = "../POO/restaurants/"; //tornem a iniciar de nou el path
                System.out.println("Error: escriu bé el nom del restaurant");

            }
        }
        return restaurantRAW.split(" ");
    }

    /**
     * Funció que genera totes las Taules(Object) i les agrega a una arrayList para a que sigui més fácil d'operar
     */
    public static void generarRestaurant(){
        String[] taulesRAW = llegirArxiu();
        int id = 0, c = 0, i=0;
        boolean terrasa = false;

        for(String item : taulesRAW){

            String[] taula = item.split(":"); //dividim en dos en base ":" per després tractar millor la part de la terrassa
            /* la informació estara dividida en dos, per tant una taula equival a dos pos del String[] taula
             * per exemple taula[0]=id, taula[1]=cadires i terrassa si hi ha*/

            for (String t : taula) {
                switch (i) {
                    case 0: id = Integer.parseInt(t);
                        break;
                    case 1: //Mirem si te terressa o no
                        if (!(t.endsWith("T"))){
                            c = Integer.parseInt(t);
                            terrasa = false;
                        }else{
                            String[] r = t.split("-"); //separem si té - sino directament guardem les cadires
                            for (int j = 0; j < r.length ; j++) {
                                if (j == 0){c = Integer.parseInt(r[j]);}
                                if (j == 1){if (r[j].equals("T")){terrasa=true;}}
                            }
                        }
                        break;
                    default: System.out.println("Error: algo ha salido mal al dividir las mesas");
                        break;
                }
                i++;
            }
            listTaules.add(new Taula(id,c,terrasa));
            i=0;
        }
    }

    /**
     * Funció que mostra els beneficis del restaurant
     * Operació: numero de clients x tasa si hi ha terressa o no
     */
    public static void mostrarBeneficios(){

        double benefecio = 0.0;

        for (Taula a: listTaules) {
            benefecio+= a.obtenerBeneficios();
        }

        System.out.println("Beneficis: "+benefecio);
    }


    /**
     * Funció que dibuixa l'estat de las tauless del restaurant
     */
    public static void dibujarRestaurante(){
        String aux="";
        for (Taula a: listTaules) {
            if (a.isTerrasa()){aux += "*";}

            aux += a.getId() +": ";
            for (int i = 0; i < a.getReserva(); i++) {
                aux+="o";
            }
            for (int j = 0; j < a.getNumCad()-a.getReserva(); j++) {
                aux+="-";
            }
            System.out.println(aux);
            aux="";
        }
    }

    /**
     * Aquesta funció s'encarrega de gestionar la reserva dels clients on es comproba
     */
    public static void reservar() {

        boolean correcte = false;
        String input = "err";
        boolean terrasa = false;
        int clients=0;
        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner

        //preguntem al usuari quantes persones son i ens assegurem que sigui un numero
        do {
            if (isNumeric(input)){
                clients = Integer.parseInt(input);
                correcte = true;
            }else {
                System.out.println("Quantes persones?");
                input = entradaEscaner.nextLine();

            }
        }while(!correcte);

        correcte = false;

        //preguntem al usuari si vol terrassa o no comprobant que ho posi correctament
        do {
            if (input.equalsIgnoreCase("s") || input.equalsIgnoreCase("n")){
                if (input.equalsIgnoreCase("s")){terrasa = true;}
                correcte = true;
            }else{
                System.out.println("Volen terrrassa?(s/n)");
                input = entradaEscaner.nextLine();
            }

        }while (!correcte);


        /*Les ordeno en base el numero de cadires per després alhora de fer reserves m'agafi sempre la taula més
          petita (sempre i quan no estiguin reservades) per no reservar les taules grans*/
        listTaules.sort(Comparator.comparing(Taula::getNumCad));

        boolean reservat = false;

        for (int i = 0; i < listTaules.size(); i++) {

            /*Gestiona tot el tema de què la reserva es faci de forma correcta:
            *  Sempre assigni els clients a la taula on siguin justs per a no ocupar una taula de forma
            *  inncessaria (respectant primer si vol terrassa o no)
            *  En el segon que es reservi sempre que sigui correcte a nivell logic.*/

            if (clients != listTaules.get(i).getNumCad() || listTaules.get(i).isTerrasa() != terrasa || listTaules.get(i).getReserva() !=0) {
                if(listTaules.get(i).isTerrasa() == terrasa && listTaules.get(i).getNumCad() >= clients && listTaules.get(i).getReserva() ==0 ){
                    listTaules.get(i).setReserva(clients);
                    reservat = true;
                    System.out.println("Taula "+listTaules.get(i).getId()+" reservada per a "+clients+" persones");
                    i = listTaules.size();
                }
            } else {
                //System.out.println(terrasa);
                listTaules.get(i).setReserva(clients);
                reservat = true;
                System.out.println("Taula "+ listTaules.get(i).getId()+" reservada per a "+clients+" persones");
                i = listTaules.size();

            }
        }

        if (!reservat){
            System.out.println("No hi ha cap taula que compleixi amb aquests requeriments");
        }

        listTaules.sort(Comparator.comparing(Taula::getId)); //tornem a posar bé perquè es mostri ordenat bé
    }

    //Per comprobar si és un número el string que passem
    private static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {

            resultado = false;
        }

        return resultado;
    }
}
