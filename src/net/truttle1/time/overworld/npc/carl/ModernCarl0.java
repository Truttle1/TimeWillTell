package net.truttle1.time.overworld.npc.carl;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.npc.NPC;
import net.truttle1.time.overworld.npc.cutscene.Prop;

public class ModernCarl0 extends Carl{

	private GameObject william;
	private GameObject skrapps;
	private GameObject player;
	private int stage;
	public ModernCarl0(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.carlHappyIdle1;
		this.id = ObjectId.NPC;
		this.startX = x;
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
		if(Quest.quests[Quest.FINDCREATUREY]!= 0)
		{
			om.objects.remove(this);
		}
		else if(stage == 0)
		{
			if(player.x>=this.x-300)
			{
				player.hVelocity = 0;
				Global.disableMovement = true;
				if(Global.playingAs == 0 && player.vVelocity == 0)
				{
					stage++;
					player.setAnimation(BattleAnimation.simonIdleAnimation);
					william = new Prop(window,player.x,player.y+60,om,BattleAnimation.williamWalkAnimation);
					om.objects.add(william);
				}
				if(Global.playingAs == 1 && player.vVelocity == 0)
				{
					stage+=2;
					william = player;
					william.setAnimation(BattleAnimation.williamIdle2);
				}
			}
		}
		if(stage == 1)
		{
			william.flipped = true;
			william.x += 8;
			if(william.x >= this.x-150)
			{
				william.setAnimation(BattleAnimation.williamIdle2);
				stage++;
			}
		}
		if(stage == 2)
		{
			if(Global.talking == 0)
			{
				Global.disableMovement = false;
				Global.talkingTo = this;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamTalk2);
				this.currentAnimation = OverworldAnimation.carlHappyIdle1;
				SpeechBubble.talk("CARL! I didn't know you were going to be here in the Digital Age!/Man, this time period is very different from the Stone Age...",Global.willFont);
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				Global.disableMovement = false;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamIdle2);
				this.currentAnimation = OverworldAnimation.carlHappyTalk1;
				SpeechBubble.talk("Oh, hey William! I'm just looking for Creaturey, you know, that/monster-looking guy with the gray bandana tied around his neck?/Also, I actually haven't been to the Stone Age yet, but I've been/meaning to go there. It sounds like a pretty interesting place!",Global.carlFont);
			}
			if(Global.talking == 4 && Global.talkingTo == this)
			{
				Global.disableMovement = false;
				Global.talkingTo = this;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamTalk2);
				this.currentAnimation = OverworldAnimation.carlHappyIdle1;
				SpeechBubble.talk("Oh, you haven't been to the Stone Age yet? Well then, when did I meet you?/I mean, you seem to know me quite well right now, and the only place I've ever seen/you is the Stone Age! Also, yes, I do know who Creaturey is. He seems like a pretty/good guy, but his voice can be quite annoying at times...",Global.willFont);
			}
			if(Global.talking == 6 && Global.talkingTo == this)
			{
				Global.disableMovement = false;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamIdle2);
				this.currentAnimation = OverworldAnimation.carlHappyTalk1;
				SpeechBubble.talk("Yeah, I suppose his voice is quite high-pitched and squeaky.../Anyway, I was told that Creaturey has information about a certain/Green Time Orb. If you're interested in that sort of thing,/try finding me and Creaturey once we finally meet up.",Global.carlFont);
			}
			if(Global.talking == 8 && Global.talkingTo == this)
			{
				Global.disableMovement = false;
				Global.talkingTo = this;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamTalkHappy);
				this.currentAnimation = OverworldAnimation.carlHappyIdle1;
				SpeechBubble.talk("TIME ORBS!?!? Yes! If I haven't told you in the past...err...future...gosh/this is weird, I'm on the hunt for Time Orbs right now!!!/Let me know what you find!!!!",Global.willFont);
			}
			if(Global.talking == 10 && Global.talkingTo == this)
			{
				Global.disableMovement = false;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamIdle2);
				this.currentAnimation = OverworldAnimation.carlHappyTalk1;
				SpeechBubble.talk("Wow, you seem enthusiastic! By the way, is Skrapps with you by/any chance? I want to talk to him...",Global.carlFont);
			}
			if(Global.talking == 12)
			{
				stage++;
				Global.talking = 0;
				this.currentAnimation = OverworldAnimation.carlHappyIdle1;
				skrapps = new Prop(window,player.x,william.y-8,om,BattleAnimation.skrappsRun);
				om.objects.add(skrapps);
			}
		}
		if(stage == 3)
		{
			skrapps.flipped = false;
			skrapps.x -= 8;
			if(skrapps.x <= player.x-200)
			{
				stage++;
			}
		}
		if(stage == 4)
		{
			if(Global.talking == 0)
			{
				Global.disableMovement = false;
				Global.talkingTo = this;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamIdle2);
				skrapps.flipped = true;
				skrapps.setAnimation(BattleAnimation.skrappsTalk);
				this.currentAnimation = OverworldAnimation.carlHappyIdle1;
				SpeechBubble.talk("Yeah, I'm here, like always! What'cha need, Crocko-gator?",Global.skrappsFont);
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				this.setFrame(0, 0);
				Global.disableMovement = false;
				Global.talkingTo = this;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamIdle2);
				skrapps.flipped = true;
				skrapps.setAnimation(BattleAnimation.skrappsIdle2);
				this.currentAnimation = OverworldAnimation.carlTalk;
				SpeechBubble.talk("Well, first of all, I'm actually a caiman, not a crocodile/nor an alligator. I'm not going to get mad at you, though. Anyway,/William has told me a lot about you, and I wanted to actually meet/you in person, so, hello Skrapps! The name's Carl!",Global.carlFont);
			}
			if(Global.talking == 4 && Global.talkingTo == this)
			{
				this.setFrame(0, 0);
				Global.disableMovement = false;
				Global.talkingTo = this;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamIdle2);
				skrapps.flipped = true;
				skrapps.setAnimation(BattleAnimation.skrappsTalk);
				this.currentAnimation = OverworldAnimation.carlHappyIdle1;
				SpeechBubble.talk("Hey there, Carl! The name's Skrapps! William's my best buddy ever!/...probably because he's been my only buddy...gosh, that makes me kind of sad.../So yeah, Caimany Carl, it was cool meeting you!",Global.skrappsFont);
			}

			if(Global.talking == 6 && Global.talkingTo == this)
			{
				this.setFrame(0, 0);
				Global.disableMovement = false;
				Global.talkingTo = this;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamIdle2);
				skrapps.flipped = true;
				skrapps.setAnimation(BattleAnimation.skrappsIdle2);
				this.currentAnimation = OverworldAnimation.carlHappyTalk1;
				SpeechBubble.talk("Anyway guys, I think I better head out now. Talk to you guys soon!/And stay safe, there've been a lot of monsters roaming the streets/recently!",Global.carlFont);
			}
			if(Global.talking == 8)
			{
				Global.talking = 0;
				stage++;
			}
		}
		if(stage == 5)
		{
			this.currentAnimation = OverworldAnimation.carlWalk;
			this.flipped = true;
			this.x += 6;
			if(this.x>=this.startX+300)
			{
				stage++;
			}
		}
		if(stage == 6)
		{
			if(Global.talking == 0)
			{
				Global.disableMovement = false;
				Global.talkingTo = this;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamIdle2);
				skrapps.flipped = true;
				william.flipped = false;
				skrapps.setAnimation(BattleAnimation.skrappsTalk1);
				SpeechBubble.talk("So, what do you think Carl meant when he said that he heard about me/in stories? That makes me kinda scared...",Global.skrappsFont);
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				Global.disableMovement = false;
				Global.talkingTo = this;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamTalk);
				skrapps.flipped = true;
				william.flipped = false;
				skrapps.setAnimation(BattleAnimation.skrappsIdle1);
				SpeechBubble.talk("Don't worry about it Skrapps. I will never let anything bad happen to/you ever!!!",Global.willFont);
			}
			if(Global.talking == 4 && Global.talkingTo == this)
			{
				Global.disableMovement = false;
				Global.talkingTo = this;
				Global.talking++;
				william.setAnimation(BattleAnimation.williamIdle2);
				skrapps.flipped = true;
				william.flipped = false;
				skrapps.setAnimation(BattleAnimation.skrappsTalk);
				SpeechBubble.talk("Okay, good! Being betrayed brings out my bad side, and you don't wanna meet my/bad side! He's mean!!!",Global.skrappsFont);
			}
			if(Global.talking == 6)
			{
				Global.talking = 0;
				stage++;
			}
		}
		if(stage == 7)
		{
			if(Global.playingAs == 0)
			{
				william.flipped = false;
				william.x -= 8;
				william.setAnimation(BattleAnimation.williamWalkAnimation);
				if(william.x<=player.x)
				{
					om.objects.remove(william);
					stage++;
				}
			}
			else
			{
				stage++;
			}
		}
		if(stage == 8)
		{
			skrapps.x += 8;
			skrapps.setAnimation(BattleAnimation.skrappsRun);
			if(skrapps.x>=player.x)
			{
				om.objects.remove(skrapps);
				Quest.quests[Quest.FINDCREATUREY]++;
				om.objects.remove(this);
				Global.talking = 0;
				Global.talkingTo = null;
				Global.disableMovement = false;
			}
		}
		
	}

}
