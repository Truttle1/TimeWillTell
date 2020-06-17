package net.truttle1.time.overworld.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.ObjectId;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.ModeType;

public class InvisibleWall extends Grass{

	private boolean topLine = true;
	private boolean bottomLine = true;
	private boolean leftLine = true;
	private boolean rightLine = true;
	Color clr;
	public InvisibleWall(int x, int y, Game window, OverworldMode om) {
		super(x, y, window, om);
		currentAnimation = OverworldAnimation.concrete;
		makesDirt = false;
		id = ObjectId.Grass;
		clr = Color.black;
	}
	public InvisibleWall(int x, int y, Game window, OverworldMode om, BufferedImage[] img, Color clr) {
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

	}
}
