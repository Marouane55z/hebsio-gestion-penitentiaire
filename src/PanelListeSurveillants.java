/*
 * Auteur  : Marouane AMEJJOUD
 * Version : 1.0
 * Fichier : PanelListeSurveillants.java
 *
 * Description :
 * Ce panneau affiche la liste des surveillants dans un tableau.
 * Il permet également :
 *   - Rechercher par nom ou numéro de téléphone
 *   - Supprimer un surveillant par téléphone
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

public class PanelListeSurveillants extends JPanel {

    private Controleur monControleur;
    private JTable tableau;
    private DefaultTableModel modeleTableau;

    // --- Composants Recherche ---
    private JTextField txtRecherche;
    private JButton btnRechercher;

    // --- Composants Gestion ---
    private JTextField txtTelASupprimer;
    private JButton btnSupprimer;
    private JButton btnActualiser;
    private JButton btnExporter;

    public PanelListeSurveillants(Controleur unControleur) {
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

        JLabel titre = new JLabel("Effectif des Surveillants", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        panelNord.add(titre);

        JPanel panelRecherche = new JPanel(new FlowLayout());
        panelRecherche.add(new JLabel("Rechercher (Nom ou Tél) :"));
        this.txtRecherche = new JTextField(20);
        this.btnRechercher = new JButton("Rechercher");
        this.btnRechercher.addActionListener(new BoutonRechercherListener());
        panelRecherche.add(this.txtRecherche);
        panelRecherche.add(this.btnRechercher);

        panelNord.add(panelRecherche);
        this.add(panelNord, BorderLayout.NORTH);

        // --- CENTRE : Tableau ---
        String[] colonnes = {"Nom", "Prénom", "Téléphone", "Grade"};
        this.modeleTableau = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tableau non éditable
            }
        };
        this.tableau = new JTable(this.modeleTableau);
        JScrollPane ascenseur = new JScrollPane(this.tableau);
        this.add(ascenseur, BorderLayout.CENTER);

        // --- BAS : Gestion ---
        JPanel panelBas = new JPanel(new GridLayout(2, 1, 5, 5));

        // Ligne 1 : Actualiser + Exporter
        JPanel panelGestion = new JPanel(new FlowLayout());
        this.btnActualiser = new JButton("Tout afficher");
        this.btnActualiser.addActionListener(new BoutonActualiserListener());
        panelGestion.add(this.btnActualiser);

        this.btnExporter = new JButton("Exporter");
        this.btnExporter.addActionListener(new BoutonExporterListener());
        panelGestion.add(this.btnExporter);

        // Ligne 2 : Suppression
        JPanel panelSuppr = new JPanel(new FlowLayout());
        panelSuppr.setBorder(BorderFactory.createTitledBorder("Licenciement"));
        panelSuppr.add(new JLabel("Tél à supprimer :"));
        this.txtTelASupprimer = new JTextField(10);
        panelSuppr.add(this.txtTelASupprimer);

        this.btnSupprimer = new JButton("Licencier");
        this.btnSupprimer.setBackground(Color.RED);
        this.btnSupprimer.setForeground(Color.WHITE);
        this.btnSupprimer.addActionListener(new BoutonSupprimerListener());
        panelSuppr.add(this.btnSupprimer);

        panelBas.add(panelGestion);
        panelBas.add(panelSuppr);

        this.add(panelBas, BorderLayout.SOUTH);
    }

    /**
     * Remplit le tableau avec la liste de surveillants
     */
    public void chargerDonnees(ArrayList<Surveillant> liste) {
        this.modeleTableau.setRowCount(0);
        for (Surveillant s : liste) {
            Object[] ligne = { s.getNom(), s.getPrenom(), s.getNumeroTel(), s.getGrade() };
            this.modeleTableau.addRow(ligne);
        }
    }

    // --- LISTENERS ---

    // Recherche
    private class BoutonRechercherListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String texte = txtRecherche.getText().trim();
            ArrayList<Surveillant> resultats = monControleur.rechercherSurveillant(texte);
            chargerDonnees(resultats);
        }
    }

    // Actualiser
    private class BoutonActualiserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            txtRecherche.setText("");
            chargerDonnees(monControleur.recupererLesSurveillants());
        }
    }

    // Suppression
    private class BoutonSupprimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tel = txtTelASupprimer.getText().trim();
            if (!tel.isEmpty()) {
                boolean existe = false;
                for (int i = 0; i < modeleTableau.getRowCount(); i++) {
                    if (modeleTableau.getValueAt(i, 2).equals(tel)) existe = true;
                }

                if (existe) {
                    int rep = JOptionPane.showConfirmDialog(null, "Licencier " + tel + " ?", 
                                                            "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (rep == JOptionPane.YES_OPTION) {
                        monControleur.supprimerSurveillant(tel);
                        txtTelASupprimer.setText("");
                        monControleur.afficherEcranListeSurveillants();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Numéro inconnu.");
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

            ArrayList<Surveillant> liste = monControleur.recupererLesSurveillants();
            String res = "";

            if (choix == 0) { 
                res += "Nom;Prenom;Tel;Grade\n"; 
                for(Surveillant s : liste) res += s.versCsv() + "\n"; 
            }
            if (choix == 1) { 
                res += "<liste>\n"; 
                for(Surveillant s : liste) res += s.versXml() + "\n"; 
                res += "</liste>"; 
            }
            if (choix == 2) { 
                res += "[\n"; 
                for(int i = 0; i < liste.size(); i++) {
                    res += liste.get(i).versJson();
                    if (i < liste.size() - 1) res += ",";
                    res += "\n";
                }
                res += "]";
            }

            if (choix != -1) {
                JTextArea ta = new JTextArea(res);
                ta.setRows(15);
                ta.setColumns(40);
                JOptionPane.showMessageDialog(null, new JScrollPane(ta), "Export", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
