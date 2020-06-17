package net.truttle1.time.battle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.WorldId;

public class BattleAnimation {

	public static BufferedImage[] imageKey = new BufferedImage[99999];
	public static String[] imagePath = new String[99999];
	public static int currentKey = 0;
	public static boolean loaded = false;
	public static boolean loadedStoneAge = false;
	public static boolean loadedDigitalAge = false;
	public static volatile BufferedImage[] simonIdleAnimation;
	public static volatile BufferedImage[] simonWalkAnimation;
	public static volatile BufferedImage[] simonPunchAnimation;
	public static volatile BufferedImage[] simonHitAnimation;
	public static volatile BufferedImage[] simonDodgeAnimation;
	public static volatile BufferedImage[] simonCrouchAnimation;
	public static volatile BufferedImage[] simonDeadAnimation;
	public static volatile BufferedImage[] simonJumpAnimation;
	public static volatile BufferedImage[] simonLandAnimation;
	public static volatile BufferedImage[] simonClubHit;
	public static volatile BufferedImage[] simonClubMiss;
	public static volatile BufferedImage[] simonApples;
	public static volatile BufferedImage[] simonItem;
	public static volatile BufferedImage[] simonLevelUp;
	public static volatile BufferedImage[] simonNod;
	public static volatile BufferedImage[] simonTalk;

	public static volatile BufferedImage[] ursearIdleAnimation;
	public static volatile BufferedImage[] ursearWalkAnimation;
	public static volatile BufferedImage[] ursearAttackAnimation;
	public static volatile BufferedImage[] ursearHitAnimation;
	public static volatile BufferedImage[] ursearDieAnimation;

	public static volatile BufferedImage[] vultIdle;
	public static volatile BufferedImage[] vultAttack;
	public static volatile BufferedImage[] vultDie;
	public static volatile BufferedImage[] vultFlinch;

	public static volatile BufferedImage[] garbzopIdle;
	public static volatile BufferedImage[] garbzopAttack;
	public static volatile BufferedImage[] garbzopDie;
	public static volatile BufferedImage[] garbzopFlinch;
	public static volatile BufferedImage[] garbzopWalk;
	
	public static volatile BufferedImage[] baumberIncog;
	public static volatile BufferedImage[] baumberIdle;
	public static volatile BufferedImage[] baumberDie;
	public static volatile BufferedImage[] baumberFlinch;
	public static volatile BufferedImage[] baumberAttack;
	public static volatile BufferedImage[] bomb;
	public static volatile BufferedImage[] explosion;

	public static volatile BufferedImage[] rockstarIdle;
	public static volatile BufferedImage[] rockstarWalk;
	public static volatile BufferedImage[] rockstarAttack;
	public static volatile BufferedImage[] rockstarDie;
	public static volatile BufferedImage[] rockstarFlinch;
	
	public static volatile BufferedImage[] williamIdleAnimation;
	public static volatile BufferedImage[] williamIdle2;
	public static volatile BufferedImage[] williamIdle3;
	public static volatile BufferedImage[] williamIdle4;
	public static volatile BufferedImage[] williamIdleHappy;
	public static volatile BufferedImage[] williamIdlePoint;
	public static volatile BufferedImage[] williamDart;
	public static volatile BufferedImage[] williamTalkPoint;
	public static volatile BufferedImage[] williamTalk;
	public static volatile BufferedImage[] williamTalk2;
	public static volatile BufferedImage[] williamTalk3;
	public static volatile BufferedImage[] williamTalkHappy;
	public static volatile BufferedImage[] williamTalkDead;
	public static volatile BufferedImage[] williamHitAnimation;
	public static volatile BufferedImage[] williamWalkAnimation;
	public static volatile BufferedImage[] williamWalk2;
	public static volatile BufferedImage[] williamWalk3;
	public static volatile BufferedImage[] williamWalk4;
	public static volatile BufferedImage[] williamItem;
	public static volatile BufferedImage[] williamJumpAnimation;
	public static volatile BufferedImage[] williamLandAnimation;
	public static volatile BufferedImage[] williamDeadAnimation;
	public static volatile BufferedImage[] williamFlipAnimation;
	public static volatile BufferedImage[] williamCounterAnimation;
	public static volatile BufferedImage[] williamFailAnimation;
	public static volatile BufferedImage[] williamLevelUp;
	public static volatile BufferedImage[] williamRead;

