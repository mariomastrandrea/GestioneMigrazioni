package it.polito.tdp.borders.model;

public class CountryAndNumber 
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

	@Override
	public String toString()
	{
		String result = String.format("%s - %s  =>  #%d", this.country.getStateAbb(), 
										this.country.getStateName(), this.number);
		return result;
	} 
	
}
