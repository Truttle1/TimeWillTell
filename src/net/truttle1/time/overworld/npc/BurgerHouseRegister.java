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

public class BurgerHouseRegister extends NPC{
	private BufferedImage[] guy;
	private GameObject player;
	public BurgerHouseRegister(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.burgerHouseRegister;
		this.id = ObjectId.Foreground;
		guy = OverworldAnimation.cityNPC2Idle;
	}
	
	public void setGuy(BufferedImage[] iGuy)
	{
		guy = iGuy;
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
		if(Global.cPressed && Global.talking == 0 && distanceTo(ObjectId.Player)<200)
		{
			Global.talkingTo = this;
			Global.talking = 1;
			Global.disableMovement = true;
			setFrame(1,0);
			this.setFrame(1, 0);
			guy = OverworldAnimation.cityNPC2Talk;
			SpeechBubble.talk("Welcome to the Burger House!/May I take your order?");
			
			//Global.disableMovement = true;
		}
		if(Global.talkingTo == this && Global.talking == 2)
		{
			Store.running = false;
			Global.talking = 3;
			guy = OverworldAnimation.cityNPC2Idle;
			Store.initStore(2);
			Store.setItem(0, Store.BURGER, 9);
			Store.setItem(1, Store.FRIES, 7);
			Store.setItem(2, Store.NUGGETS, 8);
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
	
	@Override
	public void render(Graphics g) {

		this.animate(x+50, y, guy, 1, g);
		this.animate(x, y, currentAnimation, 0, g);
		if(distanceTo(ObjectId.Player)<200 && Global.talking == 0 && !Global.disableMovement)
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
