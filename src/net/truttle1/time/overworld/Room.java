package net.truttle1.time.overworld;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.blocks.BoulderThin;
import net.truttle1.time.overworld.blocks.Concrete;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.blocks.Gravel;
import net.truttle1.time.overworld.blocks.InvisibleWall;
import net.truttle1.time.overworld.blocks.Lava;
import net.truttle1.time.overworld.blocks.PoisonWater;
import net.truttle1.time.overworld.blocks.Stone;
import net.truttle1.time.overworld.blocks.Tiles;
import net.truttle1.time.overworld.blocks.Warp;
import net.truttle1.time.overworld.doors.DoorModern;
import net.truttle1.time.overworld.doors.DoorLomo;
import net.truttle1.time.overworld.doors.PadlockDoor;
import net.truttle1.time.overworld.enemies.BaumberOverworld;
import net.truttle1.time.overworld.enemies.FlairmerBoss1;
import net.truttle1.time.overworld.enemies.FlairmerGOverworld;
import net.truttle1.time.overworld.enemies.FlairmerROverworld;
import net.truttle1.time.overworld.enemies.GarbzopOverworld;
import net.truttle1.time.overworld.enemies.RockstarOverworld;
import net.truttle1.time.overworld.enemies.TrukofireOverworld;
import net.truttle1.time.overworld.enemies.UrsearOverworld;
import net.truttle1.time.overworld.enemies.VultOverworld;
import net.truttle1.time.overworld.npc.*;
import net.truttle1.time.overworld.npc.carl.Carl1;
import net.truttle1.time.overworld.npc.creaturey.Creaturey1;
import net.truttle1.time.overworld.npc.cutscene.IggyOverworld1;
import net.truttle1.time.overworld.pallates.Modern;
import net.truttle1.time.overworld.pallates.LomoVillage;
import net.truttle1.time.overworld.pallates.Pyruz;
import net.truttle1.time.overworld.pallates.StoneAge;
import net.truttle1.time.overworld.pallates.StoneAgeInside;

