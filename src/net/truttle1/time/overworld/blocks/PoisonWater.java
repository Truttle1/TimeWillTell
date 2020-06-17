package net.truttle1.time.overworld.blocks;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Game;

public class PoisonWater extends GameObject{

	public PoisonWater(Game window, int x, int y, BufferedImage[] animation) {
		super(window);
		this.x = x;
		this.y = y;
		this.currentAnimation = animation;
		this.id = ObjectId.Lava;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		this.animate(x, y, currentAnimation, 0, g);
		
	}

}
