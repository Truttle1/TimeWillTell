package net.truttle1.time.overworld.blocks;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.main.Game;

public class Lava extends GameObject{

	public Lava(Game window, int x, int y, BufferedImage[] animation) {
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
	public Rectangle getBounds()
	{
		return new Rectangle(x,y+32,100,64);
	}
	@Override
	public void render(Graphics g) {
		int tx = window.overworldMode.tx;
		int ty = window.overworldMode.ty;
		if(window.mode != ModeType.Overworld || (x>tx-300 && x<tx+1200 && y>ty-300 && y<ty+600))
		{
			if(currentAnimation.equals(OverworldAnimation.lava))
			{

				this.animate(x, y-30, currentAnimation, 0, g);
			}
			this.animate(x, y, currentAnimation, 0, g);
		}
		
		
	}

}
