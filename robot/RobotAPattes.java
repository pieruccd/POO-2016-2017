package robot;

import simulation.*;

public final class RobotAPattes extends Robot {

    public RobotAPattes(Carte carte, Case pos) {
        this.carte = carte;
        this.setPostion(pos);
    }

    @Override
    public void deverserEau(int vol) {
        // Comme le reservoir est infini, rien de spécial à faire ici ...
    }

    @Override
    public void remplirReservoir() {
        // Comme le reservoir est infini, rien de spécial à faire ici ...
    }

    @Override
    public int getEauReservoir() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setPostion(Case pos) {
        this.setPosition(pos.getLigne(), pos.getColonne());
    }

    @Override
    public boolean deplacementPossible(EnumDirection dir) {
        Case voisin = this.carte.getVoisin(this.carte.getCase(ligne, colonne), dir);
        return this.carte.getCase(voisin.getLigne(), voisin.getColonne()).getNature() != EnumNatureTerrain.EAU
                && this.carte.voisinExiste(this.getPostion(), dir);
    }

    @Override
    public void setPosition(int lig, int col) {
        if (this.carte.getCase(lig, col).getNature() == EnumNatureTerrain.EAU) {
            throw new IllegalArgumentException("Un robot à pattes ne peux pas se rendre sur l'eau !");
        } else if (this.getPostion().getNature() == EnumNatureTerrain.ROCHE) {
            this.ligne = lig;
            this.colonne = col;
            this.vitesse = 10;
        } else {
            this.colonne = col;
            this.ligne = lig;
            this.vitesse = 30;
        }
    }

}
