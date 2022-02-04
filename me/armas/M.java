package me.armas;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class M extends JavaPlugin {
	
	public static M plugin;
	public PistolaApi papi;
	
	@Override
	public void onEnable() {
		plugin = this;
		papi = new PistolaApi();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		getCommand("arma").setExecutor(new ACommand());
		Bukkit.getPluginManager().registerEvents(new AllListener(), this);
	}
	
	public static M get() {
		return plugin;
	}
	public PistolaApi getApi() {
		return papi;
	}

}
