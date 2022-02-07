package me.armas.gui;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.armas.ArmasPlugin;

public class ConfigurationGui {

	private ConfigurationGui() {
	}

	public static final HashMap<Player, String> t = new HashMap<>();

	public static void configuraciongui(Player p, String arma) {
		Inventory menu = Bukkit.createInventory(null, 1 * 9,
				"§cEdit " + ArmasPlugin.get().getConfig().getString("Armas." + arma + ".Name").replace("&", "§"));
		ItemStack a = new ItemStack(Material.NAME_TAG);
		ItemMeta aa = a.getItemMeta();
		aa.setDisplayName(ArmasPlugin.get().getConfig().getString("Armas." + arma + ".Name").replace("&", "§"));
		a.setItemMeta(aa);
		menu.setItem(1, a);

		a = new ItemStack(Material.ARROW);
		aa = a.getItemMeta();
		aa.setDisplayName("§bBalas " + ArmasPlugin.get().getConfig().getString("Armas." + arma + ".Max"));
		a.setItemMeta(aa);
		menu.setItem(4, a);

		a = new ItemStack(Material.POTION);
		aa = a.getItemMeta();
		aa.setDisplayName("§cFuerza " + ArmasPlugin.get().getConfig().getString("Armas." + arma + ".Fuerza"));
		a.setItemMeta(aa);
		menu.setItem(2, a);

		a = new ItemStack(Material.BARRIER);
		aa = a.getItemMeta();
		aa.setDisplayName("§4Atras");
		a.setItemMeta(aa);
		menu.setItem(8, a);

		a = new ItemStack(Material.GOLDEN_HORSE_ARMOR);
		aa = a.getItemMeta();
		aa.setDisplayName("§aObtener arma");
		a.setItemMeta(aa);
		menu.setItem(7, a);

		a = new ItemStack(Material.SHEARS);
		aa = a.getItemMeta();
		aa.setDisplayName("§cBorrar arma");
		a.setItemMeta(aa);
		menu.setItem(6, a);

		p.openInventory(menu);
	}

	public static void maxedit(Player p, String arma) {
		Inventory menu = Bukkit.createInventory(null, 1 * 9,
				"§cEdit " + ArmasPlugin.get().getConfig().getString("Armas." + arma + ".Name").replace("&", "§")
						+ " Max");

		ItemStack a = new ItemStack(Material.ARROW);
		ItemMeta aa = a.getItemMeta();
		aa.setDisplayName("§b" + ArmasPlugin.get().getConfig().getString("Armas." + arma + ".Max"));
		a.setItemMeta(aa);
		menu.setItem(4, a);

		a = new ItemStack(Material.ACACIA_BUTTON);
		aa = a.getItemMeta();
		aa.setDisplayName("§c-1");
		a.setItemMeta(aa);
		menu.setItem(3, a);
		a = new ItemStack(Material.ACACIA_BUTTON);
		aa = a.getItemMeta();
		aa.setDisplayName("§c-5");
		a.setItemMeta(aa);
		menu.setItem(2, a);

		a = new ItemStack(Material.ACACIA_BUTTON);
		aa = a.getItemMeta();
		aa.setDisplayName("§a+1");
		a.setItemMeta(aa);
		menu.setItem(5, a);
		a = new ItemStack(Material.ACACIA_BUTTON);
		aa = a.getItemMeta();
		aa.setDisplayName("§a+5");
		a.setItemMeta(aa);
		menu.setItem(6, a);

		a = new ItemStack(Material.BARRIER);
		aa = a.getItemMeta();
		aa.setDisplayName("§4Atras");
		a.setItemMeta(aa);
		menu.setItem(8, a);

		p.openInventory(menu);

	}

	public static void fuerzaedit(Player p, String arma) {
		Inventory menu = Bukkit.createInventory(null, 1 * 9,
				"§cEdit " + ArmasPlugin.get().getConfig().getString("Armas." + arma + ".Name").replace("&", "§")
						+ " Fuerza");

		ItemStack a = new ItemStack(Material.ARROW);
		ItemMeta aa = a.getItemMeta();
		aa.setDisplayName("§b" + ArmasPlugin.get().getConfig().getString("Armas." + arma + ".Fuerza"));
		a.setItemMeta(aa);
		menu.setItem(4, a);

		a = new ItemStack(Material.ACACIA_BUTTON);
		aa = a.getItemMeta();
		aa.setDisplayName("§c-1");
		a.setItemMeta(aa);
		menu.setItem(3, a);
		a = new ItemStack(Material.ACACIA_BUTTON);
		aa = a.getItemMeta();
		aa.setDisplayName("§c-5");
		a.setItemMeta(aa);
		menu.setItem(2, a);

		a = new ItemStack(Material.ACACIA_BUTTON);
		aa = a.getItemMeta();
		aa.setDisplayName("§a+1");
		a.setItemMeta(aa);
		menu.setItem(5, a);
		a = new ItemStack(Material.ACACIA_BUTTON);
		aa = a.getItemMeta();
		aa.setDisplayName("§a+5");
		a.setItemMeta(aa);
		menu.setItem(6, a);

		a = new ItemStack(Material.BARRIER);
		aa = a.getItemMeta();
		aa.setDisplayName("§4Atras");
		a.setItemMeta(aa);
		menu.setItem(8, a);

		p.openInventory(menu);

	}

}
