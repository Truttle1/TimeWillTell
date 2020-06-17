package net.truttle1.time.overworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameMode;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.WorldId;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.blocks.BoulderThin;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.doors.DoorModern;
import net.truttle1.time.overworld.doors.DoorLomo;
import net.truttle1.time.overworld.warps.DigitalAgeWarps;
import net.truttle1.time.overworld.warps.StoneAgeWarps;

public class OverworldMode extends GameMode{

	BufferedImage testRoom;
	public int tx;
	public int ty;
	public int parallaxX;
	public int parallaxOffset;
	public boolean pauseMenuOpen = false;
	private PauseMenu pauseMenu;
	public static RoomId currentRoom;
	public static Room test;
	public static Room simonHouse;
	public static Room stoneAge1;
	public static Room stoneAge2;
	public static Room stoneAge3;
	public static Room stoneAge4;
	public static Room stoneAge5;
	public static Room pyruzOutside;
	public static Room pyruz1;
	public static Room pyruz2;
	public static Room pyruz3;
	public static Room pyruzPrison;

	public static Room lomo1;
	public static Room lomo2;
	public static Room lomo3;
	public static Room lomoBldg0;
	public static Room lomoBldg1;
	public static Room lomoBldg2;
	public static Room lomoBldg3;

	public static Room modern0;
	public static Room modern1;
	public static Room modern2;
	public static Room modern3;
	public static Room park;

	public static Room burgerHouse0;
	public static Room burgerHouse1;
	public static Room burgerHouse2;
	
