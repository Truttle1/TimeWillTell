package net.truttle1.time.overworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.blocks.Lava;
import net.truttle1.time.overworld.npc.NPC;
import net.truttle1.time.overworld.npc.Rage0;
import net.truttle1.time.overworld.npc.creaturey.Creaturey1;
import net.truttle1.time.overworld.npc.creaturey.Creaturey3;
import net.truttle1.time.overworld.npc.creaturey.Creaturey5;
import net.truttle1.time.overworld.npc.cutscene.IggyOverworld1;
import net.truttle1.time.overworld.npc.cutscene.IggyPreBattleCutscene;

public class WilliamPlayer extends GameObject{

	public OverworldMode om;
	BufferedImage[] tintedAnimation;
	BufferedImage[] curAnim;
	public WilliamPlayer(Game window, int x, int y, OverworldMode om) {
		super(window);
		this.x = x;
		this.y = y;
		startX = x;
		startY = y;
		this.om = om;
		this.id = ObjectId.Player;
		if(Global.playerSad)
		{
			currentAnimation = BattleAnimation.williamIdle3;
		}
		else
		{
			currentAnimation = BattleAnimation.williamIdle2;
		}
		Global.playingAs = 1;
	}

	@Override
	public void tick() {
		if(om.currentRoom == RoomId.StoneAge4 &&Quest.quests[Quest.LOMO]<8)
		{
			if(this.x>14400)
			{
				if(Global.talking == 0 && this.x>14600)
				{
					Global.disableMovement = true;
					Global.talkingTo = this;
					Global.talking = 1;
					this.hVelocity = 0;
					currentAnimation = BattleAnimation.williamTalk;
					SpeechBubble.talk("Yikes! I don't know how to swim! Also, that/water smells like it's poisoned!");
				}
				if(Global.talking>=2 && Global.talkingTo == this)
				{
					if(this.x>14500)
					{
						currentAnimation = BattleAnimation.williamWalkAnimation;
						this.flipped = false;
						this.x -= 12;
					}
					else
					{
						currentAnimation = BattleAnimation.williamIdle2;
						Global.talking = 0;
						Global.disableMovement = false;
						Global.talkingTo = null;
					}
				}
			}
		}


		if(!(Global.talkingTo instanceof IggyPreBattleCutscene) && !(Global.talkingTo instanceof Creaturey5) && !Global.bossBattle)
		{
			if(y<=Global.currentRoom.height-200)
			{
				window.overworldMode.ty = y-200;
			}
			if(window.overworldMode.ty+600>Global.currentRoom.height)
			{
				window.overworldMode.ty = Global.currentRoom.height-600;
			}
			if(y<175)
			{
				window.overworldMode.ty = -25;
			}
		}

		if(!(Quest.quests[Quest.LOMOBANK] <= 11) && !Global.disableMovement && !Global.bossBattle)
		{
			if(y>Global.currentRoom.height+500)
			{
				x = startX;
				y = startY - 40;
			}
			if(Global.talking > 0 && vVelocity<0)
			{
				vVelocity = 0;
				hVelocity = 0;
			}
			if(x>400 && x<Global.currentRoom.width-620 && (Global.talking == 0 && (Quest.quests[Quest.LOMO]<2 || Quest.quests[Quest.LOMO]>=5)))
			{
				window.overworldMode.tx = x-400;
			}
			if(x<400)
			{
				window.overworldMode.tx = 0;
			}
			if(x>Global.currentRoom.width-620)
			{
				window.overworldMode.tx = Global.currentRoom.width-1024;
			}
		}
		else
		{
			lomoBankTalk();
		}
		if(Quest.quests[Quest.LOMOBANK]>=12 && Quest.quests[Quest.LOMOBANK]<=14 && Global.talkingTo instanceof Creaturey1 && om.currentRoom == RoomId.StoneAge4)
		{
			window.overworldMode.ty = y-300;
		}
		if(Quest.quests[Quest.LOMO]>=5 && Quest.quests[Quest.LOMO]<=6 && Global.talkingTo instanceof Creaturey3 && om.currentRoom == RoomId.Lomo2 && Global.talking != 0)
		{
			window.overworldMode.ty = Global.talkingTo.y-200;
		}
		if(Quest.quests[Quest.PYRUZ_W]>=8 && Quest.quests[Quest.PYRUZ_W]<=8 && Global.talkingTo instanceof Rage0 && om.currentRoom == RoomId.Pyruz1)
		{
			window.overworldMode.ty = Global.talkingTo.y-150;
		}
		if(Global.talking==0 && !Global.disableMovement  && !Global.bossBattle)
		{
			if(Global.zPressed && vVelocity == 0 && currentAnimation != BattleAnimation.williamJumpAnimation &&  currentAnimation != BattleAnimation.williamLandAnimation)
			{
				AudioHandler.playSound(AudioHandler.seJump);
				setFrame(0,0);
				currentAnimation = BattleAnimation.williamJumpAnimation;
				vVelocity = -50;
			}
			if(!Global.rightDown && !Global.leftDown)
			{
				hVelocity = 0;
			}
			if(Global.rightDown)
			{
				if(currentAnimation != BattleAnimation.williamJumpAnimation &&  currentAnimation != BattleAnimation.williamLandAnimation)
				{
					if(Global.playerSad)
					{
						currentAnimation = BattleAnimation.williamWalk4;
					}
					else
					{
						currentAnimation = BattleAnimation.williamWalkAnimation;
					}
				}
				this.flipped = true;
				hVelocity = 14;
			}
			else if(Global.leftDown)
			{
				if(currentAnimation != BattleAnimation.williamJumpAnimation && currentAnimation != BattleAnimation.williamLandAnimation)
				{

					if(Global.playerSad)
					{
						currentAnimation = BattleAnimation.williamWalk4;
					}
					else
					{
						currentAnimation = BattleAnimation.williamWalkAnimation;
					}
				}
				this.flipped = false;
				hVelocity = -14;
			}
			else if(currentAnimation != BattleAnimation.williamJumpAnimation && currentAnimation != BattleAnimation.williamLandAnimation)
			{

				if(Global.playerSad)
				{
					currentAnimation = BattleAnimation.williamIdle3;
				}
				else
				{
					currentAnimation = BattleAnimation.williamIdle2;
				}
			}
			if(vVelocity>0 && vVelocity != 0)
			{
				setFrame(0,0);
				currentAnimation = BattleAnimation.williamLandAnimation;
			}
		}
		if(!Global.enableGravity)
		{
			vVelocity = 0;
		}
		y+=vVelocity;
		vVelocity+=4;

		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(window.overworldMode.objects.get(i) instanceof Lava)
			{
				Lava l = (Lava) window.overworldMode.objects.get(i);
				if(getBounds().intersects(l.getBounds()))
				{
					AudioHandler.playSound(AudioHandler.seSizzle);
					Global.williamHP--;
					Global.hurt = true;
					vVelocity = -50;
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
						Global.hurt = false;
					}
				}
				if(g.getBounds().intersects(bottomRectangle()) && vVelocity>0)
				{
					vVelocity = 0;
					this.y = g.y-120;
					Global.hurt = false;
					if(currentAnimation == BattleAnimation.williamJumpAnimation || currentAnimation == BattleAnimation.williamLandAnimation)
					{
						if(Global.playerSad)
						{
							currentAnimation = BattleAnimation.williamIdle3;
						}
						else
						{
							currentAnimation = BattleAnimation.williamIdle2;
						}
					}
				}
			}
		}

		if(Store.running)
		{
			currentAnimation = BattleAnimation.williamIdle2;
		}
		if(Global.hurt)
		{
			currentAnimation = BattleAnimation.williamHitAnimation;
		}
		if(Global.talkingTo instanceof NPC && !(Global.talkingTo instanceof IggyOverworld1) && Global.talking != 0)
		{
			hVelocity = 0;
		}
		x+=hVelocity;
	}

	private Rectangle bottomRectangle()
	{
		return new Rectangle(x+40,y+110,60,20);
	}
	private Rectangle topRectangle()
	{
		return new Rectangle(x+40,y,60,20);
	}
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+90,y,30,80);
	}
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+15,y,30,80);
	}
	@Override
	public Rectangle getBounds()
	{
		//return new Rectangle(x+15,y,135,140);
		return new Rectangle(x+29,y+7,82,129);
	}
	@Override
	public void render(Graphics g) {
		if(getFrame(0)>=currentAnimation[2].getWidth())
		{
			setFrame(0,0);
		}
		if(curAnim != currentAnimation)
		{
			tintedAnimation = null;
			curAnim = currentAnimation;
		}
		this.animate(x, y, currentAnimation, 0, g);
		if(heldItem != null)
		{
			this.animate(x+40, y-40, heldItem, 1, g);
		}
	}
	
	private void lomoBankTalk()
	{
		if(this.x<600 && Quest.quests[Quest.LOMOBANK]==11 && om.currentRoom == RoomId.StoneAge4)
		{
			this.flipped = true;
			currentAnimation = BattleAnimation.williamWalk3;
			this.x += 14;
		}
		else if(Global.talking == 0 && Quest.quests[Quest.LOMOBANK]==11)
		{
			currentAnimation = BattleAnimation.williamTalk;
			setFrame(0,0);
			Global.talking = 1;
			Global.talkingTo = this;
			SpeechBubble.talk("WHAT!? Oh no! The orb!... *sigh*...life is so unfair.../But I guess I'll look on the bright side:/This guy left his items on the ground!/Finders Keepers!",Global.willFont);
		}
		else if(Global.talking >= 2 && Quest.quests[Quest.LOMOBANK]==11 && om.currentRoom == RoomId.StoneAge4)
		{
			Global.talking = 0;
			Global.disableMovement = false;
			Quest.quests[Quest.LOMOBANK] = 12;
			AudioHandler.playMusic(AudioHandler.prehistoricMusic);
		}
	}

}
