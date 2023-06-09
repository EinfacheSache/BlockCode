package de.cubeattack.blockcode.main;

import de.cubeattack.blockcode.config.MessagesConfig;
import de.cubeattack.blockcode.listener.Listeners;
import de.cubeattack.blockcode.commands.BlockCodeCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    private static Main plugin;

    public static File config = new File("plugins/AntiProxyKick_by_CubeAttack", "config.yml");

    @Override
    public void onEnable() {

        try {
            FileUtils.copyToFile(this.getResource("messages.yml"), new File("plugins/messages.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Main.plugin = this;
        MessagesConfig.load();
        Main.getPlugin().getCommand("blockcode").setExecutor(new BlockCodeCommand());
        Main.getPlugin().getCommand("blockcode").setTabCompleter(new BlockCodeCommand());
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Listeners(), this);
        Main.getPlugin().getLogger().fine("|----------------------------------------|");
        Main.getPlugin().getLogger().fine("| [BlockCode] Plugin Enabled             |");
        Main.getPlugin().getLogger().fine("| [BlockCode] Plugin coded by CubeAttack |");
        Main.getPlugin().getLogger().fine("|----------------------------------------|");
    }

    @Override
    public void onDisable() {
        Main.getPlugin().getLogger().fine("|----------------------------------------|");
        Main.getPlugin().getLogger().fine("| [BlockCode] Plugin Disabled            |");
        Main.getPlugin().getLogger().fine("| [BlockCode] Plugin coded by CubeAttack |");
        Main.getPlugin().getLogger().fine("|----------------------------------------|");
    }

    public static Main getPlugin() {
        return plugin;
    }
}
