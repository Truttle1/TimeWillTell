package net.truttle1.time.overworld.doors;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.npc.NPC;

public class PadlockDoor extends NPC{

	private GameObject player;
	private int padlockStage = 0;
	private BufferedImage[] skrapps;
	private int skrappsX;
	private boolean skrappsFlipped;
	private boolean doorUnlocking;
	private int x2;
	private int y2;
	private BufferedImage[] door2;
	private int opening = 0;
	private boolean mainDoor;
	public PadlockDoor(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		this.currentAnimation = OverworldAnimation.padlockDoorClosed;
		this.door2 = OverworldAnimation.padlockDoorNormal;
		this.id = ObjectId.NPC;
		x2 = 10200;
		y2 = 2000;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rectangle topRectangle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Rectangle leftRectangle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Rectangle rightRectangle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Rectangle bottomRectangle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(Graphics g) {
		if(Quest.quests[Quest.PADLOCK_GREEN] != 1 || Quest.quests[Quest.PADLOCK_PURPLE] != 1 || Quest.quests[Quest.PADLOCK_ORANGE] != 1)
		{
			currentAnimation = OverworldAnimation.padlockDoorClosed;
			if(Quest.quests[Quest.PADLOCK_GREEN] == 0 && Quest.quests[Quest.PADLOCK_ORANGE] == 0 && Quest.quests[Quest.PADLOCK_PURPLE] == 0)
			{
				setFrame(0,0);
			}
			if(Quest.quests[Quest.PADLOCK_GREEN] == 0 && Quest.quests[Quest.PADLOCK_ORANGE] == 1 && Quest.quests[Quest.PADLOCK_PURPLE] == 0)
			{
				setFrame(0,1);
			}
			if(Quest.quests[Quest.PADLOCK_GREEN] == 1 && Quest.quests[Quest.PADLOCK_ORANGE] == 0 && Quest.quests[Quest.PADLOCK_PURPLE] == 0)
			{
				setFrame(0,2);
			}
			if(Quest.quests[Quest.PADLOCK_GREEN] == 0 && Quest.quests[Quest.PADLOCK_ORANGE] == 0 && Quest.quests[Quest.PADLOCK_PURPLE] == 1)
			{
				setFrame(0,3);
			}
			if(Quest.quests[Quest.PADLOCK_GREEN] == 1 && Quest.quests[Quest.PADLOCK_ORANGE] == 0 && Quest.quests[Quest.PADLOCK_PURPLE] == 1)
			{
				setFrame(0,4);
			}
			if(Quest.quests[Quest.PADLOCK_GREEN] == 0 && Quest.quests[Quest.PADLOCK_ORANGE] == 1 && Quest.quests[Quest.PADLOCK_PURPLE] == 1)
			{
				setFrame(0,5);
			}
			if(Quest.quests[Quest.PADLOCK_GREEN] == 1 && Quest.quests[Quest.PADLOCK_ORANGE] == 1 && Quest.quests[Quest.PADLOCK_PURPLE] == 0)
			{
				setFrame(0,6);
			}
			if(Quest.quests[Quest.PADLOCK_GREEN] == 1 && Quest.quests[Quest.PADLOCK_ORANGE] == 1 && Quest.quests[Quest.PADLOCK_PURPLE] == 1)
			{
				setFrame(0,7);
			}
		}
		else if(opening == 0)
		{
			currentAnimation = OverworldAnimation.padlockDoorNormal;
			setFrame(0,0);
		}
		try
		{
			this.animate(x, y-16, currentAnimation, 0, g);
			this.animate(x2, y2-16, door2, 2, g);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			this.setFrame(0, 0);
			this.setFrame(2, 0);
			this.animate(x, y-16, currentAnimation, 0, g);
			this.animate(x2, y2-16, door2, 2, g);
		}
		if(skrapps != null)
		{
			if(getFrame(1)>getAnimationLength(skrapps))
			{
				setFrame(1,0);
			}
			if(skrappsFlipped)
			{
				this.animateFlip(skrappsX, y+250, skrapps, 1, g);
			}
			else
			{
				this.animateNoFlip(skrappsX, y+250, skrapps, 1, g);
			}
		}

		if(distanceToPoint(ObjectId.Player,x+100,y)<340 && Global.talking == 0 && !Global.disableMovement)
		{
			this.animate(x+150, y+150, BattleAnimation.cIcon, 1, g);
		}
		if(distanceToPoint(ObjectId.Player,x2+100,y2)<340 && Global.talking == 0 && !Global.disableMovement)
		{
			this.animate(x2+150, y2+150, BattleAnimation.cIcon, 1, g);
		}
	}
	@Override
	public void tick()
	{
		for(int i=0; i<om.objects.size();i++)
		{
			if(om.objects.get(i).id == ObjectId.Player)
			{
				player = om.objects.get(i);
			}
		}
		if(player.x>=this.x+100 && player.y <= this.y+400 && Quest.quests[Quest.PYRUZ_W] == 7)
		{
			doorCutscene();
		}
		else if(Quest.quests[Quest.PADLOCK_PURPLE] == 0 || Quest.quests[Quest.PADLOCK_GREEN] == 0 || Quest.quests[Quest.PADLOCK_ORANGE] == 0)
		{

			if(Global.cPressed && distanceToPoint(ObjectId.Player,x+100,y)<340)
			{
				doorUnlocking = true;
			}
		}
		if(doorUnlocking && (Global.talkingTo == null || Global.talkingTo == this))
		{
			unlockDoor();
		}
		else if(Quest.quests[Quest.PADLOCK_PURPLE] == 1 && Quest.quests[Quest.PADLOCK_GREEN] == 1 && Quest.quests[Quest.PADLOCK_ORANGE] == 1)
		{
			openDoor();
		}
	}
	private void doorCutscene()
	{
		player.hVelocity = 0;
		if(padlockStage == 0)
		{
			player.currentAnimation = BattleAnimation.williamIdle2;
			Global.disableMovement = true;
			if(player.vVelocity == 0)
			{
				skrapps = BattleAnimation.skrappsRun;
				skrappsFlipped = true;
				skrappsX = player.x;
				padlockStage++;
			}
		}
		if(padlockStage == 1)
		{
			Global.disableMovement = true;
			skrappsX += 8;
			if(skrappsX>=player.x+200)
			{
				skrapps = BattleAnimation.skrappsIdle2;
				padlockStage++;
				skrappsFlipped = false;
			}
		}
		if(padlockStage == 2)
		{
			Global.disableMovement = true;
			if(Global.talking == 0)
			{
				Global.talkingTo = this;
				Global.talking = 1;
				skrapps = BattleAnimation.skrappsTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
				SpeechBubble.talk("Hey William, this door looks pretty strange, I wonder what's behind it!",Global.skrappsFont);
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				Global.talking = 3;
				skrapps = BattleAnimation.skrappsIdle2;
				player.currentAnimation = BattleAnimation.williamTalk2;
				SpeechBubble.talk("I dunno, but it looks like we need to find three padlock-shaped objects/and place them into those keys...wow, that sounded weird to say/out loud...",Global.willFont);
			}
			if(Global.talking == 4 && Global.talkingTo == this)
			{
				Global.talking = 5;
				skrapps = BattleAnimation.skrappsTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
				SpeechBubble.talk("Well alright then! Let's get findin'!",Global.skrappsFont);
			}
			if(Global.talking >= 6 && Global.talkingTo == this)
			{
				skrapps = BattleAnimation.skrappsRun;
				skrappsFlipped = false;
				skrappsX -= 8;
				if(skrappsX<=player.x)
				{
					skrapps = null;
					Quest.quests[Quest.PYRUZ_W]++;
					Global.disableMovement = false;
					Global.talkingTo = null;
					Global.talking = 0;
				}
			}
		}
	}

	private void unlockDoor()
	{
		if(Global.talking == 0)
		{
			Global.talking = 1;
			Global.talkingTo = this;
			Global.disableMovement = true;
			SpeechBubble.talk("It's a strange door that looks completely unlike any other door in this/cave! Because it's so different, you may be tempted to go through it!/Well, guess what, you can't...because it's locked.");
		}
		if(Global.talking == 2)
		{
			if(Global.keyItems[Store.PPADLOCK]>=1)
			{
				SpeechBubble.yesNo("Hey, it looks like that Purple Padlock you have fits in this door! Place it?",Global.textFont);
				Global.talking = 3;
			}
			else
			{
				Global.talking = 6;
			}
		}
		if(Global.talking == 4)
		{

			if(SpeechBubble.selection == 0)
			{
				Global.keyItems[Store.PPADLOCK]--;
				Quest.quests[Quest.PADLOCK_PURPLE] = 1;
				SpeechBubble.talk("The purple padlock fit nicely into the door!");
				Global.talking++;
			}
			else if(SpeechBubble.selection == 1)
			{
				Global.talking = 15;
				SpeechBubble.talk("Maybe this door isn't so interesting after all...");
			}
		}
		if(Global.talking == 6)
		{
			if(Global.keyItems[Store.OPADLOCK]>=1)
			{
				SpeechBubble.yesNo("Hey, it looks like that Orange Padlock you have fits in this door! Place it?",Global.textFont);
				Global.talking = 7;
			}
			else
			{
				Global.talking = 10;
			}
		}
		if(Global.talking == 8)
		{

			if(SpeechBubble.selection == 0)
			{
				Global.keyItems[Store.OPADLOCK]--;
				Quest.quests[Quest.PADLOCK_ORANGE] = 1;
				SpeechBubble.talk("The orange padlock fit nicely into the door!");
				Global.talking++;
			}
			else if(SpeechBubble.selection == 1)
			{
				Global.talking = 15;
				SpeechBubble.talk("Maybe this door isn't so interesting after all...");
			}
		}if(Global.talking == 10)
		{
			if(Global.keyItems[Store.GPADLOCK]>=1)
			{
				SpeechBubble.yesNo("Hey, it looks like that Green Padlock you have fits in this door! Place it?",Global.textFont);
				Global.talking = 11;
			}
			else
			{
				Global.talking = 14;
			}
		}
		if(Global.talking == 12)
		{

			if(SpeechBubble.selection == 0)
			{
				Global.keyItems[Store.GPADLOCK]--;
				Quest.quests[Quest.PADLOCK_GREEN] = 1;
				SpeechBubble.talk("The green padlock fit nicely into the door!");
				Global.talking++;
			}
			else if(SpeechBubble.selection == 1)
			{
				Global.talking = 15;
				SpeechBubble.talk("Maybe this door isn't so interesting after all...");
			}
		}
		if(Global.talking == 14)
		{
			if(Quest.quests[Quest.PADLOCK_GREEN] == 1 && Quest.quests[Quest.PADLOCK_ORANGE] == 1 && Quest.quests[Quest.PADLOCK_PURPLE] == 1)
			{
				SpeechBubble.talk("The door has been unlocked!");
				Global.talking++;
			}
			else
			{
				Global.talking = 16;
			}
		}
		if(Global.talking == 16)
		{
			Global.talking = 0;
			Global.talkingTo = null;
			doorUnlocking = false;
			Global.disableMovement = false;
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
		currentAnimation = OverworldAnimation.padlockDoorOpen;
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
			currentAnimation = OverworldAnimation.padlockDoorNormal;
			opening++;
		}
	}

	private void enterSecondDoor()
	{
		Global.talkingTo = this;
		Global.disableMovement = true;
		door2 = OverworldAnimation.padlockDoorOpen;
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
			door2 = OverworldAnimation.padlockDoorNormal;
			opening++;
		}
	}


