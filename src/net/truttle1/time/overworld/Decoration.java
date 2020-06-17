package net.truttle1.time.overworld;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.ObjectId;

public class Decoration extends GameObject{

	private BufferedImage[] graphic;
	private int verticalOffset;
	public Decoration(Game window, int x, int y, BufferedImage[] iGraphic, int vertOffset) {
		super(window);
		this.x = x;
		this.y = y;
		this.id = ObjectId.DecorationOverGrass;
		graphic = iGraphic;
		verticalOffset = vertOffset;
		this.currentAnimation = graphic;
		
	}
	@Override
	public void tick() 
	{
		
	}
	@Override
	public void render(Graphics g) 
	{
		this.animateBig(x, y + verticalOffset, currentAnimation, 0, g);
		
	}

}
