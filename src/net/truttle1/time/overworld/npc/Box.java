package net.truttle1.time.overworld.npc;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;

public class Box extends NPC{

	private int inputDelay = -1;
	private BufferedImage[] cKey; 
	private String str;
	private GameObject player;
	private int item;
	private int boxId;
	private boolean key;
	public Box(Game window, int x, int y, OverworldMode om, boolean key, int item, int id) {
		super(window,x,y, om);
		currentAnimation = OverworldAnimation.sign;
		this.id = ObjectId.NPC;
		this.boxId = id;
		this.item = item;
		this.key = key;
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
		if(!Global.chestOpened[boxId] && distanceTo(ObjectId.Player)<200 && Global.talking == 0 && !Global.disableMovement)
		{
			this.animate(x+50, y-100, BattleAnimation.cIcon, 1, g);
		}
		if(getFrame(0)>getAnimationLength(currentAnimation))
		{
			setFrame(0,0);
		}
		try
		{
			this.animate(x, y-10, currentAnimation, 0, g);
			if(Global.talkingTo == this && Global.talking == 1)
			{
				if(key)
				{

					switch(Global.playingAs)
					{
					case 0:
						g.drawImage(Store.keyItemImage[item],player.x+64,player.y-32,null);
						break;
					case 1:
						g.drawImage(Store.keyItemImage[item],player.x+64,player.y-42,null);
						break;
					default:
						g.drawImage(Store.keyItemImage[item],player.x+64,player.y-32,null);
						break;
					}
				}
				else
				{
					switch(Global.playingAs)
					{
					case 0:
						g.drawImage(Store.itemImage[item],player.x+64,player.y-32,null);
						break;
					case 1:
						g.drawImage(Store.itemImage[item],player.x+64,player.y-42,null);
						break;
					default:
						g.drawImage(Store.itemImage[item],player.x+64,player.y-32,null);
						break;
					}
				}
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
		if(Global.chestOpened[boxId])
		{
			currentAnimation = OverworldAnimation.boxOpen;
		}
		else
		{
			currentAnimation = OverworldAnimation.boxClosed;
		}
		if(player == null || !om.objects.contains(player))
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i).id == ObjectId.Player)
				{
					player = om.objects.get(i);
				}
			}
		}
		if(!Global.chestOpened[boxId] && Global.cPressed && Global.talking == 0 && distanceTo(ObjectId.Player)<200)
		{
			Global.talkingTo = this;
			Global.talking = 1;
			Global.disableMovement = true;
			if(key)
			{
				Global.keyItems[item]++;
				SpeechBubble.talk("You obtained " + Store.keyItemNames[item] + " and put it in the Key Items pocket!");
			}
			else
			{
				Global.items[item]++;
				SpeechBubble.talk("You obtained " + Store.itemNames[item] + "!");
			}
			Global.chestOpened[boxId] = true;
			switch(Global.playingAs)
			{
			case 0:
				player.currentAnimation = BattleAnimation.simonItem;
				break;
			case 1:
				player.currentAnimation = BattleAnimation.williamItem;
				break;
			default:
				player.currentAnimation = BattleAnimation.williamItem;
				break;
			}
		}
		if(Global.talking == 2 && Global.talkingTo == this)
		{
			Global.zPressed = false;
			Global.talking = 0;
			Global.disableMovement = false;
			Global.talkingTo = null;
			switch(Global.playingAs)
			{
			case 0:
				player.currentAnimation = BattleAnimation.simonItem;
				break;
			case 1:
				player.currentAnimation = BattleAnimation.williamItem;
				break;
			default:
				player.currentAnimation = BattleAnimation.williamItem;
				break;
			}
		}
		if(Global.talkingTo == this && Global.talking == 1)
		{

			switch(Global.playingAs)
			{
			case 0:
				player.currentAnimation = BattleAnimation.simonItem;
				break;
			case 1:
				player.currentAnimation = BattleAnimation.williamItem;
				break;
			default:
				player.currentAnimation = BattleAnimation.williamItem;
				break;
			}
		}
	}

}
