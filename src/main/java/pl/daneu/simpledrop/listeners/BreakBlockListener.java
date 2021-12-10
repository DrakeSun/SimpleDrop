package pl.daneu.simpledrop.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.daneu.simpledrop.SimpleDrop;
import pl.daneu.simpledrop.objects.Drop;
import pl.daneu.simpledrop.objects.User;
import pl.daneu.simpledrop.utils.ItemBuilder;
import pl.daneu.simpledrop.utils.TextUtil;

import java.util.UUID;

public class BreakBlockListener implements Listener {

    private final SimpleDrop plugin;

    public BreakBlockListener(SimpleDrop plugin){ this.plugin = plugin; }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e){
        Block block = e.getBlock();
        Material mBlock = block.getType();

        if(!mBlock.equals(Material.STONE)) return;

        Player p = e.getPlayer();
        if(p.getGameMode().equals(GameMode.CREATIVE)) return;

        Material material = p.getItemInHand().getType();
        if(!material.equals(Material.WOODEN_PICKAXE) &&
                !material.equals(Material.STONE_PICKAXE) &&
                !material.equals(Material.GOLDEN_PICKAXE) &&
                !material.equals(Material.IRON_PICKAXE) &&
                !material.equals(Material.DIAMOND_PICKAXE) &&
                !material.equals(Material.NETHERITE_PICKAXE)) return;

        UUID uuid = p.getUniqueId();
        User user = plugin.usersManager.usersMap.get(uuid);
        if(!user.dropStone()) e.setDropItems(false);

        for(Drop drop : plugin.dropsManager.dropsList){
            if(!user.dropActiveStatusMap().get(drop.nameInConfig())) return;
            if(!drop.canDrop(p, getFortuneLevel(p))) return;

            int dropRandomAmount = drop.dropRandomAmount(getFortuneLevel(p));
            ItemStack itemToDrop = new ItemBuilder(drop.material())
                    .setAmount(dropRandomAmount)
                    .create();

            p.getWorld().dropItemNaturally(block.getLocation(), itemToDrop);
            p.sendMessage(TextUtil.getReplacedText(drop.dropMessage(), "%amount%", String.valueOf(dropRandomAmount)));
        }
    }

    private int getFortuneLevel(Player p){
        ItemStack item = p.getItemInHand();
        for(Enchantment enchant : item.getEnchantments().keySet()){
            if(!enchant.equals(Enchantment.LOOT_BONUS_BLOCKS)) continue;
            return item.getEnchantments().get(enchant);
        }
        return 0;
    }

}
