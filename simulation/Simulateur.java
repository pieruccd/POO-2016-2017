package simulation;

import Evenement.Evenement;
import robot.*;
import gui.*;
import java.awt.Color;
import java.util.ArrayList;

public final class Simulateur implements Simulable {

    private GUISimulator gui;
    private DonneesSimulation donnees;
    private int tailleCase = 80;
    private int dateSimulation = 0; // Par défaut on se met à la date 0
    private ArrayList<Evenement> listeEvenements;

    public Simulateur(GUISimulator gui, DonneesSimulation donnees, ArrayList<Evenement> listeEvenements) {
        this.gui = gui;
        this.donnees = donnees;
        gui.setSimulable(this);
        this.listeEvenements = listeEvenements;
        drawMap();
    }

    public void addEvenement(Evenement e) {
        listeEvenements.add(e);
    }

    public void incrementeDate() {
        System.out.println("On incrémente !");
        System.out.println("Date Actuelle : " + this.dateSimulation);

        for (int i = 0; i < listeEvenements.size(); i++) {
            if (listeEvenements.get(i).date == dateSimulation) {
                listeEvenements.get(i).execute();
                System.out.println("MAJ de la carte ...");
                this.drawMap();
            }
        }

        this.dateSimulation++;
    }

    public boolean simulationTerminee() {
        for (int i = 0; i < listeEvenements.size(); i++) {
            if (listeEvenements.get(i).date >= dateSimulation) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void next() {
        if (!simulationTerminee()) {
            System.out.println("Next !");
            incrementeDate();
        }
    }

    @Override
    public void restart() {
        System.out.println("Restart");
        dateSimulation = 0;
        // QCH A FAIRE ICI
        drawMap();
        System.out.println("Carte remis en configuration initiale");
    }

    public void drawMap() {
        //gui.reset();
        //gui.setSize(tailleCase * donnees.getCarte().getNbColonnes(), tailleCase * donnees.getCarte().getNbLignes());

        int xOffset = tailleCase / 2;
        int yOffset = tailleCase / 2;
        int xCour = xOffset;
        int yCour = yOffset;

        /* Dessin des rectangles représentant une case */
        for (int i = 0; i < donnees.getCarte().getNbColonnes(); i++) {
            xCour = xOffset;
            for (int j = 0; j < donnees.getCarte().getNbLignes(); j++) {
                gui.addGraphicalElement(new Rectangle(xCour, yCour, Color.BLACK, Color.WHITE, tailleCase, tailleCase));
                switch (donnees.getCarte().getCase(i, j).getNature()) {
                    case EAU:
                        gui.addGraphicalElement(new Text(xCour, yCour, Color.BLACK, "EAU"));
                        break;
                    case FORET:
                        gui.addGraphicalElement(new Text(xCour, yCour, Color.BLACK, "FORET"));
                        break;
                    case HABITAT:
                        gui.addGraphicalElement(new Text(xCour, yCour, Color.BLACK, "HABITAT"));
                        break;
                    case ROCHE:
                        gui.addGraphicalElement(new Text(xCour, yCour, Color.BLACK, "ROCHE"));
                        break;
                    case TERRAIN_LIBRE:
                        gui.addGraphicalElement(new Text(xCour, yCour, Color.BLACK, "TERRAIN_LIBRE"));
                        break;
                }
                gui.addGraphicalElement(new Text(xCour, yCour + 10, Color.BLACK, i + "," + j));
                //gui.addGraphicalElement(new ImageElement(xCour, yCour, "..\\..\\images\\foret.gif", tailleCase, tailleCase, gui));
                xCour = xCour + tailleCase;
            }
            yCour = yCour + tailleCase;
        }

        for (int i = 0; i < donnees.getIncendies().size(); i++) {
            if (donnees.getIncendies().get(i).getNbLitres() != 0) {
                gui.addGraphicalElement(new Text(donnees.getIncendies().get(i).getCase().getColonne() * tailleCase + xOffset,
                        donnees.getIncendies().get(i).getCase().getLigne() * tailleCase + 20 + yOffset, Color.RED, "I ="
                        + donnees.getIncendies().get(i).getNbLitres()));
            }
        }

        for (int i = 0; i < donnees.getRobots().size(); i++) {
            gui.addGraphicalElement(new Text(donnees.getRobots().get(i).getColonne() * tailleCase + xOffset,
                    donnees.getRobots().get(i).getLigne() * tailleCase - 20 + yOffset, Color.BLUE, "Robot"));
        }

    }

}
