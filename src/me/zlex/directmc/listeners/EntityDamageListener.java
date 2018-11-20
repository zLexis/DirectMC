package me.zlex.directmc.listeners;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import me.zlex.directmc.main.DirectMC;
public class EntityDamageListener implements Listener{
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
		if (e.getEntity() instanceof Player){
			if (DirectMC.hasGodmode((Player) e.getEntity())){
				e.setCancelled(true);
			}
		}else if (e.getDamager() instanceof Player){
			if (DirectMC.hasOnepunch((Player) e.getDamager())){
				LivingEntity en = (LivingEntity) e.getEntity();
				e.setDamage(en.getMaxHealth());
			}
		}
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamage(EntityDamageEvent e){
		if (e.getEntity() instanceof Player){
			if (DirectMC.hasGodmode((Player) e.getEntity())){
				e.setCancelled(true);
			}
		}
	}
}