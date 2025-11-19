//Fichier : Directeur.java
import java.time.LocalDate; // Ne pas oublier l'import !
/**
 * Représente le directeur de l'établissement pénitentiaire.
 * Hérite de la classe {@link Personnel} et ajoute la gestion de la date d'intégration.
 * 
 * Cette classe fournit également des méthodes d'exportation au format CSV, XML et JSON.
 * 
 * @author Marouane AMEJJOUD
 * @version 1.0
 */
public class Directeur extends Personnel {

    /** Date d'intégration du directeur dans l'établissement. */
    private LocalDate dateIntegration;

    /**
     * Constructeur du directeur.
     * Appelle le constructeur de la classe mère {@link Personnel}.
     *
     * @param nom Nom du directeur
     * @param prenom Prénom du directeur
     * @param numeroTel Numéro de téléphone du directeur
     * @param dateIntegration Date d'intégration dans l'établissement
     */
    public Directeur(String nom, String prenom, String numeroTel, LocalDate dateIntegration) {
        super(nom, prenom, numeroTel);
        this.dateIntegration = dateIntegration;
    }

    /**
     * Retourne la date d'intégration du directeur.
     *
     * @return La date d'intégration
     */
    public LocalDate getDateIntegration() {
        return this.dateIntegration;
    }

    /**
     * Modifie la date d'intégration du directeur.
     *
     * @param dateIntegration Nouvelle date d'intégration
     */
    public void setDateIntegration(LocalDate dateIntegration) {
        this.dateIntegration = dateIntegration;
    }

    /**
     * Exporte les informations du directeur au format CSV.
     * Format : nom;prenom;numeroTel;dateIntegration
     *
     * @return Chaîne CSV représentant le directeur
     */
    public String versCsv() {
        return this.getNom() + ";" + this.getPrenom() + ";" + this.getNumeroTel() + ";" + this.dateIntegration;
    }

    /**
     * Exporte les informations du directeur au format XML.
     *
     * @return Chaîne XML représentant le directeur
     */
    public String versXml() {
        String chaine = "";
        chaine += "<directeur>";
        chaine += "<nom>" + this.getNom() + "</nom>";
        chaine += "<prenom>" + this.getPrenom() + "</prenom>";
        chaine += "<numeroTel>" + this.getNumeroTel() + "</numeroTel>";
        chaine += "<dateIntegration>" + this.dateIntegration + "</dateIntegration>";
        chaine += "</directeur>";
        return chaine;
    }

    /**
     * Exporte les informations du directeur au format JSON.
     *
     * @return Objet JSON sous forme de chaîne
     */
    public String versJson() {
        String chaine = "";
        chaine += "{";
        chaine += "\"nom\":\"" + this.getNom() + "\",";
        chaine += "\"prenom\":\"" + this.getPrenom() + "\",";
        chaine += "\"numeroTel\":\"" + this.getNumeroTel() + "\",";
        chaine += "\"dateIntegration\":\"" + this.dateIntegration + "\"";
        chaine += "}";
        return chaine;
    }
}