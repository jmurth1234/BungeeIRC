package net.rymate.bungee.irc;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jibble.pircbot.PircBot;

/**
 * Created with IntelliJ IDEA. User: Ryan Date: 01/11/13 Time: 21:58 To change
 * this template use File | Settings | File Templates.
 */
public class IRCListener extends PircBot {

    private final IRCMain plugin;

    public IRCListener(IRCMain aThis) {
        this.plugin = aThis;
        this.setName(plugin.config.ircNick);
    }

    @Override
    public void onMessage(String channel, String sender,
            String login, String hostname, String message) {
        if (message.startsWith("hello?")) {
            sendMessage(channel, sender + ": hi");
        }

        if (!message.startsWith(".")) {
            String format = plugin.getConfig().chatFormat;
            String chatMessage = plugin.decolorize(message);
            format = plugin.replaceIrcPlaceholders(sender, channel, chatMessage, format);
            format = plugin.colorize(format);
            
            for (ProxiedPlayer pl : this.plugin.getProxy().getPlayers()) {
                if (pl instanceof ProxiedPlayer) {
                    pl.sendMessage(format);
                }
            }
            
        } else if (message.equalsIgnoreCase(".players")) {
            sendMessage(channel, sender + ": There are " + plugin.getProxy().getOnlineCount() + " players online.");
            sendMessage(channel, sender + ": They are: " + plugin.getProxy().getPlayers().toString());
        }
    }

}
