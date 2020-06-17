package net.truttle1.time.overworld;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.WorldId;

public class OverworldAnimation {

	public static boolean loaded = false;
	public static boolean loadedStoneAge = false;
	public static boolean loadedDigitalAge = false;
	public static volatile BufferedImage[] grass;
	public static volatile BufferedImage[] gravel;
	public static volatile BufferedImage[] concrete;
	public static volatile BufferedImage[] tiles;
	public static volatile BufferedImage[] ground;
	public static volatile BufferedImage[] stone;
	public static volatile BufferedImage[] stone2;
	public static volatile BufferedImage[] spike;
	public static volatile BufferedImage[] spike2;
	public static volatile BufferedImage[] flagpole;
	public static volatile BufferedImage[] flag;
	public static volatile BufferedImage[] tree1;
	public static volatile BufferedImage[] tree2;
	public static volatile BufferedImage[] juice;
	public static volatile BufferedImage[] rightArrow;
	public static volatile BufferedImage[] leftArrow;
	public static volatile BufferedImage[] waterTop;
	public static volatile BufferedImage[] water;
	public static volatile BufferedImage[] lava;
	public static volatile BufferedImage[] lavaTop;
	public static volatile BufferedImage[] boat;
	public static volatile BufferedImage[] boxOpen;
	public static volatile BufferedImage[] boxClosed;

	public static volatile BufferedImage[] hilgaIdle;
	public static volatile BufferedImage[] hilgaTalk;
	public static volatile BufferedImage[] hilgaWalk;
	

	public static volatile BufferedImage[] rCavemanIdleSad;
	public static volatile BufferedImage[] rCavemanIdle;
	public static volatile BufferedImage[] rCavemanTalk;
	public static volatile BufferedImage[] rCavemanWalk;
	
	public static volatile BufferedImage[] bCavemanIdle;
	public static volatile BufferedImage[] bCavemanIdleSad;
	public static volatile BufferedImage[] bCavemanTalk;
	public static volatile BufferedImage[] bCavemanWalk;

	public static volatile BufferedImage[] bGirlCavemanIdleSad;
	public static volatile BufferedImage[] bGirlCavemanIdle;
	public static volatile BufferedImage[] bGirlCavemanWalk;
	public static volatile BufferedImage[] bGirlCavemanTalk;

	public static volatile BufferedImage[] rGirlCavemanIdleSad;
	public static volatile BufferedImage[] rGirlCavemanIdle;
	public static volatile BufferedImage[] rGirlCavemanTalk;
	public static volatile BufferedImage[] rGirlCavemanWalk;

	public static volatile BufferedImage[] carlIdle;
	public static volatile BufferedImage[] carlHappyIdle;
	public static volatile BufferedImage[] carlHappyIdle1;
	public static volatile BufferedImage[] carlTalk;
	public static volatile BufferedImage[] carlHappyTalk;
	public static volatile BufferedImage[] carlHappyTalk1;
	public static volatile BufferedImage[] carlHappyTalk2;
	public static volatile BufferedImage[] carlWalk;

	public static volatile BufferedImage[] creatSadIdle;
	public static volatile BufferedImage[] creatSadIdle2;
	public static volatile BufferedImage[] creatSadWalk;
	public static volatile BufferedImage[] creatSadTalk;
	public static volatile BufferedImage[] creatSadTalk2;
	public static volatile BufferedImage[] creatHappyIdle;
	public static volatile BufferedImage[] creatHappyWalk;
	public static volatile BufferedImage[] creatHappyTalk;
	public static volatile BufferedImage[] creatHappyTalkStomp;
	public static volatile BufferedImage[] creatHappyIdle2;
	public static volatile BufferedImage[] creatHappyTalk2;
	public static volatile BufferedImage[] creatMadIdle;
	public static volatile BufferedImage[] creatMadIdle2;
	public static volatile BufferedImage[] creatMadTalk;
	public static volatile BufferedImage[] creatMadTalk2;
	public static volatile BufferedImage[] creatJump;
	public static volatile BufferedImage[] creatLand;
	public static volatile BufferedImage[] creatSlide;
	public static volatile BufferedImage[] creatRun;
	public static volatile BufferedImage[] creatDie;
	public static volatile BufferedImage[] creatDead;
	public static volatile BufferedImage[] creatPunch;
	public static volatile BufferedImage[] creatPunch2;
	public static volatile BufferedImage[] creatWin;
	
	public static volatile BufferedImage[] suzyIdle;
	public static volatile BufferedImage[] suzyTalk;
	public static volatile BufferedImage[] suzyWalk;
	public static volatile BufferedImage[] suzyScared;
	

