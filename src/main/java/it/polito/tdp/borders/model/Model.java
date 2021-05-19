package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model 
{	
	private Graph<Country, DefaultEdge> graph;
	private Map<Integer,Country> countriesMap;
	private Simulatore simulatore;
	
	
	public Model() 
	{
		this.countriesMap = new HashMap<>();
		this.simulatore = new Simulatore();
	}
	
	public void creaGrafo(int anno) 
	{	
		this.graph = new SimpleGraph<>(DefaultEdge.class);

		BordersDAO dao = new BordersDAO();
		
		//vertici
		dao.getCountriesFromYear(anno, this.countriesMap);
		Graphs.addAllVertices(graph, this.countriesMap.values());
		
		// archi
		List<Adiacenza> archi = dao.getCoppieAdiacenti(anno);
		for(Adiacenza c : archi) 
		{
			this.graph.addEdge(this.countriesMap.get(c.getState1no()), 
					this.countriesMap.get(c.getState2no())) ;
		}
	}
	
	public List<CountryAndNumber> getCountryAndNumbers()
	{
		List<CountryAndNumber> result = new LinkedList<>();
		
		for(Country c : this.graph.vertexSet())
			result.add(new CountryAndNumber(c, this.graph.degreeOf(c)));
		
		result.sort((c1,c2) -> Integer.compare(c2.getNumber(), c1.getNumber()));
		return result;
	}
	
	public void Simula(Country partenza)
	{
		if(this.graph == null)
			return;
		
		this.simulatore.initialize(partenza, this.graph);
		this.simulatore.run();
	}
	
	public Integer getT() { return this.simulatore.getT(); }
	
	public List<CountryAndNumber> getStanziali()
	{
		Map<Country, Integer> stanziali = this.simulatore.getStanziali();
		
		List<CountryAndNumber> result = new LinkedList<>();
		
		for(var pair : stanziali.entrySet())
		{
			Country country = pair.getKey();
			Integer number = pair.getValue();
			CountryAndNumber cn = new CountryAndNumber(country, number);
			result.add(cn);
		}
		
		result.sort((c1,c2) -> Integer.compare(c2.getNumber(), c1.getNumber()));
		return result;
	}

	public Collection<Country> getCountries()
	{
		if(this.graph == null)
			return null;
		
		List<Country> result = new ArrayList<>(this.graph.vertexSet()); 
		result.sort((c1,c2) -> c1.getStateName().compareTo(c2.getStateName()));
		return result;
	}
}
