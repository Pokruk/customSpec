package customSpec.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpectatorModeManagerListener extends SpectatorModeManager implements Listener {
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		Bukkit.getLogger().info("PlayerQUIT");
		SpectatorMode specMode = this.getSpectatorModeFor(e.getPlayer());
		if (specMode == null) return;
		
		if (specMode.isEnabled())
			specMode.disable();
		this.remove(e.getPlayer());
	}

	@EventHandler
	public void onPlayerKickEvent(PlayerKickEvent e) {
		Bukkit.getLogger().info("PlayerQUIT");
		SpectatorMode specMode = this.getSpectatorModeFor(e.getPlayer());		
		if (specMode == null) return;

		if (specMode.isEnabled())
			specMode.disable();
		this.remove(e.getPlayer());
	}
}
