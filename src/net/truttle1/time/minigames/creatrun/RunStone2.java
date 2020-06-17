package net.truttle1.time.minigames.creatrun;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.main.GameMode;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;

public class RunStone2 extends RunStone{

	private boolean topLine = true;
	private boolean bottomLine = true;
	private boolean leftLine = true;
	private boolean rightLine = true;
	public int x;
	public int y;
	int unmodifiedX;
	int unmodifiedY;
	MinigameMode1 mm;
	Color color;
	public RunStone2(int x, int y, Game window, MinigameMode1 mm) {
		super(x,y,window,mm);
		currentAnimation = OverworldAnimation.stone2;
		unmodifiedX = x;
		unmodifiedY = y;
		this.x = x*100;
		this.y = (y*100)-24;
		this.id = ObjectId.Grass;
		this.color = new Color(96,59,35);
		this.mm = mm;
	}
	@Override
	public void tick()
	{
		for(int i=0; i<mm.objects.size();i++)
		{
			if(mm.objects.get(i) != null && mm.objects.get(i).id == ObjectId.Grass && mm.objects.get(i) instanceof RunStone2)
			{
				RunStone2 g = (RunStone2)mm.objects.get(i);
				if(g != null && g != this)
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
	
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x,y,100,100);
	}
	@Override
	public void render(Graphics g) {
		currentAnimation = OverworldAnimation.stone2;
		this.animate(x,y,currentAnimation,0,g);
		if(topLine)
		{
			g.setColor(color);
			g.fillRect(x, y, 100, 4);
		}
		if(bottomLine)
		{
			g.setColor(color);
			g.fillRect(x, y+96, 100, 4);
		}
		if(leftLine)
		{
			g.setColor(color);
			g.fillRect(x, y, 4, 100);
		}
		if(rightLine)
		{
			g.setColor(color);
			g.fillRect(x+96, y, 4, 100);
		}
	}
}
