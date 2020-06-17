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

public class Rage1 extends NPC{

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
	public Rage1(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.rageIdle4;
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
				this.animateNoFlip(x+50, y-100, BattleAnimation.cIcon, 3, g);
			}
			else
			{
				this.animate(x+50, y-100, BattleAnimation.cIcon, 3, g);
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
		if(player.x >= this.x+100)
		{
			this.flipped = true;
		}
		else
		{
			this.flipped = false;
		}
		if(Global.cPressed && Global.talking == 0 && !Global.disableMovement && distanceTo(ObjectId.Player)<175)
		{
			Global.talkingTo = this;
			Global.talking = 1;
			currentAnimation = OverworldAnimation.rageTalk1;
			SpeechBubble.talk("Sorry dudes, I don't have anything new for you right now./Maybe you could come back later.");
		}
		if(Global.talkingTo == this && Global.talking == 2)
		{
			Global.disableMovement = false;
			Global.talking = 0;
			Global.talkingTo = null;
			currentAnimation = OverworldAnimation.rageIdle4;
		}
	}
}