	final Color PHBACKGROUND = new Color(158,208,255);
	final Color LMBACKGROUND = new Color(158,208,255);
	public final Color LMABACKGROUND = new Color(159,171,204);
	final Color CVBACKGROUND = new Color(145,145,145);
	final Color PYOBACKGROUND = new Color(222,156,120);
	final Color PYBACKGROUND = new Color(136,67,26);
	final Color DGBACKGROUND = new Color(128,255,255);
	final Color BHBACKGROUND = new Color(255,0,0);
	public static GameObject currentWarp;
	public volatile ArrayList<GameObject> objects = new ArrayList<GameObject>();
	public OverworldMode(Game window) {
		super(window);
		this.tx = -9999;
		this.ty = -9999;
		BufferedImage house1 = window.imageLoad.loadImage("/overworld/rooms/simonhouse.png");
		BufferedImage stone1 = window.imageLoad.loadImage("/overworld/rooms/stoneage1.png");
		BufferedImage stone2 = window.imageLoad.loadImage("/overworld/rooms/stoneage2.png");
		BufferedImage stone3 = window.imageLoad.loadImage("/overworld/rooms/stoneage3.png");
		BufferedImage stone4 = window.imageLoad.loadImage("/overworld/rooms/stoneage4.png");
		BufferedImage stone5 = window.imageLoad.loadImage("/overworld/rooms/stoneage5.png");
		BufferedImage lom1 = window.imageLoad.loadImage("/overworld/rooms/lomo1.png");
		BufferedImage lom2 = window.imageLoad.loadImage("/overworld/rooms/lomo2.png");
		BufferedImage lom3 = window.imageLoad.loadImage("/overworld/rooms/lomo3.png");
		BufferedImage pOut = window.imageLoad.loadImage("/overworld/rooms/pyruzoutside.png");
		BufferedImage py1 = window.imageLoad.loadImage("/overworld/rooms/pyruz1.png");
		BufferedImage py2 = window.imageLoad.loadImage("/overworld/rooms/pyruz2.png");
		BufferedImage py3 = window.imageLoad.loadImage("/overworld/rooms/pyruz3.png");
		BufferedImage pyp = window.imageLoad.loadImage("/overworld/rooms/pyruzprison.png");
		BufferedImage lomH = window.imageLoad.loadImage("/overworld/rooms/lomohouse.png");
		
		BufferedImage ds0 = window.imageLoad.loadImage("/overworld/rooms/modern0.png");
		BufferedImage ds1 = window.imageLoad.loadImage("/overworld/rooms/modern1.png");
		BufferedImage ds2 = window.imageLoad.loadImage("/overworld/rooms/modern2.png");
		BufferedImage ds3 = window.imageLoad.loadImage("/overworld/rooms/modern3.png");
		BufferedImage ds4 = window.imageLoad.loadImage("/overworld/rooms/modern4.png");
		
		BufferedImage bh = window.imageLoad.loadImage("/overworld/rooms/burgerhouse.png");
		
		//Stone Age
		simonHouse = new Room(house1,this,false,Game.caveInside,WorldSubType.StoneInside,RoomId.SimonHouse,AudioHandler.prehistoricMusic);
		stoneAge1 = new Room(stone1,this,false,Game.prehistoricFaded,WorldSubType.StoneAge,RoomId.StoneAge1,AudioHandler.prehistoricMusic);
		stoneAge2 = new Room(stone2,this,false,Game.prehistoricFaded,WorldSubType.StoneAge,RoomId.StoneAge2,AudioHandler.prehistoricMusic);
		stoneAge3 = new Room(stone3,this,false,Game.prehistoricFaded,WorldSubType.StoneAge,RoomId.StoneAge3,AudioHandler.prehistoricMusic);
		stoneAge4 = new Room(stone4,this,false,Game.prehistoricFaded,WorldSubType.StoneAge,RoomId.StoneAge4,AudioHandler.prehistoricMusic);
		stoneAge5 = new Room(stone5,this,false,Game.prehistoricFaded,WorldSubType.StoneAge,RoomId.StoneAge5,AudioHandler.prehistoricMusic);
		lomo1 = new Room(lom1,this,false,Game.lomoVillage,WorldSubType.LomoVillage,RoomId.Lomo1,AudioHandler.lomoMusic);
		lomo2 = new Room(lom2,this,false,Game.lomoVillage,WorldSubType.LomoVillage,RoomId.Lomo2,AudioHandler.lomoMusic);
		lomo3 = new Room(lom3,this,false,Game.lomoVillage,WorldSubType.LomoVillage,RoomId.Lomo3,AudioHandler.lomoMusic);
		pyruzOutside = new Room(pOut,this,false,Game.outsidePyruz,WorldSubType.PyruzOutside,RoomId.PyruzOutside,AudioHandler.mountainLoop);
		pyruz1 = new Room(py1,this,false,Game.pyruz,WorldSubType.Pyruz,RoomId.Pyruz1,AudioHandler.mountainLoop);
		pyruz2 = new Room(py2,this,false,Game.pyruz,WorldSubType.Pyruz,RoomId.Pyruz2,AudioHandler.mountainLoop);
		pyruz3 = new Room(py3,this,false,Game.pyruz,WorldSubType.Pyruz,RoomId.Pyruz3,AudioHandler.throneRoom);
		pyruzPrison = new Room(pyp,this,false,Game.pyruz,WorldSubType.Pyruz,RoomId.PyruzPrison,AudioHandler.evilShuffle);

		lomoBldg0 = new Room(lomH,this,false,Game.caveInside,WorldSubType.StoneInside,RoomId.LomoBldg0,AudioHandler.lomoMusic);
		lomoBldg1 = new Room(lomH,this,false,Game.caveInside,WorldSubType.StoneInside,RoomId.LomoBldg1,AudioHandler.lomoMusic);
		lomoBldg2 = new Room(lomH,this,false,Game.caveInside,WorldSubType.StoneInside,RoomId.LomoBldg2,AudioHandler.lomoMusic);
		lomoBldg3 = new Room(lomH,this,false,Game.caveInside,WorldSubType.StoneInside,RoomId.LomoBldg3,AudioHandler.lomoMusic);
		
		//Digital Age
		modern0 = new Room(ds0,this,false,Game.cityFaded,WorldSubType.Modern,RoomId.Modern0,AudioHandler.metro);
		modern1 = new Room(ds1,this,false,Game.cityFaded,WorldSubType.Modern,RoomId.Modern1,AudioHandler.metro);
		modern2 = new Room(ds2,this,false,Game.cityFaded,WorldSubType.Modern,RoomId.Modern2,AudioHandler.metro);
		modern3 = new Room(ds3,this,false,Game.cityFaded,WorldSubType.Modern,RoomId.Modern3,AudioHandler.metro);
		park = new Room(ds4,this,false,Game.cityFaded,WorldSubType.Modern,RoomId.Park,AudioHandler.metro);
		
		burgerHouse0 = new Room(bh,this,false,Game.burgerHouse,WorldSubType.BurgerHouse,RoomId.Burger0,AudioHandler.metro);
		burgerHouse1 = new Room(bh,this,false,Game.burgerHouse,WorldSubType.BurgerHouse,RoomId.Burger1,AudioHandler.metro);
		burgerHouse2 = new Room(bh,this,false,Game.burgerHouse,WorldSubType.BurgerHouse,RoomId.Burger2,AudioHandler.metro);
		
		simonHouse.loadStage();
		simonHouse.addPlayer(200, 500);
		StoneAgeWarps.warps(simonHouse);
		pauseMenu = new PauseMenu(this);
//		stoneAge3.loadStage();
//		stoneAge3.addPlayer(200, 500);
//		StoneAgeWarps.warps(stoneAge3);
//		Quest.quests[Quest.TUTORIAL] = 7;
	}

