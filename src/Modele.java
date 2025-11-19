/**
 * Fichier : Modele.java
 * Auteur  : Marouane AMEJJOUD
 * Version : 1.0
 *
 * Classe centrale assurant la communication avec la base de données.
 * Elle gère l’ensemble des opérations CRUD pour :
 * - Les cellules
 * - Les détenus
 * - Les surveillants
 * - Le directeur
 *
 * Chaque méthode ouvre une connexion MySQL, exécute une requête
 * puis ferme proprement cette connexion.
 */

import java.sql.*;
import java.util.ArrayList;

public class Modele {
    
    private Connection connexion;
    private String dernierMessage;

    /**
     * Constructeur du modèle.
     * Initialise uniquement le message d'état.
     */
    public Modele() {
        this.dernierMessage = "";
    }
    
    /**
     * Retourne le dernier message d’erreur SQL généré.
     */
    public String getDernierMessage() {
        return this.dernierMessage;
    }

    /**
     * Initialise la connexion à la base MySQL.
     * Charge le driver JDBC et établit la connexion avec les identifiants.
     */
    private void chargerConnexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://172.16.203.101/HebSIO?serverTimezone=UTC";
            String utilisateur = "sio";
            String motDePasse = "slam";
            
            this.connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            
        } catch (ClassNotFoundException erreur) { 
            this.dernierMessage = "Erreur Driver : " + erreur.getMessage();
        } catch (SQLException erreur) { 
            this.dernierMessage = "Erreur SQL Connexion : " + erreur.getMessage();
        }
    }

    // ========================================================================
    //                              GESTION CELLULES
    // ========================================================================

    /**
     * Retourne le nombre total de cellules enregistrées dans la base.
     */
    public int getNbCellules() {
        this.chargerConnexion();
        int nombre = 0;
        String requete = "SELECT COUNT(*) AS total FROM Cellule";
        
        try {
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            ResultSet resultat = pst.executeQuery();
            
            if (resultat.next()) {
                nombre = resultat.getInt("total");
            }
            resultat.close();
            pst.close();
            this.connexion.close(); 
        } catch (SQLException erreur) {
             this.dernierMessage = "Erreur comptage : " + erreur.getMessage();
        }
        return nombre;
    }

    /**
     * Génère un identifiant intelligent pour une cellule :
     * (numéro bâtiment × 100) + n° séquentiel.
     */
    public int genererIdCellule(int idBatiment) {
        this.chargerConnexion();
        int nouvelId = 0;
        int baseCalcul = idBatiment * 100;
        
        String requete = "SELECT MAX(numero_cellule) as max_id FROM Cellule WHERE numero_cellule >= ? AND numero_cellule < ?";

        try {
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            pst.setInt(1, baseCalcul); 
            pst.setInt(2, baseCalcul + 100);
            
            ResultSet resultat = pst.executeQuery();
            
            if (resultat.next()) {
                int maxTrouve = resultat.getInt("max_id");
                
                if (maxTrouve == 0) {
                    nouvelId = baseCalcul + 1;
                }
                if (maxTrouve > 0) {
                    nouvelId = maxTrouve + 1;
                }
            }
            
            resultat.close();
            pst.close();
            this.connexion.close();
        } catch (SQLException erreur) {
             this.dernierMessage = "Erreur génération ID : " + erreur.getMessage();
        }
        return nouvelId;
    }

    /**
     * Insère une cellule standard dans la base.
     */
    public void insererCellule(int numero, int nbPlaces, int idBatiment) {
        this.chargerConnexion();
        try {
            String requete = "INSERT INTO Cellule (numero_cellule, nb_places, id_batiment, type_cellule) VALUES (?, ?, ?, 'Standard')";
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            
            pst.setInt(1, numero);
            pst.setInt(2, nbPlaces);
            pst.setInt(3, idBatiment);
            
            pst.executeUpdate();
            pst.close();
            this.connexion.close();
        } catch (SQLException erreur) {
            this.dernierMessage = "Erreur insertion : " + erreur.getMessage();
        }
    }

    /**
     * Insère un mitard (cellule disciplinaire) avec raison et durée de punition.
     */
    public void insererMitard(int numero, int nbPlaces, int idBatiment, String raison, int temps) {
        this.chargerConnexion();
        try {
            String requete = "INSERT INTO Cellule (numero_cellule, nb_places, id_batiment, type_cellule, raison_mitard, temps_purger) VALUES (?, ?, ?, 'Mitard', ?, ?)";
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            
            pst.setInt(1, numero);
            pst.setInt(2, nbPlaces);
            pst.setInt(3, idBatiment);
            pst.setString(4, raison);
            pst.setInt(5, temps);
            
            pst.executeUpdate();
            pst.close();
            this.connexion.close();
        } catch (SQLException erreur) {
            this.dernierMessage = "Erreur insertion mitard : " + erreur.getMessage();
        }
    }

    /**
     * Retourne toutes les cellules avec leur type,
     * et le nombre d’occupants pour chacune.
     */
    public ArrayList<Cellule> getLesCellules() {
        this.chargerConnexion();
        ArrayList<Cellule> lesCellules = new ArrayList<Cellule>();

        String requete = 
        "SELECT c.*, " +
        "(SELECT COUNT(*) FROM Detenu d WHERE d.id_cellule = c.numero_cellule) AS nb_occupants " +
        "FROM Cellule c";

        try {
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            ResultSet resultat = pst.executeQuery();

            while (resultat.next()) {
                int num = resultat.getInt("numero_cellule");
                int nbPlaces = resultat.getInt("nb_places");
                String type = resultat.getString("type_cellule");
                int nbOccupants = resultat.getInt("nb_occupants");

                Cellule uneCellule = null;

                if (type.equals("Mitard")) {
                    String raison = resultat.getString("raison_mitard");
                    int temps = resultat.getInt("temps_purger");
                    uneCellule = new Mitard(nbPlaces, num, raison, temps);
                } else {
                    uneCellule = new Cellule(nbPlaces, num);
                }

                if (uneCellule != null) {
                    for (int i = 0; i < nbOccupants; i++) {
                        uneCellule.ajouterUnDetenu(new Detenu());
                    }
                    lesCellules.add(uneCellule);
                }
            }
            resultat.close();
            pst.close();
            this.connexion.close();
        } catch (SQLException erreur) {
             this.dernierMessage = "Erreur récupération cellules : " + erreur.getMessage();
        }
        return lesCellules;
    }

    // ========================================================================
    //                           GESTION PRISONNIERS
    // ========================================================================

    /**
     * Insère un nouveau détenu dans la base.
     */
    public void insererPrisonnier(int numEcrou, String nom, String prenom, String crime, int idCellule) {
        this.chargerConnexion();
        try {
            String requete = "INSERT INTO Detenu (numero_ecrou, nom, prenom, crime, id_cellule) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            
            pst.setInt(1, numEcrou);
            pst.setString(2, nom);
            pst.setString(3, prenom);
            pst.setString(4, crime);
            pst.setInt(5, idCellule);
            
            pst.executeUpdate();
            pst.close();
            this.connexion.close();
        } catch (SQLException erreur) {
            this.dernierMessage = "Erreur insertion détenu : " + erreur.getMessage();
        }
    }

    /**
     * Supprime un détenu à partir de son numéro d’écrou.
     */
    public void supprimerPrisonnier(int numEcrou) {
        this.chargerConnexion();
        try {
            String requete = "DELETE FROM Detenu WHERE numero_ecrou = ?";
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            
            pst.setInt(1, numEcrou);
            pst.executeUpdate();
            pst.close();
            this.connexion.close();
        } catch (SQLException erreur) {
             this.dernierMessage = "Erreur suppression : " + erreur.getMessage();
        }
    }

    /**
     * Retourne l’ensemble des détenus.
     */
    public ArrayList<Detenu> getLesPrisonniers() {
        return this.rechercherPrisonnier("");
    }
    
    /**
     * Recherche un détenu par nom (approximatif)
     * ou par numéro d’écrou (partiel ou exact).
     */
    public ArrayList<Detenu> rechercherPrisonnier(String recherche) {
        this.chargerConnexion();
        ArrayList<Detenu> lesDetenus = new ArrayList<Detenu>();
        
        String requete = "SELECT * FROM Detenu WHERE nom LIKE ? OR numero_ecrou LIKE ?";
        
        try {
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            pst.setString(1, "%" + recherche + "%"); 
            pst.setString(2, "%" + recherche + "%");
            
            ResultSet resultat = pst.executeQuery();
            
            while (resultat.next()) {
                int numEcrou = resultat.getInt("numero_ecrou");
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String crime = resultat.getString("crime");
                int idCellule = resultat.getInt("id_cellule");
                
                Cellule laCellule = (idCellule > 0) ? new Cellule(0, idCellule) : null;
                
                lesDetenus.add(new Detenu(numEcrou, nom, prenom, crime, laCellule));
            }
            resultat.close();
            pst.close();
            this.connexion.close();
        } catch (SQLException erreur) {
             this.dernierMessage = "Erreur recherche prisonniers : " + erreur.getMessage();
        }
        return lesDetenus;
    }

    /**
     * Retourne le nombre de détenus enregistrés.
     */
    public int getNbPrisonniers() {
        this.chargerConnexion();
        int nombre = 0;
        try {
            PreparedStatement pst = this.connexion.prepareStatement("SELECT COUNT(*) AS total FROM Detenu");
            ResultSet resultat = pst.executeQuery();
            if (resultat.next()) 
                nombre = resultat.getInt("total");
            
            resultat.close();
            pst.close();
            this.connexion.close();
        } catch (SQLException erreur) { 
            this.dernierMessage = "Erreur comptage : " + erreur.getMessage(); 
        }
        return nombre;
    }

    // ========================================================================
    //                           GESTION SURVEILLANTS
    // ========================================================================

    /**
     * Insère un nouveau surveillant.
     */
    public void insererSurveillant(String nom, String prenom, String tel, String grade) {
        this.chargerConnexion();
        try {
            String requete = "INSERT INTO Surveillant (nom, prenom, numero_tel, grade) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            
            pst.setString(1, nom);
            pst.setString(2, prenom);
            pst.setString(3, tel);
            pst.setString(4, grade);
            
            pst.executeUpdate();
            pst.close();
            this.connexion.close();
        } catch (SQLException erreur) { 
            this.dernierMessage = "Erreur insertion : " + erreur.getMessage(); 
        }
    }

    /**
     * Supprime un surveillant via son numéro de téléphone.
     */
    public void supprimerSurveillant(String tel) {
        this.chargerConnexion();
        try {
            String requete = "DELETE FROM Surveillant WHERE numero_tel = ?";
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            pst.setString(1, tel);
            pst.executeUpdate();
            pst.close();
            this.connexion.close();
        } catch (SQLException erreur) { 
            this.dernierMessage = "Erreur suppression : " + erreur.getMessage(); 
        }
    }

    /**
     * Retourne tous les surveillants.
     */
    public ArrayList<Surveillant> getLesSurveillants() {
        return this.rechercherSurveillant("");
    }
    
    /**
     * Recherche les surveillants par nom ou par téléphone.
     */
    public ArrayList<Surveillant> rechercherSurveillant(String recherche) {
        this.chargerConnexion();
        ArrayList<Surveillant> liste = new ArrayList<Surveillant>();
        String requete = "SELECT * FROM Surveillant WHERE nom LIKE ? OR numero_tel LIKE ?";
        
        try {
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            pst.setString(1, "%" + recherche + "%");
            pst.setString(2, "%" + recherche + "%");
            
            ResultSet resultat = pst.executeQuery();
            
            while (resultat.next()) {
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String tel = resultat.getString("numero_tel");
                String grade = resultat.getString("grade");
                
                liste.add(new Surveillant(nom, prenom, tel, grade));
            }
            resultat.close(); 
            pst.close(); 
            this.connexion.close();
        } catch (SQLException erreur) { 
            this.dernierMessage = "Erreur recherche : " + erreur.getMessage(); 
        }
        return liste;
    }
    
    /**
     * Retourne le nombre total de surveillants.
     */
    public int getNbSurveillants() {
        this.chargerConnexion();
        int nombre = 0;
        try {
            PreparedStatement pst = this.connexion.prepareStatement("SELECT COUNT(*) AS total FROM Surveillant");
            ResultSet resultat = pst.executeQuery();
            if (resultat.next()) 
                nombre = resultat.getInt("total");
            
            resultat.close(); 
            pst.close(); 
            this.connexion.close();
        } catch (SQLException erreur) { 
            this.dernierMessage = "Erreur comptage : " + erreur.getMessage(); 
        }
        return nombre;
    }

    // ========================================================================
    //                           GESTION DIRECTEUR
    // ========================================================================

    /**
     * Récupère le directeur (unique) enregistré en base.
     */
    public Directeur getLeDirecteur() {
        this.chargerConnexion();
        Directeur leDirecteur = null;
        String requete = "SELECT * FROM Directeur WHERE id = ?";
        
        try {
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            pst.setInt(1, 1);
            ResultSet resultat = pst.executeQuery();
            
            if (resultat.next()) {
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String tel = resultat.getString("numero_tel");
                
                java.sql.Date dateSql = resultat.getDate("date_integration");
                java.time.LocalDate dateIntegration = dateSql.toLocalDate();
                
                leDirecteur = new Directeur(nom, prenom, tel, dateIntegration);
            }
            resultat.close(); 
            pst.close(); 
            this.connexion.close();
        } catch (SQLException erreur) { 
            this.dernierMessage = "Erreur directeur : " + erreur.getMessage(); 
        }
        return leDirecteur;
    }

    /**
     * Met à jour les informations du directeur (nom / prénom / téléphone).
     */
    public void modifierDirecteur(String nom, String prenom, String tel) {
        this.chargerConnexion();
        try {
            String requete = "UPDATE Directeur SET nom = ?, prenom = ?, numero_tel = ? WHERE id = 1";
            PreparedStatement pst = this.connexion.prepareStatement(requete);
            
            pst.setString(1, nom);
            pst.setString(2, prenom);
            pst.setString(3, tel);
            
            pst.executeUpdate();
            pst.close();
            this.connexion.close();
        } catch (SQLException erreur) { 
            this.dernierMessage = "Erreur update : " + erreur.getMessage(); 
        }
    }
}
