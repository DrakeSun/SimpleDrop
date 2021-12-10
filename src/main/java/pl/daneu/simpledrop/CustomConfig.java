package pl.daneu.simpledrop;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    private final File file;
    private FileConfiguration fileConfiguration;

    public CustomConfig(SimpleDrop plugin, String name){
        if(!plugin.getDataFolder().exists()){ plugin.getDataFolder().mkdir(); }

        file = new File(plugin.getDataFolder(), name + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();
                plugin.saveResource(name + ".yml",  true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration get(){ return fileConfiguration; }

    public void reload(){ fileConfiguration = YamlConfiguration.loadConfiguration(file); }

    public void save(){
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
