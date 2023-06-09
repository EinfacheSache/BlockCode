package de.cubeattack.blockcode.utils;

import org.bukkit.Location;
import java.util.ArrayList;
import java.util.List;

public class PluginUtils {

    public static List<Location> getLocationsFromToLocations(final Location loc1, final Location loc2) {
        final List<Location> locs = new ArrayList<Location>();
        int min_x = loc1.getBlockX();
        int min_y = loc1.getBlockY();
        int min_z = loc1.getBlockZ();
        int max_x = loc2.getBlockX();
        int max_y = loc2.getBlockY();
        int max_z = loc2.getBlockZ();
        if (loc1.getBlockX() > loc2.getBlockX()) {
            min_x = loc2.getBlockX();
            max_x = loc1.getBlockX();
        }
        if (loc1.getBlockY() > loc2.getBlockY()) {
            min_y = loc2.getBlockY();
            max_y = loc1.getBlockY();
        }
        if (loc1.getBlockZ() > loc2.getBlockZ()) {
            min_z = loc2.getBlockZ();
            max_z = loc1.getBlockZ();
        }
        for (int x = min_x; x <= max_x; ++x) {
            for (int z = min_z; z <= max_z; ++z) {
                for (int y = min_y; y <= max_y; ++y) {
                    locs.add(new Location(loc1.getWorld(), (double)x, (double)y, (double)z));
                }
            }
        }
        return locs;
    }
}

