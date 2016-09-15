package tp3;

/**
 * Classe représentant des équipes de hockey
 *
 * @author Sharomova Olga
 */
public class Equipe {

    private String nom;
    private int nbJoues;
    private int nbGagnes;
    private int nbPerdus;
    private int nbNuls;
    private int nbPoints;

    /**
     * Constructeur paramétrique pour créer des équipes à partir de toutes les
     * informations
     *
     * @param nom le nom d'une équipe
     * @param nbJoues le nombre de match joues
     * @param nbGagnes le nombre de match gagner
     * @param nbPerdus le nombre de match perdues
     * @param nbNuls le nombre de match nuls
     * @param nbPoints le nombre points
     */
    public Equipe(String nom, int nbJoues, int nbGagnes, int nbPerdus,
            int nbNuls, int nbPoints) {
        setNom(nom);
        setNbJoues(nbJoues);
        setNbGagnes(nbGagnes);
        setNbPerdus(nbPerdus);
        setNbNuls(nbNuls);
        setNbPoints(nbPoints);
    }

    /**
     *
     * @return le nom d'une équipe
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @return le nombre de match joues
     */
    public int getNbJoues() {
        return nbJoues;
    }

    /**
     *
     * @return le nombre de match gagner
     */
    public int getNbGagnes() {
        return nbGagnes;
    }

    /**
     *
     * @return le nombre de match perdues
     */
    public int getNbPerdus() {
        return nbPerdus;
    }

    /**
     *
     * @return le nombre de match nuls
     */
    public int getNbNuls() {
        return nbNuls;
    }

    /**
     *
     * @return le nombre points
     */
    public int getNbPoints() {
        return nbPoints;
    }

    /**
     *
     * @param nom le nom d'une équipe
     */
    private void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * @param nbJoues le nombre de match joues
     */
    private void setNbJoues(int nbJoues) {
        this.nbJoues = nbJoues;
    }

    /**
     *
     * @param nbGagnes le nombre de match gagner
     */
    private void setNbGagnes(int nbGagnes) {
        this.nbGagnes = nbGagnes;
    }

    /**
     *
     * @param nbPerdus le nombre de match perdues
     */
    private void setNbPerdus(int nbPerdus) {
        this.nbPerdus = nbPerdus;
    }

    /**
     *
     * @param nbNuls le nombre de match nuls
     */
    private void setNbNuls(int nbNuls) {
        this.nbNuls = nbNuls;
    }

    /**
     *
     * @param nbPoints le nombre des points
     */
    private void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    /**
     * Méthode qui ajoute des points gagnes
     */
    public void ajouterMatcheGagne() {
        int nb = 2;
        nbGagnes = nbGagnes + 1;
        calculerPoints(nb);
    }

    /**
     * Méthode qui ajoute des points nols
     */
    public void ajouterMatcheNul() {
        int nb = 1;
        nbNuls = nbNuls + 1;
        calculerPoints(nb);
    }

    /**
     * Méthode qui ajoute des points perdus
     */
    public void ajouterMatchePerdu() {
        nbPerdus = nbPerdus + 1;
        nbJoues = nbJoues + 1;
    }

    /**
     * Méthode qui ajoute calcule des points
     *
     * @param nb nombre des points
     */
    private void calculerPoints(int nb) {
        nbPoints = nbPoints + nb;
        nbJoues = nbJoues + 1;
    }
}
