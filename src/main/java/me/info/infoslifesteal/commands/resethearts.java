package me.info.infoslifesteal.commands;

import me.info.infoslifesteal.InfosLifesteal;
import me.info.infoslifesteal.PlayerMemory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.io.File;
import java.util.List;

public class resethearts implements CommandExecutor {
    public PlayerMemory data;
    private InfosLifesteal plugin;



    public resethearts(InfosLifesteal plugin) {
        this.plugin = plugin;
        plugin.getCommand("resetHearts").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Only players may execute this command!");
            return true;
        }
        else{
            if(!player.hasPermission("info.resetHearts")){
                player.sendMessage("You do not have permission to do this!");
            }
            else{
                int MaxHP;
                File file = new File(InfosLifesteal.getInstance().getDataFolder(), "data.yml");
                file.delete();
                for(Player all : Bukkit.getServer().getOnlinePlayers()){
                    this.data = new PlayerMemory(plugin);
                    data.getConfig().set("players." + all.getUniqueId().toString() + ".total", (MaxHP = 20));
                    data.saveConfig();
                    all.setMaxHealth(20);
                    data.reloadConfig();
                }


            }
        }
        return false;

    }
}
