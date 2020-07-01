package net.truttle1.time.overworld;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.blocks.Lava;
import net.truttle1.time.overworld.doors.Door;
import net.truttle1.time.overworld.npc.Box;
import net.truttle1.time.overworld.npc.cutscene.IggyPreBattleCutscene;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;

public class SimonPlayer extends GameObject{

	private boolean club;
	public boolean animationManualOverride = false;
	public SimonPlayer(Game window, int x, int y) {
		super(window);
		this.x = x;
		this.y = y;
		startX = x;
		startY = y;
		this.id = ObjectId.Player;
		currentAnimation = BattleAnimation.simonIdleAnimation;
		Global.playingAs = 0;
	}

	@Override
	public void tick() {
		if(Global.currentRoom.id == RoomId.SimonHouse)
		{
			if(y>Global.currentRoom.height+500)
			{
				x = startX;
				y = startY;
			}
			if(Global.talking > 0 && vVelocity<0)
			{
				vVelocity = 0;
				hVelocity = 0;
			}
			if(x>400 && x<Global.currentRoom.width-620)
			{
				window.overworldMode.tx = x-400;
			}
			if(x<400)
			{
				window.overworldMode.tx = 0;
			}
			if(y<=Global.currentRoom.height-200)
			{
				window.overworldMode.ty = y-200;
			}
	
			if(y<175)
			{
				window.overworldMode.ty = -25;
			}
			if(x>Global.currentRoom.width-620)
			{
				window.overworldMode.tx = Global.currentRoom.width-1024;
			}
			if(window.overworldMode.ty+600>Global.currentRoom.height)
			{
				window.overworldMode.ty = Global.currentRoom.height-600;
			}
		}
		if(Quest.quests[Quest.LOMOBANK] != 11 && !Global.bossBattle && !Global.disableMovement && !(Global.talkingTo instanceof IggyPreBattleCutscene))
		{
			if(y>Global.currentRoom.height+500)
			{
				x = startX;
				y = startY;
			}
			if(Global.talking > 0 && vVelocity<0)
			{
				vVelocity = 0;
				hVelocity = 0;
			}
			if(x>400 && x<Global.currentRoom.width-620)
			{
				window.overworldMode.tx = x-400;
			}
			if(x<400)
			{
				window.overworldMode.tx = 0;
			}
			if(y<=Global.currentRoom.height-200)
			{
				window.overworldMode.ty = y-200;
			}
	
			if(y<175)
			{
				window.overworldMode.ty = -25;
			}
			if(x>Global.currentRoom.width-620)
			{
				window.overworldMode.tx = Global.currentRoom.width-1024;
			}
			if(window.overworldMode.ty+600>Global.currentRoom.height)
			{
				window.overworldMode.ty = Global.currentRoom.height-600;
			}
		}
		if(Global.talking==0 && !Global.disableMovement && !club  && !Global.bossBattle)
		{
			if(Global.zPressed && vVelocity == 0 && currentAnimation != BattleAnimation.simonJumpAnimation &&  currentAnimation != BattleAnimation.simonLandAnimation)
			{
				AudioHandler.playSound(AudioHandler.seJump);
				setFrame(0,0);
				currentAnimation = BattleAnimation.simonJumpAnimation;
				vVelocity = -35;
			}
			if(!club && Global.vPressed && currentAnimation != BattleAnimation.simonClubHit && vVelocity == 0  && Quest.quests[Quest.PYRUZ_S]>=1)
			{
				hVelocity = 0;
				this.setFrame(0, 0);
				club = true;
				currentAnimation = BattleAnimation.simonClubHit;
			}
			if(!Global.rightDown && !Global.leftDown)
			{
				hVelocity = 0;
			}
			if(Global.rightDown)
			{
				if(currentAnimation != BattleAnimation.simonJumpAnimation &&  currentAnimation != BattleAnimation.simonLandAnimation)
				{
					currentAnimation = BattleAnimation.simonWalkAnimation;
				}
				this.flipped = false;
				hVelocity = 12;
			}
			else if(Global.leftDown)
			{
				if(currentAnimation != BattleAnimation.simonJumpAnimation &&  currentAnimation != BattleAnimation.simonLandAnimation)
				{
					currentAnimation = BattleAnimation.simonWalkAnimation;
				}
				this.flipped = true;
				hVelocity = -12;
			}
			else if(!animationManualOverride && (Global.talking == 0) && currentAnimation != BattleAnimation.simonJumpAnimation && currentAnimation != BattleAnimation.simonLandAnimation && currentAnimation != BattleAnimation.simonClubHit)
			{
				currentAnimation = BattleAnimation.simonIdleAnimation;
			}
			if(vVelocity>0 && vVelocity != 0)
			{
				setFrame(0,0);
				currentAnimation = BattleAnimation.simonLandAnimation;
			}
		}
		
		y+=vVelocity;
		vVelocity+=2;

		if(Global.disableMovement)
		{
			hVelocity = 0;
			if(vVelocity<0)
			{
				vVelocity = 0;
			}
			if(!this.animationManualOverride && currentAnimation != BattleAnimation.simonItem && Global.talkingTo != null && !(Global.talkingTo instanceof Door) && !(Quest.quests[Quest.TUTORIAL]==7) && !(Quest.quests[Quest.TUTORIAL]==8))
			{
				currentAnimation = BattleAnimation.simonIdleAnimation;
			}
		}
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{

			if(window.overworldMode.objects.get(i) instanceof Lava)
			{
				Lava l = (Lava) window.overworldMode.objects.get(i);
				if(getBounds().intersects(l.getBounds()))
				{
					AudioHandler.playSound(AudioHandler.seSizzle);
					Global.simonHP--;
					Global.hurt = true;
					vVelocity = -30;
				}
			}
			if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
			{
				Grass g = (Grass)window.overworldMode.objects.get(i);

				if(g.getBounds().intersects(leftRectangle())) {
					if(hVelocity<0)
					{
						hVelocity = 0;
					}
				}

				if(g.getBounds().intersects(rightRectangle())) {
					if(hVelocity>0)
					{
						hVelocity = 0;
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
					Global.hurt = false;
					this.y = g.y-180;
					if(!this.animationManualOverride && !Global.disableMovement && Global.talking == 0 && (currentAnimation == BattleAnimation.simonJumpAnimation || currentAnimation == BattleAnimation.simonLandAnimation))
					{
						currentAnimation = BattleAnimation.simonIdleAnimation;
					}
				}
			}
		}
		x+=hVelocity;
		if(Global.hurt)
		{
			currentAnimation = BattleAnimation.simonHitAnimation;
		}

		if(club)
		{
			hVelocity = 0;
			currentAnimation = BattleAnimation.simonClubHit;
			if(getFrame(0)==22)
			{
				Global.swingingClub = true;
			}
			if(getFrame(0) >= 35)
			{
				Global.swingingClub = false;
				club = false;
				setFrame(0,0);
				currentAnimation = BattleAnimation.simonIdleAnimation;
			}
		}
		if(Global.disableMovement && Global.talkingTo instanceof Door)
		{
			currentAnimation = BattleAnimation.simonWalkAnimation;
		}
		else if(Global.disableMovement && Global.talkingTo instanceof Box && Global.talking >= 1)
		{
			this.setFrame(0, 0);
			currentAnimation = BattleAnimation.simonItem;
		}
		if(Global.talkingTo instanceof IggyPreBattleCutscene)
		{
			currentAnimation = BattleAnimation.simonIdleAnimation;
		}
		/*if(Global.disableMovement)
		{
			currentAnimation = BattleAnimation.simonIdleAnimation;
		}*/
	}

	private Rectangle bottomRectangle()
	{
		return new Rectangle(x+60,y+150,100,50);
	}
	private Rectangle topRectangle()
	{
		return new Rectangle(x+60,y+24,100,50);
	}
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+120,y+24,50,120);
	}
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+45,y+24,50,120);
	}
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+42,y+34,118,150);
	}

	@Override
	public void setAnimation(BufferedImage[] animation)
	{
		this.currentAnimation = animation;
		super.currentAnimation = animation;
		currentAnimation = animation;
	}
	@Override
	public void render(Graphics g) 
	{
		if(currentAnimation == BattleAnimation.simonJumpAnimation)
		{
			this.animateAtSpeed(x, y, currentAnimation, 0, g, 0.10);
		}
		else
		{

			this.animate(x, y, currentAnimation, 0, g);
		}
	}

}
