package tp3;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 * TP3 Gestion de tournois de hockey
 *
 * Programme créera le ou les groupes d'équipes inscrites aux différents
 * tournois à partir de données de base fournies dans un fichier texte contenant
 * les données pour chaque tournoi.
 *
 * Date: 07 mai 2014
 *
 * @author Olga Sharomova
 */
public class TP3 {

    /**
     * numero du bouton 1 por créer un tournoi
     */
    private static final int NOUV_TOUR = 0;
    /**
     * numero du bouton 1 pour lire les données dans une fichier data
     */
    private static final int LIRE_TOUR = 1;
    /**
     * numero du bouton 2 pour créer nouveau match
     */
    private static final int NOUV_MATCH = 2;
    /**
     * numero du bouton 3 pour écrir dans une fichier txt
     */
    private static final int RAPPORT = 3;
    /**
     * numero du bouton 4 pour afficher les group
     */
    private static final int AFFICHER = 4;
    /**
     * numero du bouton 5 pour fermer la fichier
     */
    private static final int FERM_FICHIER = 5;
    /**
     * numero du bouton 6 pour quitter le programme
     */
    private static final int SORTIR_PROG = 6;
    /**
     * nom du fichier txt à écrir
     */
    private static final String NOM_FICHIER = "ListeMatchs.txt";
    /**
     * nom du fichier txt à lire
     */
    private static final String NOM_FICHIER_LIRE = "clubs.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Tournoi unTournoi = null;//inicialiser le classe tournoi      
        String nomFichier = "";//nom fichier data
        int numero = 0;//numero du group choisi
        boolean exist = false;//pour verifier le fichier
        int choixMenu;//choix menu des boutons

