package net.truttle1.time.battle.monsters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.Attacker;
import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.SkrappsBattler;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;

public class FlairmerRed extends Monster{

	BufferedImage[] idleAnimation;
	private boolean beganAttack = false;
	private int bossPhase = 0;
	private int attackType = 0;
	private boolean walkAttack = true;
	private boolean healed = false;
	private boolean healInit = false;
	private boolean missed;
	private int fireX;
	private double fireY;
	private boolean showingFire = false;
	private EyeCandy dodgeCandy;
	private int healVal;
	public FlairmerRed(Game window, int x, int y, BattleMode bm) {
		super(window,x,y,bm);
		super.boxOffsetX = -28;
		super.boxOffsetY = 96;
		super.boxWidth = 140;
		if(Global.bossBattle)
		{
			super.hp = 8;
			super.maxHp = 8;
			healVal = 5;
		}
		else
		{
			healVal = -1;
			super.hp = 13;
			super.maxHp = 13;
		}
		super.attack = 1;
		super.name = "Flairmer R";
		super.type = MonsterType.Flairmer1;
		currentAnimation = BattleAnimation.flareRedIdle;
		dodgeType = 0;
		if(Global.bossBattle)
		{
			xp = 12;
			money = 10;
		}
		else
		{
			xp = 9;
			money = 6;
		}
		heightMod = 80;
	}


	
	@Override
	public void tick() {

		if(this.monsterID%2 != 0 && currentAnimation == BattleAnimation.flareRedIdle)
		{
			currentAnimation = BattleAnimation.flareRedIdle2;
		}
		if(Global.bossBattle)
		{
			information = "This is called a Flairmer. Max HP: 8; Attack: 1; Flairmers have two attacks: a punching/attack that can be countered, and a fireball attack that cannot. They also carry/around Healy Rocks, which heal two HP. Flarimer history is considered to be/so interesting that anyone who studies it becomes addicted to it's study...strange...";
		}
		else
		{
			information = "This is called a Flairmer. Max HP: 13; Attack: 1; Flairmers have two attacks: a punching/attack that can be countered, and a fireball attack that cannot./Flarimer history is considered to be so interesting that anyone who studies it becomes/addicted to studying it...strange...";
		}
		if(Quest.quests[Quest.LOMOBANK]<16)
		{
			Quest.quests[Quest.LOMOBANK] = 16;
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
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.flareRedDie, bm);
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
		if((Global.attacker != Attacker.Monsters || bm.attackingMonster != this.monsterID) && currentAnimation == BattleAnimation.flareRedFlinch && getFrame(0)>=5)
		{
			currentAnimation = BattleAnimation.flareRedIdle;
		}
		if((hp > 0 || Global.attackPhase >= 1) && Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			if(!beganAttack && hp>0)
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
				heal();
			}
			else if(attackType == 2)
			{
				flame();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		super.arrowRender(g);
		if(showingFire)
		{
			super.animate(fireX, (int)fireY, BattleAnimation.fire, 1, g);
		}
		if(currentAnimation == BattleAnimation.flareRedPunch)
		{
			super.animateAtSpeed(super.x,super.y,currentAnimation,0,g,2);
		}
		else if(Global.attackPhase == 0 && Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID && currentAnimation != BattleAnimation.flareRedTalk3)
		{
			super.animateAtSpeed(super.x,super.y,currentAnimation,0,g,2);
		}
		else if(Global.attackPhase == 2 && Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID && currentAnimation != BattleAnimation.flareRedWait)
		{
			super.animateAtSpeed(super.x,super.y,currentAnimation,0,g,2);
		}
		else
		{
			if(currentAnimation[2].getWidth()<getFrame(0))
			{
				setFrame(0,0);
			}
			super.animate(super.x,super.y,currentAnimation,0,g);
		}
	}
	protected void heal()
	{
		if(Global.attackPhase == 0)
		{
			if(Global.bossBattle && Global.talking == 0)
			{
				Global.talkSound = 0;
				currentAnimation = BattleAnimation.flareRedTalk3;
				if(Quest.quests[Quest.PYRUZ_W] == 0)
				{
					Global.talkingTo = this;
					Global.talking = 1;
					Global.disableMovement = true;
					SpeechBubble.talk("BWARR HARR HARR HARR!!! Puny baby think he DEFEATED Flairmer?/Well, Flairmer have little SURPRISE!/SAY HELLO TO MY LITTLE FRIEND, THE HEALY ROCK!!!");
				}
				else
				{
					Global.attackPhase++;
				}
			}
			else if(Quest.quests[Quest.PYRUZ_W] == 0 && Global.talking == 0)
			{
				Global.attackPhase++;
			}
			else if(!Global.bossBattle)
			{
				Global.attackPhase++;
			}
			if(Global.talking == 2)
			{
				Global.talking = 0;
				Global.disableMovement = false;
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 1)
		{
			Global.disableMovement = false;
			currentAnimation = BattleAnimation.flareRedHeal;
			if(getFrame(0)==135)
			{
				hp+=2;
				EyeCandy atk = new EyeCandy(bm.window, x+32, y-64, BattleAnimation.heart, bm,true,2,true,1,0,0);
				atk.setRepeating(true);
				bm.eyeCandy.add(atk);
			}
			if(getFrame(0)==239)
			{
				if(this.monsterID >= bm.amountOfMonsters)
				{
					currentAnimation = BattleAnimation.flareRedIdle;
					Global.attacker = Attacker.Simon;
					beganAttack = false;
					Global.attackPhase = 0;
					bm.selectedMonsterID = 0;
					healed = true;
				}
				else
				{
					healed = true;
					currentAnimation = BattleAnimation.flareRedIdle;
					bm.attackingMonster++;
					beganAttack = false;
				}
			}
		}
	}

	protected void flame()
	{
		if(Global.attackPhase == 0)
		{
			missed = false;
			if(Global.bossBattle && Global.talking == 0 && bossPhase == 0)
			{
				Global.talkSound = 0;
				if(Quest.quests[Quest.PYRUZ_W] == 0)
				{
					currentAnimation = BattleAnimation.flareRedTalk3;
					Global.talkingTo = this;
					Global.talking = 1;
					Global.disableMovement = true;
					SpeechBubble.talk("BWARR HARR HARR HARR!!! GET READY TO FEEL THE FLAAAAAARE!!/BWAR HAR HAR!!! YOU NO DODGE THIS!!!");
				
				}
				else if(super.hp > 0)
				{
					bossPhase = 999;
					Global.attackPhase++;
				}
			}
			else if((Global.talking == 0 && Quest.quests[Quest.PYRUZ_W] == 0) || bossPhase != 0)
			{
				if(super.hp > 0)
				{
					Global.attackPhase++;
				}
			}
			else if(!Global.bossBattle)
			{
				Global.attackPhase++;
			}
			if(Global.talking == 2)
			{
				Global.talking = 0;
				Global.disableMovement = false;
				Global.attackPhase++;
				bossPhase+=1;
				setFrame(0,0);
				this.setFrame(0, 0);
			}
		}
		if(Global.attackPhase == 1)
		{
			Global.disableMovement = false;
			currentAnimation = BattleAnimation.flareRedFlame;
			if(getFrame(0)==44)
			{
				showingFire = true;
				fireX = this.x+10;
				fireY = this.y+30;
				AudioHandler.playSound(AudioHandler.seFire);
				
			}
			if(getFrame(0)==59)
			{
				setFrame(0,0);
				this.setFrame(0, 0);
				Global.attackPhase++;
			}
			
		}

		if(Global.attackPhase == 2)
		{
			currentAnimation = BattleAnimation.flareRedWait;
			this.attackProjectile(0, 0, BattleAnimation.fire, 275, 1, AudioHandler.seSizzle,BattleAnimation.flareRedWait);
			showingFire = showingProjectile;
			/*
			if(!bm.eyeCandy.contains(dodgeCandy) && fireX <= 275 && fireX >= charAttacking.x+100 && charAttacking instanceof SkrappsBattler)
			{
				dodgeCandy = new EyeCandy(window, charAttacking.x+32, charAttacking.y-40, BattleAnimation.zIcon, bm);
				bm.eyeCandy.add(dodgeCandy);
			}
			if(missed && fireX <= 0)
			{
				Global.attackPhase++;
				showingFire = false;
				currentAnimation = BattleAnimation.flareRedIdle;
			}
			else if(fireX <= charAttacking.x+100)
			{
				bm.eyeCandy.remove(dodgeCandy);
				if(missed)
				{
					if(charAttacking.id == ObjectId.SkrappsBattler)
					{
						charAttacking.currentAnimation = BattleAnimation.skrappsCrouch;
						charAttacking.setFrame(0, 14);
					}
				}
				if(charAttacking.id == ObjectId.SimonBattler)
				{
					Global.simonHP--;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitAny, bm,true,1);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking.setFrame(0,0);
					charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					AudioHandler.playSound(AudioHandler.seSizzle);
					Global.attackPhase++;
					showingFire = false;
					currentAnimation = BattleAnimation.flareRedIdle;
				}
				if(charAttacking.id == ObjectId.SkrappsBattler)
				{
					if(!missed && charAttacking.dodging && charAttacking.getFrame(0)>5 && charAttacking.getFrame(0)<15)
					{
						missed = true;
					}
					else if(!missed)
					{
						Global.partnerHP[Global.SKRAPPS]--;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitAny, bm,true,1);
						atk.setRepeating(true);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.skrappsFlinch;
						Global.attackPhase++;
						AudioHandler.playSound(AudioHandler.seSizzle);
						showingFire = false;
						currentAnimation = BattleAnimation.flareRedIdle;
					}
				}
				if(charAttacking.id == ObjectId.WilliamBattler)
				{
					if(!charAttacking.dodging)
					{
						AudioHandler.playSound(AudioHandler.seSizzle);
						Global.williamHP--;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitAny, bm,true,1);
						atk.setRepeating(true);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.williamHitAnimation;
					}
					else
					{
						AudioHandler.playSound(AudioHandler.seSizzle);
						Global.williamHP-=2;
						charAttacking.dodging = false;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitAny, bm,true,2);
						atk.setRepeating(true);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.williamHitAnimation;
					
					}
					Global.attackPhase++;
					showingFire = false;
					currentAnimation = BattleAnimation.flareRedIdle;
				}
			}*/
		}
		if(Global.attackPhase == 3)
		{
			for(int i=0; i<bm.objects.size();i++)
			{
				bm.objects.get(i).canDodge = true;
				bm.objects.get(i).dodging = false;
			}
			bm.eyeCandy.remove(dodgeCandy);
			bm.eyeCandy.remove(super.dodgeCandy);
			bm.eyeCandy.remove(this.dodgeCandy);
			if(this.monsterID >= bm.amountOfMonsters && (charAttacking.id != ObjectId.SimonBattler || charAttacking.currentAnimation == (BattleAnimation.simonIdleAnimation)))
			{
				currentAnimation = BattleAnimation.flareRedIdle;
				Global.attacker = Attacker.Simon;
				beganAttack = false;
				Global.attackPhase = 0;
				bm.selectedMonsterID = 0;
			}
			else if(this.monsterID < bm.amountOfMonsters)
			{
				currentAnimation = BattleAnimation.flareRedIdle;
				bm.attackingMonster++;
				beganAttack = false;
			}
		}
		if(showingFire)
		{
			showingProjectile = true;
			fireX -= 8;
			fireY += 1;
			projectileX = fireX;
			projectileY = (int) fireY;
		}
	}
	@Override
	public void attack()
	{
		if(Global.attackPhase == 0 && hp > 0)
		{
			charAttacking.canDodge = true;
			currentAnimation = BattleAnimation.flareRedWalk;
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
				currentAnimation = BattleAnimation.flareRedPunch;
				Global.attackPhase++;
				
			}
		}
		else if(Global.attackPhase == 1)
		{
			if(!currentAnimation.equals(BattleAnimation.flareRedFlinch))
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
				if(getFrame(0) == 33 && charAttacking.id == ObjectId.SimonBattler)
				{
					bm.eyeCandy.remove(dodgeCandy);
					if(!(charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
					{
						Global.simonHP--;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
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
				
				if(getFrame(0) == 33 && charAttacking.id == ObjectId.SkrappsBattler)
				{
					bm.eyeCandy.remove(dodgeCandy);
					if(!(charAttacking.dodging && charAttacking.getFrame(0)>4 && charAttacking.getFrame(0)<18))
					{
						Global.partnerHP[Global.SKRAPPS]--;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.skrappsFlinch;
					}
				}
				if(charAttacking.id == ObjectId.WilliamBattler)
				{
					if(getFrame(0) < 34 && charAttacking.currentAnimation != BattleAnimation.williamFailAnimation && charAttacking.dodging && charAttacking.getFrame(0)>12 && charAttacking.getFrame(0)<16)
					{
						super.setFrame(0,0);
						setFrame(0,0);
						hp--;
						EyeCandy atk = new EyeCandy(window, x+100, y+64, BattleAnimation.hitOneHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.canDodge = true;
						currentAnimation = BattleAnimation.flareRedFlinch;
						bm.eyeCandy.remove(dodgeCandy);
					}
					else if(getFrame(0) == 34 && charAttacking.dodging)
					{
						Global.williamHP-=2;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitTwoHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,13);
						charAttacking.currentAnimation = BattleAnimation.williamFailAnimation;
						bm.eyeCandy.remove(dodgeCandy);
					}
					else if(getFrame(0) == 34 && !charAttacking.dodging)
					{
						Global.williamHP--;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.canDodge = true;
						charAttacking.currentAnimation = BattleAnimation.williamHitAnimation;
						bm.eyeCandy.remove(dodgeCandy);
					}
				}*/
				attackFrameBased(2,34,1,BattleAnimation.flareRedFlinch);
				if(getFrame(0)>=56)
				{
					bm.eyeCandy.remove(dodgeCandy);
					x = attackWalkDistance();
					setFrame(0, 0);
					currentAnimation = BattleAnimation.flareRedWalk;
					this.flipped = true;
					Global.attackPhase++;
				}
			}
			else if(getFrame(0)>=5)
			{
				this.flipped = true;
				Global.attackPhase++;
				bm.eyeCandy.remove(dodgeCandy);
			}
		}
		else if(Global.attackPhase == 2)
		{
			currentAnimation = BattleAnimation.flareRedWalk;
			x+=10;
			if(x>=startX)
			{
				x = startX;
				setFrame(0, 0);
				currentAnimation = BattleAnimation.flareRedIdle;
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
	protected void attackInit()
	{

		Global.attackPhase = 0;
		if(hp > 0)
		{
			if(hp>=healVal || healed)
			{
				if(Math.random()<.5 || (Global.bossBattle && bossPhase<1))
				{
					attackType = 2;
				}
				else
				{
					attackType = 0;
				}
			}
			else if(!healed)
			{
				attackType = 1;
			}

			selectAttacking();
			beganAttack = true;
		}
		
	}

	@Override
	public void flinch() {
		setFrame(0,0);
		window.battleMode.selectedMonster.currentAnimation = BattleAnimation.flareRedFlinch;
		
		
	}
	
	

}
