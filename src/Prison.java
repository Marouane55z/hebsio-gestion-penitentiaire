// Fichier : Prison.java
import java.util.ArrayList;

/**
 * Classe représentant une prison.
 * <p>
 * Une prison possède un nom, une adresse, un directeur, des bâtiments et des prisonniers.
 * Cette classe permet de gérer l'ajout de prisonniers et de bâtiments ainsi que
 * l'export des informations au format CSV, XML ou JSON.
 * </p>
 */
public class Prison {

    /** Nom de la prison */
    private String nomPrison;

    /** Adresse de la prison */
    private String adresse;

    /** Directeur de la prison */
    private Directeur sonDirecteur;

    /** Liste des prisonniers */
    private ArrayList<Detenu> lesPrisonniers;

    /** Liste des bâtiments */
    private ArrayList<Batiment> lesBatiments;

    /**
     * Constructeur de la classe Prison.
     * <p>
     * Initialise le nom, l'adresse et le directeur de la prison.
     * Initialise également les listes de prisonniers et de bâtiments.
     * </p>
     *
     * @param nomPrison Nom de la prison
     * @param adresse Adresse de la prison
     * @param sonDirecteur Directeur de la prison
     */
    public Prison(String nomPrison, String adresse, Directeur sonDirecteur) {
        this.nomPrison = nomPrison;
        this.adresse = adresse;
        this.sonDirecteur = sonDirecteur;

        // Initialisation des listes
        this.lesPrisonniers = new ArrayList<>();
        this.lesBatiments = new ArrayList<>();
    }

    // ========================================================================
    //                             GETTERS & SETTERS
    // ========================================================================

    public String getNomPrison() {
        return this.nomPrison;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Directeur getSonDirecteur() {
        return this.sonDirecteur;
    }

    public ArrayList<Detenu> getLesPrisonniers() {
        return this.lesPrisonniers;
    }

    public ArrayList<Batiment> getLesBatiments() {
        return this.lesBatiments;
    }

    public void setSonDirecteur(Directeur sonDirecteur) {
        this.sonDirecteur = sonDirecteur;
    }

    // ========================================================================
    //                             METHODES D'AJOUT
    // ========================================================================

    /**
     * Ajoute un bâtiment à la prison si celui-ci n'existe pas déjà.
     *
     * @param unBatiment Bâtiment à ajouter
     */
    public void ajouterBatiment(Batiment unBatiment) {
        if (unBatiment != null && !this.lesBatiments.contains(unBatiment)) {
            this.lesBatiments.add(unBatiment);
        }
    }

    /**
     * Ajoute un prisonnier à la prison si celui-ci n'existe pas déjà.
     *
     * @param unPrisonnier Prisonnier à ajouter
     */
    public void ajouterPrisonnier(Detenu unPrisonnier) {
        if (unPrisonnier != null && !this.lesPrisonniers.contains(unPrisonnier)) {
            this.lesPrisonniers.add(unPrisonnier);
        }
    }

    // ========================================================================
    //                             METHODES DE FORMATAGE
    // ========================================================================

    /**
     * Exporte les données de la prison au format CSV.
     *
     * @return Chaîne CSV représentant la prison
     */
    public String versCsv() {
        String chaine = "";
        chaine += "Prison;" + this.nomPrison + ";" + this.adresse + "\n";

        chaine += "Directeur;\n";
        chaine += this.sonDirecteur.versCsv() + "\n";

        chaine += "Batiments;\n";
        for (Batiment b : this.lesBatiments) {
            chaine += b.versCsv();
        }

        chaine += "Prisonniers;\n";
        for (Detenu d : this.lesPrisonniers) {
            chaine += d.versCsv() + "\n";
        }

        return chaine;
    }

    /**
     * Exporte les données de la prison au format XML.
     *
     * @return Chaîne XML représentant la prison
     */
    public String versXml() {
        String chaine = "";
        chaine += "<prison>\n";
        chaine += "\t<nom>" + this.nomPrison + "</nom>\n";
        chaine += "\t<adresse>" + this.adresse + "</adresse>\n";

        chaine += "\t<directeur>\n";
        chaine += "\t\t" + this.sonDirecteur.versXml() + "\n";
        chaine += "\t</directeur>\n";

        chaine += "\t<batiments>\n";
        for (Batiment b : this.lesBatiments) {
            chaine += "\t\t" + b.versXml() + "\n";
        }
        chaine += "\t</batiments>\n";

        chaine += "\t<prisonniers>\n";
        for (Detenu d : this.lesPrisonniers) {
            chaine += "\t\t" + d.versXml() + "\n";
        }
        chaine += "\t</prisonniers>\n";

        chaine += "</prison>";
        return chaine;
    }

    /**
     * Exporte les données de la prison au format JSON.
     *
     * @return Chaîne JSON représentant la prison
     */
    public String versJson() {
        String chaine = "";
        chaine += "{\n";
        chaine += "\t\"nomPrison\":\"" + this.nomPrison + "\",\n";
        chaine += "\t\"adresse\":\"" + this.adresse + "\",\n";

        chaine += "\t\"directeur\":" + this.sonDirecteur.versJson() + ",\n";

        // Tableau de bâtiments
        chaine += "\t\"batiments\":[\n";
        int cptBat = 0;
        for (Batiment b : this.lesBatiments) {
            chaine += "\t\t" + b.versJson();
            cptBat++;
            if (cptBat < this.lesBatiments.size()) {
                chaine += ",";
            }
            chaine += "\n";
        }
        chaine += "\t],\n";

        // Tableau de prisonniers
        chaine += "\t\"prisonniers\":[\n";
        int cptPrisonnier = 0;
        for (Detenu d : this.lesPrisonniers) {
            chaine += "\t\t" + d.versJson();
            cptPrisonnier++;
            if (cptPrisonnier < this.lesPrisonniers.size()) {
                chaine += ",";
            }
            chaine += "\n";
        }
        chaine += "\t]\n";

        chaine += "}";
        return chaine;
    }
}
