package net.truttle1.time.battle;

public class SpecialAttack 
{
	private String name;
	private int id;
	private int cost;
	private int type;
	
	public SpecialAttack(String iName, int iId, int iCost, int iType)
	{
		name = iName;
		id = iId;
		cost = iCost;
		type = iType;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getId()
	{
		return id;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public int getType()
	{
		return type;
	}
}
