package me.info.infoslifesteal;
import me.info.infoslifesteal.commands.resethearts;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
public final class InfosLifesteal extends JavaPlugin implements Listener {
    public static InfosLifesteal instance;
    public PlayerMemory data;
    @Override
    public void onEnable() {
        instance = this;
        this.registerCommands();
        this.data = new PlayerMemory(this);
        this.getServer().getPluginManager().registerEvents(this, this);

    }

    public void registerCommands() {
        new resethearts(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        data.reloadConfig();
        Player player = event.getEntity().getPlayer();

        Player kill = event.getEntity().getKiller();

        int MaxHP = 20;

        if (this.data.getConfig().contains("players." + player.getUniqueId().toString() + ".total")) {
            MaxHP = this.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".total");
        }

        data.getConfig().set("players." + player.getUniqueId().toString() + ".total", (MaxHP - 2));
        data.saveConfig();

        event.getEntity().setMaxHealth(MaxHP);

        if (kill == null) {
            return;
        } else {

            MaxHP = this.data.getConfig().getInt("players." + kill.getUniqueId().toString() + ".total");
            data.getConfig().set("players." + kill.getUniqueId().toString() + ".total", (MaxHP + 2));
            kill.setMaxHealth(MaxHP);
            data.reloadConfig();
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        int MaxHP;

        Player player = event.getPlayer();
        MaxHP = this.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".total");
        player.setMaxHealth(MaxHP);
    }
    public static InfosLifesteal getInstance(){return instance;}

}
