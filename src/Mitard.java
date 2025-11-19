/**
 * Classe représentant un Mitard (cellule disciplinaire).
 * Elle hérite de la classe Cellule et ajoute des spécificités liées à la punition.
 * * @author Marouane AMEJJOUD
 * @version 1.0
 */
public class Mitard extends Cellule {
	
	/** La raison de la mise au mitard. */
	private String raison;
	
	/** La durée de la peine en jours. */
	private int tempsPurger;

	/**
	 * Constructeur de la classe Mitard.
	 * * @param nbPlaces      Le nombre de places (généralement 1).
	 * @param numeroCellule Le numéro unique de la cellule.
	 * @param raison        La cause de l'incarcération au mitard.
	 * @param tempsPurger   La durée de la sanction en jours.
	 */
	public Mitard(int nbPlaces, int numeroCellule, String raison, int tempsPurger) {
		super(nbPlaces, numeroCellule); 
		this.raison = raison;
		this.tempsPurger = tempsPurger;
	}

	// --- GETTERS & SETTERS ---
	
	/**
	 * Retourne la raison de la sanction.
	 * @return La raison sous forme de chaîne.
	 */
	public String getRaison() {
		return this.raison;
	}

	/**
	 * Définit la raison de la sanction.
	 * @param raison La nouvelle raison.
	 */
	public void setRaison(String raison) {
		this.raison = raison;
	}

	/**
	 * Retourne le temps à purger.
	 * @return Le nombre de jours.
	 */
	public int getTempsPurger() {
		return this.tempsPurger;
	}

	/**
	 * Définit le temps à purger.
	 * @param tempsPurger Le nouveau nombre de jours.
	 */
	public void setTempsPurger(int tempsPurger) {
		this.tempsPurger = tempsPurger;
	}

	// --- METHODES DE FORMATAGE (Surcharge) ---

	/**
	 * Exporte les données du mitard au format CSV.
	 * Ajoute la raison et le temps aux données de base de la cellule.
	 * * @return Une chaîne formatée en CSV (séparateur point-virgule).
	 */
	@Override
	public String versCsv() {
		String chaine = super.versCsv(); 
		chaine += ";" + this.raison;
		chaine += ";" + this.tempsPurger;
		return chaine;
	}
	
	/**
	 * Exporte les données du mitard au format XML.
	 * * @return Une chaîne contenant la structure XML du mitard.
	 */
	@Override
	public String versXml() {
		String chaine = "";
		
		chaine += "<mitard>";
		chaine += "<numero>" + this.getNumeroCellule() + "</numero>";
		chaine += "<nbPlaces>" + this.getNbPlacesCellule() + "</nbPlaces>";
		chaine += "<nbOccupants>" + this.getNbDetenus() + "</nbOccupants>";
		chaine += "<raison>" + this.raison + "</raison>";
		chaine += "<tempsPurger>" + this.tempsPurger + "</tempsPurger>";
		chaine += "</mitard>";
		
		return chaine;
	}
	
	/**
	 * Exporte les données du mitard au format JSON.
	 * * @return Une chaîne formatée en JSON.
	 */
	@Override
	public String versJson() {
		String chaine = super.versJson();
		
		// Retrait de l'accolade fermante pour ajouter les champs spécifiques
		chaine = chaine.substring(0, chaine.length() - 1);
		
		chaine += ",";
		chaine += "\"raison\":\"" + this.raison + "\",";
		chaine += "\"tempsPurger\":" + this.tempsPurger;
		chaine += "}";
		
		return chaine;
	}
}