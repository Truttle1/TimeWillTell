package net.truttle1.time.overworld;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.npc.NPC;

public class TimeMachine extends NPC{

	private int stage;
	private boolean warped;
	private final int CDISTANCE = 150;
	public TimeMachine(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		this.currentAnimation = OverworldAnimation.timeMachineDisabled;
		this.id = ObjectId.NPC;
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
	public void render(Graphics g) {

		if(distanceToPoint(ObjectId.Player,x+100,y+100)<CDISTANCE && Global.talking == 0 && !Global.disableMovement)
		{
			if(flipped)
			{
				this.animateNoFlip(x+100, y+20, BattleAnimation.cIcon, 1, g);
			}
			else
			{
				this.animate(x+100, y+20, BattleAnimation.cIcon, 1, g);
			}
		}
		if(this.getFrame(0)>=71)
		{
			this.setFrame(0, 48);
		}
		this.animate(x, y, currentAnimation, 0, g);
	}
	@Override
	public void tick()
	{
		if(Quest.quests[Quest.TIMEMACHINE] >= 1 && stage == 0)
		{
			currentAnimation = OverworldAnimation.timeMachineY;
		}
		if(distanceToPoint(ObjectId.Player,x+100,y+100)<CDISTANCE && Global.talking == 0 && !Global.disableMovement && Global.cPressed)
		{
			if(getPlayer() instanceof WilliamPlayer)
			{
				if(Global.talking == 0)
				{
					om.stopPlayerMoving(getPlayer());
					Global.talking++;
					Global.disableMovement = true;
					Global.talkingTo = this;
					SpeechBubble.yesNo("Would you like to travel through time?",Global.textFont);
				}
				
			}
			else
			{
				if(Global.talking == 0)
				{
					om.stopPlayerMoving(getPlayer());
					Global.talking++;
					Global.disableMovement = true;
					Global.talkingTo = this;
					SpeechBubble.talk("William is the only one who knows how to use the Time Machine.");
				}
			}
			
			
		}
		if(getPlayer() instanceof WilliamPlayer)
		{
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				if(SpeechBubble.selection == 1)
				{
					Global.talking = 0;
					Global.disableMovement = false;
					Global.talkingTo = null;
				}
				else
				{
					om.objects.remove(getPlayer());
					stage++;
					this.setFrame(0, 0);
					this.currentAnimation = OverworldAnimation.timeMachineYRide;
					Global.talking = 0;
					Global.disableMovement = false;
					Global.talkingTo = null;
				}
			}
		}
		else
		{

			if(Global.talking == 2 && Global.talkingTo == this)
			{
				Global.talking = 0;
				Global.disableMovement = false;
				Global.talkingTo = null;
			}
		}
		if(stage == 1)
		{
			if(this.getFrame(0) == 48)
			{
				AudioHandler.playMusic(AudioHandler.intro);
				Fade.run(window, ModeType.AgeMenu,false,10);
				stage++;
			}
			window.menuMode.init();
		}
		if(stage == 2)
		{
			this.x -= 20;
			if(Fade.fadeIn)
			{
				om.clearRoom();
				om.clearRoomWarps();
			}
		}
	}
	

}
