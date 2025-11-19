/**
 * Classe représentant un Surveillant.
 * <p>
 * Hérite de la classe abstraite Personnel.
 * Un surveillant possède un nom, un prénom, un numéro de téléphone et un grade.
 * </p>
 */
public class Surveillant extends Personnel {
    
    /** Grade du surveillant (ex: "Chef de Garde") */
    private String grade;
    
    /**
     * Constructeur pour un Surveillant.
     * <p>
     * Appelle le constructeur de la classe mère Personnel pour initialiser le nom,
     * le prénom et le numéro de téléphone, puis définit le grade.
     * </p>
     *
     * @param nom Nom du surveillant
     * @param prenom Prénom du surveillant
     * @param numeroTel Numéro de téléphone
     * @param grade Grade du surveillant
     */
    public Surveillant(String nom, String prenom, String numeroTel, String grade) {
        super(nom, prenom, numeroTel); // Appel du constructeur de Personnel
        this.grade = grade;
    }

    // --- GETTER & SETTER ---

    /**
     * Retourne le grade du surveillant.
     * @return grade
     */
    public String getGrade() {
        return this.grade;
    }

    /**
     * Définit le grade du surveillant.
     * @param grade Nouveau grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    // --- METHODES DE FORMATAGE ---

    /**
     * Exporte les données du surveillant au format CSV.
     * Format : nom;prenom;numeroTel;grade
     * @return chaîne CSV
     */
    public String versCsv() {
        String chaine = "";
        chaine += this.getNom() + ";";       // Depuis Personnel
        chaine += this.getPrenom() + ";";    // Depuis Personnel
        chaine += this.getNumeroTel() + ";"; // Depuis Personnel
        chaine += this.grade;                // Depuis Surveillant
        return chaine;
    }
    
    /**
     * Exporte les données du surveillant au format XML.
     * @return chaîne XML
     */
    public String versXml() {
        String chaine = "";
        chaine += "<surveillant>";
        chaine += "<nom>" + this.getNom() + "</nom>";
        chaine += "<prenom>" + this.getPrenom() + "</prenom>";
        chaine += "<numeroTel>" + this.getNumeroTel() + "</numeroTel>";
        chaine += "<grade>" + this.grade + "</grade>";
        chaine += "</surveillant>";
        return chaine;
    }
    
    /**
     * Exporte les données du surveillant au format JSON.
     * <p>
     * Chaque champ est entouré de guillemets.
     * </p>
     * @return chaîne JSON
     */
    public String versJson() {
        String chaine = "";
        chaine += "{";
        chaine += "\"nom\":\"" + this.getNom() + "\",";
        chaine += "\"prenom\":\"" + this.getPrenom() + "\",";
        chaine += "\"numeroTel\":\"" + this.getNumeroTel() + "\",";
        chaine += "\"grade\":\"" + this.grade + "\""; // Pas de virgule finale
        chaine += "}";
        return chaine;
    }
}