        do {
            // les textes figurant sur les boutons
            String textesDesBoutons[] = {
                "Nouveau tournoi", // indice 0
                "Lire tournoi", // indice 1
                "Nouveau match", // indice 2
                "Rapport matchs", // indice 3
                "Afficher classement", // indice 4
                "Fermer fichier tournoi", // indice 5
                "Quitter" // indice 6
            };
//image pour l'icon
            ImageIcon img = new ImageIcon("hockeyplayer.png");
            choixMenu = // indice du bouton qui a été cliqué ou CLOSED_OPTION
                    JOptionPane.showOptionDialog(null,
                    "Choisissez l'option voulue",
                    "Tournois de hockey",
                    0,
                    JOptionPane.PLAIN_MESSAGE,
                    img, // l’image voulue 
                    textesDesBoutons, // les textes des boutons
                    textesDesBoutons[1]);   // le bouton par défaut

            switch (choixMenu) {
                case NOUV_TOUR:
                    if (unTournoi == null) {
                        unTournoi = nouveauTournoi(unTournoi);
                    } else {
                        JOptionPane.showMessageDialog(null, "Un tournoi est"
                                + " charge, veuiller le fermer avant de faire "
                                + "cette action",
                                "Lecture d'un tournoi",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                    break;
                case LIRE_TOUR:
                    if (unTournoi == null) {
                        unTournoi = lireTournoi(unTournoi, nomFichier);
                    } else {
                        JOptionPane.showMessageDialog(null, "Un tournoi est"
                                + " charge, veuiller le fermer avant de faire"
                                + " cette action",
                                "Lecture d'un tournoi",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                    break;
                case NOUV_MATCH:
                    if (unTournoi != null) {
                        nouveauMatch(unTournoi, numero);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Aucun tournoi charge",
                                "Entrer un match", JOptionPane.PLAIN_MESSAGE);
                    }
                    break;
                case RAPPORT:
                    if (unTournoi != null) {
                        rapportMatchs(unTournoi);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Aucun tournoi charge",
                                "Entrer un match", JOptionPane.PLAIN_MESSAGE);
                    }
                    break;
                case AFFICHER:
                    if (unTournoi != null) {
                        afficherClassement(unTournoi);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Aucun tournoi charge",
                                "Entrer un match", JOptionPane.PLAIN_MESSAGE);
                    }
                    break;
                case FERM_FICHIER:
                    if (unTournoi != null) {
                        nomFichier = fermerFichier(nomFichier,
                                unTournoi, exist);
                        exist = true;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Aucun tournoi charge",
                                "Entrer un match", JOptionPane.PLAIN_MESSAGE);
                    }
                    break;
                case SORTIR_PROG:
                    quitter(nomFichier, unTournoi, exist);
            }
        } while (choixMenu != SORTIR_PROG);
    }

    /**
     * Méthode qui créer un nouveau à partir un fichier txt
     *
     * @param unTournoi information sur les données de la classe tournoi
     * @return l'information de unTournoi
     * @throws IOException
     */
    private static Tournoi nouveauTournoi(Tournoi unTournoi)
            throws IOException {
        BufferedReader lecteurFichier = null;//pour lire un fichier txt
        String[] nom = null;//un tableau des noms 
        int nbEquipes = 0;//nombre équipes
        int nbGroupes = 0;//nombre groupes
        String txt = null;//pour entrer nom du fichier
        String ligne = null;//pour lire dans un fichier
        int nb = 0;//compteur
        txt = JOptionPane.showInputDialog(null,
                "Entrez le nom du fichier contenant les noms des équipes ",
                "Lectur des noms des équipes",
                JOptionPane.PLAIN_MESSAGE);
        if (txt != null) {
            if (!txt.equals(NOM_FICHIER_LIRE)) {
                JOptionPane.showMessageDialog(null, "Fichier \"" + txt
                        + "\" inexistant",
                        "Message d'erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    lecteurFichier = new BufferedReader(new FileReader(txt));
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Erreur d'ouverture"
                            + " du fichier",
                            "Lectur un match ",
                            JOptionPane.ERROR_MESSAGE);
                }
                nbEquipes = Integer.parseInt(lecteurFichier.readLine());
                nbGroupes = Integer.parseInt(lecteurFichier.readLine());
                nom = new String[nbEquipes];

                while ((ligne = lecteurFichier.readLine()) != null) {
                    nom[nb] = ligne;
                    ++nb;
                }
                unTournoi = new Tournoi(nom, nbGroupes);
            }
        }
        return unTournoi;
    }

    /**
     * Méthode qui permet de lire un tournoi- dans un fichier data
     *
     * @param unTournoi information sur les données de la classe tournoi
     * @return l'information de unTournoi
     * @throws IOException
     */
    private static Tournoi lireTournoi(Tournoi unTournoi, String nomFichier)
            throws IOException {
        String nomFichierData = null;//nom du fichier data

        nomFichierData = JOptionPane.showInputDialog(null,
                "Entrez le nom du fichier tournoi ",
                "Lectur d'un tournoi",
                JOptionPane.PLAIN_MESSAGE);

        if (nomFichierData == null) {
            JOptionPane.showMessageDialog(null, "Fichier \""
                    + nomFichierData
                    + "\" inexistant",
                    "Message d'erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            unTournoi = new Tournoi(nomFichier);
        }
        return unTournoi;
    }

    /**
     * Méthode qui permet d'enter les données d'un nouveau match pour un groupe
     *
     * @param unTournoi information sur les données de la classe tournoi
     * @param numero numero du group choisi
     * @return numero du group choisi
     */
    private static int nouveauMatch(Tournoi unTournoi, int numero) {
        String date = "";//pour entrer une date
        String equipeHot = "";// nom équipe hote
        String equipeVis = "";//nom équipe visiteuse 
        String butsHote = "";//nombre buts de hote
        String butsVis = "";//nombre buts de visiteuse
        String nomHot = "hote";//titre hote
        String nomVis = "visiteuse";//titre visiteus

        String txt = JOptionPane.showInputDialog(null,
                "Entrez le numero de groupe 1 a  " + unTournoi.getNbGroupes(),
                "Nouveau match",
                JOptionPane.PLAIN_MESSAGE);

        if (txt == null) {
            JOptionPane.showMessageDialog(null, "Le match n'a pas été ajouté",
                    "Entrer un match ", JOptionPane.PLAIN_MESSAGE);
        } else {
            numero = Integer.parseInt(txt);
            if (numero > unTournoi.getNbGroupes() || numero < 0) {
                JOptionPane.showMessageDialog(null, "Le numero de groupe"
                        + " inexistant",
                        "Message d'erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                equipeHot = demenderEquipe(unTournoi, numero, nomHot);

                if (equipeHot.equals("")) {
                    JOptionPane.showMessageDialog(null, "Le match n'a pas "
                            + "été ajouté",
                            "Entrer un match ", JOptionPane.PLAIN_MESSAGE);
                } else {
                    do {
                        equipeVis = demenderEquipe(unTournoi, numero, nomVis);
                    } while (equipeHot.equals(equipeVis)
                            && equipeVis.equals(""));

                    if (equipeVis.equals("")) {
                        JOptionPane.showMessageDialog(null, "Le match n'a pas"
                                + " été ajouté",
                                "Entrer un match ", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        butsHote = demenderButs(nomHot);

                        if (butsHote == null) {
                            JOptionPane.showMessageDialog(null, "Le match n'a "
                                    + "pas été ajouté",
                                    "Entrer un match ",
                                    JOptionPane.PLAIN_MESSAGE);
                        } else {
                            butsVis = demenderButs(nomVis);

                            if (butsVis == null) {
                                JOptionPane.showMessageDialog(null, "Le match"
                                        + " n'a pas été ajouté",
                                        "Entrer un match ",
                                        JOptionPane.PLAIN_MESSAGE);
                            } else {
                                date = JOptionPane.showInputDialog(null,
                                        "Entrez la date en format jj/mm/aaaa ",
                                        "Nouveau match",
                                        JOptionPane.PLAIN_MESSAGE);

                                if (date == null) {
                                    JOptionPane.showMessageDialog(null, "Le "
                                            + "match n'a pas été ajouté",
                                            "Entrer un match ",
                                            JOptionPane.PLAIN_MESSAGE);
                                } else {
                                    try {
                                        unTournoi.ajouterMatche(numero,
                                                equipeHot, equipeVis,
                                                Integer.parseInt(butsHote),
                                                Integer.parseInt(butsVis),
                                                date);
                                        JOptionPane.showMessageDialog(null,
                                                "Le match a été ajouté",
                                                "Entrer un match ",
                                                JOptionPane.PLAIN_MESSAGE);

                                    } catch (IllegalArgumentException e) {

                                        JOptionPane.showMessageDialog(null,
                                                e.getMessage(),
                                                "Message d'erreur",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return numero;
    }

    /**
     * Méthode qui demende noms équipe hote et nom équipe visiteus
     *
     * @param unTournoi information sur les données de la classe tournoi
     * @param numero numero du group choisi
     * @param nom équipe
     * @return nom équipe choisi
     */
    private static String demenderEquipe(Tournoi unTournoi, int numero,
            String nom) {
        String[] tabNom = null;//noms équipes
        String txt = null;//pour demender numero d'équipe
        String affiche = "";//chaine pour afficher des équipe
        String equipe = "";//nom équipe trouver

        tabNom = unTournoi.getNomsEquipes(numero);
        for (int i = 0; i < tabNom.length; i++) {
            affiche += i + 1 + ": " + tabNom[i] + "\n ";
        }
        txt = JOptionPane.showInputDialog(null,
                affiche + "Entrez le numero correspondant a l'equipe " + nom
                + "entre 1 et " + tabNom.length,
                "Nouveau match",
                JOptionPane.PLAIN_MESSAGE);

        if (txt != null) {
            if (Integer.parseInt(txt) > tabNom.length
                    || Integer.parseInt(txt) == 0) {
                JOptionPane.showMessageDialog(null, "Le numero d'equipe"
                        + " inexistant",
                        "Message d'erreur", JOptionPane.ERROR_MESSAGE);
                txt = null;
            } else {
                for (int i = 0; i < tabNom.length; i++) {
                    if (Integer.parseInt(txt) == i) {
                        equipe = tabNom[i - 1];
                    }
                }
            }
        }
        return equipe;
    }

    /**
     * Méthode qui demende buts équipe hote et buts équipe visiteus
     *
     * @param nom équipe
     * @return numero de but
     */
    private static String demenderButs(String nom) {
        String buts = null;//pour demender buts

        buts = JOptionPane.showInputDialog(null,
                "Entrez le numero de buts de l'equipe  " + nom,
                "Nouveau match",
                JOptionPane.PLAIN_MESSAGE);

        return buts;
    }

    /**
     * Méthode qui produit une liste des matchs sur un fichier txt
     *
     * @param unTournoi information sur les données de la classe tournoi
     */
    private static void rapportMatchs(Tournoi unTournoi) {
        final String[][] entetes = {
            {
                "Hote", "Visiteur", "Buts", "Buts", "Date du"
            },
            {
                "", "", "hote", "visiteur", "match"
            }
        };//pou afficher entetes
        PrintWriter createurFichier = null;//pour écrir dans un fichier
        try {
            createurFichier = new PrintWriter(
                    new BufferedOutputStream(new FileOutputStream(NOM_FICHIER)));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur d'ouverture du fichier",
                    "Ecrir un match ",
                    JOptionPane.ERROR_MESSAGE);
        }
        createurFichier.println("LISTE DES MATCHS JOUER\n");
        createurFichier.println("=================================\n");
        for (int i = 0; i < entetes.length; i++) {
            for (int j = 0; j < entetes[i].length; j++) {
                createurFichier.printf("%25s", entetes[i][j]);
            }
            createurFichier.println("");
        }
        for (int j = 0; j < unTournoi.getNbGroupes(); j++) {
            Match[] matchs = unTournoi.getMatchs(j);
            for (int i = 0; i < matchs.length; i++) {
                if (!matchs[i].getDateMatch().equals("")) {
                    createurFichier.printf("%25s%25s%25d%25d%25s\n",
                            matchs[i].getEquipeHote(),
                            matchs[i].getEquipeVisiteuse(),
                            matchs[i].getNbButsHote(),
                            matchs[i].getNbButsVisiteuse(),
                            matchs[i].getDateMatch());
                }
            }
        }
        createurFichier.close();
    }

    /**
     * Méthode qui affiche tous les groupes du tournoi à l'écran
     *
     * @param unTournoi information sur les données de la classe tournoi
     */
    private static void afficherClassement(Tournoi unTournoi) {
        String affichage = "";//afficher la chaine avec les groupes
        String nom = "Nom";//afficher la chaine avec les groupes
        String joue = "Jouees";//afficher la chaine avec les groupes
        String perdue = "Perdues";//afficher la chaine avec les groupes
        String gagne = "Ganees";//afficher la chaine avec les groupes
        String point = "Points";//afficher la chaine avec les groupes
        String nulle = "Nulles";//afficher la chaine avec les groupes

        JTextArea zoneTexte = new JTextArea(5, 30);
        zoneTexte.setFont((new Font("Courier new", Font.BOLD, 12)));
        zoneTexte.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3, true));

        for (int j = 0; j < unTournoi.getNbGroupes(); j++) {
            Equipe[] classement = unTournoi.getClassement(j);
            affichage += String.format("Groupe   %d\n ===========\n"
                    + "%-25s%10s%10s%10s%10s%10s\n",
                    j + 1, nom, joue, gagne, perdue, nulle, point);
            for (int i = 0; i < unTournoi.getNbEquipes(j); i++) {
                affichage += String.format("%-25s%10d%10d%10d%10d%10d\n",
                        classement[i].getNom(),
                        classement[i].getNbJoues(),
                        classement[i].getNbGagnes(),
                        classement[i].getNbPerdus(),
                        classement[i].getNbNuls(),
                        classement[i].getNbPoints());
            }
        }
        zoneTexte.setText(affichage);
        JOptionPane.showMessageDialog(null, zoneTexte,
                "Classement", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Méthode qui permet de fermer le fichier tournoi ouvert
     *
     * @param nomFichier nom du fichier binaire
     * @param unTournoi information sur les données de la classe tournoi
     * @param exist puor verifier si le fichier exist
     * @return nom du fichier binaire
     * @throws IOException
     */
    private static String fermerFichier(String nomFichier, Tournoi unTournoi,
            boolean exist) throws IOException {
        String nom = "";//pour entrer nom du fichier
        String menu[] = {
            "Oui", // indice 0
            "Non" // indice 1
        };
//afficher un image
        ImageIcon img2 = new ImageIcon("hokeyes.png");
        int choix = // indice du bouton qui a été cliqué ou CLOSED_OPTION
                JOptionPane.showOptionDialog(null,
                "Le tournoi  a été modifié, voulez-vous le sauvegarder?",
                "Quitter",
                0,
                JOptionPane.PLAIN_MESSAGE,
                img2, // l’image voulue 
                menu, // les textes des boutons
                menu[0]);   // le bouton par défaut

        switch (choix) {
            case 0:
                if (exist == false) {
                    nomFichier = JOptionPane.showInputDialog(null,
                            "Entrez le nom du fichier tournoi ",
                            "Lectur nom du fichier",
                            JOptionPane.PLAIN_MESSAGE);
                    if (nomFichier == null) {
                        JOptionPane.showMessageDialog(null, "Fichier \""
                                + nomFichier + "\" inexistant",
                                "Message d'erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    int fichier = // indice du bouton qui a été cliqué ou CLOSED
                            JOptionPane.showOptionDialog(null,
                            "Voulez-vous conserver le fichier ("
                            + nomFichier + " )?",
                            "Sauvegarder",
                            0,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            menu, // les textes des boutons
                            menu[0]);   // le bouton par défaut
                    switch (fichier) {
                        case 0:
                            unTournoi.sauvegarder(nomFichier);
                            unTournoi = null;
                            break;
                        case 1:
                            nom = JOptionPane.showInputDialog(null,
                                    "Entrez le nom du fichier tournoi ",
                                    "Lectur nom du fichier",
                                    JOptionPane.PLAIN_MESSAGE);
                            if (nom != null) {
                                unTournoi.sauvegarder(nom);
                                unTournoi = null;
                            }
                    }
                }
            case 1:
        }
        return nomFichier;
    }

    /**
     * Méthode qui permet quitter le programme et sauvegarder les données
     *
     * @param nomFichier nom du fichier binaire
     * @param unTournoi information sur les données de la classe tournoi
     * @param exist puor verifier si le fichier exist
     * @throws IOException
     */
    private static void quitter(String nomFichier, Tournoi unTournoi,
            boolean exist) throws IOException {
        nomFichier = fermerFichier(nomFichier, unTournoi, exist);
    }
}
