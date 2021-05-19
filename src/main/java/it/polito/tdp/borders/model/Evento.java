package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>
{
	private final int time;
	private final Country country;
	private final int num;
	
	
	public Evento(int time, Country country, int num)
	{
		this.time = time;
		this.country = country;
		this.num = num;
	}

	public int getTime()
	{
		return this.time;
	}

	public Country getCountry()
	{
		return this.country;
	}

	public int getNum()
	{
		return this.num;
	}

	@Override
	public int compareTo(Evento other)
	{
		return Integer.compare(this.time, other.time);
	}
}
