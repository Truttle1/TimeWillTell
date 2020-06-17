package net.truttle1.time.battle.monsters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.battle.AttackSelection;
import net.truttle1.time.battle.Attacker;
import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.SimonBattler;
import net.truttle1.time.battle.SkrappsBattler;
import net.truttle1.time.battle.WilliamBattler;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;

public abstract class Monster extends GameObject{

	public int hp;
	public int maxHp;
	public int attack;
	public int defense;
	public int monsterID;
	public MonsterType type;
	protected int arrowOffsetX;
	protected int startX;
	protected int startY;
	protected int boxOffsetX;
	protected int boxOffsetY;
	protected int boxWidth;
	protected int xp;
	protected int money;
	public int heightMod = 0;
	protected String name;
	public BattleMode bm;
	public byte attacking;
	public byte dodgeType = 0;
	public int damageDealt;
	public int simonDealt;
	public int williamDealt;
	public int[] partnerDealt = new int[99];
	public GameObject charAttacking;
	public GameObject charAttacking1;
	public GameObject charAttacking2;
	protected Attacker killedAttacker;
	public String information;
	public boolean spikeFront = false;
	protected EyeCandy dodgeCandy;
	protected boolean attacked = false;
	protected boolean candyAdded = false;
	protected int hitTime;
	protected boolean hit = false;
	protected BufferedImage[] projectile;
	protected int projectileX;
	protected int projectileY;
	protected boolean missed;
	protected boolean showingProjectile;
	private int stickResistance;
	private int fireResistance;
	public Monster(Game window, int x, int y, BattleMode bm) {
		super(window);
		startX = x;
		startY = y;
		super.x = x;
		super.y = y;
		this.bm = bm;
		startX = x;
		startY = y;
		this.id = ObjectId.Monster;
		for(int i=0; i<99;i++)
		{
			partnerDealt[i] = 0;
		}
	}

	@Override
	public abstract void tick();
	protected void checkSelected()
	{
		if(window.battleMode.selectedMonsterID == monsterID)
		{
			window.battleMode.selectedMonster = this;
		}
	}

	protected abstract void attackInit();
	
	public boolean allPlayersDead()
	{
		return (!Global.hasSimon || Global.simonHP <= 0) && (!Global.hasWilliam || Global.williamHP <= 0) && (!Global.hasPartner || Global.partnerHP[0] <= 0);
	}
	
	protected void selectAttacking()
	{

		if(allPlayersDead())
		{
			Global.attacker = Attacker.Lose;
			return;
		}
		if(!Global.hasSimon && !Global.hasWilliam && !Global.hasPartner)
		{
			return;
		}
		Global.attackPhase = 0;
		hit = false;
		if(Global.hasWilliam && Math.random()<0.30)
		{
			attacking = 1;
			for(int i=0; i<bm.objects.size(); i++)
			{
				if (bm.objects.get(i).id == ObjectId.WilliamBattler)
				{
					WilliamBattler temp = (WilliamBattler) bm.objects.get(i);
					if(!temp.dead)
					{
						this.charAttacking = bm.objects.get(i);
					}
					else if(Global.hasSimon || Global.hasPartner)
					{
						attackInit();
					}
				}
			}
		}
		else if(Global.hasPartner && Global.currentPartner != Global.RAGE && Math.random()<0.38)
		{
			attacking = 2;

			for(int i=0; i<bm.objects.size(); i++)
			{
				if (bm.objects.get(i).id == ObjectId.SkrappsBattler)
				{
					SkrappsBattler temp = (SkrappsBattler) bm.objects.get(i);
					if(!temp.dead)
					{
						this.charAttacking = bm.objects.get(i);
					}
					else if(Global.hasSimon || Global.hasWilliam)
					{
						if(Global.simonHP > 0 || Global.williamHP > 0 || Global.partnerHP[0] > 0)
						{
							attackInit();
						}
					}
				}
			}
		}
		else if(Global.hasSimon)
		{
			attacking = 0;
			for(int i=0; i<bm.objects.size(); i++)
			{
				if (bm.objects.get(i).id == ObjectId.SimonBattler)
				{
					SimonBattler temp = (SimonBattler) bm.objects.get(i);
					if(!temp.dead)
					{
						this.charAttacking = bm.objects.get(i);
					}
					else if(Global.hasWilliam || Global.hasPartner)
					{
						attackInit();
					}
				}
			}
		}
		else if ((Global.hasSimon || Global.hasPartner || Global.hasWilliam) && Global.attacker != Attacker.Lose)
		{
			attackInit();
		}
	}
	protected void selectAttackingAll()
	{
		Global.attackPhase = 0;
		hit = false;
		if(Global.hasWilliam)
		{
			attacking = 1;
			for(int i=0; i<bm.objects.size(); i++)
			{
				if (bm.objects.get(i).id == ObjectId.WilliamBattler)
				{
					WilliamBattler temp = (WilliamBattler) bm.objects.get(i);
					if(!temp.dead)
					{
						this.charAttacking1 = bm.objects.get(i);
					}
				}
			}
		}
		if(Global.hasPartner && Global.currentPartner != Global.RAGE)
		{
			attacking = 2;

			for(int i=0; i<bm.objects.size(); i++)
			{
				if (bm.objects.get(i).id == ObjectId.SkrappsBattler)
				{
					SkrappsBattler temp = (SkrappsBattler) bm.objects.get(i);
					if(!temp.dead)
					{
						this.charAttacking2 = bm.objects.get(i);
					}
				}
			}
		}
		if(Global.hasSimon)
		{
			attacking = 0;
			for(int i=0; i<bm.objects.size(); i++)
			{
				if (bm.objects.get(i).id == ObjectId.SimonBattler)
				{
					SimonBattler temp = (SimonBattler) bm.objects.get(i);
					if(!temp.dead)
					{
						this.charAttacking = bm.objects.get(i);
					}
				}
			}
		}
		else if (Global.attacker != Attacker.Lose)
		{
			attackInit();
		}
	}
	
