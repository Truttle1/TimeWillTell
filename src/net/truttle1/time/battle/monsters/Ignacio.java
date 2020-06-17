package net.truttle1.time.battle.monsters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.Attacker;
import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.SimonBattler;
import net.truttle1.time.battle.SkrappsBattler;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;

public class Ignacio extends Monster{

	private boolean beganAttack;
	private int attackType;
	private boolean talkedAboutPepper;
	private boolean usedCharge;
	private boolean usedFlop;
	private BufferedImage[] tintedAnimation;
	private int jumpVelocity;
	public Ignacio(Game window, int x, int y, BattleMode bm) {
		super(window, x, y, bm);
		super.name = "Ignacio";
		super.type = MonsterType.Ignacio;
		super.hp = 135;
		super.maxHp = 135;
		super.attack = 4;
		this.currentAnimation = BattleAnimation.iggyIdleBattle;
	}

	@Override
	public void tick() {
		if(hp>maxHp)
		{
			hp = maxHp;
		}
		super.boxOffsetX = -160;
		super.arrowOffsetX = 125;
		super.boxOffsetY = 96;
		super.boxWidth = 108;
		xp = 350;
		money = 100;
		heightMod = 120;
		information = "The Tyrant Lizard King himself: Ignacio! Max HP: 135; Attack: 4; This guy has a smashing/attack that does four damage, a belly flop attack that does five damage (yikes), as well as/a charge that does four damage to EVERYONE!! Yikesapalooza! He is also able to heal himself/using Fire-breath Chili peppers. Watch out for the fire: It does two damage...";

		if(usedCharge && currentAnimation == BattleAnimation.iggyIdleBattle)
		{
			currentAnimation = BattleAnimation.iggyIdleTaunt1;
		}
		if(usedFlop && currentAnimation == BattleAnimation.iggyIdleBattle)
		{
			currentAnimation = BattleAnimation.iggyIdleTaunt;
		}
		if(super.hp <= 0 && killedAttacker == null)
		{
			killedAttacker = Global.attacker;
		}
		if(Global.attacker != Attacker.Monsters)
		{
			bm.eyeCandy.remove(dodgeCandy);
		}
		if(super.hp<=0 && (Global.attacker != killedAttacker)) 
		{
			Quest.quests[Quest.LOMO] = Global.LOMOCONSTANT;
			bm.xpGainedSimon+=(double)xp*((double)simonDealt/damageDealt);
			bm.xpGainedWilliam+=(double)xp*((double)williamDealt/damageDealt);
			bm.xpGainedPartner[Global.currentPartner]+=(double)xp*((double)partnerDealt[Global.currentPartner]/damageDealt);
			bm.moneyGained += money;
			Global.money += money;
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.iggyDead, bm);
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
		checkSelected();
		if((Global.attacker != Attacker.Monsters || bm.attackingMonster == this.monsterID) && currentAnimation.equals(BattleAnimation.iggyFlinch) && getFrame(0)>=5)
		{
			currentAnimation = BattleAnimation.iggyIdleBattle;
		}
		if(Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			if(!beganAttack)
			{
				attackInit();
			}
			attack();
		}
	}