	public static volatile BufferedImage[] williamSpikeWalk;
	public static volatile BufferedImage[] williamSpikeTalk;
	public static volatile BufferedImage[] williamSpikeJump;
	public static volatile BufferedImage[] williamSpikeLand;
	public static volatile BufferedImage[] williamSpikeDart;
	public static volatile BufferedImage[] williamSpikeIdle;
	public static volatile BufferedImage[] williamSpikeHit;

	public static volatile BufferedImage[] flareRedIdle;
	public static volatile BufferedImage[] flareRedIdle2;
	public static volatile BufferedImage[] flareRedWalk;
	public static volatile BufferedImage[] flareRedWalk2;
	public static volatile BufferedImage[] flareRedPunch;
	public static volatile BufferedImage[] flareRedTalk;
	public static volatile BufferedImage[] flareRedTalk2;
	public static volatile BufferedImage[] flareRedTalk3;
	public static volatile BufferedImage[] flareRedTalk4;
	public static volatile BufferedImage[] flareRedHeal;
	public static volatile BufferedImage[] flareRedFlame;
	public static volatile BufferedImage[] flareRedFlinch;
	public static volatile BufferedImage[] flareRedDie;
	public static volatile BufferedImage[] flareRedWait;

	public static volatile BufferedImage[] flareGreenIdle;
	public static volatile BufferedImage[] flareGreenWalk;
	public static volatile BufferedImage[] flareGreenWalk2;
	public static volatile BufferedImage[] flareGreenTalk;
	public static volatile BufferedImage[] flareGreenGrabAxe;
	public static volatile BufferedImage[] flareGreenJump;
	public static volatile BufferedImage[] flareGreenLand;
	public static volatile BufferedImage[] flareGreenWhack;
	public static volatile BufferedImage[] flareGreenDie;
	public static volatile BufferedImage[] flareGreenFlinch;

	public static volatile BufferedImage[] flareFly;
	public static volatile BufferedImage[] flareFlyTalk;
	public static volatile BufferedImage[] flareFlyBurn;

	public static volatile BufferedImage[] fire;
	public static volatile BufferedImage[] note;
	public static volatile BufferedImage[] ball;
	public static volatile BufferedImage[] dart;

	public static volatile BufferedImage[] arrow;
	public static volatile BufferedImage[] arrowUpwards;
	public static volatile BufferedImage[] specialBar;
	public static volatile BufferedImage[] zIcon;
	public static volatile BufferedImage[] xIcon;
	public static volatile BufferedImage[] cIcon;
	public static volatile BufferedImage[] heart;
	public static volatile BufferedImage[] star;
	public static volatile BufferedImage[] hitAny;
	public static volatile BufferedImage[] hitOneHP;
	public static volatile BufferedImage[] hitTwoHP;

