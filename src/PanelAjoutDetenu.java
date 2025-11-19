import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Vue (Panneau) permettant l'incarcération d'un nouveau détenu.
 * Utilise une liste déroulante dynamique pour le choix de la cellule afin de respecter l'identification relative.
 * * @author Marouane AMEJJOUD
 * @version 1.0
 */
public class PanelAjoutDetenu extends JPanel {

    /** Référence vers le contrôleur pour les interactions métier. */
    private Controleur monControleur;
    
    private JTextField txtEcrou;
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtCrime;
    
    /** Liste déroulante pour choisir la cellule. */
    private JComboBox<IndexLibelle> cbxCellule; 
    
    private JButton btnValider;

    /**
     * Constructeur du panneau d'ajout de détenu.
     * @param unControleur Le contrôleur principal de l'application.
     */
    public PanelAjoutDetenu(Controleur unControleur) {
        this.monControleur = unControleur;
        this.initialiser();
    }

    /**
     * Initialise l'interface graphique et charge la liste des cellules.
     */
    private void initialiser() {
        this.setLayout(new BorderLayout(10, 10));

        JLabel titre = new JLabel("Incarcérer un Nouveau Prisonnier", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(titre, BorderLayout.NORTH);

        JPanel panelFormulaire = new JPanel(new GridLayout(5, 2, 5, 5));
        
        panelFormulaire.add(new JLabel("Numéro Écrou :"));
        this.txtEcrou = new JTextField();
        panelFormulaire.add(this.txtEcrou);

        panelFormulaire.add(new JLabel("Nom :"));
        this.txtNom = new JTextField();
        panelFormulaire.add(this.txtNom);
        
        panelFormulaire.add(new JLabel("Prénom :"));
        this.txtPrenom = new JTextField();
        panelFormulaire.add(this.txtPrenom);
        
        panelFormulaire.add(new JLabel("Crime :"));
        this.txtCrime = new JTextField();
        panelFormulaire.add(this.txtCrime);
        
        // --- CONFIGURATION DE LA LISTE DÉROULANTE ---
        panelFormulaire.add(new JLabel("Cellule d'affectation :"));
        
        this.cbxCellule = new JComboBox<IndexLibelle>();
        this.cbxCellule.addItem(new IndexLibelle(0, "Choisir une cellule..."));
        
        // Chargement dynamique des cellules depuis la BDD via le contrôleur
        ArrayList<Cellule> lesCellules = this.monControleur.recupererLesCellules();
        
        for (Cellule c : lesCellules) {
            int placesRestantes = c.getNbPlacesCellule() - c.getNbDetenus();
            String affichage = "Cellule " + c.getNumeroCellule() + " (" + placesRestantes + " places libres)";
            
            if (placesRestantes <= 0) {
                affichage = "Cellule " + c.getNumeroCellule() + " (PLEINE)";
            }

            IndexLibelle element = new IndexLibelle(c.getNumeroCellule(), affichage);
            this.cbxCellule.addItem(element);
        }
        
        panelFormulaire.add(this.cbxCellule);

        this.add(panelFormulaire, BorderLayout.CENTER);

        // Bouton Valider
        JPanel panelBoutons = new JPanel();
        this.btnValider = new JButton("Enregistrer l'incarcération");
        this.btnValider.addActionListener(new BoutonValiderListener());
        panelBoutons.add(this.btnValider);
        
        this.add(panelBoutons, BorderLayout.SOUTH);
    }

    /**
     * Classe interne gérant la validation du formulaire d'ajout.
     */
    private class BoutonValiderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ecrouStr = txtEcrou.getText().trim();
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String crime = txtCrime.getText().trim();

            IndexLibelle selection = (IndexLibelle) cbxCellule.getSelectedItem();
            int idCellule = selection.getIndex(); 
            String texteCellule = selection.getLibelle();

            // Validation de la capacité
            if (texteCellule.contains("(PLEINE)")) {
                JOptionPane.showMessageDialog(null, "Impossible : Cette cellule est déjà complète !", "Erreur Capacité", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validation Identification Relative (Cellule obligatoire)
            if (idCellule == 0) {
                JOptionPane.showMessageDialog(null, "Erreur : L'affectation à une cellule est obligatoire.", "Règle de gestion", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validation des champs et insertion
            if (!ecrouStr.isEmpty() && !nom.isEmpty()) {
                try {
                    int ecrou = Integer.parseInt(ecrouStr);

                    monControleur.creerNouveauPrisonnier(ecrou, nom, prenom, crime, idCellule);
                    
                    JOptionPane.showMessageDialog(null, "Prisonnier incarcéré avec succès dans la cellule " + idCellule + " !");
                    
                    // Reset du formulaire
                    txtEcrou.setText("");
                    txtNom.setText("");
                    txtPrenom.setText("");
                    txtCrime.setText("");
                    cbxCellule.setSelectedIndex(0);

                } catch (NumberFormatException erreur) {
                    JOptionPane.showMessageDialog(null, "Le numéro d'écrou doit être un nombre entier.", "Erreur Format", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            if (ecrouStr.isEmpty() || nom.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Veuillez remplir au moins le numéro d'écrou et le nom.", "Champs manquants", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}