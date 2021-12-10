package pl.daneu.simpledrop.managers;

import org.bukkit.configuration.file.FileConfiguration;
import pl.daneu.simpledrop.SimpleDrop;
import pl.daneu.simpledrop.utils.TextUtil;

import java.util.List;

public class ConfigManager {

    public final String GUI_DROPS_NAME;
    public final List<String> GUI_DROPS_LOREFOREACHGUI;

    public ConfigManager(SimpleDrop plugin){
        FileConfiguration config = plugin.getConfig();

        GUI_DROPS_NAME = TextUtil.getColoredText(config, "guis.drops.name");
        GUI_DROPS_LOREFOREACHGUI = TextUtil.getColoredList(config.getStringList("guis.drops.lore-for-each-drop"));

    }

}
