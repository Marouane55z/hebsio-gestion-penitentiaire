/**
 * Classe abstraite représentant un membre du personnel.
 * <p>
 * Cette classe sert de base pour tous les types de personnel
 * de la prison, comme le Directeur ou le Surveillant.
 * Elle contient les informations communes à tous : nom, prénom et numéro de téléphone.
 * </p>
 */
public abstract class Personnel {
    
    /** Nom du membre du personnel */
    private String nom;
    
    /** Prénom du membre du personnel */
    private String prenom;
    
    /** Numéro de téléphone du membre du personnel */
    private String numeroTel;

    /**
     * Constructeur de la classe Personnel.
     * <p>
     * Il sera appelé par les classes filles (ex: Directeur, Surveillant).
     * </p>
     *
     * @param nom Le nom du personnel
     * @param prenom Le prénom du personnel
     * @param numeroTel Le numéro de téléphone du personnel
     */
    public Personnel(String nom, String prenom, String numeroTel) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTel = numeroTel;
    }

    // ========================================================================
    //                                  GETTERS
    // ========================================================================

    /**
     * Retourne le nom du personnel.
     *
     * @return le nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Retourne le prénom du personnel.
     *
     * @return le prénom
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * Retourne le numéro de téléphone du personnel.
     *
     * @return le numéro de téléphone
     */
    public String getNumeroTel() {
        return this.numeroTel;
    }

    // ========================================================================
    //                                  SETTERS
    // ========================================================================

    /**
     * Modifie le nom du personnel.
     *
     * @param nom Le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Modifie le prénom du personnel.
     *
     * @param prenom Le nouveau prénom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Modifie le numéro de téléphone du personnel.
     *
     * @param numeroTel Le nouveau numéro de téléphone
     */
    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }
}
