package net.truttle1.time.minigames.creatrun;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.WorldId;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.carl.Carl3;
import net.truttle1.time.overworld.npc.creaturey.Creaturey4;
import net.truttle1.time.overworld.npc.cutscene.IggyOverworld1;
import net.truttle1.time.overworld.warps.StoneAgeWarps;

public class CreatureyPlayer1 extends GameObject{

	int startX;
	int startY;
	int textOffsetY;
	public MinigameMode1 mm;
	double scaleDouble = 1;
	double sDouble = 0.5;
	int texts = 0;
	boolean fadeIn = true;
	boolean dead = false;
	boolean sliding = false;
	boolean checkpointFade = true;
	boolean punching = false;
	boolean doublePunch = false;
	int slideTime = 0;
	int checkpointTexts = 0;
	int checkpointOffset = 0;
	boolean won = false;
	public CreatureyPlayer1(Game window, int x, int y, MinigameMode1 mm) {
		super(window);
		this.x = x;
		this.y = y;
		startX = x;
		startY = y;
		this.mm = mm;
		this.id = ObjectId.CreatureyMini1;
		currentAnimation = OverworldAnimation.creatSadWalk;
		Global.playingAs = 3;
		scaleDouble = 1;
		mm.ty = (int) (y*(mm.scale)-100);
	}

