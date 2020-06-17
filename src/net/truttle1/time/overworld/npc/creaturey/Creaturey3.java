package net.truttle1.time.overworld.npc.creaturey;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;
import net.truttle1.time.overworld.npc.carl.Carl2;

public class Creaturey3 extends Creaturey{

	GameObject player;
	Carl2 carl;
	int translateTo;
	public Creaturey3(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.creatSadIdle2;
		this.id = ObjectId.NPC;
		this.flipped = false;
	}

	@Override
	public void render(Graphics g) {

		if(Quest.quests[Quest.LOMO] >= 6)
		{
			
		}
		if(currentAnimation[2].getWidth()<=getFrame(0))
		{
			this.setFrame(0,0);
		}
		this.animate(x, y, currentAnimation, 0, g);
		
	}
	@Override
	public void tick()
	{
		if(Quest.quests[Quest.LOMO] >= 6)
		{
			om.objects.remove(this);
		}
		else if(player == null || !om.objects.contains(player) || carl == null || !om.objects.contains(carl))
		{

			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i) instanceof SimonPlayer)
				{
					player = (SimonPlayer) om.objects.get(i);
				}
				if(om.objects.get(i) instanceof WilliamPlayer)
				{
					player = (WilliamPlayer) om.objects.get(i);
				}
			}
		}
		if(Quest.quests[Quest.LOMO] == 5)
		{
			if(Global.talking == 0 && player.y<=this.y+200 && player.x>this.x-225)
			{
				window.overworldMode.ty = this.y-150;
				currentAnimation = OverworldAnimation.creatSadTalk2;
				player.currentAnimation = BattleAnimation.williamIdle3;
				player.hVelocity = 0;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				this.flipped = false;
				SpeechBubble.talk("I think this is the only way that Iggy could've gone.../I mean, there's been no diversions in this path up to this/point, and I don't see any ahead, so/I think we're getting close to finding Carl...",Global.creatFont);
			}
			if(Global.talking>=2 && Global.talkingTo == this)
			{
				this.flipped = true;
				this.x += 8;
				currentAnimation = OverworldAnimation.creatSadWalk;
				if(this.x>1600)
				{
					Global.disableMovement = false;
					Quest.quests[Quest.LOMO] = 6;
					Global.talking = 0;
					om.objects.remove(this);
				}
			}
		}
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
			{
				Grass g = (Grass)window.overworldMode.objects.get(i);

				if(g.getBounds().intersects(leftRectangle())) {
					if(hVelocity<0)
					{
						hVelocity = -hVelocity;
					}
				}

				if(g.getBounds().intersects(rightRectangle())) {
					if(hVelocity>0)
					{
						hVelocity = -hVelocity;
					}
				}
				if(g.getBounds().intersects(topRectangle())) {
					if(vVelocity<0)
					{
						vVelocity = 0;
					}
				}
				if(g.getBounds().intersects(bottomRectangle()) && vVelocity>0)
				{
					vVelocity = 0;
					this.y = g.y-280;
				}
			}
		}

		
	}

}
