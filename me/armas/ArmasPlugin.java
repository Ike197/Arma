package me.armas;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ArmasPlugin extends JavaPlugin {

	private static ArmasPlugin plugin;
	private PistolaApi papi;

	@Override
	public void onEnable() {
		plugin = this;
		papi = new PistolaApi();

		getConfig().options().copyDefaults(true);
		saveConfig();

		getCommand("arma").setExecutor(new ACommand());
		Bukkit.getPluginManager().registerEvents(new ArmasListener(), this);
	}

	public static ArmasPlugin get() {
		return plugin;
	}

	public PistolaApi getApi() {
		return papi;
	}

}