	@Override
	public void tick() {
		if(Quest.quests[Quest.LOMO] < Global.LOMOCONSTANT && (currentRoom == RoomId.Lomo1 || currentRoom == RoomId.Lomo2  || currentRoom == RoomId.Lomo3))
		{
			if(AudioHandler.currentMusic.equals(AudioHandler.lomoMusic))
			{
				AudioHandler.playMusic(AudioHandler.lomoDeserted);
			}
		}
		if(Quest.quests[Quest.LOMO]<Global.LOMOCONSTANT && Global.currentRoom.world == WorldSubType.LomoVillage)
		{
			Global.currentRoom.bg = Game.lomoVillageAbandoned;
		}
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i) instanceof Grass)
			{
				if(objects.get(i).x>tx-200 && objects.get(i).x<tx+2000)
				{
					objects.get(i).tick();
				}
			}
			else
			{
				objects.get(i).tick();
			}
		}
		for(int i=0; i<eyeCandy.size();i++)
		{
			eyeCandy.get(i).tick();
		}
		if(Global.xPressed && !pauseMenuOpen && Global.talking == 0 && !Global.disableMovement)
		{
			pauseMenuOpen = true;
			Global.xPressed = false;
			pauseMenu.closed = false;
		}
		else if(pauseMenu.closed)
		{
			pauseMenuOpen = false;
		}
		if(pauseMenuOpen)
		{
			Global.disableMovement = true;
			pauseMenu.tick();
		}
		if(Global.comingFromTime == 0 && window.menuMode.ready)
		{
			window.graphicsLoader.run();
			Game.currentWorld = WorldId.StoneAge;
			System.out.println("Hi!");
			OverworldMode.stoneAge3.loadStage();
			OverworldMode.stoneAge3.addPlayer(2500, 700);
			StoneAgeWarps.warps(OverworldMode.stoneAge3);
			Global.comingFromTime = -1;
		}
		if(Global.comingFromTime == 3 && window.menuMode.ready)
		{
			if(Quest.quests[Quest.ESCAPED] == 3)
			{
				window.textMode.setup("Act II/The Digital Age", 250, 250);
				Fade.run(window, ModeType.Text, true);
			}
			window.graphicsLoader.run();
			Game.currentWorld = WorldId.Digital;
			System.out.println("Hi!");
			OverworldMode.modern0.loadStage();
			OverworldMode.modern0.addPlayer(400, 800);
			DigitalAgeWarps.warps(OverworldMode.modern0);
			window.menuMode.ready = false;
			if(!Fade.running)
			{
				Global.comingFromTime = -1;
			}
		}
	}

	@Override
	public void render(Graphics g) {

		if(Global.currentRoom.world == WorldSubType.StoneAge)
		{
			g.setColor(PHBACKGROUND);
		}
		if(Global.currentRoom.world == WorldSubType.Modern)
		{
			g.setColor(DGBACKGROUND);
		}
		if(Global.currentRoom.world == WorldSubType.BurgerHouse)
		{
			g.setColor(BHBACKGROUND);
		}
		if(Global.currentRoom.world == WorldSubType.StoneInside)
		{
			g.setColor(CVBACKGROUND);
		}
		if(Global.currentRoom.world == WorldSubType.PyruzOutside)
		{
			g.setColor(PYOBACKGROUND);
		}
		if(Global.currentRoom.world == WorldSubType.Pyruz)
		{
			g.setColor(PYBACKGROUND);
		}
		if(Global.currentRoom.world == WorldSubType.LomoVillage)
		{
			if(Quest.quests[Quest.LOMO]<Global.LOMOCONSTANT )
			{
				g.setColor(LMABACKGROUND);
			}
			else
			{
				g.setColor(LMBACKGROUND);
			}
		}
		g.fillRect(0, 0, window.WIDTH, window.HEIGHT);
		if(Global.currentRoom.world == WorldSubType.LomoVillage)
		{
			g.translate((int) ((-(tx))%1024), -ty);
			g.drawImage(Global.currentRoom.bg,0,Global.currentRoom.height-576,null);
			g.translate((int) (((tx))%1024), ty);
		}
		else if(Global.currentRoom.world == WorldSubType.BurgerHouse)
		{
			g.translate((int) ((-(tx))%1024), -ty);
			g.drawImage(Global.currentRoom.bg,16,Global.currentRoom.height-576,null);
			g.translate((int) (((tx))%1024), ty);
		}
		else if(Global.currentRoom.world == WorldSubType.Pyruz)
		{
			g.translate((int) ((-(tx))%1024), -ty%576);
			for(int i=0; i<Global.currentRoom.height;i+=576)
			{
				//g.drawImage(Global.currentRoom.bg,0,Global.currentRoom.height-i,null);
			}
			g.drawImage(Global.currentRoom.bg,0,0,null);
			
			g.translate((int) (((tx))%1024), ty%576);
		}
		else
		{
			g.translate((int) ((-0.5*(tx))%1024), -ty);
			g.drawImage(Global.currentRoom.bg,0,Global.currentRoom.height-576,null);
			g.translate((int) ((0.5*(tx))%1024), ty);
		}
		g.translate(-tx, -ty);
		for(int i=0; i<objects.size();i++)
		{
			if(!(objects.get(i).id == ObjectId.Decoration) && objects.get(i).id != ObjectId.Video  && objects.get(i).id != ObjectId.BaumberOverworld  && objects.get(i).id != ObjectId.GarbzopOverworld && objects.get(i).id != ObjectId.Grass && objects.get(i).id != ObjectId.NPC &&objects.get(i).id != ObjectId.Door && objects.get(i).id != ObjectId.Item && objects.get(i).id != ObjectId.Player && objects.get(i).id != ObjectId.UrsearOverworld && objects.get(i).id != ObjectId.RockstarOverworld && objects.get(i).id != ObjectId.FlairmerOverworld && objects.get(i).id != ObjectId.IggyOverworld  && objects.get(i).id != ObjectId.Foreground  && objects.get(i).id != ObjectId.ForegroundNPC
					&& objects.get(i).id != ObjectId.Warp)
			{
				objects.get(i).render(g);
			}
		}
		for(int i=0; i<eyeCandy.size();i++)
		{
			eyeCandy.get(i).render(g);
		}

		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.Door && objects.get(i) instanceof DoorLomo)
			{
				objects.get(i).render(g);
			}
		}
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.Decoration)
			{
				objects.get(i).render(g);
			}
		}

		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.Warp)
			{
				objects.get(i).render(g);
			}
		}

		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.Door && objects.get(i) instanceof DoorModern)
			{
				objects.get(i).render(g);
			}
		}
		for(int i=0; i<objects.size();i++)
		{
			if((objects.get(i).id == ObjectId.Grass) && !(objects.get(i) instanceof BoulderThin))
			{
				GameObject obj = objects.get(i);
				if(obj.x<tx+2000 || obj.x>tx-200)
				{
					obj.render(g);
				}
			}
		}

		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.DecorationOverGrass)
			{
				objects.get(i).render(g);
			}
		}

		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.NPC)
			{
				objects.get(i).render(g);
			}
		}

		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i) instanceof BoulderThin)
			{
				GameObject obj = objects.get(i);
				if(obj.x<tx+2000 || obj.x>tx-200)
				{
					obj.render(g);
				}
			}
		}
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.Foreground)
			{
				objects.get(i).render(g);
			}
		}
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.Player)
			{
				objects.get(i).render(g);
			}
			if(objects.get(i).id == ObjectId.UrsearOverworld)
			{
				objects.get(i).render(g);
			}
			if(objects.get(i).id == ObjectId.RockstarOverworld)
			{
				objects.get(i).render(g);
			}
			if(objects.get(i).id == ObjectId.FlairmerOverworld)
			{
				objects.get(i).render(g);
			}
			if(objects.get(i).id == ObjectId.GarbzopOverworld)
			{
				objects.get(i).render(g);
			}
			if(objects.get(i).id == ObjectId.BaumberOverworld)
			{
				objects.get(i).render(g);
			}
		}
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.Item)
			{
				objects.get(i).render(g);
			}
		}
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.IggyOverworld)
			{
				objects.get(i).render(g);
			}
		}
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.ForegroundNPC)
			{
				objects.get(i).render(g);
			}
		}
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.Video)
			{
				objects.get(i).render(g);
			}
		}
		g.translate(tx, ty);
		if(pauseMenuOpen)
		{
			pauseMenu.render(g);
		}
		if(Global.playingAs == 1 && Global.hurt)
		{
			g.setColor(Color.red);
			g.fillRect(64, 128, 120, 32);
			g.setColor(Color.green);
			g.fillRect(64, 128, (int)(120*(double)Global.williamHP/(double)Global.williamHPMax), 32);
		
		}
		if(Global.playingAs == 0 && Global.hurt)
		{
			g.setColor(Color.red);
			g.fillRect(64, 128, 120, 32);
			g.setColor(Color.orange);
			g.fillRect(64, 128, (int)(120*(double)Global.simonHP/(double)Global.simonHPMax), 32);
		
		}
		if(Global.playingAs == 1 && Global.williamHP <= 0)
		{
			Global.disableMovement = true;
			for(int i=0; i<objects.size();i++)
			{
				if(objects.get(i).id == ObjectId.Player)
				{	
					AudioHandler.stopMusic();
					AudioHandler.playSound(AudioHandler.seLose);
					EyeCandy die = new EyeCandy(window, objects.get(i).x, objects.get(i).y, BattleAnimation.williamDeadAnimation, this);
					die.setRepeating(true);
					eyeCandy.add(die);
					objects.remove(i);
					
				}
			}
			g.setColor(Color.gray.darker());
			g.setFont(Global.winFont2);
			g.drawString("Game Over!", 305, 162);
			g.setColor(Color.red);
			g.setFont(Global.winFont2);
			g.drawString("Game Over!", 300, 156);
			for(int i=0; i<eyeCandy.size();i++)
			{
				eyeCandy.get(i).render(g);
			}
		}
		if(Global.playingAs == 0 && Global.simonHP <= 0)
		{
			Global.disableMovement = true;
			for(int i=0; i<objects.size();i++)
			{
				if(objects.get(i).id == ObjectId.Player)
				{	
					EyeCandy die = new EyeCandy(window, objects.get(i).x, objects.get(i).y, BattleAnimation.simonHitAnimation, this);
					die.setRepeating(true);
					eyeCandy.add(die);
					objects.remove(i);
				}
			}
			g.setColor(Color.gray.darker());
			g.setFont(Global.winFont2);
			g.drawString("Game Over!", 305, 162);
			g.setColor(Color.red);
			g.setFont(Global.winFont2);
			g.drawString("Game Over!", 300, 156);
			for(int i=0; i<eyeCandy.size();i++)
			{
				eyeCandy.get(i).render(g);
			}
		}

		if(Global.comingFromTime == 3)
		{
			if(Quest.quests[Quest.ESCAPED] == 3)
			{
				g.setColor(Color.black);
				g.fillRect(0, 0, 1024, 576);
			}
		}
	}
	public void clearRoom()
	{
		for(int h = 0; h<50;h++)
		{
			for(int i=0; i<objects.size();i++)
			{
				if(objects.get(i).id != ObjectId.Warp && objects.get(i).id != ObjectId.Door)
				{
					objects.remove(i);
				}
			}
			for(int i=0; i<eyeCandy.size();i++)
			{
				if(eyeCandy.get(i).id != ObjectId.Warp)
				{
					eyeCandy.remove(i);
				}
			}
		}
	}
	public void clearRoomWarps()
	{
		for(int h = 0; h<50;h++)
		{
			for(int i=0; i<objects.size();i++)
			{
				objects.remove(i);
			}
		}
	}
	public void stopPlayerMoving(GameObject player)
	{
		if(player instanceof SimonPlayer)
		{
			player.currentAnimation = BattleAnimation.simonIdleAnimation;
		}
		if(player instanceof WilliamPlayer)
		{
			player.currentAnimation = BattleAnimation.williamIdle2;
		}
	}
}
