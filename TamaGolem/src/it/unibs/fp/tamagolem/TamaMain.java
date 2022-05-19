package it.unibs.fp.tamagolem;

public class TamaMain {
	
	private static final String SCELTA_SBAGLIATA = "\nAOOOOO ma non se pò annà avanti così!\nVi avevo avvisato, non ho così tanto tempo da perdere!\nQuesta battaglia sarebbe infinita, in veste di giudice impongo la fine di questa battaglia!\n\nVai golem Supremo, accoppali con la pietra dell'assoluto!\n\n* il golem Supremo procede a sconfiggere i giocatori e i loro golem *\n* urla strazianti *";
	private static final String REGOLE = "\nEcco le regole:\n-Ogni partita genera degli equilibri diversi tra gli elementi, sta a te capirli\n-Hai a tua disposizione 3 golem, ognuno può ingurgitare fino a 4 pietre\n-Ogni pietra contiene un elemento in equilibrio con gli altri, l'interazione può essere forte, debole o nulla in caso di pietre contenenti lo stesso elemento\n-Se le interazioni tra le pietre di due golem sono tutte nulle, interverrà severamente il giudice\n-Un golem viene sconfitto quando la sua vita raggiunge lo 0\n-Il giocatore i cui golem vengono sconfitti prima, perde";
	private static final String RICHIESTA_NOME = "\nBeh, innanzitutto inserisci il tuo nome!\nCome ti chiami?";
	private static final String OPZIONE_2 = "!\n2)Non voglio lottare, esci";
	private static final String OPZIONE_1 = " cosa vuoi fare?\n1)Voglio lottare contro ";
	private static final double NUMERO_ELEMENTI = 6;
	private static final String LOTTA = "\nOra è tutto pronto per la lotta!\n";
	private static final String FASE_SETUP = "\nFase di setup in corso...\nGenerando l'equilibrio...\nL'equilibrio è stato generato!";
	private static final String SFIDA = "?\nGrande idea!\nInserisci il tuo nome!\nCome ti chiami?";
	private static final String ARRIVO_AVVERSARIO = "Bel nome!\nOh, ma chi è quello? Un altro allievo?\nCome dici? Vuoi sfidare ";
	private static final String INTRODUZIONE = "Benvenuto allievo dell'Accademia!\nSei pronto a utilizzare le tecniche per domare gli antichi elementi che governano l'equilibrio del mondo?";

