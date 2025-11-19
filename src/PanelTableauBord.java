/*
 * Auteur  : Marouane AMEJJOUD
 * Version : 1.0
 * Fichier : PanelTableauBord.java
 *
 * Description :
 * Panneau affichant un tableau de bord général de la prison.
 * Il présente sous forme de "tuiles" les statistiques suivantes :
 *   - Nombre de cellules
 *   - Nombre de détenus
 *   - Nombre de surveillants
 *   - Directeur en poste
 *
 * Les couleurs des tuiles sont personnalisées pour différencier les informations.
 */

import javax.swing.*;
import java.awt.*;

public class PanelTableauBord extends JPanel {

    private Controleur monControleur;

    public PanelTableauBord(Controleur unControleur) {
        this.monControleur = unControleur;
        this.initialiser();
    }

    /**
     * Initialise l'interface graphique
     */
    private void initialiser() {
        this.setLayout(new BorderLayout(20, 20));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Marges autour du panneau

        // --- 1. Titre ---
        JLabel titre = new JLabel("Tableau de Bord - HebSIO Prison", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setForeground(Color.DARK_GRAY);
        this.add(titre, BorderLayout.NORTH);

        // --- 2. Zone centrale : Grille de statistiques ---
        JPanel panelStats = new JPanel(new GridLayout(2, 2, 15, 15));

        // Récupération des données via le contrôleur
        int nbCellules = monControleur.recupererNbCellules();
        int nbDetenus = monControleur.recupererNbPrisonniers();
        int nbSurveillants = monControleur.recupererNbSurveillants();

        // Directeur
        String nomDirecteur = "Non défini";
        Directeur dir = monControleur.recupererLeDirecteur();
        if (dir != null) {
            nomDirecteur = dir.getPrenom() + " " + dir.getNom();
        }

        // Création des "tuiles" de statistiques
        panelStats.add(creerTuile("Cellules", String.valueOf(nbCellules), new Color(100, 149, 237)));       // Bleu
        panelStats.add(creerTuile("Détenus", String.valueOf(nbDetenus), new Color(255, 99, 71)));         // Rouge
        panelStats.add(creerTuile("Surveillants", String.valueOf(nbSurveillants), new Color(60, 179, 113))); // Vert
        panelStats.add(creerTuile("Directeur", nomDirecteur, new Color(255, 165, 0)));                     // Orange

        this.add(panelStats, BorderLayout.CENTER);
    }

    /**
     * Méthode utilitaire pour créer une "tuile" affichant une statistique
     * @param titre       : titre de la tuile
     * @param valeur      : valeur à afficher
     * @param couleurFond : couleur de fond de la tuile
     * @return JPanel configuré
     */
    private JPanel creerTuile(String titre, String valeur, Color couleurFond) {
        JPanel tuile = new JPanel(new BorderLayout());
        tuile.setBackground(couleurFond);
        tuile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel lblTitre = new JLabel(titre, SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitre.setForeground(Color.WHITE);

        JLabel lblValeur = new JLabel(valeur, SwingConstants.CENTER);
        lblValeur.setFont(new Font("Arial", Font.BOLD, 36));
        lblValeur.setForeground(Color.WHITE);

        tuile.add(lblTitre, BorderLayout.NORTH);
        tuile.add(lblValeur, BorderLayout.CENTER);

        return tuile;
    }
}
