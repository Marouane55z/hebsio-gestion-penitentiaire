import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe représentant la fenêtre principale de l'application.
 * <p>
 * Cette classe contient :
 * - La barre de menu avec toutes les fonctionnalités.
 * - Le panneau central qui change selon le choix de l'utilisateur.
 * </p>
 */
public class VuePrincipale extends JFrame {

    /** Contrôleur de l'application */
    private Controleur monControleur;

    /** Panneau central où les vues (JPanel) sont affichées */
    private JPanel panelCentral;

    // --- ÉLÉMENTS DU MENU ---

    // Menu Application
    private JMenuItem itemAccueil;
    private JMenuItem itemQuitter;

    // Menu Cellules
    private JMenuItem itemAjouterCellule;
    private JMenuItem itemAjouterMitard;
    private JMenuItem itemListerCellules;

    // Menu Prisonniers
    private JMenuItem itemAjouterPrisonnier;
    private JMenuItem itemListerPrisonniers;

    // Menu Personnel
    private JMenuItem itemGererDirecteur;
    private JMenuItem itemAjouterSurveillant;
    private JMenuItem itemListerSurveillants;

    /**
     * Constructeur de la vue principale.
     * Initialise la fenêtre et la barre de menu.
     *
     * @param unControleur Le contrôleur à associer
     */
    public VuePrincipale(Controleur unControleur) {
        this.monControleur = unControleur;
        this.initialiser();
    }

    /**
     * Initialisation de la fenêtre principale et de ses composants.
     */
    private void initialiser() {
        this.setTitle("Gestion Prison HebSIO");
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Création de la barre de menu
        JMenuBar barreMenu = new JMenuBar();

        // --- Menu Application ---
        JMenu menuApp = new JMenu("Application");
        this.itemAccueil = new JMenuItem("Tableau de Bord");
        this.itemQuitter = new JMenuItem("Quitter");

        this.itemAccueil.addActionListener(new MenuListener());
        this.itemQuitter.addActionListener(new MenuListener());

        menuApp.add(this.itemAccueil);
        menuApp.addSeparator();
        menuApp.add(this.itemQuitter);

        // --- Menu Cellules ---
        JMenu menuCellules = new JMenu("Cellules");
        this.itemAjouterCellule = new JMenuItem("Ajouter une cellule standard");
        this.itemAjouterMitard = new JMenuItem("Ajouter un Mitard");
        this.itemListerCellules = new JMenuItem("Lister les cellules");

        this.itemAjouterCellule.addActionListener(new MenuListener());
        this.itemAjouterMitard.addActionListener(new MenuListener());
        this.itemListerCellules.addActionListener(new MenuListener());

        menuCellules.add(this.itemAjouterCellule);
        menuCellules.add(this.itemAjouterMitard);
        menuCellules.add(this.itemListerCellules);

        // --- Menu Prisonniers ---
        JMenu menuPrisonniers = new JMenu("Prisonniers");
        this.itemAjouterPrisonnier = new JMenuItem("Incarcérer un prisonnier");
        this.itemListerPrisonniers = new JMenuItem("Registre des prisonniers");

        this.itemAjouterPrisonnier.addActionListener(new MenuListener());
        this.itemListerPrisonniers.addActionListener(new MenuListener());

        menuPrisonniers.add(this.itemAjouterPrisonnier);
        menuPrisonniers.add(this.itemListerPrisonniers);

        // --- Menu Personnel ---
        JMenu menuPerso = new JMenu("Personnel");
        this.itemGererDirecteur = new JMenuItem("Profil Directeur");
        this.itemAjouterSurveillant = new JMenuItem("Recruter Surveillant");
        this.itemListerSurveillants = new JMenuItem("Liste Effectifs");

        this.itemGererDirecteur.addActionListener(new MenuListener());
        this.itemAjouterSurveillant.addActionListener(new MenuListener());
        this.itemListerSurveillants.addActionListener(new MenuListener());

        menuPerso.add(this.itemGererDirecteur);
        menuPerso.addSeparator();
        menuPerso.add(this.itemAjouterSurveillant);
        menuPerso.add(this.itemListerSurveillants);

        // --- Assemblage barre de menu ---
        barreMenu.add(menuApp);
        barreMenu.add(menuCellules);
        barreMenu.add(menuPrisonniers);
        barreMenu.add(menuPerso);

        this.setJMenuBar(barreMenu);

        // Panneau central (les vues s'affichent ici)
        this.panelCentral = new JPanel(new BorderLayout());
        this.add(this.panelCentral);

        this.setVisible(true);
    }

    /**
     * Change le panneau central pour afficher la nouvelle vue.
     *
     * @param nouveauPanel Le JPanel à afficher
     */
    public void changerPanel(JPanel nouveauPanel) {
        this.panelCentral.removeAll();
        this.panelCentral.add(nouveauPanel, BorderLayout.CENTER);
        this.panelCentral.revalidate();
        this.panelCentral.repaint();
    }

    /**
     * Listener interne pour gérer les clics sur le menu.
     */
    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Menu Application
            if (e.getSource() == itemAccueil) {
                monControleur.afficherEcranAccueil();
            }
            if (e.getSource() == itemQuitter) {
                System.exit(0);
            }

            // Menu Cellules
            if (e.getSource() == itemAjouterCellule) {
                monControleur.afficherEcranAjoutCellule();
            }
            if (e.getSource() == itemAjouterMitard) {
                monControleur.afficherEcranAjoutMitard();
            }
            if (e.getSource() == itemListerCellules) {
                monControleur.afficherEcranListeCellules();
            }

            // Menu Prisonniers
            if (e.getSource() == itemAjouterPrisonnier) {
                monControleur.afficherEcranAjoutPrisonnier();
            }
            if (e.getSource() == itemListerPrisonniers) {
                monControleur.afficherEcranListePrisonniers();
            }

            // Menu Personnel
            if (e.getSource() == itemGererDirecteur) {
                monControleur.afficherEcranDirecteur();
            }
            if (e.getSource() == itemAjouterSurveillant) {
                monControleur.afficherEcranAjoutSurveillant();
            }
            if (e.getSource() == itemListerSurveillants) {
                monControleur.afficherEcranListeSurveillants();
            }
        }
    }
}
