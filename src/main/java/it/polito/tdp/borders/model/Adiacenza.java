package it.polito.tdp.borders.model;

public class Adiacenza 
{
	private final int state1no;
	private final int state2no;
	
	
	public Adiacenza(int state1no, int state2no) 
	{
		this.state1no = state1no;
		this.state2no = state2no;
	}
	
	public int getState1no() 
	{
		return this.state1no;
	}
	
	public int getState2no() 
	{
		return this.state2no;
	}
}
