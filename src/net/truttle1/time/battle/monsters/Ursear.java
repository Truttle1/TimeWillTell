package net.truttle1.time.battle.monsters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.battle.Attacker;
import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.main.Game;

public class Ursear extends Monster{

	BufferedImage[] idleAnimation;
	private boolean beganAttack = false;
	public Ursear(Game window, int x, int y, BattleMode bm) {
		super(window,x,y,bm);
		super.boxOffsetX = -40;
		super.boxOffsetY = 96;
		super.boxWidth = 96;
		super.hp = 5;
		super.maxHp = 5;
		super.attack = 1;
		super.name = "Ursear";
		super.type = MonsterType.Ursear;
		currentAnimation = BattleAnimation.ursearIdleAnimation;
		dodgeType = 0;
		xp = 4;
		money = 1;
		heightMod = 80;
		information = "This is an Ursear. Max HP: 5; Attack: 1;/The Ursear attacks through punches. Their punches are quite easy to dodge or/counterattack. Ursears are the simplest monsters ever. In fact, that there is/almost no information on them...Because they're so simple, there is no information.";
	}


	
	@Override
	public void tick() {
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
			bm.xpGainedSimon+=(double)xp*((double)simonDealt/damageDealt);
			bm.xpGainedWilliam+=(double)xp*((double)williamDealt/damageDealt);
			bm.xpGainedPartner[Global.currentPartner]+=(double)xp*((double)partnerDealt[Global.currentPartner]/damageDealt);
			//Global.partnerXP[Global.currentPartner] += bm.xpGainedPartner[Global.currentPartner];
			bm.moneyGained += money;
			Global.money += money;
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.ursearDieAnimation, bm);
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
		if(Global.attacker != Attacker.Monsters && currentAnimation == BattleAnimation.ursearHitAnimation && getFrame(0)>=5)
		{
			currentAnimation = BattleAnimation.ursearIdleAnimation;
		}
		if(Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			if(!beganAttack)
			{
				attackInit();
			}

			if(!Global.tutorialBattle || Global.tutorialBattlePhase > 4)
			{
				attack();
			}
			else if(Global.tutorialBattlePhase == 3)
			{
				Global.tutorialBattlePhase = 4;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		super.arrowRender(g);
		if(Global.attackPhase == 0 && Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			super.animateAtSpeed(super.x,super.y,currentAnimation,0,g,2);
		}
		else if(Global.attackPhase == 2 && Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
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
	@Override
	public void attack()
	{
		
		if(Global.attackPhase == 0)
		{
			charAttacking.canDodge = true;
			currentAnimation = BattleAnimation.ursearWalkAnimation;
			x-=10;
			if(!bm.eyeCandy.contains(dodgeCandy) && x<=charAttacking.x+180 && charAttacking.id == ObjectId.WilliamBattler)
			{
				dodgeCandy = new EyeCandy(window, super.x-72, charAttacking.y-135, BattleAnimation.xIcon, bm);
				bm.eyeCandy.add(dodgeCandy);
			}
			if(x<=attackWalkDistance())
			{
				x = attackWalkDistance();
				setFrame(0, 0);
				currentAnimation = BattleAnimation.ursearAttackAnimation;
				Global.attackPhase++;
				
			}
		}
		else if(Global.attackPhase == 1)
		{
			if(!currentAnimation.equals(BattleAnimation.ursearHitAnimation))
			{
				attackFrameBased(4,17,1,BattleAnimation.ursearHitAnimation);
				if(getFrame(0)>=29)
				{
					x = attackWalkDistance();
					setFrame(0, 0);
					currentAnimation = BattleAnimation.ursearWalkAnimation;
					this.flipped = true;
					Global.attackPhase++;
				}
			}
			else if(getFrame(0)>=5)
			{
				this.flipped = true;
				Global.attackPhase++;
			}
		}
		else if(Global.attackPhase == 2)
		{
			currentAnimation = BattleAnimation.ursearWalkAnimation;
			x+=10;
			if(x>=startX)
			{
				x = startX;
				setFrame(0, 0);
				currentAnimation = BattleAnimation.ursearIdleAnimation;
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

		if(Global.attacker != Attacker.Lose)
		{
			try
			{
				selectAttacking();
				beganAttack = true;
			}
			catch(Exception e) {System.out.println("HLEP THERE IS NO PLAYER! TRYING TO KEEP THE GAME RUNNING SMOOTHLY. HIGH CHANCE OF ULTIMATE FAIL!");}
		}
		
	}

	@Override
	public void flinch() 
	{
		setFrame(0,0);
		this.currentAnimation = BattleAnimation.ursearHitAnimation;
	}
	
	

}
