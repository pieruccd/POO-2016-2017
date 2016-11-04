package Evenement;


import Evenement.Evenement;
import simulation.*;
import robot.*;

public class EvenementDeverserEau extends Evenement {

    Incendie incendie;
    Robot robot;

    public EvenementDeverserEau(int date, Incendie incendie, Robot robot) {
        super(date);
        this.incendie = incendie;
        this.robot = robot;
    }

    @Override
    public void execute() {
        if (incendie.getCase().equals(robot.getPostion())) {
            System.out.println("Incendie : " + incendie.getNbLitres());
            System.out.println("Robot : " + robot.getEauReservoir());
            System.out.println("On a versé : " + Math.min(robot.getEauReservoir(), incendie.getNbLitres()));
            int eauVersee = Math.min(robot.getEauReservoir(), incendie.getNbLitres());
            robot.deverserEau(eauVersee);
            this.incendie.recevoirEau(eauVersee);
            System.out.println("Intensité de l'incendie = " + incendie.getNbLitres());
        } else {
            throw new IllegalArgumentException("Le robot et l'incendie ne sont pas sur la même case : ARRET");
        }
    }

}
