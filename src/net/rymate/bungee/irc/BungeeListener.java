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
import org.pircbotx.PircBotX;

/**
 * Created with IntelliJ IDEA. User: Ryan Date: 01/11/13 Time: 21:32 To change
 * this template use File | Settings | File Templates.
 */
public class BungeeListener extends Command
        implements Listener {

    public IRCMain pasta;
    public List<String> input = new ArrayList();
    private PircBotX irc;

    public BungeeListener(IRCMain Nethad) {
        super("netchat", "netchat.use", new String[0]);
        this.pasta = Nethad;
        this.pasta.getProxy().getPluginManager().registerListener(this.pasta, this);
        this.irc = this.pasta.getBot();
    }

    public void execute(CommandSender s, String[] args) {
        if (!this.input.contains(s.getName())) {
            this.input.add(s.getName());
            s.sendMessage(ChatColor.DARK_GREEN + "NetChat focus toggled on.");
            return;
        }

        this.input.remove(s.getName());
        s.sendMessage(ChatColor.DARK_RED + "NetChat focus toggled off.");
    }

    @EventHandler
    public void onPlayerChat(ChatEvent e) {
        if (e.isCommand()) {
            return;
        }

        if ((e.getSender() instanceof ProxiedPlayer)) {
            ProxiedPlayer s = (ProxiedPlayer) e.getSender();
            if (pasta.getConfig().crossServerChat) {
                e.setCancelled(true);
                for (ProxiedPlayer pl : this.pasta.getProxy().getPlayers()) {
                    if (pl instanceof ProxiedPlayer) {
                        pl.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + s.getServer().getInfo().getName() + ChatColor.DARK_RED + "] " + ChatColor.BLUE + s.getName() + ChatColor.WHITE + ": " + e.getMessage());
                    }
                }
            }
            irc.sendIRC().message(pasta.getConfig().channelName, "[" + s.getServer().getInfo().getName() + "] " + "<" + s.getDisplayName() + "> " + e.getMessage());
            return;

        }
    }

    @EventHandler
    public void onPlayerJoin(LoginEvent e) {
        if (e.isCancelled()) {
            return;
        }
        irc.sendIRC().message(pasta.getConfig().channelName, e.getConnection().getName() + " joined the server");
    }

    @EventHandler
    public void onPlayerLeave(PlayerDisconnectEvent e) {
        irc.sendIRC().message(pasta.getConfig().channelName, e.getPlayer().getDisplayName() + " left the server");
    }
}
