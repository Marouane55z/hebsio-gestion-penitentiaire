// Fichier : ModeleTest.java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Classe de test pour la classe Modele.
 * <p>
 * Vérifie les opérations CRUD pour :
 * - Cellules et Mitards
 * - Prisonniers
 * - Surveillants
 * - Directeur
 * ainsi que la génération d'ID automatique pour les cellules.
 * </p>
 */
public class ModeleTest {

    private Modele leModele;

    /**
     * Initialisation avant chaque test.
     * Crée un nouvel objet Modele pour un environnement propre.
     */
    @BeforeEach
    public void initialisation() {
        this.leModele = new Modele();
    }

    /**
     * Méthode utilitaire pour nettoyer la BDD après un test.
     * @param requete Requête SQL de suppression
     */
    private void executerRequeteNettoyage(String requete) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/HebSIO?serverTimezone=UTC";
            Connection conn = DriverManager.getConnection(url, "sio", "slam");

            PreparedStatement pst = conn.prepareStatement(requete);
            pst.executeUpdate();

            pst.close();
            conn.close();
        } catch (Exception e) {
            // On ignore les erreurs de nettoyage
            System.out.println("Erreur nettoyage test : " + e.getMessage());
        }
    }

    /**
     * Test des opérations sur les cellules standard.
     * Vérifie insertion, récupération et nettoyage.
     */
    @Test
    public void testGestionCellules() {
        int nbAvant = leModele.getNbCellules();
        leModele.insererCellule(8888, 10, 1);
        int nbApres = leModele.getNbCellules();
        assertEquals(nbAvant + 1, nbApres, "Le nombre de cellules devrait augmenter.");
        this.executerRequeteNettoyage("DELETE FROM Cellule WHERE numero_cellule = 8888");
    }

    @Test
    public void testRecuperationCelluleInseree() {
        leModele.insererCellule(8889, 12, 1);
        ArrayList<Cellule> liste = leModele.getLesCellules();
        boolean trouve = false;
        for (Cellule c : liste) {
            if (c.getNumeroCellule() == 8889) {
                trouve = true;
                assertEquals(12, c.getNbPlacesCellule());
            }
        }
        assertTrue(trouve, "La cellule 8889 devrait être trouvée.");
        this.executerRequeteNettoyage("DELETE FROM Cellule WHERE numero_cellule = 8889");
    }

    /**
     * Test des opérations sur les Mitards.
     * Vérifie l'héritage et les propriétés spécifiques.
     */
    @Test
    public void testGestionMitard() {
        leModele.insererMitard(9999, 1, 1, "TestUnitaire", 45);
        ArrayList<Cellule> liste = leModele.getLesCellules();
        boolean trouve = false;
        for (Cellule c : liste) {
            if (c.getNumeroCellule() == 9999) {
                trouve = true;
                assertTrue(c instanceof Mitard, "L'objet doit être un Mitard.");
            }
        }
        assertTrue(trouve, "Le Mitard 9999 devrait être trouvé.");
        this.executerRequeteNettoyage("DELETE FROM Cellule WHERE numero_cellule = 9999");
    }

    @Test
    public void testProprietesMitard() {
        leModele.insererMitard(9998, 1, 1, "RaisonUnitaire", 30);
        ArrayList<Cellule> liste = leModele.getLesCellules();
        for (Cellule c : liste) {
            if (c.getNumeroCellule() == 9998) {
                Mitard m = (Mitard) c;
                assertEquals("RaisonUnitaire", m.getRaison());
                assertEquals(30, m.getTempsPurger());
            }
        }
        this.executerRequeteNettoyage("DELETE FROM Cellule WHERE numero_cellule = 9998");
    }

    /**
     * Test des opérations sur les prisonniers.
     * Vérifie insertion, recherche et suppression.
     */
    @Test
    public void testGestionPrisonnier() {
        leModele.insererCellule(7777, 2, 1);
        leModele.insererPrisonnier(12345, "TestNom", "TestPrenom", "TestCrime", 7777);
        ArrayList<Detenu> resultats = leModele.rechercherPrisonnier("TestNom");
        boolean trouve = false;
        for (Detenu d : resultats) {
            if (d.getNumeroEcrou() == 12345) {
                trouve = true;
                assertEquals("TestPrenom", d.getPrenom());
            }
        }
        assertTrue(trouve, "Le prisonnier devrait être trouvé par la recherche.");
        leModele.supprimerPrisonnier(12345);
        this.executerRequeteNettoyage("DELETE FROM Cellule WHERE numero_cellule = 7777");
    }

    @Test
    public void testSuppressionPrisonnier() {
        leModele.insererCellule(7776, 2, 1);
        leModele.insererPrisonnier(12346, "Nom2", "Prenom2", "Crime2", 7776);
        leModele.supprimerPrisonnier(12346);
        ArrayList<Detenu> resultats = leModele.getLesPrisonniers();
        boolean existe = false;
        for (Detenu d : resultats) {
            if (d.getNumeroEcrou() == 12346) existe = true;
        }
        assertFalse(existe, "Le prisonnier ne devrait plus exister.");
        this.executerRequeteNettoyage("DELETE FROM Cellule WHERE numero_cellule = 7776");
    }

    /**
     * Test des opérations sur les surveillants.
     * Vérifie insertion, recherche et suppression.
     */
    @Test
    public void testGestionSurveillant() {
        String telUnique = "0999999999";
        leModele.insererSurveillant("Super", "Mario", telUnique, "Gardien");
        ArrayList<Surveillant> liste = leModele.rechercherSurveillant(telUnique);
        boolean trouve = false;
        for (Surveillant s : liste) {
            if (s.getNumeroTel().equals(telUnique)) {
                trouve = true;
                assertEquals("Mario", s.getPrenom());
            }
        }
        assertTrue(trouve, "Le surveillant devrait être trouvé.");
        leModele.supprimerSurveillant(telUnique);
    }

    @Test
    public void testSuppressionSurveillant() {
        String tel = "0888888888";
        leModele.insererSurveillant("Luigi", "Bros", tel, "Gardien");
        leModele.supprimerSurveillant(tel);
        ArrayList<Surveillant> liste = leModele.getLesSurveillants();
        boolean existe = false;
        for (Surveillant s : liste) {
            if (s.getNumeroTel().equals(tel)) existe = true;
        }
        assertFalse(existe, "Le surveillant devrait être supprimé.");
    }

    /**
     * Test de la modification du directeur.
     * Vérifie la mise à jour et restaure l'état initial.
     */
    @Test
    public void testGestionDirecteur() {
        Directeur dirInitial = leModele.getLeDirecteur();
        assertNotNull(dirInitial, "Le directeur ID 1 doit exister.");

        String ancienNom = dirInitial.getNom();
        String ancienPrenom = dirInitial.getPrenom();
        String ancienTel = dirInitial.getNumeroTel();

        // Modification
        leModele.modifierDirecteur("NouveauNomTest", "NouveauPrenomTest", "0000000000");

        // Vérification
        Directeur dirModifie = leModele.getLeDirecteur();
        assertEquals("NouveauNomTest", dirModifie.getNom());

        // Restauration (Nettoyage)
        leModele.modifierDirecteur(ancienNom, ancienPrenom, ancienTel);
    }

    /**
     * Test de la génération d'identifiants automatiques pour les cellules.
     */
    @Test
    public void testGenerationIdCellule() {
        this.executerRequeteNettoyage("DELETE FROM Cellule WHERE id_batiment = 9");
        this.executerRequeteNettoyage("DELETE FROM Batiment WHERE id = 9");
        this.executerRequeteNettoyage("INSERT INTO Batiment (id, numero_batiment) VALUES (9, 9)");
        int idGenere = leModele.genererIdCellule(9);
        assertEquals(901, idGenere);
        this.executerRequeteNettoyage("DELETE FROM Cellule WHERE id_batiment = 9");
        this.executerRequeteNettoyage("DELETE FROM Batiment WHERE id = 9");
    }

    @Test
    public void testGenerationIdCelluleSuivant() {
        this.executerRequeteNettoyage("DELETE FROM Cellule WHERE id_batiment = 9");
        this.executerRequeteNettoyage("DELETE FROM Batiment WHERE id = 9");
        this.executerRequeteNettoyage("INSERT INTO Batiment (id, numero_batiment) VALUES (9, 9)");
        leModele.insererCellule(901, 1, 9);
        int idSuivant = leModele.genererIdCellule(9);
        assertEquals(902, idSuivant);
        this.executerRequeteNettoyage("DELETE FROM Cellule WHERE id_batiment = 9");
        this.executerRequeteNettoyage("DELETE FROM Batiment WHERE id = 9");
    }
}
