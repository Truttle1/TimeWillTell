package net.truttle1.time.overworld.doors;

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
import net.truttle1.time.overworld.npc.NPC;

public class Door extends NPC{

	private int inputDelay = -1;
	private BufferedImage[] cKey; 
	private GameObject player;
	private int doorId;
	private boolean locked;
	private boolean mainDoor;
	private BufferedImage[] door2;
	private int opening = 0;
	public int x2;
	public int y2;
	public Door(Game window, int x, int y, int x2, int y2, OverworldMode om, boolean locked, int id) {
		super(window,x,y, om);
		this.id = ObjectId.NPC;
		this.doorId = id;
		this.locked = locked;
		this.x2 = x2;
		this.y2 = y2;
		currentAnimation = OverworldAnimation.doorClosed;
		door2 = OverworldAnimation.doorClosed;
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
		if(getFrame(0)>getAnimationLength(currentAnimation))
		{
			setFrame(0,0);
		}
		if(getFrame(2)>getAnimationLength(door2))
		{
			setFrame(2,0);
		}
		try
		{
			this.animate(x, y-10, currentAnimation, 0, g);
			this.animate(x2, y2-10, door2, 2, g);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			setFrame(0,0);
		}

		if(distanceToPoint(ObjectId.Player,x+50,y)<200 && Global.talking == 0 && !Global.disableMovement)
		{
			this.animate(x+150, y, BattleAnimation.cIcon, 1, g);
		}
		if(distanceToPoint(ObjectId.Player,x2+50,y2)<200 && Global.talking == 0 && !Global.disableMovement)
		{
			this.animate(x2+150, y2, BattleAnimation.cIcon, 1, g);
		}
	}

