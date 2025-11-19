/*
 * Auteur  : Marouane AMEJJOUD
 * Version : 1.0
 * Fichier : PanelListeCellules.java
 *
 * Description :
 * Ce panneau affiche la liste des cellules sous forme de tableau.
 * Il permet également :
 *   - Retourner à l'accueil
 *   - Exporter les données (CSV, XML, JSON)
 *
 * Le remplissage des données est fait via la méthode chargerDonnees()
 * appelée par le Contrôleur.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelListeCellules extends JPanel {

    private Controleur monControleur;
    private JTable tableau;
    private DefaultTableModel modeleTableau;
    private JButton btnRetour;
    private JButton btnExporter; 

    /**
     * Constructeur du panneau
     * @param unControleur Contrôleur principal pour les actions
     */
    public PanelListeCellules(Controleur unControleur) {
        this.monControleur = unControleur;
        this.initialiser();
    }

    /**
     * Initialise l'interface graphique :
     * - Titre
     * - Tableau
     * - Boutons (Retour, Export)
     */
    private void initialiser() {
        this.setLayout(new BorderLayout(10, 10));

        // Titre
        JLabel titre = new JLabel("Inventaire des Cellules", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(titre, BorderLayout.NORTH);

        // Tableau
        String[] colonnes = {"Numéro", "Places", "Type", "Info Mitard"};
        this.modeleTableau = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Interdit la modification directe
            }
        };
        
        this.tableau = new JTable(this.modeleTableau);
        JScrollPane ascenseur = new JScrollPane(this.tableau);
        this.add(ascenseur, BorderLayout.CENTER);

        // Boutons
        JPanel panelBas = new JPanel(new FlowLayout());
        
        this.btnRetour = new JButton("Retour");
        this.btnRetour.addActionListener(new BoutonRetourListener());
        panelBas.add(this.btnRetour);
        
        this.btnExporter = new JButton("Exporter les données");
        this.btnExporter.addActionListener(new BoutonExporterListener());
        panelBas.add(this.btnExporter);
        
        this.add(panelBas, BorderLayout.SOUTH);
    }

    /**
     * Méthode appelée par le Contrôleur pour remplir le tableau
     * @param lesCellules Liste des cellules à afficher
     */
    public void chargerDonnees(ArrayList<Cellule> lesCellules) {
        this.modeleTableau.setRowCount(0); // Vide le tableau

        for (Cellule c : lesCellules) {
            String type = "Standard";
            String info = "-";
            if (c instanceof Mitard) {
                type = "Mitard";
                Mitard m = (Mitard) c; 
                info = m.getTempsPurger() + " jours (" + m.getRaison() + ")";
            }
            Object[] ligne = { c.getNumeroCellule(), c.getNbPlacesCellule(), type, info };
            this.modeleTableau.addRow(ligne);
        }
    }

    // --- LISTENER DU BOUTON RETOUR ---
    private class BoutonRetourListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            monControleur.afficherEcranAccueil(); 
        }
    }
    
    // --- LISTENER DU BOUTON EXPORT ---
    private class BoutonExporterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Choix du format
            String[] options = {"CSV", "XML", "JSON"};
            int choix = JOptionPane.showOptionDialog(null, "Choisissez le format d'export :", "Export",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            
            // Récupère les données actualisées via le contrôleur
            ArrayList<Cellule> liste = monControleur.recupererLesCellules();
            String resultat = "";
            
            // Génération selon le format choisi
            if (choix == 0) { // CSV
                resultat += "Numero;Places;Type;Info\n";
                for(Cellule c : liste) {
                    resultat += c.versCsv() + "\n";
                }
            }
            
            if (choix == 1) { // XML
                resultat += "<liste_cellules>\n";
                for(Cellule c : liste) {
                    resultat += c.versXml() + "\n";
                }
                resultat += "</liste_cellules>";
            }
            
            if (choix == 2) { // JSON
                resultat += "[\n";
                for(int i=0; i<liste.size(); i++) {
                    resultat += liste.get(i).versJson();
                    if (i < liste.size() -1) { resultat += ","; }
                    resultat += "\n";
                }
                resultat += "]";
            }
            
            // Affichage
            if (choix != -1) { // Si l'utilisateur n'a pas fermé la fenêtre
                JTextArea textArea = new JTextArea(resultat);
                textArea.setRows(15);
                textArea.setColumns(40);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(null, scrollPane, "Résultat de l'export", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
