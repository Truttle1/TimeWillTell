package net.truttle1.time.minigames.creatrun;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.GameMode;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.blocks.Stone;
import net.truttle1.time.overworld.pallates.LomoVillage;
import net.truttle1.time.overworld.pallates.StoneAge;

public class MinigameMode1 extends GameMode{

	public double scale = 1;
	public boolean gameStarted = false;
	public int scrollX = 0;
	public int scrollY;
	public int tx;
	public int ty;
	public int creatX;
	public int creatY;
	public boolean flairmerExists = false;
	public boolean flairmerTalked = false;
	private BufferedImage stage;
	private int spentTime;
	public ArrayList<GameObject> objects = new ArrayList<GameObject>();
	public MinigameMode1(Game window) {
		super(window);
	}

	public void init()
	{

		stage = window.imageLoad.loadImage("/overworld/rooms/minigame1.png");
		loadMap();
		objects.add(new CreatureyPlayer1(window,10,1000,this));
	}
	private void loadMap()
	{

		for(int x=0; x<stage.getWidth();x++)
		{
			for(int y=0; y<stage.getHeight();y++)
			{
				if(stage.getRGB(x,y) == LomoVillage.grass.getRGB())
				{
				}
				if(stage.getRGB(x,y) == LomoVillage.stone.getRGB())
				{
					objects.add(new RunStone(x,y,window,this));
				}
				if(stage.getRGB(x,y) == LomoVillage.spike.getRGB())
				{
					objects.add(new MinigameSpike(x*100,y*100,window));
				}
				if(stage.getRGB(x,y) == LomoVillage.spike2.getRGB())
				{
					objects.add(new MinigameSpike2(x*100,y*100,window));
				}
				if(stage.getRGB(x,y) == LomoVillage.carl1.getRGB())
				{
					objects.add(new CheckpointFlag(x*100,y*100,window));
				}
				if(stage.getRGB(x,y) == LomoVillage.stone2.getRGB())
				{
					objects.add(new RunStone2(x,y,window,this));
				}
				if(stage.getRGB(x,y) == LomoVillage.creaturey1.getRGB())
				{
					objects.add(new MinigameTree(x,y,window,this));
				}
			}
		}
	}
	@Override
	public void tick() {
		spentTime++;
		for(int i=0; i<objects.size();i++)
		{
			if(spentTime<10 || objects.get(i).id != ObjectId.Grass)
			{
				objects.get(i).tick();
			}
			if(objects.get(i) instanceof MinigameTree)
			{
				objects.get(i).tick();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(window.overworldMode.LMABACKGROUND);
		g.fillRect(0, 0, window.WIDTH, window.HEIGHT);
		Graphics2D g2d = (Graphics2D) g;
		g2d.scale(scale, scale);
		g.translate(-tx, -ty);
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.Grass)
			{
				objects.get(i).render(g);
			}
		}
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.Spike || objects.get(i).id == ObjectId.Spike2 || objects.get(i).id == ObjectId.Flag)
			{
				objects.get(i).render(g);
			}
		}

		/**/
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.CreatureyMini1)
			{
				objects.get(i).render(g);
			}
		}
		for(int i=0; i<objects.size();i++)
		{
			if(objects.get(i).id == ObjectId.FlareMini1)
			{
				objects.get(i).render(g);
			}
		}
		g.translate(tx, ty);
		g2d.scale(1/scale, 1/scale);
	}

}
