package net.truttle1.time.overworld.npc.cutscene;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.SimonBattler;
import net.truttle1.time.battle.WilliamBattler;
import net.truttle1.time.battle.monsters.FlairmerRed;
import net.truttle1.time.battle.monsters.Ignacio;
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
import net.truttle1.time.overworld.blocks.Warp;
import net.truttle1.time.overworld.npc.NPC;

public class IggyPreBattleCutscene extends NPC{

	private GameObject simon;
	private GameObject william;
	private GameObject skrapps;
	private GameObject carl;
	private GameObject rage;
	private GameObject npcPlayable;
	private GameObject orb;
	private int stage;
	private int partnerCounter;
	private int offsetX;
	private int offsetY;
	private int shakeTime;
	private int txOriginal;
	private int tyOriginal;
	private boolean playedStomp;
	private boolean finishedBattle;
	public IggyPreBattleCutscene(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = BattleAnimation.iggySit;
		this.id = ObjectId.NPC;
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
		if(currentAnimation.equals(BattleAnimation.iggyTalkPoint))
		{
			this.animate(x, y-32, currentAnimation, 0, g);
		}
		else if(currentAnimation.equals(BattleAnimation.iggyIdleTaunt1))
		{
			try
			{
				this.animateAtSpeed(x, y-24, currentAnimation, 0, g,1.25);
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				this.setFrame(0, 0);
				this.animateAtSpeed(x, y-24, currentAnimation, 0, g,1.25);
			}
		}
		else if(currentAnimation.equals(BattleAnimation.iggyDead) || currentAnimation.equals(BattleAnimation.iggyDeadTalk) )
		{
			this.animate(x, y-24, currentAnimation, 0, g);
		}
		else
		{
			this.animate(x, y, currentAnimation, 0, g);
		}
	}
	@Override
	public void tick()
	{
		if(Quest.quests[Quest.LOMO]>=Global.LOMOCONSTANT && Quest.quests[Quest.ESCAPED]>=2)
		{
			om.objects.remove(this);
		}
		if(simon == null || !om.objects.contains(simon))
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i) instanceof SimonPlayer)
				{
					simon = om.objects.get(i);
				}
			}
		}
		if(simon != null && !om.objects.contains(simon))
		{
			simon = null;
		}
		
		if(william == null || !om.objects.contains(william))
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i) instanceof WilliamPlayer)
				{
					william = om.objects.get(i);
				}
			}
		}
		if(william != null && !om.objects.contains(william))
		{
			william = null;
		}

		if(Quest.quests[Quest.LOMO] == Global.LOMOCONSTANT)
		{
			if(!finishedBattle)
			{
				stage = 1;
				this.setFrame(0, 0);
				currentAnimation = BattleAnimation.iggyDead;
				AudioHandler.playMusic(AudioHandler.levelUpWilliam);
				finishedBattle = true;
			}
			runEnding();
		}
		else
		{
			if(Global.playingAs == 0 && simon != null && simon.x >= this.x-280)
			{
				simon.hVelocity = 0;
				if(simon.y>1490)
				{
					runCutscene();
				}
				else
				{
					Global.disableMovement = true;
				}
			}
			if(Global.playingAs == 1 && william != null && william.x >= this.x-280)
			{
				william.hVelocity = 0;
				if(william.y>1550)
				{
					runCutscene();
				}
				else
				{
					Global.disableMovement = true;
				}
			}
		}
	}
	private void runCutscene()
	{
		if(Global.playingAs == 0)
		{
			simon.hVelocity = 0;
			npcPlayable = william;
		}
		else
		{
			william.hVelocity = 0;
			npcPlayable = simon;
		}
		if(stage == 0)
		{
			Global.talkingTo = this;
			Global.disableMovement = true;
			setupCutscene();
			//AudioHandler.playMusic(AudioHandler.timeForTrouble);
		}
		if(stage == 1)
		{
			om.ty -= 5;
			if(om.tx <= this.x+200)
			{
				om.tx += 1;
			}
			if(Global.playingAs == 1 && simon != null)
			{
				simon.x -= 4;
			}
			else if(Global.playingAs == 0 && william != null)
			{
				william.x -= 4;
			}
			if(skrapps != null)
			{
				skrapps.x -= 8;
			}
			if(skrapps.x<=this.x-600)
			{
				simon.currentAnimation = BattleAnimation.simonIdleAnimation;
				skrapps.currentAnimation = BattleAnimation.skrappsIdle2;
				stage++;
				skrapps.flipped = true;
				simon.flipped = false;
				william.flipped = true;
				simon.hVelocity = 0;
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
			}
		}
		if(stage == 2)
		{
			if(Global.talking == 0)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				this.currentAnimation = BattleAnimation.iggyTalkSit;
				Global.talking++;
				SpeechBubble.talk("My my my! Look what the cat dragged in! I was expecting your arrival soon./And now that you're here, the King himself is gonna have some fun pummeling you!!/FREE AND LIVING PUNCHING BAGS!!! BWARR HARR HAR HARRR!!",Global.iggyFont);
			}
			if(Global.talking == 2)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamTalkPoint);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				this.currentAnimation = BattleAnimation.iggySit1;
				Global.talking++;
				SpeechBubble.talk("First of all, no, you're not going to pummel us! Second of all, you're going to/release the humans and give me the Time Orb!/We can do this the easy way or the hard way, you stupid lug!",Global.willFont);
			}
			if(Global.talking == 4)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				this.currentAnimation = BattleAnimation.iggyTalkSit;
				Global.talking++;
				SpeechBubble.talk("BWARR HARR HARR!! Woah, little squirt! I've never had anyone make me laugh/this hard in ages! I'm gonna wallop you three, it should be quick, easy, and hillariously fun!/As for the humans: They're all my property now! Boss's orders! And that goes for you too,/Neanderthal!",Global.iggyFont);
			}
			if(Global.talking == 6)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setFrame(0,0);
				skrapps.setAnimation(BattleAnimation.skrappsTalk1);
				this.currentAnimation = BattleAnimation.iggySit1;
				Global.talking++;
				SpeechBubble.talk("Wait a second...BOSS!?!?!",Global.skrappsFont);
			}
			if(Global.talking == 8)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				this.currentAnimation = BattleAnimation.iggyTalkSit1;
				Global.talking++;
				SpeechBubble.talk("What, you'd think I would become a malevolent all powerful ruler of a/prehistoric dragon tribe just because I WANT to!?!? Well, duh, the answer would be YES!!/But you know what's even better than that!? GETTING PAID TO DO IT!!! BWARR HARR/HARR!!! And by the way, my part of the boss's plan is pretty much complete now...",Global.iggyFont);
			}
			if(Global.talking == 10)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setFrame(0,0);
				skrapps.setAnimation(BattleAnimation.skrappsTalk1);
				this.currentAnimation = BattleAnimation.iggySit1;
				Global.talking++;
				SpeechBubble.talk("Umm, did you not notice us walk right in? We're kinda here to stop you,/after all.",Global.skrappsFont);
			}
			if(Global.talking == 12)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				this.currentAnimation = BattleAnimation.iggyTalkSit1;
				Global.talking++;
				SpeechBubble.talk("Well I hope you like the last few minutes of your life, because in a short while,/you're gonna meet an EXPLOSIVE end!!! BWARR HARR HARR!!!",Global.iggyFont);
			}
			if(Global.talking == 14)
			{
				Global.talking = 0;
				stage++;
				this.currentAnimation = BattleAnimation.iggySit1;
				AudioHandler.playMusic(AudioHandler.run);
				AudioHandler.playSound(AudioHandler.seQuake);
				offsetX = 0;
				offsetY = 0;
				shakeTime = 0;
				txOriginal = om.tx;
				tyOriginal = om.ty;
			}
		}
		if(stage == 3)
		{
			if(offsetX <= 50 && Math.random()<=.25)
			{
				int tempX = (int) (Math.random()*30)+20;
				offsetX += tempX;
				om.tx += tempX;
			}
			if(offsetX >= -50 && Math.random()<=.25)
			{
				int tempX = (int) (Math.random()*30)+20;
				offsetX -= tempX;
				om.tx -= tempX;
			}
			if(offsetY <= 50 && Math.random()<=.25)
			{
				int tempY = (int) (Math.random()*30)+20;
				offsetY += tempY;
				om.ty += tempY;
			}
			if(offsetY >= -50 && Math.random()<=.25)
			{
				int tempY = (int) (Math.random()*30)+20;
				offsetY -= tempY;
				om.ty -= tempY;
			}
			shakeTime++;
			if(shakeTime>=24)
			{
				stage++;
				om.tx = txOriginal;
				om.ty = tyOriginal;
			}
		}
		if(stage == 4)
		{
			if(Global.talking == 0)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamTalkPoint);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggySit1;
				Global.talking++;
				SpeechBubble.talk("What was that!? Is the volcano ERUPTING!?!?!",Global.willFont);
			}
			if(Global.talking == 2)
			{
				Global.talking = 0;
				stage++;
				this.setFrame(0, 0);
				vVelocity = -60;
				william.setAnimation(BattleAnimation.williamIdle3);
				currentAnimation = BattleAnimation.iggyJump;
				AudioHandler.playSound(AudioHandler.seJump2);
			}
		}
		if(stage == 5)
		{
			if(vVelocity >= 0)
			{
				currentAnimation = BattleAnimation.iggyLand;
				simon.flipped = true;
				william.flipped = false;
				skrapps.flipped = false;
			}
			if(vVelocity != 0)
			{
				this.x -= 32;
			}
			this.y += vVelocity;
			vVelocity += 4;
			
			if(y >= 1320)
			{
				vVelocity = 0;
				om.tx -= 8;
				if(om.tx<this.x-100)
				{
					stage++;
				}
				if(!playedStomp)
				{
					AudioHandler.playSound(AudioHandler.seStomp);
					playedStomp = true;
				}
				currentAnimation = BattleAnimation.iggyIdleTaunt;
				this.flipped = true;
			}
		}

		if(stage == 6)
		{
			if(Global.talking == 0)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyTaunt;
				Global.talking++;
				SpeechBubble.talk("BWARR HARR HARR HARRR!!! Yup, the volcano's erupting! Now, even if I may die in the/following fight, I will know that I at least stopped you two! IN MY BOSS'S FUTURE UTOPIA,/I WILL BE REMEMBERED AS A HERO!!! BWAARRR HAAAAARRR HAAAARRRR!!/I CAN SEE IT NOW: THE KING IGNACIO THE FIRST MEMORIAL DUMPSTER!!!",Global.iggyFont);
			}
			if(Global.talking == 2)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamTalkPoint);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyIdleTaunt1;
				Global.talking++;
				SpeechBubble.talk("You monster!!! Do you even have a conscience? Because if you do,/you will let us out of this stupid volcano right now! The Time Orb can wait!",Global.willFont);
			}
			if(Global.talking == 4)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyTaunt;
				Global.talking++;
				SpeechBubble.talk("CONSCIENCE!?! NAH, I TRADED MINE AWAY FOR LAVA IMMUNITY AND MY OWN/PERSONAL KINGDOM!!! BWARR HARRR HARRRR!!! True story, I actually did need to give/up my conscience or else my boss wouldn't give me this position, and,well, y'know,/I was kinda short on cash, so I took the job knowing it would pay well.",Global.iggyFont);
			}
			if(Global.talking == 6)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setAnimation(BattleAnimation.skrappsTalk1);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyIdleTaunt1;
				Global.talking++;
				SpeechBubble.talk("William's right! You are a monster! LET US OUT!!! LET US OUT!!! LET US OUT!!! ",Global.skrappsFont);
			}
			if(Global.talking == 8)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamTalkPoint);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyIdleTaunt1;
				Global.talking++;
				SpeechBubble.talk("Hold on, we shouldn't call the big guy a monster, that's insensitive.../I have a friend named Creaturey who's considered to ba a monster, and he does/not deserve to be classified as the same thing as this waste of oxygen!!!",Global.willFont);
			}
			if(Global.talking == 10)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyIdleTaunt2;
				Global.talking = 0;
				stage++;
			}
		}
		if(stage == 7)
		{
			txOriginal = om.tx;
			tyOriginal = om.ty;
			om.tx = 0;
			om.ty = 1700;
			rage = new Prop(window,0,1930,om,OverworldAnimation.rageWalk);
			rage.flipped = true;
			om.objects.add(rage);
			carl = new Prop(window,0,2000,om,OverworldAnimation.carlWalk);
			carl.flipped = true;
			om.objects.add(carl);
			stage++;
		}
		if(stage == 8)
		{
			carl.x += 8;
			rage.x += 12;
			if(rage.x >= 500)
			{
				stage++;
			}
		}
		if(stage == 9)
		{
			if(Global.talking == 0)
			{
				carl.setAnimation(OverworldAnimation.carlTalk);
				rage.setAnimation(OverworldAnimation.rageIdle4);
				//william.flipped = true;
				Global.talking++;
				SpeechBubble.talk("Everyone listen up! The volcano is currently in the process of/erupting, something it hasn't done in 6000 years! Now you may be/tempted to be completely horrified, and, well, don't be! Me and/this kind-hearted reptile named Rage are here to set you free!",Global.carlFont);
			}
			if(Global.talking == 2)
			{
				carl.setAnimation(OverworldAnimation.carlIdle);
				rage.setAnimation(OverworldAnimation.rageTalk1);
				//william.flipped = true;
				Global.talking++;
				SpeechBubble.talk("One by one, I will be punching the cages until they fall apart! I would/like to remind you not to flinch when my fist comes directly/towards the metal bar in font of your face. It makes me feel very/sad when you do that.");
			}

			if(Global.talking == 4)
			{
				carl.setAnimation(OverworldAnimation.carlTalk);
				rage.setAnimation(OverworldAnimation.rageIdle4);
				//william.flipped = true;
				Global.talking++;
				SpeechBubble.talk("Once you are out of the cage, get in a group, and I will lead all/of you to the exit of this cave! By the way, my name is Carl, and/I am a CAIMAN, not a crocodile or an alligator. Also, all of the/credit for this idea goes to Rage. Thank him later!",Global.carlFont);
			}
			if(Global.talking == 6)
			{
				Global.talking = 0;
				stage++;
				this.setFrame(0, 0);
			}
		}
		if(stage == 10)
		{
			om.ty = tyOriginal;
			om.tx = txOriginal;

			if(Global.talking == 0)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyTalkPoint;
				this.flipped = false;
				Global.talking++;
				SpeechBubble.talk("Rage you blasted TRAITOR!!! When I'm through with these three,/you will be promplty EXECUTED!!",Global.iggyFont);
			}
			if(Global.talking == 2)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdle3);
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyTaunt;
				this.flipped = true;
				Global.talking++;
				SpeechBubble.talk("And as for you three, I think we've waited long enough. I think/we should get started now! BWACK HACKKKK HARRRRRR!!!",Global.iggyFont);
			}
			if(Global.talking == 4)
			{
				this.setFrame(0, 0);
				Global.talking = 0;
				stage++;
			}
		}
		if(stage == 11)
		{
			this.currentAnimation = BattleAnimation.iggyIdleTaunt;
			stage++;
			Global.talking = 0;
			Global.bossBattle = true;
			window.battleMode.addMonster(new Ignacio(window,600,160,window.battleMode));
			AudioHandler.playMusic(AudioHandler.actOneFinalBoss);
			window.battleMode.startBattle();
			om.ty = tyOriginal;
			om.tx = txOriginal;
			Fade.run(window, ModeType.Battle,false,5);
		}
		if(stage == 12)
		{
			om.ty = tyOriginal;
			om.tx = txOriginal;
			Global.talkingTo = this;
		}
	}
	private void setupCutscene()
	{
		if(Global.playingAs == 1 && william.vVelocity == 0)
		{
			william.currentAnimation = BattleAnimation.williamIdle3;
			simon = new Prop(window,william.x,william.y-65,om,BattleAnimation.simonWalkAnimation);
			om.objects.add(simon);
			simon.flipped = true;
			skrapps = new Prop(window,william.x,william.y,om,BattleAnimation.skrappsRun);
			om.objects.add(skrapps);
			om.ty = william.y-200;
			stage++;
		}
		if(Global.playingAs == 0 && simon.vVelocity == 0)
		{
			simon.currentAnimation = BattleAnimation.simonIdleAnimation;
			william = new Prop(window,simon.x,simon.y+60,om,BattleAnimation.williamWalk3);
			om.objects.add(william);
			skrapps = new Prop(window,simon.x,simon.y+58,om,BattleAnimation.skrappsRun);
			om.objects.add(skrapps);
			stage++;
			om.ty = simon.y-150;
		}
	}
	private void runEnding()
	{
		if(stage == 1)
		{
			Global.disableMovement = true;
			Global.talkingTo = this;
			stage++;
			william.setFrame(0, 0);
		}
		if(stage == 2)
		{
			if(Global.talking == 0)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamTalkHappy);
				skrapps.setAnimation(BattleAnimation.skrappsIdleBattle);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyDead;
				this.flipped = true;
				Global.talking++;
				SpeechBubble.talk("MWAH HAH HAH HAAAAAH!!! Man, that felt awesome, completely destroying some evil/dude fifty times my size! I think I'm gonna like the rest of my journey!/By the way scumball, the best part of this is that your entire prison's/100% gone! Now give me the Time Orb, I've won it fair and square!!!",Global.willFont);
			}
			if(Global.talking == 2)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdleAnimation);
				skrapps.setAnimation(BattleAnimation.skrappsIdleBattle);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyDeadTalk;
				this.flipped = true;
				Global.talking++;
				SpeechBubble.talk("No, I'll NEVER!! I REGRET NOTHING!!!",Global.iggyFont);
			
			}
			if(Global.talking == 4)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamTalkPoint);
				skrapps.setAnimation(BattleAnimation.skrappsIdleBattle);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyDead;
				this.flipped = true;
				Global.talking++;
				SpeechBubble.talk("Of course, I could attack you some more!!! I really wanna get out of this stupid/volcano, so again, we can do this the easy way or the hard way, and I hope/for your own safety you choose the easy way!!!",Global.willFont);
			}
			if(Global.talking == 6)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdleHappy);
				skrapps.setAnimation(BattleAnimation.skrappsIdleBattle);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyDeadTalk;
				this.flipped = true;
				Global.talking++;
				SpeechBubble.talk("AHH!! EASY WAY!! EASY WAY!!!",Global.iggyFont);
			}
			if(Global.talking == 8)
			{
				Global.talking = 0;
				this.stage++;
				orb = new Prop(window, this.x+220, this.y+150, om, OverworldAnimation.yOrbItem);
				om.objects.add(orb);
				orb.vVelocity = -20;
			}
		}
		if(stage == 3)
		{
			if(orb.y >= this.y+250)
			{
				stage++;
				william.setFrame(0, 0);
				orb.y = this.y+280;
			}
			else
			{
				orb.y += orb.vVelocity;
				orb.vVelocity += 4;
			}
		}
		if(stage == 4)
		{
			this.currentAnimation = BattleAnimation.iggyDead;
			william.x -= 4;
			william.setAnimation(BattleAnimation.williamWalkAnimation);
			if(william.x <= orb.x+32)
			{
				stage++;
				william.setFrame(0, 0);
				william.setAnimation(BattleAnimation.williamItem);
			}
		}
		if(stage == 5)
		{
			orb.x = william.x+48;
			orb.y = william.y-48;
			if(Global.talking == 0)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setFrame(0, 0);
				william.setAnimation(BattleAnimation.williamItem);
				skrapps.setAnimation(BattleAnimation.skrappsIdleBattle);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyDead;
				this.flipped = true;
				Global.talking++;
				SpeechBubble.talk("William re-obtained the YELLOW TIME ORB!");
			}
			else if(Global.talking == 2)
			{
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdleHappy);
				skrapps.setAnimation(BattleAnimation.skrappsIdleBattle);
				Global.talking = 0;
				stage++;
			}
		}
		if(stage == 6)
		{
			william.setAnimation(BattleAnimation.williamIdleAnimation);
			orb.y += 45486464;
			rage.x = this.x-350;
			rage.y = this.y+66;
			om.tx -= 20;
			if(om.tx <= rage.x-200)
			{
				stage++;
			}
		}
		if(stage == 7)
		{

			if(Global.talking == 0)
			{
				AudioHandler.playMusic(AudioHandler.run);
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setFrame(0, 0);
				william.setAnimation(BattleAnimation.williamIdleAnimation);
				skrapps.setAnimation(BattleAnimation.skrappsIdleBattle);
				rage.setAnimation(OverworldAnimation.rageTalk1);
				//william.flipped = true;
				this.currentAnimation = BattleAnimation.iggyDead;
				this.flipped = true;
				Global.talking++;
				SpeechBubble.talk("Umm, sorry to spoil the party, but THE VOLCANO IS ERUPTING!!!!/I'm gonna escort you dudes out of this volcano...except you Ignacio,/you don't deserve to be rescued after all you've done!");
			}
			else if(Global.talking == 2)
			{
				AudioHandler.playMusic(AudioHandler.run);
				simon.setAnimation(BattleAnimation.simonIdleAnimation);
				william.setAnimation(BattleAnimation.williamIdleAnimation);
				skrapps.setAnimation(BattleAnimation.skrappsIdleBattle);
				rage.setAnimation(OverworldAnimation.rageIdle1);
				Global.talking = 0;
				stage++;
			}
		}
		if(stage == 8)
		{
			AudioHandler.playMusic(AudioHandler.run);

			Quest.quests[Quest.ESCAPED] = 1;
			Warp tWarp = new Warp(window,0,0,om,0);
			tWarp.setWarp(1200, 1300, OverworldMode.pyruzOutside, true,AudioHandler.run);
			om.objects.add(tWarp);
			tWarp.forceWarp();
			Global.talking = 0;
			Global.disableMovement = false;
			Global.talkingTo = null;
		}
		if(stage < 6)
		{
			om.tx = this.x;
			om.ty = this.y-128;
		}
	}

}

