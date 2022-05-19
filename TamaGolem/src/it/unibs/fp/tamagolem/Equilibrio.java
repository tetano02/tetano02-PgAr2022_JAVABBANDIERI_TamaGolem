package it.unibs.fp.tamagolem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Equilibrio {
	
	//Classe utile alla gestione dell'Equilibrio durante la partita 
	
	private static final int VITA_GOLEM = 10;
	public static final String KAGUNE = "kagune";
	public static final String SHARINGAN = "sharingan";
	public static final String KATANA_MINGSHAO = "katana mingshao";
	public static final String POTERE_DEL_FONDATORE = "potere del fondatore";
	public static final String AT_FIELD = "at field";
	public static final String ULTRA_ISTINTO = "ultra istinto";
	private static final int NUMERO_ELEMENTI = 6;
	
	public static Map<String, Integer> elencoElementi = new HashMap<>();
	public static int potere[][]=new int[NUMERO_ELEMENTI][NUMERO_ELEMENTI]; //Matrice contenente i poteri che legano due elementi
	public static ArrayList<String>sacco=new ArrayList<>();
	
	//Inizializza la map inserendo gli elementi
	public static void inizializzaElementi() {
		elencoElementi.put(ULTRA_ISTINTO, 0);
		elencoElementi.put(AT_FIELD, 1);
		elencoElementi.put(POTERE_DEL_FONDATORE, 2);
		elencoElementi.put(KATANA_MINGSHAO, 3);
		elencoElementi.put(SHARINGAN, 4);
		elencoElementi.put(KAGUNE, 5);
	}
	
	//Inizializza il sacco, l'ordine delle pietre nel sacco non è importante, aggiungiamo le key s/n volte
	public static void inizializzaSacco(double s) {
		sacco.removeAll(sacco); //Rimuove le pietre rimanenti dalla partita precedente, se è la prima partita non cambia nulla
		for(int i=0; i<(s/NUMERO_ELEMENTI);i++) {
			for(String key:Equilibrio.elencoElementi.keySet()) {
				sacco.add(key);
			}
		}
	}
	
	//Tiene conto delle pietre di un elemento presenti nel sacco
	private static int contaPietre(String key) {
		int conta=0;
		for(int i=0; i<sacco.size(); i++) {
			if(sacco.get(i).equals(key))
				conta++;
		}
		return conta;
	}
	
	//Stampa tutte le pietre nel sacco 
	public static void stampaElementiSacco() {
		System.out.print("\n");
		for(String key:elencoElementi.keySet()) {
			System.out.print(contaPietre(key));
			System.out.println(" pietre contenenti il potere "+ key);
		}
		System.out.print("\n");
	}
	
	//Genera l'equilibrio di una partita
	
	//Note sul funzionamento della generazione:
	//-Genera numeri casuali nelle celle della parte di matrice sopra la diagonale, tranne che nell'ultima cella
	//-I numeri casuali possono essere positivi o negativi in un range da 1 a 5, tranne che nell'ultima cella
	//-Quando viene generato un numero casuale, nella sua cella simmetrica rispetto alla diagonale viene aggiunto il suo opposto
	
	//Funzionamento dell'ultima cella:
	//-Essa contiene il valore opposto della somma della riga, in modo che la somma totale sia zero
	
	//Se l'ultima riga contiene zeri, numeri fuori dal range massimo(-VITA_GOLEM+1,VITA_GOLEM-1) o la somma di quella riga è diversa da zero, ricomincia il processo 
	//Il range è -VITA_GOLEM+1,VITA_GOLEM-1 in quanto il programma non include la possibiltà di oneshottare un golem
	
	public static void generaEquilibrio() {
		do {
			inizializzaPoteri();//Inizializza la matrice a tutti elementi zero
			for(int i=0; i<NUMERO_ELEMENTI-1; i++) {
				for(int j=0; j<NUMERO_ELEMENTI; j++) {
					if(potere[i][j]==0 && i!=j) { //Se il potere è uguale a zero e non è sulla diagonale,il potere deve ancora essere settato
						if(j==NUMERO_ELEMENTI-1){
							//Il valore corrisponde al valore rimanente dei poteri per arrivare a zero
							potere[i][j]=-sommaRiga(i);
							potere[j][i]=-potere[i][j]; //Simmetria della matrice
						}else {
							potere[i][j]=generaRandom();
							potere[j][i]=-potere[i][j]; //Simmetria della matrice
						}
					}	
				}
			}
		}while(controlloTotale());//Se la somma dei valori dell'ultima riga non è uguale a zero, ripete il ciclo
	}
	
	//Inizializza tutta la matrice dei poteri a zero
	private static void inizializzaPoteri() {
		for(int i=0; i<NUMERO_ELEMENTI; i++) {
			for(int j=0; j<NUMERO_ELEMENTI; j++) {
				potere[i][j]=0;
			}
		}
		}
	
	//Controlla se sono presenti zeri oltre a quelli sulla diagonale
	private static boolean controlloZeri() {
		for(int i=0; i<NUMERO_ELEMENTI; i++)
			for(int j=0; j<NUMERO_ELEMENTI; j++) {
				if(i!=j && potere[i][j]==0)
					return true;
			}
		return false;
	}
	
	//Controlla se ci sono dei valori fuori dal range
	private static boolean controllaRangeUltimaRiga() {
		for(int j=0; j<NUMERO_ELEMENTI; j++)
			if(potere[NUMERO_ELEMENTI-1][j]<=(-VITA_GOLEM) || potere[NUMERO_ELEMENTI-1][j]>=VITA_GOLEM)
				return true;
		return false;
	}
	
	//Calcola la somma dei valori su una riga, utile per controllare la somma dell'ultima riga
	private static int sommaRiga(int i) {
		int somma=0;
		for(int j=0; j<NUMERO_ELEMENTI-1; j++) {
			somma+=potere[i][j];
		}
		return somma;
	}
	
	//Controllo finale se la matrice è corretta, se è corretta ritorna false così da poter uscire dal while
	private static boolean controlloTotale() {
		if(sommaRiga(NUMERO_ELEMENTI-1)!=0 || controlloZeri() || controllaRangeUltimaRiga())
			return true;
		return false;
	}
	
	//Genera un numero random che corrisponde al potere
	private static int generaRandom() {
		Random rand=new Random();
		int potere=(rand.nextInt()%5)+1; //Un potere va da 1 a 5
		int segno=rand.nextInt();
		if(segno%2==0)//Eque possibilità che il valore sia positivo(Potere forte) o negativo(Potere debole)
			segno=1;
		else
			segno=-1;
		return potere*segno;
	}
	
	//Restituisce l'interazione tra i poteri di due pietre
	public static int interazionePietre(Golem g1,Golem g2) {
		int interazione;
		int riga=elencoElementi.get(g1.getPietra());
		int colonna=elencoElementi.get(g2.getPietra());
		interazione=potere[riga][colonna];
		return interazione;
	}
	
	//Stampa finale dell'equilibrio della partita
	public static void stampaEquilibrio() {
		int pot;
		System.out.println("EQUILIBRIO DELLA PARTITA\n\nEcco gli equilibri che hanno regolato questa partita:\n");
		for(String key1:elencoElementi.keySet()) {
			for(String key2:elencoElementi.keySet()) {
				pot=potere[elencoElementi.get(key1)][elencoElementi.get(key2)];
				if(pot==0)
					System.out.println(key1+" ha interazione nulla con "+key2+", essendo lo stesso elemento");
				else if(pot>0)
					System.out.println(key1+" è un elemento forte "+pot+" contro "+key2);
				else if(pot<0)
					System.out.println(key1+" è un elemento debole "+-pot+" contro "+key2);
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
}
