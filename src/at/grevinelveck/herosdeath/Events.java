package at.grevinelveck.herosdeath;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class Events implements Listener {
	public HerosDeath plugin;

	// Blockbreak event cancel if dead or if gravestone
	@EventHandler
	public void breakDeath(BlockBreakEvent event) {
		if (plugin.getConfig().contains(event.getPlayer().getName() + ", dead"))
			;
		{
			event.setCancelled(true);
		}
	}

	// cancel block place if dead
	@EventHandler
	public void placeDeath(BlockPlaceEvent event) {
		if (plugin.getConfig().contains(event.getPlayer().getName() + ", dead"))
			;
		{
			event.setCancelled(true);
		}
	}

	// cancel entity damage if dead
	@EventHandler
	public void noBreathing(EntityDamageByBlockEvent event) {
		if (event.getEntity() instanceof Player) {
			String player = ((Player) event.getEntity()).getName();
			if (plugin.getConfig().contains(player + ", dead"))
				;
			{
				event.setCancelled(true);
			}
		}
	}

	// cancel more damage
	@EventHandler
	public void noPain(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			String player = ((Player) event.getEntity()).getName();
			if (plugin.getConfig().contains(player + ", dead"))
				;
			{
				event.setCancelled(true);
			}
		}
	}

	// Prevent mobs from chasing dead players
	@EventHandler
	public void noFollow(EntityTargetEvent event) {
		if (event.getEntity() instanceof Player) {
			String player = ((Player) event.getEntity()).getName();
			if (plugin.getConfig().contains(player + ", dead"))
				;
			{
				event.setCancelled(true);
			}
		}
	}

	// On death event
	@EventHandler
	public void whenDead(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Block block = player.getWorld().getBlockAt(player.getLocation());
		if (!block.isEmpty()
				&& !(block.getType().getId() == Material.SNOW.getId())) {
			block.setTypeId(63);
			Sign s = (Sign) block.getState();
			s.setLine(0, "R.I.P");
			s.setLine(1, "");
			s.setLine(2, "Here Lies");
			s.setLine(3, player.getName());
			s.update();
			return;

		}
	}

	// Prevent dead from dropping items
	@EventHandler
	public void noTrace(PlayerDropItemEvent event) {
		if (plugin.getConfig().contains(event.getPlayer().getName() + ", dead"))
			;
		{
			event.setCancelled(true);
		}
	}

	// prevent dead from interacting
	@EventHandler
	public void noTouch(PlayerInteractEvent event) {
		if (plugin.getConfig().contains(event.getPlayer().getName() + ", dead"))
			;
		{
			event.setCancelled(true);
		}
	}

	// prevent interacting with entities
	@EventHandler
	public void noTouchEver(PlayerInteractEntityEvent event) {
		if (plugin.getConfig().contains(event.getPlayer().getName() + ", dead"))
			;
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onWelcome(PlayerJoinEvent event) {
		if (!plugin.getConfig().contains(event.getPlayer().getName()))
			;
		{
			plugin.getConfig().getStringList("names")
					.add(event.getPlayer().getName() + ", alive");
			plugin.saveConfig();
		}
		if (plugin.getConfig().contains(
				event.getPlayer().getName() + ", revive"))
			;
		event.getPlayer().sendMessage(ChatColor.GOLD + "You have been revived");
		plugin.getConfig().getStringList("names")
				.add(event.getPlayer().getName() + ", alive");
		plugin.saveConfig();
	}

	// cancel pickup of items
	@EventHandler
	public void noGet(PlayerPickupItemEvent event) {
		if (plugin.getConfig().contains(event.getPlayer().getName() + ", dead"))
			;
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onGhostHood(PlayerRespawnEvent event) {
		if (plugin.getConfig().contains(
				event.getPlayer().getName() + ", revive"))
			;
		{
			event.getPlayer().sendMessage(
					ChatColor.GOLD + "You have been revived");
			plugin.getConfig().getStringList("names")
					.add(event.getPlayer().getName() + ", alive");
			plugin.saveConfig();
		}
		if (!plugin.getConfig().contains(
				event.getPlayer().getName() + ", revive"))
			;
		{
			{
				plugin.getConfig().getStringList("names")
						.add(event.getPlayer().getName() + ", dead");
			}
		}
	}

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if (event.getLine(0).equalsIgnoreCase("revive")) {
			Block blockunder = (event.getBlock().getWorld().getBlockAt(event
					.getBlock().getX(), event.getBlock().getY() - 1, event
					.getBlock().getZ()));
			Block block = (event.getBlock().getWorld().getBlockAt(event
					.getBlock().getX(), event.getBlock().getY() - 1, event
					.getBlock().getZ()));
			Location lock = (Location) blockunder;
			Location lock2 = (Location) block;

			System.out
					.println((blockunder.getType() != Material.GOLD_BLOCK && blockunder
							.getType() != Material.IRON_BLOCK));
			if (blockunder.getType() != Material.GOLD_BLOCK
					|| blockunder.getType() != Material.IRON_BLOCK
					|| blockunder.getType() != Material.DIAMOND_BLOCK) {
				return;
			}
			// Check if player exist in the database
			if (!plugin.getConfig().contains(event.getLine(1))) {
				event.getPlayer().sendMessage(
						ChatColor.RED + "Player not found");
				return;
			}
			if (plugin.getConfig().contains(event.getLine(1) + ", alive"))
				;
			{
				Player player = event.getPlayer();
				// Remove blocks
				player.getPlayer().getWorld().getBlockAt(lock2)
						.setType(Material.AIR);
				player.getPlayer().getWorld().getBlockAt(lock)
						.setType(Material.AIR);
				player.getPlayer().getWorld().strikeLightning(lock2);
				player.getPlayer().getWorld().getBlockAt(lock2)
						.setType(Material.AIR);
				event.getPlayer()
						.sendMessage(
								ChatColor.GOLD
										+ "The gods have accepted your sacrifice and will grant you a future boon");
			}
			if (plugin.getConfig().contains(event.getLine(1) + ", dead"))
				;
			{
				Player player = event.getPlayer();
				// Remove blocks
				player.getPlayer().getWorld().getBlockAt(lock2)
						.setType(Material.AIR);
				player.getPlayer().getWorld().getBlockAt(lock)
						.setType(Material.AIR);
				player.getPlayer().getWorld().strikeLightning(lock2);
				player.getPlayer().getWorld().getBlockAt(lock2)
						.setType(Material.AIR);
				event.getPlayer()
						.sendMessage(
								ChatColor.RED
										+ "The gods have accepted your sacrifice and will breath new life into your fallen comrade.");
				Bukkit.getServer().getPlayer(event.getLine(1))
						.sendMessage(ChatColor.GOLD + "You have been revived");
				plugin.getConfig().getStringList("names")
						.add(event.getLine(0) + ", alive");
				plugin.saveConfig();

			}
			if (plugin.getConfig().contains(event.getLine(1) + ", revive")) {
				event.getPlayer().sendMessage(
						ChatColor.GOLD + "The gods frown apon greedy fools");

			}

		}
	}
}
