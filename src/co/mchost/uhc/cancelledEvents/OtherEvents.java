package co.mchost.uhc.cancelledEvents;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import co.mchost.uhc.core.Main;

public class OtherEvents implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		
		Player player = e.getEntity();
				
		Main.uhc.kill(player);
		
		e.setDeathMessage(ChatColor.RED + "" + e.getDeathMessage());
		
		player.sendMessage(ChatColor.DARK_RED + "You have died! Better luck next time!");
		
	}
	
}
