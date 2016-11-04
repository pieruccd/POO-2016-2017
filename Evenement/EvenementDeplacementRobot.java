package Evenement;

import Evenement.Evenement;
import simulation.*;
import robot.*;

public class EvenementDeplacementRobot extends Evenement {
    
    Robot robot;
    EnumDirection direction;

    public EvenementDeplacementRobot(int date, Robot robot, EnumDirection direction) {
        super(date);
        this.robot = robot;
        this.direction = direction;
    }

    @Override
    public void execute() {
        robot.deplacer(direction);
    }
    
    
    
    
    
}
