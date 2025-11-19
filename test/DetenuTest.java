import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Detenu.
 * <p>
 * Cette classe vérifie que les liens entre Detenu et Cellule fonctionnent correctement,
 * et que les méthodes d'export CSV et JSON renvoient les chaînes attendues.
 * </p>
 */
public class DetenuTest {

    /**
     * Test de l'identification relative entre un détenu et sa cellule.
     * <p>
     * Vérifie que :
     * 1. Le détenu connaît sa cellule.
     * 2. La cellule contient automatiquement le détenu dans sa liste.
     * </p>
     */
    @Test
    public void testIdentificationRelative() {
        Cellule c = new Cellule(10, 500);
        Detenu d = new Detenu(999, "Test", "Jean", "Test", c);

        // Vérifie que le détenu connaît bien sa cellule
        assertNotNull(d.getSaCellule(), "Le détenu doit avoir une cellule.");
        assertEquals(500, d.getSaCellule().getNumeroCellule(), "Le numéro de cellule doit correspondre.");

        // Vérifie que la cellule contient le détenu
        assertEquals(1, c.getNbDetenus(), "La cellule aurait dû recevoir le détenu automatiquement.");
    }

    /**
     * Test de l'export CSV pour un détenu.
     * <p>
     * Le format attendu est : Ecrou;Nom;Prenom;Crime;NumCellule
     * </p>
     */
    @Test
    public void testVersCsv() {
        Cellule c = new Cellule(1, 100);
        Detenu d = new Detenu(123, "Valjean", "Jean", "Vol", c);

        String attendu = "123;Valjean;Jean;Vol;100";
        assertEquals(attendu, d.versCsv(), "Le CSV du détenu est incorrect.");
    }

    /**
     * Test de l'export JSON pour un détenu.
     * <p>
     * Vérifie que la méthode versJson() renvoie le format attendu avec guillemets.
     * </p>
     */
    @Test
    public void testVersJson() {
        Cellule c = new Cellule(1, 100);
        Detenu d = new Detenu(123, "Valjean", "Jean", "Vol", c);

        String attendu = "{\"numeroEcrou\":123,\"nom\":\"Valjean\",\"prenom\":\"Jean\",\"crime\":\"Vol\",\"numeroCellule\":100}";
        assertEquals(attendu, d.versJson(), "Le JSON du détenu est incorrect.");
    }
}
