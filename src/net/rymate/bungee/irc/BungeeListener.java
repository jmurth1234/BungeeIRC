package net.rymate.bungee.irc;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;

/**
 * Created with IntelliJ IDEA. User: Ryan Date: 01/11/13 Time: 21:32 To change
 * this template use File | Settings | File Templates.
 */
public class BungeeListener implements Listener {

    public IRCMain pasta;
    public List<String> input = new ArrayList();
    private IRCListener irc;

    public BungeeListener(IRCMain Nethad) {
        this.pasta = Nethad;
        this.pasta.getProxy().getPluginManager().registerListener(this.pasta, this);
        this.irc = this.pasta.getBot();
    }

    @EventHandler
    public void onPlayerChat(ChatEvent e) {
        if (e.isCommand()) {
            return;
        }

        if ((e.getSender() instanceof ProxiedPlayer)) {
            ProxiedPlayer s = (ProxiedPlayer) e.getSender();
            
            String format = pasta.getConfig().chatFormat;
            String chatMessage = pasta.decolorize(e.getMessage());
            format = pasta.replaceMinecraftPlaceholders(s, chatMessage, format);
            format = pasta.colorize(format);
            
            if (pasta.getConfig().crossServerChat) {
                e.setCancelled(true);
                for (ProxiedPlayer pl : this.pasta.getProxy().getPlayers()) {
                    if (pl instanceof ProxiedPlayer) {
                        pl.sendMessage(format);
                    }
                }
            }
            
            format = pasta.decolorize(format);
            irc.sendMessage(pasta.getConfig().channelName, format);
            return;

        }
    }

    @EventHandler
    public void onPlayerJoin(LoginEvent e) {
        if (e.isCancelled()) {
            return;
        }
        irc.sendMessage(pasta.getConfig().channelName, e.getConnection().getName() + " joined the server");
    }

    @EventHandler
    public void onPlayerLeave(PlayerDisconnectEvent e) {
        irc.sendMessage(pasta.getConfig().channelName, e.getPlayer().getDisplayName() + " left the server");
    }

}
