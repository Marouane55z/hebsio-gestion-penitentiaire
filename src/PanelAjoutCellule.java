/**
 * Panneau Swing permettant l'ajout d'une nouvelle cellule dans l'application.
 * Le numéro de la cellule est généré automatiquement par le contrôleur.
 * 
 * @author Marouane AMEJJOUD
 * @version 1.0
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAjoutCellule extends JPanel {

    /** Contrôleur principal permettant la création d'une cellule */
    private Controleur monControleur;
    
    /** Champ texte pour saisir le nombre de places */
    private JTextField txtPlaces;

    /** Champ texte pour saisir l'ID du bâtiment */
    private JTextField txtBatiment;

    /** Bouton de validation de l'ajout */
    private JButton btnValider;

    /**
     * Constructeur du panneau d'ajout de cellule.
     * @param unControleur le contrôleur gérant les actions.
     */
    public PanelAjoutCellule(Controleur unControleur) {
        this.monControleur = unControleur;
        this.initialiser();
    }

    /**
     * Initialise la mise en page et les composants graphiques.
     */
    private void initialiser() {
        this.setLayout(new BorderLayout(10, 10));

        JLabel titre = new JLabel("Ajouter une Cellule (Numéro Auto)", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(titre, BorderLayout.NORTH);

        JPanel panelFormulaire = new JPanel(new GridLayout(2, 2, 5, 5));
        
        panelFormulaire.add(new JLabel("ID Bâtiment :"));
        this.txtBatiment = new JTextField();
        panelFormulaire.add(this.txtBatiment);

        panelFormulaire.add(new JLabel("Nombre de Places :"));
        this.txtPlaces = new JTextField();
        panelFormulaire.add(this.txtPlaces);
        
        this.add(panelFormulaire, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel();
        this.btnValider = new JButton("Générer et Valider");
        this.btnValider.addActionListener(new BoutonValiderListener());
        panelBoutons.add(this.btnValider);
        
        this.add(panelBoutons, BorderLayout.SOUTH);
    }

    /**
     * Listener gérant l'action du bouton de validation.
     */
    private class BoutonValiderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String placeStr = txtPlaces.getText().trim();
            String batStr = txtBatiment.getText().trim();

            if (!placeStr.isEmpty() && !batStr.isEmpty()) {
                try {
                    int places = Integer.parseInt(placeStr);
                    int batiment = Integer.parseInt(batStr);

                    monControleur.creerNouvelleCellule(places, batiment);
                    
                    JOptionPane.showMessageDialog(null, "Cellule ajoutée ! (Numéro généré automatiquement)");
                    
                    txtPlaces.setText("");
                    txtBatiment.setText("");

                } catch (NumberFormatException erreur) {
                    JOptionPane.showMessageDialog(null, "Valeurs incorrectes.");
                }
            }
        }
    }
}