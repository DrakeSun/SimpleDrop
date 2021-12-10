package pl.daneu.simpledrop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.daneu.simpledrop.SimpleDrop;
import pl.daneu.simpledrop.guis.DropGUI;

public class DropCommand implements CommandExecutor {

    private final SimpleDrop plugin;

    public DropCommand(SimpleDrop plugin){ this.plugin = plugin; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!command.getName().equalsIgnoreCase("drop")) return false;
        if(!(sender instanceof Player p)) return false;

        DropGUI dropGUI = new DropGUI(plugin);
        dropGUI.open(p);
        return true;
    }
}
