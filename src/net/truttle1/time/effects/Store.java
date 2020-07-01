package net.truttle1.time.effects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.SimonBattler;
import net.truttle1.time.battle.SkrappsBattler;
import net.truttle1.time.battle.WilliamBattler;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ImageLoader;

public class Store {

	public static int[] storeItem = new int[999];
	public static int[] storePrice = new int[999];
	public static String[] itemDescriptions = new String[999];
	public static String[] itemNames = new String[999];
	public static String[] keyItemNames = new String[999];
	public static int[] itemType = new int[999];
	public static BufferedImage[] itemImage = new BufferedImage[999];
	public static BufferedImage[] keyItemImage = new BufferedImage[999];

	public static int numOfItems;
	//Items
	public static final int APPLE = 0;
	public static final int APPLEJUICE = 1;
	public static final int ORANGEJUICE = 2;
	public static final int POISONJUICE = 3;
	public static final int TENDERLOMO = 4;
	public static final int SPSTICK = 5;
	public static final int BURGER = 6;
	public static final int FRIES = 7;
	public static final int SODA = 8;
	public static final int NUGGETS = 9;
	
	//Key Items
	public static final int KEY = 0;
	public static final int GPADLOCK = 1;
	public static final int PPADLOCK = 2;
	public static final int OPADLOCK = 3;
	
	
	public static boolean running = false;
	public static int selection = 0;
	public static int selectionOffset = 0;
	public static boolean noMoneyMessage = false;
	public static ImageLoader loader;
	public static String itemUser;
	public static void initStore(int numOfItems)
	{
		AudioHandler.playMusic(AudioHandler.cutscene2);
		setNames();
		for(int i=0; i<999; i++)
		{
			storeItem[i] = 0;
			storePrice[i] = 0;
		}
		Store.numOfItems = numOfItems;
		Store.running = true;
	}
	public static void setItem(int itemNum, int itemType, int itemPrice)
	{
		storeItem[itemNum] = itemType;
		storePrice[itemNum] = itemPrice;
	}
	public static void runStore()
	{
		if(Store.running)
		{
			Global.disableMovement = true;
			if(Global.xPressed)
			{
				Store.running = false;
				Global.talking++;
			}
			if(Global.zPressed)
			{
				if(Global.money<storePrice[selection])
				{
					noMoneyMessage = true;
				}
				else
				{
					Global.money -= storePrice[selection];
					Global.items[storeItem[selection]]++;
				}
			}
			if(Global.upPressed)
			{
				noMoneyMessage = false;
				if(selection == 0)
				{
					selection = numOfItems;
				}
				else
				{
					selection--;
				}
			}
			if(Global.downPressed)
			{
				noMoneyMessage = false;
				if(selection == numOfItems)
				{
					selection = 0;
				}
				else
				{
					selection++;
				}
			}
		}
	}
	public static void itemUse(GameObject user, int item)
	{
		itemUse(user,item,true,"");
	}
	public static void itemUse(GameObject user, int item, boolean displaying, String noUserString)
	{
		if(itemType[item] == 0)
		{
			int heal = 0;
			int sp = 0;
			if(item == TENDERLOMO)
			{
				heal = 8;
				sp = 6;
			}
			if(user instanceof WilliamBattler || noUserString.contains("William"))
			{
				if(displaying)
				{
					WilliamBattler w = (WilliamBattler) user;
					EyeCandy atk = new EyeCandy(w.bm.window, w.x+32, w.y-64, BattleAnimation.heart, w.bm,true,heal,true,1,0,0);
					EyeCandy atk2 = new EyeCandy(w.bm.window, w.x+84, w.y-64, BattleAnimation.star, w.bm,true,sp,true,0,1,0);
					atk.setRepeating(true);
					w.bm.eyeCandy.add(atk);
					atk2.setRepeating(true);
					w.bm.eyeCandy.add(atk2);
				}
				Global.williamHP += heal;
				Global.williamSP += sp;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("William used the " + Store.itemNames[item] + "/He healed " + heal + "HP and " + sp + " SP!");
				}
			}
			if(user instanceof SimonBattler || noUserString.contains("Simon"))
			{
				if(displaying)
				{
					SimonBattler w = (SimonBattler) user;
					EyeCandy atk = new EyeCandy(w.bm.window, w.x+32, w.y-64, BattleAnimation.heart, w.bm,true,heal,true,1,0,0);
					EyeCandy atk2 = new EyeCandy(w.bm.window, w.x+84, w.y-64, BattleAnimation.star, w.bm,true,sp,true,0,1,0);
					atk.setRepeating(true);
					w.bm.eyeCandy.add(atk);
					atk2.setRepeating(true);
					w.bm.eyeCandy.add(atk2);
					
				}
				Global.simonHP += heal;
				Global.simonSP += sp;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("Simon used the " + Store.itemNames[item] + "!/He healed " + heal + "HP and " + sp + " SP!");
				}
			}
			if(user instanceof SkrappsBattler || noUserString.contains("Skrapps"))
			{
				heal *= 1.75;
				sp *= 1.75;
				if(displaying)
				{
					SkrappsBattler w = (SkrappsBattler) user;
					EyeCandy atk = new EyeCandy(w.bm.window, w.x+32, w.y-64, BattleAnimation.heart, w.bm,true,heal,true,1,0,0);
					EyeCandy atk2 = new EyeCandy(w.bm.window, w.x+84, w.y-64, BattleAnimation.star, w.bm,true,sp,true,0,1,0);
					atk.setRepeating(true);
					w.bm.eyeCandy.add(atk);
					atk2.setRepeating(true);
					w.bm.eyeCandy.add(atk2);
				}
				Global.partnerHP[Global.SKRAPPS] += heal;
				Global.partnerSP[Global.SKRAPPS] += sp;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("Skrapps used the " + Store.itemNames[item] + "!/He healed " + heal + "HP and " + sp + "SP!/CARNIVORE POWER!");
				}
			}
			
		}
		if(itemType[item] == 1)
		{
			int heal = 0;
			if(item == 0)
			{
				heal = 4;
			}
			if(item == 1)
			{
				heal = 8;
			}
			if(user instanceof WilliamBattler || noUserString.contains("William"))
			{
				if(displaying)
				{
					WilliamBattler w = (WilliamBattler) user;
					EyeCandy atk = new EyeCandy(w.bm.window, w.x+32, w.y-64, BattleAnimation.heart, w.bm,true,heal,true,1,0,0);
					atk.setRepeating(true);
					w.bm.eyeCandy.add(atk);
				}
				Global.williamHP += heal;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("William used the " + Store.itemNames[item] + "!/He healed " + heal + "HP!");
				}
				
			}
			if(user instanceof SimonBattler || noUserString.contains("Simon"))
			{
				if(displaying)
				{
					SimonBattler w = (SimonBattler) user;
					EyeCandy atk = new EyeCandy(w.bm.window, w.x+32, w.y-64, BattleAnimation.heart, w.bm,true,heal,true,1,0,0);
					atk.setRepeating(true);
					w.bm.eyeCandy.add(atk);
				}
				Global.simonHP += heal;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("Simon used the " + Store.itemNames[item] + "!/He healed " + heal + "HP!");
				}
			}
			if(user instanceof SkrappsBattler || noUserString.contains("Skrapps"))
			{
				if(displaying)
				{
					SkrappsBattler w = (SkrappsBattler) user;
					EyeCandy atk = new EyeCandy(w.bm.window, w.x+32, w.y-64, BattleAnimation.heart, w.bm,true,heal/2,true,0.75f,0.67f,0);
					atk.setRepeating(true);
					w.bm.eyeCandy.add(atk);
				}
				Global.partnerHP[Global.SKRAPPS] += heal/2;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("Skrapps used the " + Store.itemNames[item] + "!/He healed " + heal/2 + "HP!/Isn't Skrapps a carnivore, though?");
				}
			}
		}
		if(itemType[item] == 2)
		{
			int sp = 0;
			if(item == ORANGEJUICE)
			{
				sp = 4;
			}
			if(item == FRIES)
			{
				sp = 8;
			}
			if(user instanceof WilliamBattler || noUserString.contains("William"))
			{
				if(displaying)
				{
					WilliamBattler w = (WilliamBattler) user;
					EyeCandy atk2 = new EyeCandy(w.bm.window, w.x+84, w.y-64, BattleAnimation.star, w.bm,true,sp,true,0,1,0);
					atk2.setRepeating(true);
					w.bm.eyeCandy.add(atk2);
				}
				Global.williamSP += sp;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("William used the " + Store.itemNames[item] + "/He healed " + sp + " SP!");
				}
			}
			if(user instanceof SimonBattler || noUserString.contains("Simon"))
			{
				if(displaying)
				{
					SimonBattler w = (SimonBattler) user;
					EyeCandy atk2 = new EyeCandy(w.bm.window, w.x+84, w.y-64, BattleAnimation.star, w.bm,true,sp,true,0,1,0);
					atk2.setRepeating(true);
					w.bm.eyeCandy.add(atk2);
				}
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("Simon used the " + Store.itemNames[item] + "!/He healed " + sp + " SP!");
				}
				Global.simonSP += sp;
			}
			if(user instanceof SkrappsBattler || noUserString.contains("Skrapps"))
			{
				sp *= .5;
				if(displaying)
				{
					SkrappsBattler w = (SkrappsBattler) user;
					EyeCandy atk2 = new EyeCandy(w.bm.window, w.x+84, w.y-64, BattleAnimation.star, w.bm,true,sp,true,0.5f,1,0);
					atk2.setRepeating(true);
					w.bm.eyeCandy.add(atk2);
				}
				Global.partnerSP[Global.SKRAPPS] += sp;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("Skrapps used the " + Store.itemNames[item] + "!/He healed " + sp + "SP.../Plant products don't work very well on Skrapps...");
				}
			}
			
		}
		
		if(itemType[item] == 4)
		{
			int sp = 0;
			if(item == SPSTICK)
			{
				sp = 5;
			}
			if(item == NUGGETS)
			{
				sp = 6;
			}
			if(user instanceof WilliamBattler || noUserString.contains("William"))
			{
				if(displaying)
				{
					WilliamBattler w = (WilliamBattler) user;
					EyeCandy atk2 = new EyeCandy(w.bm.window, w.x+84, w.y-64, BattleAnimation.star, w.bm,true,sp,true,0,1,0);
					atk2.setRepeating(true);
					w.bm.eyeCandy.add(atk2);
				}
				Global.williamSP += sp;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("William used the " + Store.itemNames[item] + "/He healed " + sp + " SP!");
				}
			}
			if(user instanceof SimonBattler || noUserString.contains("Simon"))
			{
				if(displaying)
				{
					SimonBattler w = (SimonBattler) user;
					EyeCandy atk2 = new EyeCandy(w.bm.window, w.x+84, w.y-64, BattleAnimation.star, w.bm,true,sp,true,0,1,0);
					atk2.setRepeating(true);
					w.bm.eyeCandy.add(atk2);
				}
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("Simon used the " + Store.itemNames[item] + "!/He healed " + sp + " SP!");
				}
				Global.simonSP += sp;
			}
			if(user instanceof SkrappsBattler || noUserString.contains("Skrapps"))
			{
				sp *= 2;
				if(displaying)
				{
					SkrappsBattler w = (SkrappsBattler) user;
					EyeCandy atk2 = new EyeCandy(w.bm.window, w.x+84, w.y-64, BattleAnimation.star, w.bm,true,sp,true,0.5f,1,0);
					atk2.setRepeating(true);
					w.bm.eyeCandy.add(atk2);
				}
				Global.partnerSP[Global.SKRAPPS] += sp;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("Skrapps used the " + Store.itemNames[item] + "!/He healed " + sp + "SP.../It's carnivore time!");
				}
			}
			
		}

		if(itemType[item] == 5)
		{
			int heal = 0;
			if(item == BURGER)
			{
				heal = 10;
			}
			if(user instanceof WilliamBattler || noUserString.contains("William"))
			{
				if(displaying)
				{
					WilliamBattler w = (WilliamBattler) user;
					EyeCandy atk = new EyeCandy(w.bm.window, w.x+32, w.y-64, BattleAnimation.heart, w.bm,true,heal,true,1,0,0);
					atk.setRepeating(true);
					w.bm.eyeCandy.add(atk);
				}
				Global.williamHP += heal;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("William used the " + Store.itemNames[item] + "!/He healed " + heal + "HP!");
				}
				
			}
			if(user instanceof SimonBattler || noUserString.contains("Simon"))
			{
				if(displaying)
				{
					SimonBattler w = (SimonBattler) user;
					EyeCandy atk = new EyeCandy(w.bm.window, w.x+32, w.y-64, BattleAnimation.heart, w.bm,true,heal,true,1,0,0);
					atk.setRepeating(true);
					w.bm.eyeCandy.add(atk);
				}
				Global.simonHP += heal;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("Simon used the " + Store.itemNames[item] + "!/He healed " + heal + "HP!");
				}
			}
			if(user instanceof SkrappsBattler || noUserString.contains("Skrapps"))
			{
				if(displaying)
				{
					SkrappsBattler w = (SkrappsBattler) user;
					EyeCandy atk = new EyeCandy(w.bm.window, w.x+32, w.y-64, BattleAnimation.heart, w.bm,true,heal*2,true,1,0,0);
					atk.setRepeating(true);
					w.bm.eyeCandy.add(atk);
				}
				Global.partnerHP[Global.SKRAPPS] += heal*2;
				if(!displaying)
				{
					Global.talking = 1;
					SpeechBubble.talk("Skrapps used the " + Store.itemNames[item] + "!/He healed " + heal*2 + "HP/Meat is DELICIOUS!!!");
				}
			}
		}
	}
	public static void renderStore(Graphics g)
	{
		if(Store.running)
		{
			g.setColor(Color.gray.darker().darker());
			g.fillRect(64, 32, 940, 320);
			g.fillRect(64,380,256,50);
			g.setColor(Color.white);
			g.setFont(Global.winFont1);
			g.drawString("Store", 74, 68);
			g.drawString("$: " + Global.money, 74, 415);
			
			for (int i=selectionOffset; i<selectionOffset+8;i++)
			{
				if(i>numOfItems)
				{
					continue;
				}
				if(selection == i)
				{
					g.setColor(Color.white);
					g.fillRect(74, 74+(32*i), 920, 32);
					g.setColor(Color.gray.darker().darker());
				}
				else
				{
					g.setColor(Color.white);
				}
				g.setFont(Global.battleFont);
				g.drawString(itemNames[storeItem[i]], 84, 96+(32*i));
				g.drawString("$" + Integer.toString(storePrice[i]), 860, 96+(32*i));
				g.drawString("x" + Integer.toString(Global.items[storeItem[i]]), 940, 96+(32*i));
				
			}
			g.setColor(Color.white);
			g.drawString("Press [X] to exit.", 74, 340);

			g.setColor(Color.gray.darker().darker());
			g.fillRect(64, 495, 940, 74);
			String currentText;
			if(!noMoneyMessage)
			{
				currentText = itemDescriptions[storeItem[selection]];
			}
			else
			{
				currentText = "I'm sorry, but you are too poor to afford this item!/We're not going to give it to you for free, either. We need/money to operate the store, y'know.";
			}
			String[] dialog = new String[6];
			dialog = currentText.split("/");
			g.setColor(Color.white);
			for(int i=0; i<dialog.length; i++)
			{
				if(dialog[i] != null)
				{
					g.drawString(dialog[i], 74, 515+(i*24));
				}
			}
		}
	}
	public static void setNames()
	{
		loader = new ImageLoader();
		itemNames[APPLE] = "Apple";
		itemType[APPLE] = 1;
		itemDescriptions[APPLE] = "A crunchy apple! Restores 4 HP!";
		itemImage[APPLE] = loader.loadImage("/overworld/items/apple.png");
		itemNames[APPLEJUICE] = "Apple Juice";
		itemDescriptions[APPLEJUICE] = "Juice that is flavored like apples!(Probably because it is made of apples,/artificial flavoring didn't exist when it was made...)/It restores 8 HP!";
		itemImage[APPLEJUICE] = loader.loadImage("/overworld/items/juice.png");
		itemType[APPLEJUICE] = 1;
		itemNames[ORANGEJUICE] = "Orange Juice";
		itemDescriptions[ORANGEJUICE] = "This juice was made of freshly-squeezed oranges that are colored orange!/What was named first, the fruit or the color? (Hint: FRUIT)/It restores 4 SP!";
		itemType[ORANGEJUICE] = 2;
		itemImage[ORANGEJUICE] = loader.loadImage("/overworld/items/juice.png");
		itemNames[POISONJUICE] = "Poison Juice";
		itemImage[POISONJUICE] = loader.loadImage("/overworld/items/juice.png");
		itemDescriptions[POISONJUICE] = "This juice is made of some type of poisonous berries...look, I don't/know what's in it, I'm just an item description!/Why don't you ask the shopkeeper!? Throw it at an enemy to poison them!";
		itemType[POISONJUICE] = 3;
		
		itemNames[POISONJUICE] = "Poison Juice";
		itemImage[POISONJUICE] = loader.loadImage("/overworld/items/juice.png");
		itemDescriptions[POISONJUICE] = "This juice is made of some type of poisonous berries...look, I don't/know what's in it, I'm just an item description!/Why don't you ask the shopkeeper!? Throw it at an enemy to poison them!";
		itemType[POISONJUICE] = 3;

		itemNames[BURGER] = "Hamburger";
		itemDescriptions[BURGER] = "A meaty burger made of totally-not-artaficial meat from the Burger/House! Served on a sesame seed bun./Restores 10 HP.";
		itemType[BURGER] = 5;
		itemImage[BURGER] = loader.loadImage("/overworld/items/hamburger.png");

		itemNames[FRIES] = "French Fries";
		itemDescriptions[FRIES] = "Fries that are totally from France. They're so French, they're almost as/French as French toast! Made from potatoes grown in Idaho!/Restore 8 SP.";
		itemType[FRIES] = 2;
		itemImage[FRIES] = loader.loadImage("/overworld/items/fries.png");

		itemNames[TENDERLOMO] = "Tenderlomo";
		itemDescriptions[TENDERLOMO] = "A chunk of nutrient-rich meat that is rich in both nutrients and/redundancy!/Restores 8 HP and 6 SP!";
		itemType[TENDERLOMO] = 0;
		itemImage[TENDERLOMO] = loader.loadImage("/overworld/items/tenderlomo.png");

		itemNames[SPSTICK] = "SP Stick";
		itemDescriptions[SPSTICK] = "An SP-flavored stick of some type of meat. (Chicken, probably.)/And yes, SP now has a flavor, it's a pretty good flavor actually.../Restores 5 SP!";
		itemType[SPSTICK] = 4;
		itemImage[SPSTICK] = loader.loadImage("/overworld/items/drumstick.png");
		
		itemNames[NUGGETS] = "Nuggets";
		itemDescriptions[NUGGETS] = "Made of all-natural totally not artificial chicken! (I think...)/Restore 6 SP.";
		itemType[NUGGETS] = 4;
		itemImage[NUGGETS] = loader.loadImage("/overworld/items/nuggets.png");

		keyItemNames[KEY] = "Key";
		keyItemImage[KEY] = loader.loadImage("/overworld/items/key.png");
		
		keyItemNames[GPADLOCK] = "Green Padlock";
		keyItemImage[GPADLOCK] = loader.loadImage("/overworld/items/padlock_g.png");
		keyItemNames[OPADLOCK] = "Orange Padlock";
		keyItemImage[OPADLOCK] = loader.loadImage("/overworld/items/padlock_o.png");
		keyItemNames[PPADLOCK] = "Purple Padlock";
		keyItemImage[PPADLOCK] = loader.loadImage("/overworld/items/padlock_p.png");
	}
}
