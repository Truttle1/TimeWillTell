package net.truttle1.time.overworld.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.ObjectId;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.ModeType;

public class Tiles extends Grass{

	private boolean topLine = true;
	private boolean bottomLine = true;
	private boolean leftLine = true;
	private boolean rightLine = true;
	Color clr;
	public Tiles(int x, int y, Game window, OverworldMode om) {
		super(x, y, window, om);
		currentAnimation = OverworldAnimation.tiles;
		makesDirt = false;
		id = ObjectId.Grass;
		clr = Color.black;
	}
	public Tiles(int x, int y, Game window, OverworldMode om, BufferedImage[] img, Color clr) {
		super(x, y, window, om);
		currentAnimation = img;
		makesDirt = false;
		id = ObjectId.Grass;
		this.clr = clr;
	}
	@Override
	public void tick()
	{
		//System.out.println("!");
		int tx = window.overworldMode.tx;
		int ty = window.overworldMode.ty;
		if(window.mode != ModeType.Overworld || (x>tx-300 && x<tx+1200 && y>ty-300 && y<ty+600))
		{
			if(topLine && bottomLine && leftLine && rightLine)
			{
				for(int i=0; i<om.objects.size();i++)
				{
					if(om.objects.get(i) != null && om.objects.get(i).id == ObjectId.Grass)
					{
						Grass g = (Grass)om.objects.get(i);
						if(g != null && g != this && g instanceof Tiles)
						{
							if(g.unmodifiedX == this.unmodifiedX & g.unmodifiedY == this.unmodifiedY-1)
							{
								topLine = false;
							}
							if(g.unmodifiedX == this.unmodifiedX & g.unmodifiedY == this.unmodifiedY+1)
							{
								bottomLine = false;
							}
							if(g.unmodifiedX == this.unmodifiedX+1 & g.unmodifiedY == this.unmodifiedY)
							{
								rightLine = false;
							}
							if(g.unmodifiedX == this.unmodifiedX-1 & g.unmodifiedY == this.unmodifiedY)
							{
								leftLine = false;
							}
						}
					}
				}
			}
			
		}
	}
	@Override
	public void render(Graphics g) {

		int tx = window.overworldMode.tx;
		int ty = window.overworldMode.ty;
		if(window.mode != ModeType.Overworld || (x>tx-300 && x<tx+1200 && y>ty-300 && y<ty+600))
		{
			this.animate(x,y,currentAnimation,0,g);
			if(topLine)
			{
				g.setColor(clr);
				g.fillRect(x, y, 100, 4);
			}
			if(bottomLine)
			{
				g.setColor(clr);
				g.fillRect(x, y+96, 100, 4);
			}
			if(leftLine)
			{
				g.setColor(clr);
				g.fillRect(x, y, 4, 100);
			}
			if(rightLine)
			{
				g.setColor(clr);
				g.fillRect(x+96, y, 4, 100);
			}
		}
	}
}