public class Room {
	public BufferedImage stage;
	public OverworldMode om;
	public int height;
	public int width;
	public WorldSubType world;
	public BufferedImage bg;
	public RoomId id;
	public File music;
	public Room(BufferedImage stage, OverworldMode om, boolean load, BufferedImage bg, WorldSubType world, RoomId id, File music)
	{
		this.stage = stage;
		this.om = om;
		this.bg = bg;
		this.world = world;
		if(load)
		{
			loadStage();
		}
		this.id = id;
		this.music = music;
	}
	public void addPlayer(int x, int y)
	{
		GameObject player;
		if(Global.playingAs == 0) 
		{
			om.objects.add(new SimonPlayer(om.window,x,y));
		}
		else if(Global.playingAs == 1)
		{
			om.objects.add(new WilliamPlayer(om.window,x+50,y,om));
		}
	}
	public void loadStage()
	{
		OverworldMode.currentRoom = this.id;

		if(AudioHandler.music == null || !AudioHandler.currentMusic.equals(this.music))
		{
			if(Quest.quests[Quest.LOMO]<Global.LOMOCONSTANT && this.world == WorldSubType.LomoVillage && AudioHandler.currentMusic.equals(AudioHandler.lomoDeserted))
			{
				
			}
			else if(Quest.quests[Quest.ESCAPED] == 1)
			{
				
			}
			else
			{
				AudioHandler.playMusic(music);
			}
		}
		if(this.world == WorldSubType.LomoVillage && Quest.quests[Quest.LOMO]<Global.LOMOCONSTANT)
		{
			Global.playerSad = true;
		}
		else if(this.id == RoomId.Pyruz3 && Quest.quests[Quest.LOMO]<Global.LOMOCONSTANT)
		{
			Global.playerSad = true;
		}
		else if(this.world == WorldSubType.ConvexPath)
		{
			Global.playerSad = true;
		}
		else
		{
			Global.playerSad = false;
		}
		if(world == WorldSubType.StoneAge)
		{
			for(int x=0; x<stage.getWidth();x++)
			{
				for(int y=0; y<stage.getHeight();y++)
				{
					if(stage.getRGB(x,y) == StoneAge.grass.getRGB())
					{
						om.objects.add(new Grass(x,y,om.window,om));
					}
					if(stage.getRGB(x,y) == StoneAge.stone.getRGB())
					{
						om.objects.add(new Stone(x,y,om.window,om));
					}
					if(stage.getRGB(x,y) == StoneAge.enemy1.getRGB())
					{
						om.objects.add(new UrsearOverworld(x*100,y*100,om.window,om,2));
					}
					if(stage.getRGB(x,y) == StoneAge.waterTop.getRGB())
					{
						om.objects.add(new PoisonWater(om.window,x*100,y*100,OverworldAnimation.waterTop));
					}
					if(stage.getRGB(x,y) == StoneAge.water.getRGB())
					{
						om.objects.add(new PoisonWater(om.window,x*100,y*100,OverworldAnimation.water));
					}
					if(stage.getRGB(x,y) == StoneAge.enemy2.getRGB())
					{
						om.objects.add(new UrsearOverworld(x*100,y*100,om.window,om,1));
					}
					if(stage.getRGB(x,y) == StoneAge.flairBoss1.getRGB())
					{
						om.objects.add(new FlairmerBoss1(x*100,y*100,om.window,om,1));
					}
					if(stage.getRGB(x,y) == StoneAge.hilga1.getRGB())
					{
						om.objects.add(new Hilga1(om.window,x*100,y*100, om));
					}
					if(stage.getRGB(x,y) == StoneAge.warpRight.getRGB())
					{
						om.objects.add(new Warp(om.window,x*100,y*100, om,0));
					}
					if(stage.getRGB(x,y) == StoneAge.warpLeft.getRGB())
					{
						om.objects.add(new Warp(om.window,x*100,y*100, om,1));
					}
					if(stage.getRGB(x,y) == StoneAge.suzy1.getRGB())
					{
						om.objects.add(new Suzy1(om.window,x*100,y*100, om));
					}
					if(stage.getRGB(x,y) == StoneAge.suzy2.getRGB())
					{
						om.objects.add(new Suzy2(om.window,x*100,y*100, om));
					}
					if(stage.getRGB(x,y) == StoneAge.carl1.getRGB())
					{
						om.objects.add(new Carl1(om.window,x*100,y*100, om));
					}
					if(stage.getRGB(x,y) == StoneAge.creaturey1.getRGB())
					{
						om.objects.add(new Creaturey1(om.window,x*100,y*100, om));
					}
					if(stage.getRGB(x,y) == StoneAge.iggy1.getRGB())
					{
						om.objects.add(new IggyOverworld1(om.window,x*100,y*100, om));
					}
					if(stage.getRGB(x,y) == StoneAge.enemy3.getRGB())
					{
						om.objects.add(new VultOverworld(x*100,y*100,om.window, om,1));
					}
					if(stage.getRGB(x,y) == StoneAge.enemy4.getRGB())
					{
						om.objects.add(new VultOverworld(x*100,y*100,om.window, om,2));
					}
				}
			}
		}

		if(world == WorldSubType.Pyruz || world == WorldSubType.PyruzOutside)
		{
			loadPyruz();
		}
		if(world == WorldSubType.Modern || world == WorldSubType.BurgerHouse || world == WorldSubType.ConvexPath)
		{
			loadModern();
		}
		if(world == WorldSubType.LomoVillage)
		{
			for(int x=0; x<stage.getWidth();x++)
			{
				for(int y=0; y<stage.getHeight();y++)
				{
					if(stage.getRGB(x,y) == LomoVillage.grass.getRGB())
					{
						om.objects.add(new Grass(x,y,om.window,om));
					}
					if(stage.getRGB(x,y) == LomoVillage.stone.getRGB())
					{
						om.objects.add(new Stone(x,y,om.window,om));
					}
					if(stage.getRGB(x,y) == LomoVillage.stone2.getRGB())
					{
						om.objects.add(new Stone(x,y,om.window,om,OverworldAnimation.stone2,new Color(96,59,35)));
					}
					if(stage.getRGB(x,y) == LomoVillage.warpRight.getRGB())
					{
						om.objects.add(new Warp(om.window,x*100,y*100, om,0));
					}
					if(stage.getRGB(x,y) == LomoVillage.warpLeft.getRGB())
					{
						om.objects.add(new Warp(om.window,x*100,y*100, om,1));
					}
					if(stage.getRGB(x,y) == LomoVillage.carl1.getRGB())
					{
						om.objects.add(new Carl1(om.window,x*100,y*100, om));
					}
					if(stage.getRGB(x,y) == LomoVillage.creaturey1.getRGB())
					{
						om.objects.add(new Creaturey1(om.window,x*100,y*100, om));
					}
					if(stage.getRGB(x,y) == LomoVillage.iggy1.getRGB())
					{
						om.objects.add(new IggyOverworld1(om.window,x*100,y*100, om));
					}
				}
			}
		}
		if(world == WorldSubType.StoneInside)
		{
			for(int x=0; x<stage.getWidth();x++)
			{
				for(int y=0; y<stage.getHeight();y++)
				{
					if(stage.getRGB(x,y) == StoneAgeInside.stone.getRGB())
					{
						om.objects.add(new Stone(x,y,om.window,om));
					}
					if(stage.getRGB(x,y) == StoneAgeInside.enemy1.getRGB())
					{
						om.objects.add(new UrsearOverworld(x*100,y*100,om.window,om,2));
					}
					if(stage.getRGB(x,y) == StoneAgeInside.hilga1.getRGB())
					{
						om.objects.add(new Hilga1(om.window,x*100,y*100, om));
					}
					if(stage.getRGB(x,y) == StoneAgeInside.warpRight.getRGB())
					{
						om.objects.add(new Warp(om.window,x*100,y*100, om,0));
					}
					if(stage.getRGB(x,y) == StoneAgeInside.warpLeft.getRGB())
					{
						om.objects.add(new Warp(om.window,x*100,y*100, om,1));
					}
				}
			}
		}
		Global.currentRoom = this;
		height = stage.getHeight()*100;
		width = stage.getWidth()*100;
	}

