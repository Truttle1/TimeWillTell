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
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.npc.cutscene.VolcanoExplodeCutscene;

public class SaveSign extends NPC{

	private int inputDelay = -1;
	private BufferedImage[] cKey; 
	private GameObject player;
	public SaveSign(Game window, int x, int y, OverworldMode om) {
		super(window,x,y, om);
		currentAnimation = OverworldAnimation.saveSign;
		this.id = ObjectId.NPC;
		if(Quest.quests[Quest.ESCAPED] == 1)
		{
			om.objects.add(new VolcanoExplodeCutscene(window, 0, 0, om));
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
			this.animate(x, y-18, currentAnimation, 0, g);
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
			player.hVelocity = 0;
			if(player instanceof SimonPlayer)
			{
				player.hVelocity = 0;
				player.currentAnimation = BattleAnimation.simonIdleAnimation;
			}
			if(player instanceof WilliamPlayer)
			{
				player.currentAnimation = BattleAnimation.williamIdle2;
				player.hVelocity = 0;
			}
			SpeechBubble.yesNo("This is a Save Sign. It allows you to save the game! Don't ask how/it works... Would you like to save the game now?",Global.textFont);
		}
		if(Global.talking == 2 && Global.talkingTo == this)
		{
			if(SpeechBubble.selection == 0)
			{
				window.save();
				player.startX = player.x;
				player.startY = player.y;
				SpeechBubble.talk("The game has been saved! Have a nice day!");
			}
			else
			{
				SpeechBubble.talk("Then why did you even bother coming here? I mean you can READ,/can't you!?");
			}
			Global.talking = 3;
		}
		if(Global.talking >= 4 && Global.talkingTo == this)
		{
			Global.zPressed = false;
			Global.talking = 0;
			Global.disableMovement = false;
			Global.talkingTo = null;
		}
	}

}
