package pl.daneu.simpledrop.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(Material material){ item = new ItemStack(material); }

    public ItemBuilder(Material material, Integer amount){ item = new ItemStack(material, amount); }

    public ItemBuilder(ItemStack item){ this.item = item; }

    public ItemStack create(){ return item; }

    public Material getMaterial(){ return item.getType(); }

    public int getAmount(){ return item.getAmount(); }

    public String getName(){ return item.getItemMeta().getDisplayName(); }

    public List<String> getLore(){ return  item.getItemMeta().getLore(); }

    public short getDurability(){ return  item.getDurability(); }

    public boolean isUnbreakable(){ return item.getItemMeta().isUnbreakable(); }

    public ItemBuilder setName(String name){
        ItemMeta meta = item.getItemMeta();
        name = ChatColor.translateAlternateColorCodes('&', name);
        meta.setDisplayName(name);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setAmount(Integer amount){
        item.setAmount(amount);

        return this;
    }

    public ItemBuilder setLore(List<String> lore){
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> newLore = new ArrayList<>();
        lore.forEach(text -> newLore.add(ChatColor.translateAlternateColorCodes('&', text)));
        meta.setLore(newLore);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setEnchantment(Enchantment enchant, int lvl){
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchant, lvl, true);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setDurability(Integer durability){
        item.setDurability(durability.shortValue());

        return this;
    }

    public ItemBuilder setInfDurability(){
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setFlag(ItemFlag flag){
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flag);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setAttribute(Attribute attribute, String attributeType, Double amount, AttributeModifier.Operation operation, EquipmentSlot eqSlot){
        ItemMeta meta = item.getItemMeta();
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), attributeType, amount , operation, eqSlot);
        meta.addAttributeModifier(attribute, modifier);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setSkullPlayerTexture(OfflinePlayer p){
        if(item.getType().equals(Material.PLAYER_HEAD)){
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwningPlayer(p);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setSkullTexture(String texture){
        if(item.getType().equals(Material.PLAYER_HEAD)){
            SkullMeta meta = (SkullMeta) item.getItemMeta();

            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            profile.getProperties().add(new ProfileProperty("textures", texture));

            meta.setPlayerProfile(profile);
            item.setItemMeta(meta);
        }
        return this;
    }
}