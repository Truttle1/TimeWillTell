package net.truttle1.time.main;

import java.awt.Font;

import net.truttle1.time.battle.Attacker;
import net.truttle1.time.overworld.Room;

public class Global {
	public static int frameRate = 24;
	public static int framePerImg = 24/frameRate;
	
	public static boolean zDown;
	public static boolean xDown;
	public static boolean cDown;
	public static boolean vDown;
	public static boolean upDown;
	public static boolean downDown;
	public static boolean rightDown;
	public static boolean leftDown;
	public static boolean disableMovement;
	public static boolean zPressed;
	public static boolean xPressed;
	public static boolean cPressed;
	public static boolean vPressed;
	public static boolean upPressed;
	public static boolean downPressed;
	public static boolean rightPressed;
	public static boolean leftPressed;
	
	public static boolean zReleased;
	public static boolean xReleased;
	public static boolean cReleased;
	public static boolean vReleased;
	public static boolean upReleased;
	public static boolean downReleased;
	public static boolean rightReleased;
	public static boolean leftReleased;
	public static boolean informing = false;
	public static boolean enableGravity = true;
	public static boolean swingingClub;
	public static int comingFromTime = -1;
	public static int[] items = new int[100];
	public static int[] keyItems = new int[100];
	public static String OS = System.getProperty("os.name");
	
	public static boolean save = false;
	public static boolean load = false;

	public static int playingAs = 0;
	public static boolean hurt = false;
	public static boolean[] chestOpened = new boolean[9999];
	public static boolean[] unlock = new boolean[9999];
	public static boolean hasSimon = true;
	public static boolean hasWilliam = false;
	public static boolean hasPartner = false;
	public static boolean unlockedPartner[] = new boolean[99];
	public static int attackPhase = 0;
	public static Attacker attacker = Attacker.Simon;

	public static Room currentRoom;
	public static int talking = 0;
	public static GameObject talkingTo;
	public static int simonHP = 10;
	public static int simonHPMax = 10;
	public static int simonSP = 4;
	public static int simonSPMax = 4;
	public static int williamHP = 8;
	public static int williamHPMax = 8;
	public static int williamSP = 4;
	public static int williamSPMax = 4;
	public static int simonPow = 1;
	public static int williamPow = 1;
	public static int flinchAmount = 16;
	public static int williamDefense = 0;

	public static int partnerHP[] = new int[99];
	public static int partnerHPMax[] = new int[99];
	public static int partnerSP[] = new int[99];
	public static int partnerSPMax[] = new int[99];
	public static int partnerPow[] = new int[99];
	
	public static boolean doorOpened[] = new boolean[999];

	public static final int SKRAPPS = 0;
	public static final int RAGE = 1;
	
	public static int money = 5;

	public static boolean playerSad = false;
	
	public static int simonXP;
	public static int williamXP;
	public static int partnerXP[] = new int[99];
	
	public static int currentPartner = 0;

	public static int talkSound = 0;
	public static int simonLevel = 1;
	public static int williamLevel = 1;
	public static int partnerLevel[] = new int[99];
	
	public static boolean parallax = true;
	public static boolean bossBattle = false;
	public static boolean tutorialBattle = false;
	public static int tutorialBattlePhase = 0;
	public static final int LOMOCONSTANT = 11;

	public static Font battleFont = new Font("Candal", Font.BOLD, 20);
	public static Font textFont = new Font("Candal", Font.PLAIN, 24);
	public static Font iggyFont = new Font("Benny Blanco", Font.PLAIN, 20);
	public static Font willFont = new Font("pingwing", Font.PLAIN, 24);
	public static Font carlFont = new Font("Consolas", Font.BOLD, 28);
	public static Font skrappsFont = new Font("j.d.", Font.BOLD, 19);
	public static Font creatFont = new Font("Corbel", Font.BOLD, 32);
	public static Font creatBigger = new Font("Corbel", Font.BOLD, 108);
	public static Font creatBig = new Font("Corbel", Font.BOLD, 72);
	public static Font winFont1 = new Font("Candal", Font.BOLD, 36);
	public static Font winFont2 = new Font("Candal", Font.BOLD, 72);
	public static void doChangesForLinux()
	{
		willFont = new Font("pingwing", Font.PLAIN, 22);
		carlFont = new Font("Consolas", Font.BOLD, 24);
		creatBig = new Font("Corbel", Font.BOLD, 64);
	}
	public static void initPartners()
	{

		partnerHP[SKRAPPS] = 6;
		partnerHPMax[SKRAPPS] = 6;
		partnerSP[SKRAPPS] = 12;
		partnerSPMax[SKRAPPS] = 12;
		partnerPow[SKRAPPS] = 2;
		partnerLevel[SKRAPPS] = 1;
		
		partnerHP[RAGE] = 20;
		partnerHPMax[RAGE] = 20;
		partnerSP[RAGE] = 0;
		partnerSPMax[RAGE] = 0;
		partnerPow[RAGE] = 255;
		partnerLevel[RAGE] = 1;
		for(int i=0; i<unlockedPartner.length;i++)
		{
			unlockedPartner[i] = false;
		}
	}
}
