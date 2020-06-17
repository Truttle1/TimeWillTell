package net.truttle1.time.overworld.doors;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.WorldId;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.Room;
import net.truttle1.time.overworld.RoomId;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.npc.NPC;
import net.truttle1.time.overworld.warps.StoneAgeWarps;
import net.truttle1.time.main.Game;

public class DoorLomo extends NPC{

	private int inputDelay = -1;
	private BufferedImage[] cKey; 
	public Room warpRoom;
	public int warpX;
	public int warpY;
	public int warpDir;
	private boolean warping = false;
	public boolean changeMusic = false;
	public File music;
	private GameObject player;
	public DoorLomo(Game window, int x, int y, OverworldMode om) {
		super(window,x,y, om);
		currentAnimation = OverworldAnimation.caveDoor;
		this.id = ObjectId.Door;
		if(Quest.quests[Quest.LOMO] < Global.LOMOCONSTANT && (om.currentRoom == RoomId.Lomo1 || om.currentRoom == RoomId.Lomo2))
		{
			currentAnimation = OverworldAnimation.caveDoorBoard;
		}
	}
	public void setWarp(int wx, int wy, Room r, boolean changeMusic, File music)
	{
		warpX = wx;
		warpY = wy;
		warpRoom = r;
		this.changeMusic = changeMusic;
		this.music = music;
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
		return new Rectangle(x+130,y,80,300);
	}
	@Override
	public void render(Graphics g) {
		if(player != null && player.getBounds().intersects(getBounds()) && Global.talking == 0 && !Global.disableMovement)
		{
			this.animate(x+120, y-50, BattleAnimation.cIcon, 1, g);
		}
		if(getFrame(0)>getAnimationLength(currentAnimation))
		{
			setFrame(0,0);
		}
		try
		{
			this.animate(x, y, currentAnimation, 0, g);
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
		if(warping && Fade.fadeIn)
		{
			/*
			if(changeMusic)
			{
				AudioHandler.playMusic(music);
			}
			*/
			System.out.println("HI!!!!");
			warpRoom.loadStage();
			if(Game.currentWorld == WorldId.StoneAge || Game.currentWorld == WorldId.Pyruz)
			{
				StoneAgeWarps.warps(warpRoom);
			}
			warpRoom.addPlayer(warpX, warpY);
			om.objects.remove(this);
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
		if(!om.objects.contains(player))
		{
			player = null;
		}
		if(Global.talking == 2 && Global.talkingTo == this)
		{
			Global.zPressed = false;
			Global.talking = 0;
			Global.disableMovement = false;
		}
		if(Global.cPressed && Global.talking == 0 && player != null && player.getBounds().intersects(getBounds()) )
		{
			if(Quest.quests[Quest.LOMO]<Global.LOMOCONSTANT)
			{
				if(Global.talking == 0)
				{
					Global.talkingTo = this;
					Global.talking = 1;
					Global.disableMovement = true;
					player.hVelocity = 0;
					if(player instanceof WilliamPlayer)
					{
						player.currentAnimation = BattleAnimation.williamTalk;
						SpeechBubble.talk("This place is starting to set me on edge...Why did I/decide to come here? Where is everyone? Why is/this building boarded up? So many questions...*sigh*",Global.willFont);
					}
					else
					{
						SpeechBubble.talk("It's Locked!");
					}
				}
			}
			else
			{
				warping = true;
				Fade.warpHang = true;
				Fade.run(window, ModeType.Overworld,false);
				om.clearRoom();
				for(int j=0; j<om.objects.size();j++)
				{
					if(om.objects.get(j).id == ObjectId.Warp)
					{
						om.objects.remove(om.objects.get(j));
					}
				}
				for(int j=0; j<om.objects.size();j++)
				{
					if(om.objects.get(j).id == ObjectId.Door && om.objects.get(j) != this)
					{
						om.objects.remove(om.objects.get(j));
					}
				}
			}
		}
	}

}
