package net.truttle1.time.overworld.npc;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.monsters.Ursear;
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
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.enemies.WilliamE2;

public class Suzy1 extends NPC{

	BufferedImage[] ursearAnimation;
	SimonPlayer simon;
	boolean ursearVisible = false;
	int ursearX = 0;
	boolean normalTalk = false;
	int ursearTime = -1;
	public Suzy1(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.suzyIdle;
		this.flipped = true;
		this.id = ObjectId.NPC;
		if(Quest.quests[Quest.TUTORIAL]>6)
		{
			om.objects.remove(this);
		}
	}


	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+30,y+100,100,50);
	}
	public Rectangle topRectangle()
	{
		return new Rectangle(x+30,y+34,60,50);
	}
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+90,y+34,50,60);
	}
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+28,y+34,50,60);
	}
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+28,y+27,91,113);
	}

	@Override
	public void render(Graphics g) {
		if(distanceTo(ObjectId.Player)<150 && Global.talking == 0 && !Global.disableMovement)
		{
			if(flipped)
			{
				this.animateNoFlip(x+50, y-100, BattleAnimation.cIcon, 1, g);
			}
			else
			{
				this.animate(x+50, y-100, BattleAnimation.cIcon, 1, g);
			}
		}
		animate(x,y,currentAnimation,0,g);
		if(ursearVisible)
		{
			animate(ursearX,y-50,ursearAnimation,1,g);
		}
		
	}
	@Override
	public int distanceTo(ObjectId id)
	{
		for(int i=0; i<om.objects.size();i++)
		{
			if(om.objects.get(i).id == id)
			{
				GameObject temp = om.objects.get(i);
				int distX = (int) Math.pow(Math.abs((this.x-50)-temp.x),2);
				int distY = (int) Math.pow(Math.abs(this.y-temp.y),2);
				return (int) Math.sqrt(distX+distY);
			}
		}
		return 9999;
	}
	private void runTutorial()
	{
		if(simon == null)
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i).id == ObjectId.Player && om.objects.get(i) instanceof SimonPlayer)
				{
					simon = (SimonPlayer)om.objects.get(i);
				}
			}
		}
		//System.out.println(simon.x);
		if(Quest.quests[Quest.TUTORIAL]==2 && simon.x == 1660)
		{
			if(Global.talking == 0)
			{
				Global.talkingTo = this;
				Global.talking = 1;
				SpeechBubble.talk("Hi Dad! Oh, Mommy wants you to take me to get some purple/apples at the purple apple tree? I wouldn't go there if I were you.../there's a mean lizard kid there. He yelled at me to go away.");
			}
			if(Global.talking == 2)
			{
				Global.talkingTo = this;
				Global.talking = 3;
				SpeechBubble.talk("But if you still wanna go, you'll need to jump over this rock./I was told by my friend that pressing 'Z' made you jump./I don't know what she meant by that.");
			}
			if(Global.talking == 4)
			{
				Global.zPressed = false;
				Global.talking = 5;
			}
			if(Global.talking == 5)
			{

				Global.talking = 0;
				Global.disableMovement = false;
				if(Quest.quests[Quest.TUTORIAL] == 2)
				{
					Quest.quests[Quest.TUTORIAL] = 3;
				}
			}
		}
		if(Quest.quests[Quest.TUTORIAL]==3)
		{
			if(!Global.disableMovement && simon.x >= 3088 && Global.talking == 0)
			{
				Global.disableMovement = true;
				this.flipped = false;
				hVelocity = 8;
				currentAnimation = OverworldAnimation.suzyWalk;
				this.x = 2448;
			}
			if(this.x >= 2992)
			{
				hVelocity = 0;
				if(Global.talking == 0)
				{
					simon.flipped = true;
					Global.talkingTo = this;
					Global.talking = 1;
					SpeechBubble.talk("Be sure to watch out for Ursears on this path!/They're even meaner than the lizard kid that I met at the purple apple/tree. That means that their mean-ness levels are infinity!");
				}
				if(Global.talking == 2)
				{
					Quest.quests[Quest.TUTORIAL] = 4;
					Global.talking = 0;
				}
			}
		}
		if(Quest.quests[Quest.TUTORIAL] == 4)
		{

			if(!ursearVisible)
			{
				AudioHandler.playMusic(AudioHandler.evilShuffle);
				ursearVisible = true;
				Global.disableMovement = true;
				ursearX = this.x+720;
				ursearAnimation = BattleAnimation.ursearWalkAnimation;
			}
			else
			{
				if(ursearX>this.x+256)
				{
					ursearX -= 10;
				}
				else if(Global.talking == 0)
				{
					if(ursearAnimation == BattleAnimation.ursearWalkAnimation)
					{
						setFrame(1,0);
					}
					ursearAnimation = OverworldAnimation.ursearTalk;
					Global.talkingTo = this;
					Global.talking = 1;
					simon.flipped = false;
					SpeechBubble.talk("FILTHY HUMAN!!! This planet is for the Ursears!/I mean, we're meaner and tougher than you guys!/And survival of the fittest means that I GET TO DESTROY YOU!/Man, I love Darwin!...whoever that is...BUT HE'S PROBABLY AN URSEAR!!!");
				}
				else if(Global.talking == 2)
				{
					if(currentAnimation == OverworldAnimation.suzyIdle)					{
						setFrame(0,0);
					}
					ursearAnimation = BattleAnimation.ursearIdleAnimation;
					currentAnimation = OverworldAnimation.suzyScared;
					Global.talkingTo = this;
					Global.talking = 3;
					SpeechBubble.talk("Save me Daddy! I don't wanna die!/What, you don't know how to battle properly?/*sigh* Guess I'll have to tell you.../I'm too young to become an orphan!");
				}
				else if(Global.talking == 4)
				{
					Global.tutorialBattle = true;
					Global.tutorialBattlePhase = 0;
					window.battleMode.addMonster(new Ursear(window,720,320,window.battleMode));
					AudioHandler.playMusic(AudioHandler.battleMusic);
					window.battleMode.startBattle();
					Fade.run(window, ModeType.Battle,true);
					Global.talking = 0;
					Global.disableMovement = false;
					ursearTime = 0;
				}
			}
		}

		if(Quest.quests[Quest.TUTORIAL]==5)
		{
			if(Global.talking == 0)
			{
				Global.talkingTo = this;
				Global.talking = 1;
				SpeechBubble.talk("Thank you for defeating that Ursear!/There are more up ahead though, so be careful!");
			}
			if(Global.talking == 2)
			{

				Global.talking = 0;
				Global.disableMovement = false;
				Quest.quests[Quest.TUTORIAL] = 6;
			}
		}

		if(Quest.quests[Quest.TUTORIAL]==6)
		{
			if(!Global.disableMovement && simon.x >= 5600 && Global.talking == 0)
			{
				Global.disableMovement = true;
				this.flipped = false;
				hVelocity = 8;
				currentAnimation = OverworldAnimation.suzyWalk;
				this.x = 4800;
				simon.x = 5590;
			}
			if(this.x >= 5500)
			{
				hVelocity = 0;
				if(Global.talking == 0)
				{
					simon.flipped = true;
					Global.talkingTo = this;
					Global.talking = 1;
					SpeechBubble.talk("Don't fall in the bottomless pit! If you fall in it, you/will respawn at your last warp point./Right now, the last warp point is just outside our house.");
				}
				if(Global.talking == 2)
				{
					Quest.quests[Quest.TUTORIAL] = 7;
					Global.talking = 0;
					Global.disableMovement = false;
				}
			}
		}
		
	}
	@Override
	public void tick() 
	{

		if(Quest.quests[Quest.LOMOBANK] == 1)
		{
			om.objects.add(new WilliamE2(window, 1900, 1350, om));
		}
		if(Quest.quests[Quest.TUTORIAL]>8)
		{
			om.objects.remove(this);
		}
		runTutorial();
		//continuePath();
	
		if(ursearTime>-1)
		{
			ursearTime++;
			if(ursearTime>5)
			{
				ursearVisible = false;
				ursearTime = -1;
				AudioHandler.playMusic(AudioHandler.prehistoricMusic);
			}
		}
		if(Global.talkingTo == this && Global.talking != 0 && !ursearVisible)
		{
			currentAnimation = OverworldAnimation.suzyTalk;
		}
		else if(hVelocity == 0 && Global.talking == 0)
		{
			currentAnimation = OverworldAnimation.suzyIdle;
		}
		if(Global.talkingTo == this && Global.talking == 2 && Quest.quests[Quest.TUTORIAL]==2)
		{
			if(Quest.quests[Quest.TUTORIAL]==3)
			{
				Global.talkingTo = null;
				Global.talking = 0;
				Global.disableMovement = false;
			}
		}
		if(Global.cPressed && Global.talking == 0 && distanceTo(ObjectId.Player)<150)
		{
			Global.talkingTo = this;
			Global.talking = 1;
			if(Quest.quests[Quest.TUTORIAL]==3)
			{
				normalTalk = true;
				SpeechBubble.talk("If you still wanna go, you'll need to jump over this rock./I was told by my friend that pressing 'Z' made you jump./I don't know what she meant by that.");
			}
			if(Quest.quests[Quest.TUTORIAL]==6)
			{
				normalTalk = true;
				SpeechBubble.talk("Thank you for defeating that Ursear!/There are more up ahead though, so be careful!");
			}
			if(Quest.quests[Quest.TUTORIAL]==7)
			{
				normalTalk = true;
				SpeechBubble.talk("Don't fall in the bottomless pit! If you fall in it, you/will respawn at your last warp point./Right now, the last warp point is just outside our house.");
			}
		}
		
		if(Quest.quests[Quest.TUTORIAL] == 3 && Global.talking >= 2 && Global.talkingTo == this && normalTalk)
		{
			normalTalk = false;
			Global.talking = 0;
			Global.talkingTo = null;
			Global.disableMovement = false;
			Global.zPressed = false;
		}
		if(Quest.quests[Quest.TUTORIAL] >= 6 && Global.talking >= 2 && Global.talkingTo == this && normalTalk)
		{
			normalTalk = false;
			Global.talking = 0;
			Global.talkingTo = null;
			Global.disableMovement = false;
			Global.zPressed = false;
		}
		if(hPath[pathStage] != 99)
		{
			hVelocity = hPath[pathStage];
		}

		x+=hVelocity;
		y+=vVelocity;
		vVelocity+=2;
		System.out.println(x);
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
			{
				Grass g = (Grass)window.overworldMode.objects.get(i);
	
				if(g.getBounds().intersects(topRectangle())) {
					if(vVelocity<0)
					{
						vVelocity = 0;
					}
				}
				if(g.getBounds().intersects(bottomRectangle()) && vVelocity>0)
				{
					vVelocity = 0;
					this.y = g.y-130;
				}
			}
		}
	}
}