	@Override
	protected void attackInit() {
		this.tintedAnimation = null;
		if(hp>=100)
		{
			if(Math.random() < 0.45)
			{
				attackType = 0;
				usedFlop = false;
			}
			else
			{
				attackType = 3;
				usedCharge = false;
				usedFlop = true;
			}
		}
		else if(hp >= 45)
		{
			if(Math.random() < 0.45)
			{
				attackType = 1;
				usedCharge = false;
				usedFlop = false;
			}
			else if(Math.random() < 0.45)
			{
				attackType = 2;
				usedCharge = true;
				usedFlop = false;
			}
			else if(Math.random() < 0.45)
			{
				attackType = 3;
				usedCharge = false;
				usedFlop = true;
			}
			else
			{
				attackType = 0;
				usedCharge = false;
				usedFlop = false;
			}
		}
		else
		{
			if(Math.random() < 0.45)
			{
				attackType = 2;
				usedCharge = true;
				usedFlop = false;
			}
			else if(Math.random() < 0.55)
			{
				attackType = 3;
				usedCharge = false;
				usedFlop = true;
			}
			else
			{
				attackType = 1;
				usedCharge = false;
				usedFlop = false;
			}
		}
		
		if(Global.attacker != Attacker.Lose)
		{
			try
			{
				selectAttacking();
				beganAttack = true;
			}
			catch(Exception e) {System.out.println("HLEP THERE IS NO PLAYER! TRYING TO KEEP THE GAME RUNNING SMOOTHLY. HIGH CHANCE OF ULTIMATE FAIL!");}
			if(attackType == 2)
			{
				selectAttackingAll();
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		super.arrowRender(g);
		if(this.currentAnimation == BattleAnimation.iggyEatPepperGreen || this.currentAnimation == BattleAnimation.iggyEatPepperRed || this.currentAnimation == BattleAnimation.iggyEatPepperBlue)
		{
			this.animate(x,y-360,tintedAnimation,0,g);
		}
		else if(currentAnimation.equals(BattleAnimation.iggyIdleTaunt1))
		{

			this.animateAtSpeed(x, y, currentAnimation, 0, g,1.25);
		}
		else if(currentAnimation.equals(BattleAnimation.iggyIdleTaunt))
		{
			this.animate(x,y+20,currentAnimation,0,g);
		}
		else
		{
			this.animate(x,y,currentAnimation,0,g);
		}

		if(showingProjectile)
		{
			super.animate(projectileX, projectileY, projectile, 1, g);
		}
	}

	@Override
	public void attack() {
		System.out.println("Iggy's Turn! Attacking : " + this.charAttacking.id);
		if(attackType == 0)
		{
			smashAttack();
		}
		if(attackType == 1)
		{
			pepperAttack();
		}
		if(attackType == 2)
		{
			chargeAttack();
		}
		if(attackType == 3)
		{
			flopAttack();
		}
	}

	@Override
	public void flinch() {
		this.setFrame(0, 0);
		currentAnimation = BattleAnimation.iggyFlinch;
		
	}
	
	private void smashAttack()
	{
		if(Global.attackPhase == 0)
		{
			currentAnimation = BattleAnimation.iggyWalk;
			this.x -= 8;
			if(this.x<=charAttacking.x+80)
			{
				currentAnimation = BattleAnimation.iggySmash;
				Global.attackPhase++;
				this.setFrame(0, 0);
			}

			if(!bm.eyeCandy.contains(dodgeCandy) && x<=charAttacking.x+180 && charAttacking.id == ObjectId.WilliamBattler)
			{
				dodgeCandy = new EyeCandy(window, super.x-72, charAttacking.y-135, BattleAnimation.xIcon, bm);
				bm.eyeCandy.add(dodgeCandy);
			}
		}
		if(Global.attackPhase == 1)
		{
			if(charAttacking.id == ObjectId.WilliamBattler)
			{
				this.attackFrameBasedRange(0,22,24,4,BattleAnimation.iggyFlinch,AudioHandler.seHit2);
			}
			else
			{
				this.attackFrameBasedRange(2,22,24,4,BattleAnimation.iggyFlinch,AudioHandler.seHit2);
			}
			if(this.currentAnimation == BattleAnimation.iggySmash && getFrame(0)>=32)
			{
				this.setFrame(0,0);
				Global.attackPhase++;
			}
			if(this.currentAnimation == BattleAnimation.iggyFlinch && getFrame(0)>=5)
			{
				this.setFrame(0,0);
				Global.attackPhase++;
			}
			if(this.currentAnimation == BattleAnimation.iggyIdleBattle )
			{
				this.setFrame(0,0);
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 2)
		{
			this.currentAnimation = BattleAnimation.iggyWalk;
			this.flipped = true;
			this.x += 8;
			if(this.x>=this.startX)
			{
				this.x = startX;
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 3)
		{
			this.flipped = false;
			this.currentAnimation = BattleAnimation.iggyIdleBattle;

			if(this.monsterID >= bm.amountOfMonsters)
			{
				Global.attackPhase = 0;
				Global.attacker = Attacker.Simon;
				beganAttack = false;
				bm.selectedMonsterID = 0;
			}
			else
			{
				bm.attackingMonster++;
				beganAttack = false;
			}
		}
	}

	private void flopAttack()
	{
		if(Global.attackPhase == 0)
		{
			currentAnimation = BattleAnimation.iggyWalk;
			this.x -= 8;
			if(this.x<=charAttacking.x+120)
			{
				currentAnimation = BattleAnimation.iggyJump;
				Global.attackPhase++;
				this.setFrame(0, 0);
				jumpVelocity = 32;
			}

		}
		if(Global.attackPhase == 1)
		{
			this.x -= 14;
			this.y -= jumpVelocity;
			jumpVelocity -= 2;
			if(jumpVelocity == 0)
			{
				currentAnimation = BattleAnimation.iggyFlop0;
				this.setFrame(0, 0);
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 2)
		{
			if(this.getFrame(0) >= 17)
			{
				Global.attackPhase++;
				jumpVelocity = 4;

				if(!bm.eyeCandy.contains(dodgeCandy) && charAttacking instanceof SkrappsBattler)
				{
					dodgeCandy = new EyeCandy(window, charAttacking.x+32, charAttacking.y-40, BattleAnimation.zIcon, bm);
					bm.eyeCandy.add(dodgeCandy);
				}
				else if(!bm.eyeCandy.contains(dodgeCandy) && charAttacking instanceof SimonBattler)
				{
					dodgeCandy = new EyeCandy(window, charAttacking.x+32, charAttacking.y-40, BattleAnimation.cIcon, bm);
					bm.eyeCandy.add(dodgeCandy);
				}
			}
		}
		if(Global.attackPhase == 3)
		{
			final int damage = 5;
			this.setFrame(0, 17);
			this.y += jumpVelocity;
			jumpVelocity *= 2;
			if(this.y>charAttacking.y-280)
			{
				Global.attackPhase++;
				currentAnimation = BattleAnimation.iggyFlop1;
				this.setFrame(0, 0);

				if(charAttacking.id == ObjectId.SimonBattler && !(charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
				{
					Global.simonHP-=damage;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage);
					atk.setRepeating(false);
					bm.eyeCandy.add(atk);
					charAttacking.setFrame(0,0);
					charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					AudioHandler.playSound(AudioHandler.seBounce);
				}
				else if(charAttacking.id == ObjectId.SimonBattler && (charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
				{
					Global.simonHP-=damage-1;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage-1);
					atk.setRepeating(false);
					bm.eyeCandy.add(atk);
					AudioHandler.playSound(AudioHandler.seBounce);
				}
				else if(charAttacking.id == ObjectId.SkrappsBattler)
				{
					if(!(charAttacking.dodging && charAttacking.getFrame(0)>4 && charAttacking.getFrame(0)<18))
					{
						AudioHandler.stopSoundEffects();
						Global.partnerHP[Global.SKRAPPS]-=damage;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.skrappsFlinch;
						AudioHandler.playSound(AudioHandler.seBounce);
					}
					else if(charAttacking.dodging)
					{
						AudioHandler.stopSoundEffects();
						Global.partnerHP[Global.SKRAPPS]-=damage-1;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage-1);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						AudioHandler.playSound(AudioHandler.seBounce);
					}
				}
				else if(charAttacking.id == ObjectId.WilliamBattler)
				{
					if(charAttacking.dodging)
					{
						AudioHandler.stopSoundEffects();
						Global.williamHP-=damage*2;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage*2);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,13);
						charAttacking.currentAnimation = BattleAnimation.williamFailAnimation;
						bm.eyeCandy.remove(dodgeCandy);
						AudioHandler.playSound(AudioHandler.seBounce);
						
					}
					else if(!charAttacking.dodging)
					{
						AudioHandler.stopSoundEffects();
						Global.williamHP-=damage;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.canDodge = true;
						charAttacking.currentAnimation = BattleAnimation.williamHitAnimation;
						bm.eyeCandy.remove(dodgeCandy);
						AudioHandler.playSound(AudioHandler.seBounce);
						
					}
				}
			}
		}
		if(Global.attackPhase == 4)
		{
			bm.eyeCandy.remove(dodgeCandy);
			if(this.getFrame(0)>12)
			{
				Global.attackPhase++;
				this.jumpVelocity = 20;
			}
		}
		if(Global.attackPhase == 5)
		{
			this.x += 12;
			this.y -= jumpVelocity;
			jumpVelocity -= 2;
			if(this.getFrame(0)>34)
			{
				this.setFrame(0, 35);
			}
			if(this.y>=this.startY)
			{
				currentAnimation = BattleAnimation.iggyWalk;
				this.setFrame(0, 0);
				this.y = startY;
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 6)
		{
			this.flipped = true;
			this.x += 6;
			if(this.x >= this.startX)
			{

				this.flipped = false;
				this.currentAnimation = BattleAnimation.iggyIdleBattle;

				if(this.monsterID >= bm.amountOfMonsters)
				{
					Global.attackPhase = 0;
					Global.attacker = Attacker.Simon;
					beganAttack = false;
					bm.selectedMonsterID = 0;
				}
				else
				{
					bm.attackingMonster++;
					beganAttack = false;
				}
			}
		}
		
		
	}
	private void pepperAttack()
	{
		if(Global.attackPhase == 0)
		{
			if(talkedAboutPepper)
			{
				Global.attackPhase++;
				this.setFrame(0, 0);
			}
			else
			{
				if(Global.talking == 0)
				{
					Global.talkingTo = this;
					currentAnimation = BattleAnimation.iggyTaunt2;
					Global.talking = 1;
					SpeechBubble.talk("It's supper time!!! Tonight on the menu: Ignacio's Extra Spicy Hot Chili Peppers!/BWARR HARR HARR!!! Watch out for the flames, you puny things, these peppers have a/side effect of causing fire breath!!!",Global.iggyFont);
				}
				if(Global.talking == 2)
				{
					Global.attackPhase++;
					this.setFrame(0, 0);
					Global.talking = 0;
					talkedAboutPepper = true;
				}
			}
		}
		if(Global.attackPhase == 1)
		{
			Global.talking = 0;
			Global.disableMovement = false;
			if(charAttacking.id == ObjectId.SimonBattler && tintedAnimation == null)
			{
				tintedAnimation = BattleAnimation.iggyEatPepperRed;
				/*
				for(int i=20; i<90;i++)
				{
					tintedAnimation[i] = this.replaceTwoColors(new Color(0,255,0), new Color(204,100,39),new Color(35,158,70), new Color(136,67,26), tintedAnimation[i]);
				}*/
				currentAnimation = tintedAnimation;
			}
			if(charAttacking.id == ObjectId.WilliamBattler && tintedAnimation == null)
			{
				tintedAnimation = BattleAnimation.iggyEatPepperGreen;
				currentAnimation = tintedAnimation;
			}
			if(charAttacking.id == ObjectId.SkrappsBattler && tintedAnimation == null)
			{
				tintedAnimation = BattleAnimation.iggyEatPepperBlue;
				/*
				for(int i=20; i<90;i++)
				{
					tintedAnimation[i] = this.replaceTwoColors(new Color(0,255,0), Color.blue,new Color(35,158,70), Color.blue.darker(), tintedAnimation[i]);
				}*/
				currentAnimation = tintedAnimation;
			}

			if(getFrame(0) == 80)
			{
				AudioHandler.playSound(AudioHandler.seChomp);
			}
			if(getFrame(0)==98)
			{
				hp+=12;
				EyeCandy atk = new EyeCandy(bm.window, x+32, y-64, BattleAnimation.heart, bm,true,12,true,1,0,0);
				atk.setRepeating(true);
				bm.eyeCandy.add(atk);
			}
			if(getFrame(0) == 131)
			{
				setFrame(0, 0);
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 2)
		{
			currentAnimation = BattleAnimation.iggyFireBreath;
			if(getFrame(0)==27)
			{
				AudioHandler.playSound(AudioHandler.seFire);
				showingProjectile = true;
				this.projectileX = this.x+40;
				this.projectileY = this.y+120;
				Global.attackPhase++;
				tintedAnimation = null;
			}
		}
		if(Global.attackPhase == 3)
		{
			if(getFrame(0)>=47)
			{
				this.setFrame(0,0);
				currentAnimation = BattleAnimation.iggyIdleTaunt2;
			}
			attackProjectile(-10,2,BattleAnimation.fire,375,2,AudioHandler.seSizzle, BattleAnimation.iggyIdleTaunt2);
			
		}
		if(Global.attackPhase == 4)
		{
			this.flipped = false;
			this.currentAnimation = BattleAnimation.iggyIdleBattle;

			if(this.monsterID >= bm.amountOfMonsters)
			{
				Global.disableMovement = false;
				Global.attackPhase = 0;
				Global.attacker = Attacker.Simon;
				beganAttack = false;
				bm.selectedMonsterID = 0;
			}
			else
			{
				Global.disableMovement = false;
				Global.attackPhase = 0;
				bm.attackingMonster++;
				beganAttack = false;
			}
		}
	}

	private void chargeAttack()
	{
		int damage = 4;
		if(Global.attackPhase == 0)
		{
			this.setFrame(0, 0);
			currentAnimation = BattleAnimation.iggyWindUp;
			Global.attackPhase++;
		}
		if(Global.attackPhase == 1)
		{
			if(this.getFrame(0)>=23)
			{
				Global.attackPhase++;
				this.setFrame(0, 0);
				currentAnimation = BattleAnimation.iggyRun;
			}
		}
		if(Global.attackPhase == 2)
		{
			this.x -= 16;
			if(this.x <= charAttacking.x+100)
			{
				charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
				Global.simonHP -= damage;
				EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage);
				atk.setRepeating(true);
				bm.eyeCandy.add(atk);
				charAttacking.setFrame(0,0);
				charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
				charAttacking.setFrame(0,0);
				Global.attackPhase++;
				AudioHandler.playSound(AudioHandler.seDodge);
			}
		}
		if(Global.attackPhase == 3)
		{
			this.x -= 16;
			if(charAttacking1!= null && this.x <= charAttacking1.x+50)
			{
				if(charAttacking1.dodging)
				{

					charAttacking1.currentAnimation = BattleAnimation.williamHitAnimation;
					Global.williamHP -= damage*2;
					EyeCandy atk = new EyeCandy(window, charAttacking1.x+100, charAttacking1.y+64,BattleAnimation.hitAny, bm, true, damage*2);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking1.setFrame(0,0);
					Global.attackPhase++;
				}
				else
				{
					charAttacking1.currentAnimation = BattleAnimation.williamHitAnimation;
					Global.williamHP -= damage;
					EyeCandy atk = new EyeCandy(window, charAttacking1.x+100, charAttacking1.y+64,BattleAnimation.hitAny, bm, true, damage);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking1.setFrame(0,0);
					Global.attackPhase++;
				}
				AudioHandler.playSound(AudioHandler.seDodge);
				
			}
		}
		if(Global.attackPhase == 4)
		{
			this.x -= 16;
			if(charAttacking2!= null && this.x <= charAttacking2.x+50)
			{
				charAttacking2.currentAnimation = BattleAnimation.skrappsFlinch;
				Global.partnerHP[Global.SKRAPPS] -= damage;
				EyeCandy atk = new EyeCandy(window, charAttacking2.x+100, charAttacking2.y+64,BattleAnimation.hitAny, bm, true, damage);
				atk.setRepeating(true);
				bm.eyeCandy.add(atk);
				charAttacking2.setFrame(0,0);
				Global.attackPhase++;
				AudioHandler.playSound(AudioHandler.seDodge);
				
			}
		}
		if(Global.attackPhase == 5)
		{
			this.x -= 16;
			if(this.x <= 0-320)
			{
				this.x = 1024;
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 6)
		{
			this.x -= 16;
			if(this.x <= this.startX)
			{
				this.x = this.startX;
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 7)
		{

			this.flipped = false;
			this.currentAnimation = BattleAnimation.iggyIdleBattle;

			if(this.monsterID >= bm.amountOfMonsters)
			{
				Global.disableMovement = false;
				Global.attackPhase = 0;
				Global.attacker = Attacker.Simon;
				beganAttack = false;
				bm.selectedMonsterID = 0;
			}
			else
			{
				Global.disableMovement = false;
				Global.attackPhase = 0;
				bm.attackingMonster++;
				beganAttack = false;
			}
		}
	}
}
