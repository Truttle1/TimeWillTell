package net.truttle1.time.overworld.npc;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.monsters.Ursear;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.RoomId;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.blocks.Grass;

public class Suzy2 extends NPC{

	BufferedImage[] ursearAnimation;
	SimonPlayer simon;
	boolean ursearVisible = false;
	int ursearX = 0;
	boolean normalTalk = false;
	int ursearTime = -1;
	public Suzy2(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.suzyIdle;
		this.flipped = true;
		this.id = ObjectId.NPC;
		if(Quest.quests[Quest.TUTORIAL]>12 && om.currentRoom != RoomId.SimonHouse)
		{
			this.y = 99999;
		}
	}


	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+30,y+100,100,50);
	}
	public Rectangle topRectangle()
	{
		return new Rectangle(x+30,y+34,60,50);
	}
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+90,y+34,50,60);
	}
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+28,y+34,50,60);
	}
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+28,y+27,91,113);
	}

	@Override
	public void render(Graphics g) {
		if(distanceTo(ObjectId.Player)<150 && Global.talking == 0 && !Global.disableMovement)
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
		animate(x,y,currentAnimation,0,g);
		if(ursearVisible)
		{
			animate(ursearX,y-60,ursearAnimation,1,g);
		}
		
	}
	@Override
	public int distanceTo(ObjectId id)
	{
		for(int i=0; i<om.objects.size();i++)
		{
			if(om.objects.get(i).id == id)
			{
				GameObject temp = om.objects.get(i);
				int distX = (int) Math.pow(Math.abs((this.x-50)-temp.x),2);
				int distY = (int) Math.pow(Math.abs(this.y-temp.y),2);
				return (int) Math.sqrt(distX+distY);
			}
		}
		return 9999;
	}
	@Override
	public void tick() {
	continuePath();

	if(Quest.quests[Quest.TUTORIAL]>12 && om.currentRoom != RoomId.SimonHouse)
	{
		om.objects.remove(this);
	}
	if(Global.talkingTo == this && Global.talking != 0)
	{
		currentAnimation = OverworldAnimation.suzyTalk;
	}
	else if(hVelocity == 0 && Global.talking == 0)
	{
		currentAnimation = OverworldAnimation.suzyIdle;
	}
	if(Global.cPressed && Global.talking == 0 && distanceTo(ObjectId.Player)<150)
	{
		if(Quest.quests[Quest.TUTORIAL]==7)
		{
			Global.talkingTo = this;
			Global.talking = 1;
			normalTalk = true;
			SpeechBubble.talk("There were a lot of Ursears back there! You know what,/I'll heal you back up to full health!");
			Global.simonHP = Global.simonHPMax;
		}
		else if(Global.hasSimon && Global.playingAs == 0)
		{
			Global.talkingTo = this;
			Global.talking = 1;
			normalTalk = true;
			SpeechBubble.talk("Hello, Father! You know what, I'll heal you back up to full health!");
			Global.simonHP = Global.simonHPMax;
			Global.simonSP = Global.simonSPMax;
			Global.williamHP = Global.williamHPMax;
			Global.williamSP = Global.williamSPMax;
			for(int i=0; i<99;i++)
			{
				Global.partnerHP[i] = Global.partnerHPMax[i];
				Global.partnerSP[i] = Global.partnerSPMax[i];
			}
		}
		else if(Global.playingAs == 1 && Quest.quests[Quest.LOMO]<Global.LOMOCONSTANT)
		{
			Global.talkingTo = this;
			Global.talking = 1;
			normalTalk = true;
			SpeechBubble.talk("Get out of my house you dumb lizard! You're SUUUPER/mean to me for no good reason, and because of that, I HATE YOU!/Now get out of my house!");
		}
		else if(Global.playingAs == 1)
		{
			Global.talkingTo = this;
			Global.talking = 1;
			normalTalk = true;
			SpeechBubble.talk("Oh, so you're friends with my Dad now, right? Well then,/I guess I can heal you up to full health! Good luck, and/tell my Dad that I love him!");
			Global.simonHP = Global.simonHPMax;
			Global.simonSP = Global.simonSPMax;
			Global.williamHP = Global.williamHPMax;
			Global.williamSP = Global.williamSPMax;
			for(int i=0; i<99;i++)
			{
				Global.partnerHP[i] = Global.partnerHPMax[i];
				Global.partnerSP[i] = Global.partnerSPMax[i];
			}
		}
	}

	if(Global.talking >= 2 && Global.talkingTo == this)
	{
		normalTalk = false;
		Global.talking = 0;
		Global.disableMovement = false;
	}
	if(Quest.quests[Quest.TUTORIAL] >= 7 && Global.talking >= 2 && Global.talkingTo == this && normalTalk)
	{
		normalTalk = false;
		Global.talking = 0;
		Global.disableMovement = false;
		Global.zPressed = false;
	}
	if(hPath[pathStage] != 99)
	{
		hVelocity = hPath[pathStage];
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
				this.y = g.y-130;
			}
		}
	}
	x+=hVelocity;
	y+=vVelocity;
	vVelocity+=2;
	}
}
