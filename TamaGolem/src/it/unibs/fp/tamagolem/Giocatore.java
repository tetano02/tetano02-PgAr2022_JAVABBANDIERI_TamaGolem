package it.unibs.fp.tamagolem;

public class Giocatore {
	
	private String nome;
	private int numeroGolem;
	private Golem golemAttuale;

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getNumeroGolem() {
		return numeroGolem;
	}
	
	public void setNumeroGolem(int numeroGolem) {
		this.numeroGolem = numeroGolem;
	} 
	
	public Golem getGolemAttuale() {
		return golemAttuale;
	}
	
	public void setGolemAttuale(Golem golemAttuale) {
		this.golemAttuale = golemAttuale;
	}
	
	public void setInizioBattaglia(int g) {
		this.setGolemAttuale(new Golem());
		this.getGolemAttuale().setVitaIniziale();
		this.setNumeroGolem(g);
	}
	
	//Scelta delle pietre da dare al golem
	public void sceltaPietre(int numeroPietre) {
		String pietraScelta;
		System.out.println("SCELTA PIETRE\n\n"+this.getNome()+" scegli "+numeroPietre+" pietre tra le seguenti presenti nel sacco:");
		Equilibrio.stampaElementiSacco();
		for(int i=0; i<numeroPietre; i++) {
			pietraScelta=InputDati.inputPietra();
			this.getGolemAttuale().setPietra(pietraScelta);
			Equilibrio.sacco.remove(Equilibrio.sacco.indexOf(pietraScelta)); //toglie la pietra scelta dal sacco
		}
	}
	
	public void stringAttacco() {
		System.out.println("Il golem di "+this.getNome()+" sferra la pietra con potere "+this.getGolemAttuale().getPietra());
	}
	
	public void stringEvocazione() {
		System.out.println(this.nome+" evoca in campo un nuovo golem!\n");
	}
	
}
