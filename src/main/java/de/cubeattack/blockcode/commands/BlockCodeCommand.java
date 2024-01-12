package de.cubeattack.blockcode.commands;

import java.util.ArrayList;
import java.io.FileNotFoundException;

import de.cubeattack.blockcode.config.MessagesConfig;
import de.cubeattack.blockcode.main.Main;
import de.cubeattack.blockcode.utils.PluginUtils;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.io.FileOutputStream;
import java.io.File;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.io.PrintStream;
import java.util.List;

import org.bukkit.command.CommandExecutor;

public class BlockCodeCommand implements CommandExecutor, TabCompleter
{
    public static PrintStream logger;
    public static PrintStream PrintImports;
    public static ArrayList<Material>  alist;
    public static HashMap<Player, Location> loc1;
    public static HashMap<Player, Location> loc2;

    static {
        BlockCodeCommand.logger = null;
        BlockCodeCommand.PrintImports = null;
        BlockCodeCommand.alist = new ArrayList<Material>();
        BlockCodeCommand.loc1 = new HashMap<Player, Location>();
        BlockCodeCommand.loc2 = new HashMap<Player, Location>();
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (args.length == 0) {
                if (p.hasPermission("blocktocode.help")) {
                    p.sendMessage("§7» §6BlockToCode §7«");
                    p.sendMessage("§e/btc §7- §6Main-Command for BlockToCode");
                    p.sendMessage("§e/btc help §7- §6Show all Command's for BlockToCode");
                    p.sendMessage("§e/btc export <Name> §7- §6Exports Blocks to Code");
                    p.sendMessage("§7» §6BlockToCode §7«");
                }
                else {
                    p.sendMessage(String.valueOf(MessagesConfig.prefix) + MessagesConfig.no_permission);
                }
            }
            else if (args[0].equalsIgnoreCase("export")) {
                if (args.length == 2) {
                    if (p.hasPermission("blocktocode.export")) {
                        final Location ploc = p.getLocation();
                        final File f = new File("plugins/BlockCode/Exports", String.valueOf(args[1]) + ".code");
                        final File PrintImportsFile = new File("plugins/BlockCode/Exports", (args[1]) + ".imports.code");
                        if (!f.exists()) {
                            f.getParentFile().mkdirs();
                        }
                        try {
                            if(!BlockCodeCommand.loc1.containsKey(p) || !BlockCodeCommand.loc2.containsKey(p)){
                                p.sendMessage(MessagesConfig.prefix + "§cBitte setze erst eine Position");
                                return true;
                            }
                            BlockCodeCommand.logger = new PrintStream(new FileOutputStream(f));
                            BlockCodeCommand.PrintImports = new PrintStream(new FileOutputStream(PrintImportsFile));
                            BlockCodeCommand.PrintImports.println("import org.bukkit.Location;");
                            BlockCodeCommand.PrintImports.println("import org.bukkit.World;");
                            BlockCodeCommand.logger.println("public static void Main(Location l, World w){");
                            BlockCodeCommand.logger.println("build_"+ 1 +"(l,w);");
                            BlockCodeCommand.logger.println("build_"+ 2 +"(l,w);");
                            BlockCodeCommand.logger.println("build_"+ 3 +"(l,w);");
                            BlockCodeCommand.logger.println("build_"+ 4 +"(l,w);");
                            BlockCodeCommand.logger.println("build_"+ 5 +"(l,w);");
                            BlockCodeCommand.logger.println("build_"+ 6 +"(l,w);");
                            BlockCodeCommand.logger.println("build_"+ 7 +"(l,w);");
                            BlockCodeCommand.logger.println("build_"+ 8 +"(l,w);");
                            BlockCodeCommand.logger.println("}");
                            BlockCodeCommand.logger.println("public static void build_1(Location l, World w){");
                            int methodenCounter = 1;
                            int lineCounter = 0;
                            for (final Location loc : PluginUtils.getLocationsFromToLocations(BlockCodeCommand.loc1.get(p), BlockCodeCommand.loc2.get(p))) {
                                final int x = loc.getBlockX() - ploc.getBlockX();
                                final int y = loc.getBlockY() - ploc.getBlockY();
                                final int z = loc.getBlockZ() - ploc.getBlockZ();
                                if(lineCounter >= 1500) {
                                    BlockCodeCommand.logger.println("}");
                                    methodenCounter ++;
                                    BlockCodeCommand.logger.println("public static void build_"+ methodenCounter+"(Location l, World w){");
                                    lineCounter = 0;
                                }
                                Material id = loc.getBlock().getBlockData().getMaterial();
                                String subid = String.valueOf(loc.getBlock().getBlockData());
                                if (id != Material.AIR) {
                                    if(!alist.contains(id)) {
                                        BlockCodeCommand.PrintImports.println("import static org.bukkit.Material." + id + ";");
                                        alist.add(id);
                                    }
                                    String plusNumbersX = "plus";
                                    String plusNumbersY = "plus";
                                    String plusNumbersZ = "plus";
                                    if(x >= (0)){
                                        plusNumbersX = "+";
                                    }
                                    if(y >= (0)){
                                        plusNumbersY = "+";
                                    }
                                    if(z >= (0)){
                                        plusNumbersZ = "+";
                                    }
                                    BlockData data = loc.getBlock().getBlockData();
                                    String configBlock = "    w.getBlockAt(l.getBlockX()" +  plusNumbersX + x + ",l.getBlockY()" + plusNumbersY + y + ",l.getBlockZ()" + plusNumbersZ + z + ").setType(" + id + ");";
                                    String configBlocks = configBlock.replace("plus","");
                                    BlockCodeCommand.logger.println(configBlocks);
                                    lineCounter = lineCounter + 1;
                                    if(subid.contains("[")) {
                                        if (!subid.equalsIgnoreCase("CraftBlockData{minecraft:" + id.name().toLowerCase())) {
                                            String configBlock1Data = "    w.getBlockAt(l.getBlockX()" + plusNumbersX + x + ",l.getBlockY()" + plusNumbersY + y + ",l.getBlockZ()" + plusNumbersZ + z + ").setBlockData(" + id + ".createBlockData(\"" + data + "\"));";
                                            String configBlock2Data = configBlock1Data.replace("plus", "");
                                            String configBlock3Data = configBlock2Data.replace("CraftBlockData{minecraft:" + id.name().toLowerCase(), "");
                                            String configBlock4Data = configBlock3Data.replace("}", "");
                                            BlockCodeCommand.logger.println(configBlock4Data);
                                            lineCounter = lineCounter + 1;
                                        }
                                    }
                                }
                            }
                            BlockCodeCommand.logger.println("}");
                            p.sendMessage(MessagesConfig.prefix + "§7The blocks have §asuccessfully §7been exported as code!");
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                                @Override
                                public void run() {
                                    BlockCodeCommand.logger.close();
                                    BlockCodeCommand.PrintImports.close();
                                }
                            }, 20L);
                        }
                        catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        p.sendMessage(MessagesConfig.prefix + MessagesConfig.no_permission);
                    }
                }
                else {
                    p.sendMessage(String.valueOf(MessagesConfig.prefix) + "§cToo much/less arguments");
                }
            }
            else {
                p.sendMessage(String.valueOf(MessagesConfig.prefix) + MessagesConfig.unknown_command);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> tablist = new ArrayList<String>();
        String current = args[args.length -1].toLowerCase();
        if(args.length == 1) {
            list.add("export");
            for(String string : list){
                if(string.startsWith(current)){
                    tablist.add(string);
                }
            }
        }
        return tablist;
    }
}

