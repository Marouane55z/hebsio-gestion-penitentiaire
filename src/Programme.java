/**
 * Classe principale du projet.
 * <p>
 * Elle contient la méthode main qui lance l'application.
 * Le programme crée un objet Controleur et démarre l'interface.
 * </p>
 */
public class Programme {

    /**
     * Méthode principale du programme.
     * <p>
     * Elle est exécutée au lancement du projet et initialise le contrôleur principal.
     * </p>
     *
     * @param args Paramètres éventuels de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // Création du contrôleur principal qui gère l'application
        Controleur monControleur = new Controleur();

        // Démarrage de l'application (affichage de l'accueil)
        monControleur.demarrer();
    }
}
