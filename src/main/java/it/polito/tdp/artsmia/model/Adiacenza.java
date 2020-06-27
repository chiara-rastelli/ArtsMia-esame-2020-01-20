package it.polito.tdp.artsmia.model;

public class Adiacenza implements Comparable<Adiacenza>{

	Integer id1;
	Integer id2;
	Integer peso;
	
	public Adiacenza(Integer id1, Integer id2, Integer peso) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.peso = peso;
	}
	public Integer getId1() {
		return id1;
	}
	public void setId1(Integer id1) {
		this.id1 = id1;
	}
	public Integer getId2() {
		return id2;
	}
	public void setId2(Integer id2) {
		this.id2 = id2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacenza o) {
		return -this.peso.compareTo(o.peso);
	}
	@Override
	public String toString() {
		return "Adiacenza tra artista " + id1 + " e artista " + id2 + ": peso=" + peso + "\n";
	}
}
