package me.armas;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.armas.gui.ConfigurationGui;
import me.armas.gui.EditGui;

public class ArmasListener implements Listener {

	public static final String PREFIX = "Armas.";
	public static final String NAME_SUFFIX = ".Name";
	public static final String EDIT_PREFIX = "§cEdit ";

	// cna: CambiarNombreArma
	// la lista de los jugadores que están cambiando el nombre del arma
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
		if (!cna.contains(p)) return;
		String nuevoNombre = e.getMessage();
		ArmasPlugin.get().getConfig().set(PREFIX + ConfigurationGui.getHashmap().get(p) + NAME_SUFFIX,
				nuevoNombre);
		ArmasPlugin.get().saveConfig();
		e.setCancelled(true);
		cna.remove(p);
		p.sendMessage(
				"§aHas cambiado el nombre del arma a " + nuevoNombre.replace("&", "§") + " §acorrectamente");
	}

	public int getSumFromSlot(int slot) {
		switch (slot) {
			case 3: return -1;
			case 2: return -5;
			case 5: return 1;
			case 6: return 5;
			default: return 0;
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getClickedInventory() == null)
			return;
		int slot = e.getSlot();
		ItemStack item = e.getCurrentItem();
		String CURRENT_ARMA_NAME = ConfigurationGui.getHashmap().get(p);
		if(CURRENT_ARMA_NAME == null) return;
		String inventoryName = p.getOpenInventory().getTitle();
		FileConfiguration config = ArmasPlugin.get().getConfig();
		if (item != null && p.getOpenInventory().getTitle().equals("§cPistolas")) {
			for (String key : ArmasPlugin.get().getConfig().getConfigurationSection("Armas").getKeys(false)) {
				if (!item.getItemMeta().getDisplayName()
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
				+ ArmasPlugin.get().getConfig().getString(PREFIX + CURRENT_ARMA_NAME + NAME_SUFFIX)
						.replace("&","§"))) {
			switch (slot) {
				case 1:
					cna.add(p);
					p.closeInventory();
					p.sendMessage("§a§lSelecciona el nombre del arma.");
					break;
				case 2:
					ConfigurationGui.fuerzaedit(p, CURRENT_ARMA_NAME);
					break;
				case 4:
					ConfigurationGui.maxedit(p, CURRENT_ARMA_NAME);
					break;
				case 6:
					ArmasPlugin.get().getApi().removePistola(CURRENT_ARMA_NAME);
					p.sendMessage("§cHas borrado el arma " + CURRENT_ARMA_NAME + " correctamente.");
					Bukkit.getConsoleSender().sendMessage(
							"§cEl arma " + CURRENT_ARMA_NAME + " ha sido eliminada por el jugador "
									+ p.getName());
					p.closeInventory();
					break;
				case 7:
					ArmasPlugin.get().getApi().getPistola(CURRENT_ARMA_NAME, p);
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
			int sum = getSumFromSlot(slot);
			if(inventoryName.startsWith(EDIT_PREFIX
					+ config
					.getString(PREFIX + CURRENT_ARMA_NAME + NAME_SUFFIX)
					.replace("&", "§")) && slot == 8) {
				ConfigurationGui.configuraciongui(p, CURRENT_ARMA_NAME);
				e.setCancelled(true);
				return;
			}
			if (inventoryName.equals(EDIT_PREFIX
					+ config
							.getString(PREFIX + CURRENT_ARMA_NAME + NAME_SUFFIX)
							.replace("&", "§")
					+ " Max")) {
				config.set(PREFIX + CURRENT_ARMA_NAME + ".Max",
						config.getInt(PREFIX + CURRENT_ARMA_NAME + ".Max")
								+ sum);
				ArmasPlugin.get().saveConfig();
				ConfigurationGui.maxedit(p, CURRENT_ARMA_NAME);
				e.setCancelled(true);
			} else if (inventoryName.equals(EDIT_PREFIX
					+ config.getString(PREFIX + CURRENT_ARMA_NAME + NAME_SUFFIX)
							.replace("&", "§")
					+ " Fuerza")) {
				config.set(PREFIX + CURRENT_ARMA_NAME + ".Fuerza",
						ArmasPlugin.get().getConfig().getInt(PREFIX + CURRENT_ARMA_NAME + ".Fuerza")
								+ sum);
				ArmasPlugin.get().saveConfig();
				ConfigurationGui.fuerzaedit(p, CURRENT_ARMA_NAME);
				e.setCancelled(true);
			}
		}
	}

}
