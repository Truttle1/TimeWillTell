package net.truttle1.time.battle.monsters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.Attacker;
import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;

public class Trukofire extends Monster
{

	BufferedImage[] idleAnimation;
	private boolean beganAttack = false;
	private int attackType = 0;
	public Trukofire(Game window, int x, int y, BattleMode bm) 
	{
		super(window, x, y, bm);
		super.boxOffsetX = -40;
		super.boxOffsetY = 96;
		super.boxWidth = 124;
		super.hp = 30;
		super.maxHp = 30;
		super.attack = 1;
		super.defense = 0;
		super.name = "Trukofire";
		super.type = MonsterType.Trukofire;
		currentAnimation = BattleAnimation.trukofireIdle;
		idleAnimation = currentAnimation;
		dodgeType = 0;
		xp = 35;
		money = 14;
		heightMod = 140;
	}

	@Override
	public void tick() 
	{
		heightMod = 20;
		super.boxOffsetX = -30;
		information = "It's a sentient firetruck: a Trukofire! Max HP: 30; Attack: 3;/These trucks, strangely enough, attack with fire. Their fireballs do 3 damage./Trukofires also have a dash attack, which hurts all party members./These enemies guard the Convex Inc. building in Aqua City.";
		
		if(super.hp <= 0 && killedAttacker == null)
		{
			killedAttacker = Global.attacker;
		}
		if(Global.attacker != Attacker.Monsters)
		{
			bm.eyeCandy.remove(dodgeCandy);
		}
		checkIfDead(BattleAnimation.trukofireDie);
		checkSelected();
		if(currentAnimation == BattleAnimation.trukofireFlinch && getFrame(0)>=11)
		{
			currentAnimation = BattleAnimation.trukofireIdle;
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
	public void attack() 
	{
		if(attackType == 0)
		{
			attackFire();
		}
		else
		{
			attackDash();
		}
	}
	
	private void attackDash()
	{
		int damage = 2;
		if(Global.attackPhase == 0)
		{
			currentAnimation = BattleAnimation.trukofireWind;
			if(getFrame(0)>=29)
			{
				currentAnimation = BattleAnimation.trukofireDash;
				this.setFrame(0, 0);
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 1)
		{
			this.x -= 20;
			if(charAttacking != null && this.x <= charAttacking.x+100 && charAttacking.dead == false)
			{
				damageSimon(charAttacking,damage);
				Global.attackPhase++;
				AudioHandler.playSound(AudioHandler.seDodge);
			}
			if(charAttacking == null || charAttacking.dead == true)
			{
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 2)
		{
			this.x -= 20;
			if(charAttacking1 != null && this.x <= charAttacking1.x+50 && charAttacking1.dead == false)
			{
				damageWilliam(charAttacking1,damage);
				charAttacking1.setFrame(0,0);
				Global.attackPhase++;
				AudioHandler.playSound(AudioHandler.seDodge);
			}
			if(charAttacking1 == null || charAttacking1.dead == true)
			{
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 3)
		{
			this.x -= 20;
			if(charAttacking2 != null && this.x <= charAttacking2.x+50 && charAttacking2.dead == false)
			{
				damagePartner(charAttacking2,damage);
				charAttacking2.setFrame(0,0);
				Global.attackPhase++;
				AudioHandler.playSound(AudioHandler.seDodge);
			}
			if(charAttacking2 == null || charAttacking2.dead == true)
			{
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 4)
		{
			this.x -= 20;
			if(this.x <= 0-320)
			{
				this.x = 1024;
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 5)
		{
			this.x -= 20;
			if(this.x <= this.startX)
			{
				this.x = this.startX;
				Global.attackPhase++;
			}
		}
		if(Global.attackPhase == 6)
		{
			for(int i=0; i<bm.objects.size();i++)
			{
				bm.objects.get(i).canDodge = true;
			}
			bm.eyeCandy.remove(dodgeCandy);
			nextMonster(BattleAnimation.trukofireIdle);
			beganAttack = false;
		}
	}
	
	private void attackFire()
	{
		if(Global.attackPhase == 0)
		{
			currentAnimation = BattleAnimation.trukofireBurn;
			if(getFrame(0)>=13)
			{
				this.setFrame(1, 0);
				Global.attackPhase++;
				showingProjectile = true;
				projectileX = this.x+10;
				projectileY = this.y+70;
				AudioHandler.playSound(AudioHandler.seFire);
			}
			
		}
		else if(Global.attackPhase == 1)
		{
			if(getFrame(0)>=35)
			{
				currentAnimation = BattleAnimation.trukofireIdle;
			}
			attackProjectile(-10,1,BattleAnimation.fire,375,3,AudioHandler.seSizzle, BattleAnimation.trukofireIdle);
		}
		if(Global.attackPhase == 2)
		{
			for(int i=0; i<bm.objects.size();i++)
			{
				bm.objects.get(i).canDodge = true;
			}
			bm.eyeCandy.remove(dodgeCandy);
			nextMonster(BattleAnimation.trukofireIdle);
			beganAttack = false;
		}
	}
	
	@Override
	protected void attackInit() 
	{
		if(Math.random() < .6)
		{
			attackType = 0;
		}
		else
		{
			attackType = 1;
		}
		
		if(attackType == 1)
		{
			selectAttackingAll();
		}
		else
		{
			if(Global.attacker != Attacker.Lose)
			{
				try
				{
					selectAttacking();
				}
				catch(Exception e) {System.out.println("HLEP THERE IS NO PLAYER! TRYING TO KEEP THE GAME RUNNING SMOOTHLY. HIGH CHANCE OF ULTIMATE FAIL!");}
			}
		}
		beganAttack = true;
		setFrame(0,0);
	}

	@Override
	public void render(Graphics g) 
	{
		//System.out.println(heightMod);
		super.arrowRender(g);
		super.animate(super.x,super.y,currentAnimation,0,g);
		if(showingProjectile)
		{
			super.animate(projectileX, projectileY, projectile, 1, g);
		}
	}

	@Override
	public void flinch() 
	{
		setFrame(0,0);
		this.currentAnimation = BattleAnimation.trukofireFlinch;
	}

}
