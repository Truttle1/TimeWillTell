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

public class CavemanNPC extends NPC{

	private String txt;
	private BufferedImage[] talk;
	private BufferedImage[] idle;
	private BufferedImage[] walk;
	public int cDistance;
	public CavemanNPC(Game window, int x, int y, OverworldMode om, int type, String txt, boolean generatePath) {
		super(window, x, y, om);
		cDistance = 150;
		this.txt = txt;
		this.id = ObjectId.NPC;
		switch(type)
		{
			case 0:
				this.walk = OverworldAnimation.rCavemanWalk;
				this.talk = OverworldAnimation.rCavemanTalk;
				this.idle = OverworldAnimation.rCavemanIdle;
				break;
			case 1:
				this.walk = OverworldAnimation.rGirlCavemanWalk;
				this.talk = OverworldAnimation.rGirlCavemanTalk;
				this.idle = OverworldAnimation.rGirlCavemanIdle;
				break;
			case 2:
				this.walk = OverworldAnimation.bCavemanWalk;
				this.talk = OverworldAnimation.bCavemanTalk;
				this.idle = OverworldAnimation.bCavemanIdle;
				break;
			default:
				this.walk = OverworldAnimation.bGirlCavemanWalk;
				this.talk = OverworldAnimation.bGirlCavemanTalk;
				this.idle = OverworldAnimation.bGirlCavemanIdle;
				break;
		}
		currentAnimation = idle;
	}
	public CavemanNPC(Game window, int x, int y, OverworldMode om, int type, String txt, int cDistance) {
		super(window, x, y, om);
		this.cDistance = cDistance;
		this.txt = txt;
		this.id = ObjectId.NPC;
		switch(type)
		{
			case 0:
				this.walk = OverworldAnimation.rCavemanWalk;
				this.talk = OverworldAnimation.rCavemanTalk;
				this.idle = OverworldAnimation.rCavemanIdle;
				break;
			case 1:
				this.walk = OverworldAnimation.rGirlCavemanWalk;
				this.talk = OverworldAnimation.rGirlCavemanTalk;
				this.idle = OverworldAnimation.rGirlCavemanIdle;
				break;
			case 2:
				this.walk = OverworldAnimation.bCavemanWalk;
				this.talk = OverworldAnimation.bCavemanTalk;
				this.idle = OverworldAnimation.bCavemanIdle;
				break;
			default:
				this.walk = OverworldAnimation.bGirlCavemanWalk;
				this.talk = OverworldAnimation.bGirlCavemanTalk;
				this.idle = OverworldAnimation.bGirlCavemanIdle;
				break;
		}
		currentAnimation = idle;
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
		if(getPlayer().x >= this.x+40)
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
			SpeechBubble.talk(txt);
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
