package customSpec.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class customSpec extends JavaPlugin implements CommandExecutor {
	boolean customSpecEnabled = true;

	SpectatorModeManagerListener spectatorsManager = new SpectatorModeManagerListener();

	public void onEnable() {
		getCommand("customSpec").setExecutor(this);
		getServer().getPluginManager().registerEvents(spectatorsManager, this);
	}

	public void onDisable() {
		this.spectatorsManager.disableAll();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equals("customspec") || label.equals("cs")) {
			if (!this.customSpecEnabled) {
				sender.sendMessage("This command is off now");
				return true;
			}
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command allowed only for players");
				return true;
			}
			SpectatorMode spectator = this.spectatorsManager.createIfNotExist((Player) sender);
			spectator.toggle();
			return true;
		}
		if (label.equals("customspecoff") || label.equals("csoff")) {
			if (!sender.hasPermission("op")) {
				sender.sendMessage("You must have op to use that");
				return true;
			}
			this.spectatorsManager.disableAll();
			this.spectatorsManager.getSpectators()
					.forEach(spec -> spec.player.sendMessage("CustomSpec turned off by admin"));
			this.spectatorsManager.clear();
			this.customSpecEnabled = false;
			sender.sendMessage("CustomSpec turned off");
			return true;
		}
		if (label.equals("customspecon") || label.equals("cson")) {
			if (!sender.hasPermission("op")) {
				sender.sendMessage("You must have op to use that");
				return true;
			}
			this.customSpecEnabled = true;
			sender.sendMessage("CustomSpec turned on");
			return true;
		}
		return false;
	}
}
