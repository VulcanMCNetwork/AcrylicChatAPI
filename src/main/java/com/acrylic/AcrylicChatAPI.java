package com.acrylic;

import com.acrylic.chatvariables.*;
import com.acrylic.tests.Test;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AcrylicChatAPI extends JavaPlugin {

    /**
     * Highly discourage defining a new AbstractChatVariableSet but if you have to
     * resort to it, you may.
     *
     * @see AbstractChatVariableSet
     */
    @Setter @Getter
    private static AbstractChatVariableSet<ChatVariable> chatVariableSet = new ChatVariableSet();

    @Override
    public void onEnable() {
        // Plugin startup logic
        //chatVariableSet.register(new ItemChatVariable());
        //chatVariableSet.register(new TestChatVariable());
        //Bukkit.getPluginManager().registerEvents(new Test(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
