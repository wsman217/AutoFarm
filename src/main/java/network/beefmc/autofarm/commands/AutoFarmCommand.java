package network.beefmc.autofarm.commands;

import network.beefmc.autofarm.AutoFarm;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AutoFarmCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(ChatColor.RED + "This command is only meant for players.");
            return true;
        }

        //TODO probably should give a help message here.
        if (args.length < 1)
            return true;

        switch (args[0]) {
            case "wand" -> giveWand(p);
            case "create" -> create(p, args);
            default -> p.sendMessage(ChatColor.RED + "This is not a valid argument.");
        }
        return true;
    }

    private void giveWand(Player p) {
        if (!p.hasPermission("autofarm.wand")) {
            p.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return;
        }

        p.getInventory().addItem(network.beefmc.autofarm.AutoFarm.getWand());
        p.sendMessage(ChatColor.LIGHT_PURPLE + "AutoFarm Wand has been added to your inventory.");
    }

    private void create(Player p, String[] args) {
        if (!AutoFarm.selections.containsKey(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You do not have any selections.");
            return;
        }

        if (args.length < 2) {
            p.sendMessage(ChatColor.RED + "Please specify a plant type.");
            return;
        }

        //TODO Save selection and plant type to a file somewhere. Maybe add a time for how often they regen.
        ArrayList<Block> blocks = new ArrayList<>();

        AutoFarm.selections.get(p.getUniqueId()).selections.forEach(selection -> {
            Location firstCorner = selection.firstCorner, secondCorner = selection.secondCorner;
            int topX = secondCorner.getBlockX(), bottomX = firstCorner.getBlockX(),
                    topZ = secondCorner.getBlockZ(), bottomZ = firstCorner.getBlockZ();

            if (firstCorner.getBlockX() > secondCorner.getBlockX()) {
                topX = firstCorner.getBlockX();
                bottomX = secondCorner.getBlockX();
            }

            if (firstCorner.getBlockZ() > secondCorner.getBlockZ()) {
                topZ = firstCorner.getBlockZ();
                bottomZ = secondCorner.getBlockZ();
            }

            for (int x = bottomX; x <= topX; x++) {
                for (int z = bottomZ; z <= topZ; z++) {
                    World world = firstCorner.getWorld();
                    if (world != null)
                        blocks.add(world.getBlockAt(x, firstCorner.getBlockY(), z));
                }
            }
        });
    }
}
