package net.truttle1.time.effects;

import java.awt.Color;
import java.awt.Graphics;

import net.truttle1.time.main.GameMode;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.RoomId;

public class Fade {

	public static boolean running = false;
	public static int fadeNum = 0;
	public static boolean fadeIn = false;
	public static Game window;
	public static ModeType mode;
	public static boolean pause;
	public static boolean warpHang;
	public static int warpTime;
	public static int speed;

	public static void run(Game window, ModeType mode, boolean pause)
	{
		run(window,mode, pause, 25);
	}
	public static void run(Game window, ModeType mode, boolean pause, int speed)
	{
		running = true;
		fadeNum = 0;
		fadeIn = false;
		Fade.window = window;
		Fade.mode = mode;
		Fade.pause = pause;
		Fade.speed = speed;
	}
	public static void render(Graphics g)
	{
		if(running)
		{
			if(fadeNum>255)
			{
				fadeNum = 255;
			}
			Color c = new Color(0,0,0,fadeNum);
			g.setColor(c);
			g.fillRect(0, 0, window.WIDTH, window.HEIGHT);
			if(!fadeIn)
			{
				fadeNum+=speed;
				if(fadeNum>=255)
				{
					window.mode = Fade.mode;
					fadeIn = true;
				}
			}
			if(fadeIn)
			{

				if(Global.tutorialBattle && Global.tutorialBattlePhase>4)
				{
					Global.tutorialBattle = false;
					Quest.quests[Quest.TUTORIAL] = 5;
				}
				if(warpHang)
				{
					warpTime++;
					if(warpTime>5)
					{
						if(Quest.quests[Quest.LOMO] == 0 && OverworldMode.currentRoom == RoomId.Lomo1)
						{
							fadeNum-=5;
						}
						else if(OverworldMode.currentRoom == RoomId.Pyruz3 && Quest.quests[Quest.LOMO] < Global.LOMOCONSTANT)
						{
							fadeNum-=4;
						}
						else
						{
							fadeNum-=speed;
						}
					}
				}
				else
				{
					fadeNum-=speed;
				}
				if(fadeNum<=0)
				{
					pause = false;
					running = false;
					warpTime = 0;
					warpHang = false;
				}
			}
		}
	}
}
