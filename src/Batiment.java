// Fichier : Batiment.java
import java.util.ArrayList;

/**
 * Représente un bâtiment contenant des cellules et des surveillants.
 * Permet d'ajouter des éléments et d'exporter les données dans divers formats.
 * 
 * @author Marouane AMEJJOUD
 * @version 1.0
 */
public class Batiment {
	
	private int numBatiment;
	private ArrayList<Cellule> lesCellules;
	private ArrayList<Surveillant> lesSurveillants;
	
	/**
	 * Constructeur d'un bâtiment identifé par son numéro.
	 * Initialise les listes de cellules et de surveillants.
	 * 
	 * @param numBatiment numéro du bâtiment
	 */
	public Batiment(int numBatiment) {
		this.numBatiment = numBatiment;
		this.lesCellules = new ArrayList<Cellule>();
		this.lesSurveillants = new ArrayList<Surveillant>();
	}
	
	// --- GETTERS ---
	
	/**
	 * Retourne le numéro du bâtiment.
	 * @return numéro du bâtiment
	 */
	public int getNumBatiment() {
		return this.numBatiment;
	}
	
	/**
	 * Retourne la liste des cellules du bâtiment.
	 * @return liste des cellules
	 */
	public ArrayList<Cellule> getLesCellules() {
		return this.lesCellules;
	}
	
	/**
	 * Retourne la liste des surveillants affectés au bâtiment.
	 * @return liste des surveillants
	 */
	public ArrayList<Surveillant> getLesSurveillants() {
		return this.lesSurveillants;
	}

	// --- METHODES D'AJOUT ---
	
	/**
	 * Ajoute une cellule au bâtiment si elle n'est pas déjà présente.
	 * 
	 * @param uneCellule cellule à ajouter
	 */
	public void ajouterCellule(Cellule uneCellule) {
		if (uneCellule != null && !this.lesCellules.contains(uneCellule)) {
			this.lesCellules.add(uneCellule);
		}
	}
	
	/**
	 * Ajoute un surveillant au bâtiment s'il n'est pas déjà présent.
	 * 
	 * @param unSurveillant surveillant à ajouter
	 */
	public void ajouterSurveillant(Surveillant unSurveillant) {
		if (unSurveillant != null && !this.lesSurveillants.contains(unSurveillant)) {
			this.lesSurveillants.add(unSurveillant);
		}
	}
	
	// --- METHODES D'EXPORTATION ---

	/**
	 * Exporte les données du bâtiment au format CSV.
	 * 
	 * @return chaîne CSV représentant le bâtiment
	 */
	public String versCsv() {
		String chaine = "";
		chaine += "Batiment;" + this.numBatiment + "\n";
		
		chaine += "Cellules;\n";
		for (Cellule c : this.lesCellules) {
			chaine += c.versCsv() + "\n";
		}
		
		chaine += "Surveillants;\n";
		for (Surveillant s : this.lesSurveillants) {
			chaine += s.versCsv() + "\n";
		}
		return chaine;
	}
	
	/**
	 * Exporte les données du bâtiment au format XML.
	 * 
	 * @return chaîne XML représentant le bâtiment
	 */
	public String versXml() {
		String chaine = "";
		chaine += "<batiment>\n";
		chaine += "\t<numero>" + this.numBatiment + "</numero>\n";
		
		chaine += "\t<cellules>\n";
		for (Cellule c : this.lesCellules) {
			chaine += "\t\t" + c.versXml() + "\n";
		}
		chaine += "\t</cellules>\n";
		
		chaine += "\t<surveillants>\n";
		for (Surveillant s : this.lesSurveillants) {
			chaine += "\t\t" + s.versXml() + "\n";
		}
		chaine += "\t</surveillants>\n";
		
		chaine += "</batiment>";
		return chaine;
	}
	
	/**
	 * Exporte les données du bâtiment au format JSON.
	 * 
	 * @return chaîne JSON représentant le bâtiment
	 */
	public String versJson() {
		String chaine = "";
		chaine += "{\n";
		chaine += "\t\"numeroBatiment\":" + this.numBatiment + ",\n";
		
		chaine += "\t\"cellules\":[\n";
		int compteurCellule = 0;
		for (Cellule c : this.lesCellules) {
			chaine += "\t\t" + c.versJson();
			compteurCellule++;
			if (compteurCellule < this.lesCellules.size()) {
				chaine += ",";
			}
			chaine += "\n";
		}
		chaine += "\t],\n";
		
		chaine += "\t\"surveillants\":[\n";
		int compteurSurv = 0;
		for (Surveillant s : this.lesSurveillants) {
			chaine += "\t\t" + s.versJson();
			compteurSurv++;
			if (compteurSurv < this.lesSurveillants.size()) {
				chaine += ",";
			}
			chaine += "\n";
		}
		chaine += "\t]\n";
		
		chaine += "}";
		return chaine;
	}
}