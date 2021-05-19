package it.polito.tdp.borders.model;

public class CountryAndNumber //implements Comparable<CountryAndNumber>
{
	private Country country;
	private int number;
	
	
	public CountryAndNumber(Country country, int number)
	{
		this.country = country;
		this.number = number;
	}

	public Country getCountry()
	{
		return this.country;
	}

	public int getNumber()
	{
		return this.number;
	}

	/*
	@Override
	public int compareTo(CountryAndNumber other)
	{
		//ordine decrescente
		return Integer.compare(other.number, this.number);
	}
	*/
	
	@Override
	public String toString()
	{
		return this.country.getStateAbb() + " - " + this.country.getStateName() + " = " + this.number;
	} 
	
}
