package me.petterim1.biomecommand;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.plugin.PluginBase;

public class Plugin extends PluginBase implements Listener {

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (command.getName().equalsIgnoreCase("setbiome")) {
            if (sender instanceof Player) {
                if (args.length != 1 && args.length != 2) {
                    return false;
                }

                int id;
                try {
                    id = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("\u00A7cBiome id must be a number");
                    return true;
                }

                Player p = (Player) sender;
                if (args.length == 2 && args[1].equalsIgnoreCase("true")) {
                    p.usedChunks.forEach((key, value) -> {
                        FullChunk chunk = p.getLevel().getChunk(Level.getHashX(key), Level.getHashZ(key));
                        for (int x = 0; x < 16; x++) {
                            for (int z = 0; z < 16; z++) {
                                chunk.setBiomeId(x, z, id);
                            }
                        }
                    });
                } else {
                    FullChunk chunk = p.getChunk();
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            chunk.setBiomeId(x, z, id);
                        }
                    }
                }

                sender.sendMessage("\u00A7aBiome id set");
            } else {
                sender.sendMessage("\u00A7cThis command can be used only as a player");
            }
        }

        return true;
    }
}
