package pl.daneu.simpledrop.guis.customgui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class CustomGUI {

    private final Inventory inventory;
    private final String name;
    private boolean allItemsNonClickable;

    private final HashMap<Integer, Button> buttons;

    public CustomGUI(String name, int size){
        inventory = Bukkit.createInventory(null, size, Component.text(name));
        this.name = name;

        buttons = new HashMap<>();
    }

    public CustomGUI(String name, int size, boolean allItemsNonClickable){
        inventory = Bukkit.createInventory(null, size, Component.text(name));
        this.name = name;
        this.allItemsNonClickable = allItemsNonClickable;

        buttons = new HashMap<>();
    }

    public Button getButton(int slot){ return buttons.get(slot); }

    public Inventory getInventory(){ return inventory; }

    public CustomGUI setItem(int slot, ItemStack item){ inventory.setItem(slot, item); return this; }

    //Ustawianie buttonow
    public CustomGUI setButton(int slot, Button button){
        buttons.put(slot, button);

        return this;
    }

    //Register eventu w Listener
    public void registerEvent(InventoryClickEvent e){
        if(!e.getView().title().equals(Component.text(name))) return;

        ItemStack item = e.getCurrentItem();
        if(item == null) return;
        if(allItemsNonClickable) e.setCancelled(true);

        int slot = e.getSlot();
        Button button = getButton(slot);
        if(button == null) return;
        if(button.isCanBeClick()) e.setCancelled(false);

        button.execute(e);
    }

    //Tworzenie calego wygladu gui
    public abstract void open(Player p);
    public abstract void registerButtons();
}
