package net.truttle1.time.overworld.npc.creaturey;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;
import net.truttle1.time.overworld.npc.carl.Carl2;

public class Creaturey2 extends Creaturey{

	GameObject player;
	Carl2 carl;
	int translateTo;
	int stage;
	public Creaturey2(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.creatSadIdle2;
		this.id = ObjectId.NPC;
		this.flipped = true;
	}

	@Override 
	public int distanceTo(ObjectId id)
	{
		for(int i=0; i<om.objects.size();i++)
		{
			if(om.objects.get(i).id == id)
			{
				GameObject temp = om.objects.get(i);
				int distX = (int) Math.pow(Math.abs((this.x+100)-temp.x),2);
				int distY = (int) Math.pow(Math.abs(this.y-temp.y),2);
				return (int) Math.sqrt(distX+distY);
			}
		}
		return 9999;
	}

	@Override
	public void render(Graphics g) {

		if(distanceTo(ObjectId.Player)<300 && Global.talking == 0 && !Global.disableMovement)
		{
			if(flipped)
			{
				this.animateNoFlip(x+150, y-100, BattleAnimation.cIcon, 1, g);
			}
			else
			{
				this.animate(x+150, y-100, BattleAnimation.cIcon, 1, g);
			}
		}
		if(currentAnimation[2].getWidth()<=getFrame(0))
		{
			this.setFrame(0,0);
		}
		this.animate(x, y, currentAnimation, 0, g);
	}
	@Override
	public void tick()
	{
		if(player != null)
		{
			System.out.println(player.x);
		}
		if(Quest.quests[Quest.PYRUZ_S] >= 4 && Quest.quests[Quest.PYRUZ_S] <= 9)
		{
			om.objects.remove(this);
		}
		else if(Quest.quests[Quest.PYRUZ_S] == 3 && player != null)
		{
			if(stage == 0)
			{
				if(player.x >= 500)
				{
					Global.disableMovement = true;
					if(player.vVelocity == 0)
					{
						stage++;
					}
				}
				this.x = 800;
				currentAnimation = OverworldAnimation.creatHappyIdle2;
				this.flipped = false;
			}
			else if(stage == 1)
			{
				if(player instanceof WilliamPlayer)
				{
					if(Global.talking == 0)
					{
						Global.talkingTo = this;
						Global.talking++;
						om.stopPlayerMoving(player);
						this.currentAnimation = OverworldAnimation.creatHappyTalkStomp;
						SpeechBubble.talk("William!!! So I hear that you defeated the big bad Ignacio! Cool!/To celebrate, I'm going to be giving you a gift! Meet me by the Purple/Apple Tree at the bottom of the mountain to pick it up!",Global.creatFont);
					}
					if(Global.talking == 2 && Global.talkingTo == this)
					{
						Global.talkingTo = this;
						Global.talking++;
						om.stopPlayerMoving(player);
						this.currentAnimation = OverworldAnimation.creatHappyIdle2;
						player.currentAnimation = BattleAnimation.williamTalkHappy;
						SpeechBubble.talk("Woah, a gift!?!? Gee, I've never had one of those before...or maybe I have.../I don't know anything about myself from before winding up in the Stone Age...",Global.willFont);
					
					}
					if(Global.talking == 4 && Global.talkingTo == this)
					{
						Global.talkingTo = this;
						Global.talking++;
						om.stopPlayerMoving(player);
						this.currentAnimation = OverworldAnimation.creatHappyTalkStomp;
						player.currentAnimation = BattleAnimation.williamIdle2;
						SpeechBubble.talk("Well, my gift will be very helpful to you on your journey! I'm going/to go to the Tree now. I should be there once you arrive to show you/how it works so you can try it out.",Global.creatFont);
					}
					if(Global.talking == 6)
					{
						Global.talking = 0;
						this.stage++;
						this.currentAnimation = OverworldAnimation.creatHappyWalk;
					}
				}
				else if(player instanceof SimonPlayer)
				{
					if(Global.talking == 0)
					{
						Global.talkingTo = this;
						Global.talking++;
						om.stopPlayerMoving(player);
						this.currentAnimation = OverworldAnimation.creatHappyTalk2;
						SpeechBubble.talk("Oh, hey you! You're Simon, correct? I just met your wife and daughter,/they said they're a-okay with you going on an adventure with William,/but you should check in on them every so often, such as right now,/especially because you were inprisoned and all.",Global.creatFont);
					}
					if(Global.talking == 2)
					{
						Global.talkingTo = this;
						Global.talking++;
						om.stopPlayerMoving(player);
						this.currentAnimation = OverworldAnimation.creatHappyTalkStomp;
						SpeechBubble.talk("By the way, I have a gift for William. It's at the Purple Apple Tree,/so please stop by so I can give it to him. Also, I kinda want him to be the/Overworld character when I give him the gift.",Global.creatFont);
					}
					if(Global.talking == 4)
					{
						Global.talkingTo = this;
						Global.talking++;
						om.stopPlayerMoving(player);
						this.currentAnimation = OverworldAnimation.creatHappyTalkStomp;
						SpeechBubble.talk("To change your Overworld character, open the pause menu with [X],/select [Party Members], choose a character, and press [Z]!/Easy as pie! Anyway, see you around, Mr. Simon!",Global.creatFont);
					}
					if(Global.talking == 6)
					{
						Global.talking = 0;
						this.stage++;
						this.currentAnimation = OverworldAnimation.creatHappyWalk;
					}
				}
			}
			else if(stage == 2)
			{
				this.flipped = true;
				this.x += 8;
				if(this.x >= 1100)
				{
					stage++;
				}
			}
			else if(stage == 3)
			{
				Global.talkingTo = null;
				Global.disableMovement = false;
				om.objects.remove(this);
				Quest.quests[Quest.PYRUZ_S] = 4;
			}
		}
		else if(Quest.quests[Quest.LOMO] >= 8)
		{
			this.x = 800;
			if(player != null && Global.talking == 0)
			{
				currentAnimation = OverworldAnimation.creatHappyIdle2;
				if(player.x>this.x+100)
				{
					flipped = true;
				}
				else
				{
					flipped = false;
				}
			}
			if(player instanceof WilliamPlayer)
			{
				if(Global.cPressed && Global.talking == 0 && distanceTo(ObjectId.Player)<300)
				{
					AudioHandler.playMusic(AudioHandler.cutscene1);
					currentAnimation = OverworldAnimation.creatHappyTalk2;
					Global.talkingTo = this;
					Global.talking = 1;
					Global.disableMovement = true;
					Global.williamHP = Global.williamHPMax;
					Global.williamSP = Global.williamSPMax;
					Global.simonHP = Global.simonHPMax;
					Global.simonSP = Global.simonSPMax;
					for(int i=0; i<99;i++)
					{
						Global.partnerHP[i] = Global.partnerHPMax[i];
						Global.partnerSP[i] = Global.partnerSPMax[i];
					}
					SpeechBubble.talk("Hey there William! Feeling tired? Well, I just restored your HP and SP/back up to full health!...It's just a little magic power that I have!/See ya later, my friend!",Global.creatFont);
				}
			}
			if(player instanceof SimonPlayer)
			{
				if(Global.cPressed && Global.talking == 0 && distanceTo(ObjectId.Player)<300)
				{
					AudioHandler.playMusic(AudioHandler.cutscene1);
					Global.simonHP = Global.simonHPMax;
					Global.simonSP = Global.simonSPMax;
					Global.williamHP = Global.williamHPMax;
					Global.williamSP = Global.williamSPMax;
					for(int i=0; i<99;i++)
					{
						Global.partnerHP[i] = Global.partnerHPMax[i];
						Global.partnerSP[i] = Global.partnerSPMax[i];
					}
					currentAnimation = OverworldAnimation.creatHappyTalk2;
					Global.talkingTo = this;
					Global.talking = 1;
					Global.disableMovement = true;
					SpeechBubble.talk("Hey there Simon! Feeling tired? Well, I just restored your HP and SP/back up to full health!...It's just a little magic power that I have!/See ya later, my acquaintance!",Global.creatFont);
				}
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				AudioHandler.playMusic(AudioHandler.lomoMusic);
				Global.disableMovement = false;
				Global.talking = 0;
				Global.talkingTo = null;
				currentAnimation = OverworldAnimation.creatHappyIdle2;
			}
		}
		else if(Quest.quests[Quest.LOMO] > 4)
		{
			om.objects.remove(this);
		}
		if(player == null || !om.objects.contains(player) || carl == null || !om.objects.contains(carl))
		{

			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i) instanceof Carl2)
				{
					carl = (Carl2)om.objects.get(i);
				}
				if(om.objects.get(i) instanceof SimonPlayer)
				{
					player = (SimonPlayer) om.objects.get(i);
				}
				if(om.objects.get(i) instanceof WilliamPlayer)
				{
					player = (WilliamPlayer) om.objects.get(i);
				}
			}
		}
		if(Quest.quests[Quest.LOMO]==2)
		{
			if(Global.talking == 1)
			{
				currentAnimation = OverworldAnimation.creatMadIdle2;
				player.currentAnimation = BattleAnimation.williamIdle3;
			}
		}
		if(player.x <= this.x+350 && Quest.quests[Quest.LOMO]<2)
		{
			if(Global.talking == 0 && Quest.quests[Quest.LOMO] == 0)
			{
				window.overworldMode.ty = Global.currentRoom.height-600;
				AudioHandler.playMusic(AudioHandler.cutscene1);
				currentAnimation = OverworldAnimation.creatHappyTalk2;
				player.currentAnimation = BattleAnimation.williamIdle2;
				player.hVelocity = 0;
				Global.talkingTo = this;
				Global.talking = 1;
				Quest.quests[Quest.LOMO] = 1;
				Global.disableMovement = true;
				translateTo = window.overworldMode.tx - 300;
				SpeechBubble.talk("Hey there William! It's me again, Creaturey! You know,/that guy you just met a few minutes ago...you remember me,/correct...?",Global.creatFont);
			}

			if(Global.talking == 2 && Global.talkingTo == this)
			{
				flipped = false;
				if(translateTo < window.overworldMode.tx)
				{
					currentAnimation = OverworldAnimation.creatHappyIdle2;
					window.overworldMode.tx -= 10;
				}
				else
				{
					Global.talking = 3;
					currentAnimation = OverworldAnimation.creatHappyTalk2;
					SpeechBubble.talk("Anyway William, this is Carl!/We've been traveling around through all the different time periods,/and right now, we're visiting the Stone Age!",Global.creatFont);
					
				}
			}
	
			if(Global.talking == 3)
			{
				if(translateTo < window.overworldMode.tx)
				{
					Global.talking = 2;
				}
			}
			if(Global.talking == 4 && Quest.quests[Quest.LOMO] == 1)
			{
				if(translateTo > window.overworldMode.tx+20)
				{
					Global.talking = 2;
				}
				currentAnimation = OverworldAnimation.creatHappyIdle2;
			}
	
			if(Global.talking == 6 && Global.talkingTo == this)
			{
				flipped = false;
					Global.talking = 7;
					currentAnimation = OverworldAnimation.creatMadTalk;
					SpeechBubble.talk("Carl, this is the first time this guy met us! We don't/follow the same timeline as him! Now where is this peaceful, upbeat/village full of people that you told me about yesterday?",Global.creatFont);
			}
	
			if(Global.talking == 8 && Quest.quests[Quest.LOMO] == 1)
			{
				currentAnimation = OverworldAnimation.creatMadIdle;
			}
	
			if(Global.talking == 10 && Global.talkingTo == this)
			{
				flipped = false;
				Global.talking = 11;
				currentAnimation = OverworldAnimation.creatMadIdle;
				player.currentAnimation = BattleAnimation.williamTalk;
				SpeechBubble.talk("Wow Creaturey, that was uncalled for...and people/tell me I have a hot temper...but I only get upset when there is/an actual REASON to get upset...LIKE SOMEONE/SNOOPING AROUND THE PLACE WHERE I KEEP MY TIME ORB...",Global.willFont);
			}
			if(Global.talking == 12 && Global.talkingTo == this)
			{
				flipped = true;
				Global.talking = 13;
				currentAnimation = OverworldAnimation.creatSadTalk2;
				player.currentAnimation = BattleAnimation.williamIdle2;
				SpeechBubble.talk("Sorry little guy...and Carl...I just get kinda worked up sometimes.../But anyways, I think we should look around this place. If the/townspeople left just last night as Carl says, then they should be pretty/easy to find...right?",Global.creatFont);
			}
			if(Global.talking == 14 && Global.talkingTo == this)
			{
				currentAnimation = OverworldAnimation.creatSadIdle2;
				flipped = false;
			}
			if(Global.talking >= 18)
			{
				player.currentAnimation = BattleAnimation.williamIdle3;
			}

		}

		if(Quest.quests[Quest.LOMO]==4)
		{
			if(Global.talking == 0 && Global.talkingTo == this)
			{
				flipped = false;
				Global.talking = 1;
				currentAnimation = OverworldAnimation.creatMadTalk;
				SpeechBubble.talk("That dirtbag...he probably thinks he's reeeeeealy cool, causing/trouble for everyone all the time...no wonder this place was deserted./If I had to live within 70 nautical miles of that guy, my head would/explode!",Global.creatFont);
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				flipped = true;
				Global.talking = 3;
				currentAnimation = OverworldAnimation.creatMadIdle2;
				player.currentAnimation = BattleAnimation.williamTalk;
				SpeechBubble.talk("Look dude, I think we should just abandon Carl. There's something MUCH more/important that I need to do: get my Time Orb back from that neanderthal. Since that/king dude isn't at his mountain right now, it should be a cinch to just grab the Time/Orb and go!",Global.willFont);
			}
			if(Global.talking == 4 && Global.talkingTo == this)
			{
				flipped = true;
				Global.talking = 5;
				currentAnimation = OverworldAnimation.creatMadTalk;
				player.currentAnimation = BattleAnimation.williamIdle3;
				SpeechBubble.talk("Wow William, I don't remember you being such a horrible/person! Also, there is a lake of poisonous water in/front of Mt Pyruz, and Carl's the only person I know with a boat, so like/it or not, we ARE going to find Carl!",Global.creatFont);
		
			}
			if(Global.talking == 6 && Global.talkingTo == this)
			{
				flipped = true;
				Global.talking = 7;
				currentAnimation = OverworldAnimation.creatMadIdle2;
				player.currentAnimation = BattleAnimation.williamTalk3;
				SpeechBubble.talk("Sorry dude, I didn't know you would get so upset over that...I just met you,/after all...now let's go look for Carl...That is, if you still want me to...",Global.willFont);
			}
			if(Global.talking == 8 && Global.talkingTo == this)
			{
				flipped = true;
				Global.talking = 9;
				currentAnimation = OverworldAnimation.creatHappyTalk2;
				player.currentAnimation = BattleAnimation.williamIdle4;
				SpeechBubble.talk("It's okay, I forgive you! And yes, I still want you to/help look. I mean, I need all the help I can get if I want to find him!/Now let's get going! Ignacio went eastward, so that's where/we'll start heading!",Global.creatFont);
			}
			if(Global.talking>=10)
			{
				flipped = false;
				currentAnimation = OverworldAnimation.creatSadWalk;
				player.currentAnimation = BattleAnimation.williamIdle3;
				x -= 8;
				if(x<800)
				{
					Global.disableMovement = false;
					Quest.quests[Quest.LOMO] = 5;
					Global.talking = 0;
					om.objects.remove(this);
					AudioHandler.playMusic(AudioHandler.lomoDeserted);
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
					this.y = g.y-280;
				}
			}
		}

		
	}

}