	@Override
	public void tick()
	{

		//System.out.println(Global.talking);
		if(!Global.unlock[doorId] && locked)
		{
			currentAnimation = OverworldAnimation.doorLocked;
		}
		else
		{
			switch(opening)
			{
				case 0:
					currentAnimation = OverworldAnimation.doorClosed;
					door2 = OverworldAnimation.doorClosed;
					break;
				case 1:
					if(mainDoor)
					{
						enterMainDoor();
						door2 = OverworldAnimation.doorClosed;
					}
					else
					{
						enterSecondDoor();
					}
					break;
				case 2:
					if(mainDoor)
					{
						exitSecondDoor();
						currentAnimation = OverworldAnimation.doorClosed;
					}
					else
					{
						exitMainDoor();
						door2 = OverworldAnimation.doorClosed;
						
					}
					break;
				default:
					break;
			}
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
		if(!locked || Global.unlock[doorId])
		{
			if(Global.cPressed && opening == 0 && distanceToPoint(ObjectId.Player,x+50,y)<200)
			{
				opening = 1;
				mainDoor = true;
			}
			if(Global.cPressed && opening == 0 && distanceToPoint(ObjectId.Player,x2+50,y2)<200)
			{
				opening = 1;
				mainDoor = false;
			}
		}
		if(locked && !Global.unlock[doorId])
		{
			if(Global.keyItems[Store.KEY] <= 0)
			{
				if(Global.cPressed && Global.talking == 0 && distanceToPoint(ObjectId.Player,x+50,y)<200)
				{
					Global.talkingTo = this;
					Global.talking = 1;
					Global.disableMovement = true;
					SpeechBubble.talk("This door is locked.", Global.textFont);
				}
				if(Global.talking == 2 && Global.talkingTo == this)
				{
					Global.zPressed = false;
					Global.talking = 0;
					Global.disableMovement = false;
				}
			}
			if(Global.keyItems[Store.KEY] >= 1)
			{
				if(Global.cPressed && Global.talking == 0 && distanceToPoint(ObjectId.Player,x+50,y)<200)
				{
					Global.talkingTo = this;
					Global.talking = 1;
					Global.disableMovement = true;
					SpeechBubble.yesNo("This door is locked, but you have a key for it!/Would you like to use that key now?", Global.textFont);
					om.stopPlayerMoving(player);
				}
				if(Global.talking == 2 && Global.talkingTo == this)
				{
					Global.talking = 3;
					if(SpeechBubble.selection == 0)
					{
						Global.keyItems[Store.KEY]--;
						Global.unlock[doorId] = true;
						SpeechBubble.talk("You used the key and unlocked the door!");
					}
					else if(SpeechBubble.selection == 1)
					{
						SpeechBubble.talk("Maybe this key belongs somewhere else...");
					}
				}
			}

		}

		if(Global.talking >= 4 && Global.talkingTo == this)
		{
			Global.zPressed = false;
			Global.cPressed = false;
			Global.talking = 0;
			Global.disableMovement = false;

			if(SpeechBubble.selection == 0)
			{
			}
		}
	}
	
	private void enterMainDoor()
	{

		Global.talkingTo = this;
		if(getFrame(0)<=2)
		{
			player.hVelocity = 0;
		}
		Global.disableMovement = true;
		currentAnimation = OverworldAnimation.doorOpen;
		if(getFrame(0)<6)
		{
			if(player.x<this.x+50)
			{
				player.x+=10;
				if(player instanceof SimonPlayer)
				{
					player.flipped = false;
					player.currentAnimation = BattleAnimation.simonWalkAnimation;
				}
				else if(player instanceof WilliamPlayer)
				{
					player.flipped = true;
					player.currentAnimation = BattleAnimation.williamWalkAnimation;
				}
			}
			if(player.x>this.x+150)
			{
				player.x-=10;
				if(player instanceof SimonPlayer)
				{
					player.flipped = true;
					player.currentAnimation = BattleAnimation.simonWalkAnimation;
				}
				else if(player instanceof WilliamPlayer)
				{
					player.flipped = false;
					player.currentAnimation = BattleAnimation.williamWalkAnimation;
				}
			}
		}
		if(getFrame(0)>15)
		{
			om.objects.remove(player);
		}
		if(getFrame(0)>=23)
		{
			currentAnimation = OverworldAnimation.doorClosed;
			opening++;
		}
	}

	private void enterSecondDoor()
	{
		Global.talkingTo = this;
		Global.disableMovement = true;
		door2 = OverworldAnimation.doorOpen;
		if(getFrame(2)<=2)
		{
			player.hVelocity = 0;
		}
		if(getFrame(2)<6)
		{
			if(player.x<this.x2+50)
			{
				player.x+=10;
				if(player instanceof SimonPlayer)
				{
					player.flipped = false;
					player.currentAnimation = BattleAnimation.simonWalkAnimation;
				}
				else if(player instanceof WilliamPlayer)
				{
					player.flipped = true;
					player.currentAnimation = BattleAnimation.williamWalkAnimation;
				}
			}
			if(player.x>this.x2+150)
			{
				player.x-=10;
				if(player instanceof SimonPlayer)
				{
					player.flipped = true;
					player.currentAnimation = BattleAnimation.simonWalkAnimation;
				}
				else if(player instanceof WilliamPlayer)
				{
					player.flipped = false;
					player.currentAnimation = BattleAnimation.williamWalkAnimation;
				}
			}
		}
		if(getFrame(2)>15)
		{
			om.objects.remove(player);
		}
		if(getFrame(2)>=23)
		{
			door2 = OverworldAnimation.doorClosed;
			opening++;
		}
	}


	private void exitMainDoor()
	{
		Global.disableMovement = true;
		currentAnimation = OverworldAnimation.doorOpen;
		//FOCUS ON DOOR
		if(x>400 && x<Global.currentRoom.width-620)
		{
			window.overworldMode.tx = x-400;
		}
		if(x<400)
		{
			window.overworldMode.tx = 0;
		}
		if(x>Global.currentRoom.width-620)
		{
			window.overworldMode.tx = Global.currentRoom.width-1024;
		}

		if(y<=Global.currentRoom.height-200)
		{
			window.overworldMode.ty = y-200;
		}
		if(window.overworldMode.ty+600>Global.currentRoom.height)
		{
			window.overworldMode.ty = Global.currentRoom.height-600;
		}

		if(getFrame(0)>15 && !om.objects.contains(player))
		{
			if(Global.playingAs == 0)
			{
				player = new SimonPlayer(window, x+100, y+100);
			}
			if(Global.playingAs == 1)
			{
				player = new WilliamPlayer(window, x+100, y+200,om);
			}
			om.objects.add(player);
		}
		if(getFrame(0)>=23)
		{
			currentAnimation = OverworldAnimation.doorClosed;
			opening = 0;
			Global.disableMovement = false;
			Global.talkingTo = null;
		}
	}

	private void exitSecondDoor()
	{
		Global.disableMovement = true;
		door2 = OverworldAnimation.doorOpen;
		//FOCUS ON DOOR
		if(x2>400 && x2<Global.currentRoom.width-620)
		{
			window.overworldMode.tx = x2-400;
		}
		if(x2<400)
		{
			window.overworldMode.tx = 0;
		}
		if(x2>Global.currentRoom.width-620)
		{
			window.overworldMode.tx = Global.currentRoom.width-1024;
		}

		if(y2<=Global.currentRoom.height-200)
		{
			window.overworldMode.ty = y2-200;
		}
		if(window.overworldMode.ty+600>Global.currentRoom.height)
		{
			window.overworldMode.ty = Global.currentRoom.height-600;
		}

		if(getFrame(2)>15 && !om.objects.contains(player))
		{
			if(Global.playingAs == 0)
			{
				player = new SimonPlayer(window, x2+100, y2+100);
			}
			if(Global.playingAs == 1)
			{
				player = new WilliamPlayer(window, x2+100, y2+200,om);
			}
			om.objects.add(player);
		}
		if(getFrame(2)>=23)
		{
			door2 = OverworldAnimation.doorClosed;
			opening = 0;
			Global.disableMovement = false;
			Global.talkingTo = null;
		}
	}

}
