package customSpec.main;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class customSpec extends JavaPlugin implements CommandExecutor, Listener{
	HashMap<String, CSPlayer> Spectators= new HashMap<String, CSPlayer>();
	
	boolean customSpecEnabled = true;
	
	@Override
	public void onEnable() {
		 getCommand("customSpec").setExecutor(this);
		 getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		for (Entry<String, CSPlayer> entry: Spectators.entrySet()) {
			String playerName = entry.getKey();
			Player player = Bukkit.getPlayer(entry.getKey());
			CSPlayer csplayer = entry.getValue();
			player.teleport(csplayer.location);
			player.setGameMode(csplayer.gamemode);
			Spectators.remove(playerName);
		}
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		Bukkit.getLogger().info("PlayerQUIT");
		String playerName = player.getName();
		if (Spectators.containsKey(playerName)) {
			CSPlayer csplayer = Spectators.get(playerName);
			player.teleport(csplayer.location);
			player.setGameMode( csplayer.gamemode );
			Spectators.remove(playerName);
		}
	}
		
	@EventHandler
	public void onPlayerKickEvent(PlayerKickEvent e) {
		Player player = e.getPlayer();
		Bukkit.getLogger().info("PlayerQUIT");
		String playerName = player.getName();
		if (Spectators.containsKey(playerName)) {
			CSPlayer csplayer = Spectators.get(playerName);
			player.teleport(csplayer.location);
			player.setGameMode( csplayer.gamemode );
			Spectators.remove(playerName);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((label.equals("customspec") || label.equals("cs"))) {
			if (customSpecEnabled) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					String playerName = sender.getName();
					if (Spectators.containsKey(playerName)) {
						CSPlayer csplayer = Spectators.get(playerName);
						player.teleport(csplayer.location);
						player.setGameMode( csplayer.gamemode );
						Spectators.remove(playerName);
						return true;
					}
					else {
						CSPlayer csplayer = new CSPlayer();
						csplayer.location = player.getLocation();
						csplayer.gamemode = player.getGameMode();
						
						Spectators.put(playerName, csplayer);
						player.setGameMode(GameMode.SPECTATOR);
						return true;
					}
					
				}
				else {
					sender.sendMessage("This command allowed only for players");
				}
			}
			else {
				sender.sendMessage("This command is off now");
			}
		}
		else{
			if (label.equals("customspecoff") || (label.equals("csoff"))) {
				if (sender.hasPermission("op")) {
					for (Entry<String, CSPlayer> entry: Spectators.entrySet()) {
						String playerName = entry.getKey();
						Player player = Bukkit.getServer().getPlayer(playerName);
						CSPlayer csplayer = entry.getValue();
						player.teleport(csplayer.location);
						player.setGameMode( csplayer.gamemode );
						Spectators.remove(playerName);
						player.sendMessage("CustomSpec turned off");
						return true;
					}
					customSpecEnabled = false;
					sender.sendMessage("CustomSpec turned off");
					return true;
				}
				else {
					sender.sendMessage("You must have op to use that");
					return true;
				}
			}
			else {
				if ((label.equals("customspecon") || (label.equals("cson")))){
					if (sender.hasPermission("op")) {
						customSpecEnabled = true;
						sender.sendMessage("CustomSpec turned on");
						return true;
					}
					else {
						sender.sendMessage("You must have op to use that");
						return true;
					}
				}
			}
		}
		return false;
	}
}
