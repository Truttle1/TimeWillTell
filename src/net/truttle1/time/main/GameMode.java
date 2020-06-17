package net.truttle1.time.main;

import java.awt.Graphics;
import java.util.ArrayList;

import net.truttle1.time.battle.EyeCandy;

public abstract class GameMode {
	public Game window;
	public ArrayList<EyeCandy> eyeCandy = new ArrayList<EyeCandy>();
	public abstract void tick();
	public abstract void render(Graphics g);
	public GameMode(Game window)
	{
		this.window = window;
	}
}
