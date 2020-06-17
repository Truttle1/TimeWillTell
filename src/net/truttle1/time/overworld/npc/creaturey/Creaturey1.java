package net.truttle1.time.overworld.npc.creaturey;

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
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;

public class Creaturey1 extends Creaturey
{

	GameObject player;
	public Creaturey1(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.creatSadIdle;
		this.id = ObjectId.NPC;
	}

	@Override
	public void render(Graphics g) {
		if(currentAnimation[2].getWidth()<this.getFrame(0))
		{
			setFrame(0,0);
		}
		try
		{
			this.animate(x, y, currentAnimation, 0, g);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			setFrame(0,0);
		}
	

	}
	@Override
	public void tick()
	{
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

		if(player == null || !om.objects.contains(player))
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
		if(player.x>this.x+100 && Quest.quests[Quest.LOMOBANK]<13)
		{
			flipped = true;
		}
		else
		{
			flipped = false;
		}
		if(Quest.quests[Quest.LOMOBANK] == 13)
		{
			this.x += 8;
			this.flipped = true;
			if(x>player.x+600)
			{
				Quest.quests[Quest.LOMOBANK] = 14;
				Global.talkingTo = null;
				Global.talking = 0;
				Global.disableMovement = false;
				om.objects.remove(this);
			}
		}
		if(Quest.quests[Quest.LOMOBANK]>=14)
		{
			om.objects.remove(this);
		}
		if(Quest.quests[Quest.TUTORIAL]<14)
		{
			if( Global.playingAs == 0 && player.x>this.x-200 && Global.talking == 0)
			{
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatSadTalk;
				player.currentAnimation = BattleAnimation.simonIdleAnimation;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("Umm, sorry to bother you, but I don't think it's safe to/travel on this path...There's this strange guy up ahead who seems/to have taken over the volcano...and he beat me up so badly/that there is this horrible bruise on the right side of my face...",Global.creatFont);
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				SpeechBubble.talk("Man, it would surely be nice if the left side of my face/was the only one that could be seen...but anyway,/I would suggest you turn around...before he hurts you too.../*sob*...HE'S SO MEEEEAN!!!! :(",Global.creatFont);
			
			}
			if(Global.talking >= 4)
			{
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatSadIdle;
				player.currentAnimation = BattleAnimation.simonWalkAnimation;
				player.flipped = true;
				player.x -= 8;
				if(player.x<this.x-400)
				{
					Global.disableMovement = false;
					Global.talking = 0;
					player.currentAnimation = BattleAnimation.simonIdleAnimation;
				}
			}
		}
		

		if(Quest.quests[Quest.LOMOBANK] == 11)
		{
			currentAnimation = OverworldAnimation.creatHappyIdle;
		}
		if(Quest.quests[Quest.LOMOBANK] == 12)
		{
			System.out.println(player.x);
			if(!Global.disableMovement && !(player instanceof SimonPlayer) && Global.playingAs == 1 && player.x>this.x-200 && Global.talking == 0)
			{
				player.hVelocity = 0;
				window.overworldMode.ty = player.y-300;
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatHappyTalk;
				player.currentAnimation = BattleAnimation.williamIdle3;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("Hey there William! How are you doing on this fine day!/Wait a second...have we met before...I mean, have you met me.../I know I've met you...",Global.creatFont);
			}
			else if(Global.talkingTo == this && Global.talking == 2)
			{
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatHappyIdle;
				player.currentAnimation = BattleAnimation.williamTalkPoint;
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				SpeechBubble.talk("Who are you, you strange green monster!? I've never seen you in my life!/Now, the fact that you've seen me enough to/know my NAME is quite concerning!/So who are you and who are you working for!? TELL ME NOW!!!",Global.willFont);
			}
			else if(Global.talkingTo == this && Global.talking == 4)
			{
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatSadTalk;
				player.currentAnimation = BattleAnimation.williamIdle3;
				Global.talkingTo = this;
				Global.talking = 5;
				Global.disableMovement = true;
				SpeechBubble.talk("I'm...not working for anyone...I'm...just a humble time traveler.../You see, me and my friend Carl enjoy visiting different time/periods using our time machines...",Global.creatFont);
			}
			else if(Global.talkingTo == this && Global.talking == 6)
			{
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatSadTalk;
				player.currentAnimation = BattleAnimation.williamIdle3;
				Global.talkingTo = this;
				Global.talking = 7;
				Global.disableMovement = true;
				SpeechBubble.talk("And the crazy thing is...we met you several times on your travels.../You were looking for these strange things called Time Orbs.../to save the world from something in the far future.../you don't remember any of this...?...sir?",Global.creatFont);
			}
			else if(Global.talkingTo == this && Global.talking == 8)
			{
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatSadIdle;
				player.currentAnimation = BattleAnimation.williamTalk2;
				Global.talkingTo = this;
				Global.talking = 9;
				Global.disableMovement = true;
				SpeechBubble.talk("Wait, I'm a time traveler? And those orbs are related to time travel?/Anything else about me that you know from the future?/And...about the past...I don't know how I got this time orb.../or why I kept it stored in that tree...",Global.willFont);
			}
			else if(Global.talkingTo == this && Global.talking == 10)
			{
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatHappyTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
				Global.talkingTo = this;
				Global.talking = 11;
				Global.disableMovement = true;
				SpeechBubble.talk("Well, I know some more things, but I shouldn't tell you those.../they might impact your decisions.../but I can tell you that you carry around this encyclopedia.../and as it turns out, I have a copy of it...",Global.creatFont);
			}
			else if(Global.talkingTo == this && Global.talking == 12)
			{
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatHappyTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
				Global.talkingTo = this;
				Global.talking = 13;
				Global.disableMovement = true;
				SpeechBubble.talk("so I guess I'm supposed to give it to you...strange, huh?.../Also, I'd warn about going up ahead to the volcano...this guy/gave me a bruise on my face, yeah, it healed so you can't see it anymore./Also, I think that guy also captured that Caveman...strange...",Global.creatFont);
			}
			else if(Global.talkingTo == this && Global.talking == 14)
			{
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatHappyIdle;
				player.currentAnimation = BattleAnimation.williamItem;
				player.heldItem = OverworldAnimation.bookItem;
				Global.talkingTo = this;
				Global.talking = 15;
				Global.disableMovement = true;
				SpeechBubble.talk("WILLIAM OBTAINED THE ENCYCLOPEDIA");
			}
			else if(Global.talkingTo == this && Global.talking == 16)
			{
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatHappyTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
				player.heldItem = null;
				Global.talkingTo = this;
				Global.talking = 17;
				Global.disableMovement = true;
				SpeechBubble.talk("I'm gonna go look for Carl...the caiman...don't  call him/any other type of crocodilian, he's sensitive about that.../but anyways, he said he'd be at Lomo village...to get there, you head/east, then turn west at the staircasey thing...anyway see ya... ",Global.creatFont);
			}
			else if(Global.talkingTo == this && Global.talking >= 18)
			{
				Global.talkSound = 0;
				currentAnimation = OverworldAnimation.creatHappyWalk;
				player.currentAnimation = BattleAnimation.williamIdle3;
				Global.talkingTo = this;
				Quest.quests[Quest.LOMOBANK] = 13;
			}
		}
	}

}