	public static volatile BufferedImage[] carneWalk;
	public static volatile BufferedImage[] carneTalk;
	public static volatile BufferedImage[] carneIdle;
	public static volatile BufferedImage[] carneTalkSad;
	public static volatile BufferedImage[] carneIdleSad;

	public static volatile BufferedImage[] ursearTalk;

	public static volatile BufferedImage[] yOrbItem;
	public static volatile BufferedImage[] bookItem;

	public static volatile BufferedImage[] appleTree;
	public static volatile BufferedImage[] sign;
	public static volatile BufferedImage[] saveSign;
	public static volatile BufferedImage[] cage;
	public static volatile BufferedImage[] caveDoor;
	public static volatile BufferedImage[] caveDoorBoard;

	public static volatile BufferedImage[] doorClosed;
	public static volatile BufferedImage[] doorLocked;
	public static volatile BufferedImage[] doorOpen;
	
	public static volatile BufferedImage[] rageIdle;
	public static volatile BufferedImage[] rageIdle1;
	public static volatile BufferedImage[] rageIdle2;
	public static volatile BufferedImage[] rageIdle3;
	public static volatile BufferedImage[] rageIdle4;
	public static volatile BufferedImage[] rageTalk;
	public static volatile BufferedImage[] rageTalk1;
	public static volatile BufferedImage[] rageItem;
	public static volatile BufferedImage[] rageWalk;
	public static volatile BufferedImage[] rageGive;
	public static volatile BufferedImage[] ragePunch;

	public static volatile BufferedImage[] boulder;
	public static volatile BufferedImage[] boulderThin;
	
	public static volatile BufferedImage[] eruptVideo;

	public static volatile BufferedImage[] padlockDoorClosed;
	public static volatile BufferedImage[] padlockDoorOpen;	
	public static volatile BufferedImage[] padlockDoorNormal;
	public static volatile BufferedImage[] throne;
	public static volatile BufferedImage[] bankSign;
	
	public static volatile BufferedImage[] timeMachineDisabled;
	public static volatile BufferedImage[] timeMachineY;
	public static volatile BufferedImage[] timeMachineYRide;
	
	public static volatile BufferedImage[] skyscraper;
	public static volatile BufferedImage[] slidingDoor;

	public static volatile BufferedImage[] cityNPC0Idle;
	public static volatile BufferedImage[] cityNPC0Talk;

	public static volatile BufferedImage[] cityNPC1Idle;
	public static volatile BufferedImage[] cityNPC1Talk;

	public static volatile BufferedImage[] cityNPC2Idle;
	public static volatile BufferedImage[] cityNPC2Talk;

	public static volatile BufferedImage[] cityNPC3Idle;
	public static volatile BufferedImage[] cityNPC3Talk;

	public static volatile BufferedImage[] cityNPC4Idle;
	public static volatile BufferedImage[] cityNPC4Talk;

	public static volatile BufferedImage[] cityNPC5Idle;
	public static volatile BufferedImage[] cityNPC5Talk;
	
	public static volatile BufferedImage[] burgerHouseSign;
	public static volatile BufferedImage[] burgerHouseRegister;
	public static volatile BufferedImage[] billboard;

