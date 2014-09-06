/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rymate.bungee.irc;

import java.io.File;
import net.rymate.bungee.irc.config.Config;

/**
 *
 * @author Ryan
 */
public class ConfigFile {
    private static String configpath = File.separator+"plugins"+File.separator+"BungeeIRC"+File.separator+"settings.yml";
    public Config c = new Config(configpath);
    public String serverHostname = c.getString("IRC Server Hostname", "irc.esper.net");
    public int port = c.getInt("IRC Server Port", 6667);
    public String ircNick = c.getString("IRC Nickname", "MyFirstBungeeIrcBot");
    public String nickservPass = c.getString("IRC Nickserv Password", "");
    public String channelName = c.getString("Channel Name", "#icantconfig");
    public String chatFormat = c.getString("Chat Format", "&b[%from] %player:&f %message");
    public boolean crossServerChat = c.getBoolean("Cross Server Chat", false);
    
}
