package fr.gsb.entities;

public class Motifs {

    private int num ;
    private String libelle ;

    public Motifs(int num, String libelle) {
        this.num = num;
        this.libelle = libelle;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Motif{" +
                "num=" + num +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}