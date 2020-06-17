package net.truttle1.time.overworld.npc.cutscene;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.npc.NPC;

public class CavemanCageDecoration extends NPC{

	BufferedImage[] cage;
	public CavemanCageDecoration(Game window, int x, int y, OverworldMode om, BufferedImage[] currentAnimation) {
		super(window, x, y, om);
		this.currentAnimation = currentAnimation;
		this.cage = OverworldAnimation.cage;
		this.id = ObjectId.NPC;
		if(Math.random()<=.5)
		{
			this.flipped = true;
		}
		this.setFrame(0,(int)(Math.random()*12));
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
	public void render(Graphics g) 
	{
		if(Quest.quests[Quest.LOMO] == Global.LOMOCONSTANT)
		{
			om.objects.remove(this);
		}
		animate(this.x,this.y,currentAnimation,0,g);
		animate(this.x-50,this.y-50,cage,1,g);
	}
	
	@Override
	public void tick()
	{
		if(Quest.quests[Quest.LOMO] == Global.LOMOCONSTANT)
		{
			om.objects.remove(this);
		}
	}

}
