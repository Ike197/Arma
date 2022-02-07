package me.armas;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.armas.gui.EditGui;

public class ArmasCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cEste comando no se puede usar desde la consola.");
			return false;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("armas.admin")) {
			p.sendMessage("§cNo tienes permiso para usar este comando.");
			return false;
		}
		if (args.length == 0) {
			p.sendMessage("§d§l======== §c§lArmas §d§l========");
			p.sendMessage("");
			p.sendMessage("§7/arma give <nombre>");
			p.sendMessage("§7/arma remove <nombre>");
			p.sendMessage("§7/arma create <nombre>");
			p.sendMessage("§7/arma edit");
		} else if (args[0].equalsIgnoreCase("give")) {
			if (args.length == 2) {
				ArmasPlugin.get().getApi().getPistola(args[1], p);
			} else {
				p.sendMessage("§cusa: /arma give <nombre>");
			}
		} else if (args[0].equalsIgnoreCase("edit")) {
			EditGui.editGui(p);
		} else if (args[0].equalsIgnoreCase("remove")) {
			if (args.length != 2) {
				p.sendMessage("§cusa: /arma remove <nombre>");
				return false;
			}
			ArmasPlugin.get().getApi().removePistola(args[1]);
			p.sendMessage("§cHas borrado el arma " + args[1] + " correctamente.");
			Bukkit.getConsoleSender()
					.sendMessage("§cEl arma " + args[1] + " ha sido eliminada por el jugador " + p.getName());
		} else if (args[0].equalsIgnoreCase("create")) {
			if (args.length != 2) {
				p.sendMessage("§cusa: /arma create <nombre>");
				return false;
			}
			ArmasPlugin.get().getApi().createPistola(args[1]);
			p.sendMessage("§aHas creado el arma " + args[1] + " correctamente.");
			Bukkit.getConsoleSender()
					.sendMessage("§aEl arma " + args[1] + " ha sido creada por el jugador " + p.getName());
		}
		return true;
	}

}
