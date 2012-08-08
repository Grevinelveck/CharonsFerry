package at.grevinelveck.herosdeath;

import java.util.List;

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

	// Blockbreak event cancel if dead or if gravestone
	@EventHandler
	public void breakDeath(BlockBreakEvent event) {

		if (HerosDeath.plugin.getConfig().contains(
				event.getPlayer().getName() + ", dead"))
			;
		{
			event.setCancelled(true);
		}
	}

	// cancel block place if dead
	@EventHandler
	public void placeDeath(BlockPlaceEvent event) {
		if (HerosDeath.plugin.getConfig().contains(
				event.getPlayer().getName() + ", dead"))
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
			if (HerosDeath.plugin.getConfig().contains(player + ", dead"))
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
			if (HerosDeath.plugin.getConfig().contains(player + ", dead"))
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
			if (HerosDeath.plugin.getConfig().contains(player + ", dead"))
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
		if (HerosDeath.plugin.getConfig().contains(
				event.getPlayer().getName() + ", dead"))
			;
		{
			event.setCancelled(true);
		}
	}

	// prevent dead from interacting
	@EventHandler
	public void noTouch(PlayerInteractEvent event) {
		if (HerosDeath.plugin.getConfig().contains(
				event.getPlayer().getName() + ", dead"))
			;
		{
			event.setCancelled(true);
		}
	}

	// prevent interacting with entities
	@EventHandler
	public void noTouchEver(PlayerInteractEntityEvent event) {
		if (HerosDeath.plugin.getConfig().contains(
				event.getPlayer().getName() + ", dead"))
			;
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onWelcome(PlayerJoinEvent event) {
		String player = event.getPlayer().getName();
		if (HerosDeath.plugin.getConfig().contains("player." + player)) {
			HerosDeath.plugin.getConfig().set("player." + player + ".alive",
					true);
			HerosDeath.plugin.getConfig().set("player." + player + ".revive",
					false);
			HerosDeath.plugin.saveConfig();
		}
		if (HerosDeath.plugin.getConfig().contains(
				event.getPlayer().getName() + ", revive"))
			;
		event.getPlayer().sendMessage(ChatColor.GOLD + "You have been revived");
		List<String> myList = HerosDeath.plugin.getConfig().getStringList(
				"names");
		myList.add(player + ", alive");
		HerosDeath.plugin.getConfig().set("names", myList);
		HerosDeath.plugin.saveConfig();
	}

	// cancel pickup of items
	@EventHandler
	public void noGet(PlayerPickupItemEvent event) {
		if (HerosDeath.plugin.getConfig().contains(
				event.getPlayer().getName() + ", dead"))
			;
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onGhostHood(PlayerRespawnEvent event) {
		String player = event.getPlayer().getName();
		if (HerosDeath.plugin.getConfig().contains(
				event.getPlayer().getName() + ", revive"))
			;
		{
			event.getPlayer().sendMessage(
					ChatColor.GOLD + "You have been revived");
			List<String> myList = HerosDeath.plugin.getConfig().getStringList(
					"names");
			myList.add(player + ", alive");
			HerosDeath.plugin.getConfig().set("names", myList);

		}
		if (!HerosDeath.plugin.getConfig().contains(
				event.getPlayer().getName() + ", revive"))
			;
		{
			{
				List<String> myList = HerosDeath.plugin.getConfig()
						.getStringList("names");
				myList.add(player + ", dead");
				HerosDeath.plugin.getConfig().set("names", myList);
			}

		}
		HerosDeath.plugin.saveConfig();
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
			if (!HerosDeath.plugin.getConfig().contains(event.getLine(1))) {
				event.getPlayer().sendMessage(
						ChatColor.RED + "Player not found");
				return;
			}
			if (HerosDeath.plugin.getConfig().contains(
					event.getLine(1) + ", alive"))
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
			if (HerosDeath.plugin.getConfig().contains(
					event.getLine(1) + ", dead"))
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
				HerosDeath.plugin.getConfig().getStringList("names")
						.add(event.getLine(0) + ", alive");
				HerosDeath.plugin.saveConfig();

			}
			if (HerosDeath.plugin.getConfig().contains(
					event.getLine(1) + ", revive")) {
				event.getPlayer().sendMessage(
						ChatColor.GOLD + "The gods frown apon greedy fools");

			}

		}
	}
}
