/*
 * Auteur  : Marouane AMEJJOUD
 * Version : 1.0
 * Fichier : PanelDirecteur.java
 *
 * Description :
 * Ce panneau permet de visualiser et modifier les informations du Directeur.
 * Les informations affichées sont :
 *   - Nom
 *   - Prénom
 *   - Téléphone
 *   - Date d'intégration (lecture seule)
 *
 * L'utilisateur peut modifier le nom, prénom et téléphone et valider les modifications.
 * Le contrôleur prend en charge la mise à jour des données.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelDirecteur extends JPanel {

    private Controleur monControleur;
    
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtTel;
    private JTextField txtDate; // Lecture seule
    private JButton btnModifier;

    /**
     * Constructeur du panneau Directeur
     * @param unControleur Contrôleur principal pour les actions
     */
    public PanelDirecteur(Controleur unControleur) {
        this.monControleur = unControleur;
        this.initialiser();
    }

    /**
     * Initialise l'interface graphique :
     * - Titre
     * - Formulaire avec les champs
     * - Bouton de modification
     */
    private void initialiser() {
        this.setLayout(new BorderLayout(10, 10));

        JLabel titre = new JLabel("Profil du Directeur", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        titre.setForeground(Color.BLUE);
        this.add(titre, BorderLayout.NORTH);

        JPanel panelFormulaire = new JPanel(new GridLayout(4, 2, 5, 5));
        
        panelFormulaire.add(new JLabel("Nom :"));
        this.txtNom = new JTextField();
        panelFormulaire.add(this.txtNom);

        panelFormulaire.add(new JLabel("Prénom :"));
        this.txtPrenom = new JTextField();
        panelFormulaire.add(this.txtPrenom);
        
        panelFormulaire.add(new JLabel("Téléphone :"));
        this.txtTel = new JTextField();
        panelFormulaire.add(this.txtTel);
        
        panelFormulaire.add(new JLabel("Date d'intégration :"));
        this.txtDate = new JTextField();
        this.txtDate.setEditable(false); // Lecture seule
        panelFormulaire.add(this.txtDate);

        this.add(panelFormulaire, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        this.btnModifier = new JButton("Enregistrer les modifications");
        this.btnModifier.addActionListener(new BoutonModifierListener());
        panelBoutons.add(this.btnModifier);
        
        this.add(panelBoutons, BorderLayout.SOUTH);
    }

    /**
     * Remplit les champs avec les informations du Directeur
     * @param unDirecteur Objet Directeur contenant les données
     */
    public void remplirDonnees(Directeur unDirecteur) {
        if (unDirecteur != null) {
            this.txtNom.setText(unDirecteur.getNom());
            this.txtPrenom.setText(unDirecteur.getPrenom());
            this.txtTel.setText(unDirecteur.getNumeroTel());
            this.txtDate.setText(unDirecteur.getDateIntegration().toString());
        } 
        
        if (unDirecteur == null) {
            JOptionPane.showMessageDialog(this, "Erreur : Aucun directeur trouvé (ID 1) en base.");
        }
    }

    // --- LISTENER DU BOUTON MODIFIER ---
    private class BoutonModifierListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String tel = txtTel.getText().trim();

            if (!nom.isEmpty() && !tel.isEmpty()) {
                monControleur.mettreAJourDirecteur(nom, prenom, tel);
                JOptionPane.showMessageDialog(null, "Informations mises à jour !");
            }
            
            if (nom.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Le nom ne peut pas être vide.");
            }
        }
    }
}
