package net.truttle1.time.overworld.npc.carl;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.npc.NPC;

public class Carl1 extends Carl{

	GameObject simon;
	public Carl1(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.carlIdle;
		this.id = ObjectId.NPC;
	}

	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+16,y+16,164,144);
	}

	@Override
	public void render(Graphics g) {
		if(distanceTo(ObjectId.Player)<165 && Global.talking == 0 && !Global.disableMovement)
		{
			if(flipped)
			{
				this.animateNoFlip(x+50, y-100, BattleAnimation.cIcon, 1, g);
			}
			else
			{
				this.animate(x+50, y-100, BattleAnimation.cIcon, 1, g);
			}
		}
		this.animate(x, y, currentAnimation, 0, g);
	}
	@Override
	public void tick()
	{
		super.tick();
		if(Quest.quests[Quest.LOMOBANK]>1)
		{
			om.objects.remove(this);
		}
		if(simon == null || !om.objects.contains(simon))
		{

			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i) instanceof SimonPlayer || om.objects.get(i).id == ObjectId.Player)
				{
					simon = om.objects.get(i);
				}
			}
		}
		if(simon.x>this.x+100)
		{
			flipped = true;
		}
		else
		{
			flipped = false;
		}
		if(Quest.quests[Quest.TUTORIAL]<12 && Global.playingAs == 0)
		{
			if(Global.cPressed && Global.talking == 0 && distanceTo(ObjectId.Player)<165)
			{
				currentAnimation = OverworldAnimation.carlTalk;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("Hey there! I'm Carl the caiman! I'm not an alligator, nor am I a/gharial, and I'm certainly not a crocodile!! I've been traveling/around this place with my friend Creaturey. There's a strange guy/living in the volcano up ahead.",Global.carlFont);
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				SpeechBubble.talk("He think's he's something special, but really, he's a total loser/who likes to bully the villagers... Oh yeah, there's a village up/ahead too... It's called Lomo Village! But of course, you'd know/that. You live near it.",Global.carlFont);
			
			}
			if(Global.talking == 4  && Global.talkingTo == this)
			{
				currentAnimation = OverworldAnimation.carlIdle;
				Global.disableMovement = false;
				Global.talking = 0;
			}
		}
	}

}
