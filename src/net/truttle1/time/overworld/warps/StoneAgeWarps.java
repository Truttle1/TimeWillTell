package net.truttle1.time.overworld.warps;

import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.WorldId;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.Room;
import net.truttle1.time.overworld.RoomId;
import net.truttle1.time.overworld.TimeMachine;
import net.truttle1.time.overworld.blocks.BoulderThin;
import net.truttle1.time.overworld.blocks.Stone;
import net.truttle1.time.overworld.blocks.Tree;
import net.truttle1.time.overworld.doors.Door;
import net.truttle1.time.overworld.enemies.FlairmerBoss2;
import net.truttle1.time.overworld.npc.Box;
import net.truttle1.time.overworld.npc.Carne1;
import net.truttle1.time.overworld.npc.CavemanNPC;
import net.truttle1.time.overworld.npc.LomoJuiceBar;
import net.truttle1.time.overworld.npc.Rage0;
import net.truttle1.time.overworld.npc.Rage1;
import net.truttle1.time.overworld.npc.SaveSign;
import net.truttle1.time.overworld.npc.Sign;
import net.truttle1.time.overworld.npc.carl.Carl2;
import net.truttle1.time.overworld.npc.carl.Carl4;
import net.truttle1.time.overworld.npc.creaturey.Creaturey2;
import net.truttle1.time.overworld.npc.creaturey.Creaturey3;
import net.truttle1.time.overworld.npc.creaturey.Creaturey5;
import net.truttle1.time.overworld.npc.cutscene.CagedSimonCutscene;
import net.truttle1.time.overworld.npc.cutscene.CavemanCageDecoration;
import net.truttle1.time.overworld.npc.cutscene.IggyPreBattleCutscene;
import net.truttle1.time.overworld.npc.cutscene.Prop;
import net.truttle1.time.overworld.npc.cutscene.RageCarneCutscene;
import net.truttle1.time.overworld.npc.cutscene.WilliamSkrappsCutscene;
import net.truttle1.time.overworld.npc.skrapps.SkrappsNPC1;
//NEXT BOX: 23
public class StoneAgeWarps {

