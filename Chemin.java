
import Evenement.*;
import java.util.ArrayList;
import robot.*;
import simulation.*;

public class Chemin {

    private int dateDebut;
    private ArrayList<Evenement> chemin;

    public Chemin(int dateDebut) {
        this.dateDebut = dateDebut;
        this.chemin = new ArrayList<>(0);
    }

    public int getDateDebut() {
        return dateDebut;
    }

    public ArrayList<Evenement> getChemin() {
        return chemin;
    }

    public void setDateDebut(int dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setChemin(ArrayList<Evenement> chemin) {
        this.chemin = chemin;
    }

    public void calculerChemin(Robot robot, Case destination) {
        int dateCour = dateDebut;
        System.out.println("CALCUL ...");
        while (robot.getPostion().getLigne() != destination.getLigne() || robot.getColonne() != destination.getColonne()) {
            for (EnumDirection dir : EnumDirection.values()) {
                if (robot.deplacementPossible(dir)) {
                    System.out.println("DEPLACEMENT POSSIBLE -> (" + robot.getLigne() + ";" + robot.getColonne() + ")");
                    dateCour++;
                    robot.deplacer(dir);
                    EvenementDeplacementRobot cheminElementaire = new EvenementDeplacementRobot(dateDebut, robot, dir);
                    this.chemin.add(cheminElementaire);
                    break;
                }
            }
        }
    }

}
