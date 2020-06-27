package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	ArtsmiaDAO db;
	SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
	List<Adiacenza> listaAdiacenzeGrafo;
	
	List<Integer> percorsoMigliore;
	int dimensioniPercorsoMigliore;
	
	public Model() {
		this.db = new ArtsmiaDAO();
	}
	
	public List<String> getAllRoles(){
		return this.db.listRoles();
	}

	public void creaGrafo(String ruolo) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, this.db.listArtists(ruolo));
		System.out.println("Grafo creato con "+this.graph.vertexSet().size()+" vertici\n");
		
		this.listaAdiacenzeGrafo = new ArrayList<>(this.db.listAdiacenze(ruolo));
		for (Adiacenza a: this.listaAdiacenzeGrafo) {
			Graphs.addEdgeWithVertices(this.graph, a.getId1(), a.getId2(), a.getPeso());
		}
		System.out.println("Grafo creato con "+this.graph.edgeSet().size()+" archi\n");
	}
	
	public Set<Integer> getVertici(){
		return this.graph.vertexSet();
	}
	
	public int getNumeroArchi() {
		return this.graph.edgeSet().size();
	}
	
	public List<Adiacenza> getAdiacenze() {
		Collections.sort(this.listaAdiacenzeGrafo);
		return this.listaAdiacenzeGrafo;
	}
	
	public boolean isContenuto(int artistId) {
		return this.graph.vertexSet().contains(artistId);
	}
	
	public int getUnIdACaso() {
		List<Integer> nelGrafo = new ArrayList<>();
		for (Integer id : this.graph.vertexSet())
			nelGrafo.add(id);
		return nelGrafo.get(0);
	}

	public void cercaPercorso(int idArtista) {
		this.percorsoMigliore = new ArrayList<>();
		List<Integer> parziale = new ArrayList<>();
		parziale.add(idArtista);
		this.dimensioniPercorsoMigliore = Integer.MIN_VALUE;
		for (Integer i : Graphs.neighborListOf(this.graph, idArtista)) {
			parziale.add(i);
			DefaultWeightedEdge eTemp = this.graph.getEdge(i, idArtista);
			Integer peso =(int) this.graph.getEdgeWeight(eTemp);
			this.ricorri(parziale, peso);
			parziale.remove(parziale.size()-1);
		}
		
	}

	private void ricorri(List<Integer> parziale, int pesoArco) {
		
		List<Integer> vicini = Graphs.neighborListOf(this.graph, parziale.get(parziale.size()-1));
		List<Integer> disponibili = new ArrayList<Integer>();
		int ultimo = parziale.get(parziale.size()-1);
		for (Integer i : vicini) {
			if (!parziale.contains(i)) {
				DefaultWeightedEdge eTemp = this.graph.getEdge(i, ultimo);
				if (this.graph.getEdgeWeight(eTemp)== pesoArco)
					disponibili.add(i);
			}
		}
		
		// caso terminale: ho finito i disponibili
		if (disponibili.size() == 0) {
			if (parziale.size()>this.dimensioniPercorsoMigliore) {
				this.percorsoMigliore = new ArrayList<>(parziale);
				this.dimensioniPercorsoMigliore = parziale.size();
			}
			return;
		}
		
		for (Integer i : disponibili) {
				parziale.add(i);
				this.ricorri(parziale, pesoArco);
				parziale.remove(parziale.size()-1);
		}
		
	}
}
