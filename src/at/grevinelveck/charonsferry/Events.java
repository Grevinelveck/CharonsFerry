package at.grevinelveck.charonsferry;

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

import at.grevinelveck.charonsferry.functions.Ressurection;

public class Events implements Listener {
	FileConfiguration config = CharonsFerry.plugin.getConfig();
	Ressurection grantLife = new Ressurection(null, null);
	
	@EventHandler
	public void noHunger(FoodLevelChangeEvent event){
		if (event.getEntity() instanceof Player) {
			Player target = (Player) event.getEntity();
			String player = target.getName();
			if (config.getBoolean("player." + player + ".alive") == false) {
				event.setCancelled(true);
		}
		}
	}

	@EventHandler
	public void breakDeath(BlockBreakEvent event) {

		final String player = event.getPlayer().getName();
		final Player God = event.getPlayer();
		if ((config.getBoolean("player." + player + ".alive") == false)
				&& (!God.hasPermission("CharonsFerry.Poltergeist"))) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void placeDeath(BlockPlaceEvent event) {
		final String player = event.getPlayer().getName();
		final Player God = event.getPlayer();
		if ((config.getBoolean("player." + player + ".alive") == false)
				&& (!God.hasPermission("CharonsFerry.Poltergeist"))) {
			event.setCancelled(true);
		}
	}

	// broken
	@EventHandler
	public void noDamage(EntityDamageByBlockEvent event) {
		if (event.getEntity() instanceof Player) {
			Player target = (Player) event.getEntity();
			String player = target.getName();
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
			if (config.getBoolean("player." + player + ".alive") == false) {
				event.setCancelled(true);
			}
		}
	}

	// Broken
	@EventHandler
	public void noFollow(EntityTargetLivingEntityEvent event) {
		if (event.getTarget() instanceof Player) {
			Player target = (Player) event.getTarget();
			String player = target.getName();
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
		final Player God = event.getPlayer();
		if ((config.getBoolean("player." + player + ".alive") == false)
				&& (!God.hasPermission("CharonsFerry.Poltergeist"))) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void noTouch(PlayerInteractEvent event) {
		final String player = event.getPlayer().getName();
		final Player God = event.getPlayer();
		if ((config.getBoolean("player." + player + ".alive") == false)
				&& (!God.hasPermission("CharonsFerry.Poltergeist"))) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void noTouchEver(PlayerInteractEntityEvent event) {
		final String player = event.getPlayer().getName();
		final Player God = event.getPlayer();
		if ((config.getBoolean("player." + player + ".alive") == false)
				&& (!God.hasPermission("CharonsFerry.Poltergeist"))) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onWelcome(PlayerJoinEvent event) {
		final String player = event.getPlayer().getName();

		if (!config.contains("player." + player)) {
			config.addDefault("player." + player + ".alive", true);
			config.addDefault("player." + player + ".revive", false);
			CharonsFerry.plugin.saveConfig();
		}

		if ((config.getBoolean("player." + player + ".revive") == true)
				&& (config.getBoolean("player." + player + ".alive") == false)) {
			config.set("player." + player + ".alive", true);
			config.set("player." + player + ".revive", false);
			CharonsFerry.plugin.saveConfig();
		}
		if ((config.getBoolean("player." + player + ".revive") == false)
				&& (config.getBoolean("player." + player + ".alive") == false)) {
			final Player target=event.getPlayer();
			config.set("player." + player + ".alive", true);
			config.set("player." + player + ".revive", false);
			CharonsFerry.plugin.saveConfig();
			for (Player players : Bukkit.getOnlinePlayers())
		    {
		    players.hidePlayer(target);
		    }
		}
	}

	@EventHandler
	public void noGet(PlayerPickupItemEvent event) {
		final String player = event.getPlayer().getName();

		if (config.getBoolean("player." + player + ".alive") == false) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onGhostHood(PlayerRespawnEvent event) {
		final String player = event.getPlayer().getName();

		if (config.getBoolean("player." + player + ".revive") == false) {
			final Player target=event.getPlayer();
			config.set("player." + player + ".alive", false);
			config.set("player." + player + ".revive", false);
			CharonsFerry.plugin.saveConfig();
			for (Player players : Bukkit.getOnlinePlayers())
		    {
		    players.hidePlayer(target);
		    }
			event.getPlayer()
					.sendMessage(
							ChatColor.RED
									+ "You are dead.  A friend will have to revive you.");
		}
		if (config.getBoolean("player." + player + ".revive") == true) {
			config.set("player." + player + ".alive", true);
			config.set("player." + player + ".revive", false);
			CharonsFerry.plugin.saveConfig();
			event.getPlayer().sendMessage(
					ChatColor.GOLD + "You have been revived");
		}

	}

	// broken
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		final Player placer = event.getPlayer();
		final String player = event.getLine(1);
		final Player target=Bukkit.getPlayer(player);

		if (event.getLine(0).equalsIgnoreCase("revive")) {
			Block blockunder = (event.getBlock().getWorld().getBlockAt(event
					.getBlock().getX(), event.getBlock().getY() - 1, event
					.getBlock().getZ()));
			Block block = (event.getBlock().getWorld().getBlockAt(event
					.getBlock().getX(), event.getBlock().getY() + 1, event
					.getBlock().getZ()));

			System.out.println(blockunder.getType() != Material.GOLD_BLOCK
					|| blockunder.getType() != Material.IRON_BLOCK
					|| blockunder.getType() != Material.DIAMOND_BLOCK);

			if (blockunder.getType() != Material.GOLD_BLOCK
					&& blockunder.getType() != Material.IRON_BLOCK
					&& blockunder.getType() != Material.DIAMOND_BLOCK) {
				return;
			} else {

				if (!config.contains("player." + player)) {
					placer.sendMessage(ChatColor.RED + "Player not found");
					return;
				}
				if ((config.getBoolean("player." + player + ".revive") == true)
						&& (config.getBoolean("player." + player + ".alive") == true)) {
					event.getPlayer().sendMessage(
							ChatColor.RED + "The gods frown apon greedy fools");

				}

				if ((config.getBoolean("player." + player + ".revive") == false)
						&& (config.getBoolean("player." + player + ".alive") == true)) {
					block.setType(Material.AIR);
					blockunder.setType(Material.FIRE);
					Location loc2 = event.getPlayer().getLocation();
					placer.getWorld().createExplosion(loc2, 0);
					event.getPlayer()
							.sendMessage(
									ChatColor.GOLD
											+ "The gods have accepted your sacrifice and will grant you a future boon");
					config.set("player." + player + ".alive", true);
					config.set("player." + player + ".revive", true);
					CharonsFerry.plugin.saveConfig();
				}
				if ((config.getBoolean("player." + player + ".revive") == false)
						&& (config.getBoolean("player." + player + ".alive") == false)) {
					block.setType(Material.AIR);
					blockunder.setType(Material.FIRE);
					Location loc = event.getPlayer().getLocation();
					placer.getWorld().createExplosion(loc, 0);
					event.getPlayer()
							.sendMessage(
									ChatColor.GOLD
											+ "The gods have accepted your sacrifice and will revive your fallen comrade");
					grantLife.life(player, target);
				}

			}
		}

	}
}
