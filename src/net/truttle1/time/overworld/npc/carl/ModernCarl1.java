package net.truttle1.time.overworld.npc.carl;

import java.awt.Graphics;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.npc.cutscene.Prop;

public class ModernCarl1 extends Carl 
{
	private int cDistance;
	private Prop creaturey;
	private int simonNodTime = 0;
	public ModernCarl1(Game window, int x, int y, OverworldMode om) 
	{
		super(window, x, y, om);
		cDistance = 150;
		currentAnimation = Quest.quests[Quest.KNOWS_ABOUT_CONVEX] > 0 ? OverworldAnimation.carlIdle : OverworldAnimation.carlHappyIdle;
		this.id = ObjectId.NPC;
	}

	@Override
	public void tick()
	{
		if(Quest.quests[Quest.FINDCREATUREY] < 6)
		{
			waiting();
		}
		else if(Quest.quests[Quest.KNOWS_ABOUT_CONVEX] < 6)
		{
			cutscene();
		}
	}
	
	private void cutscene()
	{
		//System.out.println("CUTSCENE TIME");
		if(getPlayer().x > this.x-250)
		{
			if(Global.talkingTo == null && Global.talking == 0 && Quest.quests[Quest.KNOWS_ABOUT_CONVEX] == 0)
			{
				this.setFrame(0, 0);
				Global.cPressed = false;
				Global.talkingTo = this;
				Global.talking++;
				currentAnimation = OverworldAnimation.carlTalk;
				String playerName = "";
				if(getPlayer() instanceof WilliamPlayer)
				{
					playerName = "William";
				}
				else if(getPlayer() instanceof SimonPlayer)
				{
					playerName = "Simon";
				}
				SpeechBubble.talk("Hey " + playerName + "!/Have you seen Creaturey yet?",Global.carlFont);
				Global.disableMovement = true;
				om.stopPlayerMoving(getPlayer());
			}
			if(Global.talkingTo == this && Global.talking == 2 && Quest.quests[Quest.KNOWS_ABOUT_CONVEX] == 0)
			{
				Global.talking = 0;
				Global.talkingTo = this;
				Global.disableMovement = true;
				currentAnimation = OverworldAnimation.carlHappyIdle;
				Quest.quests[Quest.KNOWS_ABOUT_CONVEX] = 1;
			}
			
			if(Quest.quests[Quest.KNOWS_ABOUT_CONVEX] == 1)
			{
				if(creaturey == null)
				{
					getPlayer().flipped = !getPlayer().flipped;
					creaturey = new Prop(window, x-900, y-100, om, OverworldAnimation.creatHappyWalk);
					om.objects.add(creaturey);
				}
				creaturey.flipped = true;
				creaturey.x += 4;
				if(creaturey.x > this.x - 600)
				{
					Quest.quests[Quest.KNOWS_ABOUT_CONVEX] = 2;
				}
			}
			
			if(Quest.quests[Quest.KNOWS_ABOUT_CONVEX] == 2)
			{
				if(Global.talkingTo == this && Global.talking == 0)
				{
					this.setFrame(0, 0);
					Global.cPressed = false;
					Global.talkingTo = this;
					Global.talking++;
					creaturey.currentAnimation = OverworldAnimation.creatHappyTalk2;
					String playerName = "";
					if(getPlayer() instanceof WilliamPlayer)
					{
						playerName = "William";
					}
					else if(getPlayer() instanceof SimonPlayer)
					{
						playerName = "Simon";
					}
					SpeechBubble.talk("Hey " + playerName + " and Carl!/There's some important info I have on the Time Orb, and I needed to/make sure you all were here before I told any of you.",Global.creatFont);
					Global.disableMovement = true;
					om.stopPlayerMoving(getPlayer());
				}
				if(Global.talkingTo == this && Global.talking == 2)
				{
					this.setFrame(0, 0);
					getPlayer().flipped = !getPlayer().flipped;
					Global.cPressed = false;
					Global.talkingTo = this;
					Global.talking++;
					creaturey.currentAnimation = OverworldAnimation.creatHappyIdle2;
					this.currentAnimation = OverworldAnimation.carlTalk;
					String playerName = "";
					if(getPlayer() instanceof WilliamPlayer)
					{
						playerName = "William";
					}
					else if(getPlayer() instanceof SimonPlayer)
					{
						playerName = "Simon";
					}
					SpeechBubble.talk("Well, now that we're all here: what is it?",Global.carlFont);
					Global.disableMovement = true;
					om.stopPlayerMoving(getPlayer());
				}
				if(Global.talkingTo == this && Global.talking == 4)
				{
					this.setFrame(0, 0);
					getPlayer().flipped = !getPlayer().flipped;
					Global.cPressed = false;
					Global.talkingTo = this;
					Global.talking++;
					creaturey.currentAnimation = OverworldAnimation.creatSadTalk2;
					this.currentAnimation = OverworldAnimation.carlIdle;
					String playerName = "";
					if(getPlayer() instanceof WilliamPlayer)
					{
						playerName = "William";
					}
					else if(getPlayer() instanceof SimonPlayer)
					{
						playerName = "Simon";
					}
					SpeechBubble.talk("Well, I was reading some awful tabloid the other day, and it mentioned a/strange \"Glowing Green Orb\" that Convex Inc. was researching!",Global.creatFont);
					Global.disableMovement = true;
					om.stopPlayerMoving(getPlayer());
				}
				if(Global.talkingTo == this && Global.talking == 6)
				{
					this.setFrame(0, 0);
					getPlayer().flipped = !getPlayer().flipped;
					Global.cPressed = false;
					Global.talkingTo = this;
					Global.talking++;
					creaturey.currentAnimation = OverworldAnimation.creatSadIdle;
					this.currentAnimation = OverworldAnimation.carlTalk;
					String playerName = "";
					if(getPlayer() instanceof WilliamPlayer)
					{
						playerName = "William";
					}
					else if(getPlayer() instanceof SimonPlayer)
					{
						playerName = "Simon";
					}
					SpeechBubble.talk("That sounds EXACTLY like the Time Orb!/Too bad our source is apparently an unreliable tabloid.../Hey " + playerName + "! You should check Convex Inc. out and see if that's/true.",Global.carlFont);
					Global.disableMovement = true;
					om.stopPlayerMoving(getPlayer());
				}
				if(Global.talkingTo == this && Global.talking == 8)
				{
					this.setFrame(0, 0);
					getPlayer().flipped = !getPlayer().flipped;
					Global.cPressed = false;
					Global.talkingTo = this;
					Global.talking++;
					creaturey.currentAnimation = OverworldAnimation.creatSadTalk;
					this.currentAnimation = OverworldAnimation.carlIdle;
					String playerName = "";
					if(getPlayer() instanceof WilliamPlayer)
					{
						playerName = "William";
					}
					else if(getPlayer() instanceof SimonPlayer)
					{
						playerName = "Simon";
					}
					SpeechBubble.talk("That's a good idea, but only if " + playerName + " and his crew are up for/the challenge./" + playerName + ": Are you up for the task of investigating Convex Inc?",Global.creatFont);
					Global.disableMovement = true;
					om.stopPlayerMoving(getPlayer());
				}
				if(Global.talkingTo == this && Global.talking == 10)
				{
					Global.talking = 0;
					creaturey.currentAnimation = OverworldAnimation.creatSadIdle;
					this.currentAnimation = OverworldAnimation.carlIdle;
					Quest.quests[Quest.KNOWS_ABOUT_CONVEX] = 3;
					getPlayer().setFrame(0, 0);
				}
			}
			
			if(Quest.quests[Quest.KNOWS_ABOUT_CONVEX] == 3)
			{
				if(getPlayer() instanceof SimonPlayer)
				{
					getPlayer().currentAnimation = BattleAnimation.simonNod;
					simonNodTime++;
					if(simonNodTime >= 24)
					{
						Quest.quests[Quest.KNOWS_ABOUT_CONVEX] = 4;
					}
				}
				else if(getPlayer() instanceof WilliamPlayer)
				{

					if(Global.talkingTo == this && Global.talking == 0)
					{
						this.setFrame(0, 0);
						Global.cPressed = false;
						Global.talkingTo = this;
						Global.talking++;
						getPlayer().setAnimation(BattleAnimation.williamTalk2);
						SpeechBubble.talk("I'll gladly check out that stupid corporate building!//If it gets me closer to getting the Time Orb and figuring out why I'm here,/I don't care how dangerous it is!",Global.willFont);
						Global.disableMovement = true;
						//om.stopPlayerMoving(getPlayer());
					}
					if(Global.talkingTo == this && Global.talking == 2)
					{
						this.setFrame(0, 0);
						Global.cPressed = false;
						Global.talkingTo = this;
						Global.talking++;
						getPlayer().setAnimation(BattleAnimation.williamIdle2);
						creaturey.currentAnimation = OverworldAnimation.creatHappyIdle2;
						this.currentAnimation = OverworldAnimation.carlTalk;
						Global.talking = 0;
						creaturey.currentAnimation = OverworldAnimation.creatSadIdle;
						this.currentAnimation = OverworldAnimation.carlIdle;
						Quest.quests[Quest.KNOWS_ABOUT_CONVEX] = 4;
						getPlayer().setFrame(0, 0);
					}
				}
			}
			
			if(Quest.quests[Quest.KNOWS_ABOUT_CONVEX] == 4)
			{
				if(Global.talkingTo == this && Global.talking == 0)
				{
					this.setFrame(0, 0);
					Global.cPressed = false;
					Global.talkingTo = this;
					Global.talking++;
					creaturey.currentAnimation = OverworldAnimation.creatHappyTalkStomp;
					String playerName = "";
					if(getPlayer() instanceof WilliamPlayer)
					{
						playerName = "William";
					}
					else if(getPlayer() instanceof SimonPlayer)
					{
						playerName = "Simon";
					}
					SpeechBubble.talk("That's good! I'm gonna go look for more information in the rest of the/city.",Global.creatFont);
					Global.disableMovement = true;
					om.stopPlayerMoving(getPlayer());
				}
				if(Global.talkingTo == this && Global.talking == 2)
				{
					this.setFrame(0, 0);
					getPlayer().flipped = !getPlayer().flipped;
					Global.cPressed = false;
					Global.talkingTo = this;
					Global.talking++;
					creaturey.currentAnimation = OverworldAnimation.creatHappyIdle2;
					this.currentAnimation = OverworldAnimation.carlTalk;
					String playerName = "";
					if(getPlayer() instanceof WilliamPlayer)
					{
						playerName = "William";
					}
					else if(getPlayer() instanceof SimonPlayer)
					{
						playerName = "Simon";
					}
					SpeechBubble.talk("And I'm going to stay here!/" + playerName+ ": If you find anything interesting at Convex Inc, tell/me what it is./I'll be here in the park the whole time.",Global.carlFont);
					Global.disableMovement = true;
					om.stopPlayerMoving(getPlayer());
				}
				if(Global.talkingTo == this && Global.talking == 4)
				{
					Global.talking = 0;
					creaturey.currentAnimation = OverworldAnimation.creatHappyIdle2;
					this.currentAnimation = OverworldAnimation.carlIdle;
					Quest.quests[Quest.KNOWS_ABOUT_CONVEX] = 5;
					getPlayer().flipped = !getPlayer().flipped;
				}
			}

			if(Quest.quests[Quest.KNOWS_ABOUT_CONVEX] == 5)
			{
				creaturey.currentAnimation = OverworldAnimation.creatHappyWalk;
				creaturey.flipped = false;
				creaturey.x -= 4;
				if(creaturey.x < this.x - 1000)
				{
					getPlayer().flipped = !getPlayer().flipped;
					Quest.quests[Quest.KNOWS_ABOUT_CONVEX] = 6;
					Global.disableMovement = false;
					om.objects.remove(creaturey);
				}
			}
			
		}
	}
	
