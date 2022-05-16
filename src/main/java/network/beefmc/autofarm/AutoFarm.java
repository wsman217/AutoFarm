package network.beefmc.autofarm;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class AutoFarm extends JavaPlugin {

    private static final ItemStack wand = new ItemStack(Material.STICK);
    public static final HashMap<UUID, AutoFarmSelection> selections = new HashMap<>();
    public static AutoFarm instance;

    @Override
    public void onEnable() {
        instance = this;
        setupWand();
    }

    private void setupWand() {
        ItemMeta im = wand.getItemMeta();
        if (im == null)
            return;
        im.setDisplayName(ChatColor.LIGHT_PURPLE + "AutoFarm Wand");
        im.setLore(Arrays.asList(ChatColor.GOLD + "Right click to add a single block.",
                ChatColor.AQUA + "Shift Left click to set first corner.",
                ChatColor.AQUA + "Shift Right click to set second corner.",
                ChatColor.BLUE + "Run command /autofarm create <farm-type>"));
        im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        wand.setItemMeta(im);
    }

    public static ItemStack getWand() {
        return wand.clone();
    }
}
