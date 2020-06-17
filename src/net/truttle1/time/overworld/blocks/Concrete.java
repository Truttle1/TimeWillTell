package net.truttle1.time.overworld.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.ObjectId;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.ModeType;

public class Concrete extends Grass{

	private boolean topLine = true;
	private boolean bottomLine = true;
	private boolean leftLine = true;
	private boolean rightLine = true;
	Color clr;
	public Concrete(int x, int y, Game window, OverworldMode om) {
		super(x, y, window, om);
		currentAnimation = OverworldAnimation.concrete;
		makesDirt = false;
		id = ObjectId.Grass;
		clr = Color.black;
	}
	public Concrete(int x, int y, Game window, OverworldMode om, BufferedImage[] img, Color clr) {
		super(x, y, window, om);
		currentAnimation = img;
		makesDirt = false;
		id = ObjectId.Grass;
		this.clr = clr;
	}
	@Override
	public void tick()
	{
		
	}
	@Override
	public void render(Graphics g) {

		int tx = window.overworldMode.tx;
		int ty = window.overworldMode.ty;
		if(window.mode != ModeType.Overworld || (x>tx-300 && x<tx+1200 && y>ty-300 && y<ty+600))
		{
			this.animate(x,y,currentAnimation,0,g);
		}
	}
}
