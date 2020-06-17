package net.truttle1.time.minigames.creatrun;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.main.GameMode;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;

public class MinigameTree extends RunStone{

	private boolean topLine = true;
	private boolean bottomLine = true;
	private boolean leftLine = true;
	private boolean rightLine = true;
	public int hp = 2;
	public int x;
	public int y;
	int unmodifiedX;
	int unmodifiedY;
	MinigameMode1 mm;
	Color color;
	public MinigameTree(int x, int y, Game window, MinigameMode1 mm) {
		super(x,y,window,mm);
		currentAnimation = OverworldAnimation.tree1;
		unmodifiedX = x;
		unmodifiedY = y;
		this.x = x*100;
		this.y = (y*100)-24;
		this.id = ObjectId.Grass;
		this.mm = mm;
	}
	@Override
	public void tick()
	{
		System.out.println(hp);
		if(hp <= 0)
		{
			mm.objects.remove(this);
		}
		if(hp == 1)
		{
			currentAnimation = OverworldAnimation.tree2;
		}
	}
	
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+60,y,250,300);
	}
	public Rectangle treeBounds()
	{
		return new Rectangle(x-20,y,250,300);
	}
	@Override
	public void render(Graphics g) {
		this.animate(x,y+20,currentAnimation,0,g);
		
	}
}
