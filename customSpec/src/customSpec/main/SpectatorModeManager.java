package customSpec.main;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpectatorModeManager implements Listener {
	private HashMap<String, SpectatorMode> spectators = new HashMap<>();

	public SpectatorMode getSpectatorModeFor(Player player) {
		return this.spectators.get(player.getName());
	}
	
	public SpectatorMode createIfNotExist(Player player) {
		SpectatorMode spectator = getSpectatorModeFor(player);
		if (spectator == null) {
			spectator = new SpectatorMode(player);
			this.spectators.put(player.getName(), spectator);
		}
		return spectator;
	}

	public Collection<SpectatorMode> getSpectators() {
		return this.spectators.values();
	}

	public void clear() {
		this.spectators.clear();
	}

	public void disableAll() {
		for (SpectatorMode spectator : this.spectators.values()) {
			if (spectator.isEnabled())
				spectator.disable();
		}
	}

	public SpectatorMode remove(Player player) {
		return this.spectators.remove(player.getName());
	}
}
