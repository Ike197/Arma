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

import me.armas.gui.ConfiguracionGui;
import me.armas.gui.EditGui;

public class AllListener implements Listener {

	public ArrayList<Player> cna = new ArrayList<>();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			M.get().getApi().disparaPistola(p);
		}
	}

	@EventHandler
	public void onchact(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (cna.contains(p)) {
			M.get().getConfig().set("Armas." + ConfiguracionGui.t.get(p) + ".Name", e.getMessage());
			M.get().saveConfig();
			e.setCancelled(true);
			cna.remove(p);
			p.sendMessage("§aHas cambiado el nombre del arma a "+ e.getMessage().replace("&", "§") +" §acorrectamente");
		}
//		if (crear.contains(p)) {
//			if(!M.get().getApi().checkPistola(e.getMessage().toLowerCase())) {
//				M.get().getConfig().set("Armas." + e.getMessage().toLowerCase() + ".Name", e.getMessage());
//				M.get().getConfig().set("Armas." + e.getMessage().toLowerCase() + ".Max", 12);
//				M.get().getConfig().set("Armas." + e.getMessage().toLowerCase() + ".Fuerza", 5);
//				M.get().saveConfig();
//				e.setCancelled(true);
//				crear.remove(p);
//				p.sendMessage("§aHas creado el arma " + e.getMessage() + " correctamente.");
//			} else {
//				p.sendMessage("§cEsta arma ya esta creada, intentalo de nuevo.");
//				e.setCancelled(true);
//			}
//		}
	}

	@EventHandler
	public void onInventory(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(p.getOpenInventory() != null && e.getInventory() != null && e.getClick() != null && e.getClickedInventory() != null) {
			if (p.getOpenInventory().getTitle().equals("§cPistolas")) {
				for (String key : M.get().getConfig().getConfigurationSection("Armas").getKeys(false)) {
					if (e.getCurrentItem().getItemMeta().getDisplayName()
							.equals(M.get().getConfig().getString("Armas." + key + ".Name").replace("&", "§"))) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(M.get(), new Runnable() {
							@Override
							public void run() {
//								crear.remove(p);
								ConfiguracionGui.configuraciongui(p, key);
								ConfiguracionGui.t.put(p, key);
							}
						}, 20);
//					} else {
//						crear.add(p);
//						p.closeInventory();
//						p.sendMessage("§dSeleccione un nombre para su arma.");
					}
				}
				e.setCancelled(true);
			} else if (p.getOpenInventory().getTitle().equals("§cEdit "
					+ M.get().getConfig().getString("Armas." + ConfiguracionGui.t.get(p) + ".Name").replace("&", "§"))) {
				int slot = e.getSlot();
				if (slot == 1) {
					cna.add(p);
					p.closeInventory();
					p.sendMessage("§a§lSelencciona el nombre del arma.");
				}
				if (slot == 2) {
					ConfiguracionGui.fuerzaedit(p, ConfiguracionGui.t.get(p));
				}
				if (slot == 4) {
					ConfiguracionGui.maxedit(p, ConfiguracionGui.t.get(p));
				}
				if (slot == 6) {
					M.get().getApi().removePistola(ConfiguracionGui.t.get(p));
					p.sendMessage("§cHas borrado el arma " + ConfiguracionGui.t.get(p) + " correctamente.");
					Bukkit.getConsoleSender().sendMessage(
							"§cEl arma " + ConfiguracionGui.t.get(p) + " ha sido eliminada por el jugador " + p.getName());
					p.closeInventory();
				}
				if (slot == 7) {
					M.get().getApi().getPistola(ConfiguracionGui.t.get(p), p);
					p.closeInventory();
				}
				if (slot == 8) {
					EditGui.editgui(p);
				}
				e.setCancelled(true);
			} else if (p.getOpenInventory().getTitle().equals("§cEdit "
					+ M.get().getConfig().getString("Armas." + ConfiguracionGui.t.get(p) + ".Name").replace("&", "§")
					+ " Max")) {
				int slot = e.getSlot();
				if (slot == 3) {
					M.get().getConfig().set("Armas." + ConfiguracionGui.t.get(p) + ".Max",
							M.get().getConfig().getInt("Armas." + ConfiguracionGui.t.get(p) + ".Max") - 1);
					M.get().saveConfig();
					ConfiguracionGui.maxedit(p, ConfiguracionGui.t.get(p));
				}
				if (slot == 2) {
					M.get().getConfig().set("Armas." + ConfiguracionGui.t.get(p) + ".Max",
							M.get().getConfig().getInt("Armas." + ConfiguracionGui.t.get(p) + ".Max") - 5);
					M.get().saveConfig();
					ConfiguracionGui.maxedit(p, ConfiguracionGui.t.get(p));
				}
				if (slot == 5) {
					M.get().getConfig().set("Armas." + ConfiguracionGui.t.get(p) + ".Max",
							M.get().getConfig().getInt("Armas." + ConfiguracionGui.t.get(p) + ".Max") + 1);
					M.get().saveConfig();
					ConfiguracionGui.maxedit(p, ConfiguracionGui.t.get(p));
				}
				if (slot == 6) {
					M.get().getConfig().set("Armas." + ConfiguracionGui.t.get(p) + ".Max",
							M.get().getConfig().getInt("Armas." + ConfiguracionGui.t.get(p) + ".Max") + 5);
					M.get().saveConfig();
					ConfiguracionGui.maxedit(p, ConfiguracionGui.t.get(p));
				}
				if (slot == 8) {
					ConfiguracionGui.configuraciongui(p, ConfiguracionGui.t.get(p));
				}
				e.setCancelled(true);
			} else if (p.getOpenInventory().getTitle().equals("§cEdit "
					+ M.get().getConfig().getString("Armas." + ConfiguracionGui.t.get(p) + ".Name").replace("&", "§")
					+ " Fuerza")) {
				int slot = e.getSlot();
				if (slot == 3) {
					M.get().getConfig().set("Armas." + ConfiguracionGui.t.get(p) + ".Fuerza",
							M.get().getConfig().getInt("Armas." + ConfiguracionGui.t.get(p) + ".Fuerza") - 1);
					M.get().saveConfig();
					ConfiguracionGui.fuerzaedit(p, ConfiguracionGui.t.get(p));
				}
				if (slot == 2) {
					M.get().getConfig().set("Armas." + ConfiguracionGui.t.get(p) + ".Fuerza",
							M.get().getConfig().getInt("Armas." + ConfiguracionGui.t.get(p) + ".Fuerza") - 5);
					M.get().saveConfig();
					ConfiguracionGui.fuerzaedit(p, ConfiguracionGui.t.get(p));
				}
				if (slot == 5) {
					M.get().getConfig().set("Armas." + ConfiguracionGui.t.get(p) + ".Fuerza",
							M.get().getConfig().getInt("Armas." + ConfiguracionGui.t.get(p) + ".Fuerza") + 1);
					M.get().saveConfig();
					ConfiguracionGui.fuerzaedit(p, ConfiguracionGui.t.get(p));
				}
				if (slot == 6) {
					M.get().getConfig().set("Armas." + ConfiguracionGui.t.get(p) + ".Fuerza",
							M.get().getConfig().getInt("Armas." + ConfiguracionGui.t.get(p) + ".Fuerza") + 5);
					M.get().saveConfig();
					ConfiguracionGui.fuerzaedit(p, ConfiguracionGui.t.get(p));
				}
				if (slot == 8) {
					ConfiguracionGui.configuraciongui(p, ConfiguracionGui.t.get(p));
				}
				e.setCancelled(true);
			}
		}
	}

}
