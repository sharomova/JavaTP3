package tp3;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe représentant un match de hockey
 *
 * @author jmalo
 */
public class Match {

    private String equipeHote;
    private String equipeVisiteuse;
    private int nbButsHote;
    private int nbButsVisiteuse;
    private String dateMatch;

    /**
     * Constructeur par défaut
     */
    public Match() {
        equipeHote = "";
        equipeVisiteuse = "";        
        nbButsHote = 0;
        nbButsVisiteuse = 0;
        dateMatch = "";
    }

    /**
     * Constructeur paramétrique pour créer un match à partir de toutes les
     * informations
     *
     * @param equipeHote le nom de l'équipe hôte
     * @param equipeVisiteur le nom de l'équipe visiteuse
     * @param nbButsHote le nombre de buts de l'équipe hôte
     * @param nbButsVisiteur le nombre de buts de l»'équipe visiteuse
     * @param DateMatch la date du match. La date peut être vide si les autres
     * informations sont vides aussi
     * @throws IllegalArgumentException si la date est invalide.
     */
    public Match(String equipeHote, String equipeVisiteur,
            int nbButsHote, int nbButsVisiteur, String dateMatch)
            throws IllegalArgumentException {
        setMatch(equipeHote, equipeVisiteur, nbButsHote, nbButsVisiteur,
                dateMatch);
    }

    /**
     * Méthode qui place les informations dans le match
     *
     * @param equipeHote le nom de l'équipe hôte
     * @param equipeVisiteur le nom de l'équipe visiteuse
     * @param nbButsHote le nombre de buts de l'équipe hôte
     * @param nbButsVisiteur le nombre de buts de l»'équipe visiteuse
     * @param DateMatch la date du match. La date peut être vide si les autres
     * informations sont vides aussi
     * @throws IllegalArgumentException si la date est invalide
     */
    public void setMatch(String equipeHote, String equipeVisiteuse,
            int nbButsHote, int nbButsVisiteur, String dateMatch)
            throws IllegalArgumentException {
        if (dateMatch.equals("")
                && (!equipeHote.equals("") || !equipeVisiteuse.equals("")
                || nbButsHote < 0 || nbButsVisiteur < 0)) {
            throw new IllegalArgumentException("Date invalide");
        }
        else {
            this.setDateMatch(dateMatch);
            this.setEquipeHote(equipeHote);
            this.setEquipeVisiteuse(equipeVisiteuse);
            this.setNbButsHote(nbButsHote);
            this.setNbButsVisiteuse(nbButsVisiteur);
        }
    }

    /**
     *
     * @return Le nom de l'équipe hôte
     */
    public String getEquipeHote() {
        return equipeHote;
    }

    /**
     * @param equipeHote Le nouveau nom de l'équipe hôte
     */
    private void setEquipeHote(String equipeHote) {
        this.equipeHote = equipeHote;
    }

    /**
     * @return Le nombre de buts de l'équipe hôte
     */
    public int getNbButsHote() {
        return nbButsHote;
    }

    /**
     * @param nbButsHote Le nouveau nombre de buts de l'équipe hôte
     */
    private void setNbButsHote(int nbButsHote) {
        if (nbButsHote >= 0) {
            this.nbButsHote = nbButsHote;
        }
    }

    /**
     *
     * @return Le nom de l'équipe visiteuse
     */
    public String getEquipeVisiteuse() {
        return equipeVisiteuse;
    }

    /**
     * @param equipeVisiteuse Le nouveau nom de l'équipe visiteuse
     */
    private void setEquipeVisiteuse(String equipeVisiteuse) {
        this.equipeVisiteuse = equipeVisiteuse;
    }

    /**
     * @return le nombre de buts de l'équipe Visiteuse
     */
    public int getNbButsVisiteuse() {
        return nbButsVisiteuse;
    }

    /**
     * @param nbButsVisiteuse le nombre de buts de l'équipe visiteuse à modifier
     */
    private void setNbButsVisiteuse(int nbButsVisiteuse) {
        if (nbButsVisiteuse >= 0) {
            this.nbButsVisiteuse = nbButsVisiteuse;
        }
    }

    /**
     * @return la date du match
     */
    public String getDateMatch() {
        return dateMatch;
    }

    /**
     * @param dateMatch la date du match
     */
    private void setDateMatch(String dateMatch) throws IllegalArgumentException {
        if (!estDateValide(dateMatch) && !dateMatch.equals("")) {
            throw new IllegalArgumentException("Date invalide");
        }
        else {
            this.dateMatch = dateMatch;
        }
    }

    /**
     * Méthode qui valide une date selon le format jj/mm/aaaa. La date valide
     * doit être entre aujourd'hui et il y a un an exactement
     *
     * @param date Chaîne de caractère sontenant la date à valider
     * @return booléen indiquant si la date est valide ou non
     */
    private boolean estDateValide(String date) {
        boolean valide = true;
        Pattern pat = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4})");
        Matcher mat = pat.matcher(date);

        if (mat.matches()) {
            Calendar laDate = null;
            Calendar aujourdhui = Calendar.getInstance();
            Calendar aujourdhuiMoinsUnAn = (Calendar) aujourdhui.clone();
            aujourdhuiMoinsUnAn.add(Calendar.YEAR, -1); // Il y a un an

            int jour = Integer.parseInt(mat.group(1));
            int mois = Integer.parseInt(mat.group(2));
            int an = Integer.parseInt(mat.group(3));
            laDate = Calendar.getInstance();
            laDate.set(an, mois - 1, jour - 1);

            if (laDate.before(aujourdhuiMoinsUnAn)
                    || laDate.after(aujourdhui)) {
                valide = false;
            }
        }
        else {
            valide = false;
        }
        return valide;
    }
}
