package net.truttle1.time.overworld.blocks;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.enemies.WilliamE1;
import net.truttle1.time.overworld.npc.NPC;

public class Tree extends NPC{
	boolean usingTree;
	SimonPlayer simon;
	public Tree(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.appleTree;
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
	public void tick()
	{

		if(usingTree)
		{
			if(Fade.fadeIn && Quest.quests[Quest.TUTORIAL] == 7)
			{
				if(Global.talking == 0 && simon != null)
				{
					Global.talkingTo = this;
					Global.talking = 1;
					simon.setFrame(0, 0);
					simon.flipped = true;
					simon.currentAnimation = BattleAnimation.simonApples;
					Global.items[Store.APPLE] += 3;
					SpeechBubble.talk("Simon collected some apples from the tree! He also took some extras/to be used in battle! You can find them in the items menu!");
				}
				if(Global.talking == 2)
				{
	
					Quest.quests[Quest.TUTORIAL] = 8;
					Global.talkingTo = this;
					Global.talking = 3;
					simon.setFrame(0, 0);
					simon.flipped = true;
					simon.currentAnimation = BattleAnimation.simonApples;
					AudioHandler.stopMusic();
					SpeechBubble.talk("HEY!!! WHAT ARE YOU DOING HERE!?!",Global.willFont);
				}
			}
			if(Quest.quests[Quest.TUTORIAL] == 8 && Global.talking == 4)
			{
				AudioHandler.playMusic(AudioHandler.williamTheme);
				Global.talking = 0;
				Global.talkingTo = null;
				om.objects.add(new WilliamE1(om.window,simon.x-800,750,om));
				Quest.quests[Quest.TUTORIAL] = 9;
			}
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
		}
		if(Global.cPressed && Global.talking == 0 && distanceTo(ObjectId.Player)<300)
		{
			if(Quest.quests[Quest.TUTORIAL]==7)
			{
				Global.disableMovement = true;
				Fade.run(om.window,ModeType.Overworld, false);
				usingTree = true;
			}
		}
	}
	@Override
	public void render(Graphics g) {
		this.animate(x, y, currentAnimation, 0, g);

		if(Quest.quests[Quest.TUTORIAL]==7 && distanceTo(ObjectId.Player)<300 && Global.talking == 0 && !Global.disableMovement)
		{
			this.animate(x+150, y+150, BattleAnimation.cIcon, 1, g);
		}
	}

}
