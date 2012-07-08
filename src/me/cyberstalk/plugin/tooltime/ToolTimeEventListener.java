/* This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://sam.zoy.org/wtfpl/COPYING for more details. */
package me.cyberstalk.plugin.tooltime;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutEnchantment;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.getspout.spoutapi.sound.SoundEffect;

public class ToolTimeEventListener implements Listener{

	public ToolTimeEventListener() {

	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		SpoutPlayer player = (SpoutPlayer)event.getPlayer();
		if (new SpoutItemStack(player.getItemInHand()).isCustomItem()) {
			int max = new SpoutItemStack(player.getItemInHand()).getEnchantmentLevel(SpoutEnchantment.MAX_DURABILITY);
			int level = new SpoutItemStack(player.getItemInHand()).getEnchantmentLevel(SpoutEnchantment.DURABILITY);
			level = level + 1;
			short lvl = (short) level;
			
			ItemStack stack;
			if(ToolTime.maxDur - lvl <= 0){
				stack = new ItemStack(Material.AIR);
				playClang(player);
			} else {
				stack = new SpoutItemStack(ToolTime.tool, 1);
				stack.addUnsafeEnchantment(SpoutEnchantment.MAX_DURABILITY, ToolTime.maxDur);
				stack.addUnsafeEnchantment(SpoutEnchantment.DURABILITY, lvl);
			}
			player.setItemInHand(stack);
			
			if(ToolTime.keepCount){
				player.sendMessage(lvl+" of "+max);
			}
		}
	}
	
	private void playClang(SpoutPlayer player){
		SpoutManager.getSoundManager().playSoundEffect(player,
				SoundEffect.PIG_HURT, player.getLocation(), 12, 100);
	}
}