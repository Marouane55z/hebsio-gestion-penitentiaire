/*
 * Auteur  : Marouane AMEJJOUD
 * Version : 1.0
 * Fichier : PanelAjoutSurveillant.java
 *
 * Description :
 * Ce panneau graphique permet d’ajouter un nouveau surveillant dans le système.
 * L’utilisateur peut y renseigner :
 *   - Nom
 *   - Prénom
 *   - Numéro de téléphone
 *   - Grade
 *
 * Le contrôleur est ensuite chargé d’enregistrer le surveillant dans la base
 * de données ou la structure de gestion interne. Une partie des champs est
 * obligatoire (nom et téléphone).
 *
 * Rôle général :
 * - Interface de saisie et formulaire pour la création d’un surveillant.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAjoutSurveillant extends JPanel {

    private Controleur monControleur;
    
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtTel;
    private JTextField txtGrade;
    private JButton btnValider;

    /**
     * Constructeur du panneau d'ajout d'un surveillant.
     * @param unControleur Contrôleur principal pour la communication avec le système.
     */
    public PanelAjoutSurveillant(Controleur unControleur) {
        this.monControleur = unControleur;
        this.initialiser();
    }

    /**
     * Initialise et configure l’interface graphique :
     * - Zone de titre
     * - Formulaire de 4 champs (nom, prénom, téléphone, grade)
     * - Bouton de validation
     */
    private void initialiser() {
        this.setLayout(new BorderLayout(10, 10));

        JLabel titre = new JLabel("Recruter un Surveillant", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
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
        
        panelFormulaire.add(new JLabel("Grade :"));
        this.txtGrade = new JTextField();
        panelFormulaire.add(this.txtGrade);

        this.add(panelFormulaire, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        this.btnValider = new JButton("Valider le recrutement");
        this.btnValider.addActionListener(new BoutonValiderListener());
        panelBoutons.add(this.btnValider);
        
        this.add(panelBoutons, BorderLayout.SOUTH);
    }

    /**
     * Listener du bouton de validation.
     * Fonction :
     *   - Récupère les données du formulaire
     *   - Vérifie les champs obligatoires (nom + téléphone)
     *   - Appelle le contrôleur pour créer le surveillant
     *   - Réinitialise la zone de saisie
     *   - Affiche un message d’erreur si nécessaire
     */
    private class BoutonValiderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String tel = txtTel.getText().trim();
            String grade = txtGrade.getText().trim();

            if (!nom.isEmpty() && !tel.isEmpty()) {
                monControleur.creerNouveauSurveillant(nom, prenom, tel, grade);
                JOptionPane.showMessageDialog(null, "Surveillant ajouté !");
                
                txtNom.setText("");
                txtPrenom.setText("");
                txtTel.setText("");
                txtGrade.setText("");
            }
            
            if (nom.isEmpty() || tel.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Nom et Téléphone obligatoires.", 
                    "Erreur", 
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }
}
