package it.unibs.fp.tamagolem;

import java.util.Scanner;

public class InputDati {
		
		//Classe utile per gli input
	
		//Avvertenze:
		//-L'avvertenza sugli Scanner è perchè ho deciso di non chiuderli, in quanto una volta chiusi gli scanner per l'input da tastiera
		//essi non possono più essere riaperti durante il programma
		//-L'avvertenza sulla stringa "DaButtare" è semplicemente perchè il suo unico scopo è salvare un dato scorretto e Eclipse la segna come se non avesse utilizzi
	
		//Input per il nome di un giocatore, non ci sono vincoli per il nome di un giocatore
		public static String inputNomeGiocatore() {
			Scanner leggiNome=new Scanner(System.in);
			return leggiNome.nextLine();
		}
		
		//Serve per l'input della scelta nel menù iniziale
		public static int inputScelta() {
			Scanner leggiScelta=new Scanner(System.in);
			String daButtare;
			int scelta=0;
			boolean valido=false;
			while(valido==false) {
				System.out.println("Fai la tua scelta:");
				if(leggiScelta.hasNextInt()) {
					scelta=leggiScelta.nextInt();
					valido=true;
				}
				else {
					System.out.println("Scelta non valida, riprova");
					daButtare = leggiScelta.next();
				}
				if(valido==true) {
					if(scelta!=1 && scelta!=2) {
						System.out.println("Scelta non valida, riprova");
						valido=false;
					}else
						valido=true;
				}
			}
			return scelta;
		}
		
		//Serve per l'input di una pietra dal sacco
		public static String inputPietra() {
			Scanner leggiPietra=new Scanner(System.in);
			String pietra;
			int conta=0;
			do {
				if(conta==1)
					System.out.println("Pietra non trovata, riprova!\n");
				conta=1;
				System.out.println("Inserisci la pietra: ");
				pietra=leggiPietra.nextLine();
			}while(!(Equilibrio.sacco.contains(pietra)));
			System.out.print("\n");
			return pietra;
		}
	}
