package net.rymate.bungee.irc;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Level;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class IRCMain extends Plugin
        implements Listener {

    private static IRCMain thisFile;
    public static ServerInfo hub;
    public final ConfigFile config = new ConfigFile();
    private IRCListener bot;

    public void onEnable() {
        bot = new IRCListener(this);

        thisFile = this;
        getProxy().getLogger().log(Level.INFO, "BungeeIRC is enabling!");

        this.getProxy().getScheduler().runAsync(this, new Runnable() {
            public void run() {
                //Connect to server
                try {
                    // Connect to the IRC server.
                    bot.connect(config.serverHostname, config.port);

                    // Join the #pircbot channel.
                    bot.joinChannel(config.channelName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        BungeeListener listener = new BungeeListener(this);

        getProxy().getLogger().log(Level.INFO, "BungeeIRC is enabled!");
    }

    public void onDisable() {
        getProxy().getLogger().log(Level.INFO, "BungeeIRC is disabling!");
    }

    public static IRCMain getPlugin() {
        return thisFile;
    }

    public IRCListener getBot() {
        return bot;
    }

    public ConfigFile getConfig() {
        return config;
    }

    public String replaceMinecraftPlaceholders(ProxiedPlayer player, String message, String format) {

        return format.replaceAll("%from", player.getServer().getInfo().getName())
                .replaceAll("%uuid", player.getUniqueId().toString()) // for people who really want UUIDs in chat
                .replaceAll("%player", player.getName())
                .replaceAll("%message", message)
                .replaceAll("%displayname", player.getDisplayName());
    }

    public String replaceIrcPlaceholders(String player, String channel, String message, String format) {

        return format.replaceAll("%from", channel)
                .replaceAll("%uuid", "") // for people who really want UUIDs in chat
                .replaceAll("%player", player)
                .replaceAll("%message", message)
                .replaceAll("%displayname", player);
    }

    public String colorize(String string) {
        if (string == null) {
            return "";
        }

        return string.replaceAll("&([a-z0-9])", "\u00A7$1");
    }

    public String decolorize(String string) { 
        if (string == null) {
            return "";
        }

        return string.replaceAll("&([a-z0-9])", "")
                .replaceAll("§([a-z0-9])", "");
    }

}
