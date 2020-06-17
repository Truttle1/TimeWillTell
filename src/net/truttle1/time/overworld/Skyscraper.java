package net.truttle1.time.overworld;

import java.awt.Graphics;

import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.ObjectId;

public class Skyscraper extends GameObject{

	private int subImage;
	public Skyscraper(Game window, int x, int y, int subImage) {
		super(window);
		this.x = x;
		this.y = y;
		this.subImage = subImage;
		this.id = ObjectId.Decoration;
		this.currentAnimation = OverworldAnimation.skyscraper;
		
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render(Graphics g) {
		this.setFrame(0, subImage);
		this.animateBig(x, y-20, currentAnimation, 0, g);
		
	}

}
