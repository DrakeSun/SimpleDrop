package pl.daneu.simpledrop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.daneu.simpledrop.SimpleDrop;

public class InventoryClickListener implements Listener {

    private final SimpleDrop plugin;

    public InventoryClickListener(SimpleDrop plugin){ this.plugin = plugin; }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        plugin.dropGUI.registerEvent(e);
    }

}
