package net.truttle1.time.overworld.npc.creaturey;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
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
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;
import net.truttle1.time.overworld.npc.carl.Carl3;

public class Creaturey4 extends Creaturey{

	GameObject player;
	Carl3 carl;
	int translateTo;
	boolean pressedZ = false;
	public Creaturey4(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.creatHappyWalk;
		this.id = ObjectId.NPC;
		this.flipped = true;
		Global.disableMovement = true;
	}

	@Override
	public void render(Graphics g) {
		if(currentAnimation[2].getWidth()<=getFrame(0))
		{
			this.setFrame(0,0);
		}
		this.animate(x, y, currentAnimation, 0, g);
	}
	@Override
	public void tick()
	{
		if(Quest.quests[Quest.LOMO] == 7)
		{

			window.overworldMode.ty = this.y-200;
			window.overworldMode.tx = this.x-300;
		}
		if(Quest.quests[Quest.LOMO] >= 9)
		{
			om.objects.remove(this);
		}
		if(Global.talkingTo != this && this.x<=2100)
		{
			this.x += 16;
		}
		if(player == null || !om.objects.contains(player) || carl == null || !om.objects.contains(carl))
		{

			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i) instanceof SimonPlayer)
				{
					player = (SimonPlayer) om.objects.get(i);
				}
				if(om.objects.get(i) instanceof WilliamPlayer)
				{
					player = (WilliamPlayer) om.objects.get(i);
				}
				if(om.objects.get(i) instanceof Carl3)
				{
					carl = (Carl3) om.objects.get(i);
				}
			}
		}
		System.out.println(Global.talking);
		if(Quest.quests[Quest.LOMO] == 7)
		{
			if(Global.talking == 0 && this.x>2100)
			{
				AudioHandler.playMusic(AudioHandler.cutscene1);
				currentAnimation = OverworldAnimation.creatHappyTalkStomp;
				carl.currentAnimation = OverworldAnimation.carlHappyIdle;
				//player.currentAnimation = BattleAnimation.williamIdle3;
				//player.hVelocity = 0;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				this.flipped = true;
				SpeechBubble.talk("Carl!!! Man, you could not BELIEVE what I had to go/through to get here...well, actually you could, since you were/also brought here...anyway, let me get that/cage off of you my friend!",Global.creatFont);
			}
			if(Global.talking == 2)
			{
				Global.talking = 3;
				currentAnimation = OverworldAnimation.creatHappyIdle2;
				Fade.run(window, ModeType.Overworld,false);
			}
			if(Fade.fadeNum >= 250 && Global.talking > 2)
			{
				carl.caged = false;
				Quest.quests[Quest.LOMO] = 8;
				Global.talking = 0;
			}
		}

		if(Quest.quests[Quest.LOMO] == 8 && Fade.fadeNum <= 10)
		{
			if(Global.talking == 0)
			{
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlHappyTalk;
				SpeechBubble.talk("Thanks Creaturey, I appreciate it! Did you see anything while I/was captured? I mean, you did need to come all the way over here/in order to rescue me and all.../Also, where's William? Wasn't he with you?",Global.carlFont);
				
			}
			if(Global.talking == 2)
			{
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlHappyIdle;
				currentAnimation = OverworldAnimation.creatSadTalk2;
				SpeechBubble.talk("Oh him? I don't know...I hope he gets here soon.../He needs to use your boat to cross the poisonous river and/get to the enterance of Mt. Pyruz.",Global.creatFont);	
			}
			if(Global.talking >= 3)
			{
				if(Global.talking == 4)
				{
					pressedZ = true;
				}
				if(player == null)
				{
					player = new WilliamPlayer(window,this.x-800,this.y,om);
					om.objects.add(player);
					Global.talking = 3;
				}
				else if(player.x<this.x-200)
				{
					player.x += 8;
					player.currentAnimation = BattleAnimation.williamWalkAnimation;
					Global.talking = 3;
					player.flipped = true;
				}
				if((Global.talking == 3 && pressedZ  && player.x>=this.x-200)|| Global.talking == 4 || Global.talking == 5)
				{
					Global.talking = 6;
				}
				else if(Global.talking == 3 && player.x>=this.x-200)
				{
					player.currentAnimation = BattleAnimation.williamIdle2;
				}
			}
			if(Global.talking == 6)
			{
				Global.talkingTo = this;
				Global.talking = 7;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlHappyIdle;
				currentAnimation = OverworldAnimation.creatHappyIdle2;
				player.currentAnimation = BattleAnimation.williamTalk2;
				this.flipped = false;
				SpeechBubble.talk("Hey guys! Phew, I'm glad you two are both safe! Especially you Creaturey, I mean,/those Flairmers are REALLY suspicious and dangerous looking...We should've been/more careful!...Especially since when I knocked out that dude out earlier today,/he walked BACK INTO THE VILLAGE!!!",Global.willFont);	
			}
			if(Global.talking == 8)
			{
				Global.talkingTo = this;
				Global.talking = 9;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlIdle;
				currentAnimation = OverworldAnimation.creatSadTalk2;
				player.currentAnimation = BattleAnimation.williamIdle2;
				this.flipped = true;
				SpeechBubble.talk("Oh...speaking of that Flarimer dude...he kinda spilled the beans on/Iggy's motives a little bit...and by \"a little bit\" I mean he/basically told me EXACTLY why the entire village was deserted.../By accident, of course...I don't think Flairmer's that bright...",Global.creatFont);	
			}

			if(Global.talking == 10)
			{
				Global.talkingTo = this;
				Global.talking = 11;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlTalk;
				currentAnimation = OverworldAnimation.creatSadIdle2;
				player.currentAnimation = BattleAnimation.williamIdle2;
				this.flipped = true;
				SpeechBubble.talk("Really? No way! What did he say?",Global.carlFont);	
			}
			if(Global.talking == 12)
			{
				Global.talkingTo = this;
				Global.talking = 13;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlIdle;
				currentAnimation = OverworldAnimation.creatSadTalk2;
				player.currentAnimation = BattleAnimation.williamIdle3;
				this.flipped = true;
				SpeechBubble.talk("Ignacio's plan is to kidnap all of the humans and \"rule over them!\"/I don't know what type of ruling Ignacio is going to be enforcing,/but based on his past behavior, it isn't good...like, at all...In fact, it is/probably the worst thing to happen up to this point in history...",Global.creatFont);	
			}
			if(Global.talking == 14)
			{
				Global.talkingTo = this;
				Global.talking = 15;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlIdle;
				currentAnimation = OverworldAnimation.creatSadIdle2;
				player.currentAnimation = BattleAnimation.williamTalkPoint;
				this.flipped = false;
				SpeechBubble.talk("AND WITH THE TIME ORB, HE COULD DO EVEN MORE DAMAGE!!!...I think... BUT I'M GOING TO THAT/DISGRACE OF A DINOSAUR'S CAVE AND I'M GONNA KNOCK SOME SENSE INTO HIM ANYWAY!!!/AND I'LL KNOCK SOME SENSE INTO ANYONE EVEN REMOTELY LIKE THAT GUY AS WELL!!!",Global.willFont);	
			}
			if(Global.talking == 16)
			{
				Global.talkingTo = this;
				Global.talking = 17;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlTalk;
				currentAnimation = OverworldAnimation.creatSadIdle2;
				player.currentAnimation = BattleAnimation.williamIdle3;
				this.flipped = false;
				SpeechBubble.talk("Wow dude, would you mind taking down the intensity a bit?/You're kinda scaring me, both for your life and for MY OWN life!",Global.carlFont);	
			}
			if(Global.talking == 18)
			{
				Global.talkingTo = this;
				Global.talking = 19;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlIdle;
				currentAnimation = OverworldAnimation.creatSadIdle2;
				player.currentAnimation = BattleAnimation.williamTalk;
				this.flipped = false;
				SpeechBubble.talk("Fine, I'll be more \"careful\" or whatever...and I'll try to be less angry.../(Even though I don't think that's actually, y'know, POSSIBLE...)/But anyways, Carl, are you able to let me borrow your boat?...Please and thank you?",Global.willFont);	
			}
			if(Global.talking == 20)
			{
				Global.talkingTo = this;
				Global.talking = 21;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlHappyTalk;
				currentAnimation = OverworldAnimation.creatSadIdle2;
				player.currentAnimation = BattleAnimation.williamIdle2;
				this.flipped = true;
				SpeechBubble.talk("I think that's something I can let you do. Meet me down by the/Poison Lake and I'll take you across using my brand new high-speed/motorboat!...from the future, of course!",Global.carlFont);	
			}
			if(Global.talking == 22)
			{
				Global.talkingTo = this;
				Global.talking = 23;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlTalk;
				currentAnimation = OverworldAnimation.creatSadIdle2;
				player.currentAnimation = BattleAnimation.williamIdle4;
				this.flipped = true;
				SpeechBubble.talk("That is, if you don't BEAT ME UP first!",Global.carlFont);	
			}
			if(Global.talking == 24)
			{
				Global.talkingTo = this;
				Global.talking = 25;
				Global.disableMovement = true;
				carl.currentAnimation = OverworldAnimation.carlHappyIdle;
				currentAnimation = OverworldAnimation.creatHappyTalk2;
				player.currentAnimation = BattleAnimation.williamIdle2;
				this.flipped = false;
				SpeechBubble.talk("Oh, and I'll be hanging out in the village!/Come talk to me if you need your HP restored!...or if you just/feel like talking to me...please...I get lonely.../But anyways, see ya later William!",Global.creatFont);	
			}
			if(Global.talking>26)
			{
				carl.currentAnimation = OverworldAnimation.carlWalk;
				currentAnimation = OverworldAnimation.creatHappyWalk;
				this.flipped = false;
				this.x -= 16;
				carl.x -= 16;
				if(carl.x<1500)
				{
					AudioHandler.playMusic(AudioHandler.lomoMusic);
					Global.disableMovement = false;
					Global.talkingTo = null;
					Global.talking = 0;
					om.objects.remove(carl);
					om.objects.remove(this);
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
