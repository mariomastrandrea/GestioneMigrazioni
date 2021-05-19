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
	private Map<Integer,Country> countriesIdMap;
	private Simulatore simulatore;
	private BordersDAO dao;
	
	
	public Model() 
	{
		this.countriesIdMap = new HashMap<>();
		this.dao = new BordersDAO();
		this.simulatore = new Simulatore();
	}
	
	public void creaGrafo(int anno) 
	{	
		this.graph = new SimpleGraph<>(DefaultEdge.class);

		//vertici
		//this.dao.getCountriesFromYear(anno, this.countriesIdMap);
		List<Country> countries = this.dao.getCountriesFromYear(anno, this.countriesIdMap);
		Graphs.addAllVertices(this.graph, countries);
		
		// archi
		List<Adiacenza> archi = this.dao.getCoppieAdiacenti(anno);
		for(Adiacenza c : archi) 
		{
			Country country1 = this.countriesIdMap.get(c.getState1no());
			Country country2 = this.countriesIdMap.get(c.getState2no());
			
			this.graph.addEdge(country1, country2);
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
		if(this.graph == null || partenza == null)
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
