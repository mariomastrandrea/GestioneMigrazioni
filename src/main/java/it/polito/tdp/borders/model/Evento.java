package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>
{
	private int t;
	private Country country;
	private int n;
	
	public Evento(int t, Country country, int n)
	{
		this.t = t;
		this.country = country;
		this.n = n;
	}

	public int getT()
	{
		return this.t;
	}

	public Country getCountry()
	{
		return this.country;
	}

	public int getN()
	{
		return this.n;
	}

	@Override
	public int compareTo(Evento other)
	{
		return Integer.compare(t, n);
	}
	
}
