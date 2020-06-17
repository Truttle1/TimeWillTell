package net.truttle1.time.overworld.items;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.ItemObject;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;

public class YellowOrb extends ItemObject{

	int orbTime;
	boolean visible = true;
	SimonPlayer simon;
	public YellowOrb(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		this.currentAnimation = OverworldAnimation.yOrbItem;
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
	public void tick()
	{
		if(simon == null)
		{

			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i) instanceof SimonPlayer || om.objects.get(i).id == ObjectId.Player)
				{
					simon = (SimonPlayer) om.objects.get(i);
				}
			}
		}
		super.tick();
		orbTime++;
		if(orbTime>40 && simon.x>this.x+20 && visible)
		{
			Global.disableMovement = true;
			simon.x -= 4;
			simon.currentAnimation = BattleAnimation.simonWalkAnimation;
			simon.flipped = true;
		}
		else if(orbTime>50 && Quest.quests[Quest.TUTORIAL]==12)
		{
			simon.currentAnimation = BattleAnimation.simonIdleAnimation;
			if(Global.talking == 0)
			{
				Global.disableMovement = true;
				Global.talkingTo = this;
				Global.talking = 1;
				SpeechBubble.talk("Simon obtained a STRANGE YELLOW ORB!");
				//Global.items[Items.YELLOWORB]++;
				visible = false;
			}
			if(Global.talking == 2)
			{
				Quest.quests[Quest.TUTORIAL] = 13;
				Global.talking = 0;
				Global.disableMovement = false;
				AudioHandler.playMusic(AudioHandler.prehistoricMusic);

			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(visible)
		{
			this.animate(x, y, currentAnimation, 0, g);
		}
		
	}

}
