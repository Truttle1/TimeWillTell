package net.truttle1.time.battle.monsters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.Attacker;
import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;

public class WilliamEnemy2 extends Monster{

	BufferedImage[] idleAnimation;
	boolean beganAttack = false;
	int flinchTime = 0;
	int jumpVelocity = 0;
	private EyeCandy dodgeCandy;
	public int dartsThatExist = 0;
	public int dartX[] = new int[9];
	private int chosenAttack;
	private boolean talkedAboutDarts = false;
	public WilliamEnemy2(Game window, int x, int y, BattleMode bm) {
		super(window, x, y, bm);
		super.boxOffsetX = -15;
		super.boxOffsetY = 96;
		super.boxWidth = 111;
		super.hp = 8;
		super.maxHp = 8;
		super.attack = 1;
		super.name = "William";
		super.type = MonsterType.William2;
		super.spikeFront = true;
		super.information = "Wow, this boss is a total douchewalrus!";
		currentAnimation = BattleAnimation.williamSpikeIdle;
		dodgeType = 1;
		xp = 20;
		money = 0;
	}
	private void attackDart()
	{
		if(Global.attackPhase == 0)
		{
			dodgeType = 0;
			charAttacking.canDodge = true;
			currentAnimation = BattleAnimation.williamSpikeDart;
			if(getFrame(0) == 15)
			{
				dartX[dartsThatExist] = this.x-10;
				dartsThatExist++;
				AudioHandler.playSound(AudioHandler.sePew);
				if(dartsThatExist==3 || Math.random()<0.66)
				{
					Global.attackPhase++;
				}
			}
		}
		if(Global.attackPhase == 1)
		{
			if(currentAnimation == BattleAnimation.williamSpikeDart && (getFrame(0)<=15 ||getFrame(0)==23))
			{
				currentAnimation = BattleAnimation.williamSpikeIdle;
			}
		}
		if(Global.attackPhase == 2 && (charAttacking.id == ObjectId.SimonBattler && charAttacking.currentAnimation == BattleAnimation.simonIdleAnimation))
		{
			if(bm.eyeCandy.contains(dodgeCandy))
			{
				bm.eyeCandy.remove(dodgeCandy);
			}
			setFrame(0,0);
			this.flipped = false;
			x = startX;
			Global.attackPhase = 0;
			beganAttack = false;
			currentAnimation = BattleAnimation.williamSpikeIdle;
			if(this.monsterID >= bm.amountOfMonsters)
			{
				Global.attacker = Attacker.Simon;
			}
			else
			{
				bm.attackingMonster++;
			}
		}
		for(int i=0; i<dartsThatExist;i++)
		{
			dartX[i] -= 24;
			System.out.println(i + "::" + dartsThatExist);
			if(charAttacking.id == ObjectId.SimonBattler)
			{
				if(dartX[i] <= charAttacking.x+300 && dartX[i] >= -1200 )
				{
					if(!bm.eyeCandy.contains(dodgeCandy))
					{
						dodgeCandy = new EyeCandy(window, charAttacking.x+72, charAttacking.y-64, BattleAnimation.cIcon, bm);
						bm.eyeCandy.add(dodgeCandy);
					}
				}
				if(dartX[i] <= charAttacking.x+100 && dartX[i] >= -1200 )
				{
					if(charAttacking.dodging && charAttacking.getFrame(0)>2 && charAttacking.getFrame(0)<10)
					{
						if(bm.eyeCandy.contains(dodgeCandy))
						{
							bm.eyeCandy.remove(dodgeCandy);
						}
						charAttacking.canDodge = true;
						AudioHandler.playSound(AudioHandler.seHit2);
					}
					else
					{
						if(bm.eyeCandy.contains(dodgeCandy))
						{
							bm.eyeCandy.remove(dodgeCandy);
						}
						AudioHandler.playSound(AudioHandler.seDodge);
						Global.simonHP--;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					}
					dartX[i] = -1300;
					if(i <= dartsThatExist-1 && Global.attackPhase == 1)
					{
						Global.attackPhase++;
					}
				}
			}
		}
	}
	@Override
	public void attack() {
		if(Global.attackPhase == 0)
		{
			dodgeType = 1;
			charAttacking.canDodge = true;
			currentAnimation = BattleAnimation.williamSpikeWalk;
			x-=10;
			if(x<=attackWalkDistance()+64)
			{
				AudioHandler.playSound(AudioHandler.seJump);
				x = attackWalkDistance()+64;
				setFrame(0, 0);
				currentAnimation = BattleAnimation.williamSpikeJump;
				Global.attackPhase++;
				
			}
		}
		if(Global.attackPhase == 1)
		{

				x-=8;
				y-=jumpVelocity*2;
				jumpVelocity-=2;
				if(jumpVelocity<8)
				{

					if(!bm.eyeCandy.contains(dodgeCandy))
					{
						dodgeCandy = new EyeCandy(window, super.x-72, super.y, BattleAnimation.cIcon, bm);
						bm.eyeCandy.add(dodgeCandy);
					}
				}
				if(jumpVelocity==0)
				{
					currentAnimation = BattleAnimation.williamSpikeLand;
				}
				if(jumpVelocity<0 && y>=charAttacking.y-24)
				{
					Global.attackPhase++;
					jumpVelocity = 12;
					if(!(charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
					{
						AudioHandler.playSound(AudioHandler.seHit);
						Global.simonHP--;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					}
				}
		}
		if(Global.attackPhase == 2)
		{
			if(bm.eyeCandy.contains(dodgeCandy))
			{
				bm.eyeCandy.remove(dodgeCandy);
			}
			x+=16;
			y-=jumpVelocity*2;
			jumpVelocity-=2;
			if(y>=startY)
			{
				y = startY;
				Global.attackPhase++;
				jumpVelocity = 8;
				AudioHandler.playSound(AudioHandler.seJump2);
			}
		}
		if(Global.attackPhase == 3)
		{
			x+=8;
			y-=jumpVelocity*2;
			jumpVelocity-=2;
			if(y>=startY)
			{
				y = startY;
				Global.attackPhase++;
				jumpVelocity = 4;
				AudioHandler.playSound(AudioHandler.seJump2);
			}
		}
		if(Global.attackPhase == 4)
		{
			x+=4;
			y-=jumpVelocity*2;
			jumpVelocity-=2;
			if(y>=startY)
			{
				y = startY;
				Global.attackPhase++;
				currentAnimation = BattleAnimation.williamSpikeWalk;
			}
		}
		if(Global.attackPhase == 5)
		{
			if(x>startX+8)
			{
				this.flipped = false;
				x-=12;
			}
			else if(x<startX-8)
			{
				this.flipped = true;
				x+=12;
			}
			else
			{
				setFrame(0,0);
				this.flipped = false;
				x = startX;
				Global.attackPhase = 0;
				beganAttack = false;
				currentAnimation = BattleAnimation.williamSpikeIdle;
				if(this.monsterID >= bm.amountOfMonsters)
				{
					Global.attacker = Attacker.Simon;
				}
				else
				{
					bm.attackingMonster++;
				}
			}
		}
	}

	@Override
	public void tick() {
		Global.disableMovement = false;
		checkSelected();
		if(super.hp<=0 && Global.attacker == Attacker.Monsters) 
		{
			Quest.quests[Quest.LOMOBANK]=5;
			bm.xpGainedSimon+=xp;
			bm.moneyGained += money;
			Global.money += money;
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.williamDeadAnimation, bm);
			die.setRepeating(true);
			bm.eyeCandy.add(die);
			for(int i = super.monsterID; i<=bm.amountOfMonsters;++i)
			{
				for(int j=0; j<bm.objects.size();j++)
				{
					if(bm.objects.get(j) instanceof Monster)
					{
						Monster m = (Monster) bm.objects.get(j);
						if(m.monsterID == i)
						{
							m.monsterID--;
						}
					}
				}
			}
			bm.amountOfMonsters--;
			bm.attackingMonster = 0;
			bm.objects.remove(this);
		}
		if(currentAnimation.equals(BattleAnimation.williamSpikeHit))
		{
			flinchTime++;
			if(flinchTime>Global.flinchAmount)
			{
				setFrame(0,0);
				currentAnimation = BattleAnimation.williamSpikeIdle;
			}
		}
		if(Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			if(!beganAttack)
			{
				attackInit();
			}
			if(chosenAttack == 1)
			{
				if(!talkedAboutDarts)
				{
					if(Global.talking == 0)
					{
						currentAnimation = BattleAnimation.williamSpikeTalk;
						Global.talkingTo = this;
						Global.talking = 1;
						SpeechBubble.talk("Hey mammalian freakazoid! Check out what I brought! That's right! DARTS!/Bet this is making you kinda scared, huh!?",Global.willFont);
					}
					else if(Global.talking == 2)
					{
						Global.talking = 0;
						talkedAboutDarts = true;
					}
				}
				else
				{	
					attackDart();
				}
			}
			else
			{	
				attack();
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		super.arrowRender(g);
		if(currentAnimation[2].getWidth()<getFrame(0))
		{
			setFrame(0,0);
		}
		for(int i=0; i<dartsThatExist;i++)
		{
			g.drawImage(BattleAnimation.dart[0],dartX[i],this.y+40,null);
		}
		super.animate(super.x,super.y,currentAnimation,0,g);
	}

	@Override
	public void flinch() 
	{
		setFrame(0,0);
		currentAnimation = BattleAnimation.williamSpikeHit;
		flinchTime = 0;
	}
	protected void attackInit()
	{
		jumpVelocity = 20;
		beganAttack = true;
		setFrame(0,0);
		dartsThatExist = 0;

		if(Math.random()<.5)
		{
			chosenAttack = 1;
		}
		else
		{
			chosenAttack = 0;
		}
		for(int i=0; i<bm.objects.size(); i++)
		{
			if (bm.objects.get(i).id == ObjectId.SimonBattler)
			{
				this.charAttacking = bm.objects.get(i);
			}
		}
	}

}
