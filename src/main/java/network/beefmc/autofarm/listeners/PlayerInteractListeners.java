package network.beefmc.autofarm.listeners;

import network.beefmc.autofarm.AutoFarm;
import network.beefmc.autofarm.AutoFarmSelection;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerInteractListeners implements Listener {

    private final HashMap<UUID, Location> firstCorners = new HashMap<>();

    @EventHandler
    public void onPlayerUseWand(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPermission("autofarm.select"))
            return;

        if (!p.getInventory().getItemInMainHand().isSimilar(AutoFarm.getWand()))
            return;

        if (!AutoFarm.selections.containsKey(p.getUniqueId()))
            AutoFarm.selections.put(p.getUniqueId(), new AutoFarmSelection());

        Block clickedBlock = e.getClickedBlock();
        if (clickedBlock == null)
            return;

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getAction() != Action.LEFT_CLICK_BLOCK)
            return;

        Location loc = clickedBlock.getLocation().add(0, 1, 0);

        if (!p.isSneaking() && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            AutoFarm.selections.get(p.getUniqueId()).addSelection(loc);
            p.sendMessage(ChatColor.LIGHT_PURPLE + "Selection added at: X=" + loc.getBlockX() + ", Y=" + loc.getBlockY() +
                    ", Z=" + loc.getBlockZ());
            return;
        }

        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            firstCorners.put(p.getUniqueId(), loc);
            p.sendMessage(ChatColor.LIGHT_PURPLE + "First corner set to: X=" + loc.getBlockX() + ", Y=" + loc.getBlockY() +
                    ", Z=" + loc.getBlockZ());
            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!firstCorners.containsKey(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED + "Please set a first corner before setting the second.");
                return;
            }

            if (loc.getBlockY() != firstCorners.get(p.getUniqueId()).getBlockY()) {
                p.sendMessage("First corner height is not the same as second corner height.");
                return;
            }

            AutoFarm.selections.get(p.getUniqueId()).addSelection(firstCorners.get(p.getUniqueId()), loc);
            p.sendMessage(ChatColor.LIGHT_PURPLE + "Second corner set to: X=" + loc.getBlockX() + ", Y=" + loc.getBlockY() +
                    ", Z=" + loc.getBlockZ());
        }
    }
}
