// Fichier : DirecteurTest.java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

/**
 * Classe de test pour la classe Directeur.
 * <p>
 * Vérifie le bon fonctionnement des méthodes d'export (CSV, XML, JSON)
 * de la classe Directeur.
 * </p>
 */
public class DirecteurTest {

    // Modèle de test : date d'intégration fixe
    private LocalDate dateTest = LocalDate.of(2020, 5, 15);
    private Directeur directeurTest = new Directeur("Dupont", "Charles", "0606060606", dateTest);

    /**
     * Test de l'export CSV pour le directeur.
     * <p>
     * Le format attendu est : nom;prenom;numeroTel;dateIntegration
     * </p>
     */
    @Test
    public void testVersCsv() {
        String resultatCsv = this.directeurTest.versCsv();

        String resultatAttendu = "Dupont;Charles;0606060606;2020-05-15";
        assertEquals(resultatAttendu, resultatCsv, "Le CSV du directeur est incorrect.");
    }

    /**
     * Test de l'export XML pour le directeur.
     * <p>
     * Vérifie que la chaîne XML contient correctement les balises et valeurs.
     * </p>
     */
    @Test
    public void testVersXml() {
        String resultatXml = this.directeurTest.versXml();

        String resultatAttendu = "<directeur>" +
                                 "<nom>Dupont</nom>" +
                                 "<prenom>Charles</prenom>" +
                                 "<numeroTel>0606060606</numeroTel>" +
                                 "<dateIntegration>2020-05-15</dateIntegration>" +
                                 "</directeur>";
        assertEquals(resultatAttendu, resultatXml, "Le XML du directeur est incorrect.");
    }

    /**
     * Test de l'export JSON pour le directeur.
     * <p>
     * Vérifie que la chaîne JSON contient correctement les clés et valeurs.
     * </p>
     */
    @Test
    public void testVersJson() {
        String resultatJson = this.directeurTest.versJson();

        String resultatAttendu = "{\"nom\":\"Dupont\",\"prenom\":\"Charles\",\"numeroTel\":\"0606060606\",\"dateIntegration\":\"2020-05-15\"}";
        assertEquals(resultatAttendu, resultatJson, "Le JSON du directeur est incorrect.");
    }
}
