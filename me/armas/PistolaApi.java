package me.armas;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PistolaApi {

	private static final String ARMAS_PREFIX = "Armas";
	private static final String FUERZA = "Fuerza";
	private static final String MAX = "Max";
	private static final String NAME = "Name";
	private static final String FRMT3 = "%s.%s.%s";
	private static final String FRMT2 = "%s.%s";

	private HashMap<Player, Integer> disparo = new HashMap<>();
	private ArrayList<Player> recarga = new ArrayList<>();

	public PistolaApi() {
		// TODO document why this constructor is empty
	}

	public boolean checkPistola(String modelo) {
		return ArmasPlugin.get().getConfig().contains(String.format(FRMT2, ARMAS_PREFIX, modelo.toLowerCase()));
	}

	public void getPistola(String modelo, Player p) {
		if (checkPistola(modelo)) {
			ItemStack item = new ItemStack(Material.GOLDEN_HORSE_ARMOR);
			ItemMeta meta = item.getItemMeta();
			String name = ArmasPlugin.get().getConfig()
					.getString(String.format(FRMT3, ARMAS_PREFIX, modelo.toLowerCase(), NAME)).replace("&", "§");
			meta.setDisplayName(name);
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
			p.sendMessage("§aHas recibido §c[" + name + "§c]");
			Bukkit.getConsoleSender()
					.sendMessage("§cEl jugador " + p.getName() + " ha recibido la arma " + modelo + ".");
		} else {
			Bukkit.getConsoleSender().sendMessage(
					"§cEl modelo de arma que esta siendo seleccionado no existe, inténtelo de nuevo o compruebe el archivo de configuración a ver si todo esta correcto.");
			p.sendMessage("§c§l[ERROR]: §cCompruebe la consola para mas información.");
		}
	}

	public void removePistola(String modelo) {
		if (checkPistola(modelo)) {
			ArmasPlugin.get().getConfig().set(String.format(FRMT2, ARMAS_PREFIX, modelo.toLowerCase()), null);
			ArmasPlugin.get().saveConfig();
		}
	}

	public void createPistola(String modelo) {
		ArmasPlugin plugin = ArmasPlugin.get();
		FileConfiguration config = plugin.getConfig();
		if (!checkPistola(modelo)) {
			config.set(String.format(FRMT3, ARMAS_PREFIX, modelo, NAME), modelo);
			config.set(String.format(FRMT3, ARMAS_PREFIX, modelo, MAX), 12);
			config.set(String.format(FRMT3, ARMAS_PREFIX, modelo, FUERZA), 5);
			plugin.saveConfig();
		}
	}

	public void disparaPistola(Player p) {
		if (!p.getInventory().contains(Material.ARROW)) {
			return;
		}
		for (String key : ArmasPlugin.get().getConfig().getConfigurationSection(ARMAS_PREFIX).getKeys(false)) {
			if (!p.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
					.equals(ArmasPlugin.get().getConfig()
							.getString(String.format(FRMT3, ARMAS_PREFIX, key.toLowerCase(), NAME))
							.replace("&", "§"))) {
				return;
			}
			if (disparo.containsKey(p)) {
				if (recarga.contains(p)) {
					p.sendMessage("§cEstoy recargando!!");
					continue;
				}
				if (disparo.get(p) != 0) {
					int fuerza = ArmasPlugin.get().getConfig()
							.getInt(String.format(FRMT3, ARMAS_PREFIX, key.toLowerCase(), FUERZA));
					Arrow arrow = p.launchProjectile(Arrow.class);
					arrow.setShooter(p);
					arrow.setVelocity(p.getLocation().getDirection().multiply(fuerza));
					p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 10, 10);
					disparo.put(p, disparo.get(p) - 1);
				} else {
					recarga.add(p);
					recargando(p);
				}
			} else {
				int fuerza = ArmasPlugin.get().getConfig()
						.getInt(String.format(FRMT3, ARMAS_PREFIX, key.toLowerCase(), FUERZA));
				Arrow arrow = p.launchProjectile(Arrow.class);
				arrow.setShooter(p);
				arrow.setVelocity(p.getLocation().getDirection().multiply(fuerza));
				p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 10, 10);
				disparo.put(p, ArmasPlugin.get().getConfig()
						.getInt(String.format(FRMT3, ARMAS_PREFIX, key.toLowerCase(), MAX)) - 1);
			}
		}
	}

	public void recargando(Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(ArmasPlugin.get(), () -> {
			for (String key : ArmasPlugin.get().getConfig().getConfigurationSection(ARMAS_PREFIX).getKeys(false)) {
				disparo.put(p, ArmasPlugin.get().getConfig()
						.getInt(String.format(FRMT3, ARMAS_PREFIX, key.toLowerCase(), MAX)));
				p.sendMessage("§aRecargado!");
				recarga.remove(p);
			}
		}, 5 * 20L);
	}

}
