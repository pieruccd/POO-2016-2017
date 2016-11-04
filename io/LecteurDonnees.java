package io;

import simulation.Carte;
import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;
import robot.*;
import simulation.*;

/**
 * Lecteur de cartes au format spectifié dans le sujet. Les données sur les
 * cases, robots puis incendies sont lues dans le fichier, puis simplement
 * affichées. A noter: pas de vérification sémantique sur les valeurs numériques
 * lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher. A vous de modifier
 * ou d'ajouter des méthodes, inspirées de celles présentes (ou non), qui CREENT
 * les objets au moment adéquat pour construire une instance de la classe
 * DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues: public static DonneesSimulation
 * creeDonnees(String fichierDonnees); Et faire des méthode creeCase(),
 * creeRobot(), ... qui lisent les données, créent les objets adéquats et les
 * ajoutent ds l'instance de DonneesSimulation.
 */
public class LecteurDonnees {

    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases, robots et
     * incendies). Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     *
     * @param fichierDonnees nom du fichier à lire
     */
    public static DonneesSimulation lire(String fichierDonnees)
            throws FileNotFoundException, DataFormatException {
        DonneesSimulation donneesSimulationResultat = new DonneesSimulation();
        System.out.println("\n == Lecture du fichier" + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        donneesSimulationResultat.setCarte(lecteur.lireCarte());
        donneesSimulationResultat.setIncendies(lecteur.lireIncendies());
        donneesSimulationResultat.setRobots(lecteur.lireRobots(donneesSimulationResultat.getCarte()));
        scanner.close();
        System.out.println("\n == Lecture terminee");
        return donneesSimulationResultat;
    }

    // Tout le reste de la classe est prive!
    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     *
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
            throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et affiche les donnees de la carte.
     *
     * @throws ExceptionFormatDonnees
     */
    private Carte lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m
            Carte carteLue = new Carte(nbLignes, nbColonnes, tailleCases);
            System.out.println("Carte " + nbLignes + "x" + nbColonnes
                    + "; taille des cases = " + tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    carteLue.setCase(lireCase(lig, col));
                }
            }
            return carteLue;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }

    /**
     * Lit et affiche les donnees d'une case.
     */
    private Case lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature = new String();
        //		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            EnumNatureTerrain nature = EnumNatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();

            System.out.print("nature = " + chaineNature);
            Case caseLue = new Case(lig, col, nature);
            System.out.println();
            return caseLue;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

    }

    /**
     * Lit et affiche les donnees des incendies.
     */
    private ArrayList<Incendie> lireIncendies() throws DataFormatException {
        ignorerCommentaires();
        try {
            ArrayList<Incendie> listeIncendies = new ArrayList<>(0);
            int nbIncendies = scanner.nextInt();
            System.out.println("Nb d'incendies = " + nbIncendies);
            for (int i = 0; i < nbIncendies; i++) {
                listeIncendies.add(lireIncendie(i));
            }
            return listeIncendies;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }

    /**
     * Lit et affiche les donnees du i-eme incendie.
     *
     * @param i
     */
    private Incendie lireIncendie(int i) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            Case casRes = new Case(lig, col);
            Incendie res = new Incendie(casRes, intensite);
            verifieLigneTerminee();

            System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);
            return res;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }

    /**
     * Lit et affiche les donnees des robots.
     */
    private ArrayList<Robot> lireRobots(Carte map) throws DataFormatException {
        ignorerCommentaires();
        try {
            ArrayList<Robot> listeRobots = new ArrayList(0);
            int nbRobots = scanner.nextInt();
            System.out.println("Nb de robots = " + nbRobots);
            for (int i = 0; i < nbRobots; i++) {
                listeRobots.add(lireRobot(i, map));
            }
            return listeRobots;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }

    /**
     * Lit et affiche les donnees du i-eme robot.
     *
     * @param i
     */
    private Robot lireRobot(int i, Carte map) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Robot " + i + ": ");
        Robot res;

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            Case pos = new Case(lig, col);
            System.out.print("position = (" + lig + "," + col + ");");
            String type = scanner.next();

            System.out.print("\t type = " + type);

            // lecture eventuelle d'une vitesse du robot (entier)
            System.out.print("; \t vitesse = ");
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

            if (s == null) {
                System.out.print("valeur par defaut");
            } else {
                int vitesse = Integer.parseInt(s);
                System.out.print(vitesse);
            }
            verifieLigneTerminee();

            switch (type) {
                case "DRONE":
                    res = new Drone(map, pos, (s == null) ? 150 : Integer.parseInt(s), 0);
                    break;
                case "ROUES":
                    res = new RobotARoues(map, pos, (s == null) ? 80 : Integer.parseInt(s), 0);
                    break;
                case "PATTES":
                    res = new RobotAPattes(map, pos);
                    break;
                case "CHENILLES":
                    res = new RobotAChenilles(map, pos, (s == null) ? 60 : Integer.parseInt(s), 0);
                    break;
                default:
                    throw new IllegalArgumentException("La lecture du type de robot à échoué !");
            }

            System.out.println();
            return res;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }

    /**
     * Ignore toute (fin de) ligne commencant par '#'
     */
    private void ignorerCommentaires() {
        while (scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     *
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