	public void addObject(GameObject object)
	{
		om.objects.add(object);
	}
	public void setupWarp(int x, int y, Room r, int rx, int ry, boolean cm, File m)
	{
		for(int i=0;i<om.objects.size();i++)
		{
			if(om.objects.get(i).id == ObjectId.Warp)
			{
				Warp warp = (Warp)om.objects.get(i);
				if(warp.x == x && warp.y == y)
				{
					warp.setWarp(rx, ry, r, cm, m);
				}
				
			}
		}
	}
	public void addCaveDoor(int x, int y, Room r, int rx, int ry, boolean cm, File m)
	{
			DoorLomo door = new DoorLomo(this.om.window,x,y,this.om);
			door.setWarp(rx, ry, r, cm, m);
			om.objects.add(door);
	}
	public void addModernDoor(int x, int y, Room r, int rx, int ry, boolean cm, File m)
	{
			DoorModern door = new DoorModern(this.om.window,x,y,this.om);
			door.setWarp(rx, ry, r, cm, m);
			om.objects.add(door);
	}
	public void setupWarp(int x, int y, Room r, int rx, int ry)
	{
		for(int i=0;i<om.objects.size();i++)
		{
			if(om.objects.get(i).id == ObjectId.Warp)
			{
				Warp warp = (Warp)om.objects.get(i);
				if(warp.x == x && warp.y == y)
				{
					warp.setWarp(rx, ry, r, false, null);
				}
				
			}
		}
	}
	
	
	/**Methods for loading stages*/
	private void loadModern()
	{
		for(int x=0; x<stage.getWidth();x++)
		{
			for(int y=0; y<stage.getHeight();y++)
			{
				if(stage.getRGB(x,y) == Modern.grass.getRGB())
				{
					om.objects.add(new Grass(x,y,om.window,om));
				}
				if(stage.getRGB(x,y) == Modern.gravel.getRGB())
				{
					om.objects.add(new Gravel(x,y,om.window,om));
				}
				if(stage.getRGB(x,y) == Modern.concrete.getRGB())
				{
					om.objects.add(new Concrete(x,y,om.window,om));
				}
				if(stage.getRGB(x,y) == Modern.saveSign.getRGB())
				{
					om.objects.add(new SaveSign(om.window,x*100,y*100,om));
				}
				if(stage.getRGB(x,y) == Modern.skyscraper0.getRGB())
				{
					om.objects.add(new Skyscraper(om.window,x*100,y*100,0));
				}
				if(stage.getRGB(x,y) == Modern.skyscraper1.getRGB())
				{
					om.objects.add(new Skyscraper(om.window,x*100,y*100,1));
				}
				if(stage.getRGB(x,y) == Modern.skyscraper2.getRGB())
				{
					om.objects.add(new Skyscraper(om.window,x*100,y*100,2));
				}
				if(stage.getRGB(x,y) == Modern.skyscraper3.getRGB())
				{
					om.objects.add(new Skyscraper(om.window,x*100,y*100,3));
				}
				if(stage.getRGB(x,y) == Modern.skyscraper4.getRGB())
				{
					om.objects.add(new Skyscraper(om.window,x*100,y*100,4));
				}
				if(stage.getRGB(x,y) == Modern.trash.getRGB())
				{
					om.objects.add(new Decoration(om.window,x*100,y*100,OverworldAnimation.trash, -8));
				}
				if(stage.getRGB(x,y) == Modern.tree2.getRGB())
				{
					om.objects.add(new Decoration(om.window,x*100,y*100,OverworldAnimation.modernTree, -2));
				}
				if(stage.getRGB(x,y) == Modern.tree3.getRGB())
				{
					om.objects.add(new Decoration(om.window,x*100,y*100,OverworldAnimation.modernTree1, -2));
				}
				if(stage.getRGB(x,y) == Modern.warpRight.getRGB())
				{
					om.objects.add(new Warp(om.window,x*100,y*100, om,0));
				}
				if(stage.getRGB(x,y) == Modern.warpLeft.getRGB())
				{
					om.objects.add(new Warp(om.window,x*100,y*100, om,1));
				}
				if(stage.getRGB(x,y) == Modern.invisibleWall.getRGB())
				{
					om.objects.add(new InvisibleWall(x,y,om.window,om));
				}
				if(stage.getRGB(x,y) == Modern.tiles.getRGB())
				{
					om.objects.add(new Tiles(x,y,om.window,om));
				}
				if(stage.getRGB(x,y) == Modern.garbzop0.getRGB())
				{
					om.objects.add(new GarbzopOverworld(x*100,y*100,om.window,om,1));
				}
				if(stage.getRGB(x,y) == Modern.garbzop1.getRGB())
				{
					om.objects.add(new GarbzopOverworld(x*100,y*100,om.window,om,2));
				}
				if(stage.getRGB(x,y) == Modern.garbzop2.getRGB())
				{
					om.objects.add(new GarbzopOverworld(x*100,y*100,om.window,om,3));
				}
				if(stage.getRGB(x,y) == Modern.baumber0.getRGB())
				{
					om.objects.add(new BaumberOverworld(x*100,y*100,om.window,om,1));
				}
				if(stage.getRGB(x,y) == Modern.trukofire0.getRGB())
				{
					om.objects.add(new TrukofireOverworld(x*100,y*100,om.window,om,1));
				}
				if(stage.getRGB(x,y) == LomoVillage.stone2.getRGB())
				{
					om.objects.add(new Stone(x,y,om.window,om,OverworldAnimation.stone2,new Color(96,59,35)));
				}
			}
		}
	}
	private void loadPyruz()
	{
		for(int x=0; x<stage.getWidth();x++)
		{
			for(int y=0; y<stage.getHeight();y++)
			{
				if(stage.getRGB(x,y) == Pyruz.grass.getRGB())
				{
					om.objects.add(new Grass(x,y,om.window,om));
				}
				if(stage.getRGB(x,y) == Pyruz.stone.getRGB())
				{
					om.objects.add(new Stone(x,y,om.window,om));
				}
				if(stage.getRGB(x,y) == Pyruz.stone2.getRGB())
				{
					om.objects.add(new Stone(x,y,om.window,om,OverworldAnimation.stone2,new Color(96,59,35)));
				}
				if(stage.getRGB(x,y) == Pyruz.warpRight.getRGB())
				{
					om.objects.add(new Warp(om.window,x*100,y*100, om,0));
				}
				if(stage.getRGB(x,y) == Pyruz.warpLeft.getRGB())
				{
					om.objects.add(new Warp(om.window,x*100,y*100, om,1));
				}
				if(stage.getRGB(x,y) == Pyruz.enemy1.getRGB())
				{
					om.objects.add(new UrsearOverworld(x*100,y*100,om.window,om,2));
				}
				if(stage.getRGB(x,y) == Pyruz.enemy2.getRGB())
				{
					om.objects.add(new UrsearOverworld(x*100,y*100,om.window,om,1));
				}
				if(stage.getRGB(x,y) == Pyruz.enemy3.getRGB())
				{
					//om.objects.add(new VultOverworld(x*100,y*100,om.window,om,3));
				}
				if(stage.getRGB(x,y) == Pyruz.enemy4.getRGB())
				{
					om.objects.add(new VultOverworld(x*100,y*100,om.window,om,2));
				}
				if(stage.getRGB(x,y) == Pyruz.rockstar1.getRGB())
				{
					om.objects.add(new RockstarOverworld(x*100,y*100,om.window,om,1));
				}
				if(stage.getRGB(x,y) == Pyruz.rockstar2.getRGB())
				{
					om.objects.add(new RockstarOverworld(x*100,y*100,om.window,om,2));
				}
				if(stage.getRGB(x,y) == Pyruz.rockstar3.getRGB())
				{
					om.objects.add(new RockstarOverworld(x*100,y*100,om.window,om,3));
				}
				if(stage.getRGB(x,y) == Pyruz.flairmerEnemy2.getRGB())
				{
					om.objects.add(new FlairmerROverworld(x*100,y*100,om.window,om,2));
				}
				if(stage.getRGB(x,y) == Pyruz.flairmerEnemy1.getRGB())
				{
					om.objects.add(new FlairmerROverworld(x*100,y*100,om.window,om,1));
				}
				if(stage.getRGB(x,y) == Pyruz.flairmerEnemy3.getRGB())
				{
					om.objects.add(new FlairmerGOverworld(x*100,y*100,om.window,om,1));
				}
				if(stage.getRGB(x,y) == Pyruz.saveSign.getRGB())
				{
					om.objects.add(new SaveSign(om.window,x*100,y*100,om));
				}
				if(stage.getRGB(x,y) == Pyruz.lava.getRGB())
				{
					om.objects.add(new Lava(om.window,x*100,y*100,OverworldAnimation.lava));
				}
				if(stage.getRGB(x,y) == Pyruz.lavaTop.getRGB())
				{
					om.objects.add(new Lava(om.window,x*100,y*100,OverworldAnimation.lavaTop));
				}
				if(stage.getRGB(x,y) == Pyruz.padlockDoor.getRGB())
				{
					om.objects.add(new PadlockDoor(om.window,x*100,y*100,om));
				}
				if(stage.getRGB(x,y) == Pyruz.thinBoulder.getRGB())
				{
					om.objects.add(new BoulderThin(x,y,om.window,om));
				}
			}
		}
	}
}
