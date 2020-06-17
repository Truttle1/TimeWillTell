package net.truttle1.time.overworld.npc.carl;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.npc.NPC;

public class Carl4 extends Carl{

	int side = 0;
	int boatX = 0;
	GameObject player;
	boolean riding = false;
	boolean right = true;
	BufferedImage[] boat;
	boolean onBoat;
	int boatTime = 0;
	boolean doneRiding;
	boolean boatFlipped;
	public Carl4(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.carlIdle;
		this.id = ObjectId.ForegroundNPC;
		boat = OverworldAnimation.boat;
	}
	
	@Override
	public void tick()
	{
		if(Global.enableGravity)
		{
			super.tick();
		}
		if(Quest.quests[Quest.LOMO]<8)
		{
			om.objects.remove(this);
		}
		for(int i=0; i<om.objects.size();i++)
		{
			if(om.objects.get(i) instanceof WilliamPlayer || om.objects.get(i) instanceof SimonPlayer)
			{
				player = om.objects.get(i);
			}
		}
		if(player != null)
		{
			if(!riding)
			{

				if(player.x>16300)
				{
					boatFlipped = true;
					this.flipped = true;
					this.x = 17400;
					this.boatX = this.x-680;
					if(player.x<=this.x+200 || Global.talkingTo == this)
					{
						if(Global.talking == 0)
						{
							currentAnimation = OverworldAnimation.carlTalk;
							SpeechBubble.talk("Hey there! Do you like my boat? I bought it in the Digital Age!/Creaturey told me that I would need to ferry you across/this Poisonous Lake whenever you had the desire to cross it...", Global.carlFont);
							player.hVelocity = 0;
							player.currentAnimation = BattleAnimation.williamIdle2;
							Global.talkingTo = this;
							Global.disableMovement = true;
							Global.talking = 1;
						}
						if(Global.talking == 2)
						{
							currentAnimation = OverworldAnimation.carlTalk;
							SpeechBubble.yesNo("I'm ready to go if you want...Of course, I can't FORCE you to ride/my boat, why, that would be called ILLEGAL!...are there laws here?/I dunno...and I'm getting off topic...Look, let's get right/to the point here, do you want a ride?", Global.carlFont);
							player.hVelocity = 0;
							player.currentAnimation = BattleAnimation.williamIdle2;
							Global.talkingTo = this;
							Global.disableMovement = true;
							Global.talking = 3;
						}
						if(Global.talking == 4)
						{
							if(SpeechBubble.selection == 1)
							{
								currentAnimation = OverworldAnimation.carlTalk;
								SpeechBubble.talk("Wow dude, I offer you a free ride across this lake and you/REJECT IT!!! You hurt my feelings!!!...Actually, I'm just/kidding... Just come back when you're ready, and I'll take you across/then! Those Time Orbs seem really important, after all!", Global.carlFont);
								Global.talking = 98;
							}
							if(SpeechBubble.selection == 0)
							{
								currentAnimation = OverworldAnimation.carlTalk;
								SpeechBubble.talk("Well, off we go then! And good luck collecting that Time Orb!/Whatever it is that is jeopardizing the future cannot be stopped/without you! Yeah, you learn more about how you're saving the/world later...", Global.carlFont);
								Global.talking = 48;
							}
						}
						if(Global.talking == 6)
						{
							currentAnimation = OverworldAnimation.carlTalk;
							SpeechBubble.talk("Of course, me and Creaturey have our reasons for helping you,/but we don't want those to influence your actions...so I won't/spill those...but you'll find out eventually...We just don't want/anything irrisponsible to happen, that's all!", Global.carlFont);
							player.hVelocity = 0;
							player.currentAnimation = BattleAnimation.williamIdle2;
							Global.talkingTo = this;
							Global.disableMovement = true;
							Global.talking = 48;
						}

						if(Global.talking >= 49 && Global.talking <= 89)
						{
							Global.talking = 49;
							riding = true;
							right = false;
						}
						if(Global.talking >= 99)
						{
							player.flipped = true;
							player.x += 10;
							player.currentAnimation = BattleAnimation.williamWalkAnimation;
							if(player.x>this.x-500)
							{
								currentAnimation = OverworldAnimation.carlIdle;
								Global.talking = 0;
								Global.talkingTo = null;
								Global.disableMovement = false;
							}
						}
					}
				}
				else
				{
					boatFlipped = false;
					this.x = 14600;
					this.boatX = this.x+200;
					if(player.x>=this.x-150 || Global.talkingTo == this)
					{
						if(Global.talking == 0)
						{
							currentAnimation = OverworldAnimation.carlTalk;
							SpeechBubble.talk("Hey there! Do you like my boat? I bought it in the Digital Age!/Creaturey told me that I would need to ferry you across/this Poisonous Lake whenever you had the desire to cross it...", Global.carlFont);
							player.hVelocity = 0;
							player.currentAnimation = BattleAnimation.williamIdle2;
							Global.talkingTo = this;
							Global.disableMovement = true;
							Global.talking = 1;
						}
						if(Global.talking == 2)
						{
							currentAnimation = OverworldAnimation.carlTalk;
							SpeechBubble.yesNo("I'm ready to go if you want...Of course, I can't FORCE you to ride/my boat, why, that would be called ILLEGAL!...are there laws here?/I dunno...and I'm getting off topic...Look, let's get right/to the point here, do you want a ride?", Global.carlFont);
							player.hVelocity = 0;
							player.currentAnimation = BattleAnimation.williamIdle2;
							Global.talkingTo = this;
							Global.disableMovement = true;
							Global.talking = 3;
						}
						if(Global.talking == 4)
						{
							if(SpeechBubble.selection == 1)
							{
								currentAnimation = OverworldAnimation.carlTalk;
								SpeechBubble.talk("Wow dude, I offer you a free ride across this lake and you/REJECT IT!!! You hurt my feelings!!!...Actually, I'm just/kidding... Just come back when you're ready, and I'll take you across/then! Those Time Orbs seem really important, after all!", Global.carlFont);
								Global.talking = 98;
							}
							if(SpeechBubble.selection == 0)
							{
								currentAnimation = OverworldAnimation.carlTalk;
								SpeechBubble.talk("Well, off we go then! And good luck collecting that Time Orb!/Whatever it is that is jeopardizing the future cannot be stopped/without you! Yeah, you learn more about how you're saving the/world later...", Global.carlFont);
								Global.talking = 48;
							}
						}
						if(Global.talking == 6)
						{
							currentAnimation = OverworldAnimation.carlTalk;
							SpeechBubble.talk("Of course, me and Creaturey have our reasons for helping you,/but we don't want those to influence your actions...so I won't/spill those...but you'll find out eventually...We just don't want/anything irrisponsible to happen, that's all!", Global.carlFont);
							player.hVelocity = 0;
							player.currentAnimation = BattleAnimation.williamIdle2;
							Global.talkingTo = this;
							Global.disableMovement = true;
							Global.talking = 48;
						}

						if(Global.talking >= 49 && Global.talking <= 89)
						{
							Global.talking = 49;
							riding = true;
							right = true;
						}
						if(Global.talking >= 99)
						{
							player.flipped = false;
							player.x -= 10;
							player.currentAnimation = BattleAnimation.williamWalkAnimation;
							if(player.x<this.x-500)
							{
								currentAnimation = OverworldAnimation.carlIdle;
								Global.talking = 0;
								Global.talkingTo = null;
								Global.disableMovement = false;
							}
						}
					}
				}
				
			}

			if(doneRiding && right)
			{

				if(Global.talking == 0)
				{
					currentAnimation = OverworldAnimation.carlTalk;
					SpeechBubble.talk("See you around, William! Good luck on your quest! The future/depends on you!", Global.carlFont);
					//SpeechBubble.talk("See you around, William! Good luck on your quest! The future/depends on you!...of all people...seriously, who made THAT/mistake!?", Global.carlFont);
					player.hVelocity = 0;
					player.currentAnimation = BattleAnimation.williamIdle2;
					Global.talkingTo = this;
					Global.disableMovement = true;
					Global.talking = 1;
					player.flipped = false;
				}
				if(Global.talking == 2)
				{
					currentAnimation = OverworldAnimation.carlIdle;
					Global.talking = 0;
					doneRiding = false;
					riding = false;
					Global.disableMovement = false;
					Global.enableGravity = true;
					Global.talkingTo = null;
				}
			}
			if(doneRiding && !right)
			{

				if(Global.talking == 0)
				{
					currentAnimation = OverworldAnimation.carlTalk;
					SpeechBubble.talk("See you around, William!", Global.carlFont);
					player.hVelocity = 0;
					player.currentAnimation = BattleAnimation.williamIdle2;
					Global.talkingTo = this;
					Global.disableMovement = true;
					Global.talking = 1;
					player.flipped = true;
				}
				if(Global.talking == 2)
				{
					currentAnimation = OverworldAnimation.carlIdle;
					Global.talkingTo = null;
					Global.talking = 0;
					doneRiding = false;
					riding = false;
					Global.disableMovement = false;
					Global.enableGravity = true;
				}
			}
			if(riding && right && !doneRiding)
			{
				Global.enableGravity = false;
				Global.talking = 0;
				this.flipped = true;
				if(this.x<boatX+380)
				{
					
					boatTime = 0;
					currentAnimation = OverworldAnimation.carlWalk;
					player.currentAnimation = BattleAnimation.williamWalkAnimation;
					this.x+=8;
					player.x+=8;
					window.overworldMode.tx = player.x-400;
				}
				else if(this.x<17100)
				{
					currentAnimation = OverworldAnimation.carlIdle;
					player.currentAnimation = BattleAnimation.williamIdle2;
					boatTime++;
					if(boatTime>15)
					{
						this.x+=4;
						player.x+=4;
						boatX+=4;
					}
					window.overworldMode.tx = player.x-400;
				}
				else if(this.x<17400)
				{
					currentAnimation = OverworldAnimation.carlWalk;
					player.currentAnimation = BattleAnimation.williamWalkAnimation;
					
					this.x+=4;
					player.x+=9;
					window.overworldMode.tx = player.x-400;
				}
				else
				{
					currentAnimation = OverworldAnimation.carlIdle;
					player.currentAnimation = BattleAnimation.williamIdle2;
					this.setFrame(0, 0);
					doneRiding = true;
				}
			}
			else if(riding && !right && !doneRiding)
			{
				Global.enableGravity = false;
				Global.talking = 0;
				this.flipped = false;
				if(this.x>boatX+20)
				{
					
					boatTime = 0;
					currentAnimation = OverworldAnimation.carlWalk;
					player.currentAnimation = BattleAnimation.williamWalkAnimation;
					this.x-=8;
					player.x-=8;
					window.overworldMode.tx = player.x-400;
				}
				else if(this.x>14800)
				{
					currentAnimation = OverworldAnimation.carlIdle;
					player.currentAnimation = BattleAnimation.williamIdle2;
					boatTime++;
					if(boatTime>15)
					{
						this.x-=4;
						player.x-=4;
						boatX-=4;
					}
					window.overworldMode.tx = player.x-400;
				}
				else if(this.x>14600)
				{
					currentAnimation = OverworldAnimation.carlWalk;
					player.currentAnimation = BattleAnimation.williamWalkAnimation;
					
					this.x-=4;
					player.x-=12;
					window.overworldMode.tx = player.x-400;
				}
				else
				{
					currentAnimation = OverworldAnimation.carlIdle;
					player.currentAnimation = BattleAnimation.williamIdle2;
					this.setFrame(0, 0);
					doneRiding = true;
				}
			}
			if(Global.talkingTo != null && Global.talkingTo.equals(this))
			{
				Global.disableMovement = true;
			}
		}
	}
	@Override
	public void render(Graphics g) {
		if(this.getFrame(0)>=currentAnimation[2].getWidth())
		{
			this.setFrame(0, 0);
		}
		this.animate(x, y, currentAnimation, 0, g);
		boolean f = flipped;
		if(right)
		{
			flipped = true;
		}
		else
		{
			flipped = false;
		}
		if(!boatFlipped)
		{

			this.animateNoFlip(this.boatX, this.y-100, boat, 1, g);
		}
		else
		{
			this.animateFlip(this.boatX, this.y-100, boat, 1, g);
			
		}
		flipped = f;
		
	}
}
