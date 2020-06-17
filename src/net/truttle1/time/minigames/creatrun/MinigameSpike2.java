package net.truttle1.time.minigames.creatrun;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;

public class MinigameSpike2 extends GameObject{

	public int x;
	public int y;
	public MinigameSpike2(int x, int y, Game window) {
		super(window);
		id = ObjectId.Spike2;
		this.x = x;
		this.y = y;
		currentAnimation = OverworldAnimation.spike2;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		this.animate(x, y, currentAnimation, 0, g);
	}
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(this.x,this.y,100,100);
	}

}
