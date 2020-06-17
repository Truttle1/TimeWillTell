package net.truttle1.time.overworld.blocks;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.main.Game;

public class Grass extends GameObject{

	BufferedImage[] currentAnimation;
	OverworldMode om;
	int unmodifiedX;
	int unmodifiedY;
	boolean makesDirt = true;
	public Grass(int x, int y,Game window,OverworldMode om) {
		super(window);
		unmodifiedX = x;
		unmodifiedY = y;
		this.x = x*100;
		this.y = (y*100)-24;
		this.id = ObjectId.Grass;
		this.om = om;
		currentAnimation = OverworldAnimation.grass;
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i) != null && om.objects.get(i).id == ObjectId.Grass)
				{
					Grass g = (Grass)om.objects.get(i);
					if(g != null && g != this && g.makesDirt && g.unmodifiedX == this.unmodifiedX & g.unmodifiedY == this.unmodifiedY-1)
					{
						currentAnimation = OverworldAnimation.ground;
					}
				}
			}
	}

	@Override
	public void tick() {
		//System.out.println("I AM GRASS! I LIVE AT: " + x + " , " + y);
	}

	@Override
	public void render(Graphics g) {
		this.animate(x,y,currentAnimation,0,g);
	}
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x,y,100,100);
	}

}
