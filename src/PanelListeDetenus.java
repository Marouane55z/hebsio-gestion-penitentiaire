/*
 * Auteur  : Marouane AMEJJOUD
 * Version : 1.0
 * Fichier : PanelListeDetenus.java
 *
 * Description :
 * Ce panneau affiche la liste des prisonniers dans un tableau.
 * Il permet également :
 *   - Rechercher par nom ou numéro d'écrou
 *   - Supprimer un détenu par numéro d'écrou
 *   - Actualiser la liste complète
 *   - Exporter la liste (CSV, XML, JSON)
 *
 * Le remplissage des données se fait via la méthode chargerDonnees()
 * appelée par le Contrôleur.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelListeDetenus extends JPanel {

    private Controleur monControleur;
    private JTable tableau;
    private DefaultTableModel modeleTableau;

    // --- Composants Recherche ---
    private JTextField txtRecherche;
    private JButton btnRechercher;

    // --- Composants Gestion ---
    private JTextField txtEcrouASupprimer;
    private JButton btnSupprimer;
    private JButton btnActualiser;
    private JButton btnExporter;

    /**
     * Constructeur
     * @param unControleur Le contrôleur principal pour gérer les actions
     */
    public PanelListeDetenus(Controleur unControleur) {
        this.monControleur = unControleur;
        this.initialiser();
    }

    /**
     * Initialise l'interface graphique
     */
    private void initialiser() {
        this.setLayout(new BorderLayout(10, 10));

        // --- HAUT : Titre + Recherche ---
        JPanel panelNord = new JPanel(new GridLayout(2, 1, 5, 5));

        JLabel titre = new JLabel("Registre des Prisonniers", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        panelNord.add(titre);

        JPanel panelRecherche = new JPanel(new FlowLayout());
        panelRecherche.add(new JLabel("Rechercher (Nom ou Écrou) :"));
        this.txtRecherche = new JTextField(20);
        this.btnRechercher = new JButton("Rechercher");
        this.btnRechercher.addActionListener(new BoutonRechercherListener());

        panelRecherche.add(this.txtRecherche);
        panelRecherche.add(this.btnRechercher);

        panelNord.add(panelRecherche);
        this.add(panelNord, BorderLayout.NORTH);

        // --- CENTRE : Tableau ---
        String[] colonnes = {"Écrou", "Nom", "Prénom", "Crime", "N° Cellule"};
        this.modeleTableau = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tableau non modifiable
            }
        };
        this.tableau = new JTable(this.modeleTableau);
        JScrollPane ascenseur = new JScrollPane(this.tableau);
        this.add(ascenseur, BorderLayout.CENTER);

        // --- BAS : Gestion ---
        JPanel panelBas = new JPanel(new GridLayout(2, 1, 5, 5));

        // Ligne 1 : Actualiser + Exporter
        JPanel panelGestion = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.btnActualiser = new JButton("Tout afficher / Actualiser");
        this.btnActualiser.addActionListener(new BoutonActualiserListener());
        panelGestion.add(this.btnActualiser);

        this.btnExporter = new JButton("Exporter la liste");
        this.btnExporter.addActionListener(new BoutonExporterListener());
        panelGestion.add(this.btnExporter);

        // Ligne 2 : Suppression
        JPanel panelSuppr = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSuppr.setBorder(BorderFactory.createTitledBorder("Zone de Suppression"));
        panelSuppr.add(new JLabel("Numéro d'écrou à supprimer : "));
        this.txtEcrouASupprimer = new JTextField(10);
        panelSuppr.add(this.txtEcrouASupprimer);

        this.btnSupprimer = new JButton("Supprimer");
        this.btnSupprimer.setBackground(Color.RED);
        this.btnSupprimer.setForeground(Color.WHITE);
        this.btnSupprimer.addActionListener(new BoutonSupprimerListener());
        panelSuppr.add(this.btnSupprimer);

        panelBas.add(panelGestion);
        panelBas.add(panelSuppr);

        this.add(panelBas, BorderLayout.SOUTH);
    }

    /**
     * Remplit le tableau avec une liste de détenus
     * @param lesDetenus Liste de détenus
     */
    public void chargerDonnees(ArrayList<Detenu> lesDetenus) {
        this.modeleTableau.setRowCount(0);
        for (Detenu d : lesDetenus) {
            String affichageCellule = (d.getSaCellule() != null) ? 
                                      String.valueOf(d.getSaCellule().getNumeroCellule()) : "Aucune";
            Object[] ligne = { d.getNumeroEcrou(), d.getNom(), d.getPrenom(), d.getCrime(), affichageCellule };
            this.modeleTableau.addRow(ligne);
        }
    }

    // --- LISTENERS ---

    // Recherche
    private class BoutonRechercherListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String texte = txtRecherche.getText().trim();
            ArrayList<Detenu> resultats = monControleur.rechercherDetenu(texte);
            chargerDonnees(resultats);
        }
    }

    // Actualiser
    private class BoutonActualiserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            txtRecherche.setText(""); // Vide le champ
            ArrayList<Detenu> listeAJour = monControleur.recupererLesPrisonniers();
            chargerDonnees(listeAJour);
        }
    }

    // Suppression
    private class BoutonSupprimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String texteEcrou = txtEcrouASupprimer.getText().trim();
            if (!texteEcrou.isEmpty()) {
                try {
                    int numEcrou = Integer.parseInt(texteEcrou);
                    boolean existe = false;
                    for (int i = 0; i < modeleTableau.getRowCount(); i++) {
                        if ((int)modeleTableau.getValueAt(i, 0) == numEcrou) existe = true;
                    }

                    if (existe) {
                        int rep = JOptionPane.showConfirmDialog(null, 
                                "Supprimer le détenu " + numEcrou + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (rep == JOptionPane.YES_OPTION) {
                            monControleur.supprimerPrisonnier(numEcrou);
                            txtEcrouASupprimer.setText("");
                            monControleur.afficherEcranListePrisonniers();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Écrou introuvable dans la liste actuelle.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Format incorrect.");
                }
            }
        }
    }

    // Export
    private class BoutonExporterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] options = {"CSV", "XML", "JSON"};
            int choix = JOptionPane.showOptionDialog(null, "Format :", "Export", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            ArrayList<Detenu> liste = monControleur.recupererLesPrisonniers();
            String res = "";

            if (choix == 0) { 
                res += "Ecrou;Nom;Prenom;Crime;Cellule\n"; 
                for(Detenu d : liste) res += d.versCsv() + "\n"; 
            }
            if (choix == 1) { 
                res += "<liste>\n"; 
                for(Detenu d : liste) res += d.versXml() + "\n"; 
                res += "</liste>"; 
            }
            if (choix == 2) { 
                res += "[\n"; 
                for(int i=0; i<liste.size(); i++) {
                    res += liste.get(i).versJson();
                    if(i<liste.size()-1) res += ",";
                    res += "\n";
                }
                res += "]";
            }

            if(choix != -1) {
                JTextArea ta = new JTextArea(res);
                ta.setRows(15); ta.setColumns(40);
                JOptionPane.showMessageDialog(null, new JScrollPane(ta), "Export", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
