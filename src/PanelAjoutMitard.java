/*
 * Auteur  : Marouane AMEJJOUD
 * Version : 1.0
 * Fichier : PanelAjoutMitard.java
 *
 * Description :
 * Ce panneau graphique permet l’ajout d’un mitard (cellule disciplinaire)
 * dans l’interface de gestion. L’utilisateur peut y renseigner :
 *   - L'ID du bâtiment dans lequel créer le mitard
 *   - Le nombre de places (par défaut 1)
 *   - La raison disciplinaire (par défaut "Disciplinaire")
 *   - Le temps de sanction en jours (par défaut 15)
 *
 * Une fois validé, le panneau transmet les informations au contrôleur
 * afin de créer le mitard dans le système.
 *
 * Rôle principal :
 * - Interface utilisateur pour la saisie et la validation des données d’un mitard.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAjoutMitard extends JPanel {

    private Controleur monControleur;
    
    private JTextField txtBatiment;
    private JTextField txtPlaces;
    private JTextField txtRaison;
    private JTextField txtTemps;
    private JButton btnValider;

    /**
     * Constructeur du panneau d'ajout de mitard.
     * @param unControleur Contrôleur principal pour la communication avec le système.
     */
    public PanelAjoutMitard(Controleur unControleur) {
        this.monControleur = unControleur;
        this.initialiser();
    }

    /**
     * Configure et initialise l’ensemble des composants graphiques du panneau.
     * Organisation :
     *   - Titre
     *   - Formulaire (4 champs : bâtiment, places, raison, temps)
     *   - Bouton de validation
     */
    private void initialiser() {
        this.setLayout(new BorderLayout(10, 10));

        JLabel titre = new JLabel("Construction d'un Mitard", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setForeground(Color.RED);
        this.add(titre, BorderLayout.NORTH);

        JPanel panelFormulaire = new JPanel(new GridLayout(4, 2, 5, 5));
        
        panelFormulaire.add(new JLabel("ID Bâtiment :"));
        this.txtBatiment = new JTextField();
        panelFormulaire.add(this.txtBatiment);

        panelFormulaire.add(new JLabel("Nombre de Places :"));
        this.txtPlaces = new JTextField("1");
        panelFormulaire.add(this.txtPlaces);
        
        panelFormulaire.add(new JLabel("Raison (Défaut) :"));
        this.txtRaison = new JTextField("Disciplinaire");
        panelFormulaire.add(this.txtRaison);
        
        panelFormulaire.add(new JLabel("Temps (Jours) :"));
        this.txtTemps = new JTextField("15");
        panelFormulaire.add(this.txtTemps);

        this.add(panelFormulaire, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        this.btnValider = new JButton("Valider le Mitard");
        this.btnValider.addActionListener(new BoutonValiderListener());
        panelBoutons.add(this.btnValider);
        
        this.add(panelBoutons, BorderLayout.SOUTH);
    }

    /**
     * Listener associé au bouton de validation.
     * Rôle :
     *   - Vérifier les champs
     *   - Convertir en valeurs numériques
     *   - Appeler le contrôleur pour créer le mitard
     *   - Réinitialiser le formulaire après succès
     */
    private class BoutonValiderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String batStr = txtBatiment.getText().trim();
            String placeStr = txtPlaces.getText().trim();
            String raison = txtRaison.getText().trim();
            String tempsStr = txtTemps.getText().trim();

            if (!batStr.isEmpty() && !placeStr.isEmpty() && !tempsStr.isEmpty()) {
                try {
                    int batiment = Integer.parseInt(batStr);
                    int places = Integer.parseInt(placeStr);
                    int temps = Integer.parseInt(tempsStr);

                    monControleur.creerNouveauMitard(places, batiment, raison, temps);
                    
                    JOptionPane.showMessageDialog(null, "Mitard construit avec succès !");
                    
                    txtBatiment.setText("");
                    txtRaison.setText("Disciplinaire");
                    txtTemps.setText("15");

                } catch (NumberFormatException erreur) {
                    JOptionPane.showMessageDialog(null, "Vérifiez les valeurs numériques.");
                }
            }
        }
    }
}
