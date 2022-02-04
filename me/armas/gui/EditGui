package me.armas.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.armas.M;

public class EditGui {

	public static void editgui(Player p) {
		Inventory menu = Bukkit.createInventory(null, 3 * 9, "§cPistolas");
		
		for(String key : M.get().getConfig().getConfigurationSection("Armas").getKeys(false)) {
			ItemStack a = new ItemStack(Material.GOLDEN_HORSE_ARMOR);
			ItemMeta aa = a.getItemMeta();
			aa.setDisplayName(M.get().getConfig().getString("Armas."+ key.toLowerCase() +".Name").replace("&", "§"));
			a.setItemMeta(aa);
			menu.addItem(a);
		}
		
//		ItemStack a = new ItemStack(Material.DIAMOND);
//		ItemMeta aa = a.getItemMeta();
//		aa.setDisplayName("§a§lCrear arma");
//		a.setItemMeta(aa);
//		menu.setItem(26, a);
		
		p.openInventory(menu);
	}
	
}
