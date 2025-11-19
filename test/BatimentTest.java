import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 * Classe de test pour la classe Batiment.
 * <p>
 * Cette classe vérifie le bon fonctionnement des méthodes de formatage :
 * - versCsv()
 * - versXml()
 * - versJson()
 * </p>
 */
public class BatimentTest {

    /** Instance de Batiment utilisée pour les tests */
    private Batiment batimentTest;

    /** Cellules de test */
    private Cellule c1;
    private Cellule c2;

    /** Surveillant de test */
    private Surveillant s1;

    /**
     * Méthode exécutée avant chaque test.
     * Elle initialise un environnement de test propre.
     */
    @BeforeEach
    public void setup() {
        // Création d'un bâtiment
        batimentTest = new Batiment(1); // Batiment A

        // Création des cellules
        c1 = new Cellule(4, 101); // Cellule 101
        c2 = new Cellule(2, 102); // Cellule 102

        // Création d'un surveillant
        s1 = new Surveillant("Javert", "Inspecteur", "0123456789", "Chef");

        // Ajout des cellules et du surveillant au bâtiment
        batimentTest.ajouterCellule(c1);
        batimentTest.ajouterCellule(c2);
        batimentTest.ajouterSurveillant(s1);
    }

    /**
     * Test de la méthode versCsv().
     * Vérifie que le CSV généré correspond au format attendu.
     */
    @Test
    public void testVersCsv() {
        // Appel de la méthode
        String resultatCsv = this.batimentTest.versCsv();

        // Chaîne attendue
        String resultatAttendu = "Batiment;1\n" +
                                 "Cellules;\n" +
                                 "101;4;0\n" +
                                 "102;2;0\n" +
                                 "Surveillants;\n" +
                                 "Javert;Inspecteur;0123456789;Chef\n";

        // Vérification avec assertion
        assertEquals(resultatAttendu, resultatCsv, "Le CSV du bâtiment est incorrect.");
    }

    /**
     * Test de la méthode versXml().
     * Vérifie que le XML généré correspond au format attendu.
     */
    @Test
    public void testVersXml() {
        // Appel de la méthode
        String resultatXml = this.batimentTest.versXml();

        // Chaîne attendue
        String resultatAttendu = "<batiment>\n" +
                                 "\t<numero>1</numero>\n" +
                                 "\t<cellules>\n" +
                                 "\t\t" + c1.versXml() + "\n" +
                                 "\t\t" + c2.versXml() + "\n" +
                                 "\t</cellules>\n" +
                                 "\t<surveillants>\n" +
                                 "\t\t" + s1.versXml() + "\n" +
                                 "\t</surveillants>\n" +
                                 "</batiment>";

        // Vérification
        assertEquals(resultatAttendu, resultatXml, "Le XML du bâtiment est incorrect.");
    }

    /**
     * Test de la méthode versJson().
     * Vérifie que le JSON généré correspond au format attendu.
     */
    @Test
    public void testVersJson() {
        // Appel de la méthode
        String resultatJson = this.batimentTest.versJson();

        // Chaîne attendue
        String resultatAttendu = "{\n" +
                                 "\t\"numeroBatiment\":1,\n" +
                                 "\t\"cellules\":[\n" +
                                 "\t\t" + c1.versJson() + ",\n" +
                                 "\t\t" + c2.versJson() + "\n" +
                                 "\t],\n" +
                                 "\t\"surveillants\":[\n" +
                                 "\t\t" + s1.versJson() + "\n" +
                                 "\t]\n" +
                                 "}";

        // Vérification
        assertEquals(resultatAttendu, resultatJson, "Le JSON du bâtiment est incorrect.");
    }
}
