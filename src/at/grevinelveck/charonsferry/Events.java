package at.grevinelveck.charonsferry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.grevinelveck.charonsferry.functions.Revive;

public class Events implements Listener {
	FileConfiguration config = CharonsFerry.plugin.getConfig();
	Revive grantLife = new Revive();
	private String Gravestone;

	@EventHandler
	public void noHunger(FoodLevelChangeEvent event) {
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
			event.getPlayer().sendMessage("You are dead.");
		}
	}

	@EventHandler
	public void placeDeath(BlockPlaceEvent event) {
		final String player = event.getPlayer().getName();
		final Player God = event.getPlayer();
		if ((config.getBoolean("player." + player + ".alive") == false)
				&& (!God.hasPermission("CharonsFerry.Poltergeist"))) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("You are dead.");
		}
	}


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
				target.sendMessage("You are dead.");
			}
		}
	}

	// Broken possibly only with monster apoc due to different targeting method?
	@EventHandler()
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
			event.getPlayer().sendMessage("You are dead.");
		}
	}

	@EventHandler
	public void noTouch(PlayerInteractEvent event) {
		final String player = event.getPlayer().getName();
		final Player God = event.getPlayer();
		if ((config.getBoolean("player." + player + ".alive") == false)
				&& (!God.hasPermission("CharonsFerry.Poltergeist"))) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("You are dead.");
		}
	}

	@EventHandler
	public void noTouchEver(PlayerInteractEntityEvent event) {
		final String player = event.getPlayer().getName();
		final Player God = event.getPlayer();
		if ((config.getBoolean("player." + player + ".alive") == false)
				&& (!God.hasPermission("CharonsFerry.Poltergeist"))) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("You are dead.");
		}
	}

	@EventHandler
	public void onWelcome(PlayerJoinEvent event) {
		final String player = event.getPlayer().getName();

		if (!config.contains("player." + player)) {
			config.addDefault("player." + player + ".alive", true);
			config.addDefault("player." + player + ".revive", false);
			config.set("player." + player + ".block", "Null");
			config.set("player." + player + ".time", 0);
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
			final Player target = event.getPlayer();
			for (Player players : Bukkit.getOnlinePlayers()) {
				players.hidePlayer(target);
			}
		
		}
		if ((config.getBoolean("player." + player + ".revive") == false)
				&& (config.getBoolean("player." + player + ".alive") == true)) {
		    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 0, 0), true);
		    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 0, 0), true);
		}
	}

	@EventHandler
	public void noGet(PlayerPickupItemEvent event) {
		final String player = event.getPlayer().getName();
		final Player God = event.getPlayer();
		if ((config.getBoolean("player." + player + ".alive") == false)
				&& (!God.hasPermission("CharonsFerry.Poltergeist"))) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void ghostlyTraces(PlayerMoveEvent event) {
		final Player God = event.getPlayer();
		final String player = event.getPlayer().getName();
		if ((config.getBoolean("player." + player + ".alive") == false)
				&& (!God.hasPermission("CharonsFerry.Poltergeist"))) {
			int temp=config.getInt("Minutes");
			int ticks=temp*1200;
			
	God.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, ticks, 1));
	God.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, ticks, 1));
	God.getWorld().playEffect(God.getLocation(), Effect.SMOKE, 2);
		}
	}
	
	@EventHandler
	public void onGhostHood(PlayerRespawnEvent event) {
		final String player = event.getPlayer().getName();
		final Player target = event.getPlayer();

		if (config.getBoolean("player." + player + ".revive") == false) {
			config.set("player." + player + ".alive", false);
			config.set("player." + player + ".time", +config.getInt("Minutes"));
			CharonsFerry.plugin.saveConfig();
			event.getPlayer()
			.sendMessage(
					ChatColor.RED
							+ "You are a ghost.  A friend will have to revive you.  A global revive happens every "
							+ config.getInt("Minutes") + " Minutes");
			for (Player players : Bukkit.getOnlinePlayers()) {
				players.hidePlayer(target);
			}
		}
		if (config.getBoolean("player." + player + ".revive") == true) {
			config.set("player." + player + ".alive", false);
			config.set("player." + player + ".revive", false);
			CharonsFerry.plugin.saveConfig();
			Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(CharonsFerry.plugin,
					new Runnable() {
						public void run() {
							grantLife.life(player, target);
						}
					}, 80L);

		}
	}

	// broken
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		final Player placer = event.getPlayer();
		final String player = event.getLine(1)+event.getLine(2);
		final Player target = Bukkit.getPlayer(player);

		if (event.getLine(0).equalsIgnoreCase("revive")) {
			Block blockunder = (event.getBlock().getWorld().getBlockAt(event
					.getBlock().getX(), event.getBlock().getY() - 1, event
					.getBlock().getZ()));
			Block block = (event.getBlock().getWorld().getBlockAt(event
					.getBlock().getX(), event.getBlock().getY() + 1, event
					.getBlock().getZ()));

			System.out.println(blockunder.getType() != Material.GOLD_BLOCK
					|| blockunder.getType() != Material.LAPIS_BLOCK
					|| blockunder.getType() != Material.IRON_BLOCK
					|| blockunder.getType() != Material.DIAMOND_BLOCK);

			if (blockunder.getType() != Material.GOLD_BLOCK
					&& blockunder.getType() != Material.LAPIS_BLOCK
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
							ChatColor.RED + "The gods frown upon greedy fools");

				}

				if ((config.getBoolean("player." + player + ".revive") == false)
						&& (config.getBoolean("player." + player + ".alive") == true)) {
					if (blockunder.getType()==Material.LAPIS_BLOCK){
						Gravestone="Lapis";
					}
					if (blockunder.getType()==Material.IRON_BLOCK){
						Gravestone="Iron";
					}
					if (blockunder.getType()==Material.GOLD_BLOCK){
						Gravestone="Gold";
					}
					if (blockunder.getType()==Material.DIAMOND_BLOCK){
						Gravestone="Diamond";
					}
					if (blockunder.getType()==Material.EMERALD_BLOCK){
						Gravestone="Emerald";
					}
					block.setType(Material.AIR);
					blockunder.setType(Material.FIRE);
					Location loc2 = event.getPlayer().getLocation();
					placer.getWorld().createExplosion(loc2, 0);
					event.getPlayer()
							.sendMessage(
									ChatColor.GOLD
											+ "The gods have accepted your sacrifice and will grant you a future boon");
					config.set("player." + player + ".revive", true);
					config.set("player." + player + ".block", Gravestone);
					CharonsFerry.plugin.saveConfig();
				}
				if ((config.getBoolean("player." + player + ".revive") == false)
						&& (config.getBoolean("player." + player + ".alive") == false)) {
					if (blockunder.getType()==Material.LAPIS_BLOCK){
						Gravestone="Lapis";
					}
					if (blockunder.getType()==Material.IRON_BLOCK){
						Gravestone="Iron";
					}
					if (blockunder.getType()==Material.GOLD_BLOCK){
						Gravestone="Gold";
					}
					if (blockunder.getType()==Material.DIAMOND_BLOCK){
						Gravestone="Diamond";
					}
					if (blockunder.getType()==Material.EMERALD_BLOCK){
						Gravestone="EMERALD";
					}
					block.setType(Material.AIR);
					blockunder.setType(Material.FIRE);
					Location loc = event.getPlayer().getLocation();
					placer.getWorld().createExplosion(loc, 0);
					event.getPlayer()
							.sendMessage(
									ChatColor.GOLD
											+ "The gods have accepted your sacrifice and will revive your fallen comrade");
					config.set("player." + player + ".block", Gravestone);
					CharonsFerry.plugin.saveConfig();
					grantLife.life(player, target);
				}

			}
		}

	}
}
