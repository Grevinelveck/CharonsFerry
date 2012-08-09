package at.grevinelveck.herosdeath;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

	@EventHandler
	public void breakDeath(BlockBreakEvent event) {

		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void placeDeath(BlockPlaceEvent event) {
		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

	// broken
	@EventHandler
	public void noDamage(EntityDamageByBlockEvent event) {
		if (event.getEntity() instanceof Player) {
			Player target = (Player) event.getEntity();
			String player = target.getName();
			System.out.println(player);
			if (config.getBoolean("player." + player + ".alive") == false) {
				event.setCancelled(true);
			}
		}
	}

	// broken
	@EventHandler
	public void noPain(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player target = (Player) event.getEntity();
			String player = target.getName();
			System.out.println(player);
			if (config.getBoolean("player." + player + ".alive") == false) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void noGain(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player target = (Player) event.getDamager();
			String player = target.getName();
			System.out.println(player);
			if (config.getBoolean("player." + player + ".alive") == false) {
				event.setCancelled(true);
			}
		}
	}

	// Broken
	@EventHandler
	public void noFollow(EntityTargetLivingEntityEvent event) {
		if (event.getTarget() instanceof Player)
			;
		{
			Player target = (Player) event.getTarget();
			String player = target.getName();
			System.out.println(player);
			if (config.getBoolean("player." + player + ".alive") == false) {
				event.setCancelled(true);

			}
		}
	}

	@EventHandler
	public void whenDead(PlayerDeathEvent event) {
		Player p = event.getEntity();
		String player = p.getName();
		Block block = p.getWorld().getBlockAt(p.getLocation());
		if (!block.isEmpty()
				&& !(block.getType().getId() == Material.SNOW.getId())) {
			return;
		}
		block.setTypeId(63);
		Sign s = (Sign) block.getState();
		s.setLine(0, "R.I.P");
		s.setLine(1, "");
		s.setLine(2, "Here Lies");
		s.setLine(3, player);
		s.update();
	}

	@EventHandler
	public void noTrace(PlayerDropItemEvent event) {
		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void noTouch(PlayerInteractEvent event) {
		final String player = event.getPlayer().getName();
		System.out.println(player);
		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

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

	// broken
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		final String player = event.getPlayer().getName();
		final Player placer = event.getPlayer();
		final String player2 = event.getLine(1);
		System.out.println(player);
		if (event.getLine(0).equalsIgnoreCase("revive")) {
			Block blockunder = (event.getBlock().getWorld().getBlockAt(event
					.getBlock().getX(), event.getBlock().getY() - 1, event
					.getBlock().getZ()));
			System.out.println("Gotblockunder");
			Block block = (event.getBlock().getWorld().getBlockAt(event
					.getBlock().getX(), event.getBlock().getY() + 1, event
					.getBlock().getZ()));
			System.out.println("Gotblockunder");

			System.out.println(blockunder.getType() != Material.GOLD_BLOCK
					|| blockunder.getType() != Material.IRON_BLOCK
					|| blockunder.getType() != Material.DIAMOND_BLOCK);
			if (blockunder.getType() != Material.GOLD_BLOCK
					|| blockunder.getType() != Material.IRON_BLOCK
					|| blockunder.getType() != Material.DIAMOND_BLOCK) {
				System.out.println("Is the placed block a gravestone");
				return;
			}else{
				System.out.println("check if line1 is a players name");
				System.out.println(player2);
				if (config.contains("player." + player2)) {
					placer.sendMessage(ChatColor.RED + "Player not found");
					return;
				}
				System.out
						.println("Check if player is alive and has no revive");
				if ((config.getBoolean("player." + player + ".revive") == false)
						&& (config.getBoolean("player." + player + ".alive") == true)) {
					blockunder.setType(Material.AIR);
					block.setType(Material.WATER);
					block.setType(Material.AIR);
					event.getPlayer()
							.sendMessage(
									ChatColor.GOLD
											+ "The gods have accepted your sacrifice and will grant you a future boon");
				}
				System.out.println("If player is dead with no revive");
				if ((config.getBoolean("player." + player + ".revive") == false)
						&& (config.getBoolean("player." + player + ".alive") == false)) {

					blockunder.setType(Material.AIR);
					block.setType(Material.WATER);
					block.setType(Material.AIR);
					event.getPlayer()
							.sendMessage(
									ChatColor.GOLD
											+ "The gods have accepted your sacrifice and will revive your fallen comrade");
					Bukkit.getServer()
							.getPlayer(event.getLine(1))
							.sendMessage(
									ChatColor.GOLD + "You have been revived");
					config.set("player." + player + ".alive", true);
					config.set("player." + player + ".revive", false);
					HerosDeath.plugin.saveConfig();

				}
				System.out.println("if player is alive and primed to revive");
				if ((config.getBoolean("player." + player + ".revive") == true)
						&& (config.getBoolean("player." + player + ".alive") == true)) {
					event.getPlayer().sendMessage(
							ChatColor.RED + "The gods frown apon greedy fools");

				}
				System.out.println("1");

			}
		System.out.println("2");
			}

	}
}


