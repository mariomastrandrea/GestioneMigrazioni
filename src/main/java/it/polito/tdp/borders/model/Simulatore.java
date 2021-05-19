package it.polito.tdp.borders.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore
{
	// Modello -> qual è lo stato del sistema ad ogni passo
	private Graph<Country, DefaultEdge> grafo;
	
	// Tipi di evento -> coda prioritaria
	private PriorityQueue<Evento> queue;
	
	// Parametri della simulazione 
	private int N_MIGRANTI = 1000; //tot migranti
	private Country nazionePartenza;
	
	// Valori in output
	private int T;	//istante di tempo corrente
	private Map<Country, Integer> stanziali;
	
	
	public void initialize(Country country, Graph<Country, DefaultEdge> grafo)
	{
		this.nazionePartenza = country;
		this.grafo = grafo;
		
		//imposto lo stato iniziale
		this.T = 1;
		this.stanziali = new HashMap<>();
				
		//creo la coda
		this.queue = new PriorityQueue<>();
		// inserisco il primo evento
		Evento primoEvento = new Evento(this.T, this.nazionePartenza, this.N_MIGRANTI);
		this.queue.add(primoEvento);
	}
	
	public void run()
	{
		// finché la coda non si svuota -> prendo un evento e lo eseguo
		Evento e;
		
		while((e = this.queue.poll()) != null)
		{
			// simulo l'evento 'e'
			if(e.getT() > this.T)
				this.T = e.getT();
			
			int numMigranti = e.getN();
			Country statoDiPartenza = e.getCountry();
			
			//ottengo gli stati confinanti
			Collection<Country> vicini = Graphs.neighborListOf(this.grafo, statoDiPartenza);
			
			int migrantiPerStatoConfinante = (numMigranti/2) / vicini.size();
			
			if(migrantiPerStatoConfinante == 0)
				continue;
			
			//le persone si possono spostare
			for(Country confinante : vicini)
			{
				Evento newEvento = new Evento(this.T + 1, confinante, migrantiPerStatoConfinante);
				this.queue.add(newEvento);
			}
			
			int nuoviStanziali = numMigranti - migrantiPerStatoConfinante * vicini.size();
			
			//aggiungo i nuovi stanziali a questo stato
			if(!this.stanziali.containsKey(statoDiPartenza))
				this.stanziali.put(statoDiPartenza, 0);
			
			int vecchiStanziali = this.stanziali.get(statoDiPartenza);
			this.stanziali.put(statoDiPartenza, vecchiStanziali + nuoviStanziali);
		}
	}
	
	public Map<Country, Integer> getStanziali()
	{
		return this.stanziali;
	}
	
	public int getT()
	{
		return this.T;
	}

}



