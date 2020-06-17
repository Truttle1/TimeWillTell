package net.truttle1.time.overworld.npc;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.main.Game;

public abstract class NPC extends GameObject {

	public BufferedImage[] currentAnimation;
	protected int vVelocity = 0;
	public int hVelocity = 0;
	protected int[] hPath = new int[300];
	protected int pathStage = 0;
	protected OverworldMode om;
	public NPC(Game window, int x, int y, OverworldMode om) {
		super(window);
		for(int i=0; i<300; i++)
		{
			hPath[i] = 99;
		}
		this.x = x;
		this.y = y;
		this.om = om;
	}

	protected boolean canTalk(int cDistance)
	{
		return distanceTo(ObjectId.Player)<cDistance && Global.talking == 0 && !Global.disableMovement;
	}

	protected void continuePath()
	{
		if(om.pauseMenuOpen)
		{
			hVelocity = 0;
		}
		else
		{
			pathStage++;
			if(hPath.length-1<pathStage)
			{
				pathStage = 0;
			}
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
	public int distanceToPoint(ObjectId id, int x, int y)
	{
		for(int i=0; i<om.objects.size();i++)
		{
			if(om.objects.get(i).id == id)
			{
				GameObject temp = om.objects.get(i);
				int distX = (int) Math.pow(Math.abs((x)-temp.x),2);
				int distY = (int) Math.pow(Math.abs(y-temp.y),2);
				return (int) Math.sqrt(distX+distY);
			}
		}
		return 9999;
	}
	@Override
	public void tick() {
		
	continuePath();
	if(hPath[pathStage] != 99 && hPath[pathStage] != 199)
	{
		hVelocity = hPath[pathStage];
	}
	for(int i=0; i<window.overworldMode.objects.size();i++)
	{
		if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
		{
			Grass g = (Grass)window.overworldMode.objects.get(i);

			if(g.getBounds().intersects(leftRectangle())) {
				if(hVelocity<0)
				{
					hVelocity = -hVelocity;
				}
			}

			if(g.getBounds().intersects(rightRectangle())) {
				if(hVelocity>0)
				{
					hVelocity = -hVelocity;
				}
			}
			if(g.getBounds().intersects(topRectangle())) {
				if(vVelocity<0)
				{
					vVelocity = 0;
				}
			}
			if(g.getBounds().intersects(bottomRectangle()) && vVelocity>0)
			{
				vVelocity = 0;
				this.y = g.y-180;
			}
		}
	}
	x+=hVelocity;
	y+=vVelocity;
	vVelocity+=2;
	}

	public abstract Rectangle topRectangle();
	public abstract Rectangle leftRectangle();
	public abstract Rectangle rightRectangle();
	public abstract Rectangle bottomRectangle();
	public abstract Rectangle getBounds();
	@Override
	public abstract void render(Graphics g);

}
