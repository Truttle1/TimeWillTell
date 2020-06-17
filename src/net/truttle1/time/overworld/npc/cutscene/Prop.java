package net.truttle1.time.overworld.npc.cutscene;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.npc.NPC;

public class Prop extends NPC{
	public Prop(Game window, int x, int y, OverworldMode om, BufferedImage[] currentAnimation) {
		super(window, x, y, om);
		super.currentAnimation = currentAnimation;
		this.id = ObjectId.NPC;
	}

	@Override
	public Rectangle topRectangle() {
		return null;
	}

	@Override
	public Rectangle leftRectangle() {
		return null;
	}

	@Override
	public Rectangle rightRectangle() {
		return null;
	}

	@Override
	public Rectangle bottomRectangle() {
		return null;
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	@Override
	public void render(Graphics g) {
		try
		{
			this.animate(x, y, super.currentAnimation, 0, g);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			this.setFrame(0,0);
			this.animate(x, y, super.currentAnimation, 0, g);
		}
	}
	@Override
	public void tick() {
	}
	@Override
	public void setAnimation(BufferedImage[] animation)
	{
		this.currentAnimation = animation;
		super.currentAnimation = animation;
		currentAnimation = animation;
	}
}
