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

public class Baumber extends Monster{

	BufferedImage[] idleAnimation;
	private boolean beganAttack = false;
	public Baumber(Game window, int x, int y, BattleMode bm) {
		super(window,x,y,bm);
		super.boxOffsetX = -30;
		super.boxOffsetY = 96;
		super.hp = 15;
		super.maxHp = 15;
		super.boxWidth = 126;
		super.attack = 3;
		super.defense = 1;
		super.name = "Baumber";
		super.type = MonsterType.Baumber;
		currentAnimation = BattleAnimation.baumberIdle;
		dodgeType = 0;
		xp = 44;
		money = 10;
		heightMod = 100;
		}


	
	@Override
	public void tick() {
		super.boxOffsetX = -30;
		heightMod = 110;
		information = "That's not just a tree, it's a Baumber! Max HP: 15; Attack: 3; Defense: 1;/These evil trees found in Aqua City hurl bombs at you./Those bombs deal 3 damage. Skrapps can dodge them by ducking, but everyone else must/take damage. That's not good!";
		
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
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.baumberDie, bm);
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
		if(Global.attacker != Attacker.Monsters && currentAnimation == BattleAnimation.baumberFlinch && getFrame(0)>=5)
		{
			currentAnimation = BattleAnimation.baumberIdle;
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
	public void render(Graphics g) {
		super.arrowRender(g);
		if(Global.attackPhase == 0 && Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			super.animateAtSpeed(super.x,super.y,currentAnimation,0,g,.7);
		}
		else if(Global.attackPhase == 2 && Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			super.animateAtSpeed(super.x,super.y,currentAnimation,0,g,1);
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
	}
	@Override
	public void attack()
	{
		
		if(Global.attackPhase == 0)
		{
			currentAnimation = BattleAnimation.baumberAttack;
			if(getFrame(0)>=19)
			{
				this.setFrame(1, 0);
				Global.attackPhase++;
				showingProjectile = true;
				projectileX = this.x;
				projectileY = this.y+55;
				//AudioHandler.playSound(AudioHandler.seGuitar1);
			}
		}
		if(Global.attackPhase == 1)
		{
			if(getFrame(0)>=23)
			{
				currentAnimation = BattleAnimation.baumberIdle;
			}
			for(int i=0; i<bm.objects.size();i++)
			{
				bm.objects.get(i).canDodge = true;
			}
			attackProjectile(-15,0,BattleAnimation.bomb,375,3,AudioHandler.seGuitar0, BattleAnimation.baumberIdle,BattleAnimation.explosion,-40,-50);
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
				currentAnimation = BattleAnimation.baumberIdle;
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
			try
			{
				selectAttacking();
				beganAttack = true;
			}
			catch(Exception e) {System.out.println("HLEP THERE IS NO PLAYER! TRYING TO KEEP THE GAME RUNNING SMOOTHLY. HIGH CHANCE OF ULTIMATE FAIL!");}
		}
		setFrame(0,0);
	}

	@Override
	public void flinch() {
		setFrame(0,0);
		window.battleMode.selectedMonster.currentAnimation = BattleAnimation.baumberFlinch;
		
		
	}
	
	

}
