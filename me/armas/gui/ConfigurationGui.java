package me.armas.gui;

import java.util.HashMap;
import java.util.Map;

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

	private static final String BACK_TEXT = "§4Atras";
	private static final String ARMAS_PREFIX = "Armas";
	private static final String FUERZA = "Fuerza";
	private static final String MAX = "Max";
	private static final String NAME = "Name";
	private static final String EDIT = "§cEdit ";
	private static final String FRMT3 = "%s.%s.%s";

	private static final HashMap<Player, String> hashmap = new HashMap<>();

	public static final Map<Player, String> getHashmap() {
		return hashmap;
	}

	public static void configuraciongui(Player p, String arma) {
		Inventory menu = Bukkit.createInventory(null, 1 * 9,
				EDIT + ArmasPlugin.get().getConfig()
						.getString(String.format(FRMT3, ARMAS_PREFIX, arma, NAME))
						.replace("&", "§"));
		ItemStack item = new ItemStack(Material.NAME_TAG);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ArmasPlugin.get().getConfig().getString(String.format(FRMT3, ARMAS_PREFIX, arma, NAME))
				.replace("&", "§"));
		item.setItemMeta(meta);
		menu.setItem(1, item);

		item = new ItemStack(Material.ARROW);
		meta = item.getItemMeta();
		meta.setDisplayName(
				"§bBalas " + ArmasPlugin.get().getConfig().getString(String.format(FRMT3, ARMAS_PREFIX, arma, MAX)));
		item.setItemMeta(meta);
		menu.setItem(4, item);

		item = new ItemStack(Material.POTION);
		meta = item.getItemMeta();
		meta.setDisplayName("§cFuerza "
				+ ArmasPlugin.get().getConfig().getString(String.format(FRMT3, ARMAS_PREFIX, arma, FUERZA)));
		item.setItemMeta(meta);
		menu.setItem(2, item);

		item = new ItemStack(Material.BARRIER);
		meta = item.getItemMeta();
		meta.setDisplayName(BACK_TEXT);
		item.setItemMeta(meta);
		menu.setItem(8, item);

		item = new ItemStack(Material.GOLDEN_HORSE_ARMOR);
		meta = item.getItemMeta();
		meta.setDisplayName("§aObtener arma");
		item.setItemMeta(meta);
		menu.setItem(7, item);

		item = new ItemStack(Material.SHEARS);
		meta = item.getItemMeta();
		meta.setDisplayName("§cBorrar arma");
		item.setItemMeta(meta);
		menu.setItem(6, item);

		p.openInventory(menu);
	}

	public static void maxedit(Player p, String arma) {
		Inventory menu = Bukkit.createInventory(null, 1 * 9,
				EDIT
						+ ArmasPlugin.get().getConfig().getString(String.format(FRMT3, ARMAS_PREFIX, arma, NAME))
								.replace("&", "§")
						+ " Max");

		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(
				"§b" + ArmasPlugin.get().getConfig().getString(String.format(FRMT3, ARMAS_PREFIX, arma, MAX)));
		item.setItemMeta(meta);
		menu.setItem(4, item);

		addItems(menu);

		p.openInventory(menu);

	}

	public static void fuerzaedit(Player p, String arma) {
		Inventory menu = Bukkit.createInventory(null, 1 * 9,
				EDIT
						+ ArmasPlugin.get().getConfig().getString(String.format(FRMT3, ARMAS_PREFIX, arma, NAME))
								.replace("&", "§")
						+ " Fuerza");

		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(
				"§b" + ArmasPlugin.get().getConfig().getString(String.format(FRMT3, ARMAS_PREFIX, arma, FUERZA)));
		item.setItemMeta(meta);
		menu.setItem(4, item);

		addItems(menu);

		p.openInventory(menu);

	}

	public static void addItems(Inventory menu) {
		ItemStack item;
		ItemMeta meta;

		item = new ItemStack(Material.ACACIA_BUTTON);
		meta = item.getItemMeta();
		meta.setDisplayName("§c-1");
		item.setItemMeta(meta);
		menu.setItem(3, item);

		item = new ItemStack(Material.ACACIA_BUTTON);
		meta = item.getItemMeta();
		meta.setDisplayName("§c-5");
		item.setItemMeta(meta);
		menu.setItem(2, item);

		item = new ItemStack(Material.ACACIA_BUTTON);
		meta = item.getItemMeta();
		meta.setDisplayName("§a+1");
		item.setItemMeta(meta);
		menu.setItem(5, item);
		item = new ItemStack(Material.ACACIA_BUTTON);
		meta = item.getItemMeta();
		meta.setDisplayName("§a+5");
		item.setItemMeta(meta);
		menu.setItem(6, item);

		item = new ItemStack(Material.BARRIER);
		meta = item.getItemMeta();
		meta.setDisplayName(BACK_TEXT);
		item.setItemMeta(meta);
		menu.setItem(8, item);
	}
}
