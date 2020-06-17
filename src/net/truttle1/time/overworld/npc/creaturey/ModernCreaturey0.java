package net.truttle1.time.overworld.npc.creaturey;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.monsters.Baumber;
import net.truttle1.time.battle.monsters.Garbzop;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.npc.BurgerHouseRegister;
import net.truttle1.time.overworld.npc.NPC;
import net.truttle1.time.overworld.npc.cutscene.Prop;

public class ModernCreaturey0 extends Creaturey
{

	private GameObject player;
	private BurgerHouseRegister burgerPerson;
	private int simonNodTime = -1;
	private Prop garbzop;
	public ModernCreaturey0(Game window, int x, int y, OverworldMode om) 
	{
		super(window, x, y, om);
		this.currentAnimation = OverworldAnimation.creatHappyIdle2;
		this.id = ObjectId.NPC;
		this.flipped = true;
	}
	
	@Override
	public void render(Graphics g) 
	{
		this.animate(x, y, currentAnimation, 0, g);
	}
	
	@Override
	public void tick()
	{
		if(player == null || !om.objects.contains(player))
		{
			player = getPlayer();
		}
		
		if(burgerPerson == null)
		{
			for(int i = 0; i < om.objects.size(); i++)
			{
				if(om.objects.get(i) instanceof BurgerHouseRegister)
				{
					burgerPerson = (BurgerHouseRegister) om.objects.get(i);
				}
			}
		}
		if(Quest.quests[Quest.FINDCREATUREY] == 1)
		{
			if(Global.talking == 0 && player.x > 370)
			{
				AudioHandler.playMusic(AudioHandler.cutscene1);
				window.overworldMode.ty = this.y-200;
				currentAnimation = OverworldAnimation.creatSadTalk2;
				if(player instanceof SimonPlayer)
				{
					player.setAnimation(BattleAnimation.simonIdleAnimation);
				}
				if(player instanceof WilliamPlayer)
				{
					player.setAnimation(BattleAnimation.williamIdle2);
				}
				player.hVelocity = 0;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("No, I'm not a monster from Convex Inc.../I have no clue what Convex Inc. even is.../Could I just have my burger please?",Global.creatFont);
			}
			else if(Global.talking == 2)
			{
				window.overworldMode.ty = this.y-200;
				currentAnimation = OverworldAnimation.creatSadIdle2;
				if(player instanceof SimonPlayer)
				{
					player.setAnimation(BattleAnimation.simonIdleAnimation);
				}
				if(player instanceof WilliamPlayer)
				{
					player.setAnimation(BattleAnimation.williamIdle2);
				}
				player.hVelocity = 0;
				Global.talking = 3;
				Global.disableMovement = true;
				burgerPerson.setGuy(OverworldAnimation.cityNPC2Talk);
				SpeechBubble.talk("Uh... Okay... I've never seen a monster around here that wasn't from/Convex Inc. .../But, money is money, and if you want to buy a burger, I'm not gonna/stop you. That'll be $16.30.");
			
			}
			else if(Global.talking == 4)
			{
				window.overworldMode.ty = this.y-200;
				currentAnimation = OverworldAnimation.creatHappyTalk2;
				if(player instanceof SimonPlayer)
				{
					player.setAnimation(BattleAnimation.simonIdleAnimation);
				}
				if(player instanceof WilliamPlayer)
				{
					player.setAnimation(BattleAnimation.williamIdle2);
				}
				player.hVelocity = 0;
				Global.talking = 5;
				Global.disableMovement = true;
				burgerPerson.setGuy(OverworldAnimation.cityNPC2Idle);
				SpeechBubble.talk("Thank you!/I'm glad that your business doesn't treat me differently for being/a large reptilian monster.../Thanks for that!",Global.creatFont);
			
			}
			else if(Global.talking == 6)
			{
				currentAnimation = OverworldAnimation.creatHappyTalkStomp;
				String pn = "";
				if(player instanceof SimonPlayer)
				{
					pn = "Simon";
					player.setAnimation(BattleAnimation.simonIdleAnimation);
				}
				if(player instanceof WilliamPlayer)
				{
					pn = "William";
					player.setAnimation(BattleAnimation.williamIdle2);
				}
				player.hVelocity = 0;
				Global.talking = 7;
				Global.disableMovement = true;
				flipped = false;
				SpeechBubble.talk("Oh! Hey " + pn + "!/I was looking forward to running into you! Say, have you seen Carl/around? I've been looking all over for him!",Global.creatFont);
			
			}
			else if(Global.talking == 8)
			{
				currentAnimation = OverworldAnimation.creatHappyIdle2;
				String pn = "";
				if(player instanceof SimonPlayer)
				{
					pn = "Simon";
					simonNodTime++;
					Global.talking = 10;
				}
				if(player instanceof WilliamPlayer)
				{
					pn = "William";
					player.setAnimation(BattleAnimation.williamTalk2);
					SpeechBubble.talk("You know what, Creaturey? I actually did run into Carl./He said he was looking for you as a matter of fact.",Global.willFont);
					Global.talking = 9;
				}
				player.hVelocity = 0;
				Global.disableMovement = true;
				flipped = false;
			}
			else if(Global.talking == 10)
			{
				currentAnimation = OverworldAnimation.creatHappyTalk2;
				String pn = "";
				if(player instanceof SimonPlayer)
				{
					pn = "Simon";
					player.setAnimation(BattleAnimation.simonIdleAnimation);
					SpeechBubble.talk("You did see him!? That must mean he's arrived in the Digital Age./I was just grabbing lunch before going to look for him at the park./If you see him again, tell him that's where I'll be.",Global.creatFont);
				}
				if(player instanceof WilliamPlayer)
				{
					pn = "William";
					player.setAnimation(BattleAnimation.williamIdle2);
					SpeechBubble.talk("He did? That sounds like Carl./I was just grabbing lunch before going to look for him at the park./If you see him again, tell him that's where I'll be.",Global.creatFont);
					
				}
				Global.talking = 11;
				player.hVelocity = 0;
				Global.disableMovement = true;
				flipped = false;
			}
			else if(Global.talking == 12)
			{
				Global.talking = 0;
				Quest.quests[Quest.FINDCREATUREY] = 2;
				currentAnimation = OverworldAnimation.creatSadIdle2;
				if(player instanceof WilliamPlayer)
				{
					player.setAnimation(BattleAnimation.williamIdle3);
				}
			}
		}
		else if(Quest.quests[Quest.FINDCREATUREY] == 2)
		{
			if(garbzop == null)
			{
				AudioHandler.playMusic(AudioHandler.evilShuffle);
				garbzop = new Prop(window, -100, y+100, om, BattleAnimation.garbzopWalk);
				om.objects.add(garbzop);
				player.flipped = !player.flipped;
				garbzop.flipped = true;
			}
			garbzop.x += 6;
			if(garbzop.x > 200)
			{
				Quest.quests[Quest.FINDCREATUREY] = 3;
			}
		}
		else if(Quest.quests[Quest.FINDCREATUREY] == 3)
		{
			garbzop.setAnimation(BattleAnimation.garbzopIdle);
			if(Global.talking == 0)
			{
				window.overworldMode.ty = this.y-200;
				if(player instanceof SimonPlayer)
				{
					currentAnimation = OverworldAnimation.creatSadTalk2;
					SpeechBubble.talk("Oh no, not another one!/Simon, I take it you've run into these as well... *sigh*/Hopefully you guys can take them on... I'm weak!",Global.creatFont);
					player.setAnimation(BattleAnimation.simonIdleAnimation);
				}
				if(player instanceof WilliamPlayer)
				{
					currentAnimation = OverworldAnimation.creatSadIdle2;
					SpeechBubble.talk("You stupid pieces of garbage!!!/I don't know where you come from or what you want, but you guys are nothing but/trouble!/Here comes the pain, trash boy!!!",Global.willFont);
					player.setAnimation(BattleAnimation.williamTalkPoint);
				}
				player.hVelocity = 0;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				
			}
			if(Global.talking == 2)
			{
				Quest.quests[Quest.FINDCREATUREY] = 4;
				window.battleMode.addMonster(new Garbzop(window,580,320,window.battleMode));
				window.battleMode.addMonster(new Baumber(window,750,270,window.battleMode));
				AudioHandler.playMusic(AudioHandler.bossMusic);
				window.battleMode.startBattle();
				Fade.run(window, ModeType.Battle,true);
				Global.talking = 0;
			}
		}
		else if(Quest.quests[Quest.FINDCREATUREY] == 4)
		{
			om.objects.remove(garbzop);
			if(Global.talking == 0)
			{
				window.overworldMode.ty = this.y-200;
				if(player instanceof SimonPlayer)
				{
					currentAnimation = OverworldAnimation.creatHappyTalk;
					SpeechBubble.talk("Wow, that was great, Simon!/I haven't seen you battle since I was a child, but you're still good.../Probably because you're the same age as you were when I last saw you...",Global.creatFont);
					player.setAnimation(BattleAnimation.simonIdleAnimation);
				}
				if(player instanceof WilliamPlayer)
				{
					currentAnimation = OverworldAnimation.creatHappyTalk;
					SpeechBubble.talk("Wow, that was great, William!/This is my first time seeing you battle, but you have potential!",Global.creatFont);
					player.setAnimation(BattleAnimation.williamIdlePoint);
				}
				player.hVelocity = 0;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				AudioHandler.playMusic(AudioHandler.cutscene1);
				
			}
			if(Global.talking == 2)
			{
				window.overworldMode.ty = this.y-200;
				if(player instanceof SimonPlayer)
				{
					player.setAnimation(BattleAnimation.simonIdleAnimation);
				}
				if(player instanceof WilliamPlayer)
				{
					player.setAnimation(BattleAnimation.williamIdle2);
				}
				currentAnimation = OverworldAnimation.creatHappyTalk;
				player.flipped = !player.flipped;
				SpeechBubble.talk("Anyway, I'm headed to the park.//Catch you guys later!",Global.creatFont);
				player.hVelocity = 0;
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				
			}
			if(Global.talking == 4)
			{
				Quest.quests[Quest.FINDCREATUREY] = 5;
				Global.talking = 0;
			}
		}
		else if(Quest.quests[Quest.FINDCREATUREY] == 5)
		{
			Global.talkingTo = null;
			currentAnimation = OverworldAnimation.creatHappyWalk;
			x -= 10;
			if(x < -200)
			{
				AudioHandler.playMusic(AudioHandler.metro);
				Global.disableMovement = false;
				Quest.quests[Quest.FINDCREATUREY] = 6;
			}
		}
		
		if(Quest.quests[Quest.FINDCREATUREY] >= 6)
		{
			om.objects.remove(this);
		}
		
	}

}
