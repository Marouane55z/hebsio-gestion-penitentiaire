// Fichier : PrisonTest.java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;

/**
 * Classe de test pour la classe Prison.
 * <p>
 * Vérifie les méthodes de formatage : CSV, XML et JSON.
 * Assure que les données des bâtiments, prisonniers et directeur sont correctement exportées.
 * </p>
 */
public class PrisonTest {

    private Prison prisonTest;
    private Directeur directeur;
    private Batiment batimentA;
    private Detenu detenu1;
    private Cellule cellule101;

    /**
     * Initialisation avant chaque test.
     * Crée un environnement de prison complet avec :
     * - Directeur
     * - Bâtiment et cellule
     * - Prisonnier
     */
    @BeforeEach
    public void setup() {
        // 1. Créer le directeur et la cellule
        directeur = new Directeur("Dupont", "Charles", "0606060606", LocalDate.of(2020, 1, 1));
        cellule101 = new Cellule(4, 101);

        // 2. Créer un prisonnier et l'ajouter à la cellule
        detenu1 = new Detenu(123, "Valjean", "Jean", "Vol de pain", cellule101);

        // 3. Créer un bâtiment et y ajouter la cellule
        batimentA = new Batiment(1);
        batimentA.ajouterCellule(cellule101);

        // 4. Créer la prison et y ajouter le directeur, le bâtiment et le prisonnier
        prisonTest = new Prison("La Santé", "Paris", directeur);
        prisonTest.ajouterBatiment(batimentA);
        prisonTest.ajouterPrisonnier(detenu1);
    }

    /**
     * Test de l'export CSV de la prison.
     * Vérifie que toutes les informations sont présentes et correctement formatées.
     */
    @Test
    public void testVersCsv() {
        String resultatCsv = this.prisonTest.versCsv();

        String resultatAttendu = "Prison;La Santé;Paris\n" +
                                 "Directeur;\n" +
                                 directeur.versCsv() + "\n" +
                                 "Batiments;\n" +
                                 batimentA.versCsv() + // Contient déjà les \n
                                 "Prisonniers;\n" +
                                 detenu1.versCsv() + "\n";

        assertEquals(resultatAttendu, resultatCsv, "Le CSV de la prison est incorrect.");
    }

    /**
     * Test de l'export XML de la prison.
     * Vérifie la structure et les balises.
     */
    @Test
    public void testVersXml() {
        String resultatXml = this.prisonTest.versXml();

        String resultatAttendu = "<prison>\n" +
                                 "\t<nom>La Santé</nom>\n" +
                                 "\t<adresse>Paris</adresse>\n" +
                                 "\t<directeur>\n" +
                                 "\t\t" + directeur.versXml() + "\n" +
                                 "\t</directeur>\n" +
                                 "\t<batiments>\n" +
                                 "\t\t" + batimentA.versXml() + "\n" +
                                 "\t</batiments>\n" +
                                 "\t<prisonniers>\n" +
                                 "\t\t" + detenu1.versXml() + "\n" +
                                 "\t</prisonniers>\n" +
                                 "</prison>";

        assertEquals(resultatAttendu, resultatXml, "Le XML de la prison est incorrect.");
    }

    /**
     * Test de l'export JSON de la prison.
     * Vérifie que les objets sont correctement sérialisés.
     */
    @Test
    public void testVersJson() {
        String resultatJson = this.prisonTest.versJson();

        String resultatAttendu = "{\n" +
                                 "\t\"nomPrison\":\"La Santé\",\n" +
                                 "\t\"adresse\":\"Paris\",\n" +
                                 "\t\"directeur\":" + directeur.versJson() + ",\n" +
                                 "\t\"batiments\":[\n" +
                                 "\t\t" + batimentA.versJson() + "\n" +
                                 "\t],\n" +
                                 "\t\"prisonniers\":[\n" +
                                 "\t\t" + detenu1.versJson() + "\n" +
                                 "\t]\n" +
                                 "}";

        assertEquals(resultatAttendu, resultatJson, "Le JSON de la prison est incorrect.");
    }
}
