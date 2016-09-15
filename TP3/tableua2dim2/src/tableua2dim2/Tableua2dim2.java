/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableua2dim2;

import java.util.Random;

/**
 *
 * @author 1379451
 */
public class Tableua2dim2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        int[][] matrice1 = new int[10][10];
        int[][] matrice2 = new int[10][10];

        genereAleaterment(matrice1);
        genereAleaterment(matrice2);
        System.out.println("matrice1");
        afficherTableux(matrice1);
        System.out.println("matrice2");
        afficherTableux(matrice2);
        initialiserDiagonale(matrice2, 3);
        afficherTableux(matrice2);
        initialiserDiagonaleInverse(matrice2);
        System.out.println("diagonalInvers");
        afficherTableux(matrice2);
        remplirMoitier(matrice2, 8);
        afficherTableux(matrice2);
        mettreZero(matrice1);
        copierTab(matrice1, matrice2);
    }

    private static void genereAleaterment(int[][] matrice) {
        Random generateur = new Random();
        for (int i = 0; i < matrice.length; ++i) {
            for (int j = 0; j < matrice[0].length; ++j) {
                matrice[i][j] = generateur.nextInt(100);
            }
        }

    }

    private static void afficherTableux(int[][] matrice1) {

        for (int i = 0; i < matrice1.length; ++i) {
            for (int j = 0; j < matrice1[0].length; ++j) {
                System.out.printf("%4d  ", matrice1[i][j]);
            }
            System.out.println();

        }
    }

    private static void initialiserDiagonale(int[][] matrice2, int nb) {

        System.out.println("                                         ");

        for (int k = 0; k < 10; k++) {
            matrice2[k][k] = nb;
        }
    }

    private static void initialiserDiagonaleInverse(int[][] matrice2) {
        int nb = 7;
        int k = 0;

        for (int l = 9; l >= 0; l--) {
            matrice2[k][l] = nb;
            ++k;
        }
    }

    private static void remplirMoitier(int[][] matrice2, int nb) {

        System.out.println("                                         ");
int t =0;
        for (int k = 0; k < 10; k++) {
            matrice2[0][k] = nb;
        }
        for (int k = 1; k < 10; k++) {
            matrice2[1][k] = nb;
        }
        for (int k = 2; k < 10; k++) {
            matrice2[2][k] = nb;
        }
        for (int k = 3; k < 10; k++) {
            matrice2[3][k] = nb;
        }
        for (int k = 4; k < 10; k++) {
            matrice2[4][k] = nb;
        }
        for (int k = 5; k < 10; k++) {
            matrice2[5][k] = nb;
        }
        for (int k = 6; k < 10; k++) {
            matrice2[6][k] = nb;
        }
        for (int k = 7; k < 10; k++) {
            matrice2[7][k] = nb;
        }
        for (int k = 8; k < 10; k++) {
            matrice2[8][k] = nb;
        }


    }

    private static void mettreZero(int[][] matrice1) {
        int[] nombre = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
        System.out.println("                                         ");
        for (int i = 0; i < matrice1.length; ++i) {
            for (int j = 0; j < matrice1[0].length; ++j) {

                if (i + j == 2 || i + j == 4 || i + j == 6 || i + j == 8 || i + j == 10
                        || i + j == 12 || i + j == 14 || i + j == 16 || i + j == 18 || i + j == 20) {
                } else {
                    matrice1[i][j] = 0;
                }
                System.out.printf("%4d  ", matrice1[i][j]);
            }
            System.out.println();
        }
    }

    private static void copierTab(int[][] matrice1, int[][] matrice2) {

        System.out.println("                                         ");
        for (int i = 0; i < matrice2.length; ++i) {
            for (int j = 0; j < matrice2[0].length; ++j) {

                matrice2[i][j] = matrice1[i][j];

                System.out.printf("%4d  ", matrice2[i][j]);
            }
            System.out.println();
        }
    }
}