	public static volatile BufferedImage[] iggyWalk;
	public static volatile BufferedImage[] iggyTalk;
	public static volatile BufferedImage[] iggyIdle;
	public static volatile BufferedImage[] iggyPush;
	public static volatile BufferedImage[] iggyTaunt;
	public static volatile BufferedImage[] iggyIdleTaunt;
	public static volatile BufferedImage[] iggyTaunt2;
	public static volatile BufferedImage[] iggyIdleTaunt2;
	public static volatile BufferedImage[] iggyIdleTaunt1;
	public static volatile BufferedImage[] iggySit;
	public static volatile BufferedImage[] iggySit1;
	public static volatile BufferedImage[] iggyTalkSit;
	public static volatile BufferedImage[] iggyTalkSit1;
	public static volatile BufferedImage[] iggyJump;
	public static volatile BufferedImage[] iggyLand;
	public static volatile BufferedImage[] iggyTalkPoint;
	public static volatile BufferedImage[] iggyIdleBattle;
	public static volatile BufferedImage[] iggyFlinch;
	public static volatile BufferedImage[] iggySmash;
	public static volatile BufferedImage[] iggyEatPepperRed;
	public static volatile BufferedImage[] iggyEatPepperGreen;
	public static volatile BufferedImage[] iggyEatPepperBlue;
	public static volatile BufferedImage[] iggyFireBreath;
	public static volatile BufferedImage[] iggyWindUp;
	public static volatile BufferedImage[] iggyRun;
	public static volatile BufferedImage[] iggyDead;
	public static volatile BufferedImage[] iggyDeadTalk;
	public static volatile BufferedImage[] iggyFlop0;
	public static volatile BufferedImage[] iggyFlop1;

	public static volatile BufferedImage[] skrappsIdle;
	public static volatile BufferedImage[] skrappsIdle2;
	public static volatile BufferedImage[] skrappsIdle1;
	public static volatile BufferedImage[] skrappsTalkStick;
	public static volatile BufferedImage[] skrappsTalk;
	public static volatile BufferedImage[] skrappsTalk1;
	public static volatile BufferedImage[] skrappsRun;
	public static volatile BufferedImage[] skrappsRunStick;
	public static volatile BufferedImage[] skrappsWhack;
	public static volatile BufferedImage[] skrappsIdleBattle;
	public static volatile BufferedImage[] skrappsDead;
	public static volatile BufferedImage[] skrappsCrouch;
	public static volatile BufferedImage[] skrappsFlinch;
	public static volatile BufferedImage[] skrappsItem;
	public static volatile BufferedImage[] skrappsLevelUp;

