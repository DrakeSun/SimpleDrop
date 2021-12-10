package pl.daneu.simpledrop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.daneu.simpledrop.commands.DropCommand;
import pl.daneu.simpledrop.guis.DropGUI;
import pl.daneu.simpledrop.listeners.BreakBlockListener;
import pl.daneu.simpledrop.listeners.InventoryClickListener;
import pl.daneu.simpledrop.listeners.JoinListener;
import pl.daneu.simpledrop.managers.ConfigManager;
import pl.daneu.simpledrop.managers.DropsManager;
import pl.daneu.simpledrop.managers.UserManager;
import pl.daneu.simpledrop.objects.Drop;

public class SimpleDrop extends JavaPlugin {

    public CustomConfig dropsConfig;
    public CustomConfig usersConfig;
    public ConfigManager configManager;
    public DropsManager dropsManager;
    public UserManager usersManager;

    public DropGUI dropGUI;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        dropsConfig = new CustomConfig(this, "drops");
        usersConfig = new CustomConfig(this, "users");
        configManager = new ConfigManager(this);
        dropsManager = new DropsManager(this);
        usersManager = new UserManager(this);

        dropGUI = new DropGUI(this);

        getCommand("drop").setExecutor(new DropCommand(this));

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BreakBlockListener(this), this);
        pm.registerEvents(new JoinListener(this), this);
        pm.registerEvents(new InventoryClickListener(this), this);
    }

    @Override
    public void onDisable() {
        usersManager.saveAllUsers();
    }
}
