package net.rymate.bungee.irc;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 01/11/13
 * Time: 21:58
 * To change this template use File | Settings | File Templates.
 */
public class IRCListener extends ListenerAdapter implements Listener {
    private IRCMain plugin;

    IRCListener(IRCMain aThis) {
        this.plugin = aThis;
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        if (event.getMessage().startsWith("hello?")) {
            event.respond("hi");
        }
        
        if (!event.getMessage().startsWith(".")) {
             for (ProxiedPlayer pl : this.plugin.getProxy().getPlayers())
                if (pl instanceof ProxiedPlayer)
                    pl.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.BLUE + "IRC" + ChatColor.DARK_RED + "] " + ChatColor.GOLD + event.getUser().getNick() + ChatColor.RED + ": " + event.getMessage());
        } else if (event.getMessage().equalsIgnoreCase(".players")) {
            event.respond("There are " + plugin.getProxy().getOnlineCount() + " players online.");
            event.respond("They are: " + plugin.getProxy().getPlayers().toString());
        }
    }
    
    
}
