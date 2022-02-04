package me.armas;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PistolaApi {

	public HashMap<Player, Integer> disparo = new HashMap<>();
	public ArrayList<Player> recarga = new ArrayList<>();
	
	public PistolaApi() {
		
	}
	
	public boolean checkPistola(String modelo) {
		return M.get().getConfig().contains("Armas."+ modelo.toLowerCase());
	}
	
	public void getPistola(String modelo, Player p) {
		if(checkPistola(modelo)) {
			ItemStack a = new ItemStack(Material.GOLDEN_HORSE_ARMOR);
			ItemMeta aa = a.getItemMeta();
			aa.setDisplayName(M.get().getConfig().getString("Armas."+ modelo.toLowerCase() +".Name").replace("&", "§"));
			a.setItemMeta(aa);
			p.getInventory().addItem(a);
			p.sendMessage("§aHas recibido §c["+ M.get().getConfig().getString("Armas."+ modelo.toLowerCase() +".Name").replace("&", "§") +"§c]");
			Bukkit.getConsoleSender().sendMessage("§cEl jugador "+ p.getName() +" ha recibido la arma "+ modelo +".");
		} else {
			Bukkit.getConsoleSender().sendMessage("§cEl modelo de arma que esta siendo seleccionado no existe, intentelo de nuevo o compruebe el archivo de configuracion a ver si todo esta correcto.");
			p.sendMessage("§c§l[ERROR]: §cCompruebe la consola para mas informacion.");
		}
	}
	
	public void removePistola(String modelo) {
		if(checkPistola(modelo)) {
			M.get().getConfig().set("Armas."+ modelo.toLowerCase(), null);
			M.get().saveConfig();
		}
	}
	
	public void createPistola(String modelo) {
		if(!checkPistola(modelo)) {
			M.get().getConfig().set("Armas." + modelo + ".Name", modelo);
			M.get().getConfig().set("Armas." + modelo + ".Max", 12);
			M.get().getConfig().set("Armas." + modelo + ".Fuerza", 5);
			M.get().saveConfig();
		}
	}
	
	public void disparaPistola(Player p) {
		for(String key : M.get().getConfig().getConfigurationSection("Armas").getKeys(false)) {
			if(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(M.get().getConfig().getString("Armas."+ key.toLowerCase() +".Name").replace("&", "§"))) {
				if(disparo.containsKey(p)) {
					if(!recarga.contains(p)) {
						if(p.getInventory().contains(Material.ARROW)) {
							if(disparo.get(p) != 0) {
								int fuerza = M.get().getConfig().getInt("Armas."+ key.toLowerCase() +".Fuerza");
								Arrow arrow = p.launchProjectile(Arrow.class);
								arrow.setShooter(p);
								arrow.setVelocity(p.getLocation().getDirection().multiply(fuerza));
								p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 10, 10);
								disparo.put(p, disparo.get(p) -1);
							} else {
								recarga.add(p);
								recargando(p);
							}
						}
					} else {
						p.sendMessage("§cEstoy recargando!!");
					}
				} else {
					if(p.getInventory().contains(Material.ARROW)) {
						int fuerza = M.get().getConfig().getInt("Armas."+ key.toLowerCase() +".Fuerza");
						Arrow arrow = p.launchProjectile(Arrow.class);
						arrow.setShooter(p);
						arrow.setVelocity(p.getLocation().getDirection().multiply(fuerza));
						p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 10, 10);
						disparo.put(p, M.get().getConfig().getInt("Armas."+ key.toLowerCase() +".Max") - 1);
					}
				}
				
			}
		}
	}
	
	public void recargando(Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(M.get(), new Runnable() {
			@Override
			public void run() {
				for(String key : M.get().getConfig().getConfigurationSection("Armas").getKeys(false)) {
					disparo.put(p, M.get().getConfig().getInt("Armas."+ key.toLowerCase() +".Max"));
					p.sendMessage("§aRecargado!");
					recarga.remove(p);
				}
			}
		}, 5 * 20);
	}
	
}
