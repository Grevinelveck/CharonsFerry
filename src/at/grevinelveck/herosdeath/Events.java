package at.grevinelveck.herosdeath;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class Events implements Listener {
	FileConfiguration config = HerosDeath.plugin.getConfig();

	// Blockbreak event cancel if dead or if gravestone
	@EventHandler
	public void breakDeath(BlockBreakEvent event) {
		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

	// cancel block place if dead
	@EventHandler
	public void placeDeath(BlockPlaceEvent event) {
		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

	// cancel entity damage if dead
	@EventHandler
	public void noBreathing(EntityDamageByBlockEvent event) {
		String player = ((Player) event.getEntity()).getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
		}
	}

	// cancel more damage
	@EventHandler
	public void noPain(EntityDamageByEntityEvent event) {
		String player = ((Player) event.getEntity()).getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

	// Prevent mobs from chasing dead players
	@EventHandler
	public void noFollow(EntityTargetEvent event) {
		String player = ((Player) event.getEntity()).getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			;
		}
	}

	// On death event
	// Get playername to be called and used appropreatly
	@EventHandler
	public void whenDead(PlayerDeathEvent event) {
		Player p = event.getEntity();
		String player = p.getName();
		Block block = p.getWorld().getBlockAt(p.getLocation());
		if (!block.isEmpty()
				&& !(block.getType().getId() == Material.SNOW.getId())) {
			block.setTypeId(63);
			return;
		}
		Sign s = (Sign) block.getState();
		s.setLine(0, "R.I.P");
		s.setLine(1, "");
		s.setLine(2, "Here Lies");
		s.setLine(3, player);
		s.update();
	}

	// Prevent dead from dropping items
	@EventHandler
	public void noTrace(PlayerDropItemEvent event) {
		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

	// prevent dead from interacting
	@EventHandler
	public void noTouch(PlayerInteractEvent event) {
		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

	// prevent interacting with entities
	@EventHandler
	public void noTouchEver(PlayerInteractEntityEvent event) {
		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onWelcome(PlayerJoinEvent event) {
		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (!config.contains("player." + player)) {
			config.addDefault("player." + player + ".alive", true);
			config.addDefault("player." + player + ".revive", false);
			HerosDeath.plugin.saveConfig();
		}
		if ((config.getBoolean("player." + player + ".revive") == true)
				&& (config.getBoolean("player." + player + ".alive") == false)) {
			config.set("player." + player + ".alive", true);
			config.set("player." + player + ".revive", false);
			HerosDeath.plugin.saveConfig();
		}
	}

	// cancel pickup of items
	@EventHandler
	public void noGet(PlayerPickupItemEvent event) {
		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onGhostHood(PlayerRespawnEvent event) {
		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".revive") == true) {
			config.set("player." + player + ".alive", true);
			config.set("player." + player + ".revive", false);
			HerosDeath.plugin.saveConfig();
			event.getPlayer().sendMessage(
					ChatColor.GOLD + "You have been revived");
		}
		if (config.getBoolean("player." + player + ".revive") == false) {
			config.set("player." + player + ".alive", false);
			config.set("player." + player + ".revive", false);
			HerosDeath.plugin.saveConfig();
			event.getPlayer()
					.sendMessage(
							ChatColor.RED
									+ "You are dead.  A friend will have to revive you.");
		}

	}

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		final Player player = event.getPlayer();
		System.out.println(player);
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
			final String player2 = event.getLine(1);
			System.out.println(player2);
			if (config.contains("player." + player2)) {
				player.sendMessage(ChatColor.RED + "Player not found");
				return;
			}
			if ((config.getBoolean("player." + player + ".revive") == true)
					&& (config.getBoolean("player." + player + ".alive") == true)) {
				;
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
			if ((config.getBoolean("player." + player + ".revive") == true)
					&& (config.getBoolean("player." + player + ".alive") == false)) {

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
										+ "The gods have accepted your sacrifice and will breath new life into your fallen comrade.");
				Bukkit.getServer().getPlayer(event.getLine(1))
						.sendMessage(ChatColor.GOLD + "You have been revived");
				config.set("player." + player + ".alive", true);
				config.set("player." + player + ".revive", false);
				HerosDeath.plugin.saveConfig();

			}
			if ((config.getBoolean("player." + player + ".revive") == true)
					&& (config.getBoolean("player." + player + ".alive") == true)) {
				event.getPlayer().sendMessage(
						ChatColor.RED + "The gods frown apon greedy fools");

			}

		}
	}
}
