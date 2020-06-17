package net.truttle1.time.overworld.npc.creaturey;

import java.awt.Rectangle;

import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.npc.NPC;

public abstract class Creaturey extends NPC
{

	public Creaturey(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
	}

	@Override
	public final Rectangle bottomRectangle()
	{
		return new Rectangle(x+64,y+64,190,40);
	}
	@Override
	public final Rectangle topRectangle()
	{
		return new Rectangle(x+64,y+64,190,40);
	}
	@Override
	public final Rectangle rightRectangle()
	{
		return new Rectangle(x+200,y+16,50,220);
	}
	@Override
	public final Rectangle leftRectangle()
	{
		return new Rectangle(x+80,y+16,50,220);
	}
	@Override
	public final Rectangle getBounds()
	{
		return new Rectangle(x+64,y+16,190,260);
	}
}
