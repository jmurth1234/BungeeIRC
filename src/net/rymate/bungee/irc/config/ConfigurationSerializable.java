package net.rymate.bungee.irc.config;

import java.util.Map;

public interface ConfigurationSerializable {
    public Map<String, Object> serialize();
}