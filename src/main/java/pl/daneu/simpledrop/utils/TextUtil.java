package pl.daneu.simpledrop.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextUtil {

    public static String getColoredText(String text){ return ChatColor.translateAlternateColorCodes('&', text); }

    public static String getColoredText(FileConfiguration config, String path){ return getColoredText(config.getString(path)); }

    public static List<String> getColoredList(List<String> list){
        ArrayList<String> newList = new ArrayList<>();
        list.forEach(s -> newList.add(getColoredText(s)));

        return newList;
    }

    public static List<String> getColoredList(String... text){
        ArrayList<String> list = new ArrayList<>();
        Arrays.stream(text).forEach(s -> list.add(getColoredText(s)));

        return list;
    }

    public static String getReplacedText(String text, String replace, String replaceWith){
        return getColoredText(text.replaceAll(replace, replaceWith));
    }

    public static String getReplacedPlayer(String text, String replaceWith){
        return getColoredText(text.replaceAll("%player%", replaceWith));
    }

    public static void message(Player p, String text){
        p.sendMessage(getColoredText(text));
    }

    public static void broadcastMessage(String text){
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(getColoredText(text)));
    }

    public static void titleMessage(Player p, String title, String subTitle, int fadeIn, int stay, int fadeOut){
        title = getColoredText(title);
        subTitle = getColoredText(subTitle);
        p.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
    }
}