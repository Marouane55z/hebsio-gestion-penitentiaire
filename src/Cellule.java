/**
 * Représente une cellule contenant un nombre défini de places et une liste de détenus.
 * Fournit des méthodes pour ajouter, retirer et rechercher des détenus,
 * ainsi que pour exporter les informations sous différents formats.
 *
 * @author Marouane AMEJJOUD
 * @version 1.0
 */
import java.util.ArrayList;

public class Cellule {
	
	private int nbPlaces;
	private int numeroCellule;
	private ArrayList<Detenu> lesDetenus;
	
	/**
	 * Constructeur vide initialisant la liste des détenus.
	 */
	public Cellule() {
		this.lesDetenus = new ArrayList<Detenu>();
	}
	
	/**
	 * Constructeur principal permettant d'initialiser une cellule avec son nombre de places et son numéro.
	 *
	 * @param nbPlaces nombre de places disponibles dans la cellule
	 * @param numeroCellule numéro identifiant la cellule
	 */
	public Cellule(int nbPlaces, int numeroCellule) {
		this.nbPlaces = nbPlaces;
		this.numeroCellule = numeroCellule;
		this.lesDetenus = new ArrayList<Detenu>();
	}
	
	// --- GETTERS & SETTERS ---

	/**
	 * Retourne le nombre total de places dans la cellule.
	 * @return nombre de places
	 */
	public int getNbPlacesCellule() {
		return this.nbPlaces;
	}
	
	/**
	 * Retourne le numéro identifiant la cellule.
	 * @return numéro de la cellule
	 */
	public int getNumeroCellule() {
		return this.numeroCellule;
	}
	
	/**
	 * Définit le nombre de places de la cellule.
	 * @param nbPlaces nouveau nombre de places
	 */
	public void setNbPlacesCellule(int nbPlaces) {
		this.nbPlaces = nbPlaces; 
	}
	
	/**
	 * Définit le numéro identifiant la cellule.
	 * @param numeroCellule nouveau numéro de cellule
	 */
	public void setNumeroCellule(int numeroCellule) {
		this.numeroCellule = numeroCellule; 
	}
	
	/**
	 * Retourne une chaîne listant tous les détenus contenus dans cette cellule.
	 * @return liste formatée des détenus
	 */
	public String getLesDetenus() {
		String index = "";
		for(Detenu unDetenu : this.lesDetenus) {
			index += unDetenu.versChaine() + "\n";
		}
		return index;
	}
	
	/**
	 * Ajoute un détenu à la cellule si possible.
	 * Vérifie la validité de l'objet, l'absence de doublon et la capacité restante.
	 *
	 * @param unDetenu détenu à ajouter
	 * @return message indiquant le résultat de l'opération
	 */
	public String ajouterUnDetenu(Detenu unDetenu) {
		String message = "Erreur inconnue";
		
		if (unDetenu == null || this.lesDetenus.contains(unDetenu)) {
			message = "Détenu invalide ou déjà présent.";
		}
		
		if (unDetenu != null && !this.lesDetenus.contains(unDetenu)) {
			if (this.lesDetenus.size() < this.nbPlaces) {
				this.lesDetenus.add(unDetenu);
				unDetenu.setSaCellule(this);
				message = "Ajout réussi.";
			}
			
			if (this.lesDetenus.size() >= this.nbPlaces) {
				message = "La cellule " + this.numeroCellule + " est pleine.";
			}
		}
		
		return message;
	}
	
	/**
	 * Supprime un détenu de la cellule si celui-ci est présent.
	 * @param unDetenu détenu à retirer
	 */
	public void supprimerUnDetenu(Detenu unDetenu) {
		if (unDetenu != null && this.lesDetenus.contains(unDetenu)) {
			this.lesDetenus.remove(unDetenu);
			unDetenu.setSaCellule(null);
		}
	}
	
	/**
	 * Retourne le nombre actuel de détenus dans la cellule.
	 * @return nombre de détenus
	 */
	public int getNbDetenus() {
		return this.lesDetenus.size();
	}
	
	/**
	 * Recherche un détenu par son nom et retourne son index s'il est présent.
	 * Respecte la règle du return unique.
	 *
	 * @param nom nom du détenu recherché
	 * @return index du détenu ou -1 si non trouvé
	 */
	public int chercherUnDetenu(String nom) {
		int index = -1;
		int compteur = 0;
		while(compteur < this.lesDetenus.size() && !this.lesDetenus.get(compteur).getNom().equals(nom) ) {
			compteur ++;
		}
		if(compteur < this.lesDetenus.size()) {
			index = compteur;
		}
		return index;
	}
	
	/**
	 * Retourne une chaîne de caractère décrivant la cellule et la liste de ses détenus.
	 * @return description de la cellule
	 */
	public String versChaine() {
		String chaine = "";
		chaine += "Numéro de cellule : " + this.numeroCellule + "\n";
		chaine += "Nombre de place : " + this.nbPlaces + "\n";
		chaine += "Places occupées : " + this.lesDetenus.size() + "\n";
		chaine += "--- Détenus dans cette cellule ---\n";
		
		if (this.lesDetenus.isEmpty()) {
			chaine += " (Vide)\n";
		}
		
		if (!this.lesDetenus.isEmpty()) {
			for(Detenu unDetenu : this.lesDetenus) {
				chaine += " - " + unDetenu.getPrenom() + " " + unDetenu.getNom() + " (Ecrou: " + unDetenu.getNumeroEcrou() + ")\n";
			}
		}
		return chaine;
	}

    /**
	 * Exporte les données de la cellule au format CSV.
	 * Format : numeroCellule;nbPlaces;nbPlacesOccupees
	 *
	 * @return représentation CSV de la cellule
	 */
	public String versCsv() {
		String chaine = "";
		chaine += this.numeroCellule + ";";
		chaine += this.nbPlaces + ";";
		chaine += this.lesDetenus.size();
		return chaine;
	}

    /**
	 * Exporte les données de la cellule au format XML.
	 *
	 * @return représentation XML de la cellule
	 */
	public String versXml() {
		String chaine = "";
		chaine += "<cellule>";
		chaine += "<numeroCellule>" + this.numeroCellule + "</numeroCellule>";
		chaine += "<nbPlaces>" + this.nbPlaces + "</nbPlaces>";
		chaine += "<nbPlacesOccupees>" + this.lesDetenus.size() + "</nbPlacesOccupees>";
		chaine += "</cellule>";
		return chaine;
	}

    /**
	 * Exporte les données de la cellule au format JSON.
	 *
	 * @return représentation JSON de la cellule
	 */
	public String versJson() {
		String chaine = "";
		chaine += "{";
		chaine += "\"numeroCellule\":" + this.numeroCellule + ",";
		chaine += "\"nbPlaces\":" + this.nbPlaces + ",";
		chaine += "\"nbPlacesOccupees\":" + this.lesDetenus.size();
		chaine += "}";
		return chaine;
	}
}