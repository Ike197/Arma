package me.armas;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.armas.gui.ConfigurationGui;
import me.armas.gui.EditGui;

public class ArmasListener implements Listener {

	public static final String PREFIX = "Armas.";
	public static final String NAME_SUFFIX = ".Name";
	public static final String EDIT_PREFIX = "§cEdit ";

	private ArrayList<Player> cna = new ArrayList<>();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR) {
			ArmasPlugin.get().getApi().disparaPistola(p);
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (cna.contains(p)) {
			ArmasPlugin.get().getConfig().set(PREFIX + ConfigurationGui.getHashmap().get(p) + NAME_SUFFIX,
					e.getMessage());
			ArmasPlugin.get().saveConfig();
			e.setCancelled(true);
			cna.remove(p);
			p.sendMessage(
					"§aHas cambiado el nombre del arma a " + e.getMessage().replace("&", "§") + " §acorrectamente");
		}
	}

	public int getSumFromSlot(int slot) {
		switch (slot) {
			case 3:
				return -1;
			case 2:
				return -5;
			case 5:
				return 1;
			case 6:
				return 5;
			default:
				return 0;
		}
	}

	@EventHandler
	public void onInventory(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getClickedInventory() == null)
			return;
		int slot = e.getSlot();
		if (p.getOpenInventory().getTitle().equals("§cPistolas")) {
			for (String key : ArmasPlugin.get().getConfig().getConfigurationSection("Armas").getKeys(false)) {
				if (!e.getCurrentItem().getItemMeta().getDisplayName()
						.equals(ArmasPlugin.get().getConfig().getString(PREFIX + key + NAME_SUFFIX).replace("&",
								"§"))) {
					continue;
				}
				Bukkit.getScheduler().scheduleSyncDelayedTask(ArmasPlugin.get(), () -> {
					ConfigurationGui.configuraciongui(p, key);
					ConfigurationGui.getHashmap().put(p, key);
				}, 20);
			}
			e.setCancelled(true);
		} else if (p.getOpenInventory().getTitle().equals(EDIT_PREFIX
				+ ArmasPlugin.get().getConfig().getString(PREFIX + ConfigurationGui.getHashmap().get(p) + NAME_SUFFIX)
						.replace(
								"&",
								"§"))) {
			switch (slot) {
				case 1:
					cna.add(p);
					p.closeInventory();
					p.sendMessage("§a§lSelecciona el nombre del arma.");
					break;
				case 2:
					ConfigurationGui.fuerzaedit(p, ConfigurationGui.getHashmap().get(p));
					break;
				case 4:
					ConfigurationGui.maxedit(p, ConfigurationGui.getHashmap().get(p));
					break;
				case 6:
					ArmasPlugin.get().getApi().removePistola(ConfigurationGui.getHashmap().get(p));
					p.sendMessage("§cHas borrado el arma " + ConfigurationGui.getHashmap().get(p) + " correctamente.");
					Bukkit.getConsoleSender().sendMessage(
							"§cEl arma " + ConfigurationGui.getHashmap().get(p) + " ha sido eliminada por el jugador "
									+ p.getName());
					p.closeInventory();
					break;
				case 7:
					ArmasPlugin.get().getApi().getPistola(ConfigurationGui.getHashmap().get(p), p);
					p.closeInventory();
					break;
				case 8:
					EditGui.editGui(p);
					break;
				default:
					break;
			}
			e.setCancelled(true);
		} else {
			if (slot == 8) {
				ConfigurationGui.configuraciongui(p, ConfigurationGui.getHashmap().get(p));
			} else {
				int sum = getSumFromSlot(slot);
				ArmasPlugin.get().getConfig().set(PREFIX + ConfigurationGui.getHashmap().get(p) + ".Max",
						ArmasPlugin.get().getConfig().getInt(PREFIX + ConfigurationGui.getHashmap().get(p) + ".Max")
								+ sum);
				ArmasPlugin.get().saveConfig();
				if (p.getOpenInventory().getTitle().equals(EDIT_PREFIX
						+ ArmasPlugin.get().getConfig()
								.getString(PREFIX + ConfigurationGui.getHashmap().get(p) + NAME_SUFFIX)
								.replace("&", "§")
						+ " Max")) {
					ConfigurationGui.fuerzaedit(p, ConfigurationGui.getHashmap().get(p));
				} else if (p.getOpenInventory().getTitle().equals(EDIT_PREFIX
						+ ArmasPlugin.get().getConfig()
								.getString(PREFIX + ConfigurationGui.getHashmap().get(p) + NAME_SUFFIX)
								.replace("&", "§")
						+ " Fuerza")) {
					ConfigurationGui.maxedit(p, ConfigurationGui.getHashmap().get(p));
				}
			}
			e.setCancelled(true);
		}
	}

}
