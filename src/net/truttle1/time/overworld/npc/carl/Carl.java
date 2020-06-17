package net.truttle1.time.overworld.npc.carl;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.npc.NPC;

public abstract class Carl extends NPC {

	public Carl(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+64,y+152,118,40);
	}
	@Override
	public Rectangle topRectangle()
	{
		return new Rectangle(x+64,y+16,118,50);
	}
	@Override
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+120,y+24,50,120);
	}
	@Override
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+45,y+24,50,120);
	}
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+16,y+16,164,144);
	}


}
