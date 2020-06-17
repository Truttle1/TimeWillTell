package net.truttle1.time.overworld.npc.carl;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.WilliamBattler;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;
import net.truttle1.time.overworld.npc.cutscene.IggyOverworld1;

public class Carl3 extends Carl{

	public boolean caged = false;
	private BufferedImage[] cage;
	int cageTalk;
	public Carl3(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.carlIdle;
		cage = OverworldAnimation.cage;
		this.id = ObjectId.NPC;
		this.flipped = false;
		this.caged = true;
	}

	@Override
	public void tick()
	{

		if(Quest.quests[Quest.LOMO] >= 9)
		{
			om.objects.remove(this);
		}
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
			{
				Grass g = (Grass)window.overworldMode.objects.get(i);
				if(g.getBounds().intersects(topRectangle())) {
					if(vVelocity<0)
					{
						vVelocity = 0;
					}
				}
				if(g.getBounds().intersects(bottomRectangle()) && vVelocity>0)
				{
					vVelocity = 0;
					this.y = g.y-180;
				}
			}
		}
		x+=hVelocity;
		y+=vVelocity;
		vVelocity+=2;
	}

	@Override
	public void render(Graphics g) {
		if(currentAnimation[2].getWidth()<this.getFrame(0))
		{
			setFrame(0,0);
		}
		this.animate(x, y, currentAnimation, 0, g);
		if(caged)
		{
			this.animate(x-30, y-50, cage, 1, g);
		}
	}

}
