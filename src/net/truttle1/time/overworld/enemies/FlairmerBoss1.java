package net.truttle1.time.overworld.enemies;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.monsters.FlairmerRed;
import net.truttle1.time.battle.monsters.Ursear;
import net.truttle1.time.battle.monsters.WilliamEnemy1;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.RoomId;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;

public class FlairmerBoss1 extends NPC{

	int amount;
	boolean defeated;
	int defeatedTime;
	WilliamPlayer william;
	public FlairmerBoss1(int x, int y, Game window, OverworldMode om, int amount) {
		super(window,x,y, om);
		this.window = window;
		this.id = ObjectId.NPC;
		this.amount = amount;
		currentAnimation = BattleAnimation.flareRedIdle;
		this.flipped = true;
	}

	@Override
	public void tick()
	{
		if(Quest.quests[Quest.LOMOBANK] >= 18)
		{
			om.objects.remove(this);
		}
		if(william == null)
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i).id == ObjectId.Player && om.objects.get(i) instanceof WilliamPlayer)
				{
					william = (WilliamPlayer) om.objects.get(i);
				}
			}
		}
		vVelocity+=2;
		if(Quest.quests[Quest.LOMOBANK] == 14 && william.x <= this.x+275 && Global.currentRoom.id == RoomId.StoneAge5)
		{
			if(Global.talking == 0)
			{
				william.hVelocity = 0;
				window.overworldMode.ty = this.y-150;
				william.flipped = false;
				AudioHandler.playMusic(AudioHandler.evilShuffle);
				Global.talkSound = 0;
				currentAnimation = BattleAnimation.flareRedTalk2;
				william.currentAnimation = BattleAnimation.williamIdle3;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("Lil kiddo! You're not allowed to be here! Now get lost!/Flairmer do what Flairmer want & take what Flairmer want!/Flairmer no listen to authority, Flairmer ARE authority! /GET LOST, BABY KIDDO!");
			}
			if(Global.talking == 2)
			{
				william.flipped = false;
				Global.talkSound = 0;
				currentAnimation = BattleAnimation.flareRedIdle2;
				william.currentAnimation = BattleAnimation.williamTalkPoint;
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				SpeechBubble.talk("Hold on, WHAT'RE YOU DOING IN THERE!? WHAT TYPES OF/HORRIBLE ATROCITIES ARE YOU COMMITTING!?/LET ME INTO THE VILLAGE YOU CREEP!",Global.willFont);
			}
			if(Global.talking == 4)
			{
				william.flipped = false;
				Global.talkSound = 0;
				currentAnimation = BattleAnimation.flareRedTalk;
				william.currentAnimation = BattleAnimation.williamIdle3;
				Global.talkingTo = this;
				Global.talking = 5;
				Global.disableMovement = true;
				SpeechBubble.talk("BWUCK HUCK HUCK HUCK! Lil guy just act tough & scrappy to SCARE/Flairmer!? Well, Flairmer just think it's FUNNY! BWUCK HECK/HACK HWAKK!!!Wanna challenge Flairmer? Go ahead, BABY/KIDDO DORK!!! YOU LOSE!!! BWAR HAR HARR!!!!");
			}
			if(Global.talking == 6)
			{
				william.flipped = false;
				Global.talkSound = 0;
				currentAnimation = BattleAnimation.flareRedIdle2;
				william.currentAnimation = BattleAnimation.williamTalkPoint;
				Global.talkingTo = this;
				Global.talking = 7;
				Global.disableMovement = true;
				SpeechBubble.talk("What!?!? BABY KIDDO DORK!?!?! Listen bub, I don't care how intimidating you/are, what matters is you are doing the WRONG THING!/You are literally BLOCKING THE WAY to what is probably the only major settlement/in this entire time period, so if that's what it takes, then BRING IT ON!!!",Global.willFont);
			}
			if(Global.talking == 8)
			{
				this.setFrame(0, 0);
				super.setFrame(0, 0);
				Global.talkSound = 0;
				currentAnimation = BattleAnimation.flareRedTalk3;
				william.currentAnimation = BattleAnimation.williamIdle3;
				Global.talkingTo = this;
				Global.talking = 9;
				Global.disableMovement = true;
				SpeechBubble.talk("BWARR HARR HURR HARR! FLAIRMER CRUSH PUNY LAME KIDDO!/BWARR HUAR HAUR HURR DWAR HARR HAAAAAAAARR!/GET READY FOR THE FLAIR OF MY FLAAAAAAAAAARE!!!");
			}
			if(Global.talking == 10)
			{
				Quest.quests[Quest.LOMOBANK]++;
				Global.talking = 0;
				Global.bossBattle = true;
				window.battleMode.addMonster(new FlairmerRed(window,720,270,window.battleMode));
				AudioHandler.playMusic(AudioHandler.bossMusic);
				window.battleMode.startBattle();
				Fade.run(window, ModeType.Battle,false);
				setFrame(0,0);
			}
		}
		else if(Quest.quests[Quest.LOMOBANK] == 15)
		{
			currentAnimation = BattleAnimation.flareRedWalk2;
			hVelocity = 4;
			Global.talking = 0;
			Global.disableMovement = false;
		}
		else if(Quest.quests[Quest.LOMOBANK] == 16)
		{
			if(Global.talking == 0)
			{
				william.hVelocity = 0;
				hVelocity = 0;
				window.overworldMode.ty = this.y-150;
				william.flipped = false;
				AudioHandler.playMusic(AudioHandler.evilShuffle);
				Global.talkSound = 0;
				currentAnimation = BattleAnimation.flareRedTalk4;
				william.currentAnimation = BattleAnimation.williamIdle2;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("Flairmer no like you!...Let's call it a draw, you little baby./Now remember: don't mess with Flairmer. You WILL see more Flairmer.");
			}
			else if(Global.talking == 2)
			{
				currentAnimation = BattleAnimation.flareRedWalk;
				
				Global.disableMovement = true;
				Global.talking = 0;
				Quest.quests[Quest.LOMOBANK] = 17;
			}
		}
		else if(Quest.quests[Quest.LOMOBANK] == 17)
		{
			this.flipped = false;
			hVelocity = -8;
			if(x<0)
			{
				AudioHandler.playMusic(AudioHandler.prehistoricMusic);
				Global.disableMovement = false;
				Quest.quests[Quest.LOMOBANK] = 18;
			}
		}
		
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
			{
				Grass g = (Grass)window.overworldMode.objects.get(i);

				if(g.getBounds().intersects(bottomRectangle()) && vVelocity>0)
				{
					vVelocity = 0;
					this.y = g.y-225;
				}
			}
		}
		y += vVelocity;
		x += hVelocity;
		System.out.println(Quest.quests[Quest.LOMOBANK]);
		if(defeatedTime>1)
		{
			AudioHandler.playMusic(AudioHandler.prehistoricMusic);
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.ursearDieAnimation, om);
			die.setRepeating(false);
			om.eyeCandy.add(die);
			om.objects.remove(this);
		}
	}

	@Override
	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+60,y+200,100,50);
	}
	@Override
	public Rectangle topRectangle()
	{
		return new Rectangle(x+60,y+4,60,50);
	}
	@Override
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+120,y+4,50,120);
	}
	@Override
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+45,y+24,50,120);
	}
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x+38,y,120,185);
	}


	@Override
	public void render(Graphics g) {
		try
		{
			animate(x, y, currentAnimation, 0, g);
		}catch(Exception e) {e.printStackTrace();super.setFrame(0,0);setFrame(0,0);}
	}

}
