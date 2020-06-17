package net.truttle1.time.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JFrame;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
import net.truttle1.time.menu.MenuMode;
import net.truttle1.time.minigames.creatrun.MinigameMode1;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.warps.DigitalAgeWarps;
import net.truttle1.time.overworld.warps.StoneAgeWarps;
import net.truttle1.time.titlescreen.TitleMode;

public class Game extends Canvas implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1734873902104953705L;
	public final int WIDTH = 1024;
	public final int HEIGHT = 576;
	public static boolean running = false;
	public Thread display;
	public ImageLoader imageLoad = new ImageLoader();
	KeyboardInput key;
	public BattleMode battleMode;
	public OverworldMode overworldMode;
	public MinigameMode1 minigameMode1;
	public TextMode textMode;
	public TitleMode titleMode;
	public MenuMode menuMode;
	int testx;
	int testy = 64;
	public ModeType mode;
	public GraphicsLoader graphicsLoader;
	public static WorldId currentWorld;
	public static BufferedImage prehistoricBackground;
	public static BufferedImage prehistoricFaded;
	public static BufferedImage lomoVillage;
	public static BufferedImage lomoVillageAbandoned;
	public static BufferedImage caveInside;
	public static BufferedImage outsidePyruz;
	public static BufferedImage pyruz;
	public static BufferedImage pyruzBattle;
	public static BufferedImage cityFaded;
	public static BufferedImage city;
	public static BufferedImage burgerHouse;
	@Override
	public void run() {

		Global.items[Store.APPLE] = 3;
		init();
		/*
		GameLoop loop = new GameLoop(this);
		Timer time = new Timer();
		time.scheduleAtFixedRate(loop,0,24);
		*/
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 24;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		int FPS = 0;
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
				try
				{
				render();
				}
				catch(Exception e){e.printStackTrace();}
			}
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("UPDATES: " + FPS + " FPS: " + updates);
				FPS = frames;
				frames = 0;
				updates = 0;
			}
		}
	}
	public void addNotify()
	{
		super.addNotify();
		start();
	}
	public Game()
	{
		setBackground(Color.white);
		setPreferredSize(new Dimension(WIDTH,HEIGHT));

	}
	private void start()
	{
		if(display == null)
		{
			display = new Thread(this);
			display.start();
		}
		Global.initPartners();
		AudioHandler.init();
		prehistoricBackground = imageLoad.loadImage("/fight/bg/prehistoric.png");
		prehistoricFaded = imageLoad.loadImage("/overworld/bg/prehistoric.png");
		caveInside = imageLoad.loadImage("/overworld/bg/stone_00001.png");
		lomoVillage = imageLoad.loadImage("/overworld/bg/lomo_00001.png");
		lomoVillageAbandoned = imageLoad.loadImage("/overworld/bg/lomoabandoned_00001.png");
		outsidePyruz = imageLoad.loadImage("/overworld/bg/pyruzo_00001.png");
		pyruz = imageLoad.loadImage("/overworld/bg/pyruz_00001.png");
		pyruzBattle = imageLoad.loadImage("/fight/bg/pyruz.png");
		cityFaded = imageLoad.loadImage("/overworld/bg/cityfaded.png");
		city = imageLoad.loadImage("/overworld/bg/city_00001.png");
		burgerHouse = imageLoad.loadImage("/overworld/bg/burgerhouse.png");
		
		AudioHandler.playMusic(AudioHandler.title);
		textMode = new TextMode(this);
		minigameMode1 = new MinigameMode1(this);
		currentWorld = WorldId.StoneAge;
		load();
		titleMode = new TitleMode(this);
		menuMode = new MenuMode(this);
		mode = ModeType.None;
		textMode.setup("Act I/The Stone Age", 250, 250);
		Fade.run(this, ModeType.TitleScreen, true);
		Store.setNames();
		graphicsLoader = new GraphicsLoader(this);
		load();
		System.out.println(Game.currentWorld);
		graphicsLoader.start();
	}
	private void init()
	{
		this.addKeyListener(new KeyboardInput());
		running = true;
	}
	public void load()
	{

		Global.disableMovement = false;
		Global.talking = 0;
		Global.talkingTo = null;
		if(Global.OS.contains("Linux"))
		{
			Global.doChangesForLinux();
		}
		try
		{
			String line = null;
	        FileReader fileReader = new FileReader("file1.txt");
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        while((line = bufferedReader.readLine()) != null) 
	        {
	            String[] data = line.split(",");
	            if(data[0].startsWith("QUE"))
	            {
	            	Quest.quests[Integer.parseInt(data[1])] = Integer.parseInt(data[2]);
	            }
	            if(data[0].startsWith("BOX"))
	            {
	            	Global.chestOpened[Integer.parseInt(data[1])] = Boolean.parseBoolean(data[2]);
	            }
	            if(data[0].startsWith("LOCK"))
	            {
	            	Global.unlock[Integer.parseInt(data[1])] = Boolean.parseBoolean(data[2]);
	            }
	            if(data[0].startsWith("ITM"))
	            {
	            	Global.items[Integer.parseInt(data[1])] = Integer.parseInt(data[2]);
	            }
	            if(data[0].startsWith("KEY"))
	            {
	            	Global.keyItems[Integer.parseInt(data[1])] = Integer.parseInt(data[2]);
	            }
	            if(data[0].startsWith("WORLD"))
	            {
	            	if(data[1].equals("StoneAge"))
	            	{
	    				currentWorld = WorldId.StoneAge;
	            	}
	            	if(data[1].equals("Pyruz"))
	            	{
	    				currentWorld = WorldId.Pyruz;
	            	}
	            	if(data[1].equals("Digital"))
	            	{
	    				currentWorld = WorldId.Digital;
	            	}
		            overworldMode.clearRoomWarps();
	            }
	            if(data[0].startsWith("RM"))
	            {
	            	try
	            	{

		            	overworldMode.clearRoomWarps();
	            	}catch(Exception e) {}
	            	if(data[1].equals("SimonHouse"))
	            	{
	    				overworldMode.simonHouse.loadStage();
	    				StoneAgeWarps.warps(overworldMode.simonHouse);
	            	}
	            	if(data[1].equals("StoneAge1"))
	            	{
	    				overworldMode.stoneAge1.loadStage();
	    				StoneAgeWarps.warps(overworldMode.stoneAge1);
	            	}
	            	if(data[1].equals("StoneAge2"))
	            	{
	    				overworldMode.stoneAge2.loadStage();
	    				StoneAgeWarps.warps(overworldMode.stoneAge2);
	            	}
	            	if(data[1].equals("StoneAge3"))
	            	{
	    				overworldMode.stoneAge3.loadStage();
	    				StoneAgeWarps.warps(overworldMode.stoneAge3);
	            	}
	            	if(data[1].equals("StoneAge4"))
	            	{
	    				overworldMode.stoneAge4.loadStage();
	    				StoneAgeWarps.warps(overworldMode.stoneAge4);
	            	}
	            	if(data[1].equals("StoneAge5"))
	            	{
	    				overworldMode.stoneAge5.loadStage();
	    				StoneAgeWarps.warps(overworldMode.stoneAge5);
	            	}
	            	if(data[1].equals("Lomo1"))
	            	{
	    				overworldMode.lomo1.loadStage();
	    				StoneAgeWarps.warps(overworldMode.lomo1);
	            	}
	            	if(data[1].equals("Lomo2"))
	            	{
	    				overworldMode.lomo2.loadStage();
	    				StoneAgeWarps.warps(overworldMode.lomo2);
	            	}
	            	if(data[1].equals("Lomo3"))
	            	{
	    				overworldMode.lomo3.loadStage();
	    				StoneAgeWarps.warps(overworldMode.lomo3);
	            	}
	            	if(data[1].equals("PyruzOutside"))
	            	{
	    				overworldMode.pyruzOutside.loadStage();
	    				StoneAgeWarps.warps(overworldMode.pyruzOutside);
	            	}
	            	if(data[1].equals("Pyruz1"))
	            	{
	    				overworldMode.pyruz1.loadStage();
	    				StoneAgeWarps.warps(overworldMode.pyruz1);
	            	}
	            	if(data[1].equals("Pyruz2"))
	            	{
	    				overworldMode.pyruz2.loadStage();
	    				StoneAgeWarps.warps(overworldMode.pyruz2);
	            	}
	            	if(data[1].equals("Pyruz3"))
	            	{
	    				overworldMode.pyruz3.loadStage();
	    				StoneAgeWarps.warps(overworldMode.pyruz3);
	            	}
	            	if(data[1].equals("Dunstan0") || data[1].equals("Modern0"))
	            	{
	    				overworldMode.modern0.loadStage();
	    				DigitalAgeWarps.warps(overworldMode.modern0);
	            	}
	            	if(data[1].equals("Modern2"))
	            	{
	    				overworldMode.modern2.loadStage();
	    				DigitalAgeWarps.warps(overworldMode.modern2);
	            	}
	            	if(data[1].equals("Modern3"))
	            	{
	    				overworldMode.modern3.loadStage();
	    				DigitalAgeWarps.warps(overworldMode.modern3);
	            	}
	            	if(data[1].equals("Park"))
	            	{
	    				overworldMode.park.loadStage();
	    				DigitalAgeWarps.warps(overworldMode.park);
	            	}
	            }
	            if(data[0].startsWith("PLY"))
	            {
	            	OverworldMode.stoneAge1.addPlayer(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
	            }
	            if(data[0].startsWith("SHP"))
	            {
	            	Global.simonHP = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("MSHP"))
	            {
	            	Global.simonHPMax = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("SSP"))
	            {
	            	Global.simonSP = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("MSSP"))
	            {
	            	Global.simonSPMax = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("SXP"))
	            {
	            	Global.simonXP = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("SLV"))
	            {
	            	Global.simonLevel = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("WSP"))
	            {
	            	Global.williamSP = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("WPOW"))
	            {
	            	Global.williamPow = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("SPOW"))
	            {
	            	Global.simonPow = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("MWSP"))
	            {
	            	Global.williamSPMax = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("WHP"))
	            {
	            	Global.williamHP = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("MWHP"))
	            {
	            	Global.williamHPMax = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("WXP"))
	            {
	            	Global.williamXP = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("WLV"))
	            {
	            	Global.williamLevel = Integer.parseInt(data[1]);
	            }https://gamejolt.com/games/timewilltell/351572worl
	            if(data[0].startsWith("HW"))
	            {
	            	Global.hasWilliam = Boolean.parseBoolean(data[1]);
	            }
	            if(data[0].startsWith("HS"))
	            {
	            	Global.hasSimon = Boolean.parseBoolean(data[1]);
	            }
	            if(data[0].startsWith("HP"))
	            {
	            	Global.hasPartner = Boolean.parseBoolean(data[1]);
	            }
	            if(data[0].startsWith("HAP"))
	            {
	            	Global.unlockedPartner[Integer.parseInt(data[2])] = Boolean.parseBoolean(data[1]);
	            }
	            if(data[0].startsWith("PLA"))
	            {
	            	Global.playingAs = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("CPA"))
	            {
	            	Global.currentPartner = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("$$$"))
	            {
	            	Global.money = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("PLVL"))
	            {
	            	if(Integer.parseInt(data[1]) != 0)
	            	{
	            		Global.partnerLevel[Integer.parseInt(data[2])] = Integer.parseInt(data[1]);
	            	}
	            	
	            }
	            if(data[0].startsWith("PHP"))
	            {
	            	Global.partnerHP[Integer.parseInt(data[2])] = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("MPHP"))
	            {
	            	if(Integer.parseInt(data[1]) == 0)
	            	{
	            		Global.partnerHP[Integer.parseInt(data[2])] = Global.partnerHPMax[Integer.parseInt(data[2])];
	            	}
	            	else
	            	{
		            	Global.partnerHPMax[Integer.parseInt(data[2])] = Integer.parseInt(data[1]);
	            	}
	            }
	            if(data[0].startsWith("PSP"))
	            {
	            	Global.partnerSP[Integer.parseInt(data[2])] = Integer.parseInt(data[1]);
	            }
	            if(data[0].startsWith("MPSP"))
	            {
	            	if(Integer.parseInt(data[1]) == 0)
	            	{
	            		Global.partnerSP[Integer.parseInt(data[2])] = Global.partnerSPMax[Integer.parseInt(data[2])];
	            	}
	            	else
	            	{
	            		Global.partnerSPMax[Integer.parseInt(data[2])] = Integer.parseInt(data[1]);
	            	}
	            }
	            if(data[0].startsWith("PPOW"))
	            {
	            	if(Integer.parseInt(data[1]) != 0)
	            	{
	            		Global.partnerPow[Integer.parseInt(data[2])] = Integer.parseInt(data[1]);
	            	}
	            }
	            if(data[0].startsWith("PXP"))
	            {
	            	Global.partnerXP[Integer.parseInt(data[2])] = Integer.parseInt(data[1]);
	            }
	        }
		}catch(Exception e) {e.printStackTrace();}
	}
	public void save()
	{

		try (Writer writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("file1.txt"), "utf-8"))) 
		{
			for(int i = 0; i<Quest.quests.length;i++)
			{
				writer.write("QUE," + i + "," + Quest.quests[i] + "\n");
			}
			for(int i = 0; i<Global.chestOpened.length;i++)
			{
				writer.write("BOX," + i + "," + Global.chestOpened[i] + "\n");
				writer.write("LOCK," + i + "," + Global.unlock[i] + "\n");
			}
			writer.write("WORLD," + Game.currentWorld + "\n");
			writer.write("RM," + OverworldMode.currentRoom + "\n");
			writer.write("SHP," + Global.simonHP + "\n");
			writer.write("MSHP," + Global.simonHPMax + "\n");
			writer.write("WHP," + Global.williamHP + "\n");
			writer.write("MWHP," + Global.williamHPMax + "\n");
			writer.write("SSP," + Global.simonSP + "\n");
			writer.write("MSSP," + Global.simonSPMax + "\n");
			writer.write("WSP," + Global.williamSP + "\n");
			writer.write("MWSP," + Global.williamSPMax + "\n");
			writer.write("WPOW," + Global.williamPow + "\n");
			writer.write("SPOW," + Global.simonPow + "\n");
			writer.write("HW," + Global.hasWilliam + "\n");
			writer.write("HP," + Global.hasPartner + "\n");
			writer.write("HS," + Global.hasSimon + "\n");
			writer.write("SXP," + Global.simonXP + "\n");
			writer.write("WXP," + Global.williamXP + "\n");
			writer.write("SLV," + Global.simonLevel + "\n");
			writer.write("WLV," + Global.williamLevel + "\n");
			writer.write("PLA," + Global.playingAs + "\n");
			writer.write("$$$," + Global.money + "\n");
			writer.write("CPA," + Global.currentPartner + "\n");
			for(int i=0; i<99;i++)
			{
				writer.write("PHP," + Global.partnerHP[i] + "," + i + "\n");
				writer.write("MPHP," + Global.partnerHPMax[i] + "," + i + "\n");
				writer.write("PSP," + Global.partnerSP[i] + "," + i + "\n");
				writer.write("MPSP," + Global.partnerSPMax[i] + "," + i + "\n");
				writer.write("PPOW," + Global.partnerPow[i] + "," + i + "\n");
				writer.write("PXP," + Global.partnerXP[i] + "," + i + "\n");
				writer.write("HAP," + Global.unlockedPartner[i] + "," + i + "\n");
				writer.write("PLVL," + Global.partnerLevel[i] + "," + i + "\n");
				
			}
			for(int i=0; i<overworldMode.objects.size();i++)
			{
				if(overworldMode.objects.get(i).id == ObjectId.Player)
				{
					GameObject tempPlayer = (GameObject)overworldMode.objects.get(i);
					writer.write("PLY," + tempPlayer.x + "," + tempPlayer.y + "\n");
				}
			}
			for(int i = 0; i<Global.items.length;i++)
			{
				writer.write("ITM," + i + "," + Global.items[i] + "\n");
				writer.write("KEY," + i + "," + Global.keyItems[i] + "\n");
			}
		}
		catch(Exception e) {e.printStackTrace();}
	}
	public void tick()
	{
		this.requestFocusInWindow();
		//Global.williamHP =0;
		if(Global.williamSP>Global.williamSPMax)
		{
			Global.williamSP = Global.williamSPMax;
		}
		if(Global.simonSP>Global.simonSPMax)
		{
			Global.simonSP = Global.simonSPMax;
		}
		for(int i=0; i<Global.partnerSP.length;i++)
		{
			if(Global.partnerSP[i]>Global.partnerSPMax[i])
			{
				Global.partnerSP[i] = Global.partnerSPMax[i];
			}
		}
		if(Global.williamHP>Global.williamHPMax)
		{
			Global.williamHP = Global.williamHPMax;
		}
		if(Global.simonHP>Global.simonHPMax)
		{
			Global.simonHP = Global.simonHPMax;
		}
		if(Global.partnerHP[Global.currentPartner]>Global.partnerHPMax[Global.currentPartner])
		{
			Global.partnerHP[Global.currentPartner] = Global.partnerHPMax[Global.currentPartner];
		}
		if(Global.save && mode == ModeType.Overworld)
		{
			save();
			Global.save = false;
		}
		if(Global.load)
		{
			load();
			Global.load = false;
			Global.disableMovement = false;
		}
		if(mode == ModeType.Minigame1)
		{
			minigameMode1.tick();
		}
		SpeechBubble.tick();
		if(mode == ModeType.Battle)
		{
			battleMode.tick();
		}
		if(mode == ModeType.Text)
		{
			textMode.tick();
		}
		if(mode == ModeType.TitleScreen)
		{
			titleMode.tick();
		}
		if(mode == ModeType.AgeMenu)
		{
			menuMode.tick();
		}
		if(mode == ModeType.Overworld)
		{
			Store.runStore();
			if(!Fade.pause)
			{
				overworldMode.tick();
			}
			for(int i=0; i<overworldMode.objects.size();i++)
			{
				if(overworldMode.objects.get(i).id == ObjectId.Video)
				{
					overworldMode.objects.get(i).tick();
				}
			}
		}
		if(Global.zPressed)
		{
			Global.zPressed = false;
		}
		if(Global.xPressed)
		{
			Global.xPressed = false;
		}
		if(Global.cPressed)
		{
			Global.cPressed = false;
		}
		if(Global.vPressed)
		{
			Global.vPressed = false;
		}
		if(Global.leftPressed)
		{
			Global.leftPressed = false;
		}
		if(Global.rightPressed)
		{
			Global.rightPressed = false;
		}
		if(Global.downPressed)
		{
			Global.downPressed = false;
		}
		if(Global.upPressed)
		{
			Global.upPressed = false;
		}
	}
	public void render()
	{
		if(Global.OS.contains("Linux"))
		{
			Toolkit.getDefaultToolkit().sync();
		}
		BufferStrategy bs = null;
		try
		{
		bs = this.getBufferStrategy();
		if (bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		}
		catch(Exception e) {System.out.println("THERE IS PROBLEM!!! (Problem #00001)");return;}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g = bs.getDrawGraphics();
		if(mode == ModeType.Battle)
		{
			battleMode.render(g);
		}
		else if(mode == ModeType.Overworld)
		{
			overworldMode.render(g);
			Store.renderStore(g);
		}
		else if(mode == ModeType.Minigame1)
		{
			minigameMode1.render(g);
		}
		else if(mode == ModeType.Text)
		{
			textMode.render(g);
		}
		else if(mode == ModeType.TitleScreen)
		{
			titleMode.render(g);
		}
		else if(mode == ModeType.AgeMenu)
		{
			menuMode.render(g);
		}
		SpeechBubble.render(g);
		Fade.render(g);
		g.dispose();
		bs.show();
	}
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Time Will Tell");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game window = new Game();
		frame.add(window);
		frame.setResizable(false);
		frame.pack();
		frame.setFocusable(true);
		frame.setVisible(true);
	}
}
