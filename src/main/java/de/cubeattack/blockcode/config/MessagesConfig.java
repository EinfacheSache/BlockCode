package de.cubeattack.blockcode.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessagesConfig {

    public static File file = new File("plugins/BlockCode", "messages.yml");
    public static FileConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(MessagesConfig.file);
    public static String prefix = "§7[&bBlockCode&7] ";
    public static String no_permission = "§cDazu hast du keine Rechte";
    public static String unknown_command = "&cCommand nicht gefunden";

    public static void load() {
        MessagesConfig.prefix = fixColorCodes(setObject("Messages.Prefix", MessagesConfig.prefix));
        MessagesConfig.no_permission = fixColorCodes(setObject("Messages.Error.No-Permission", MessagesConfig.no_permission));
        MessagesConfig.unknown_command = fixColorCodes(setObject("Messages.Error.Unknown-Command", MessagesConfig.unknown_command));
    }

    public static String fixColorCodes(String code) {
        code = code.replace("&", "§");
        return code;
    }

    public static String setObject(String path, String obj) {
        if (MessagesConfig.yamlConfiguration.contains(path)) {
            return MessagesConfig.yamlConfiguration.getString(path);
        }
        MessagesConfig.yamlConfiguration.set(path, (Object)obj);
        save();
        return obj;
    }

    public static void save() {
        try {
            MessagesConfig.yamlConfiguration.save(MessagesConfig.file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
