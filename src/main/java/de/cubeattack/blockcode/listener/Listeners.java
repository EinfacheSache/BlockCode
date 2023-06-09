package de.cubeattack.blockcode.listener;

import de.cubeattack.blockcode.commands.BlockCodeCommand;
import de.cubeattack.blockcode.config.MessagesConfig;
import de.cubeattack.blockcode.utils.PluginUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class Listeners implements Listener {
    @EventHandler
    public void on(final PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().getType() != Material.GOLDEN_AXE) {
            return;
        }
        if (e.getAction() == Action.LEFT_CLICK_BLOCK && p.hasPermission("blocktocode.locations")) {
            Block block = e.getClickedBlock();
            BlockCodeCommand.loc1.put(p, block.getLocation());
            if(!BlockCodeCommand.loc2.containsKey(p)){
                BlockCodeCommand.loc2.put(p, e.getClickedBlock().getLocation());
            }
            int amount = 0;
            Location loc2 = e.getClickedBlock().getLocation();
            for (Location loc : PluginUtils.getLocationsFromToLocations(BlockCodeCommand.loc1.get(p), BlockCodeCommand.loc2.get(p))) {
                amount ++;
            }
            p.sendMessage(MessagesConfig.prefix + "§6Location-1 §fset §7(" + block.getX() + ", "+ block.getY() + ", "+ block.getZ() + ") (" + amount + ")");
            e.setCancelled(true);
        }
        else if (e.getAction() == Action.RIGHT_CLICK_BLOCK && p.hasPermission("blocktocode.locations")) {
            if (e.getHand() == EquipmentSlot.HAND){
                if(p.getInventory().getItemInMainHand().getType() == Material.GOLDEN_AXE) {
                    Block block = e.getClickedBlock();
                    BlockCodeCommand.loc2.put(p, block.getLocation());
                    if (!BlockCodeCommand.loc1.containsKey(p)) {
                        BlockCodeCommand.loc1.put(p, e.getClickedBlock().getLocation());
                    }
                    int amount = 0;
                    Location loc1 = e.getClickedBlock().getLocation();
                    for (Location loc : PluginUtils.getLocationsFromToLocations(BlockCodeCommand.loc1.get(p), BlockCodeCommand.loc2.get(p))) {
                        amount++;
                    }
                    p.sendMessage(MessagesConfig.prefix + "§6Location-2 §fset §7(" + block.getX() + ", " + block.getY() + ", " + block.getZ() + ") (" + amount + ")");
                    p.setHealth(p.getHealth() - 1);
                    e.setCancelled(true);
                }
            }
        }
    }
}
