package Evenement;

import Evenement.Evenement;
import simulation.*;
import robot.*;

public class EvenementRemplir extends Evenement {
    
    Robot robot;

    public EvenementRemplir(int date, Robot robot) {
        super(date);
        this.robot = robot;
    }

    @Override
    public void execute() {
        robot.remplirReservoir();
        System.out.println("Le robot est rempli : Quantité d'eau = " + robot.getEauReservoir());
    }
    
}
