package Evenement;


abstract public class Evenement implements Comparable<Evenement> {
    
    public int date;

    public Evenement(int date) {
        this.date = date;
    }

    public int getDate() {
        return date;
    }
    
    public abstract void execute();
       
    @Override
    public int compareTo(Evenement e) {
        int diff = this.date - e.date;
        if (diff < 0) {
            return -1;
        } else if (diff > 0) {
            return 1;
        } else {
            return 0;
        }   
    }
}
