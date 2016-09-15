package tp3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Classe représentant un tournoi de hockey
 *
 * @author Sharomova Olga
 */
public class Tournoi {

    /**
     * nombre minimale d'équipes
     */
    public final int MIN_NOMBRE_EQUIPES = 8;
    /**
     * nombre maximale d'équipes
     */
    public final int MAX_NOMBRE_EQUIPES = 50;
    private Equipe[][] classement;//tableau d'équipes
    private Match[][] matchs;//tableau de match
    private int[] nbMatchGroupe;//tableau de nombre de match

    /**
     * Constructeur paramétrique pour créer un tournoi en memoir
     *
     * @param nom des équipes
     * @param nbGroupes nombre de groupes
     */
    public Tournoi(String[] nom, int nbGroupes) {
        int numero = 0;//compteur

        classement = new Equipe[nbGroupes][];
        if (nom.length <= MAX_NOMBRE_EQUIPES
                && nom.length >= MIN_NOMBRE_EQUIPES) {
            int reste = nom.length % nbGroupes;
            if (reste == 0) {
                for (int i = 0; i < nbGroupes; i++) {
                    classement[i] = new Equipe[nom.length / nbGroupes];
                }
            } else {
                for (int i = 0; i < reste; i++) {
                    classement[i] = new Equipe[nom.length / nbGroupes + 1];
                }
                for (int i = reste; i < nbGroupes; i++) {
                    classement[i] = new Equipe[nom.length / nbGroupes];
                }
                for (int i = 0; i < classement.length; i++) {
                    for (int j = 0; j < classement[i].length; j++) {
                        classement[i][j] =
                                new Equipe(nom[numero], 0, 0, 0, 0, 0);
                        ++numero;
                    }
                }
                matchs = new Match[nbGroupes][];
                for (int i = 0; i < matchs.length; i++) {
                    matchs[i] = new Match[(classement[i].length
                            * (classement[i].length - 1)) / 2];
                    for (int j = 0; j < matchs[i].length; j++) {
                        matchs[i][j] = new Match();
                        nbMatchGroupe = new int[j];
                    }
                }
            }
        }
    }

