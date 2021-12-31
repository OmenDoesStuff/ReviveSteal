package net.dashsmp.revivesteal.listener;

import net.dashsmp.revivesteal.ReviveSteal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class PlayerListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(e.getEntity() instanceof HumanEntity) {
            HumanEntity human = ((HumanEntity) e.getEntity());
            if(human instanceof Player) {

                Player p = ((Player) human).getPlayer();

                /*ItemStack playerSkull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta playerSkullMetadata = (SkullMeta) playerSkull.getItemMeta();
                playerSkullMetadata.setOwningPlayer(p);
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.RED + p.getName() + "Was Killed By: ");
                if(e.getEntity().getKiller() == null || e.getEntity().getKiller().getName() == null || e.getEntity().getKiller().getPlayer() == null ) {
                    lore.add(ChatColor.RED + "The World.");
                } else {
                    lore.add(ChatColor.RED + e.getEntity().getKiller().getName());
                }
                playerSkullMetadata.setLore(lore);
                playerSkull.setItemMeta(playerSkullMetadata);
                e.getDrops().clear();
                e.getDrops().add(playerSkull);*/

                Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + p.getName() + " Has Died.");

                ReviveSteal.ghostPlayers.add(p.getUniqueId());

                p.spigot().respawn();
                p.getInventory().clear();
                p.teleport(ReviveSteal.getSpawn());
                p.sendMessage(ChatColor.RED + "You Died!");
                p.getPlayer().setGameMode(GameMode.ADVENTURE);
                p.getPlayer().setInvulnerable(true);
                p.getPlayer().setInvisible(true);
                p.getPlayer().setAllowFlight(true);
                p.setDisplayName(ChatColor.STRIKETHROUGH + p.getName());
                p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 32767));
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(ReviveSteal.ghostPlayers.contains(e.getPlayer().getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You Are Dead!");
            p.getPlayer().setGameMode(GameMode.ADVENTURE);
            p.getPlayer().setInvulnerable(true);
            p.getPlayer().setInvisible(true);
            p.getPlayer().setAllowFlight(true);
        } else {
            p.sendMessage(ChatColor.GOLD + "Welcome " + p.getName() + "!");
            p.getPlayer().setGameMode(GameMode.SURVIVAL);
            p.getPlayer().setInvulnerable(false);
            p.getPlayer().setInvisible(false);
            p.getPlayer().setAllowFlight(false);
        }
    }
}
