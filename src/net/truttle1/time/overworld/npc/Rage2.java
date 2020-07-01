package net.truttle1.time.overworld.npc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.blocks.Warp;

public class Rage2 extends NPC{

	private int stage = 0;
	private BufferedImage[] skrapps;
	private BufferedImage[] boulder0;
	private int b0y = 0;
	private BufferedImage[] boulder1;
	private int b1y = 0;
	private boolean skrappsFlipped;
	private int skrappsX;
	private GameObject player;
	private boolean showingMoney;
	private int origX;
	private boolean boughtSpell = false;
	private boolean fadedIn = false;
	
	public Rage2(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.rageIdle3;
		this.id = ObjectId.NPC;
		this.flipped = false;
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
	public void render(Graphics g) {
		if(skrapps != null)
		{
			if(getFrame(1)>getAnimationLength(skrapps)-1)
			{
				setFrame(1,0);
			}
			try
			{
				if(skrappsFlipped)
				{
					this.animateFlip(skrappsX, y+120, skrapps, 1, g);
				}
				else
				{
					this.animateNoFlip(skrappsX, y+120, skrapps, 1, g);
				}
			}
			catch(Exception e)
			{
				setFrame(1,0);
				if(skrappsFlipped)
				{
					this.animateFlip(skrappsX, y+125, skrapps, 1, g);
				}
				else
				{
					this.animateNoFlip(skrappsX, y+125, skrapps, 1, g);
				}
			}
		}
		if(this.getFrame(0)>this.getAnimationLength(currentAnimation))
		{
			this.setFrame(0, 0);
		}
		this.animate(x, y, currentAnimation, 0, g);
		if(distanceTo(ObjectId.Player)<175 && Global.talking == 0 && !Global.disableMovement)
		{
			if(flipped)
			{
				this.animateNoFlip(x+50, y-50, BattleAnimation.cIcon, 3, g);
			}
			else
			{
				this.animate(x+50, y-50, BattleAnimation.cIcon, 3, g);
			}
		}
		if(showingMoney)
		{
			g.setFont(Global.winFont2);

			g.setColor(Color.gray.darker());
			g.drawString("$" + Global.money, this.x+124, this.y+384);
			g.setColor(Color.white);
			g.drawString("$" + Global.money, this.x+120, this.y+380);
		}
	}
	@Override
	public void tick()
	{
		if(player == null || !om.objects.contains(player))
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i).id == ObjectId.Player)
				{
					player = om.objects.get(i);
					break;
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
					this.y = g.y-250;
				}
			}
		}
		talk();
		x+=hVelocity;
		y+=vVelocity;
		vVelocity += 2;
	}

	private void talk()
	{
		if(Global.talking == 0)
		{
			if(player.x >= this.x+100)
			{
				this.flipped = true;
			}
			else
			{
				this.flipped = false;
			}
		}
		if(Quest.quests[Quest.HAS_FIREBALL] == 0)
		{

			if(Global.cPressed && Global.talking == 0 && !Global.disableMovement && distanceTo(ObjectId.Player)<175)
			{
				Global.money = 250;
				Global.talkingTo = this;
				Global.talking = 1;
				currentAnimation = OverworldAnimation.rageTalk;
				SpeechBubble.talk("Hey dudes!/Man, ruling a kingdom is quite a task./But I know what you're here for! You dudes are looking for a spell,/correct?");
			}
			if(Global.talkingTo == this && Global.talking == 2)
			{
				Global.talkingTo = this;
				Global.talking = 3;
				currentAnimation = OverworldAnimation.rageTalk;
				showingMoney = true;
				SpeechBubble.yesNo("Well, I can teach the \"Fireball\" spell to that blue dude with the stick!/\"Fireball\" damages the enemy with, well, a ball of fire./What's more, the enemy could also become burned! Cool, right!?/This fantastic spell could be yours for only $200!",Global.textFont);
			}
			if(Global.talkingTo == this && Global.talking >= 4)
			{
				if(SpeechBubble.selection == 1)
				{
					noOption();
				}
				else
				{
					yesOptionFireball();
				}
			}
		}
		else
		{

			if(Global.cPressed && Global.talking == 0 && !Global.disableMovement && distanceTo(ObjectId.Player)<175)
			{
				Global.talkingTo = this;
				Global.talking = 1;
				currentAnimation = OverworldAnimation.rageTalk;
				SpeechBubble.talk("Hey dudes!/Man, ruling a kingdom is quite a task./I would offer you dudes some spells, but I don't have any more.../Good luck on your journey though, dudes!");
			}
			if(Global.talkingTo == this && Global.talking == 2)
			{
				Global.talkingTo = null;
				Global.talking = 0;
				currentAnimation = OverworldAnimation.rageIdle3;
				showingMoney = false;
				Global.disableMovement = false;
			}
		}
	}
	
	private void noOption()
	{
		if(Global.talkingTo == this && Global.talking == 4)
		{
			Global.talkingTo = this;
			Global.talking = 5;
			currentAnimation = OverworldAnimation.rageTalk1;
			showingMoney = true;
			SpeechBubble.talk("No? Oh well.../If you dudes ever change your mind, I'll still be here.");
		}
		
		if(Global.talkingTo == this && Global.talking == 6)
		{
			Global.talkingTo = null;
			Global.talking = 0;
			currentAnimation = OverworldAnimation.rageIdle2;
			showingMoney = false;
			Global.disableMovement = false;
		}
	}
	
	private void yesOptionFireball()
	{

		if(Global.money >= 200 || boughtSpell)
		{

			if(Global.talkingTo == this && Global.talking == 4)
			{
				Global.talkingTo = this;
				Global.talking = 5;
				currentAnimation = OverworldAnimation.rageTalk;
				showingMoney = true;
				SpeechBubble.talk("Great! You're not gonna regret this, I promise!");
				Global.money -= 200;
				boughtSpell = true;
			}
			if(Global.talkingTo == this && Global.talking == 6)
			{
				SpeechBubble.talk("");
				skrappsFlipped = true;
				skrappsX = player.x;
				skrapps = BattleAnimation.skrappsRunStick;
				currentAnimation = OverworldAnimation.rageIdle2;
				Global.talking = 7;
			}
			if(Global.talking >= 7 && Global.talking <= 11 && skrappsX < x + 350)
			{
				skrappsX += 6;
				Global.talking = 7;
			}
			if(Global.talking == 7 && Global.talking <= 11 && skrappsX >= x + 350)
			{
				Global.talking = 8;
			}
			if(Global.talkingTo == this && Global.talking == 8)
			{
				skrappsFlipped = false;
				skrapps = BattleAnimation.skrappsIdleBattle;
				this.flipped = true;
				currentAnimation = OverworldAnimation.rageTalk;
				Global.talking = 9;
				SpeechBubble.talk("Ready, Skrappy dude? It's time for you to learn the \"Fireball\" spell!");
			}
			if(Global.talkingTo == this && Global.talking == 10)
			{
				SpeechBubble.talk("");
				currentAnimation = OverworldAnimation.rageIdle3;
				Fade.run(window, ModeType.Overworld, false, 5);
				Global.talking = 11;
			}
			
			if(Global.talking >= 11 && Global.talking <= 14 && !Fade.fadeIn && !fadedIn)
			{
				Global.talking = 11;
				if(Fade.fadeNum >= 230)
				{
					fadedIn = true;
				}
			}
			if(fadedIn && Global.talking == 11)
			{
				if(Fade.fadeNum < 5)
				{
					Global.talking = 12;
				}
				else
				{
					Global.talking = 11;
				}
			}
			if(Global.talkingTo == this && Global.talking == 12)
			{
				skrappsFlipped = false;
				skrapps = BattleAnimation.skrappsIdleBattle;
				currentAnimation = OverworldAnimation.rageTalk;
				Global.talking = 13;
				SpeechBubble.talk("Blue dude, you have now mastered \"Fireball!\"/You can now use it in battle!/Good luck on your journey, dudes!");
			}
			if(Global.talking >= 14 && skrappsX > player.x)
			{
				skrapps = BattleAnimation.skrappsRun;
				skrappsX -= 6;
				Global.talking = 14;
			}
			if(Global.talking == 14 && skrappsX <= player.x)
			{
				Quest.quests[Quest.HAS_FIREBALL] = 1;
				skrapps = null;
				Global.talkingTo = null;
				Global.talking = 0;
				currentAnimation = OverworldAnimation.rageIdle3;
				showingMoney = false;
				Global.disableMovement = false;
			}
			System.out.println(Global.talking);
		}
		else
		{
			if(Global.talkingTo == this && Global.talking == 4)
			{
				Global.talkingTo = this;
				Global.talking = 5;
				currentAnimation = OverworldAnimation.rageTalk1;
				showingMoney = true;
				SpeechBubble.talk("Sorry dudes, but I don't think you have the money to buy the spell./Don't worry, though. Come back with enough dough, and I'll/be happy to teach you the spell!");
			}
			
			if(Global.talkingTo == this && Global.talking == 6)
			{
				Global.talkingTo = null;
				Global.talking = 0;
				currentAnimation = OverworldAnimation.rageIdle1;
				showingMoney = false;
				Global.disableMovement = false;
			}
		}
	}
}