    /**
     * Constructeur paramétrique pour créer un tournoi à partir d'un fichier
     * data
     *
     * @param nomFichier nom du fichie data
     * @throws IOException
     */
    public Tournoi(String nomFichier) throws IOException {
        int nbGroup = 0;//nombre du groupes
        int nbEquip = 0;//nombre des équipes
        int joues = 0;//nombre de joues
        int gagnes = 0;//nombre du match gagner
        int perdus = 0;//nombre du match perdues
        int nuls = 0;//nombre du match nuls
        int points = 0;//nombre des points
        int nbMatch = 0;//nombre du match - linge
        int nbMatchDate = 0;//nombre du match - colonne
        int butsHote = 0;//nombre des buts de hote
        int butsVisiteuse = 0;//nombre des buts de visiteuse
        String nom = null;//nom d'équipe
        String nomHote = null;//nom d'équipe hote
        String nomVisiteuse = null;//nom d'équipe visiteuse
        String dateMatch = null;//date du match
        DataInputStream fichierBinaire = null;//fichier binaire

        try {
            fichierBinaire = new DataInputStream(new FileInputStream(nomFichier));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur d'ouverture"
                    + " du fichier",
                    "Lectur un match ",
                    JOptionPane.ERROR_MESSAGE);
        }
        nbGroup = fichierBinaire.readInt();
        classement = new Equipe[nbGroup][];
        for (int i = 0; i < nbGroup; i++) {
            nbEquip = fichierBinaire.readInt();
            classement[i] = new Equipe[nbEquip];
            for (int j = 0; j < nbEquip; j++) {
                nom = fichierBinaire.readUTF();
                joues = fichierBinaire.readInt();
                gagnes = fichierBinaire.readInt();
                perdus = fichierBinaire.readInt();
                nuls = fichierBinaire.readInt();
                points = fichierBinaire.readInt();

                classement[i][j] = new Equipe(nom, joues, gagnes,
                        perdus, nuls, points);
            }
        }
        matchs = new Match[nbMatch][];
        for (int i = 0; i < nbMatch; i++) {
            nbMatch = fichierBinaire.readInt();
            matchs[i] = new Match[nbMatch];
            nbMatchDate = fichierBinaire.readInt();
            for (int j = 0; j < nbMatchDate; j++) {
                nomHote = fichierBinaire.readUTF();
                nomVisiteuse = fichierBinaire.readUTF();
                butsHote = fichierBinaire.readInt();
                butsVisiteuse = fichierBinaire.readInt();
                dateMatch = fichierBinaire.readUTF();
                matchs[i][j] = new Match(nomHote, nomVisiteuse,
                        butsHote, butsVisiteuse, dateMatch);
            }
            for (int j = nbMatchDate; j < matchs[i].length; j++) {
                matchs[i][j] = new Match();
            }
        }
    }

    /**
     * Methode publique écrit tous les données du tournoi dans le fichier data
     *
     * @param nomFichier nom du fichier
     * @throws IOException
     */
    public void sauvegarder(String nomFichier) throws IOException {
        DataOutputStream fichierBinaire = null;//fichier

        try {
            fichierBinaire = new DataOutputStream(
                    new FileOutputStream(nomFichier));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur d'ouverture"
                    + " du fichier",
                    "Lectur un match ",
                    JOptionPane.ERROR_MESSAGE);
        }
        fichierBinaire.writeInt(classement.length);

        for (int i = 0; i < classement.length; i++) {
            fichierBinaire.writeInt(classement[i].length);
            for (int j = 0; j < classement[i].length; j++) {
                fichierBinaire.writeUTF(classement[i][j].getNom());
                fichierBinaire.writeInt(classement[i][j].getNbJoues());
                fichierBinaire.writeInt(classement[i][j].getNbGagnes());
                fichierBinaire.writeInt(classement[i][j].getNbPerdus());
                fichierBinaire.writeInt(classement[i][j].getNbNuls());
                fichierBinaire.writeInt(classement[i][j].getNbPoints());
            }
        }
        for (int i = 0; i < matchs.length; i++) {
            fichierBinaire.writeInt(matchs[i].length);
            fichierBinaire.writeInt(nbMatchGroupe[i]);
            for (int j = 0; j < classement[i].length; j++) {
                fichierBinaire.writeUTF(matchs[i][j].getEquipeHote());
                fichierBinaire.writeUTF(matchs[i][j].getEquipeVisiteuse());
                fichierBinaire.writeInt(matchs[i][j].getNbButsHote());
                fichierBinaire.writeInt(matchs[i][j].getNbButsVisiteuse());
                fichierBinaire.writeUTF(matchs[i][j].getDateMatch());
            }
            for (int j = nbMatchGroupe[i]; j < matchs[i].length; j++) {
                matchs[i][j] = new Match();
            }
        }
    }

    /**
     *
     * @param noGroupes numero d'un groupe
     * @return nombre d'éuipes dans ce groupe
     */
    public int getNbEquipes(int noGroupes) {
        return classement[noGroupes].length;
    }

    /**
     *
     * @return nombre de groupes dans un tournoi
     */
    public int getNbGroupes() {
        return classement.length;
    }

    /**
     *
     * @param noGroupes numero d'un groupe
     * @return les noms des éuipes dans ce groupe
     */
    public String[] getNomsEquipes(int noGroupes) {
        String tabNom[] = new String[classement[noGroupes - 1].length];
        for (int i = 0; i < tabNom.length; i++) {
            tabNom[i] = classement[noGroupes - 1][i].getNom();
        }
        return tabNom;
    }

    /**
     * Methode qui ajoute un matche
     *
     * @param numeroGr numero du groupe
     * @param nomsHote nom d'éuipe hote
     * @param nomsVisiteuses nom d'éuipe visiteuse
     * @param nbButHote nombre buts de hote
     * @param nbButVis nombre buts de visiteuse
     * @param dateMatche date du match
     */
    public void ajouterMatche(int numeroGr, String nomsHote,
            String nomsVisiteuses, int nbButHote, int nbButVis,
            String dateMatche) {
        int numeroHote = 0;//numero équipe de hote
        int numeroVis = 0;//numero équipe de visiteuse
        boolean stop = false;//pour ajouter un match
        for (int j = 0; j < matchs[numeroGr - 1].length; j++) {
            if (stop == false) {
                if (matchs[numeroGr - 1][j].getDateMatch().equals("")) {
                    matchs[numeroGr - 1][j].setMatch(nomsHote, nomsVisiteuses,
                            nbButHote, nbButVis, dateMatche);
                    stop = true;
                }
            }
        }
        for (int j = 0; j < classement[numeroGr - 1].length; j++) {
            if (nomsHote.equals(classement[numeroGr - 1][j].getNom())) {
                numeroHote = j;
            }
            if (nomsVisiteuses.equals(classement[numeroGr - 1][j].getNom())) {
                numeroVis = j;
            }
        }
        if (nbButHote > nbButVis) {
            classement[numeroGr - 1][numeroHote].ajouterMatcheGagne();
            classement[numeroGr - 1][numeroVis].ajouterMatchePerdu();
        }
        if (nbButHote < nbButVis) {
            classement[numeroGr - 1][numeroHote].ajouterMatchePerdu();
            classement[numeroGr - 1][numeroVis].ajouterMatcheGagne();
        }
        if (nbButHote == 0) {
            classement[numeroGr - 1][numeroHote].ajouterMatcheNul();
        }
        if (nbButVis == 0) {
            classement[numeroGr - 1][numeroVis].ajouterMatcheNul();
        }
    }

    /**
     * Methode qui trie à bulles le tablaeu classement
     *
     * @param classement tableau d'équipes
     */
    private void trierBullesDecroissant(Equipe[] classement) {
        Equipe tampon;
        boolean permut;
        int longueur = classement.length;

        do {
            // hypothèse : le tableau est trié
            permut = false;
            for (int i = 0; i < longueur - 1; i++) {
                // Teste si 2 éléments successifs sont dans le bon ordre ou non
                if (classement[i].getNbPoints() < classement[i + 1].getNbPoints()) {
                    // s'ils ne le sont pas, on échange leurs positions
                    tampon = classement[i];
                    classement[i] = classement[i + 1];
                    classement[i + 1] = tampon;
                    permut = true;
                }
            }
        } while (permut);
    }

    /**
     *
     * @param noGroupe numero du groupe
     * @return tableau d'équipe correspondant au groupe
     */
    public Equipe[] getClassement(int noGroupe) {
        trierBullesDecroissant(classement[noGroupe]);
        return classement[noGroupe];
    }

    /**
     *
     * @param noGroupe numero du groupe
     * @return tableau des matchs correspondant au groupe
     */
    public Match[] getMatchs(int noGroupe) {
        return matchs[noGroupe];
    }
}