	public static void main(String[] args) {
		//Variabili utili
	    double p,g,s;
		p=Math.ceil((NUMERO_ELEMENTI+1)/3)+1;
	    g=Math.ceil(((NUMERO_ELEMENTI-1)*(NUMERO_ELEMENTI-2))/(2*p));
	    s=Math.ceil((2*g*p)/NUMERO_ELEMENTI)*NUMERO_ELEMENTI;
		//Inizializzazione dei due avversari
		Giocatore g1=new Giocatore();
		Giocatore g2=new Giocatore();
		//Inizializzazione degli elementi
		Equilibrio.inizializzaElementi();
		//Interfaccia iniziale
		System.out.println(INTRODUZIONE+RICHIESTA_NOME);
		g1.setNome(InputDati.inputNomeGiocatore());
		System.out.println(ARRIVO_AVVERSARIO+g1.getNome()+SFIDA);
		g2.setNome(InputDati.inputNomeGiocatore());
		//Menu e lotta
		int scelta;
		do {
			System.out.println(g1.getNome()+OPZIONE_1+g2.getNome()+OPZIONE_2);
			scelta=InputDati.inputScelta();
			switch(scelta) {
			case 1:
				//Regole 
				System.out.println(REGOLE);
				//Fase 1
				System.out.println(FASE_SETUP);
				Equilibrio.generaEquilibrio();
				//Sacco comune
				Equilibrio.inizializzaSacco(s);
				//Fase 2
				System.out.println(LOTTA);
				//Preparazione alla battaglia
				g1.setInizioBattaglia((int)g);
				g2.setInizioBattaglia((int)g);
				g1.sceltaPietre((int)p);
				g2.sceltaPietre((int)p);
				g1.stringEvocazione();
				g2.stringEvocazione();
				//Lotta tra giocatori
				int danno;
				boolean valido=true;
				while(g1.getNumeroGolem()>0 && g2.getNumeroGolem()>0) {
					//Se i giocatori scelgono le stesse pietre e sono nello stesso ordine, la lotta continuerebbe in loop
					//Abbiamo deciso di far terminare la partita e dichiarare un pareggio
					if(g1.getGolemAttuale().confrontaElencoPietre(g2.getGolemAttuale())) {
						System.out.println(SCELTA_SBAGLIATA);
						valido=false;
						g1.setNumeroGolem(0);
						g2.setNumeroGolem(0);
					}
					if(valido) {
						//Lotta tra golem
						while(g1.getGolemAttuale().getVita()>0 && g2.getGolemAttuale().getVita()>0) {
							g1.getGolemAttuale().lancioPietre(g2.getGolemAttuale());
							g1.stringAttacco();
							g2.stringAttacco();
							//Lancio delle pietre e aggiornamento della vita
							danno=Equilibrio.interazionePietre(g1.getGolemAttuale(), g2.getGolemAttuale());
							//Effetti del lancio delle pietre
							if(danno==0)
								System.out.println("Le pietre contengono lo stesso potere, nessuno dei due ha subito danni!\n");
							else if(danno>0)
								System.out.println("Il golem di "+g2.getNome()+" ha subito "+danno+" danni!\n");
							else
								System.out.println("Il golem di "+g1.getNome()+" ha subito "+-danno+" danni!\n");
							//Ciclo delle pietre
							g1.getGolemAttuale().ruotaPietre();
							g2.getGolemAttuale().ruotaPietre();
						}
						//Dopo la sconfitta di un golem 
						if(g1.getGolemAttuale().getVita()<=0) {
							System.out.println("Il golem di "+g1.getNome()+" è stato sconfitto!");
							g1.setNumeroGolem(g1.getNumeroGolem()-1); //Decrementa il numero di golem di un giocatore
							if(g1.getNumeroGolem()>0) {
								System.out.println("Il golem di "+g2.getNome()+" ha "+g2.getGolemAttuale().getVita()+" punti vita rimanenti");
								System.out.println(g1.getNome()+" ti rimangono "+g1.getNumeroGolem()+" golem");
								//Inizializzazione di un nuovo golem
								g1.getGolemAttuale().setVitaIniziale();
								g1.getGolemAttuale().azzeraPietre();//rimuove tutte le pietre del golem sconfitto
								g1.sceltaPietre((int)p);
								g1.stringEvocazione();
							}
						}else {
							System.out.println("Il golem di "+g2.getNome()+" è stato sconfitto!");
							g2.setNumeroGolem(g2.getNumeroGolem()-1); //Decrementa il numero di golem di un giocatore
							if(g2.getNumeroGolem()>0) {
								System.out.println("Il golem di "+g1.getNome()+" ha "+g1.getGolemAttuale().getVita()+" punti vita rimanenti");
								System.out.println(g2.getNome()+" ti rimangono "+g2.getNumeroGolem()+" golem");
								//Inizializzazione di un nuovo golem
								g2.getGolemAttuale().setVitaIniziale();
								g2.getGolemAttuale().azzeraPietre();//rimuove tutte le pietre del golem sconfitto
								g2.sceltaPietre((int)p);
								g2.stringEvocazione();
							}
						}
					}
				}
				//Fine dello scontro
				if(g1.getNumeroGolem()==0 && g2.getNumeroGolem()==0)
					System.out.println("La battaglia si è conclusa con uno spiacevole pareggio!\n");
				else if(g1.getNumeroGolem()==0)
					System.out.println("\n"+g1.getNome()+" è stato sconfitto! "+g2.getNome()+" è il vincitore!\n");
				else
					System.out.println("\n"+g2.getNome()+" è stato sconfitto! "+g1.getNome()+" è il vincitore!\n");
				//Stampa dell'equilibrio
				Equilibrio.stampaEquilibrio();
			}
		}while(scelta!=2);
		System.out.println("Cosa? Per caso non ti senti in grado?\n\nRitorna quando sarai pronto!\n\nAlla prossima!");
	}
}
