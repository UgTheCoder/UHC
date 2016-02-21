package co.mchost.uhc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.mchost.uhc.core.Main;
import co.mchost.uhc.utils.GameStage;
import co.mchost.uhc.utils.Host;

public class hostCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		
		if(arg1.getName().equalsIgnoreCase("host")){
			
			if(arg0 instanceof Player){
			
				Player player = (Player) arg0;
				
				if(Main.uhc.getStage().equals(GameStage.WAITING_FOR_HOST)){
				
					Main.uhc.hostGame(new Host(player));
				
				}else{
					
					player.sendMessage(ChatColor.RED + "This game is already being hosted!");
					
				}
			
			}
						
		}
		
		if(arg1.getName().equalsIgnoreCase("start")){
			
			if(arg0 instanceof Player){
			
				Player player = (Player) arg0;
				
				if(Main.uhc.getHost().getPlayer().equals(player)){
					
					if(Main.uhc.getStage().equals(GameStage.PRE_GAME)){
						
						Main.uhc.startGame();
						
					}else{
						
						if(Main.uhc.getStage().equals(GameStage.WAITING_FOR_HOST)){
							
							player.sendMessage(ChatColor.RED + "You must host the game first! (" + ChatColor.YELLOW + "/host" + ChatColor.RED + ")");
							
						}else{
							
							player.sendMessage(ChatColor.RED + "The game has already begun!");
							
						}
						
					}
					
				}else{
					
					player.sendMessage(ChatColor.RED + "You are not the host and therefore can't start the game!");
					
				}
			
			}
						
		}
		
		return false;
	}

	
	
}
