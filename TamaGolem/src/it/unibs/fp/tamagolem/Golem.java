package it.unibs.fp.tamagolem;

import java.util.ArrayDeque;
import java.util.Deque;

public class Golem {
	
	private static final int VITA_MASSIMA = 10;
	private int vita;
	private Deque<String> pietre=new ArrayDeque<>();
	
	public int getVita() {
		return vita;
	}
	
	//Decrementa la vita di un golem 
	public void decrementaVita(int danno) {
		this.vita -= danno;
	}
	
	//Setta la vita iniziale al massimo
	public void setVitaIniziale() {
		this.vita=VITA_MASSIMA;
	}
	
	public String getPietra() {
		return pietre.getFirst();
	}
	
	public void setPietra(String pietra) {
		this.pietre.add(pietra);
	}
	
	public void rimuoviPietra() {
		this.pietre.remove();
	}
	
	//Azzera la coda
	public void azzeraPietre() {
		int dimensione=pietre.size();
		for(int i=0; i<dimensione; i++)
			this.pietre.remove();
	}
	
	//Verifica se due golem hanno le stesse pietre nello stesso ordine
	public boolean confrontaElencoPietre(Golem golemAvversario){
		for(int i=0; i<pietre.size(); i++) {
			if(!(this.pietre.getFirst().equals(golemAvversario.pietre.getFirst())))
				return false;
			this.ruotaPietre();
			golemAvversario.ruotaPietre();
		}
		return true;
	}
	
	//Lancio delle pietre e calcolo dei danni
	public void lancioPietre(Golem golemAvversario) {
		int interazionePietre=Equilibrio.interazionePietre(this, golemAvversario);
		if(interazionePietre>0)
			golemAvversario.decrementaVita(interazionePietre);
		else
			this.decrementaVita(-interazionePietre); //Il valore passato deve essere positivo, così che la vita venga decrementata
	}
	
	//Per ruotare le pietre la coda aumenta momentaneamente con la pietra utilizzata, per poi rimuoverla dalla coda
	//così che la pietra successiva prenda il suo posto
	public void ruotaPietre() {
		this.setPietra(this.getPietra());
		this.rimuoviPietra();
	}
}
