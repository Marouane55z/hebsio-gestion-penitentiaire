import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 * Classe de test pour la classe Cellule.
 * <p>
 * Cette classe vérifie le bon fonctionnement des méthodes principales de Cellule :
 * - ajout d'un détenu
 * - gestion de la capacité maximale
 * - export CSV
 * - export JSON
 * </p>
 */
public class CelluleTest {

    /** Cellule utilisée pour les tests */
    private Cellule celluleTest;

    /**
     * Méthode exécutée avant chaque test.
     * Elle initialise une cellule vide pour un environnement de test propre.
     */
    @BeforeEach
    public void setup() {
        // Création d'une cellule de 2 places, numéro 101
        celluleTest = new Cellule(2, 101);
    }

    /**
     * Test l'ajout réussi d'un détenu dans la cellule.
     * Vérifie que le détenu est bien comptabilisé et que la recherche fonctionne.
     */
    @Test
    public void testAjoutDetenuReussi() {
        // Création d'un détenu associé à la cellule
        Detenu d1 = new Detenu(123, "Dalton", "Joe", "Vol", celluleTest);

        // Vérification du nombre de détenus dans la cellule
        assertEquals(1, celluleTest.getNbDetenus(), "Le détenu aurait dû être ajouté.");

        // Vérification que la cellule connaît bien le détenu
        assertNotEquals(-1, celluleTest.chercherUnDetenu("Dalton"), "Le détenu devrait être trouvé.");
    }

    /**
     * Test la gestion de la cellule pleine.
     * Vérifie que l'ajout au-delà de la capacité maximale retourne le message d'erreur attendu.
     */
    @Test
    public void testCellulePleine() {
        // Remplissage de la cellule (capacité = 2)
        new Detenu(1, "D1", "P1", "C1", celluleTest);
        new Detenu(2, "D2", "P2", "C2", celluleTest);

        // Tentative d'ajout d'un 3ème détenu
        Detenu d3 = new Detenu();
        d3.setNom("Trop");

        String resultat = celluleTest.ajouterUnDetenu(d3);

        assertEquals("La cellule 101 est pleine.", resultat, "Le message d'erreur est incorrect.");
    }

    /**
     * Test de l'export CSV de la cellule.
     * Vérifie que la méthode versCsv() renvoie la chaîne correctement formatée.
     */
    @Test
    public void testVersCsv() {
        // Ajout d'un détenu pour tester l'export
        new Detenu(123, "A", "B", "C", celluleTest);

        String resultat = celluleTest.versCsv();
        // Format attendu : numeroCellule;nbPlaces;nbOccupants
        assertEquals("101;2;1", resultat, "Le CSV est incorrect.");
    }

    /**
     * Test de l'export JSON de la cellule.
     * Vérifie que la méthode versJson() renvoie la chaîne JSON attendue.
     */
    @Test
    public void testVersJson() {
        String resultat = celluleTest.versJson();
        // JSON sans détenu pour simplifier le test
        assertEquals("{\"numeroCellule\":101,\"nbPlaces\":2,\"nbPlacesOccupees\":0}", resultat, "Le JSON est incorrect.");
    }
}
