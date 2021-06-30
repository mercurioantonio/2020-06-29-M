package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	ImdbDAO dao;
	SimpleWeightedGraph<Director, DefaultWeightedEdge> grafo;
	Map<Integer, Director> idMap;
	
	public Model() {
		this.dao = new ImdbDAO();
		this.idMap = new HashMap<Integer, Director>();
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleWeightedGraph<Director, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        dao.listAllDirectors(anno, idMap);
		
		Graphs.addAllVertices(grafo, idMap.values());
		
		for(Director d1 : grafo.vertexSet()) {
			for(Director d2 : grafo.vertexSet()) {
				if(!d1.equals(d2) && dao.listActorsDirector(d1, d2, anno) != 0) {
					Graphs.addEdgeWithVertices(grafo, d1, d2, dao.listActorsDirector(d1, d2, anno));
				}
			}
		}
		
	}
	
	public int nVertici() {
		return grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return grafo.edgeSet().size();
	}
	
	
	public Set<Director> getVertici(){
		return grafo.vertexSet();
	}
	
	
	public String DirectorAdiacenti(Director d) {
		List<Adicenza> adiacenza = new ArrayList<>();
		String result = "";
		
		for(DefaultWeightedEdge e : grafo.edgesOf(d)) {
			adiacenza.add(new Adicenza(grafo.getEdgeSource(e), (int) grafo.getEdgeWeight(e)));
		}
		Collections.sort(adiacenza, new ComparatoreAdiacenza());
		for(Adicenza a : adiacenza) {
			result += a;
		}
		
		return result;
		
	}
	

	class ComparatoreAdiacenza  implements Comparator<Adicenza>{

		@Override
		public int compare(Adicenza a1, Adicenza a2) {
			if(a1.getShareAct() < a2.getShareAct())
			    return 1;
			else 
				return -1;
		}
		
	}

}
