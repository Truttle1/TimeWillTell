package net.truttle1.time.overworld.blocks;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;

import net.truttle1.time.effects.Fade;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.WorldId;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.Room;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.warps.DigitalAgeWarps;
import net.truttle1.time.overworld.warps.StoneAgeWarps;
import net.truttle1.time.titlescreen.TitleMode;

public class Warp extends GameObject{

	OverworldMode om;
	public Room warpRoom;
	public int warpX;
	public int warpY;
	public int warpDir;
	private boolean warping = false;
	public boolean changeMusic = false;
	public File music;
	public Warp(Game window, int x, int y, OverworldMode om, int warpDir) {
		super(window);
		this.x = x;
		this.y = y;
		this.om = om;
		this.warpDir = warpDir;
		this.id = ObjectId.Warp;
	}
	public void setWarp(int wx, int wy, Room r, boolean changeMusic, File music)
	{
		warpX = wx;
		warpY = wy;
		warpRoom = r;
		this.changeMusic = changeMusic;
		this.music = music;
		this.id = ObjectId.Warp;
	}
	@Override
	public void tick() {
		if(warpDir == 0)
		{
			this.currentAnimation = OverworldAnimation.rightArrow;
		}
		else if(warpDir == 1)
		{
			this.currentAnimation = OverworldAnimation.leftArrow;
		}
		if(warping && Fade.fadeIn)
		{
			/*
			if(changeMusic)
			{
				AudioHandler.playMusic(music);
			}
			*/
			if(Quest.quests[Quest.TUTORIAL]==13)
			{

				om.simonHouse.loadStage();
				om.simonHouse.addPlayer(200, 500);
				om.objects.remove(this);
				StoneAgeWarps.warps(om.simonHouse);
				Quest.quests[Quest.TUTORIAL] = 14;
			}

			else if(Quest.quests[Quest.ESCAPED]==1 && this.warpRoom != om.pyruzOutside)
			{
				om.lomo2.loadStage();
				om.lomo2.addPlayer(300, 2500);
				om.objects.remove(this);
				StoneAgeWarps.warps(om.lomo2);
				Quest.quests[Quest.ESCAPED] = 2;
			}
			else if(warpRoom == null)
			{
			}
			else if(Quest.quests[Quest.LOMO]>=7 || warpRoom != om.lomo3)
			{
				System.out.println("!@!!!");
				warpRoom.loadStage();
				warpRoom.addPlayer(warpX, warpY);
				om.objects.remove(this);

				for(int j=0; j<om.objects.size();j++)
				{
					if(om.objects.get(j).id == ObjectId.Door)
					{
						om.objects.remove(om.objects.get(j));
					}
				}
				if(Game.currentWorld == WorldId.StoneAge || Game.currentWorld == WorldId.Pyruz)
				{
					StoneAgeWarps.warps(warpRoom);
				}
				if(Game.currentWorld == WorldId.Digital)
				{
					DigitalAgeWarps.warps(warpRoom);
				}
			}
			else
			{
			}
		}
		for(int i=0; i<om.objects.size(); i++)
		{
			if(om.objects.get(i) != null && om.objects.get(i).id == ObjectId.Player)
			{
				GameObject player = null;
				if(om.objects.get(i) instanceof SimonPlayer)
				{
					player = (SimonPlayer)om.objects.get(i);
				}
				if(om.objects.get(i) instanceof WilliamPlayer)
				{
					player = (WilliamPlayer)om.objects.get(i);
				}
				if((Game.currentWorld == WorldId.Digital || Game.currentWorld == WorldId.StoneAge || Game.currentWorld == WorldId.Pyruz) && !warping && !Fade.running)
				{
					if(player.getBounds().intersects(getBounds()))
					{

						for(int j=0; j<om.objects.size();j++)
						{
							if(om.objects.get(j).id == ObjectId.Warp && om.objects.get(j) != this)
							{
								om.objects.remove(om.objects.get(j));
							}
						}
						for(int j=0; j<om.objects.size();j++)
						{
							if(om.objects.get(j).id == ObjectId.Door)
							{
								om.objects.remove(om.objects.get(j));
							}
						}
						warping = true;
						Fade.warpHang = true;
						if(Quest.quests[Quest.TUTORIAL] == 13)
						{

							window.textMode.setup("The Next Day...", 300, 300);
							Fade.run(window, ModeType.Text,false);
						}
						else if(warpRoom == om.lomo3 && Quest.quests[Quest.LOMO] == 6)
						{
							window.minigameMode1.init();
							Fade.run(window, ModeType.Minigame1,false);
						}
						else if(warpRoom == null)
						{
							Fade.run(window, ModeType.TitleScreen,false);
							TitleMode.demoEnded = true;
							TitleMode.intro = false;
						}
						else
						{
							Fade.run(window, ModeType.Overworld,false);
						}
						for(int j=0; j<om.objects.size();j++)
						{
							if(om.objects.get(j).id == ObjectId.Door)
							{
								om.objects.remove(om.objects.get(j));
							}
						}
						om.clearRoom();
					}
				}
			}
		}
	}

	public Rectangle getBounds()
	{
		if(warpDir == 0)
		{
			return new Rectangle(x+75,y,25,180);
		}
		else
		{
			return new Rectangle(x,y,25,180);
		}
	}
	@Override
	public void render(Graphics g) {

		if(warpDir == 0)
		{
			this.currentAnimation = OverworldAnimation.rightArrow;
		}
		else if(warpDir == 1)
		{
			this.currentAnimation = OverworldAnimation.leftArrow;
		}
		if(distanceTo(ObjectId.Player)<200 && Global.talking == 0)
		{
			if(warpDir == 0)
			{
				this.animate(x-100, y, currentAnimation, 0, g);
			}
			else if(warpDir == 1)
			{
				this.animate(x+100, y, currentAnimation, 0, g);
			}
		}
	}

	public int distanceTo(ObjectId id)
	{
		for(int i=0; i<om.objects.size();i++)
		{
			if(om.objects.get(i).id == id)
			{
				GameObject temp = om.objects.get(i);
				int distX = (int) Math.pow(Math.abs((this.x)-temp.x),2);
				int distY = (int) Math.pow(Math.abs(this.y-temp.y),2);
				return (int) Math.sqrt(distX+distY);
			}
		}
		return 9999;
	}
	
	public void forceWarp()
	{
		warping = true;
		Fade.warpHang = true;
		if(Quest.quests[Quest.TUTORIAL] == 13)
		{

			window.textMode.setup("The Next Day...", 300, 300);
			Fade.run(window, ModeType.Text,false);
		}
		else if(warpRoom == om.lomo3 && Quest.quests[Quest.LOMO] == 6)
		{
			window.minigameMode1.init();
			Fade.run(window, ModeType.Minigame1,false);
		}
		else if(warpRoom == null)
		{
			Fade.run(window, ModeType.TitleScreen,false);
			TitleMode.demoEnded = true;
			TitleMode.intro = false;
		}
		else
		{
			Fade.run(window, ModeType.Overworld,false);
		}
		om.clearRoom();
		for(int j=0; j<om.objects.size();j++)
		{
			if(om.objects.get(j).id == ObjectId.Warp && om.objects.get(j) != this)
			{
				om.objects.remove(om.objects.get(j));
			}
		}
		for(int j=0; j<om.objects.size();j++)
		{
			if(om.objects.get(j).id == ObjectId.Door)
			{
				om.objects.remove(om.objects.get(j));
			}
		}
	}

}
