package customSpec.main;

import java.util.Collection;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class SpectatorModeManager {
	private HashMap<String, SpectatorMode> spectators = new HashMap<>();

	public SpectatorMode getSpectatorModeFor(Player player) {
		SpectatorMode spectator = this.spectators.get(player.getName());
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