	private void exitMainDoor()
	{
		Global.disableMovement = true;
		currentAnimation = OverworldAnimation.padlockDoorOpen;
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

		if(y<=Global.currentRoom.height-100)
		{
			window.overworldMode.ty = y-100;
		}
		if(window.overworldMode.ty+600>Global.currentRoom.height)
		{
			window.overworldMode.ty = Global.currentRoom.height-600;
		}

		if(getFrame(0)>15 && !om.objects.contains(player))
		{
			if(Global.playingAs == 0)
			{
				player = new SimonPlayer(window, x+100, y+200);
			}
			if(Global.playingAs == 1)
			{
				player = new WilliamPlayer(window, x+100, y+300,om);
			}
			om.objects.add(player);
		}
		if(getFrame(0)>=23)
		{
			currentAnimation = OverworldAnimation.padlockDoorNormal;
			opening = 0;
			Global.disableMovement = false;
			Global.talkingTo = null;
		}
	}

	private void exitSecondDoor()
	{
		Global.disableMovement = true;
		door2 = OverworldAnimation.padlockDoorOpen;
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

		if(y2<=Global.currentRoom.height-100)
		{
			window.overworldMode.ty = y2-100;
		}
		if(window.overworldMode.ty+600>Global.currentRoom.height)
		{
			window.overworldMode.ty = Global.currentRoom.height-600;
		}

		if(getFrame(2)>15 && !om.objects.contains(player))
		{
			if(Global.playingAs == 0)
			{
				player = new SimonPlayer(window, x2+100, y2+200);
			}
			if(Global.playingAs == 1)
			{
				player = new WilliamPlayer(window, x2+100, y2+300,om);
			}
			om.objects.add(player);
		}
		if(getFrame(2)>=23)
		{
			door2 = OverworldAnimation.padlockDoorNormal;
			opening = 0;
			Global.disableMovement = false;
			Global.talkingTo = null;
		}
	}
	private void openDoor()
	{
		if(Global.cPressed && opening == 0 && distanceToPoint(ObjectId.Player,x+100,y)<340)
		{
			opening = 1;
			mainDoor = true;
		}
		if(Global.cPressed && opening == 0 && distanceToPoint(ObjectId.Player,x2+100,y2)<340)
		{
			opening = 1;
			mainDoor = false;
		}
		switch(opening)
		{
			case 0:
				currentAnimation = OverworldAnimation.padlockDoorNormal;
				door2 = OverworldAnimation.padlockDoorNormal;
				break;
			case 1:
				if(mainDoor)
				{
					enterMainDoor();
					door2 = OverworldAnimation.padlockDoorNormal;
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
					currentAnimation = OverworldAnimation.padlockDoorNormal;
				}
				else
				{
					exitMainDoor();
					door2 = OverworldAnimation.padlockDoorNormal;
					
				}
				break;
			default:
				break;
		}
	}

}
