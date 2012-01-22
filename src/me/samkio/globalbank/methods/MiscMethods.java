package me.samkio.globalbank.methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.samkio.globalbank.Bankventory;
import me.samkio.globalbank.GlobalBank;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MiscMethods {
	public static Location locFromString(String s) {
		String[] split = s.split(",");
		Location l = new Location(GlobalBank.plugin.getServer().getWorld(
				split[0]), Double.parseDouble(split[1]),
				Double.parseDouble(split[2]), Double.parseDouble(split[3]));
		return l;

	}

	public static String stringFromLoc(Location l) {
		return l.getWorld().getName() + "," + l.getX() + "," + l.getY() + ","
				+ l.getZ();

	}

	public static Bankventory getAccount(Player p) {
		if (GlobalBank.plugin.bankventories.containsKey(p)) return GlobalBank.plugin.bankventories.get(p);
		File f = new File(GlobalBank.plugin.getDataFolder() + "/Data/"
				+ p.getName() + ".bankventory");
		if (f.exists()) {
			try {
				FileInputStream fis = new FileInputStream(
						GlobalBank.plugin.getDataFolder() + "/Data/"
								+ p.getName() + ".bankventory");
				ObjectInputStream ois = new ObjectInputStream(fis);
				Bankventory b = (Bankventory) ois.readObject();
				ois.close();
				fis.close();
				GlobalBank.plugin.bankventories.put(p, b);
				return b;
			} catch (Exception e) {
				GlobalBank.log.info("[GB]" + e);
			}
		} else {
			Bankventory b = new Bankventory();
			try {
				FileOutputStream fos = new FileOutputStream(
						GlobalBank.plugin.getDataFolder() + "/Data/"
								+ p.getName() + ".bankventory");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(b);
				oos.close();
				fos.close();
			} catch (Exception e) {
				GlobalBank.log.info("[GB]" + e);
			}
			GlobalBank.plugin.bankventories.put(p, b);
			return b;
		}
		return null;
	}

	public static void saveAll() {
		for (Player p : GlobalBank.plugin.bankventories.keySet()) {
			Bankventory b = GlobalBank.plugin.bankventories.get(p);

			try {
				FileOutputStream fos = new FileOutputStream(
						GlobalBank.plugin.getDataFolder() + "/Data/"
								+ p.getName() + ".bankventory");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(b);
				oos.close();
				fos.close();
			} catch (Exception e) {
				GlobalBank.log.info("[GB]" + e);
			}
		}
	}
}
