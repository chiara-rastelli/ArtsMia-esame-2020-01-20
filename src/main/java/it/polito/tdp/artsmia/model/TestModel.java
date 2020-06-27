package it.polito.tdp.artsmia.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		m.creaGrafo("Painter");
	//	for (Adiacenza a : m.getAdiacenze())
	//		System.out.println(a.toString());
		Integer idProva = 3473;
		m.cercaPercorso(idProva);
		System.out.println("Dimensioni percorso trovato: "+m.dimensioniPercorsoMigliore+"\n");
		for (Integer i : m.percorsoMigliore)
			System.out.println(i+"\n");
	}

}
