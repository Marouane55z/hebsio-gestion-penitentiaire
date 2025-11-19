// Fichier : SurveillantTest.java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Surveillant.
 * Vérifie les méthodes de formatage : CSV et XML.
 */
public class SurveillantTest {

    /**
     * Test des méthodes d'export CSV et XML pour un surveillant.
     */
    @Test
    public void testFormats() {
        // Création d'un surveillant de test
        Surveillant s = new Surveillant("Javert", "Insp", "0102030405", "Chef");

        // --- Test CSV ---
        // Format attendu : nom;prenom;numeroTel;grade
        String csvAttendu = "Javert;Insp;0102030405;Chef";
        assertEquals(csvAttendu, s.versCsv(), "Le CSV du surveillant est incorrect.");

        // --- Test XML ---
        // Vérifie que toutes les balises sont présentes et correctement fermées
        String xmlAttendu = "<surveillant><nom>Javert</nom><prenom>Insp</prenom><numeroTel>0102030405</numeroTel><grade>Chef</grade></surveillant>";
        assertEquals(xmlAttendu, s.versXml(), "Le XML du surveillant est incorrect.");
    }
}
