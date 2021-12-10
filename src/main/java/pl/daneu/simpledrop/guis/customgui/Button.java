package pl.daneu.simpledrop.guis.customgui;

import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class Button {

    private final boolean canBeClick;

    public Button(boolean canBeClick){
        this.canBeClick = canBeClick;
    }

    public boolean isCanBeClick(){ return canBeClick; }

    public abstract void execute(InventoryClickEvent e);
}
