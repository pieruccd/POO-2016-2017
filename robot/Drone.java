package robot;

import simulation.*;


public final class Drone extends RobotReservoir {

    public Drone(Carte carte, Case position, double vitesse, int eau) {
        this.quantitéEau = eau;
        this.tempsRemplissage = 30;
        this.volumeReservoir = 10000;
        this.setVitesse(vitesse);
        this.ligne = position.getLigne();
        this.colonne = position.getColonne();
        this.carte = carte;
    }

    @Override
    public void setPosition(int lig, int col) {
        this.ligne = lig;
        this.colonne = col;
    }

    @Override
    public void setPostion(Case pos) {
        this.ligne = pos.getLigne();
        this.colonne = pos.getColonne();
    }
    
    public void setVitesse(double vitesse) {
        if (vitesse > 0 && vitesse <= 150) {
            this.vitesse = vitesse;
        } else {
            throw new IllegalArgumentException("Vitesse impossible (out of range)");
        }
    }

    @Override
    public boolean deplacementPossible(EnumDirection dir) {
        return this.carte.voisinExiste(this.getPostion(), dir);
    }

    @Override
    public void remplirReservoir() {
        if (this.getPostion().getNature() == EnumNatureTerrain.EAU) {
            this.quantitéEau = this.volumeReservoir;
        } else {
            throw new IllegalArgumentException("Le drone n'est pas au dessus d'une case d'eau : remplissage impossible");
        }
    }

}