	public static void warps(Room room)
	{
		if(room.id == RoomId.SimonHouse)
		{
			
			room.setupWarp(1500, 500, OverworldMode.stoneAge1, 700, 1300);
			//room.addObject(new SaveSign(room.om.window, 200, 600, room.om));
			
		}
		if(room.id == RoomId.StoneAge1)
		{
			room.addObject(new Box(room.om.window, 100, 700, room.om,false,1,10));
			room.setupWarp(500, 1300, OverworldMode.simonHouse, 1300, 500);
			room.setupWarp(12700, 1300, OverworldMode.stoneAge2, 200, 1300);
			room.addObject(new SaveSign(room.om.window, 7200, 1400, room.om));
			
		}
		if(room.id == RoomId.StoneAge2)
		{
			room.addObject(new Sign(room.om.window, 12200, 1400, room.om,"I mean it! DO NOT CONTINUE ON THIS PATH!/TURN BACK NOW!/...please?"));
			room.addObject(new Sign(room.om.window, 4600, 1400, room.om,"Wow you're persistant./But that doesn't mean you're smart...There are good reasons/why you shouldn't go to the Purple Apple Tree./Reasons you aren't able to understand!"));
			room.addObject(new Sign(room.om.window, 1100, 1400, room.om,"Don't go to the Purple Apple tree! There's.../danger there...seriously, don't go there.../it's a bad idea."));
			room.addObject(new SaveSign(room.om.window, 5500, 1200, room.om));
			room.setupWarp(0, 1300, OverworldMode.stoneAge1, 12500, 1300);
			room.setupWarp(12700, 1300, OverworldMode.stoneAge3, 200, 1300);
			room.setupWarp(12700, 200, OverworldMode.stoneAge4, 200, 1300);
			
		}
		if(room.id == RoomId.StoneAge3)
		{
			Game.currentWorld = WorldId.StoneAge;
			room.addObject(new SaveSign(room.om.window, 700, 1400, room.om));
			
			room.addObject(new Tree(room.om.window, 2500, 500, room.om));
			room.setupWarp(0, 1300, OverworldMode.stoneAge2, 12200, 1300);
			if(Quest.quests[Quest.LOMO]>=Global.LOMOCONSTANT)
			{
				if(Quest.quests[Quest.TIMEMACHINE] == 0)
				{
					room.addObject(new Creaturey5(room.om.window, 2500, 595, room.om));
				}
				room.addObject(new TimeMachine(room.om.window, 2700, 610, room.om));
			}
			
		}
		if(room.id == RoomId.StoneAge4)
		{
			room.addObject(new Sign(room.om.window, 7700, 1200, room.om,"LOMO VILLAGE: Upwards, then Leftwards/MT. PYRUZ VOLCANO ENTERANCE: Rightwards"));
			Game.currentWorld = WorldId.StoneAge;
			room.setupWarp(0, 1300, OverworldMode.stoneAge2, 12200, 400);
			room.setupWarp(0, 400, OverworldMode.stoneAge5, 6100, 1200);
			room.setupWarp(25500, 500, OverworldMode.pyruzOutside, 200, 1300);
			room.addObject(new Carl4(room.om.window, 14600, 800, room.om));
			room.addObject(new FlairmerBoss2( 25100, 500,room.om.window, room.om,-1));
			room.addObject(new SaveSign(room.om.window, 8400, 1300, room.om));
			room.addObject(new SaveSign(room.om.window, 24400, 600, room.om));
			
			
		}
		if(room.id == RoomId.StoneAge5)
		{
			room.setupWarp(6300, 1200, OverworldMode.stoneAge4, 200, 400);
			room.setupWarp(0, 1100, OverworldMode.lomo1, 4800, 1300, true, AudioHandler.lomoMusic);
			room.addObject(new Sign(room.om.window, 800, 1400, room.om,"Lomo Village CITY LIMIT!/Population: Mostly all of the Humans in this general area...except for/some WEIRDOS that decided to live at the BOTTOM of Mt. Pyruz rather/than where everyone else was living near the TOP of the mountain!"));
			room.addObject(new SaveSign(room.om.window, 2600, 1100, room.om));	
		}


		if(room.id == RoomId.Pyruz1)
		{
			/*room.addObject(new SaveSign(room.om.window, 1100, 12300, room.om));
			room.addObject(new SaveSign(room.om.window, 6100, 10800, room.om));
			room.addObject(new SaveSign(room.om.window, 1400, 9300, room.om));*/
			room.addObject(new Box(room.om.window, 12100, 11800, room.om,true,0,0));
			room.addObject(new Box(room.om.window, 9800, 11900, room.om,false,1,1));
			
			room.addObject(new Box(room.om.window, 2200, 9800, room.om,false,4,2));

			room.addObject(new Box(room.om.window, 4900, 7600, room.om,false,1,3));
			room.addObject(new Box(room.om.window, 5000, 7600, room.om,false,5,4));
			room.addObject(new Box(room.om.window, 5100, 7600, room.om,false,4,5));
			room.addObject(new Box(room.om.window, 5200, 7600, room.om,false,0,6));
			room.addObject(new Box(room.om.window, 5300, 7600, room.om,false,1,7));
			room.addObject(new Box(room.om.window, 5400, 7600, room.om,false,4,8));
			room.addObject(new Box(room.om.window, 5500, 7600, room.om,false,4,9));

			room.addObject(new Box(room.om.window, 9900, 6800, room.om,false,5,11));
			room.addObject(new Box(room.om.window, 12300, 9900, room.om,false,5,12));
			room.addObject(new Box(room.om.window, 12500, 5200, room.om,true,0,13));
			room.addObject(new Box(room.om.window, 3700, 4500, room.om,true,1,14));
			room.addObject(new Box(room.om.window, 12500, 8300, room.om,true,0,15));

			room.addObject(new Box(room.om.window, 400, 3400, room.om,false,1,16));
			room.addObject(new Box(room.om.window, 300, 3500, room.om,false,1,17));

			room.addObject(new Box(room.om.window, 10100, 2300, room.om,false,4,19));
			room.addObject(new Box(room.om.window, 9700, 2500, room.om,false,4,20));
			//room.addObject(new Box(room.om.window, 9300, 2800, room.om,false,4,21));
			//room.addObject(new Box(room.om.window, 11800, 2000, room.om,false,4,22));
			
			room.addObject(new Sign(room.om.window, 1500, 7700, room.om,"Trogdor II Memorial Treasure Hoard this way --->!/To commemorate the life and accomplishments of King Trogdor II/of the Flairmer Kingdom, this Treasure Hoard was established!/Yeah, good luck actually obtaining it, loser! BWARR HARR HARR HARRR!"));
			room.addObject(new Sign(room.om.window, 4700, 7700, room.om,"Uhh, wow, you actually made it to my Treasure Hoard.../THAT MUST MEAN YOU HAVE NO LIFE, LOSER! BWARR HARR HARR HARRR!/And yes, I made a memorial to myself while I was alive./It's perfecly okay, I'm the King! BOW DOWN TO ME! --Trogdor II"));
			room.addObject(new Sign(room.om.window, 8800, 7300, room.om,"Pyruz Lava Chute :: Lava flows through here!"));
			room.addObject(new Sign(room.om.window, 11300, 2000, room.om,"King Ignacio's Throne Room and Prison this way/-------->"));
			room.addObject(new Sign(room.om.window, 9400, 2700, room.om,"Hey Mr. Traveler! You get a free treasure if you jump down this hole!/-Igna-I MEAN...CARNE!!! Yeah, this is Carne writing this."));
			if(Quest.quests[Quest.PYRUZ_S]<=1)
			{
				room.addObject(new Rage0(room.om.window,12000,3000,room.om));
			}
			else if(Quest.quests[Quest.PYRUZ_S] == 2)
			{
				room.addObject(new BoulderThin(119,29,room.om.window,room.om));
				room.addObject(new BoulderThin(114,29,room.om.window,room.om));
				room.addObject(new WilliamSkrappsCutscene(room.om.window,11550,3150,room.om));
			}
			else
			{
				room.addObject(new Rage1(room.om.window, 12000, 3000, room.om));
			}

			
			room.addObject(new Door(room.om.window, 700, 10200, 900, 9100,room.om,true,0));
			room.addObject(new Door(room.om.window, 1200, 6700, 4000, 5400,room.om,true,1));
			room.addObject(new Door(room.om.window, 5900, 8900, 10900, 3000,room.om,true,2));
			
			room.setupWarp(0, 12400, OverworldMode.pyruzOutside, 1800, 1300);
			room.setupWarp(12700, 3100, OverworldMode.pyruz2, 200, 4400);
			room.setupWarp(12700, 1500, OverworldMode.pyruz3, 300, 2000);
		}
		if(room.id == RoomId.Pyruz3)
		{
			room.addObject(new CavemanCageDecoration(room.om.window,900,1980,room.om,OverworldAnimation.bCavemanIdleSad));
			room.addObject(new CavemanCageDecoration(room.om.window,500,1980,room.om,OverworldAnimation.rCavemanIdleSad));
			room.addObject(new CavemanCageDecoration(room.om.window,2400,1480,room.om,OverworldAnimation.rCavemanIdleSad));
			room.addObject(new CavemanCageDecoration(room.om.window,1400,1980,room.om,OverworldAnimation.bGirlCavemanIdleSad));
			room.addObject(new CavemanCageDecoration(room.om.window,2700,1480,room.om,OverworldAnimation.bCavemanIdleSad));
			room.addObject(new CavemanCageDecoration(room.om.window,2950,1480,room.om,OverworldAnimation.bGirlCavemanIdleSad));
			room.addObject(new CavemanCageDecoration(room.om.window,200,1980,room.om,OverworldAnimation.rCavemanIdleSad));
			room.addObject(new CavemanCageDecoration(room.om.window,1200,1980,room.om,OverworldAnimation.bGirlCavemanIdleSad));
			//room.addObject(new CavemanCageDecoration(room.om.window,3300,1500,room.om,OverworldAnimation.rCavemanIdleSad));
			
			room.setupWarp(0, 2000, OverworldMode.pyruz1, 12400, 1500);
			room.addObject(new Prop(room.om.window,3800,1300,room.om,OverworldAnimation.throne));
			if(Quest.quests[Quest.LOMO] < Global.LOMOCONSTANT)
			{
				room.addObject(new IggyPreBattleCutscene(room.om.window,3800,1300,room.om));
			}
			else
			{
				room.music = AudioHandler.mountainLoop;
			}
		}
		if(room.id == RoomId.PyruzOutside)
		{
			Game.currentWorld = WorldId.Pyruz;
			room.addObject(new Sign(room.om.window, 1500, 1400, room.om,"MT PYRUZ - INACTIVE VOLCANO/Erupts about every 6000 years./Last recorded eruption: 6000 years ago.../Why do I feel so nervous while writing this sign???"));
			room.addObject(new SkrappsNPC1(room.om.window,1100,1200,room.om));
			room.addObject(new Carne1(room.om.window,1100,1200,room.om));
			room.setupWarp(0, 1300, OverworldMode.stoneAge4, 25300, 500);
			room.setupWarp(2000, 1300, OverworldMode.pyruz1, 200, 12400);
			room.addObject(new Box(room.om.window, 1100, 400, room.om,true,3,18));
			
		}
		//LOCATION Lomo Village
		if(room.id == RoomId.Lomo1)
		{
			Game.currentWorld = WorldId.StoneAge;
			final int DOORHEIGHT = 1200;
			room.addCaveDoor(1300, DOORHEIGHT, OverworldMode.lomoBldg0, 100, 400, false, AudioHandler.lomoMusic);
			//room.addCaveDoor(1700, DOORHEIGHT, null, 0, 0, false, null);
			room.addCaveDoor(2100, DOORHEIGHT, OverworldMode.lomoBldg1, 100, 400, false, AudioHandler.lomoMusic);
			//room.addCaveDoor(2500, DOORHEIGHT, null, 0, 0, false, null);
			room.addCaveDoor(2900, DOORHEIGHT, OverworldMode.lomoBldg2, 100, 400, false, AudioHandler.lomoMusic);
			//room.addCaveDoor(3700, DOORHEIGHT, null, 0, 0, false, null);
			room.addCaveDoor(4100, DOORHEIGHT, OverworldMode.lomoBldg3, 100, 400, false, AudioHandler.lomoMusic);
			room.setupWarp(5100, 1300, OverworldMode.stoneAge5, 100, 1100, true, AudioHandler.prehistoricMusic);
			room.setupWarp(0, 1000, OverworldMode.lomo2, 1200, 2900, true, AudioHandler.lomoMusic);
			room.addObject(new Sign(room.om.window, 4800, 1400, room.om,"Welcome to Lomo Village, the village where people meet./.../We are known for selling Tenderlomos, which are a type/of meat product...that's all we're known for..."));
			room.addObject(new Creaturey2(room.om.window, 1400, 1200, room.om));
			room.addObject(new Carl2(room.om.window, 1200, 1300, room.om));
			room.addObject(new LomoJuiceBar(room.om.window,3250,1090,room.om));
			room.addObject(new Prop(room.om.window,2700,1300,room.om,OverworldAnimation.bankSign));
			
			if(Quest.quests[Quest.LOMO] >= Global.LOMOCONSTANT)
			{
				
				room.addObject(new CavemanNPC(room.om.window,3900,1290,room.om,0,"Hey there! Man, it sure is more peaceful here now that Ignacio's gone!/I'm finally able to get started on my new invention that I'm designing./I think I'm going to call it \"The Wheel!\"",false));
				room.addObject(new CavemanNPC(room.om.window,1780,1290,room.om,1,"It sure is a good thing that the volcano didn't end up burying Lomo/Village. Well, at least Lomo Village has another 6000 years to stick/around since the volcano will not be erupting for a very long time!",false));
				room.addObject(new RageCarneCutscene(room.om.window, 2500, 1225, room.om));
			}
			
			
		}

		if(room.id == RoomId.Lomo2)
		{
			room.setupWarp(1400, 2900, OverworldMode.lomo1, 100, 1000, true, AudioHandler.lomoMusic);
			room.setupWarp(1400, 300, OverworldMode.lomo3, 200, 800, true, AudioHandler.lomoMusic);
			room.addObject(new SaveSign(room.om.window, 1100, 3000, room.om));
			
			room.addObject(new Creaturey3(room.om.window, 1100, 200, room.om));
			
			room.addObject(new Sign(room.om.window, 600, 2600, room.om,"Welcome to the Lomo Village Volcano Shelter Rock Formation!/Village this way (even though you came from there)./------------->"));
			
		}
		if(room.id == RoomId.Lomo3)
		{
			room.setupWarp(0, 800, OverworldMode.lomo2, 1100, 300, true, AudioHandler.lomoMusic);
		}
		
		if(room.id == RoomId.PyruzPrison)
		{
			room.setupWarp(0, 1200, OverworldMode.pyruz2, 4500, 800);
			room.addObject(new CagedSimonCutscene(room.om.window,2500,1200,room.om));
			room.addObject(new CavemanCageDecoration(room.om.window,500,1200,room.om,OverworldAnimation.bCavemanIdleSad));
			room.addObject(new CavemanCageDecoration(room.om.window,800,1200,room.om,OverworldAnimation.rCavemanIdleSad));
			room.addObject(new CavemanCageDecoration(room.om.window,1100,1200,room.om,OverworldAnimation.rCavemanIdleSad));
			room.addObject(new CavemanCageDecoration(room.om.window,1500,1200,room.om,OverworldAnimation.bGirlCavemanIdleSad));
			room.addObject(new CavemanCageDecoration(room.om.window,1800,1200,room.om,OverworldAnimation.bCavemanIdleSad));
			//room.addObject(new CavemanCageDecoration(room.om.window,2100,1200,room.om,OverworldAnimation.bGirlCavemanIdleSad));
		}

		if(room.id == RoomId.Pyruz2)
		{
			room.setupWarp(4700, 800, OverworldMode.pyruzPrison, 200, 1200, true, AudioHandler.mountainLoop);
			room.setupWarp(0, 4400, OverworldMode.pyruz1, 12400, 3100, true, AudioHandler.mountainLoop);
		}

		if(room.id == RoomId.LomoBldg0)
		{
			room.addObject(new CavemanNPC(room.om.window,600,400,room.om,3,"Why do you think it's okay to just barge into my house? I mean, I know/I leave my door unlocked, but that doesn't mean that some random/person is allowed to just wander in willy-nilly! So get out!",false));
			room.setupWarp(0, 400, OverworldMode.lomo1, 1300, 1300);
		}
		if(room.id == RoomId.LomoBldg1)
		{
			room.addObject(new CavemanNPC(room.om.window,600,400,room.om,2,"Hey there, have you ever tried Saft's food yet? He sells the most amazing/Tenderlomos ever! Also, they're at the cheapest price you will ever find/them. If you find imported Tenderlomos at another store, they will/definitely cost more than they do here!",false));
			room.setupWarp(0, 400, OverworldMode.lomo1, 2100, 1300);
		}
		if(room.id == RoomId.LomoBldg2)
		{
			System.out.println(room.id);
			room.addObject(new Stone(7,5,room.om.window,room.om));
			room.addObject(new Stone(7,3,room.om.window,room.om));
			room.addObject(new Stone(7,2,room.om.window,room.om));
			room.addObject(new Stone(7,1,room.om.window,room.om));
			room.addObject(new CavemanNPC(room.om.window,720,380,room.om,0,"Welcome to Lomo Bank! Due to the recent Ignacio disaster, the bank will/be temporarily disabled while we get everything sorted out. I'm sorry/for the inconvenience.",250));
			room.addObject(new CavemanNPC(room.om.window,320,390,room.om,1,"Welcome to the Lomo Bank! This is a bank, and it is in Lomo Village,/so it is Lomo Bank! Isn't that just a swell name for a place like this?",150));
			room.setupWarp(0, 400, OverworldMode.lomo1, 2900, 1300);
		}
		if(room.id == RoomId.LomoBldg3)
		{
			room.addObject(new CavemanNPC(room.om.window,700,400,room.om,3,"My husband is trying to build a new invention. In my opinion, though,/nobody is ever going to use it, and it will not catch on. The way that I/see it, everything of use that can be invented has been invented!",false));
			room.setupWarp(0, 400, OverworldMode.lomo1, 4100, 1300);
		}
	}
}