	public static volatile BufferedImage[] skrappsBall1;
	public static volatile BufferedImage[] skrappsBallThrow;
	public static volatile BufferedImage[] skrappsHit0;
	public static volatile BufferedImage[] skrappsHit1;
	public static volatile BufferedImage[] skrappsCast;

	
	public static void loadBattleAnimations(Game window)
	{
		simonWalkAnimation = loadAnimation("/fight/simon/walk/walk", 24, window);
		simonDodgeAnimation = loadAnimation("/fight/simon/guard/guard", 12, window);
		simonCrouchAnimation = loadAnimation("/fight/simon/crouch/crouch", 12, window);
		simonIdleAnimation = loadAnimation("/fight/simon/idle/idle", 24, window);
		simonWalkAnimation = loadAnimation("/fight/simon/walk/walk", 24, window);
		simonPunchAnimation = loadAnimation("/fight/simon/punch/punch", 72, window);
		simonHitAnimation = loadAnimation("/fight/simon/hit/hit", 12, window);
		simonDeadAnimation = loadAnimation("/fight/simon/dead/dead", 6, window);
		simonJumpAnimation = loadAnimation("/fight/simon/jump/jump", 1, window);
		simonLandAnimation = loadAnimation("/fight/simon/jump/land", 1, window);
		simonApples = loadAnimation("/fight/simon/apples/apples", 1, window);
		simonItem = loadAnimation("/fight/simon/item/item", 1, window);
		simonClubHit = loadAnimation("/fight/simon/club/club", 36, window);
		simonClubMiss = loadAnimation("/fight/simon/club/fall", 36, window);
		simonLevelUp = loadAnimation("/fight/simon/level/level", 24, window);
		simonNod = loadAnimation("/fight/simon/nod/nod", 24, window);
		simonTalk = loadAnimation("/fight/simon/talk/talk", 24, window);
		dart = loadAnimation("/fight/williamspike/dart/dart", 1, window);
		
		if(window.currentWorld == null || window.currentWorld == WorldId.Pyruz || window.currentWorld == WorldId.StoneAge)
		{
			loadStoneAge(window);
		}
		if(window.currentWorld == WorldId.Digital)
		{
			loadDigitalAge(window);
		}
		
		williamIdleAnimation = loadAnimation("/fight/william/idle/idle", 24, window);
		williamIdle2 = loadAnimation("/fight/william/idle2/idle", 24, window);
		williamIdle3 = loadAnimation("/fight/william/idle3/idle", 24, window);
		williamIdle4 = loadAnimation("/fight/william/idle4/idle", 24, window);
		williamLevelUp = loadAnimation("/fight/william/levelup/level", 24, window);
		williamItem = loadAnimation("/fight/william/itemhold/item", 1, window);
		williamTalkPoint = loadAnimation("/fight/william/pointtalk/talk", 19, window);
		williamIdlePoint = loadAnimation("/fight/william/pointidle/idle", 19, window);
		williamTalk = loadAnimation("/fight/william/talk/talk", 24, window);
		williamTalk2 = loadAnimation("/fight/william/talk2/talk", 24, window);
		williamTalk3 = loadAnimation("/fight/william/talk3/talk", 24, window);
		williamTalkHappy = loadAnimation("/fight/william/overjoyed/talk", 12, window);
		williamIdleHappy = loadAnimation("/fight/william/overjoyed/idle", 12, window);
		williamTalkDead = loadAnimation("/fight/william/deadtalk/talk", 24, window);
		williamHitAnimation = loadAnimation("/fight/william/hit/hit", 16, window);
		williamWalkAnimation = loadAnimation("/fight/william/run/run", 12, window);
		williamWalk3 = loadAnimation("/fight/william/run3/run", 12, window);
		williamWalk4 = loadAnimation("/fight/william/run4/run", 12, window);
		williamWalk2 = loadAnimation("/fight/william/run2/run", 6, window);
		williamJumpAnimation = loadAnimation("/fight/william/jump/jump", 2, window);
		williamLandAnimation = loadAnimation("/fight/william/jump/land", 2, window);
		williamFlipAnimation = loadAnimation("/fight/william/jump/flip", 5, window);
		williamDeadAnimation = loadAnimation("/fight/william/dead/dead", 24, window);
		williamCounterAnimation = loadAnimation("/fight/william/counter/counter", 30, window);
		williamFailAnimation = loadAnimation("/fight/william/counter/fail", 36, window);
		williamRead = loadAnimation("/fight/william/read/read", 24, window);
		williamDart = loadAnimation("/fight/william/dart/dart", 24, window);
		
		skrappsIdle = loadAnimation("/fight/skrapps/idle/idle", 24, window);
		skrappsIdle2 = loadAnimation("/fight/skrapps/idle2/idle", 24, window);
		skrappsIdle1 = loadAnimation("/fight/skrapps/idle3/idle", 24, window);
		skrappsIdleBattle = loadAnimation("/fight/skrapps/battleidle/idle", 24, window);
		skrappsRun = loadAnimation("/fight/skrapps/run/run", 12, window);
		skrappsRunStick = loadAnimation("/fight/skrapps/run1/run", 12, window);
		skrappsTalkStick = loadAnimation("/fight/skrapps/sticktalk/talk", 12, window);
		skrappsTalk = loadAnimation("/fight/skrapps/happytalk/talk2", 12, window);
		skrappsTalk1 = loadAnimation("/fight/skrapps/sadtalk/talk", 12, window);
		skrappsCrouch = loadAnimation("/fight/skrapps/dodge/dodge", 24, window);
		skrappsDead = loadAnimation("/fight/skrapps/dead/dead", 24, window);
		skrappsFlinch = loadAnimation("/fight/skrapps/flinch/flinch", 12, window);
		skrappsItem = loadAnimation("/fight/skrapps/item/item", 1, window);
		skrappsWhack = loadAnimation("/fight/skrapps/whack/whack", 48, window);
		skrappsLevelUp = loadAnimation("/fight/skrapps/level/level", 24, window);
		arrowUpwards = loadAnimation("/fight/william/dart/arrow", 1, window);
		
		skrappsBall1 = loadAnimation("/fight/skrapps/baseball/baseball1", 24, window);
		skrappsBallThrow = loadAnimation("/fight/skrapps/baseball/throw", 15, window);
		skrappsHit0 = loadAnimation("/fight/skrapps/baseball/hit", 6, window);
		skrappsHit1 = loadAnimation("/fight/skrapps/baseball/hit2", 24, window);
		skrappsCast = loadAnimation("/fight/skrapps/magic/magic", 48, window);
		ball = loadAnimation("/fight/skrapps/baseball/ball", 6, window);

		hitAny = loadAnimation("/fight/hplose/boom", 1, window);
		heart = loadAnimation("/fight/hplose/heart", 1, window);
		star = loadAnimation("/fight/hplose/star", 1, window);
		arrowUpwards = loadAnimation("/fight/william/dart/arrow", 1, window);
		specialBar = loadAnimation("/fight/william/dart/bar", 1, window);
		hitOneHP = loadAnimation("/fight/hplose/1/1", 18, window);
		hitTwoHP = loadAnimation("/fight/hplose/2/2", 18, window);
		zIcon = loadAnimation("/fight/z_icon/z", 24, window);
		xIcon = loadAnimation("/fight/x_icon/x", 24, window);
		cIcon = loadAnimation("/fight/c_icon/c", 24, window);
		arrow = loadAnimation("/fight/arrow/arrow", 24, window);
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
	public static BufferedImage[] setupKey(String path, int sub, Game window)
	{
		BufferedImage[] tempAnimation = new BufferedImage[sub];
		sub = 0;
		tempAnimation[0] = window.imageLoad.loadImage(path + "_00001.png");
		/*for(int i=0; i<sub; i++)
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
		}*/
		imageKey[currentKey] = tempAnimation[0];
		imagePath[currentKey] = path;
		currentKey++;
		return tempAnimation;
	}
	public static BufferedImage loadFrame(String path, int sub, Game window)
	{
		BufferedImage tempAnimation = null;
		int i = sub;
		if(i<9)
		{
			tempAnimation = window.imageLoad.loadImage(path + "_0000" + (i+1) + ".png");
		}
		else if(i<99)
		{
			tempAnimation = window.imageLoad.loadImage(path + "_000" + (i+1) + ".png");
		}
		else if(i<999)
		{
			tempAnimation = window.imageLoad.loadImage(path + "_00" + (i+1) + ".png");
		}
		return tempAnimation;
	}
	
	public static void loadStoneAge(Game window)
	{
		ursearIdleAnimation = loadAnimation("/fight/ursear/idle/idle", 24, window);
		ursearWalkAnimation = loadAnimation("/fight/ursear/walk/walk", 24, window);
		ursearAttackAnimation = loadAnimation("/fight/ursear/attack/attack", 30, window);
		ursearHitAnimation = loadAnimation("/fight/ursear/hit/hit", 6, window);
		ursearDieAnimation = loadAnimation("/fight/ursear/die/die", 6, window);
		
		flareRedIdle = loadAnimation("/fight/flairmer/red/idle/idle", 24, window);
		flareRedIdle2 = loadAnimation("/fight/flairmer/red/idle2/idle", 24, window);
		flareRedWalk = loadAnimation("/fight/flairmer/red/walk/walk", 24, window);
		flareRedWalk2 = loadAnimation("/fight/flairmer/red/walk2/strut", 24, window);
		flareRedTalk = loadAnimation("/fight/flairmer/red/taunt/taunt", 24, window);
		flareRedTalk2 = loadAnimation("/fight/flairmer/red/taunt2/taunt", 24, window);
		flareRedTalk3 = loadAnimation("/fight/flairmer/red/taunt3/taunt", 12, window);
		flareRedTalk4 = loadAnimation("/fight/flairmer/red/talk/talk", 24, window);
		flareRedPunch = loadAnimation("/fight/flairmer/red/punch/punch", 60, window);
		flareRedFlame = loadAnimation("/fight/flairmer/red/flame/flame", 78, window);
		flareRedFlinch = loadAnimation("/fight/flairmer/red/flinch/flinch", 18, window);
		flareRedHeal = loadAnimation("/fight/flairmer/red/heal/heal", 240, window);
		flareRedDie = loadAnimation("/fight/flairmer/red/die/die", 18, window);
		flareRedWait = loadAnimation("/fight/flairmer/red/wait/wait", 12, window);
		fire = loadAnimation("/fight/flairmer/red/fire/fire", 24, window);

		flareGreenIdle = loadAnimation("/fight/flairmer/green/idle/idle", 24, window);
		flareGreenWalk = loadAnimation("/fight/flairmer/green/walk/walk", 24, window);
		flareGreenWalk2 = loadAnimation("/fight/flairmer/green/walk/hammer", 24, window);
		flareGreenGrabAxe = loadAnimation("/fight/flairmer/green/grabaxe/grab", 120, window);
		flareGreenWhack = loadAnimation("/fight/flairmer/green/whack/whack", 72, window);
		flareGreenTalk = loadAnimation("/fight/flairmer/green/taunt/taunt", 24, window);
		flareGreenJump = loadAnimation("/fight/flairmer/green/jump/jump", 6, window);
		flareGreenLand = loadAnimation("/fight/flairmer/green/jump/land", 1, window);
		flareGreenDie = loadAnimation("/fight/flairmer/green/die/die", 24, window);
		flareGreenFlinch = loadAnimation("/fight/flairmer/green/flinch/flinch", 24, window);
		
		flareFly = loadAnimation("/fight/flairmer/red/fly/fly", 12, window);
		flareFlyTalk = loadAnimation("/fight/flairmer/red/fly/talk", 12, window);
		flareFlyBurn = loadAnimation("/fight/flairmer/red/fly/flame", 12, window);
		
		williamSpikeIdle = loadAnimation("/fight/williamspike/idle/idle", 24, window);
		williamSpikeWalk = loadAnimation("/fight/williamspike/run/run", 12, window);
		williamSpikeDart = loadAnimation("/fight/williamspike/dartthrow/dart", 24, window);
		williamSpikeJump = loadAnimation("/fight/williamspike/jump/jump", 1, window);
		williamSpikeTalk = loadAnimation("/fight/williamspike/talk/talk", 19, window);
		williamSpikeLand = loadAnimation("/fight/williamspike/jump/land", 1, window);
		williamSpikeHit = loadAnimation("/fight/williamspike/hit/hit", 12, window);
		if(Quest.quests[Quest.LOMO]<Global.LOMOCONSTANT)
		{
			iggyIdle = loadAnimation("/fight/ignacio/idle/i_idle", 33, window);
			iggyWalk = loadAnimation("/fight/ignacio/walk2/i_walk", 24, window);
			iggyTalk = loadAnimation("/fight/ignacio/talk/i_talk", 24, window);
			iggyPush = loadAnimation("/fight/ignacio/push/i_push", 24, window);
			iggyIdleTaunt = loadAnimation("/fight/ignacio/taunt/i_idletaunt", 24, window);
			iggyTaunt = loadAnimation("/fight/ignacio/taunt/i_taunt", 24, window);
			iggyIdleTaunt1 = loadAnimation("/fight/ignacio/taunt/i_idletaunt1", 12, window);
			iggyIdleTaunt2 = loadAnimation("/fight/ignacio/taunt/i_idletaunt2", 24, window);
			iggyTaunt2 = loadAnimation("/fight/ignacio/taunt/i_taunt2", 24, window);
			iggySit = loadAnimation("/fight/ignacio/sit/i_sit", 24, window);
			iggySit1 = loadAnimation("/fight/ignacio/sit/i_sit1", 24, window);
			iggyTalkSit = loadAnimation("/fight/ignacio/sit/i_talk", 24, window);
			iggyTalkSit1 = loadAnimation("/fight/ignacio/sit/i_talk1", 24, window);
			iggyJump = loadAnimation("/fight/ignacio/jump/i_jump", 1, window);
			iggyLand = loadAnimation("/fight/ignacio/jump/i_land", 1, window);
			iggyTalkPoint = loadAnimation("/fight/ignacio/talk1/i_talk1", 12, window);
			iggyIdleBattle = loadAnimation("/fight/ignacio/idlebattle/i_idle", 24, window);
			iggyFlinch = loadAnimation("/fight/ignacio/flinch/i_flinch", 6, window);
			iggySmash = loadAnimation("/fight/ignacio/smash/i_smash", 33, window);
			iggyEatPepperRed = loadAnimation("/fight/ignacio/pepperattack/i_eat0", 132, window);
			iggyEatPepperGreen = loadAnimation("/fight/ignacio/pepperattack/i_eat1", 132, window);
			iggyEatPepperBlue = loadAnimation("/fight/ignacio/pepperattack/i_eat2", 132, window);
			iggyFireBreath = loadAnimation("/fight/ignacio/fire/i_fire", 48, window);
			iggyWindUp = loadAnimation("/fight/ignacio/charge/i_charge", 24, window);
			iggyRun = loadAnimation("/fight/ignacio/charge/i_run", 11, window);
			iggyDead = loadAnimation("/fight/ignacio/dead/i_dead", 24, window);
			iggyDeadTalk = loadAnimation("/fight/ignacio/dead/i_talk", 24, window);
			iggyFlop0 = loadAnimation("/fight/ignacio/flop/flop", 18, window);
			iggyFlop1 = loadAnimation("/fight/ignacio/flop/land", 36, window);
			
		}
		
		vultIdle = loadAnimation("/fight/vult/idle/idle", 12, window);
		vultAttack = loadAnimation("/fight/vult/kick/kick", 1, window);
		vultDie = loadAnimation("/fight/vult/die/die", 6, window);
		vultFlinch = loadAnimation("/fight/vult/flinch/die", 6, window);
		
		rockstarIdle = loadAnimation("/fight/rockstar/idle/idle", 24, window);
		rockstarWalk = loadAnimation("/fight/rockstar/walk/walk", 24, window);
		rockstarAttack = loadAnimation("/fight/rockstar/play/play", 24, window);
		rockstarFlinch = loadAnimation("/fight/rockstar/flinch/die", 6, window);
		rockstarDie = loadAnimation("/fight/rockstar/die/die", 12, window);
		note = loadAnimation("/fight/rockstar/play/note", 12, window);
		loadedStoneAge = true;
		
	}
	public static void loadDigitalAge(Game window)
	{
		garbzopIdle = loadAnimation("/fight/garbzop/idle/idle", 24, window);
		garbzopWalk = loadAnimation("/fight/garbzop/walk/walk", 24, window);
		garbzopAttack = loadAnimation("/fight/garbzop/attack/attack", 24, window);
		garbzopFlinch = loadAnimation("/fight/garbzop/hit/hit", 6, window);
		garbzopDie = loadAnimation("/fight/garbzop/die/die", 6, window);
		
		baumberIdle = loadAnimation("/fight/baumber/idle/idle", 24, window);
		baumberAttack = loadAnimation("/fight/baumber/throw/throwbomb", 24, window);
		bomb = loadAnimation("/fight/baumber/throw/bomb", 12, window);
		explosion = loadAnimation("/fight/baumber/throw/explosion", 24, window);
		baumberAttack = loadAnimation("/fight/baumber/throw/throwbomb", 24, window);
		baumberIncog = loadAnimation("/fight/baumber/incognito/incognito", 1, window);
		baumberFlinch = loadAnimation("/fight/baumber/hit/hit", 6, window);
		baumberDie = loadAnimation("/fight/baumber/die/die", 6, window);
		loadedDigitalAge = true;
	}
}
