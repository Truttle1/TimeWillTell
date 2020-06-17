package net.truttle1.time.battle.monsters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.battle.Attacker;
import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;

public class Rockstar extends Monster{

	BufferedImage[] idleAnimation;
	private boolean beganAttack = false;
	public Rockstar(Game window, int x, int y, BattleMode bm) {
		super(window,x,y,bm);
		super.boxOffsetX = -40;
		super.boxOffsetY = 96;
		super.boxWidth = 124;
		super.hp = 9;
		super.maxHp = 9;
		super.attack = 1;
		super.defense = 1;
		super.name = "Rockstar";
		super.type = MonsterType.Rockstar;
		currentAnimation = BattleAnimation.rockstarIdle;
		dodgeType = 0;
		xp = 12;
		money = 5;
		heightMod = 80;
		}


	
	@Override
	public void tick() {
		information = "This is a Rockstar. Max HP: 9; Attack: 1; Defense 1; Rockstars are known for being terrible at/playing guitar. In fact, that's how they attack! Rockstars also have one Defense Point,/meaning that they will take one less damage than usual. Is my POWER stat at level 2 yet?/If it isn't, the Rockstar won't even feel my attacks!";

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
			//Global.simonXP += bm.xpGainedSimon;
			//Global.williamXP += bm.xpGainedWilliam;
			bm.xpGainedPartner[Global.currentPartner]+=(double)xp*((double)partnerDealt[Global.currentPartner]/damageDealt);
			//Global.partnerXP[Global.currentPartner] += bm.xpGainedPartner[Global.currentPartner];
			bm.moneyGained += money;
			Global.money += money;
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.rockstarDie, bm);
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
		if(Global.attacker != Attacker.Monsters && currentAnimation == BattleAnimation.rockstarFlinch && getFrame(0)>=5)
		{
			currentAnimation = BattleAnimation.rockstarIdle;
		}
		if(Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			if(!beganAttack)
			{
				attackInit();
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
		if(showingProjectile)
		{
			super.animate(projectileX, projectileY, projectile, 1, g);
		}
		//g.drawString(Integer.toString(this.monsterID), x+100, y-100);
	}
	@Override
	public void attack()
	{
		if(Global.attackPhase == 0)
		{
			currentAnimation = BattleAnimation.rockstarAttack;
			if(getFrame(0)>=16)
			{
				this.setFrame(1, 0);
				Global.attackPhase++;
				showingProjectile = true;
				projectileX = this.x;
				projectileY = this.y+64;
				AudioHandler.playSound(AudioHandler.seGuitar1);
			}
		}
		if(Global.attackPhase == 1)
		{
			if(getFrame(0)>=23)
			{
				currentAnimation = BattleAnimation.rockstarIdle;
			}
			attackProjectile(-10,0,BattleAnimation.note,375,1,AudioHandler.seGuitar0, BattleAnimation.rockstarIdle);
		}
		if(Global.attackPhase == 2)
		{
			for(int i=0; i<bm.objects.size();i++)
			{
				bm.objects.get(i).canDodge = true;
			}
			bm.eyeCandy.remove(dodgeCandy);
			if(this.monsterID >= bm.amountOfMonsters)
			{
				currentAnimation = BattleAnimation.rockstarIdle;
				Global.attacker = Attacker.Simon;
				beganAttack = false;
				Global.attackPhase = 0;
				bm.selectedMonsterID = 0;
			}
			else
			{
				bm.attackingMonster++;
				beganAttack = false;
				Global.attackPhase = 0;
			}
		}
	}
	protected void attackInit()
	{

		if(Global.attacker != Attacker.Lose)
		{
			this.setFrame(0, 0);
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
		window.battleMode.selectedMonster.currentAnimation = BattleAnimation.rockstarFlinch;
	}
	
	

}
