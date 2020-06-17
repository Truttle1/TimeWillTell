package net.truttle1.time.overworld.npc;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.enemies.WilliamE1;
import net.truttle1.time.overworld.npc.carl.Carl2;

public class Carne1 extends NPC{
	GameObject player;
	private BufferedImage[] tempSkrapps;
	private int skrappsX;
	private boolean skrappsFlipped;
	private int stage;
	public Carne1(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.carneIdle;
		this.id = ObjectId.NPC;
	}

	@Override
	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+60,y+280,100,50);
	}
	@Override
	public Rectangle topRectangle()
	{
		return new Rectangle(x+60,y+4,60,50);
	}
	@Override
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+120,y+4,50,120);
	}
	@Override
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+45,y+24,50,120);
	}
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x+38,y+100,120,185);
	}
	@Override
	public void tick()
	{
		if(Quest.quests[Quest.ESCAPED] == 1)
		{
			this.x = 1600;
			this.y = 1230;
			if(player != null && player.x >=this.x-300)
			{
				if(stage == 0)
				{
					if(Global.talking == 0)
					{
						om.stopPlayerMoving(player);
						Global.talkingTo = this;
						this.currentAnimation = OverworldAnimation.rageTalk1;
						if(player instanceof SimonPlayer)
						{
							player.setAnimation(BattleAnimation.simonIdleAnimation);
						}
						if(player instanceof WilliamPlayer)
						{
							player.currentAnimation = BattleAnimation.williamIdle2;
						}
						Global.talking++;
						SpeechBubble.talk("Dudes, leave this volcano. I'm serious, this isn't a safe place to be!!!/Now, I really wanna see Carne again, so start walking leftwards so/I can leave.");
					}
					if(Global.talking == 2 && Global.talkingTo == this)
					{
						Global.talking = 0;
						stage++;
						Global.talkingTo = null;
						
					}
				}
			}
			if(stage == 1)
			{
				this.currentAnimation = OverworldAnimation.rageIdle1;
				player.x -= 8;
				if(player instanceof SimonPlayer)
				{
					player.setAnimation(BattleAnimation.simonWalkAnimation);
					player.flipped = true;
				}
				if(player instanceof WilliamPlayer)
				{
					player.currentAnimation = BattleAnimation.williamWalkAnimation;
					player.flipped = false;
				}
				if(player.x <= this.x-500)
				{
					Global.disableMovement = false;
					stage = 0;
					Global.talking = 0;
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
		if(Quest.quests[Quest.MEETCARNE]==0 && player.x>this.x-275)
		{
			if(Global.talking == 0)
			{
				Global.bossBattle = false;
				Global.disableMovement = true;
				Global.talking = 1;
				Global.talkingTo = this;
				currentAnimation = OverworldAnimation.carneIdleSad;
				player.currentAnimation = BattleAnimation.williamTalkPoint;
				SpeechBubble.talk("What!? ANOTHER ONE!?!?! *sigh*...Let me guess, you're going/to challenge me to a fight!? You Flairmers are so predictable!",Global.willFont);
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				Global.talking = 3;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamIdle3;
				currentAnimation = OverworldAnimation.carneTalkSad;
				SpeechBubble.talk("What!? No, I'm not violent!...It's just that, ever since that dumb new/guy declared himself the King and threw out our old King, he's been/ordering his subjects to act...strangely. Of course, me and my boyfriend/have been ignoring his decrees from the beginning.");
			}
			if(Global.talking == 4 && Global.talkingTo == this)
			{
				Global.talking = 5;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamTalk3;
				currentAnimation = OverworldAnimation.carneIdleSad;
				SpeechBubble.talk("Oh, sorry about that ma'am, It's just that...I've met a couple Flairmers before.../and all three of them attacked me...",Global.willFont);
			}
			if(Global.talking == 6 && Global.talkingTo == this)
			{
				Global.talking = 7;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamIdle2;
				currentAnimation = OverworldAnimation.carneTalkSad;
				SpeechBubble.talk("It's okay, I get that alot...especially under this new King...I mean,/Flairmer kings have always been sort-of jerkwads, but this guy takes/it to a whole new level! My name is Carne by the way, nice to meet you!");
			}
			if(Global.talking == 8 && Global.talkingTo == this)
			{
				Global.talking = 9;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamTalk2;
				currentAnimation = OverworldAnimation.carneIdle;
				SpeechBubble.talk("Right back at ya!...well, my name's William, not Carne, but...ehh.../If I may ask, what brings you here to the outside of the volcano?",Global.willFont);
			}
			if(Global.talking == 10 && Global.talkingTo == this)
			{
				Global.talking = 11;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamIdle2;
				currentAnimation = OverworldAnimation.carneTalk;
				SpeechBubble.talk("Oh, I sell items! Yup, gone are the days of trekking all the way/back to Lomo Village if you want to order a Tenderlomo!/I'm also advertizing for my boyfriend Rage's business:/If you need to learn a magic spell, Rage can get you one, I tell!");
			}
			if(Global.talking >= 12 && Global.talkingTo == this)
			{
				if(tempSkrapps == null)
				{
					tempSkrapps = BattleAnimation.skrappsRun;
					skrappsX = player.x;
					skrappsFlipped = true;
				}
				else
				{
					skrappsX += 8;
					if(skrappsX>player.x+150)
					{
						Quest.quests[Quest.MEETCARNE] = 1;
						Global.talking = 0;
					}
				}
			}
		}

		if(Quest.quests[Quest.MEETCARNE]==1)
		{
			if(Global.talking == 0)
			{
				Global.disableMovement = true;
				Global.talking = 1;
				Global.talkingTo = this;
				currentAnimation = OverworldAnimation.carneIdleSad;
				player.currentAnimation = BattleAnimation.williamIdle3;
				tempSkrapps = BattleAnimation.skrappsTalk;
				SpeechBubble.talk("Wait, did you say MAGIC!?!? Dude, I'm really interested in learning magic! I NEED to meet/that Rage guy!",Global.skrappsFont);
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				Global.disableMovement = true;
				Global.talking = 3;
				Global.talkingTo = this;
				currentAnimation = OverworldAnimation.carneIdleSad;
				player.currentAnimation = BattleAnimation.williamTalk;
				skrappsFlipped = false;
				tempSkrapps = BattleAnimation.skrappsIdle2;
				SpeechBubble.talk("Dude, that sounds like a bad idea cherry on top of a bad idea cake that was burned in/a fire of bad ideas! I mean seriously, his name is **RAGE** for whatever-type-of-diety/I believe in's sake! What good can come out of someone literally NAMED RAGE!?!",Global.willFont);
			}
			if(Global.talking == 4 && Global.talkingTo == this)
			{
				Global.disableMovement = true;
				Global.talking = 5;
				Global.talkingTo = this;
				currentAnimation = OverworldAnimation.carneTalk;
				player.currentAnimation = BattleAnimation.williamIdle3;
				skrappsFlipped = true;
				tempSkrapps = BattleAnimation.skrappsIdle2;
				SpeechBubble.talk("Oh, Rage is actually quite sweet, he's one of the nicest and most selfless/Flairmers alive! Of course, that may not be saying much, especially/nowadays, but take my word for it: Rage is a good guy. You can find/him somewhere inside of the mountain!");
			}
			if(Global.talking == 6 && Global.talkingTo == this)
			{
				Global.disableMovement = true;
				Global.talking = 7;
				Global.talkingTo = this;
				currentAnimation = OverworldAnimation.carneIdleSad;
				player.currentAnimation = BattleAnimation.williamTalk2;
				skrappsFlipped = false;
				tempSkrapps = BattleAnimation.skrappsIdle2;
				SpeechBubble.talk("Kay then...I still don't really trust a person named Rage, but if Skrapps does, it's/his funeral...Now I suppose it's time we be on our way. We have a supposedly very/important mission to attend to! Pleasure speaking with ya, Carne!",Global.willFont);
			}

			if(Global.talking == 8 && Global.talkingTo == this)
			{
				Global.disableMovement = true;
				Global.talking = 9;
				Global.talkingTo = this;
				currentAnimation = OverworldAnimation.carneTalkSad;
				player.currentAnimation = BattleAnimation.williamIdle2;
				skrappsFlipped = true;
				tempSkrapps = BattleAnimation.skrappsIdle2;
				SpeechBubble.talk("See you! Remember to come talk to me if you need items! Also, be/warned: Ignacio-following Flairmers are inside of the mountain and/they will not hesitate to beat you up, so be very careful!");
			}
			if(Global.talking >= 10 && Global.talkingTo == this)
			{
				currentAnimation = OverworldAnimation.carneIdle;
				tempSkrapps = BattleAnimation.skrappsRun;
				skrappsFlipped = false;
				skrappsX -= 8;
				if(skrappsX<player.x)
				{
					Global.disableMovement = false;
					Global.talking = 0;
					Global.talkingTo = null;
					tempSkrapps = null;
					Quest.quests[Quest.MEETCARNE] = 2;
				}
			}
		}
		if(Quest.quests[Quest.MEETCARNE]>=2)
		{
			if(Global.cPressed && Global.talking == 0 && distanceToPoint(ObjectId.Player,this.x+60,this.y)<150)
			{
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				currentAnimation = OverworldAnimation.carneTalk;
				SpeechBubble.talk("Hey there, traveler! My name is Carne, and I'm selling items! May I take/your order?");
				
				//Global.disableMovement = true;
			}
			if(Global.talkingTo == this && Global.talking == 2)
			{
				currentAnimation = OverworldAnimation.carneIdle;
				Store.running = false;
				Global.talking = 3;
				Store.selection = 0;
				Store.initStore(4);
				Store.setItem(0, 0, 6);
				Store.setItem(1, 1, 10);
				Store.setItem(2, 2, 5);
				Store.setItem(3, 4, 20);
				Store.setItem(4, 5, 9);
				//Store.setItem(3, 3, 15);
			}
			if(Global.talkingTo == this && Global.talking == 4)
			{
				Global.talking = 5;
				SpeechBubble.talk("See you around! And remember: /If you need to learn a magic spell, Rage can get you one, I tell!");

				currentAnimation = OverworldAnimation.carneTalkSad;
			}
			if(Global.talkingTo == this && Global.talking == 6)
			{

				Store.running = false;
				Global.talking = 0;
				Global.disableMovement = false;
				AudioHandler.playMusic(Global.currentRoom.music);
				currentAnimation = OverworldAnimation.carneIdle;
			}
			
		}
		if(player.x>this.x+100)
		{
			this.flipped = true;
		}
		else
		{
			this.flipped = false;
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
					this.y = g.y-220;
				}
			}
		}
		if(Quest.quests[Quest.ESCAPED] != 1)
		{
			x+=hVelocity;
			y+=vVelocity;
			vVelocity += 2;
		}
	}
	
	@Override
	public void render(Graphics g) {

		if(distanceToPoint(ObjectId.Player,this.x+60,this.y)<150 && Global.talking == 0 && !Global.disableMovement)
		{
			if(flipped)
			{
				this.animateNoFlip(x+150, y-100, BattleAnimation.cIcon, 2, g);
			}
			else
			{
				this.animate(x+150, y-100, BattleAnimation.cIcon, 2, g);
			}
		}
		if(Quest.quests[Quest.ESCAPED] == 1 && Global.talking == 0)
		{
			currentAnimation = OverworldAnimation.rageIdle1;
		}
		else if(Quest.quests[Quest.ESCAPED] == 1)
		{
			currentAnimation = OverworldAnimation.rageTalk1;
		}
		this.animate(x, y, currentAnimation, 0, g);
		if(tempSkrapps != null)
		{
			if(getFrame(1)>this.getAnimationLength(tempSkrapps))
			{
				setFrame(1,0);
			}
			if(skrappsFlipped)
			{
				this.animateFlip(skrappsX, y+95, tempSkrapps, 1, g);
			}
			else
			{
				this.animateNoFlip(skrappsX, y+95, tempSkrapps, 1, g);
			}
		}
		
	}

}
