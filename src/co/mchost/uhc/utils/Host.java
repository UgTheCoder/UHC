package co.mchost.uhc.utils;

import org.bukkit.entity.Player;

public class Host {

	private Player player;
	
	public Host(Player player){
		
		this.player = player;
		
	}
	
	public Player getPlayer(){
		
		return this.player;
		
	}
	
}
