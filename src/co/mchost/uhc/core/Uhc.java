package co.mchost.uhc.core;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import co.mchost.uhc.utils.GameStage;
import co.mchost.uhc.utils.Host;

public class Uhc {
	
	private Plugin plugin;
	
	private GameStage gameStage;
	
	private Host host;
	
	private ArrayList<Player> alivePlayers = new ArrayList<>();
	
	private WorldBorder border;
	
	/**
	 * Create an instance of UHC
	 * Should only be created in Main once
	 * 
	 * @param plugin This plugin
	 */
	public Uhc(Plugin plugin){
		
		this.plugin = plugin;
		
		this.gameStage = GameStage.WAITING_FOR_HOST;
		
		border = Bukkit.getWorld("world").getWorldBorder();
		
		border.setCenter(0, 0);
		
		border.setSize(1000);
		
		border.setDamageAmount(3);
		
	}
	
	/**
	 * Host a game of UHC
	 * 
	 * @param host Host that hosted the game
	 */
	public void hostGame(Host host){
		
		this.gameStage = GameStage.PRE_GAME;
		
		this.host = host;
		
		host.getPlayer().sendMessage(ChatColor.GREEN + "You have hosted the game! Type /start teleport players and start the game!");
		
		//TODO make the game publicly joinable
		
	}
	
	/**
	 * Kill specified player
	 * 
	 * @param player Player to be killed
	 */
	public void kill(Player player){
		
		this.setSpectator(player);
		
		if(this.alivePlayers.size() == 1){
			
			this.endGame();
			
		}
		
	}
	
	/**
	 * 
	 * Starts the game
	 * 
	 */
	public void startGame(){
		
		if(!this.host.equals(null)){
		
		if(Bukkit.getServer().getOnlinePlayers().size() >= 3){
			
			this.start();
			
		}else{
			
			this.host.getPlayer().sendMessage(ChatColor.RED + "Must have at least 3 players to start game");
			
		}
		
		}
		
	}
	
	/**
	 * Set the border's size
	 * 
	 * @param size The size you wish to set the border to
	 */
	public void setBorderSize(double size){
		
		border.setSize(size);
		
	}
	
	/**
	 * 
	 * @return The current game stage
	 */
	public GameStage getStage(){
		
		return this.gameStage;
		
	}
	
	/**
	 * 
	 * @return The world border
	 */
	public WorldBorder getBorder(){
		
		return border;
		
	}
	
	/**
	 * 
	 * @return All living players
	 */
	public ArrayList<Player> getAlivePlayers(){
		
		return this.alivePlayers;
		
	}
	
	/**
	 * 
	 * @return All spectating players (
	 */
	public ArrayList<Player> getSpectators(){
		
		ArrayList<Player> spectators = new ArrayList<>();
		
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			
			if(!this.alivePlayers.contains(player)){
				
				spectators.add(player);
				
			}
			
		}
		
		return spectators;
		
	}
	
	/**
	 * 
	 * @return The host of the UHC
	 */
	public Host getHost(){
		
		if(!gameStage.equals(GameStage.WAITING_FOR_HOST)){
		
			return this.host;
		
		}else{
			
			Bukkit.broadcastMessage(ChatColor.RED + "We are stil waiting for a host and therefore can not check the host");
			
			return null;
			
		}
		
	}
	
	/**
	 * 
	 * @return Plugin
	 */
	public Plugin getPlugin(){
		
		return this.plugin;
		
	}
	
	private void randomTeleport(Player player){
		
		Location location = new Location(Bukkit.getWorld("world"), 0, 0, 0);
		
		for(int i = 0; i < 2; i++){
		
			Random generator = new Random(); 
		
			int r = generator.nextInt(450) + -450;
			
			if(i == 0)location.setX(r);
			if(i == 1)location.setZ(r);
		
		}
		
		location.setY(location.getWorld().getHighestBlockYAt(location) + 1);
		
		if(location.clone().add(0, -1, 0).getBlock().isLiquid()){
			
			randomTeleport(player);
			
		}else{
			
			player.teleport(location);
			
		}
		
	}

	private void startBorderShrink(){
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable(){

			@Override
			public void run() {
				
				setBorderSize(getBorder().getSize() - 1);
				
			}
			
		}, 3*20, 3*20);
		
	}
	
	private void endGame(){
		
		this.gameStage = GameStage.POST_GAME;

		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.GOLD +""+ ChatColor.BOLD+ "Game over!");
		Bukkit.broadcastMessage(ChatColor.GREEN + "The winner of this game was " + ChatColor.GOLD + this.alivePlayers.get(0).getName() + ChatColor.GREEN + "! Server shutting down in 15 seconds!");
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){

			@Override
			public void run() {
			
				Bukkit.getServer().shutdown();
				
			}
			
		}, 20*15);
		
	}
	
	private void start(){
				
		this.startBorderShrink();
		
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "UHC");
		Bukkit.broadcastMessage(ChatColor.GREEN + "In this free for all last man standing game mode you must gather resources, kill other players, and most importantly survive. Health does not regenerate unless you make a golden apple which is crafted with a head between gold ingots!");
		Bukkit.broadcastMessage("");
		
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			
			if(!player.equals(this.host.getPlayer())){
			
				startGameForPlayer(player);
			
			}else{
				
				setSpectator(player);
				
			}
			
		}
		
		this.gameStage = GameStage.IN_GAME;
		
	}
	
	private void startGameForPlayer(Player player){
		
		this.alivePlayers.add(player);
		
		player.setGameMode(GameMode.SURVIVAL);
		
		player.setFoodLevel(20);
		
		player.setHealth(20);
		
		player.getInventory().clear();
		
		player.getInventory().setHelmet(null);
		 
		player.getInventory().setChestplate(null);
		    
		player.getInventory().setLeggings(null);
		    
		player.getInventory().setBoots(null);
		
		player.getActivePotionEffects().clear();
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*15, 5));
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*15, 100));
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*10*60, 0));
		
		player.setAllowFlight(false);
		
		this.randomTeleport(player);
		
	}
	
	private void setSpectator(Player player){
		
		if(this.alivePlayers.contains(player))this.alivePlayers.remove(player);
				
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			
			if(!p.equals(player)){
			
				p.hidePlayer(player);
			
			}
			
		}
		
		player.setGameMode(GameMode.SPECTATOR);
		
		player.setFoodLevel(20);
		
		player.setHealth(20);
		
		player.getInventory().clear();
		
		player.getInventory().setHelmet(null);
		 
		player.getInventory().setChestplate(null);
		    
		player.getInventory().setLeggings(null);
		    
		player.getInventory().setBoots(null);
		
		player.getActivePotionEffects().clear();
						
	}
	
	
	
}
