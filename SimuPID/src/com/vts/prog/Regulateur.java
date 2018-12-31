package com.vts.prog;

public class Regulateur {
	
	//Variables
	private int kp, ki, kd, sp, pv, outPID, erreur, sommeErreur, variationErreur, erreurPrecedent;

	/**
	 * Constructeur régulateur
	 */
	public Regulateur(int sp,int pv,int kp,int ki,int kd) {
		//Initialisation des paramètres avec des valeurs par défauts
		this.sp=sp;
		this.pv=pv;
		this.kp=kp;
		this.ki=ki;
		this.kd=kd;
		
		this.erreurPrecedent=0;
		
		//Calcul des erreurs
		//calculErreurs();
	
	}
	
	/**
	 * Setteurs
	 */
	public void setSp(int sp) {
		this.sp = sp;
	}
	
	public void setPv(int pv) {
		this.pv = pv;
	}
	
	public void setKp(int kp) {
		this.kp = kp;
	}
	public void setKi(int ki) {
		this.ki = ki;
	}
	public void setKd(int kd) {
		this.kd = kd;
	}
	
	
	/**
	 * Calcul les erreurs du régulateur
	 */
	private void calculErreurs() {
		//Erreur
		this.erreur= this.sp-this.pv;
		//Somme des erreurs entre 
		this.sommeErreur=this.erreur + this.sommeErreur;
		//Limitation de la somme des erreur à +/-100%
		if (this.sommeErreur>100) this.sommeErreur=100;
		if (this.sommeErreur<-100) this.sommeErreur=-100;
		//Variation erreur
		this.variationErreur=this.erreur-this.erreurPrecedent;
	}
	
	/**
	 * Calcul la sortie du PID
	 * @return int - Sortie du régulateur PID en % 
	 */
	public int calculOutPID() {
		this.calculErreurs();
		this.outPID=this.kp * this.erreur+ this.ki * this.sommeErreur + this.kd * this.variationErreur;
		if (this.outPID>100) this.outPID=100;
		if (this.outPID<0) this.outPID=0;
		this.erreurPrecedent=this.erreur;
		
		return this.outPID;
	}
	
	/**
	 * @return String - Chaine de caractère formaté pour affichage des paramètres du régulateur
	 */
	public String afficheResultat() {
		String resultat="";
		resultat ="-------------------------------------------Résultat-------------------------------------------\n";
		resultat = resultat + "Paramètres entrée=> SP: " + this.sp + ", PV: " + this.pv + "\n";
		resultat = resultat + "Paramètres PID=> Kp: " + this.kp + ", Ki: " + this.ki + ", Kd: " + this.kd +"\n";
		resultat = resultat + "Résultats des erreurs=> erreur: " + this.erreur + ", Somme erreur: " + this.sommeErreur + ", Variation erreur: " + this.variationErreur + "\n";
		resultat = resultat + "Résultat sortie PID=> Out PID: " + this.outPID + " %" + "\n";
		resultat = resultat + "\n";
		
		return resultat;
	}
	
}
