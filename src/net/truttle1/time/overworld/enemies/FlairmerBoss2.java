package net.truttle1.time.overworld.enemies;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.monsters.FlairmerGreen;
import net.truttle1.time.battle.monsters.FlairmerRed;
import net.truttle1.time.battle.monsters.Ursear;
import net.truttle1.time.battle.monsters.WilliamEnemy1;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
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

public class FlairmerBoss2 extends NPC{

	int amount;
	boolean defeated;
	int defeatedTime;
	GameObject william;
	public FlairmerBoss2(int x, int y, Game window, OverworldMode om, int amount) {
		super(window,x,y, om);
		this.window = window;
		this.id = ObjectId.NPC;
		this.amount = amount;
		currentAnimation = BattleAnimation.flareGreenIdle;
		this.flipped = false;
	}

	@Override
	public void tick()
	{
		if(Quest.quests[Quest.PYRUZ_W] >= 4)
		{
			om.objects.remove(this);
		}
		if(william == null)
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i).id == ObjectId.Player && om.objects.get(i) instanceof WilliamPlayer)
				{
					william = (GameObject) om.objects.get(i);
				}
			}
		}
		vVelocity+=2;
		if(william != null && Quest.quests[Quest.PYRUZ_W] == 0 && william.x >= this.x-275 && Global.currentRoom.id == RoomId.StoneAge4)
		{
			if(Global.talking == 0)
			{
				william.hVelocity = 0;
				window.overworldMode.ty = this.y-150;
				william.flipped = true;
				AudioHandler.playMusic(AudioHandler.evilShuffle);
				Global.talkSound = 0;
				currentAnimation = BattleAnimation.flareGreenTalk;
				william.currentAnimation = BattleAnimation.williamIdle3;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("Hey! Lil kiddo! You no allowed to be here! Mt Pyruz is home of Flairmer,/& you're little lame-o kid, not Flairmer, so scram or face/fury of Flairmer! You know what, Flairmer crush you anyways!/Flairmer like crushing puny idiots like you! BWAR HAR HAR HAR!!!");
			}
			if(Global.talking == 2)
			{
				william.flipped = true;
				Global.talkSound = 0;
				currentAnimation = BattleAnimation.flareGreenIdle;
				william.currentAnimation = BattleAnimation.williamTalk;
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				SpeechBubble.talk("*sigh*...Let's get this over with...",Global.willFont);
			}
			if(Global.talking == 4)
			{
				Quest.quests[Quest.PYRUZ_W]++;
				Global.talking = 0;
				Global.bossBattle = true;
				window.battleMode.addMonster(new FlairmerGreen(window,550,270,window.battleMode));
				window.battleMode.addMonster(new FlairmerRed(window,750,270,window.battleMode));
				AudioHandler.playMusic(AudioHandler.bossMusic);
				window.battleMode.startBattle();
				Fade.run(window, ModeType.Battle,false);
				setFrame(0,0);
			}
		}
		else if(Quest.quests[Quest.PYRUZ_W] == 1)
		{
			currentAnimation = BattleAnimation.flareGreenWalk;
			hVelocity = -8;
			Global.talking = 0;
			Global.disableMovement = false;
		}
		else if(Quest.quests[Quest.PYRUZ_W] == 2)
		{
			if(Global.talking == 0)
			{
				william.hVelocity = 0;
				hVelocity = 0;
				window.overworldMode.ty = this.y-150;
				william.flipped = true;
				AudioHandler.playMusic(AudioHandler.evilShuffle);
				Global.talkSound = 0;
				currentAnimation = BattleAnimation.flareGreenTalk;
				william.currentAnimation = BattleAnimation.williamIdle2;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("Grrr!!! Puny lame kiddo may have defeated Flairmer,/but he no defeat other Flairmer, and he/no defeat king of all Flairmer, Ignacio!/Have fun being loser, baby kiddo!");
			}
			else if(Global.talking == 2)
			{
				currentAnimation = BattleAnimation.flareGreenWalk;
				Global.disableMovement = true;
				Global.talking = 0;
				Quest.quests[Quest.PYRUZ_W] = 3;
			}
		}
		else if(Quest.quests[Quest.PYRUZ_W] == 3)
		{
			this.flipped = true;
			hVelocity = 8;
			if(x>25600)
			{
				AudioHandler.playMusic(AudioHandler.prehistoricMusic);
				Global.disableMovement = false;
				Quest.quests[Quest.PYRUZ_W] = 4;
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
		//System.out.println(Quest.quests[Quest.PYRUZ_W]);
		if(defeatedTime>1)
		{
			AudioHandler.playMusic(AudioHandler.prehistoricMusic);
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.flareGreenDie, om);
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
