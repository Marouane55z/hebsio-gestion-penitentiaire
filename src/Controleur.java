/**
 * Contrôleur principal de l'application.
 * Gère la navigation entre les écrans, les interactions entre la vue et le modèle,
 * ainsi que la création, la suppression et la récupération des données principales
 * (cellules, détenus, surveillants, directeur, mitards, etc.).
 *
 * Implémente le cœur de la logique MVC :
 * - Le modèle stocke et gère les données.
 * - Le contrôleur fait la liaison entre modèle et vue.
 * - La vue demande au contrôleur d'agir.
 *
 * @author Marouane AMEJJOUD
 * @version 1.0
 */
import java.util.ArrayList;

public class Controleur {

    private Modele leModele;
    private VuePrincipale laVuePrincipale;

    /**
     * Constructeur du contrôleur.
     * Initialise le modèle.
     */
    public Controleur() {
        this.leModele = new Modele();
    }

    /**
     * Démarre l'application en créant la vue principale et en affichant le tableau de bord.
     */
    public void demarrer() {
        this.laVuePrincipale = new VuePrincipale(this);
        this.afficherEcranAccueil();
    }

    // -----------------------------------------------------------------
    // ---------------------- NAVIGATION PANELS ------------------------
    // -----------------------------------------------------------------

    /**
     * Affiche l'écran d'ajout d'une cellule.
     */
    public void afficherEcranAjoutCellule() {
        PanelAjoutCellule panel = new PanelAjoutCellule(this);
        this.laVuePrincipale.changerPanel(panel);
    }

    /**
     * Affiche la liste des cellules.
     */
    public void afficherEcranListeCellules() {
        PanelListeCellules panel = new PanelListeCellules(this);
        ArrayList<Cellule> lesDonnees = this.leModele.getLesCellules();
        panel.chargerDonnees(lesDonnees);
        this.laVuePrincipale.changerPanel(panel);
    }

    /**
     * Affiche l'écran d'ajout d'un prisonnier.
     */
    public void afficherEcranAjoutPrisonnier() {
        PanelAjoutDetenu panel = new PanelAjoutDetenu(this);
        this.laVuePrincipale.changerPanel(panel);
    }

    /**
     * Affiche la liste des prisonniers.
     */
    public void afficherEcranListePrisonniers() {
        PanelListeDetenus panel = new PanelListeDetenus(this);
        ArrayList<Detenu> lesDonnees = this.leModele.getLesPrisonniers();
        panel.chargerDonnees(lesDonnees);
        this.laVuePrincipale.changerPanel(panel);
    }

    /**
     * Affiche l'écran d'ajout d'un surveillant.
     */
    public void afficherEcranAjoutSurveillant() {
        PanelAjoutSurveillant panel = new PanelAjoutSurveillant(this);
        this.laVuePrincipale.changerPanel(panel);
    }

    /**
     * Affiche la liste des surveillants.
     */
    public void afficherEcranListeSurveillants() {
        PanelListeSurveillants panel = new PanelListeSurveillants(this);
        ArrayList<Surveillant> liste = this.leModele.getLesSurveillants();
        panel.chargerDonnees(liste);
        this.laVuePrincipale.changerPanel(panel);
    }

    /**
     * Affiche l'écran d'ajout d'un mitard.
     */
    public void afficherEcranAjoutMitard() {
        PanelAjoutMitard panel = new PanelAjoutMitard(this);
        this.laVuePrincipale.changerPanel(panel);
    }

    /**
     * Affiche l'écran du directeur.
     */
    public void afficherEcranDirecteur() {
        PanelDirecteur panel = new PanelDirecteur(this);
        Directeur dir = this.leModele.getLeDirecteur();
        panel.remplirDonnees(dir);
        this.laVuePrincipale.changerPanel(panel);
    }

    /**
     * Affiche l'écran d'accueil (dashboard).
     */
    public void afficherEcranAccueil() {
        PanelTableauBord panel = new PanelTableauBord(this);
        this.laVuePrincipale.changerPanel(panel);
    }

    // -----------------------------------------------------------------
    // ------------------------- MÉTIER --------------------------------
    // -----------------------------------------------------------------

    /**
     * Crée une nouvelle cellule avec un ID fourni.
     * @param numero identifiant de la cellule
     * @param places nombre de places
     * @param batiment numéro du bâtiment
     */
    public void creerNouvelleCellule(int numero, int places, int batiment) {
        this.leModele.insererCellule(numero, places, batiment);
    }

    /**
     * Crée une nouvelle cellule avec un ID auto-généré.
     * @param places nombre de places
     * @param batiment bâtiment concerné
     */
    public void creerNouvelleCellule(int places, int batiment) {
        int idGenere = this.leModele.genererIdCellule(batiment);
        this.leModele.insererCellule(idGenere, places, batiment);
    }

    /**
     * Crée un nouveau prisonnier.
     */
    public void creerNouveauPrisonnier(int ecrou, String nom, String prenom, String crime, int idCellule) {
        this.leModele.insererPrisonnier(ecrou, nom, prenom, crime, idCellule);
    }

    /**
     * Supprime un prisonnier par numéro d'écrou.
     */
    public void supprimerPrisonnier(int numEcrou) {
        this.leModele.supprimerPrisonnier(numEcrou);
    }

    /**
     * Crée un nouveau surveillant.
     */
    public void creerNouveauSurveillant(String nom, String prenom, String tel, String grade) {
        this.leModele.insererSurveillant(nom, prenom, tel, grade);
    }

    /**
     * Supprime un surveillant selon son numéro de téléphone.
     */
    public void supprimerSurveillant(String tel) {
        this.leModele.supprimerSurveillant(tel);
    }

    /**
     * Crée un mitard (cellule disciplinaire) avec un ID auto-généré.
     */
    public void creerNouveauMitard(int places, int batiment, String raison, int temps) {
        int idGenere = this.leModele.genererIdCellule(batiment);
        this.leModele.insererMitard(idGenere, places, batiment, raison, temps);
    }

    /**
     * Met à jour les données du directeur.
     */
    public void mettreAJourDirecteur(String nom, String prenom, String tel) {
        this.leModele.modifierDirecteur(nom, prenom, tel);
    }

    // -----------------------------------------------------------------
    // --------------------- RECUPÉRATION DONNÉES ----------------------
    // -----------------------------------------------------------------

    public ArrayList<Cellule> recupererLesCellules() {
        return this.leModele.getLesCellules();
    }

    public ArrayList<Detenu> recupererLesPrisonniers() {
        return this.leModele.getLesPrisonniers();
    }

    public ArrayList<Surveillant> recupererLesSurveillants() {
        return this.leModele.getLesSurveillants();
    }

    public Directeur recupererLeDirecteur() {
        return this.leModele.getLeDirecteur();
    }

    public int recupererNbPrisonniers() {
        return this.leModele.getNbPrisonniers();
    }

    public int recupererNbSurveillants() {
        return this.leModele.getNbSurveillants();
    }

    public int recupererNbCellules() {
        return this.leModele.getNbCellules();
    }

    public ArrayList<Detenu> rechercherDetenu(String texte) {
        return this.leModele.rechercherPrisonnier(texte);
    }

    public ArrayList<Surveillant> rechercherSurveillant(String texte) {
        return this.leModele.rechercherSurveillant(texte);
    }
}