	private void waiting()
	{
		if(getPlayer() != null && getPlayer().x >= this.x+40)
		{
			flipped = true;
		}
		else
		{
			flipped = false;
		}
		if(distanceTo(ObjectId.Player)<cDistance && Global.talking == 0 && !Global.disableMovement && Global.cPressed)
		{
			this.setFrame(0, 0);
			Global.cPressed = false;
			Global.talkingTo = this;
			Global.talking++;
			currentAnimation = OverworldAnimation.carlTalk;
			String playerName = "";
			if(getPlayer() instanceof WilliamPlayer)
			{
				playerName = "William";
			}
			else if(getPlayer() instanceof SimonPlayer)
			{
				playerName = "Simon";
			}
			SpeechBubble.talk("Hey " + playerName + "!/I'm just waiting here for Creaturey./I figured he'd come by the park EVENTUALLY, I mean, it's the only/green area in the entire city.");
			Global.disableMovement = true;
			om.stopPlayerMoving(getPlayer());
		}
		if(Global.talkingTo == this && Global.talking == 2)
		{
			Global.talking = 0;
			Global.talkingTo = null;
			Global.disableMovement = false;
			currentAnimation = OverworldAnimation.carlHappyIdle;
		}
	}
	@Override
	public void render(Graphics g) 
	{
		this.animate(x,y,currentAnimation,0,g);
		if(distanceTo(ObjectId.Player)<cDistance && Global.talking == 0 && !Global.disableMovement)
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
	}

}
