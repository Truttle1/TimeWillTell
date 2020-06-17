package net.truttle1.time.overworld.npc;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
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
import net.truttle1.time.overworld.enemies.WilliamE1;
import net.truttle1.time.overworld.npc.carl.Carl2;

public class LomoJuiceBar extends NPC{
	BufferedImage[] guy;
	GameObject player;
	public LomoJuiceBar(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.juice;
		this.id = ObjectId.Foreground;
		guy = OverworldAnimation.bCavemanIdle;
	}

	@Override
	public Rectangle topRectangle() {
		return null;
	}

	@Override
	public Rectangle leftRectangle() {
		return null;
	}

	@Override
	public Rectangle rightRectangle() {
		return null;
	}

	@Override
	public Rectangle bottomRectangle() {
		return null;
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
	@Override
	public void tick()
	{
		if(player == null || !om.objects.contains(player))
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
			}
		}
		if(Quest.quests[Quest.LOMO]==8 && player.x>this.x)
		{
			if(Global.talking == 0)
			{
				Global.disableMovement = true;
				setFrame(1,0);
				this.setFrame(1, 0);
				guy = OverworldAnimation.bCavemanTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("YES! I'M FINALLY FREE!!! I finally managed to escape Ignacio's prison!/Now who are you, little one? Are you a paying customer?");
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				Global.disableMovement = true;
				guy = OverworldAnimation.bCavemanIdle;
				player.currentAnimation = BattleAnimation.williamTalk;
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				SpeechBubble.talk("It depends...what are you selling?",Global.willFont);
			}
			if(Global.talking == 4 && Global.talkingTo == this)
			{
				Global.disableMovement = true;
				setFrame(1,0);
				this.setFrame(1, 0);
				guy = OverworldAnimation.bCavemanTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
				Global.talkingTo = this;
				Global.talking = 5;
				Global.disableMovement = true;
				SpeechBubble.talk("I sell juice! Specifically, I sell types that raise your/HP and sometimes your SP! I also sell Tenderlomos, which are a/delicious meat product that raise both your HP and SP!/My name is Saft, by the way!");
			}
			if(Global.talking == 6 && Global.talkingTo == this)
			{
				Global.disableMovement = true;
				guy = OverworldAnimation.bCavemanIdle;
				player.currentAnimation = BattleAnimation.williamTalk2;
				Global.talkingTo = this;
				Global.talking = 7;
				Global.disableMovement = true;
				SpeechBubble.talk("That's cool! Man, those sound delicious! Also, being an omnivore ROCKS!!!",Global.willFont);
			}
			if(Global.talking == 8 && Global.talkingTo == this)
			{
				guy = OverworldAnimation.bCavemanIdle;
				Global.talkingTo = null;
				Global.talking = 0;
				Global.disableMovement = false;
				Quest.quests[Quest.LOMO] = 9;
			}
			
		}
		if(Quest.quests[Quest.LOMO]>8)
		{
			if(Global.cPressed && Global.talking == 0 && distanceTo(ObjectId.Player)<300)
			{
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				setFrame(1,0);
				this.setFrame(1, 0);
				guy = OverworldAnimation.bCavemanTalk;
				SpeechBubble.talk("Welcome to the Lomo Juice Bar! My name is Saft! May I take/your order?");
				
				//Global.disableMovement = true;
			}
			if(Global.talkingTo == this && Global.talking == 2)
			{
				Store.running = false;
				Global.talking = 3;
				guy = OverworldAnimation.bCavemanIdle;
				Store.initStore(3);
				Store.setItem(0, 0, 5);
				Store.setItem(1, 1, 9);
				Store.setItem(2, 2, 8);
				Store.setItem(3, 4, 16);
				//Store.setItem(3, 3, 15);
				om.pauseMenuOpen = false;
			}
			if(Global.talkingTo == this && Global.talking == 4)
			{
				Global.talkingTo = null;
				om.pauseMenuOpen = false;
				Global.xPressed = false;
				Store.running = false;
				Global.talking = 0;
				Global.disableMovement = false;
				AudioHandler.playMusic(Global.currentRoom.music);
			}
			
		}
	}
	@Override
	public void render(Graphics g) {

		if(Quest.quests[Quest.LOMO]>=8)
		{
			this.animate(x+100, y+150, guy, 1, g);
		}
		this.animate(x, y, currentAnimation, 0, g);

		if(Quest.quests[Quest.LOMO]>=8)
		{
			if(distanceTo(ObjectId.Player)<300 && Global.talking == 0 && !Global.disableMovement)
			{
				if(flipped)
				{
					this.animateNoFlip(x+150, y-100, BattleAnimation.cIcon, 2, g);
				}
				else
				{
					this.animate(x+150, y-100, BattleAnimation.cIcon, 2, g);
				}
			}
		}
		
	}

}