	public static volatile BufferedImage[] modernTree;
	public static volatile BufferedImage[] modernTree1;
	public static volatile BufferedImage[] trash;
	
	
	public static void loadOverworldAnimations(Game window)
	{
		grass = loadAnimation("/overworld/grass/top", 1, window);
		ground = loadAnimation("/overworld/grass/ground", 1, window);
		stone = loadAnimation("/overworld/blocks/stone", 1, window);
		stone2 = loadAnimation("/overworld/blocks/stone2", 1, window);
		spike = loadAnimation("/overworld/blocks/spikes", 1, window);
		spike2 = loadAnimation("/overworld/blocks/spikes2", 1, window);
		lava = loadAnimation("/overworld/lava/simplelava", 1, window);
		lavaTop = loadAnimation("/overworld/lava/top", 12, window);
		flag = loadAnimation("/overworld/blocks/flag", 1, window);
		tree1 = loadAnimation("/overworld/blocks/tree", 1, window);
		tree2 = loadAnimation("/overworld/blocks/tree2", 1, window);
		flagpole = loadAnimation("/overworld/blocks/flagpole", 1, window);
		rightArrow = loadAnimation("/overworld/arrowright/arrow", 24, window);
		leftArrow = loadAnimation("/overworld/arrowleft/arrow", 24, window);
		boulder = loadAnimation("/overworld/boulder/boulder", 6, window);
		boulderThin = loadAnimation("/overworld/boulder/tboulder", 6, window);


		if(Game.currentWorld == WorldId.Pyruz || Game.currentWorld == WorldId.StoneAge)
		{
			loadStoneAge(window);
		}
		if(Game.currentWorld == WorldId.Digital)
		{
			loadDigitalAge(window);
		}
		creatMadIdle = loadAnimation("/overworld/creaturey/idlemad/idle", 12, window);
		creatMadIdle2 = loadAnimation("/overworld/creaturey/idlemad/idle2", 24, window);
		creatMadTalk = loadAnimation("/overworld/creaturey/talkmad/talk", 24, window);
		
		creatSadIdle = loadAnimation("/overworld/creaturey/idlesad/idle", 24, window);
		creatSadIdle2 = loadAnimation("/overworld/creaturey/idlesad2/idle", 24, window);
		creatSadTalk = loadAnimation("/overworld/creaturey/talksad/talk", 10, window);
		creatSadTalk2 = loadAnimation("/overworld/creaturey/talksad2/talk", 24, window);
		creatSadWalk = loadAnimation("/overworld/creaturey/walksad/walk", 24, window);
		creatHappyIdle = loadAnimation("/overworld/creaturey/idlehappy2/idle", 24, window);
		creatHappyTalk = loadAnimation("/overworld/creaturey/talkhappy2/talk", 10, window);
		creatHappyTalkStomp = loadAnimation("/overworld/creaturey/talkhappy3/talk2", 18, window);
		creatHappyWalk = loadAnimation("/overworld/creaturey/walkhappy/walk", 24, window);
		creatHappyIdle2 = loadAnimation("/overworld/creaturey/idlehappy4/idle", 24, window);
		creatHappyTalk2 = loadAnimation("/overworld/creaturey/talkhappy3/talk", 18, window);
		creatJump = loadAnimation("/overworld/creaturey/jump/jump", 1, window);
		creatLand = loadAnimation("/overworld/creaturey/jump/land", 1, window);
		creatSlide = loadAnimation("/overworld/creaturey/slide/slide", 6, window);
		creatRun = loadAnimation("/overworld/creaturey/run/run", 12, window);
		creatDie = loadAnimation("/overworld/creaturey/dead/die", 6, window);
		creatDead = loadAnimation("/overworld/creaturey/dead/dead", 6, window);
		creatPunch = loadAnimation("/overworld/creaturey/punch/punch", 24, window);
		creatPunch2 = loadAnimation("/overworld/creaturey/punch/punch2", 24, window);
		creatWin = loadAnimation("/overworld/creaturey/winminigame/win2", 52, window);
		
		carlIdle = loadAnimation("/overworld/carl/idle/idle", 6, window);
		carlHappyIdle = loadAnimation("/overworld/carl/happyidle/idle", 12, window);
		carlHappyIdle1 = loadAnimation("/overworld/carl/happyidle/idle1", 12, window);
		carlTalk = loadAnimation("/overworld/carl/talk/talk", 6, window);
		carlHappyTalk = loadAnimation("/overworld/carl/happytalk/talk", 6, window);
		carlHappyTalk1 = loadAnimation("/overworld/carl/happytalk/talk1", 12, window);
		carlHappyTalk2 = loadAnimation("/overworld/carl/happytalk/talk2", 6, window);
		carlWalk = loadAnimation("/overworld/carl/walk/walk", 12, window);


		doorClosed = loadAnimation("/overworld/door/door", 1, window);
		doorLocked = loadAnimation("/overworld/door/locked", 1, window);
		doorOpen = loadAnimation("/overworld/door/openclose", 24, window);
		
		boxClosed = loadAnimation("/overworld/box/box", 1, window);
		boxOpen = loadAnimation("/overworld/box/boxopen", 1, window);
		
		timeMachineDisabled = loadAnimation("/overworld/timemachine/machine", 1, window);
		timeMachineY = loadAnimation("/overworld/timemachine/yellowtimemachine", 24, window);
		timeMachineYRide = loadAnimation("/overworld/timemachine/enteryellow", 72, window);
		saveSign = loadAnimation("/overworld/bg/save", 1, window);
		loaded = true;
		
	}
	public static BufferedImage[] loadAnimation(String path, int sub, Game window)
	{
		try
		{
			BufferedImage[] tempAnimation = new BufferedImage[3];
			tempAnimation[0] = window.imageLoad.loadImage(path + "_strip.png");
			tempAnimation[1] = window.imageLoad.loadImage(path + "_00001.png");
			tempAnimation[2] = new BufferedImage(sub,1,BufferedImage.TYPE_INT_ARGB);
			return tempAnimation;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BufferedImage[] tempAnimation = new BufferedImage[sub];
			for(int i=0; i<sub; i+=Global.framePerImg)
			{
				if(i<9)
				{
					tempAnimation[i] = window.imageLoad.loadImage(path + "_0000" + (i+1) + ".png");
				}
				else if(i<99)
				{
					tempAnimation[i] = window.imageLoad.loadImage(path + "_000" + (i+1) + ".png");
				}
				else if(i<999)
				{
					tempAnimation[i] = window.imageLoad.loadImage(path + "_00" + (i+1) + ".png");
				}
			}
			try {
				File f = new File("res/" + path + "_strip.png");
				f.createNewFile();
				ImageIO.write(combineImages(tempAnimation[0].getWidth(),tempAnimation[0].getHeight(),tempAnimation),"png",f);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return tempAnimation;
		}
	}

	public static BufferedImage combineImages(int width, int height, BufferedImage[] iArray)
	{
		BufferedImage combined = new BufferedImage(width*iArray.length,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = combined.createGraphics();
		for(int i=0; i<iArray.length;i++)
		{
			g2d.drawImage(iArray[i], width*i, 0,null);
		}
		g2d.dispose();
		return combined;
	}
	public static void loadStoneAge(Game window)
	{
		hilgaIdle = loadAnimation("/overworld/hilga/idle/idle", 24, window);
		hilgaTalk = loadAnimation("/overworld/hilga/talk/talking", 10, window);
		hilgaWalk = loadAnimation("/overworld/hilga/walk/walk", 24, window);

	
		bCavemanIdle = loadAnimation("/overworld/stonenpc/blueidle/idle", 24, window);
		bCavemanIdleSad = loadAnimation("/overworld/stonenpc/blueidle/idlesad", 24, window);
		bCavemanWalk = loadAnimation("/overworld/stonenpc/bluewalk/walk", 24, window);
		bCavemanTalk = loadAnimation("/overworld/stonenpc/bluetalk/talk", 12, window);

		bGirlCavemanIdleSad = loadAnimation("/overworld/stonenpc/bluegidle/idlesad", 24, window);
		bGirlCavemanIdle = loadAnimation("/overworld/stonenpc/bluegidle/idle", 24, window);
		bGirlCavemanWalk = loadAnimation("/overworld/stonenpc/bluegwalk/walk", 24, window);
		bGirlCavemanTalk = loadAnimation("/overworld/stonenpc/bluegtalk/talk", 24, window);

		rCavemanIdleSad = loadAnimation("/overworld/stonenpc/redidle/idlesad", 24, window);
		rCavemanIdle = loadAnimation("/overworld/stonenpc/redidle/idle", 24, window);
		rCavemanWalk = loadAnimation("/overworld/stonenpc/redwalk/walk", 24, window);
		rCavemanTalk = loadAnimation("/overworld/stonenpc/redtalk/talk", 12, window);

		rGirlCavemanIdle = loadAnimation("/overworld/stonenpc/redgidle/idle", 24, window);
		rGirlCavemanWalk = loadAnimation("/overworld/stonenpc/redgwalk/walk", 24, window);
		rGirlCavemanTalk = loadAnimation("/overworld/stonenpc/redgtalk/talk", 24, window);

		suzyIdle = loadAnimation("/overworld/suzy/idle/idle", 24, window);
		suzyTalk = loadAnimation("/overworld/suzy/talk/talk", 24, window);
		suzyWalk = loadAnimation("/overworld/suzy/walk/walk", 24, window);
		suzyScared = loadAnimation("/overworld/suzy/scared/scared", 6, window);
		
		carneIdle = loadAnimation("/overworld/goodflairmer/idle/idle", 24, window);
		carneWalk = loadAnimation("/overworld/goodflairmer/walk/walk", 24, window);
		carneTalk = loadAnimation("/overworld/goodflairmer/talk/talk", 24, window);
		carneIdleSad = loadAnimation("/overworld/goodflairmer/idle/idle1", 24, window);
		carneTalkSad = loadAnimation("/overworld/goodflairmer/talk/talk1", 24, window);
		

		rageIdle = loadAnimation("/overworld/goodflairmer/bidle/idle",24,window);
		rageIdle1 = loadAnimation("/overworld/goodflairmer/bidle/idle1",24,window);
		rageIdle2 = loadAnimation("/overworld/goodflairmer/bidle/idle2",24,window);
		rageIdle3 = loadAnimation("/overworld/goodflairmer/bidle/idle3",24,window);
		rageIdle4 = loadAnimation("/overworld/goodflairmer/bidle/idle4",24,window);
		rageTalk = loadAnimation("/overworld/goodflairmer/btalk/talk",24,window);
		rageTalk1 = loadAnimation("/overworld/goodflairmer/btalk/talk1",24,window);
		rageItem = loadAnimation("/overworld/goodflairmer/bitem/item",24,window);
		rageWalk = loadAnimation("/overworld/goodflairmer/bwalk/walk",24,window);
		rageGive = loadAnimation("/overworld/goodflairmer/bgive/give",48,window);
		ragePunch = loadAnimation("/overworld/goodflairmer/bpunch/punch",48,window);
		
		ursearTalk = loadAnimation("/fight/ursear/talk/urseartalk", 6, window);
		
		yOrbItem = loadAnimation("/overworld/items/yelloworb/orb", 6, window);
		bookItem = loadAnimation("/overworld/items/book/book", 1, window);

		waterTop = loadAnimation("/overworld/blocks/water/water", 24, window);
		water = loadAnimation("/overworld/blocks/water/water2", 1, window);
		appleTree = loadAnimation("/overworld/bg/appletree", 1, window);
		juice = loadAnimation("/overworld/bg/juice", 1, window);
		caveDoor = loadAnimation("/overworld/bg/cavedoor", 1, window);
		caveDoorBoard = loadAnimation("/overworld/bg/boardedcave", 1, window);
		cage = loadAnimation("/overworld/cage/cage", 1, window);
		throne = loadAnimation("/overworld/throne/throne", 1, window);
		boat = loadAnimation("/overworld/boat/boat", 6, window);padlockDoorClosed = loadAnimation("/overworld/padlockdoor/closed/door", 8, window);
		padlockDoorNormal = loadAnimation("/overworld/padlockdoor/open/normal", 1, window);
		padlockDoorOpen = loadAnimation("/overworld/padlockdoor/open/open", 24, window);

		eruptVideo = loadAnimation("/overworld/kaboom/kaboom", 96, window);
		bankSign = loadAnimation("/overworld/bg/bank", 1, window);
		sign = loadAnimation("/overworld/bg/sign", 1, window);
		
		
		loadedStoneAge = true;
	
	}
	public static void loadDigitalAge(Game window)
	{
		gravel = loadAnimation("/overworld/blocks/gravel",1,window);
		concrete = loadAnimation("/overworld/blocks/concrete",1,window);
		sign = loadAnimation("/overworld/bg/sign1",1,window);
		skyscraper = loadAnimation("/overworld/skyscraper/skyscraper",4,window);
		tiles = loadAnimation("/overworld/blocks/tiles",1,window);
		slidingDoor = loadAnimation("/overworld/skyscraper/opendoor",12,window);
		cityNPC0Idle = loadAnimation("/overworld/citynpc/0/idle",24,window);
		cityNPC0Talk = loadAnimation("/overworld/citynpc/0/talk",24,window);
		cityNPC1Idle = loadAnimation("/overworld/citynpc/1/idle",24,window);
		cityNPC1Talk = loadAnimation("/overworld/citynpc/1/talk",24,window);
		cityNPC2Idle = loadAnimation("/overworld/citynpc/2/idle",24,window);
		cityNPC2Talk = loadAnimation("/overworld/citynpc/2/talk",24,window);
		cityNPC3Idle = loadAnimation("/overworld/citynpc/3/idle",24,window);
		cityNPC3Talk = loadAnimation("/overworld/citynpc/3/talk",24,window);
		cityNPC4Idle = loadAnimation("/overworld/citynpc/4/idle",24,window);
		cityNPC4Talk = loadAnimation("/overworld/citynpc/4/talk",24,window);
		cityNPC5Idle = loadAnimation("/overworld/citynpc/5/idle",24,window);
		cityNPC5Talk = loadAnimation("/overworld/citynpc/5/talk",24,window);
		burgerHouseSign = loadAnimation("/overworld/bg/burgersign",1,window);
		burgerHouseRegister = loadAnimation("/overworld/bg/register",1,window);
		billboard = loadAnimation("/overworld/advertisement/billboard", 5, window);

		trash = loadAnimation("/overworld/bg/trash", 1, window);
		modernTree = loadAnimation("/overworld/bg/tree2", 1, window);
		modernTree1 = loadAnimation("/overworld/bg/tree3", 1, window);
		
		loadedDigitalAge = true;
	}
}
