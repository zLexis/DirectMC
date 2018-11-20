package me.zlex.directmc.listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.managers.EcoManager;
public class PlayerQuitListener implements Listener{
	@SuppressWarnings("static-access")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e){
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("custom-messages").getBoolean("enabled")){
			e.setQuitMessage(DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("custom-messages").getString("player-left").replace("&", "§").replace("{PLAYER}", e.getPlayer().getName()));
		}
		if (DirectMC.getTPAManager().isPlayerPresent(e.getPlayer())){
			DirectMC.getTPAManager().deleteTPARequest(e.getPlayer());
		}
		if (DirectMC.getEcoManager().isEnabled()){
			DirectMC.getEcoManager().fixMoney(e.getPlayer(), EcoManager.getMinMoney(), EcoManager.getMaxMoney());
		}
	}
}