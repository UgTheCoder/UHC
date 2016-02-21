package co.mchost.uhc.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import co.mchost.uhc.cancelledEvents.CancelledEvents;
import co.mchost.uhc.cancelledEvents.OtherEvents;
import co.mchost.uhc.commands.hostCommands;

public class Main extends JavaPlugin {

	public static Uhc uhc;

	@Override
	public void onEnable() {

		uhc = new Uhc(this);
		
		PluginManager manager = Bukkit.getServer().getPluginManager();
		
		manager.registerEvents(new CancelledEvents(), this);
		
		manager.registerEvents(new OtherEvents(), this);
		
		this.getCommand("host").setExecutor(new hostCommands());
		
		this.getCommand("start").setExecutor(new hostCommands());
		

	}

	public void setFinalStatic(Field field, Object obj) throws Exception {
		
		field.setAccessible(true);

		Field mf = Field.class.getDeclaredField("modifiers");
		
		mf.setAccessible(true);
		
		mf.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, obj);
		
	}

}
