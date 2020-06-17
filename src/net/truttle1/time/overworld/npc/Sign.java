package net.truttle1.time.overworld.npc;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.WorldId;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;

public class Sign extends NPC{

	private int inputDelay = -1;
	private BufferedImage[] cKey; 
	private String str;
	private GameObject player;
	public Sign(Game window, int x, int y, OverworldMode om, String string) {
		super(window,x,y, om);
		currentAnimation = OverworldAnimation.sign;
		this.id = ObjectId.NPC;
		this.str = string;
	}

	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+60,y+150,100,50);
	}
	public Rectangle topRectangle()
	{
		return new Rectangle(x+60,y+4,60,50);
	}
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+120,y+4,50,120);
	}
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+45,y+4,50,120);
	}
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+55,y+4,99,195);
	}
	@Override
	public void render(Graphics g) {
		if(distanceTo(ObjectId.Player)<200 && Global.talking == 0 && !Global.disableMovement)
		{
			this.animate(x+50, y-100, BattleAnimation.cIcon, 1, g);
		}
		if(getFrame(0)>getAnimationLength(currentAnimation))
		{
			setFrame(0,0);
		}
		try
		{

			if(window.currentWorld == WorldId.Digital)
			{
				this.animate(x, y-16, currentAnimation, 0, g);
			}
			else
			{

				this.animate(x, y, currentAnimation, 0, g);
			}
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
		
		if(player == null)
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i).id == ObjectId.Player)
				{
					player = om.objects.get(i);
				}
			}
		}
		if(Global.disableMovement && Global.talkingTo == this)
		{
			player.hVelocity = 0;
			if(player instanceof SimonPlayer)
			{
				player.hVelocity = 0;
				player.currentAnimation = BattleAnimation.simonIdleAnimation;
			}
			if(player instanceof WilliamPlayer)
			{
				if(Global.playerSad)
				{
					player.currentAnimation = BattleAnimation.williamIdle3;
				}
				else
				{
					player.currentAnimation = BattleAnimation.williamIdle2;
				}
				player.hVelocity = 0;
			}
		}
		if(Global.cPressed && Global.talking == 0 && distanceTo(ObjectId.Player)<200)
		{
			Global.talkingTo = this;
			Global.talking = 1;
			Global.disableMovement = true;
			SpeechBubble.talk(str);
			om.stopPlayerMoving(player);
		}
		if(Global.talking == 2 && Global.talkingTo == this)
		{
			Global.talking = 0;
			Global.disableMovement = false;
			Global.zPressed = false;
			Global.talkingTo = null;
		}
	}

}
