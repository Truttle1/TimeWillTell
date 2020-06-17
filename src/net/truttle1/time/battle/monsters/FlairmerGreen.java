package net.truttle1.time.battle.monsters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.Attacker;
import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.SimonBattler;
import net.truttle1.time.battle.SkrappsBattler;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;

public class FlairmerGreen extends Monster{

	BufferedImage[] idleAnimation;
	private boolean beganAttack = false;
	private int bossPhase = 0;
	private int attackType = 0;
	private boolean walkAttack = true;
	private boolean healed = false;
	private boolean healInit = false;
	private int fireX;
	private int jumpVelocity;
	private double hammerSpeed;
	private boolean jumpSound = false;
	public FlairmerGreen(Game window, int x, int y, BattleMode bm) {
		super(window,x,y,bm);
		super.boxOffsetX = -28;
		super.boxOffsetY = 96;
		super.boxWidth = 140;
		if(Global.bossBattle)
		{
			super.hp = 20;
			super.maxHp = 20;
		}
		else
		{
			super.hp = 22;
			super.maxHp = 22;
		}
		super.attack = 1;
		super.name = "Flairmer G";
		super.type = MonsterType.Flairmer2;
		currentAnimation = BattleAnimation.flareGreenIdle;
		dodgeType = 0;
		if(Global.bossBattle)
		{
			xp = 15;
			money = 13;
		}
		else
		{
			xp = 11;
			money = 7;
		}
		money = 4;
		heightMod = 80;
	}


	
	@Override
	public void tick() {

		if(Global.bossBattle)
		{
			information = "This is called a Flairmer. Max HP: 20; Attack: 2; These Flairmers carry around hammers./Those hammers are DESIGNED to hurt, so even if Simon blocks, he will still probably/take damage! Green Flairmers also have a standard jump attack that/does one HP of damage, but it can't be countered by William.";		
		}
		else
		{
			information = "This is called a Flairmer. Max HP: 22; Attack: 2; These Flairmers carry around/hammers. Those hammers are DESIGNED to hurt, so even if Simon blocks, he will/still probably take damage! Green Flairmers also have a standard jump attack that/does one HP of damage, but it can't be countered by William.";
		}
		if(Quest.quests[Quest.PYRUZ_W]<2)
		{
			Quest.quests[Quest.PYRUZ_W] = 2;
		}
		if(super.hp <= 0 && killedAttacker == null)
		{
			killedAttacker = Global.attacker;
		}
		if(super.hp<=0 && (Global.attacker != killedAttacker)) 
		{
			bm.xpGainedSimon+=(double)xp*((double)simonDealt/damageDealt);
			bm.xpGainedWilliam+=(double)xp*((double)williamDealt/damageDealt);
			bm.xpGainedPartner[Global.currentPartner]+=(double)xp*((double)partnerDealt[Global.currentPartner]/damageDealt);
			//Global.partnerXP[Global.currentPartner] += bm.xpGainedPartner[Global.currentPartner];Global.simonXP += bm.xpGainedSimon;
			//Global.williamXP += bm.xpGainedWilliam;
			bm.moneyGained += money;
			Global.money += money;
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.flareGreenDie, bm);
			die.setRepeating(false);
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
		checkSelected();
		if(Global.attacker != Attacker.Monsters && currentAnimation == BattleAnimation.flareGreenFlinch && getFrame(0)>=5)
		{
			currentAnimation = BattleAnimation.flareGreenIdle;
		}
		if((hp > 0 || Global.attackPhase >= 1) && Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{

			if(!beganAttack)
			{
				attackInit();
				this.setFrame(0, 0);
				setFrame(0, 0);
			}

			if(attackType == 0)
			{
				attack();
			}
			else if(attackType == 1)
			{
				hammer();
			}
		}
	}
	protected void hammer()
	{
		if(Global.attackPhase == 0)
		{
			currentAnimation = BattleAnimation.flareGreenGrabAxe;
			if(getFrame(0)>115)
			{
				this.setFrame(0, 0);
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 1)
		{
			charAttacking.canDodge = true;
			currentAnimation = BattleAnimation.flareGreenWalk2;
			x-=8;
			if(!bm.eyeCandy.contains(dodgeCandy) && x<=charAttacking.x+180 && charAttacking.id == ObjectId.WilliamBattler)
			{
				dodgeCandy = new EyeCandy(window, super.x-72, charAttacking.y-135, BattleAnimation.xIcon, bm);
				bm.eyeCandy.add(dodgeCandy);
			}
			if(x<=attackWalkDistance())
			{
				x = attackWalkDistance();
				setFrame(0, 0);
				currentAnimation = BattleAnimation.flareGreenWhack;
				Global.attackPhase++;
				
			}
		}
		else if(Global.attackPhase == 2)
		{
			if(!currentAnimation.equals(BattleAnimation.flareGreenFlinch))
			{
				/*
				if(getFrame(0) == 1)
				{
					if(charAttacking.id == ObjectId.SimonBattler)
					{
						dodgeCandy = new EyeCandy(window, super.x-72, super.y-72, BattleAnimation.cIcon, bm);
						bm.eyeCandy.add(dodgeCandy);
					}
				}
				if(getFrame(0) == 1)
				{
					if(charAttacking.id == ObjectId.SkrappsBattler)
					{
						dodgeCandy = new EyeCandy(window, super.x-72, super.y-72, BattleAnimation.zIcon, bm);
						bm.eyeCandy.add(dodgeCandy);
					}
				}
				
				if(!hit && getFrame(0) >= 16 && getFrame(0) <= 18 && charAttacking.id == ObjectId.SimonBattler)
				{
					hit = true;
					bm.eyeCandy.remove(dodgeCandy);
					if(!(charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
					{
						Global.simonHP-=2;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitTwoHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					}

					if((charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
					{
						Global.simonHP-=1;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
					}
				}
				if(!hit && getFrame(0) >= 16 && getFrame(0) <= 18 && charAttacking.id == ObjectId.SkrappsBattler)
				{
					hit = true;
					bm.eyeCandy.remove(dodgeCandy);
					if(!(charAttacking.dodging && charAttacking.getFrame(0)>4 && charAttacking.getFrame(0)<18))
					{
						Global.partnerHP[Global.SKRAPPS]-=2;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitTwoHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.skrappsFlinch;
					}

					if((charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
					{
						Global.partnerHP[Global.SKRAPPS]-=1;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
					}
				}
				if(charAttacking.id == ObjectId.WilliamBattler)
				{
					if(getFrame(0) < 16 && charAttacking.currentAnimation != BattleAnimation.williamFailAnimation && charAttacking.dodging && charAttacking.getFrame(0)>12 && charAttacking.getFrame(0)<16)
					{
						super.setFrame(0,0);
						setFrame(0,0);
						hp--;
						EyeCandy atk = new EyeCandy(window, x+100, y+64, BattleAnimation.hitOneHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.canDodge = true;
						currentAnimation = BattleAnimation.flareGreenFlinch;
						bm.eyeCandy.remove(dodgeCandy);
					}
					else if(!hit && getFrame(0) >= 16 && getFrame(0) <= 18 && charAttacking.dodging)
					{
						hit = true;
						Global.williamHP-=4;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitAny, bm,true,4);
						atk.setRepeating(true);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,13);
						charAttacking.currentAnimation = BattleAnimation.williamFailAnimation;
						bm.eyeCandy.remove(dodgeCandy);
					}
					else if(!hit && getFrame(0) >= 16 && getFrame(0) <= 18 && !charAttacking.dodging)
					{
						hit = true;
						Global.williamHP-=2;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitTwoHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.canDodge = true;
						charAttacking.currentAnimation = BattleAnimation.williamHitAnimation;
						bm.eyeCandy.remove(dodgeCandy);
					}
				}*/
				attackFrameBasedRange(1,15,19,2,BattleAnimation.flareGreenFlinch,AudioHandler.seHit);
				if(getFrame(0)>=70)
				{
					x = attackWalkDistance();
					setFrame(0, 0);
					currentAnimation = BattleAnimation.flareGreenWalk;
					this.flipped = true;
					Global.attackPhase++;
					bm.eyeCandy.remove(dodgeCandy);
				}
			}
			else if(getFrame(0)>=5)
			{
				this.flipped = true;
				Global.attackPhase++;
			}
		}
		else if(Global.attackPhase == 3)
		{
			currentAnimation = BattleAnimation.flareGreenWalk;
			x+=10;
			if(x>=startX)
			{
				x = startX;
				setFrame(0, 0);
				currentAnimation = BattleAnimation.flareGreenIdle;
				Global.attackPhase = 0;
				if(this.monsterID >= bm.amountOfMonsters)
				{
					Global.attacker = Attacker.Simon;
					beganAttack = false;
					bm.selectedMonsterID = 0;
				}
				else
				{
					bm.attackingMonster++;
					beganAttack = false;
				}
				this.flipped = false;
				
			}
		}
	}
	
	@Override
	public void render(Graphics g) {
		super.arrowRender(g);

		if(currentAnimation.equals(BattleAnimation.flareGreenJump) && this.getFrame(0)>=4)
		{
			this.setFrame(0, 4);
		}
		if(currentAnimation != null)
		{
			if(currentAnimation[2].getWidth()<getFrame(0) || currentAnimation[2].getWidth() == 0)
			{
				setFrame(0,0);
			}
			if(currentAnimation.equals(BattleAnimation.flareGreenGrabAxe))
			{
				super.animateAtSpeed(super.x,super.y,currentAnimation,0,g,hammerSpeed);
			}
			else if(currentAnimation.equals(BattleAnimation.flareGreenWhack) && this.getFrame(0)<20)
			{
				super.animateAtSpeed(super.x,super.y,currentAnimation,0,g,hammerSpeed);
			}
			else
			{
				try
				{
						super.animate(super.x,super.y,currentAnimation,0,g);
				}
				catch(Exception e) {this.setFrame(0, 0); 
				setFrame(0,0);
				e.printStackTrace();}
			}
		}
	
	}

	@Override
	public void attack() {
		dodgeType = 1;
		if(Global.attackPhase == 0)
		{
			charAttacking.canDodge = true;
			currentAnimation = BattleAnimation.flareGreenWalk;
			x-=10;
			if(x<=attackWalkDistance()+128)
			{
				x = attackWalkDistance()+128;
				jumpVelocity = 25;
				setFrame(0, 0);
				currentAnimation = BattleAnimation.flareGreenJump;
				Global.attackPhase++;
				
			}
		}
		if(Global.attackPhase == 1)
		{

			if(charAttacking.id == ObjectId.SimonBattler)
			{
				x-=8;
			}
			else
			{
				x-=10;
			}
				y-=jumpVelocity*2;
				jumpVelocity-=2;
				if(jumpVelocity<8 && (this.charAttacking instanceof SimonBattler))
				{

					if(!bm.eyeCandy.contains(dodgeCandy))
					{
						dodgeCandy = new EyeCandy(window, charAttacking.x+72, charAttacking.y-40, BattleAnimation.cIcon, bm);
						bm.eyeCandy.add(dodgeCandy);
					}
				}
				if(jumpVelocity<8 && (this.charAttacking instanceof SkrappsBattler))
				{

					if(!bm.eyeCandy.contains(dodgeCandy))
					{
						dodgeCandy = new EyeCandy(window, charAttacking.x+32, charAttacking.y-40, BattleAnimation.zIcon, bm);
						bm.eyeCandy.add(dodgeCandy);
					}
				}
				if(jumpVelocity>=0 && !jumpSound)
				{
					AudioHandler.playSound(AudioHandler.seJump3);
					jumpSound = true;
				}
				if(jumpVelocity<=0)
				{
					if(jumpSound)
					{
						AudioHandler.stopSoundEffects();
						AudioHandler.playSound(AudioHandler.seFall);
						jumpSound = false;
					}
					currentAnimation = BattleAnimation.flareGreenLand;
				}
				if(jumpVelocity<0 && y>=charAttacking.y-60)
				{
					Global.attackPhase++;
					jumpVelocity = 12;
					if(charAttacking.id == ObjectId.SimonBattler && !(charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
					{
						Global.simonHP--;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
						AudioHandler.playSound(AudioHandler.seCrash);
					}
					else if(charAttacking.id == ObjectId.SkrappsBattler)
					{
						if(!(charAttacking.dodging && charAttacking.getFrame(0)>4 && charAttacking.getFrame(0)<18))
						{
							AudioHandler.stopSoundEffects();
							Global.partnerHP[Global.SKRAPPS]--;
							EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
							atk.setRepeating(false);
							bm.eyeCandy.add(atk);
							charAttacking.setFrame(0,0);
							charAttacking.currentAnimation = BattleAnimation.skrappsFlinch;
							AudioHandler.playSound(AudioHandler.seCrash);
						}
					}
					else if(charAttacking.id == ObjectId.WilliamBattler)
					{
						if(charAttacking.dodging)
						{
							AudioHandler.stopSoundEffects();
							Global.williamHP-=2;
							EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitTwoHP, bm);
							atk.setRepeating(false);
							bm.eyeCandy.add(atk);
							charAttacking.setFrame(0,13);
							charAttacking.currentAnimation = BattleAnimation.williamFailAnimation;
							bm.eyeCandy.remove(dodgeCandy);
							AudioHandler.playSound(AudioHandler.seCrash);
							
						}
						else if(!charAttacking.dodging)
						{
							AudioHandler.stopSoundEffects();
							Global.williamHP--;
							EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
							atk.setRepeating(false);
							bm.eyeCandy.add(atk);
							charAttacking.setFrame(0,0);
							charAttacking.canDodge = true;
							charAttacking.currentAnimation = BattleAnimation.williamHitAnimation;
							bm.eyeCandy.remove(dodgeCandy);
							AudioHandler.playSound(AudioHandler.seCrash);
							
						}
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
				currentAnimation = BattleAnimation.flareGreenWalk;
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
				currentAnimation = BattleAnimation.flareGreenIdle;
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

	protected void attackInit()
	{

		jumpSound = false;
		Global.attackPhase = 0;
		if(Math.random()<.5)
		{
			attackType = 1;
			hammerSpeed = (Math.random()*2)+0.6;
			hit = false;
		}
		else
		{
			attackType = 0;
			hammerSpeed = (Math.random()*2)+0.6;
			hit = false;
		}

		selectAttacking();
		beganAttack = true;
	}

	@Override
	public void flinch() {
		setFrame(0,0);
		window.battleMode.selectedMonster.currentAnimation = BattleAnimation.flareGreenFlinch;
		
		
	}
	
	

}
