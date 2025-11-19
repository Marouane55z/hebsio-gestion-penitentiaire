/**
 * Représente un détenu au sein de l'établissement pénitentiaire.
 * Contient ses informations personnelles, son numéro d'écrou ainsi que
 * la cellule à laquelle il est éventuellement rattaché.
 * 
 * Fournit également des méthodes d'exportation au format texte, CSV, XML et JSON.
 *
 * @author Marouane AMEJJOUD
 * @version 1.0
 */
public class Detenu {

    private int numeroEcrou;
    private String nom;
    private String prenom;
    private String crime;
    private Cellule saCellule;

    /**
     * Constructeur vide.
     * Initialise un détenu sans cellule associée.
     */
    public Detenu() {}

    /**
     * Constructeur principal.
     * Initialise toutes les informations du détenu
     * et l'associe automatiquement à sa cellule.
     *
     * @param numeroEcrou numéro d'écrou unique
     * @param nom nom du détenu
     * @param prenom prénom du détenu
     * @param crime infraction commise
     * @param saCellule cellule où il est affecté
     */
    public Detenu(int numeroEcrou, String nom, String prenom, String crime, Cellule saCellule) {
        this.numeroEcrou = numeroEcrou;
        this.nom = nom;
        this.prenom = prenom;
        this.crime = crime;
        this.saCellule = saCellule;
        this.saCellule.ajouterUnDetenu(this);
    }

    // ------------------ GETTERS ------------------

    /** @return le numéro d'écrou du détenu */
    public int getNumeroEcrou() {
        return this.numeroEcrou;
    }

    /** @return le nom du détenu */
    public String getNom() {
        return this.nom;
    }

    /** @return le prénom du détenu */
    public String getPrenom() {
        return this.prenom;
    }

    /** @return le crime associé au détenu */
    public String getCrime() {
        return this.crime;
    }

    /**
     * Retourne une description textuelle de la cellule du détenu.
     * @return description de la cellule, ou un message si aucune cellule n'est assignée
     */
    public String getCellule() {
        String chaine = "Aucune cellule assignée";
        if (this.saCellule != null) {
            chaine = this.saCellule.versChaine();
        }
        return chaine;
    }

    /** @return l'objet Cellule associé au détenu */
    public Cellule getSaCellule() {
        return this.saCellule;
    }

    // ------------------ SETTERS ------------------

    /** @param numeroEcrou nouveau numéro d'écrou */
    public void setNumeroEcrou(int numeroEcrou) {
        this.numeroEcrou = numeroEcrou;
    }

    /** @param nom nouveau nom */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /** @param prenom nouveau prénom */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /** @param crime nouveau crime */
    public void setCrime(String crime) {
        this.crime = crime;
    }

    /**
     * Affecte une nouvelle cellule au détenu.
     * @param saCellule nouvelle cellule
     */
    public void setSaCellule(Cellule saCellule) {
        this.saCellule = saCellule;
    }

    // ------------------ MÉTHODES UTILITAIRES ------------------

    /**
     * Retourne toutes les informations du détenu au format texte.
     * @return chaîne descriptive du détenu
     */
    public String versChaine() {
        String chaine = "";
        chaine += "\n Numéro d'écrou : " + this.numeroEcrou + "\n";
        chaine += "\n Nom : " + this.nom + "\n";
        chaine += "\n Prénom : " + this.prenom + "\n";
        chaine += "\n Crime : " + this.crime + "\n";

        String infoCellule = "\n Sa Cellule : Aucune\n";
        if (this.saCellule != null) {
            infoCellule = "\n Sa Cellule (Num " + this.saCellule.getNumeroCellule() + ") : \n" + this.saCellule.versChaine() + "\n";
        }
        chaine += infoCellule;

        return chaine;
    }

    /**
     * Exporte les données du détenu au format CSV.
     * Format : numeroEcrou;nom;prenom;crime;numeroCellule
     *
     * @return ligne CSV représentant le détenu
     */
    public String versCsv() {
        String chaine = "";

        chaine += this.numeroEcrou + ";";
        chaine += this.nom + ";";
        chaine += this.prenom + ";";
        chaine += this.crime + ";";

        int numCellule = 0;
        if (this.saCellule != null) {
            numCellule = this.saCellule.getNumeroCellule();
        }
        chaine += numCellule;

        return chaine;
    }

    /**
     * Exporte les données du détenu au format JSON.
     * @return objet JSON sous forme de chaîne
     */
    public String versJson() {
        String chaine = "";

        chaine += "{";
        chaine += "\"numeroEcrou\":" + this.numeroEcrou + ",";
        chaine += "\"nom\":\"" + this.nom + "\",";
        chaine += "\"prenom\":\"" + this.prenom + "\",";
        chaine += "\"crime\":\"" + this.crime + "\",";

        int numCellule = 0;
        if (this.saCellule != null) {
            numCellule = this.saCellule.getNumeroCellule();
        }
        chaine += "\"numeroCellule\":" + numCellule;
        chaine += "}";

        return chaine;
    }

    /**
     * Exporte les données du détenu au format XML.
     * @return balises XML représentant le détenu
     */
    public String versXml() {
        String chaine = "";

        chaine += "<detenu>";
        chaine += "<numeroEcrou>" + this.numeroEcrou + "</numeroEcrou>";
        chaine += "<nom>" + this.nom + "</nom>";
        chaine += "<prenom>" + this.prenom + "</prenom>";
        chaine += "<crime>" + this.crime + "</crime>";

        int numCellule = 0;
        if (this.saCellule != null) {
            numCellule = this.saCellule.getNumeroCellule();
        }
        chaine += "<numeroCellule>" + numCellule + "</numeroCellule>";
        chaine += "</detenu>";

        return chaine;
    }
}