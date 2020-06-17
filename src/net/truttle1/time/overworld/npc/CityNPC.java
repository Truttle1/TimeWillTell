package net.truttle1.time.overworld.npc;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;

public class CityNPC extends NPC{

	private String txt;
	private String txt2;
	private BufferedImage[] talk;
	private BufferedImage[] idle;
	private BufferedImage[] walk;
	public int cDistance;
	public CityNPC(Game window, int x, int y, OverworldMode om, int type, String txt) {
		super(window, x, y, om);
		cDistance = 150;
		this.txt = txt;
		this.txt2 = txt;
		this.id = ObjectId.NPC;
		getSprites(type);
		currentAnimation = idle;
	}
	public CityNPC(Game window, int x, int y, OverworldMode om, int type, String txt, String txt2) {
		super(window, x, y, om);
		cDistance = 150;
		this.txt = txt;
		this.txt2 = txt2;
		this.id = ObjectId.NPC;
		getSprites(type);
		currentAnimation = idle;
	}
	
	private void getSprites(int type)
	{
		switch(type)
		{
			case 0:
				this.idle = OverworldAnimation.cityNPC0Idle;
				this.talk = OverworldAnimation.cityNPC0Talk;
				break;
			case 1:
				this.idle = OverworldAnimation.cityNPC1Idle;
				this.talk = OverworldAnimation.cityNPC1Talk;
				break;
			case 2:
				this.idle = OverworldAnimation.cityNPC2Idle;
				this.talk = OverworldAnimation.cityNPC2Talk;
				break;
			case 3:
				this.idle = OverworldAnimation.cityNPC3Idle;
				this.talk = OverworldAnimation.cityNPC3Talk;
				break;
			case 4:
				this.idle = OverworldAnimation.cityNPC4Idle;
				this.talk = OverworldAnimation.cityNPC4Talk;
				break;
			case 5:
				this.idle = OverworldAnimation.cityNPC5Idle;
				this.talk = OverworldAnimation.cityNPC5Talk;
				break;
			default:
				this.idle = OverworldAnimation.cityNPC0Idle;
				this.talk = OverworldAnimation.cityNPC0Talk;
				break;
		}
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
	@Override
	public void tick()
	{
		//System.out.println(x + " :: " + y);
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
			currentAnimation = talk;
			if(getPlayer() instanceof WilliamPlayer)
			{
				SpeechBubble.talk(txt2);
			}
			else if(getPlayer() instanceof SimonPlayer)
			{
				SpeechBubble.talk(txt);
			}
			Global.disableMovement = true;
			om.stopPlayerMoving(getPlayer());
		}
		if(Global.talkingTo == this && Global.talking == 2)
		{
			Global.talking = 0;
			Global.talkingTo = null;
			Global.disableMovement = false;
			currentAnimation = idle;
		}
	}

}
