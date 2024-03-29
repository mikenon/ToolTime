/* This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://sam.zoy.org/wtfpl/COPYING for more details. */
package me.cyberstalk.plugin.tooltime;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;

public class ToolTime extends JavaPlugin{
	
	public static YouTool tool;
	public static int maxDur = 16;
	public static boolean keepCount = false;
	
	public void onEnable() {
		tool = new YouTool(this, "Sad Tool", "http://dl.dropbox.com/u/79326363/Spout/ToolTime/sadtool.png");
		getServer().getPluginManager().registerEvents(new ToolTimeEventListener(),this);
		System.out.println("[ToolTime] v" + getDescription().getVersion() + " Enabled");
	}
	
	public void onDisable() {
		System.out.println("[ToolTime] v" + getDescription().getVersion() + " Disabled");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((!(sender instanceof Player))) {
			System.out.println("Silly console, you can't use tools");
			return true;
		}
		Player player = (Player)sender;
		switch (args.length) {
			case 0:
				player.sendMessage("Tim Taylor would not be proud");
				player.sendMessage("/tooltime give - gives you a tool");
				player.sendMessage("/tooltime max [number] - sets durability");
				player.sendMessage("/tooltime print [true|false] - enable, or disable, printing durability");
				return true;

			case 1:
				if(args[0].equalsIgnoreCase("give")) {
					SpoutItemStack stack = new SpoutItemStack(tool);
					player.getWorld().dropItemNaturally(player.getLocation(), stack); 
					return true;
				}
				break;
			case 2:
				if(args[0].equalsIgnoreCase("max")) {
					if(Integer.parseInt(args[1])>0){
						maxDur = Integer.parseInt(args[1]);
					}
					player.sendMessage("[ToolTime] max durability set to "+maxDur);
					return true;
				}
				if(args[0].equalsIgnoreCase("print")) {
					String pseubool = "disabled";
					if(Boolean.parseBoolean(args[1])){
						keepCount = true;
						pseubool = "enabled";
					} else {
						keepCount = false;
					}
					player.sendMessage("[ToolTime] printing "+pseubool);
					return true;
				}
		}
		return false;
	}
}
