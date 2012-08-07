package at.grevinelveck.herosdeath;

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
	HerosDeath hd = new.HerosDeath();


// Blockbreak event cancel if dead or if gravestone
@EventHandler
public void breakDeath(BlockBreakEvent event) {
		event.setCancelled(true);
}

// cancel block place if dead
@EventHandler
public void placeDeath(BlockPlaceEvent event) {
		event.setCancelled(true);
	}

// cancel entity damage if dead
@EventHandler
public void noBreathing(EntityDamageByBlockEvent event) {
			event.setCancelled(true);
		}

// cancel more damage
@EventHandler
public void noPain(EntityDamageByEntityEvent event) {
			event.setCancelled(true);
}

// Prevent mobs from chasing dead players
@EventHandler
public void noFollow(EntityTargetEvent event) {
			event.setCancelled(true);
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
		event.setCancelled(true);
	}

//prevent dead from interacting
@EventHandler
public void noTouch(PlayerInteractEvent event) {
		event.setCancelled(true);
}

// prevent interacting with entities
@EventHandler
public void noTouchEver(PlayerInteractEntityEvent event) {
		event.setCancelled(true);
}

@EventHandler
public void onWelcome(PlayerJoinEvent event) {
	Player player = event.getPlayer();
	getConfig();
	config.getStringList("names").contains(player.getName());
}

// cancel pickup of items
@EventHandler
public void noGet(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}

@EventHandler
public void onGhostHood(PlayerRespawnEvent event) {
}

@EventHandler
public void onSignChange(SignChangeEvent event) {
}

//check if player is dead
public boolean isGhost(String player) {
	return true;
}

//check if player is revive
public boolean isRevive(String player) {
	return true;
}

//Set player alive and remove tombstone
public void reincarnation(String playername) {
}
}