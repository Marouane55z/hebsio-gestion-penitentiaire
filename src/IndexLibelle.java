/**
 * Classe utilitaire permettant de gérer les éléments d'une liste déroulante (JComboBox).
 * <p>
 * Cette classe permet de stocker conjointement un identifiant technique (caché)
 * et un libellé textuel (affiché à l'utilisateur). Elle facilite la récupération
 * des clés primaires lors de la sélection d'un item dans l'interface graphique.
 * </p>
 * * @author Marouane AMEJJOUD
 * @version 1.0
 */
public class IndexLibelle {
    
    /** Identifiant technique de l'objet (ex: Clé primaire en base de données). */
    private int index;

    /** Texte affiché à l'utilisateur dans l'interface graphique. */
    private String libelle;

    /**
     * Constructeur de la classe IndexLibelle.
     * * @param index   L'identifiant unique (entier) de l'élément.
     * @param libelle La chaîne de caractères à afficher dans la liste.
     */
    public IndexLibelle(int index, String libelle) {
        this.index = index;
        this.libelle = libelle;
    }

    /**
     * Récupère l'identifiant technique de l'élément.
     * * @return L'index (int) correspondant à l'ID en base de données.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Récupère le libellé textuel de l'élément.
     * * @return Le libellé (String) affiché.
     */
    public String getLibelle() {
        return this.libelle;
    }

    /**
     * Retourne la représentation textuelle de l'objet.
     * <p>
     * C'est cette méthode qui est utilisée implicitement par les composants Swing
     * (comme JComboBox) pour déterminer le texte à afficher à l'écran.
     * </p>
     * * @return Le libellé de l'élément.
     */
    @Override
    public String toString() {
        return this.libelle;
    }
}