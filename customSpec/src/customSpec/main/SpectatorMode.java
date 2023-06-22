package customSpec.main;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpectatorMode {
	private boolean enabled = false;
	private Location location;
	private GameMode initialGamemode;

	public Player player;

	SpectatorMode(Player player) {
		this.player = player;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void disable() throws IllegalStateException {
		if (!this.enabled)
			throw new IllegalStateException("Can't disable already disabled mode");
		this.player.teleport(this.location);
		this.player.setGameMode(this.initialGamemode);
		this.enabled = false;
	}

	public void enable() throws IllegalStateException {
		if (this.enabled)
			throw new IllegalStateException("Can't enable already enabled mode");
		this.enabled = true;
		this.location = this.player.getLocation();
		this.initialGamemode = this.player.getGameMode();
		this.player.setGameMode(GameMode.SPECTATOR);
	}

	public void toggle() {
		if (isEnabled()) {
			disable();
		} else {
			enable();
		}
	}
}
