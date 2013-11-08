package net.rymate.bungee.irc;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import java.util.logging.Level;

public class IRCMain extends Plugin
        implements Listener {

    private static IRCMain noodles;
    public static ServerInfo hub;
    public final ConfigFile config = new ConfigFile();
    Configuration configuration;
    private PircBotX bot;

    public void onEnable() {
        if (!config.nickservPass.isEmpty()) {
            configuration = new Configuration.Builder()
            .setName(config.ircNick) //Set the nick of the bot. CHANGE IN YOUR CODE
            .setLogin(config.ircNick) //login part of hostmask, eg name:login@host
            .setAutoNickChange(true) //Automatically change nick when the current one is in use
            .setCapEnabled(true) //Enable CAP features
            .addListener(new IRCListener(this)) //This class is a listener, so add it to the bots known listeners
            .setServerHostname(config.serverHostname)
            .addAutoJoinChannel(config.channelName)
            .setNickservPassword(config.nickservPass)
            .setServerPort(config.port)
            .buildConfiguration();
        } else {
            configuration = new Configuration.Builder()
            .setName(config.ircNick) //Set the nick of the bot. CHANGE IN YOUR CODE
            .setLogin(config.ircNick) //login part of hostmask, eg name:login@host
            .setAutoNickChange(true) //Automatically change nick when the current one is in use
            .setCapEnabled(true) //Enable CAP features
            .addListener(new IRCListener(this)) //This class is a listener, so add it to the bots known listeners
            .setServerHostname(config.serverHostname)
            .addAutoJoinChannel(config.channelName)
            .setServerPort(config.port)
            .buildConfiguration();
        }
        bot = new PircBotX(configuration);
        noodles = this;
        getProxy().getLogger().log(Level.INFO, "NetChat may or may not enable!");

        this.getProxy().getScheduler().runAsync(this, new Runnable() {
            public void run() {
                //Connect to server
                try {
                    bot.startBot();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        BungeeListener listener = new BungeeListener(this);
        

        getProxy().getLogger().log(Level.INFO, "NetChat is enabled!");
    }

    public void onDisable() {
        getProxy().getLogger().log(Level.INFO, "NetChat is disabling!");
    }

    public static IRCMain getPlugin() {
        return noodles;
    }

    public PircBotX getBot() {
        return bot;
    }

    public ConfigFile getConfig() {
        return config;
    }
}
