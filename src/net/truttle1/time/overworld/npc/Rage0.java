package net.truttle1.time.overworld.npc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.blocks.Warp;

public class Rage0 extends NPC{

	private int stage = 0;
	private BufferedImage[] skrapps;
	private BufferedImage[] boulder0;
	private int b0y = 0;
	private BufferedImage[] boulder1;
	private int b1y = 0;
	private boolean skrappsFlipped;
	private int skrappsX;
	private GameObject player;
	private boolean showingMoney;
	private int origX;
	public Rage0(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.rageIdle4;
		this.id = ObjectId.NPC;
		this.flipped = false;
	}
	
	@Override
	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+60,y+280,100,50);
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
		return new Rectangle(x+38,y+100,120,185);
	}

	@Override
	public void render(Graphics g) {
		if(skrapps != null)
		{
			if(getFrame(1)>getAnimationLength(skrapps)-1)
			{
				setFrame(1,0);
			}
			try
			{
				if(skrappsFlipped)
				{
					this.animateFlip(skrappsX, y+120, skrapps, 1, g);
				}
				else
				{
					this.animateNoFlip(skrappsX, y+120, skrapps, 1, g);
				}
			}
			catch(Exception e)
			{
				setFrame(1,0);
				if(skrappsFlipped)
				{
					this.animateFlip(skrappsX, y+125, skrapps, 1, g);
				}
				else
				{
					this.animateNoFlip(skrappsX, y+125, skrapps, 1, g);
				}
			}
		}
		if(this.getFrame(0)>this.getAnimationLength(currentAnimation))
		{
			this.setFrame(0, 0);
		}

		if(stage >= 12)
		{
			this.setFrame(2,0);
			this.setFrame(3,0);
			this.animate(11400, b0y, OverworldAnimation.boulderThin, 2, g);
		}
		if(stage >= 13)
		{
			this.animate(11950, b1y, OverworldAnimation.boulderThin, 3, g);
		}
		this.animate(x, y, currentAnimation, 0, g);
		if(showingMoney)
		{
			g.setFont(Global.winFont2);

			g.setColor(Color.gray.darker());
			g.drawString("$" + Global.money, this.x+124, this.y+384);
			g.setColor(Color.white);
			g.drawString("$" + Global.money, this.x+120, this.y+380);
		}
		if(Global.talking == 7 && stage == 2)
		{
			g.drawImage(Store.keyItemImage[Store.PPADLOCK],this.x+100,this.y-20, null);
		}
		if(stage == 9)
		{
			g.drawImage(Store.keyItemImage[Store.PPADLOCK],player.x+30,player.y-75, null);
			
		}
	}
	@Override
	public void tick()
	{
		if(player == null || !om.objects.contains(player))
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i).id == ObjectId.Player)
				{
					player = om.objects.get(i);
					break;
				}
			}
		}
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
			{
				Grass g = (Grass)window.overworldMode.objects.get(i);

				if(g.getBounds().intersects(leftRectangle())) {
					if(hVelocity<0)
					{
						hVelocity = -hVelocity;
					}
				}

				if(g.getBounds().intersects(rightRectangle())) {
					if(hVelocity>0)
					{
						hVelocity = -hVelocity;
					}
				}
				if(g.getBounds().intersects(topRectangle())) {
					if(vVelocity<0)
					{
						vVelocity = 0;
					}
				}
				if(g.getBounds().intersects(bottomRectangle()) && vVelocity>0)
				{
					vVelocity = 0;
					this.y = g.y-250;
				}
			}
		}
		talk();
		x+=hVelocity;
		y+=vVelocity;
		vVelocity += 2;
	}

	private void talk()
	{
		if(Quest.quests[Quest.PYRUZ_W] == 8)
		{
			if(stage == 0)
			{
				if(player.x>=this.x-250 && player.y >= this.y-200 && player.x <= this.x+600 && player.y <= this.y+600)
				{
					System.out.println(stage + "::" + Global.talking);
					if(Global.talking == 0)
					{
						om.ty = this.y-800;
						//AudioHandler.playMusic(AudioHandler.cutscene2);
						Global.disableMovement = true;
						Global.talking = 1;
						Global.talkingTo = this;
						player.hVelocity = 0;
						if(Quest.quests[Quest.MEETRAGE] == 0)
						{

							SpeechBubble.talk("Hey there, the name's Rage! First things first: I have no interest in/harming you like some of the other guys in here! Second, I am actually/considering helping anyone who is trying to rescue all those poor/innocent humans that Ignacio locked up.");
							currentAnimation = OverworldAnimation.rageTalk;
							player.currentAnimation = BattleAnimation.williamIdle3;
						}
						else
						{
							Global.talking = 2;
						}
					}
					if(Global.talking == 2 && Global.talkingTo == this)
					{
						currentAnimation = OverworldAnimation.rageIdle;
						Global.talking = 0;
						stage++;
						skrappsX = player.x;
					}
				}
			}
			if(stage == 1)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				currentAnimation = OverworldAnimation.rageIdle;
				skrapps = BattleAnimation.skrappsRun;
				skrappsFlipped = true;
				skrappsX += 8;
				if(skrappsX>=player.x+175)
				{
					stage++;
				}
			}
			if(stage == 2)
			{
				if(Global.talking == 0)
				{
					om.ty = this.y-800;
					//AudioHandler.playMusic(AudioHandler.cutscene2);
					Global.disableMovement = true;
					Global.talking = 1;
					Global.talkingTo = this;
					if(Quest.quests[Quest.MEETRAGE]==0)
					{
						SpeechBubble.talk("Hey, that's exactly what we're here to do! Carne told us all about you!/What types of spells are you able to teach? I WANNA KNOW 'EM ALL!!!",Global.skrappsFont);
						skrapps = BattleAnimation.skrappsTalk;
						currentAnimation = OverworldAnimation.rageIdle;
						player.currentAnimation = BattleAnimation.williamIdle3;
						
					}
					else
					{
						Global.talking = 10;
					}
				}
				if(Global.talking == 2 && Global.talkingTo == this)
				{
					om.ty = this.y-800;
					//AudioHandler.playMusic(AudioHandler.cutscene2);
					Global.disableMovement = true;
					Global.talking = 3;
					Global.talkingTo = this;
					skrappsFlipped = false;
					SpeechBubble.talk("Skrapps: BE CAREFUL! What about this guy makes him seem trustworthy?/Like I said, his name is RAGE! Also, how do you know that he isn't trying to trick us!",Global.willFont);
					skrapps = BattleAnimation.skrappsIdle2;
					currentAnimation = OverworldAnimation.rageIdle;
					player.currentAnimation = BattleAnimation.williamTalk;
				
				}
				if(Global.talking == 4 && Global.talkingTo == this)
				{
					om.ty = this.y-800;
					//AudioHandler.playMusic(AudioHandler.cutscene2);
					Global.disableMovement = true;
					Global.talking = 5;
					Global.talkingTo = this;
					SpeechBubble.talk("Green dude, tell me something: What did I do to warrent you being/so rude? I mean, I didn't get to pick my own name, so why use/it as a reason to not trust me? But it seems like you drive a hard/bargain, so I'll throw in this as well if you buy my spell:");
					skrappsFlipped = true;
					skrapps = BattleAnimation.skrappsIdle2;
					currentAnimation = OverworldAnimation.rageTalk1;
					player.currentAnimation = BattleAnimation.williamIdle3;
				}
				if(Global.talking == 6 && Global.talkingTo == this)
				{
					om.ty = this.y-800;
					//AudioHandler.playMusic(AudioHandler.cutscene2);
					Global.disableMovement = true;
					Global.talking = 7;
					Global.talkingTo = this;
					skrappsFlipped = false;
					SpeechBubble.talk("Wha? The purple padlock!?!? I kinda need that! Give it here!",Global.willFont);
					skrapps = BattleAnimation.skrappsIdle2;
					currentAnimation = OverworldAnimation.rageItem;
					player.currentAnimation = BattleAnimation.williamTalkPoint;
				}
				if(Global.talking == 8 && Global.talkingTo == this)
				{
					om.ty = this.y-800;
					//AudioHandler.playMusic(AudioHandler.cutscene2);
					Global.disableMovement = true;
					Global.talking = 11;
					Global.talkingTo = this;
					SpeechBubble.talk("Well, if your little friend buys my spell, it's all yours. Since I am a/pacifist, my spells are focused on helping yourself rather than/harming others. The spell that I am currently offering is \"Heal.\"/It allows the user to restore 7 HP to himself or a friend for 3 SP.");
					skrappsFlipped = true;
					skrapps = BattleAnimation.skrappsIdle2;
					currentAnimation = OverworldAnimation.rageTalk;
					player.currentAnimation = BattleAnimation.williamIdle3;
				}
				if(Global.talking == 10 && Global.talkingTo == this)
				{
					om.ty = this.y-800;
					//AudioHandler.playMusic(AudioHandler.cutscene2);
					Global.disableMovement = true;
					Global.talking = 11;
					Global.talkingTo = this;
					SpeechBubble.talk("Hey there! It looks like you're here to buy a spell! Since I am a/pacifist, my spells are focused on helping yourself rather than/harming others. The spell that I am currently offering is \"Heal.\"/It allows the user to restore 5 HP to himself or a friend for 3 SP.");
					skrappsFlipped = true;
					skrapps = BattleAnimation.skrappsIdle2;
					currentAnimation = OverworldAnimation.rageTalk;
					player.currentAnimation = BattleAnimation.williamIdle3;
				}
				if(Global.talking == 12 && Global.talkingTo == this)
				{
					om.ty = this.y-800;
					//AudioHandler.playMusic(AudioHandler.cutscene2);
					Global.disableMovement = true;
					Global.talking = 13;
					Global.talkingTo = this;
					skrappsFlipped = true;
					skrapps = BattleAnimation.skrappsIdle2;
					currentAnimation = OverworldAnimation.rageTalk;
					player.currentAnimation = BattleAnimation.williamIdle3;
					showingMoney = true;
					SpeechBubble.yesNo("Normally, \"Heal\" sells for $120, but I'm feeling generous, so I will/be selling it to you guys for just $30! And you get a padlock/with the spell! You don't find deals like this every day, so/whaddaya say?", Global.textFont);
				}
				if(Global.talking == 14 && Global.talkingTo == this)
				{
					if(SpeechBubble.selection == 0)
					{
						if(Global.money>=30)
						{

							currentAnimation = OverworldAnimation.rageTalk;
							SpeechBubble.talk("Okay then! Blue dude, you come with me, it will only take about/a couple minutes for you to fully master this skill. Green dude/since I know you don't trust me all that much, you can come/along and watch.",Global.textFont);
							Global.money -= 30;
							Global.talking = 17;
						}
						else
						{
							Global.talking = 15;
							currentAnimation = OverworldAnimation.rageTalk1;
							SpeechBubble.talk("Sorry dudes, but I don't think you have enough money...come back when/you happen to get some more. Again, sorry, but I can't/just give away stuff like this for free...",Global.textFont);
						
						}
					
					}
					else
					{
						showingMoney = false;
						Global.talking = 15;
						currentAnimation = OverworldAnimation.rageTalk1;
						SpeechBubble.talk("Well, see ya around later then. Remember, if you buy this spell, you get/this sweet padlock!",Global.textFont);
					}
				}
				if(Global.talking == 16)
				{
					
					showingMoney = false;
					stage = 3;
					Global.talking = 0;
				}
				if(Global.talking == 18)
				{
					
					showingMoney = false;
					stage = 4;
					Global.talking = 0;
					origX = player.x;
					Fade.run(window, ModeType.Overworld, false, 5);
				}
			}
				
		}
		if(stage == 3)
		{
			currentAnimation = OverworldAnimation.rageIdle;
			if(skrappsX>player.x && skrapps != null)
			{
				skrappsFlipped = false;
				skrappsX -= 10;
				skrapps = BattleAnimation.skrappsRun;
			}
			else
			{
				skrapps = null;
				player.currentAnimation = BattleAnimation.williamWalk4;
				skrappsX -= 8;
				player.x -= 8;
				player.flipped = false;
				if(player.x<this.x-350)
				{
					stage = 0;
					Global.talking = 0;
					Global.disableMovement = false;
					Quest.quests[Quest.MEETRAGE] = 1;
					Global.talkingTo = null;
				}
			}
		}
		if(stage == 4)
		{
			if(Fade.fadeIn)
			{
				stage++;
			}
			player.currentAnimation = BattleAnimation.williamWalk4;
			currentAnimation = OverworldAnimation.rageWalk;
			skrapps = BattleAnimation.skrappsRun;
			this.flipped = true;
			player.flipped = true;
			skrappsFlipped = true;
			player.x += 8;
			this.x += 8;
			skrappsX += 8;
			
		}
		if(stage == 5)
		{

			player.currentAnimation = BattleAnimation.williamWalkAnimation;
			currentAnimation = OverworldAnimation.rageWalk;
			skrapps = BattleAnimation.skrappsRun;
			this.flipped = false;
			player.flipped = false;
			skrappsFlipped = false;
			player.x -= 8;
			this.x -= 8;
			skrappsX -= 8;
			if(player.x<=origX)
			{
				stage = 6;
			}
		}
		if(stage == 6)
		{
			if(Global.talking == 0)
			{
				//AudioHandler.playMusic(AudioHandler.cutscene2);
				player.flipped = true;
				skrappsFlipped = true;
				Global.disableMovement = true;
				Global.talking = 1;
				Global.talkingTo = this;
				SpeechBubble.talk("SKRAPPS HAS LEARNED THE SPECIAL ATTACK \"HEAL\"!/It can be used in battle to restore HP to party members!");
				skrapps = BattleAnimation.skrappsIdleBattle;
				currentAnimation = OverworldAnimation.rageIdle3;
				player.currentAnimation = BattleAnimation.williamIdle2;
			}
			if(Global.talking == 2)
			{
				//AudioHandler.playMusic(AudioHandler.cutscene2);
				player.flipped = true;
				skrappsFlipped = false;
				Global.disableMovement = true;
				Global.talking = 3;
				Global.talkingTo = this;
				SpeechBubble.talk("YIPPEE!!! Now I can heal myself without needing to purchase expensive/Tenderlomos! Man, being a carnivore is rough sometimes! Delicious, but rough.",Global.skrappsFont);
				skrapps = BattleAnimation.skrappsTalkStick;
				currentAnimation = OverworldAnimation.rageIdle3;
				player.currentAnimation = BattleAnimation.williamIdle2;
			}
			if(Global.talking == 4)
			{
				//AudioHandler.playMusic(AudioHandler.cutscene2);
				player.flipped = true;
				skrappsFlipped = false;
				Global.disableMovement = true;
				Global.talking = 5;
				Global.talkingTo = this;
				SpeechBubble.talk("Gee, now I feel bad for being so distrusting of Rage...",Global.willFont);
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageIdle3;
				player.currentAnimation = BattleAnimation.williamTalk3;
			}
			if(Global.talking == 6)
			{
				//AudioHandler.playMusic(AudioHandler.cutscene2);
				player.flipped = true;
				skrappsFlipped = true;
				Global.disableMovement = true;
				Global.talking = 7;
				Global.talkingTo = this;
				SpeechBubble.talk("Don't feel bad, dude, it's okay to be skeptical. I mean, take a look at me,/Me and Carne are the only ones here who distrust Ignacio, and with/good reason, he's kidnapping an entire village. I even kinda look like one/of his cronies, so I understand where you're coming from, green dude.");
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
			}
			if(Global.talking == 8)
			{
				//AudioHandler.playMusic(AudioHandler.cutscene2);
				player.flipped = true;
				skrappsFlipped = true;
				Global.disableMovement = true;
				Global.talking = 9;
				Global.talkingTo = this;
				SpeechBubble.talk("Also, green dude, one more thing, I think I owe you this now...");
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
			}
			if(Global.talking == 10)
			{
				startX = this.x;
				stage = 7;
			}
			/*
			if(Global.talking == 20)
			{
				//AudioHandler.playMusic(AudioHandler.cutscene2);
				player.flipped = true;
				skrappsFlipped = true;
				Global.disableMovement = true;
				Global.talking = 9;
				Global.talkingTo = this;
				SpeechBubble.talk("Thanks for the spell Rage! Take care!",Global.skrappsFont);
				skrapps = BattleAnimation.skrappsTalkStick;
				currentAnimation = OverworldAnimation.rageIdle3;
				player.currentAnimation = BattleAnimation.williamIdle2;
			}
			if(Global.talking == 22)
			{
				//AudioHandler.playMusic(AudioHandler.cutscene2);
				player.flipped = true;
				skrappsFlipped = true;
				Global.disableMovement = true;
				Global.talking = 11;
				Global.talkingTo = this;
				SpeechBubble.talk("See ya round, you two!");
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
			}
			if(Global.talking == 24)
			{
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageIdle;
				player.currentAnimation = BattleAnimation.williamIdle2;
				Global.talking = 0;
				stage = 7;
			}*/
		}
		if(stage == 7)
		{
			Global.talking = 0;
			currentAnimation = OverworldAnimation.rageWalk;
			this.flipped = false;
			this.x -= 8;
			if(this.x<=player.x+80)
			{
				this.setFrame(0, 0);
				currentAnimation = OverworldAnimation.rageGive;
				stage = 8;
			}
		}
		if(stage == 8)
		{
			this.flipped = false;
			if(this.getFrame(0)>=47)
			{
				this.setFrame(0, 0);
				stage = 9;
			}
		}
		if(stage == 9)
		{

			if(Global.talking == 0)
			{
				//AudioHandler.playMusic(AudioHandler.cutscene2);
				Global.talking = 1;
				Global.talkingTo = this;
				SpeechBubble.talk("Rage has given William the Purple Padlock!/William added it to his Key Items.");
				Global.keyItems[Store.PPADLOCK]++;
				skrapps = BattleAnimation.skrappsIdleBattle;
				currentAnimation = OverworldAnimation.rageIdle;
				player.currentAnimation = BattleAnimation.williamItem;
			}
			if(Global.talking >= 2)
			{
				stage = 10;
				Global.talking = 0;
			}
		}
		if(stage == 10)
		{
			currentAnimation = OverworldAnimation.rageWalk;
			this.flipped = true;
			this.x += 8;
			if(this.x>=startX)
			{
				stage = 11;
			}
		}
		if(stage == 11)
		{
			if(Global.talking == 0)
			{
				//AudioHandler.playMusic(AudioHandler.cutscene2);
				player.flipped = true;
				skrappsFlipped = true;
				this.flipped = false;
				Global.disableMovement = true;
				Global.talking = 1;
				Global.talkingTo = this;
				SpeechBubble.talk("Thanks for the spell Rage! Take care!",Global.skrappsFont);
				skrapps = BattleAnimation.skrappsTalkStick;
				currentAnimation = OverworldAnimation.rageIdle;
				player.currentAnimation = BattleAnimation.williamIdle2;
			}
			if(Global.talking == 2)
			{
				//AudioHandler.playMusic(AudioHandler.cutscene2);
				player.flipped = true;
				skrappsFlipped = true;
				Global.disableMovement = true;
				Global.talking = 3;
				Global.talkingTo = this;
				SpeechBubble.talk("See ya round, you two! And good luck!");
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
			}
			if(Global.talking == 4)
			{
				Global.disableMovement = true;
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageIdle;
				player.currentAnimation = BattleAnimation.williamIdle2;
				Global.talking = 0;
				stage = 12;
				b0y = player.y - 1200;
				b1y = player.y - 1200;
			}
		}
		if(stage == 12)
		{
			player.currentAnimation = BattleAnimation.williamWalkAnimation;
			skrapps = BattleAnimation.skrappsRun;
			player.flipped = false;
			skrappsFlipped = false;
			player.x -= 8;
			skrappsX -= 8;
			b0y += 50;
			System.out.println(b0y);
			if(b0y>=2900)
			{
				stage = 13;
				b0y = 2900;
			}
		}
		if(stage == 13)
		{
			player.flipped = true;
			skrappsFlipped = true;
			player.currentAnimation = BattleAnimation.williamIdle3;
			skrapps = BattleAnimation.skrappsIdle2;

			b1y += 60;
			System.out.println(b1y);
			if(b1y>=2900)
			{
				stage = 14;
				b1y = 2900;
			}
		}
		if(stage == 14)
		{

			if(Global.talking == 0)
			{
				player.flipped = true;
				skrappsFlipped = true;
				Global.disableMovement = true;
				Global.talking = 1;
				Global.talkingTo = this;
				SpeechBubble.talk("See, I knew this guy couldn't be trusted!!",Global.willFont);
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageIdle1;
				player.currentAnimation = BattleAnimation.williamTalk;
			}
			if(Global.talking == 2)
			{
				player.flipped = true;
				skrappsFlipped = true;
				Global.disableMovement = true;
				Global.talking = 3;
				Global.talkingTo = this;
				SpeechBubble.talk("Green dude, I know this may be difficult to believe, but this was NOT/a setup! Besides, why would I actually give you that padlock if this was/a setup? Anyway, I'll see if I can let you two little guys outta there.");
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageTalk1;
				player.currentAnimation = BattleAnimation.williamIdle3;
			}
			if(Global.talking == 4)
			{
				stage = 15;
				setFrame(0,0);
				currentAnimation = OverworldAnimation.ragePunch;
				Global.talking = 0;
			}
			
		}
		if(stage == 15)
		{
			if(getFrame(0)>=47)
			{
				setFrame(0,0);
				currentAnimation = OverworldAnimation.rageIdle1;
				stage = 16;
			}
		}
		if(stage == 16)
		{
			if(Global.talking == 0)
			{
				player.flipped = true;
				skrappsFlipped = true;
				Global.disableMovement = true;
				Global.talking = 1;
				Global.talkingTo = this;
				SpeechBubble.talk("Sorry dudes, no luck. Actually, I think one of the cavemen in the prison/has a club. A club can do a much better job at breaking stone than one/of my weak little punches. Luckily, my punches can break Ignacio's cages, so I guess/I'll go free that one caveman and have him rescue you.");
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageTalk1;
				player.currentAnimation = BattleAnimation.williamIdle3;
			}
			if(Global.talking == 2)
			{
				Global.disableMovement = true;
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageIdle;
				player.currentAnimation = BattleAnimation.williamIdle3;
				Global.talking = 0;
				stage = 17;
			}
		}
		if(stage == 17)
		{
			currentAnimation = OverworldAnimation.rageWalk;
			flipped = true;
			x += 8;
			if(x>startX+400)
			{
				stage++;
			}
		}
		if(stage == 18)
		{
			if(Global.talking == 0)
			{
				player.flipped = true;
				skrappsFlipped = false;
				Global.disableMovement = true;
				Global.talking = 1;
				Global.talkingTo = this;
				SpeechBubble.talk("Well this sucks!!!");
				skrapps = BattleAnimation.skrappsIdle2;
				player.currentAnimation = BattleAnimation.williamTalk;
			}
			if(Global.talking == 2)
			{
				Global.disableMovement = true;
				skrapps = BattleAnimation.skrappsIdle2;
				currentAnimation = OverworldAnimation.rageIdle;
				player.currentAnimation = BattleAnimation.williamIdle3;
				Global.talking = 0;
				stage = 19;
			}
		}
		if(stage == 19)
		{
			Warp tWarp = new Warp(window,0,0,om,0);
			tWarp.setWarp(0, 0, OverworldMode.pyruzPrison, true,AudioHandler.evilShuffle);
			om.objects.add(tWarp);
			tWarp.forceWarp();
			Quest.quests[Quest.PYRUZ_W]++;
		}
	}
}
