// Fichier : MitardTest.java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Mitard.
 * <p>
 * Vérifie l'héritage depuis Cellule et le bon fonctionnement
 * des méthodes d'export CSV et JSON spécifiques au Mitard.
 * </p>
 */
public class MitardTest {

    /**
     * Test de l'héritage et des formats d'export (CSV et JSON) pour un Mitard.
     */
    @Test
    public void testHeritageEtFormat() {
        // Création d'un Mitard : 1 place, numéro 999, raison "Bagarre", durée 15 jours
        Mitard m = new Mitard(1, 999, "Bagarre", 15);

        // --- Test CSV ---
        // Format attendu : numeroCellule;places;occupants;raison;temps
        String csvAttendu = "999;1;0;Bagarre;15";
        assertEquals(csvAttendu, m.versCsv(), "Le CSV du Mitard est incorrect.");

        // --- Test JSON ---
        // On vérifie que la chaîne contient la raison et le temps de purger
        String json = m.versJson();
        assertTrue(json.contains("\"raison\":\"Bagarre\""), "Le JSON doit contenir la raison.");
        assertTrue(json.contains("\"tempsPurger\":15"), "Le JSON doit contenir le temps.");
    }
}
