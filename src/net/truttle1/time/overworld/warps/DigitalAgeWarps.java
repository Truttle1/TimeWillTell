package net.truttle1.time.overworld.warps;

import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.overworld.Billboard;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.Room;
import net.truttle1.time.overworld.RoomId;
import net.truttle1.time.overworld.TimeMachine;
import net.truttle1.time.overworld.npc.BurgerHouseRegister;
import net.truttle1.time.overworld.npc.CityNPC;
import net.truttle1.time.overworld.npc.Sign;
import net.truttle1.time.overworld.npc.carl.ModernCarl0;
import net.truttle1.time.overworld.npc.carl.ModernCarl1;
import net.truttle1.time.overworld.npc.creaturey.ModernCreaturey0;
import net.truttle1.time.overworld.npc.cutscene.Prop;

public class DigitalAgeWarps {


	public static void warps(Room room)
	{
		if(room.id == RoomId.Modern0)
		{
			room.addObject(new CityNPC(room.om.window,2500,800,room.om,0,"Hello there! Welcome to Aqua City! Are you here for Stone-Con?/It's the biggest Stone-Age themed convention this side of Flare/Town! I'm not personally interested in types of conventions/like that myself, but I can see the appeal...a little bit.","Uhh, hello young...man? Am I seeing things, because it looks like there/is a living, breathing dinosaur right in front of me.../.../Nah, I'm definitely seeing things! I think I need to go to a psychiatrist!"));
			room.setupWarp(2900, 800, OverworldMode.modern1, 200, 4500);
			room.addObject(new TimeMachine(room.om.window, 200, 720, room.om));
			room.addObject(new Sign(room.om.window, 1400, 900, room.om,"Welcome to Aqua City: The most amazing city in the world!/Population: A lot!"));
		}
		if(room.id == RoomId.Modern1)
		{
			Prop bhSign = new Prop(room.om.window,2100,4200,room.om,OverworldAnimation.burgerHouseSign);
			bhSign.id = ObjectId.Decoration;
			room.addObject(bhSign);
			room.setupWarp(6300, 2400, OverworldMode.modern3, 200, 2100);
			
			room.setupWarp(0, 4500, OverworldMode.modern0, 2700, 800);
			room.setupWarp(6300, 4500, OverworldMode.modern2, 200, 900);
			room.addObject(new Sign(room.om.window, 1400, 4600, room.om,"LIFE TIP: If you are a different person, people say diffent things to you!/Also, this city is the best because we have life tips written on signs!"));
			room.addObject(new Sign(room.om.window, 3600, 4300, room.om,"UP, LEFT: I-31 - Leads to Flare University./RIGHT: I-18 - Leads to Commercial Square and Aqua City Park./CONVEX INC: Shortly past Aqua City Park."));
			room.addModernDoor(2050, 4400, OverworldMode.burgerHouse0, 100, 400, false, AudioHandler.lomoMusic);
			room.addObject(new ModernCarl0(room.om.window,1100,4500,room.om));
			room.addObject(new CityNPC(room.om.window,1800,4500,room.om,1,"Hello Mr. Tourist! Are you looking for Stone-Con? If you are, it's/located in the Commercial District, but you should note that the/venue has a strict no shoes, no shirt, no service policy.","Hello there young man! Gosh, you look adorable! Where'd you get the/dinosaur costume? I mean, it looks super realistic, almost like a real life/dinosaur! Can I get a picture with you? Actually, nevermind,/I'm camera shy..."));
			room.addObject(new CityNPC(room.om.window,2500,4500,room.om,3,"The Burger House chain of fast food restaurants has grown to become/Aqua's most popular place to eat. What started out as just a small/hamburger stand in the Aqua City Park now covers pretty much/every part of the city! Their food's pretty tasty too."));
		}
		if(room.id == RoomId.Modern2)
		{
			room.setupWarp(0, 900, OverworldMode.modern1, 6100, 4500);
			room.setupWarp(7100, 900, OverworldMode.park, 200, 900);
			
			room.addObject(new CityNPC(room.om.window,1700,900,room.om,1,"Supposedly, Convex Inc. is releasing those monsters \"accidentally\"./Somehow, I don't believe them."));
			room.addObject(new CityNPC(room.om.window,2400,900,room.om,0,"Convex Inc. was founded with the sole purpose of creating monsters to/protect Aqua City. The monsters have become hostile, though./Convex says that they are \"working on capturing them\", but I highly/doubt that! Convex has some evil motives, I just know it!"));
			room.addObject(new CityNPC(room.om.window,2700,900,room.om,5,"Ahh, Lemon Co. Ltd., the one company that can remove features from/their products year after year, and people still buy their stuff.../I wonder how many people actually like their products, and how many/people buy their stuff just to look \"cool\"..."));
			
			Prop bhSign = new Prop(room.om.window,1000,620,room.om,OverworldAnimation.burgerHouseSign);
			bhSign.id = ObjectId.Decoration;
			room.addObject(bhSign);
			room.addModernDoor(950, 800, OverworldMode.burgerHouse1, 100, 400, false, AudioHandler.lomoMusic);
			
			room.addObject(new Billboard(room.om.window,3000,620,1));
			room.addObject(new Billboard(room.om.window,3500,620,2));
			room.addObject(new Billboard(room.om.window,5500,620,3));
			room.addObject(new Billboard(room.om.window,6200,620,0));
			room.addObject(new Billboard(room.om.window,1700,620,4));
			room.addObject(new Sign(room.om.window, 700, 1000, room.om,"::AQUA COMMERCIAL DISTRICT STORE LIST::/Burger House::Lemon Store::/Merlon's Magic Lessons::Weapons Depot::/Aqua Convention Center::Henri's Cookery"));
			
		}
		if(room.id == RoomId.Modern3)
		{
			Prop bhSign = new Prop(room.om.window,1400,1820,room.om,OverworldAnimation.burgerHouseSign);
			bhSign.id = ObjectId.Decoration;
			room.addObject(bhSign);
			room.setupWarp(0, 2100, OverworldMode.modern1, 6100, 2400);
			room.addModernDoor(1350, 2000, OverworldMode.burgerHouse2, 100, 400, false, AudioHandler.lomoMusic);
		}

		if(room.id == RoomId.Park)
		{
			room.setupWarp(0, 900, OverworldMode.modern2, 6900, 900);
			room.addObject(new Sign(room.om.window, 800, 1000, room.om,"Aqua City Park!/We have trees here since not having green space in cities is \"bad.\"//WEST: Convex Inc."));
			room.addObject(new ModernCarl1(room.om.window, 1700, 900, room.om));
			room.addObject(new CityNPC(room.om.window,2900,900,room.om,4,"Did you know that, about 5000 years ago, these apple trees/used to grow PURPLE apples?/The wonders of genetic engineering made the apples a much more/appealing red! Science is amazing!"));
		}
		if(room.id == RoomId.Burger0)
		{
			burgerHouse(room,OverworldMode.modern1,2100,4400);
		}
		if(room.id == RoomId.Burger1)
		{
			burgerHouse(room,OverworldMode.modern2,950,800);
		}
		if(room.id == RoomId.Burger2)
		{
			burgerHouse(room,OverworldMode.modern3,1350,2000);
			room.addObject(new ModernCreaturey0(room.om.window, 520, 300, room.om));
		}
	}
	public static void burgerHouse(Room room, Room comingFrom, int doorX, int doorY)
	{
		System.out.println("BURGERRRRRRRRR!!!!");
		room.setupWarp(0, 400, comingFrom, doorX, doorY+100);
		room.addObject(new BurgerHouseRegister(room.om.window, 800, 390, room.om));
	}
}
