package net.truttle1.time.overworld;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.main.Game;

public abstract class ItemObject extends GameObject {

	protected BufferedImage[] currentAnimation;
	public int vVelocity = 0;
	public int hVelocity = 0;
	protected int[] hPath = new int[300];
	protected int pathStage = 0;
	protected OverworldMode om;
	public int item;
	public ItemObject(Game window, int x, int y, OverworldMode om) {
		super(window);
		for(int i=0; i<300; i++)
		{
			hPath[i] = 99;
		}
		this.x = x;
		this.y = y;
		this.om = om;
		//this.item = item;
		this.id = ObjectId.Item;
	}


	protected void continuePath()
	{
		pathStage++;
		if(hPath.length-1<pathStage)
		{
			pathStage = 0;
		}
	}
	public int distanceTo(ObjectId id)
	{
		for(int i=0; i<om.objects.size();i++)
		{
			if(om.objects.get(i).id == id)
			{
				GameObject temp = om.objects.get(i);
				int distX = (int) Math.pow(Math.abs((this.x)-temp.x),2);
				int distY = (int) Math.pow(Math.abs(this.y-temp.y),2);
				return (int) Math.sqrt(distX+distY);
			}
		}
		return 9999;
	}
	@Override
	public void tick() {
	continuePath();
	if(hPath[pathStage] != 99)
	{
		hVelocity = hPath[pathStage];
	}
	for(int i=0; i<window.overworldMode.objects.size();i++)
	{
		if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
		{
			Grass g = (Grass)window.overworldMode.objects.get(i);

			if(g.getBounds().intersects(topRectangle())) {
				if(vVelocity<0)
				{
					vVelocity = 0;
				}
			}
			if(g.getBounds().intersects(bottomRectangle()) && vVelocity>0)
			{
				vVelocity = 0;
				this.y = g.y-30;
			}
		}
	}
	x+=hVelocity;
	y+=vVelocity;
	vVelocity+=2;
	}

	public Rectangle topRectangle()
	{
		return new Rectangle(x,y,50,10);
	}
	public abstract Rectangle leftRectangle();
	public abstract Rectangle rightRectangle();
	public Rectangle bottomRectangle()
	{
		return new Rectangle(x,y+40,50,10);
	}
	public Rectangle getBounds()
	{
		return new Rectangle(x,y,50,50);
	}
	@Override
	public abstract void render(Graphics g);

}
