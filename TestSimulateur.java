
import Evenement.*;
import simulation.*;
import gui.*;
import java.awt.Color;
import java.util.ArrayList;

public class TestSimulateur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(1000, 1000, Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        DonneesSimulation testDonneesSimulation = new DonneesSimulation(" .." + "/" + ".." + "/" + "cartes" + "/" + "carteSujet.map");
        
        EvenementDeplacementRobot testEvenement1 = new EvenementDeplacementRobot(0, testDonneesSimulation.getRobots().get(1), EnumDirection.NORD);
        EvenementDeverserEau testEvenement2 = new EvenementDeverserEau(1, testDonneesSimulation.getIncendies().get(4), testDonneesSimulation.getRobots().get(1));
        EvenementDeplacementRobot testEvenement3 = new EvenementDeplacementRobot(2, testDonneesSimulation.getRobots().get(1), EnumDirection.OUEST);
        EvenementDeplacementRobot testEvenement4 = new EvenementDeplacementRobot(3, testDonneesSimulation.getRobots().get(1), EnumDirection.OUEST);
        EvenementRemplir testEvenement5 = new EvenementRemplir(4, testDonneesSimulation.getRobots().get(1));
        EvenementDeplacementRobot testEvenement6 = new EvenementDeplacementRobot(5, testDonneesSimulation.getRobots().get(1), EnumDirection.EST);
        EvenementDeplacementRobot testEvenement7 = new EvenementDeplacementRobot(6, testDonneesSimulation.getRobots().get(1), EnumDirection.EST);
        EvenementDeverserEau testEvenement8 = new EvenementDeverserEau(7, testDonneesSimulation.getIncendies().get(4), testDonneesSimulation.getRobots().get(1));
        
        //Chemin cheminTo11 = new Chemin(9);
        //cheminTo11.calculerChemin(testDonneesSimulation.getRobots().get(0), testDonneesSimulation.getCarte().getCase(1, 1));
        
        ArrayList<Evenement> listeEvenements = new ArrayList<>();
        listeEvenements.add(testEvenement1);
        listeEvenements.add(testEvenement2);
        listeEvenements.add(testEvenement3);
        listeEvenements.add(testEvenement4);
        listeEvenements.add(testEvenement5);
        listeEvenements.add(testEvenement6);
        listeEvenements.add(testEvenement7);
        listeEvenements.add(testEvenement8);
        //System.out.println(cheminTo11.getChemin());
        //System.out.println(listeEvenements.toString());
        
        Simulateur simulateur = new Simulateur(gui, testDonneesSimulation ,listeEvenements);
    }

}