	@Override
	public abstract void render(Graphics g);
	protected void arrowRender(Graphics g)
	{

		if(!(Global.attacker == Attacker.Partner && Global.currentPartner == Global.RAGE) && ((Global.informing && bm.selectedMonsterID == this.monsterID) || (Global.attacker != Attacker.Monsters && Global.attackPhase == 1 && window.battleMode.selectedMonsterID == monsterID) || (Global.attacker != Attacker.Monsters && bm.attackSelection == AttackSelection.Special && bm.specialType == 1 && window.battleMode.selectedMonsterID == monsterID)))
		{
			super.animate(startX+64+arrowOffsetX,startY-64,BattleAnimation.arrow,1,g);
			g.setColor(Color.red);
			g.fillRect(startX-boxOffsetX,startY-boxOffsetY,boxWidth,32);
			g.setColor(Color.blue);
			g.fillRect(startX-boxOffsetX,startY-boxOffsetY,(int)(boxWidth*(double)(hp)/(double)(maxHp)),32);
			g.setColor(Color.white);
			g.setFont(Global.battleFont);
			g.drawString(name, startX-boxOffsetX+8, startY-boxOffsetY+24);
		}
	}
	public abstract void attack();
	public abstract void flinch();
	protected void attackXBased(int candyDistance, int xVal, int DISTANCE, int DISTANCE2)
	{

		x-=16;
		y+=10;
		if(charAttacking.id == ObjectId.SimonBattler && x<charAttacking.x+candyDistance && !candyAdded)
		{
			candyAdded = true;
			System.out.println("ADDED AN EYECANDY!");
			dodgeCandy = new EyeCandy(window, charAttacking.x+72, charAttacking.y-32, BattleAnimation.cIcon, bm);
			bm.eyeCandy.add(dodgeCandy);
		}
		if(charAttacking.id == ObjectId.SkrappsBattler && x<charAttacking.x+candyDistance && !candyAdded)
		{
			candyAdded = true;
			System.out.println("ADDED AN EYECANDY!");
			dodgeCandy = new EyeCandy(window, charAttacking.x+172, charAttacking.y-32, BattleAnimation.zIcon, bm);
			bm.eyeCandy.add(dodgeCandy);
		}
		if(x <= charAttacking.x+xVal && charAttacking.id == ObjectId.SimonBattler)
		{
			bm.eyeCandy.remove(dodgeCandy);
			if(!(charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
			{
				attacked = true;
				Global.simonHP--;
				EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
				atk.setRepeating(false);
				bm.eyeCandy.add(atk);
				charAttacking.setFrame(0,0);
				charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
				AudioHandler.playSound(AudioHandler.seHit2);
			}
			else if(charAttacking.dodging)
			{
				attacked = true;
			}
		}
		if(x <= charAttacking.x+xVal && charAttacking.id == ObjectId.SkrappsBattler)
		{
			bm.eyeCandy.remove(dodgeCandy);
			if(!(charAttacking.dodging && charAttacking.getFrame(0)>4 && charAttacking.getFrame(0)<18))
			{
				attacked = true;
				Global.partnerHP[Global.SKRAPPS]--;
				EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
				atk.setRepeating(false);
				bm.eyeCandy.add(atk);
				charAttacking.setFrame(0,0);
				charAttacking.currentAnimation = BattleAnimation.skrappsFlinch;
				AudioHandler.playSound(AudioHandler.seHit2);
			}
			else if(charAttacking.dodging)
			{
				attacked = true;
			}
		}
		if(charAttacking.id == ObjectId.WilliamBattler)
		{
			System.out.println(DISTANCE2);
			if(charAttacking.currentAnimation != BattleAnimation.williamFailAnimation && charAttacking.dodging && x >= charAttacking.x+DISTANCE && x <= charAttacking.x+DISTANCE2 && charAttacking.getFrame(0)>8 && charAttacking.getFrame(0)<24)
			{
				attacked = true;
				this.setFrame(0,0);
				hp--;
				EyeCandy atk = new EyeCandy(window, x+100, y+64, BattleAnimation.hitOneHP, bm);
				atk.setRepeating(false);
				bm.eyeCandy.add(atk);
				charAttacking.canDodge = true;
				currentAnimation = BattleAnimation.vultFlinch;
				bm.eyeCandy.remove(dodgeCandy);
				hVelocity = 0;
				hitTime = 0;
				AudioHandler.playSound(AudioHandler.seDodge);
				
			}
			else if(x <= charAttacking.x+DISTANCE && charAttacking.dodging && currentAnimation != BattleAnimation.vultFlinch)
			{
				attacked = true;
				Global.williamHP-=2;
				EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitTwoHP, bm);
				atk.setRepeating(false);
				bm.eyeCandy.add(atk);
				charAttacking.setFrame(0,13);
				charAttacking.currentAnimation = BattleAnimation.williamFailAnimation;
				bm.eyeCandy.remove(dodgeCandy);
				AudioHandler.playSound(AudioHandler.seHit2);
			}
			else if(x <= charAttacking.x+DISTANCE && !charAttacking.dodging)
			{
				attacked = true;
				Global.williamHP--;
				EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
				atk.setRepeating(false);
				bm.eyeCandy.add(atk);
				charAttacking.setFrame(0,0);
				charAttacking.canDodge = true;
				charAttacking.currentAnimation = BattleAnimation.williamHitAnimation;
				bm.eyeCandy.remove(dodgeCandy);
			}
		}
	}
	protected void attackProjectile(int xVel, int yVel, BufferedImage[] projectile,int candyDis, int damage, File sound, BufferedImage[] afterAnimation)
	{
		attackProjectile(xVel,yVel,projectile,candyDis,damage,sound,afterAnimation,null,-1,-1);
		
	}
	protected void attackProjectile(int xVel, int yVel, BufferedImage[] projectile,int candyDis, int damage, File sound, BufferedImage[] afterAnimation, BufferedImage[] hitCandy, int hitCandyX, int hintCandyY)
	{
		System.out.println(damage);
		this.projectile = projectile;
		this.projectileX += xVel;
		this.projectileY += yVel;
		if(!bm.eyeCandy.contains(dodgeCandy) && projectileX <= candyDis && projectileX >= charAttacking.x+200 && charAttacking instanceof SkrappsBattler)
		{
			dodgeCandy = new EyeCandy(window, charAttacking.x+32, charAttacking.y-40, BattleAnimation.zIcon, bm);
			bm.eyeCandy.add(dodgeCandy);
		}
		if(!bm.eyeCandy.contains(dodgeCandy) && projectileX <= candyDis+400 && projectileX >= charAttacking.x+100 && charAttacking instanceof SimonBattler)
		{
			System.out.println("abcdefg");
			dodgeCandy = new EyeCandy(window, charAttacking.x+32, charAttacking.y-40, BattleAnimation.cIcon, bm);
			bm.eyeCandy.add(dodgeCandy);
		}

		if(missed && projectileX <= 0)
		{
			Global.attackPhase++;
			showingProjectile = false;
			currentAnimation = afterAnimation;
		}
		else if((charAttacking.id == ObjectId.SimonBattler && projectileX <= charAttacking.x+150) || projectileX <= charAttacking.x+100)
		{

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
				if(hitCandy != null)
				{
					EyeCandy extra = new EyeCandy(window, charAttacking.x+hitCandyX, charAttacking.y+hitCandyX,hitCandy, bm, false, -1);
					extra.setRepeating(false);
					bm.eyeCandy.add(extra);
				}
				if(!(charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
				{
					Global.simonHP-=damage;
					
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking.setFrame(0,0);
					charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					charAttacking.setFrame(0,0);
					hit = true;
					AudioHandler.playSound(sound);

					Global.attackPhase++;
					showingProjectile = false;
					charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					currentAnimation = afterAnimation;
				}
				else if((charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10) && damage-1 > 0)
				{
	
					Global.simonHP-=damage-1;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage-1);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking.setFrame(0,0);
					charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					charAttacking.setFrame(0,0);
					hit = true;
					charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					AudioHandler.playSound(sound);

					Global.attackPhase++;
					showingProjectile = false;
					currentAnimation = afterAnimation;
				}
				else if((charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
				{
	
					hit = true;
					Global.attackPhase++;
					showingProjectile = false;
					charAttacking.dodging = false;
					currentAnimation = afterAnimation;
				}
			}
			if(charAttacking.id == ObjectId.SkrappsBattler)
			{
				if(!missed && charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<18)
				{
					missed = true;
				}
				else if(!missed)
				{
					if(hitCandy != null)
					{
						EyeCandy extra = new EyeCandy(window, charAttacking.x+hitCandyX, charAttacking.y+hitCandyX,hitCandy, bm, false, -1);
						extra.setRepeating(false);
						bm.eyeCandy.add(extra);
					}
					Global.partnerHP[Global.SKRAPPS]-=damage;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitAny, bm,true,damage);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking.setFrame(0,0);
					charAttacking.currentAnimation = BattleAnimation.skrappsFlinch;
					AudioHandler.playSound(sound);

					Global.attackPhase++;
					showingProjectile = false;
					currentAnimation = afterAnimation;
				}
			}
			if(charAttacking.id == ObjectId.WilliamBattler)
			{
				if(hitCandy != null)
				{
					EyeCandy extra = new EyeCandy(window, charAttacking.x+hitCandyX, charAttacking.y+hitCandyX,hitCandy, bm, false, -1);
					extra.setRepeating(false);
					bm.eyeCandy.add(extra);
				}
				if(!charAttacking.dodging)
				{
					AudioHandler.playSound(sound);
					Global.williamHP-=damage;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitAny, bm,true,damage);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking.setFrame(0,0);
					charAttacking.currentAnimation = BattleAnimation.williamHitAnimation;

					Global.attackPhase++;
					showingProjectile = false;
					currentAnimation = afterAnimation;
				}
				else
				{
					AudioHandler.playSound(sound);
					Global.williamHP-=damage*2;
					charAttacking.dodging = false;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitAny, bm,true,damage);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking.setFrame(0,0);
					charAttacking.currentAnimation = BattleAnimation.williamHitAnimation;

					Global.attackPhase++;
					showingProjectile = false;
					currentAnimation = afterAnimation;
				
				}
			}
		}
		
	}
	protected void attackFrameBased(int candyFrame, int frame, int damage,BufferedImage[] hitAnimation)
	{
		attackFrameBasedRange(candyFrame,frame,frame,damage,hitAnimation);
		
	}
	protected void attackFrameBasedRange(int candyFrame, int frame0, int frame1, int damage,BufferedImage[] hitAnimation)
	{
		attackFrameBasedRange(candyFrame,frame0,frame1,damage,hitAnimation,AudioHandler.sePunch);
		}
	protected void attackFrameBasedRange(int candyFrame, int frame0, int frame1, int damage,BufferedImage[] hitAnimation, File sound)
	{
		if(!currentAnimation.equals(hitAnimation))
		{
			if(getFrame(0) == candyFrame && !bm.eyeCandy.contains(dodgeCandy))
			{
				if(charAttacking.id == ObjectId.SimonBattler)
				{
					dodgeCandy = new EyeCandy(window, super.x-72, super.y-72, BattleAnimation.cIcon, bm);
					bm.eyeCandy.add(dodgeCandy);
				}
				if(charAttacking.id == ObjectId.SkrappsBattler)
				{
					dodgeCandy = new EyeCandy(window, super.x-72, super.y-72, BattleAnimation.zIcon, bm);
					bm.eyeCandy.add(dodgeCandy);
				}
			}
			if(!hit && getFrame(0) >= frame0 && getFrame(0) <= frame1 && charAttacking.id == ObjectId.SimonBattler)
			{
				bm.eyeCandy.remove(dodgeCandy);
				if(!(charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
				{
					Global.simonHP-=damage;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking.setFrame(0,0);
					charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					hit = true;
					AudioHandler.playSound(sound);
				}
				else if((charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10) && damage-1 > 0)
				{

					Global.simonHP-=damage-1;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage-1);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					//charAttacking.setFrame(0,0);
					//charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					hit = true;
					AudioHandler.playSound(sound);
				}
			}

			if(!hit && getFrame(0) >= frame0 && getFrame(0) <= frame1 && charAttacking.id == ObjectId.SkrappsBattler)
			{
				bm.eyeCandy.remove(dodgeCandy);
				if(!(charAttacking.dodging && charAttacking.getFrame(0)>4 && charAttacking.getFrame(0)<16))
				{
					Global.partnerHP[Global.SKRAPPS]-=damage;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking.setFrame(0,0);
					charAttacking.currentAnimation = BattleAnimation.skrappsFlinch;
					hit = true;
					AudioHandler.playSound(sound);
				}
				else if((charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<16) && damage-1 > 0)
				{

					Global.partnerHP[Global.SKRAPPS]-=damage-1;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage-1);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					//charAttacking.setFrame(0,0);
					//charAttacking.currentAnimation = BattleAnimation.skrappsFlinch;
					hit = true;
					AudioHandler.playSound(AudioHandler.seHit);
				}
			}
			if(charAttacking.id == ObjectId.WilliamBattler)
			{
				if(!hit && charAttacking.currentAnimation != BattleAnimation.williamFailAnimation && charAttacking.dodging && charAttacking.getFrame(0)>12 && charAttacking.getFrame(0)<16)
				{
					bm.eyeCandy.remove(dodgeCandy);
					setFrame(0,0);
					hp-=Global.williamDefense+1;
					EyeCandy atk = new EyeCandy(window, x+100, y+64,BattleAnimation.hitAny, bm, true, Global.williamDefense+1);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking.canDodge = true;
					currentAnimation = hitAnimation;
					hit = true;
					AudioHandler.playSound(AudioHandler.seDodge);
				}
				else if(!hit && getFrame(0) >= frame0 && getFrame(0) <= frame1 && charAttacking.dodging)
				{
					Global.williamHP-=damage*2;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage*2);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking.setFrame(0,13);
					charAttacking.currentAnimation = BattleAnimation.williamFailAnimation;
					this.bm.eyeCandy.remove(dodgeCandy);
					hit = true;
					AudioHandler.playSound(sound);
				}
				else if(!hit && getFrame(0) >= frame0 && getFrame(0) <= frame1 && !charAttacking.dodging)
				{
					Global.williamHP-=damage;
					EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64,BattleAnimation.hitAny, bm, true, damage);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					charAttacking.setFrame(0,0);
					charAttacking.canDodge = true;
					charAttacking.currentAnimation = BattleAnimation.williamHitAnimation;
					this.bm.eyeCandy.remove(dodgeCandy);
					hit = true;
					AudioHandler.playSound(sound);
				}
			}
		}
		
	}
	protected int attackWalkDistance()
	{
		int dist = 0;
		if(charAttacking instanceof SimonBattler)
		{
			dist = charAttacking.x+130;
		}

		if(charAttacking instanceof WilliamBattler)
		{
			dist = charAttacking.x+70;
		}
		if(charAttacking instanceof SkrappsBattler)
		{
			dist = charAttacking.x+70;
		}
		
		return dist;
	}

}
