package co.mchost.uhc.cancelledEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import co.mchost.uhc.core.Main;
import co.mchost.uhc.utils.GameStage;

public class CancelledEvents implements Listener {

	@EventHandler
	public void onChange(FoodLevelChangeEvent e){
		
		if(e.getEntity() instanceof Player){
		
			if(Main.uhc.getStage().equals(GameStage.WAITING_FOR_HOST) || Main.uhc.getStage().equals(GameStage.PRE_GAME) || Main.uhc.getSpectators().contains(e.getEntity())){
			
				Player p = (Player) e.getEntity();

				p.setFoodLevel(20);
				
				e.setCancelled(true);
			
			}
		
		}
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		
		if(e.getEntity() instanceof Player){
		
			if(Main.uhc.getStage().equals(GameStage.WAITING_FOR_HOST) || Main.uhc.getStage().equals(GameStage.PRE_GAME) || Main.uhc.getSpectators().contains(e.getEntity())){
			
				e.setCancelled(true);
			
			}
		
		}
		
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e){
		
		if(!Main.uhc.getStage().equals(GameStage.IN_GAME)){

			e.setCancelled(true);
			
		}
		
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		
		if(!Main.uhc.getStage().equals(GameStage.IN_GAME) && !Main.uhc.getStage().equals(GameStage.POST_GAME)){
			
			e.setCancelled(true);
		
		}
		
	}
	
	@EventHandler
	public void onPick(PlayerPickupItemEvent e){
		
		if(!Main.uhc.getStage().equals(GameStage.IN_GAME) && !Main.uhc.getStage().equals(GameStage.POST_GAME)){
			
			e.setCancelled(true);
		
		}
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		
		if(!Main.uhc.getStage().equals(GameStage.IN_GAME)){
			
			e.setCancelled(true);
		
		}
		
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		
		if(!Main.uhc.getStage().equals(GameStage.IN_GAME)){
			
			e.setCancelled(true);
		
		}
		
	}
	
	@EventHandler
	public void onRegen(EntityRegainHealthEvent e){
		
		if(e.getEntity() instanceof Player){
										
			if(!e.getRegainReason().equals(RegainReason.MAGIC_REGEN) && !e.getRegainReason().equals(RegainReason.MAGIC) && !e.getRegainReason().equals(RegainReason.CUSTOM)){
				
				e.setCancelled(true);
				
			}
			
		}
		
	}
	
}
