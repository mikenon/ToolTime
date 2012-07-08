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
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.item.GenericCustomTool;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.getspout.spoutapi.sound.SoundEffect;

public class ToolTimeEventListener implements Listener{

	public ToolTimeEventListener() { }
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		SpoutPlayer player = (SpoutPlayer)event.getPlayer();
		if (new SpoutItemStack(player.getItemInHand()).isCustomItem()) {
			SpoutItemStack stack = new SpoutItemStack(player.getItemInHand());
			int level = GenericCustomTool.getDurability(stack);
			
			if((level+1) > ToolTime.maxDur){
				playClang(player);
				if(stack.getAmount() > 1){
					SpoutItemStack newstack = new SpoutItemStack(ToolTime.tool, (stack.getAmount()-1));
					player.setItemInHand(newstack);
				} else {
					ItemStack newstack = new ItemStack(Material.AIR);
					player.setItemInHand(newstack);
				}
			} else {
				GenericCustomTool.setDurability(stack, (short) (level + 1));
				player.setItemInHand(stack);
				if(ToolTime.keepCount){
					player.sendMessage((level+1)+" of "+ToolTime.maxDur);
				}
			}
			
		}
	}
	
	private void playClang(SpoutPlayer player){
		SpoutManager.getSoundManager().playSoundEffect(player,
				SoundEffect.PIG_HURT, player.getLocation(), 12, 100);
	}
}