	@Override
	public void tick() {

		if(doublePunch && getFrame(0)>=23)
		{
			punching = false;
			doublePunch = false;
		}
		else if(!doublePunch && punching && getFrame(0)>=14)
		{
			punching = false;
			doublePunch = false;
		}
		mm.creatX = this.x;
		mm.creatY = this.y;
		if(!mm.gameStarted)
		{
			mm.ty = (int) (y-200);
		}
		else
		{
			if(x*(mm.scale)>400 && x<51200-620)
			{
				mm.tx = (int) (x-400/(mm.scale/1));
			}
			if(mm.tx<=0)
			{
				mm.tx = 0;
			}
			if(y>=760)
			{
				mm.ty = (int) (1126-(1200-960*mm.scale));
			}
			else
			{
				mm.ty = (int)(y-(800*mm.scale));
			}
			if(x*(mm.scale)<400)
			{
				mm.tx = 0;
			}
			/*
			if(y<175/(mm.scale/2))
			{
				mm.ty = (int) (-25/(mm.scale/2));
			}
			*/
			if(x>51200-620/(mm.scale))
			{
				mm.tx = (int) (51200-2048);
			}
		
		}
		
		if(mm.gameStarted && !won)
		{
			minigameTick();
		}
		else if(!won)
		{
			storyTick();
		}
		if(won)
		{
			winTick();
		}
		vVelocity+=8;
		y+=vVelocity;
		for(int i=0; i<window.minigameMode1.objects.size();i++)
		{
			if(!dead && (window.minigameMode1.objects.get(i).id == ObjectId.Spike || window.minigameMode1.objects.get(i).id == ObjectId.FlareMini1 || window.minigameMode1.objects.get(i).id == ObjectId.Spike2))
			{
				if(this.getBounds().intersects(window.minigameMode1.objects.get(i).getBounds()))
				{
					AudioHandler.playSound(AudioHandler.seHit2);
					dead = true;
					setFrame(0,0);
					this.setFrame(0, 0);
					currentAnimation = OverworldAnimation.creatDie;
				}
			}
			if(window.minigameMode1.objects.get(i).id == ObjectId.Grass)
			{
				RunStone g = (RunStone)window.minigameMode1.objects.get(i);
				if(g instanceof MinigameTree)
				{
					MinigameTree t = (MinigameTree) g;
					if(t.treeBounds().intersects(this.getBounds()) && punching && getFrame(0) == 5)
					{
						AudioHandler.playSound(AudioHandler.seDodge);
						t.hp -= 1;
						System.out.println("Hi!");
					}
					if(t.treeBounds().intersects(this.getBounds()) && doublePunch && getFrame(0) == 13)
					{
						AudioHandler.playSound(AudioHandler.seDodge);
						t.hp -= 1;
						System.out.println("Hi!");
					}
				}
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
					if(slideTime<32)
					{
						slideTime = 8;
						sliding = true;
					}
				}
				if(g.getBounds().intersects(bottomRectangle()))
				{
					vVelocity = 0;
					this.y = g.y-250;
				}
			}

			if(!dead && window.minigameMode1.objects.get(i).id == ObjectId.Flag && vVelocity == 0)
			{
				if(this.getBounds().intersects(window.minigameMode1.objects.get(i).getBounds()))
				{
					CheckpointFlag flg = (CheckpointFlag)window.minigameMode1.objects.get(i);
					if(!flg.currentAnimation.equals(OverworldAnimation.flag))
					{
						AudioHandler.playSound(AudioHandler.seWoosh);
					}
					flg.setAnimation(OverworldAnimation.flag);
					startX = this.x;
					startY = this.y;
					checkpointFade = fadeIn;
					checkpointOffset = textOffsetY;
					checkpointTexts = texts;
				}
			}
		}
		if(mm.gameStarted)
		{
			minigameFooter();
		}
		x+=hVelocity;
	}

	private void minigameFooter()
	{


		if(!dead && !won)
		{

			if(!sliding && !punching)
			{

				if(vVelocity < 0)
				{
					this.flipped = true;
					currentAnimation = OverworldAnimation.creatJump;
				}
				else if(vVelocity > 0)
				{
					this.flipped = true;
					currentAnimation = OverworldAnimation.creatLand;
				}
				else if(hVelocity > 4)
				{
					this.flipped = false;
					currentAnimation = OverworldAnimation.creatRun;
				}
				else
				{
					this.flipped = true;
					currentAnimation = OverworldAnimation.creatMadIdle2;
				}
			}
			else if(sliding)
			{
				this.flipped = true;
				currentAnimation = OverworldAnimation.creatSlide;
			}
		}
		else if(dead)
		{
			this.flipped = false;
			if(this.getFrame(0)>=5)
			{
				currentAnimation = OverworldAnimation.creatDead;
			}
		}
	}
	private Rectangle bottomRectangle()
	{
		if(sliding)
		{
			return new Rectangle(x+40,y+240,180,8);
		}
		else
		{
			return new Rectangle(x+30,y+200,200,48);
		}
	}
	private Rectangle topRectangle()
	{
		if(sliding)
		{
			return new Rectangle(x+30,y+100,200,80);
		}
		else
		{
			return new Rectangle(x+30,y,220,90);
		}
	}
	public Rectangle rightRectangle()
	{

		if(sliding)
		{
			return new Rectangle(x+190,y+150,80,80);
		}
		else
		{
			return new Rectangle(x+190,y,110,230);
		}
	}
	public Rectangle leftRectangle()
	{
		if(sliding)
		{
			return new Rectangle(x+40,y+150,80,80);
		}
		else
		{
			return new Rectangle(x+40,y,80,230);
		}
	}
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+40,y,200,200);
	}
	@Override
	public void render(Graphics g) {
		if(!dead)
		{
			if(this.x>1000&& this.x<2700)
			{
				g.setFont(Global.creatBigger);
				g.setColor(Color.gray.darker());
				g.drawString("CreatuRun!", mm.tx+700, mm.ty+(300-textOffsetY));

				g.setColor(Color.green);
				g.drawString("CreatuRun!", mm.tx+695, mm.ty+(295-textOffsetY));

				g.setColor(Color.black);
				g.setFont(Global.creatBig);
				g.drawString("Press [Right Arrow] to move faster.", mm.tx+500, mm.ty+(460-textOffsetY));
	 			g.drawString("Press [Left Arrow] to move slower.", mm.tx+550, mm.ty+(540-textOffsetY));
			}
			if(this.x>2800&& this.x<8000)
			{
				g.setColor(Color.black);
				g.setFont(Global.creatBig);
				g.drawString("Press [Z] to jump...err...make Creaturey jump!", mm.tx+300, mm.ty+(300-textOffsetY));
			}
			if(this.x>8500&& this.x<13700)
			{
				g.setColor(Color.black);
				g.setFont(Global.creatBig);
				g.drawString("Bottomless pits are known to cause side effects in Creatureys,", mm.tx+50, mm.ty+(300-textOffsetY));
				g.drawString("The most noticable one is DEATH, so don't fall in the", mm.tx+100, mm.ty+(380-textOffsetY));
				g.drawString("bottomless pit!", mm.tx+150, mm.ty+(460-textOffsetY));
			}
			if(this.x>13900&& this.x<17700)
			{
				g.setColor(Color.black);
				g.setFont(Global.creatBig);
				g.drawString("Spikes are NOT your friends!", mm.tx+250, mm.ty+(300-textOffsetY));
				g.drawString("Don't step on them, no matter how enticing they look!", mm.tx+300, mm.ty+(380-textOffsetY));
			}
			if(this.x>17900&& this.x<18700)
			{
				g.setColor(Color.black);
				g.setFont(Global.creatBig);
				g.drawString("Is the gap you need to get through too small?", mm.tx+250, mm.ty+(300-textOffsetY));
				g.drawString("Well, press [X] to do a sliding move!", mm.tx+300, mm.ty+(380-textOffsetY));
			}
			if(this.x>33400&& this.x<35300)
			{
				g.setColor(Color.white);
				g.setFont(Global.creatBig);
				g.drawString("Trees in the way? Well, you can break through them!", mm.tx+250, mm.ty+(300-textOffsetY));
				g.drawString("Press [C] to punch! You can double-punch if you time it right!", mm.tx+150, mm.ty+(380-textOffsetY));
			}
			if(this.x>=50850)
			{
				g.setFont(Global.creatBigger);
				g.setColor(Color.gray.darker());
				g.drawString("You did it!", mm.tx+700, mm.ty+(300-textOffsetY));

				g.setColor(Color.green);
				g.drawString("You did it!", mm.tx+695, mm.ty+(295-textOffsetY));

				g.setColor(Color.black);
				g.setFont(Global.creatBig);
				g.drawString("Carl is in the next room!", mm.tx+500, mm.ty+(460-textOffsetY));
	 			g.drawString("Press [Z] to continue!", mm.tx+550, mm.ty+(540-textOffsetY));
			}
		}
		if(dead)
		{
			g.setFont(Global.creatBigger);
			g.setColor(Color.gray.darker());
			g.drawString("Creaturey Died! :(", mm.tx+650, mm.ty+(300-textOffsetY));

			g.setColor(Color.red);
			g.drawString("Creaturey Died! :(", mm.tx+645, mm.ty+(295-textOffsetY));

			g.setColor(Color.black);
			g.setFont(Global.creatBig);
			g.drawString("Press [V] to restart.", mm.tx+800, mm.ty+(460-textOffsetY));
 				
		}
		
		if(getFrame(0)>=getAnimationLength(currentAnimation))
		{
			setFrame(0,0);
		}
		this.animate(x, y, currentAnimation, 0, g);
	}
	private void winTick()
	{
		sliding = false;
		punching = false;
		currentAnimation = OverworldAnimation.creatWin;
		hVelocity = 0;
		if(textOffsetY>0 && fadeIn)
		{
			textOffsetY -= 50;
		}
		if(Global.zPressed)
		{
			AudioHandler.playMusic(AudioHandler.cutscene1);
			OverworldMode om = window.overworldMode;
			for(int j=0; j<om.objects.size();j++)
			{
				if(om.objects.get(j).id == ObjectId.Warp && om.objects.get(j) != this)
				{
					om.objects.remove(om.objects.get(j));
				}
			}
			Quest.quests[Quest.LOMO] = 7;
			Fade.run(window, ModeType.Overworld,false);

			window.overworldMode.lomo3.loadStage();
			//window.overworldMode.lomo3.addPlayer(warpX, warpY);
			StoneAgeWarps.warps(window.overworldMode.lomo3);
			window.overworldMode.objects.add(new Creaturey4(window,300,700,window.overworldMode));
			window.overworldMode.objects.add(new Carl3(window,2600,800,window.overworldMode));
		}
	}
	private void minigameTick()
	{
		System.out.println(x);
		slideTime++;
		if(sliding)
		{
			if(slideTime>16)
			{
				sliding = false;
			}
		}
		if(this.x>900 && texts == 0)
		{
			texts = 1;
			textOffsetY = 600;
			fadeIn = true;
		}
		if(this.x>2500 && texts == 1)
		{
			texts = 2;
			fadeIn = false;
		}
		if(this.x>2700 && texts == 2)
		{
			texts = 3;
			textOffsetY = 600;
			fadeIn = true;
		}
		if(this.x>7800 && texts == 3)
		{
			texts = 4;
			fadeIn = false;
		}
		if(this.x>8500 && texts == 4)
		{
			texts = 5;
			textOffsetY = 600;
			fadeIn = true;
		}
		if(this.x>13500 && texts == 5)
		{
			texts = 6;
			fadeIn = false;
		}
		if(this.x>13800 && texts == 6)
		{
			texts = 7;
			textOffsetY = 600;
			fadeIn = true;
		}
		if(this.x>17500 && texts == 7)
		{
			texts = 8;
			fadeIn = false;
		}
		if(this.x>17800 && texts == 8)
		{
			texts = 9;
			textOffsetY = 600;
			fadeIn = true;
		}
		if(this.x>18800 && texts == 9)
		{
			texts = 10;
			fadeIn = false;
		}
		if(this.x>33500 && texts == 10)
		{
			texts = 11;
			textOffsetY = 600;
			fadeIn = true;
		}
		if(this.x>35000 && texts == 11)
		{
			texts = 12;
			fadeIn = false;
		}
		if(textOffsetY>0 && fadeIn)
		{
			textOffsetY -= 50;
		}
		else if(textOffsetY<600 && !fadeIn)
		{
			textOffsetY += 100;
		}
		else if(fadeIn)
		{
			textOffsetY = 0;
		}
		else if(!fadeIn)
		{
			textOffsetY = 600;
		}
		if(x > 1000 && !dead && Global.cPressed && !sliding && vVelocity == 0)
		{
			if(!punching)
			{
				this.flipped = true;
				doublePunch = false;
				setFrame(0,0);
				this.setFrame(0,0);
				hVelocity = 0;
				currentAnimation = OverworldAnimation.creatPunch2;
				punching = true;
			}
			else if(!doublePunch && this.getFrame(0)>6 && this.getFrame(0)<13)
			{
				this.flipped = true;
				currentAnimation = OverworldAnimation.creatPunch;
				doublePunch = true;
			}
		}
		if(x > 1000 && !dead && Global.zPressed && vVelocity < .3 && vVelocity > -.3 && currentAnimation != OverworldAnimation.creatJump &&  currentAnimation != OverworldAnimation.creatLand)
		{
			AudioHandler.playSound(AudioHandler.seJump);
			setFrame(0,0);
			currentAnimation = OverworldAnimation.creatJump;
			vVelocity = -90;
		}
		if(x > 1000 && !dead && Global.xPressed && !sliding && !punching && vVelocity == 0)
		{
			setFrame(0,0);
			this.setFrame(0, 0);
			sliding = true;
			slideTime = 0;
		}
		if(x > 1000 && Global.vDown)
		{
			texts = checkpointTexts;
			fadeIn = checkpointFade;
			textOffsetY = checkpointOffset;
			x = startX;
			y = startY;
			vVelocity = 0;
			dead = false;
			slideTime = 0;
			sliding = false;
			punching = false;
			doublePunch = false;
			for(int i = 0; i<mm.objects.size(); i++)
			{
				if(mm.objects.get(i).id == ObjectId.FlareMini1)
				{
					mm.objects.get(i).setFrame(0,0);
					mm.objects.get(i).x = this.x - 1200;
				}
			}
					
		}
		if(y<1200)
		{
			if(!punching && Global.rightDown)
			{
				hVelocity = 34;
			}
			else if(!punching && Global.leftDown)
			{
				hVelocity = 14;
			}
			else
			{
				if(!sliding)
				{
					hVelocity = 22;
				}
				else if(!punching)
				{
					hVelocity = 26;
				}
				else if(punching || doublePunch)
				{
					hVelocity = 0;
				}
			}
		}
		else
		{
			dead = true;
			hVelocity = 0;
		}
		if(dead)
		{
			hVelocity = 0;
			textOffsetY = 0;
		}

		if(punching || doublePunch)
		{
			hVelocity = 0;
		}

		if(this.x>=50850)
		{
			won = true;
			setFrame(0,0);
			this.setFrame(0, 0);
			currentAnimation = OverworldAnimation.creatWin;
			texts = 99;
			textOffsetY = 600;
			fadeIn = true;
		}
	}
	private void storyTick()
	{
		if(!mm.flairmerExists)
		{
			if(this.x<400)
			{
				currentAnimation = OverworldAnimation.creatSadWalk;
				x+=8;
				this.flipped = true;
			}
			else if(Global.talking == 0)
			{
				currentAnimation = OverworldAnimation.creatHappyTalkStomp;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				this.flipped = false;
				SpeechBubble.talk("Hey William! I think Carl is just up ahead.../Just after the strange hills that I'm seeing! Ignacio couldn't've/gotten that far, right William!?",Global.creatFont);
			
			}
			else if(Global.talking == 2)
			{
				currentAnimation = OverworldAnimation.creatSadIdle2;
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				this.flipped = false;
				SpeechBubble.talk("...",Global.creatFont);
				AudioHandler.music.stop();
			}
			else if(Global.talking == 4)
			{
				currentAnimation = OverworldAnimation.creatSadTalk2;
				Global.talkingTo = this;
				Global.talking = 5;
				Global.disableMovement = true;
				this.flipped = false;
				SpeechBubble.talk("William?",Global.creatFont);
			}
			else if(Global.talking == 6)
			{
				currentAnimation = OverworldAnimation.creatSadIdle2;
				Global.talkingTo = this;
				Global.talking = 7;
				Global.disableMovement = true;
				this.flipped = false;
				startX = x;
				startY = y;
				SpeechBubble.talk("HEEEEEEEEEEEEEEEEERE'S FLAIRMER!!!!");
			}
			else if(Global.talking >= 8)
			{
				currentAnimation = OverworldAnimation.creatMadIdle2;
				mm.objects.add(new FlairmerChase(window,-150,this.y-50,mm));
				Global.talking = 0;
				mm.flairmerExists = true;
				AudioHandler.playMusic(AudioHandler.evilShuffle);
			}
		}
	}

}
