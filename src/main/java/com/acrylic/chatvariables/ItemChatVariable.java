package com.acrylic.chatvariables;

import acrylic.nmsutils.json.AbstractJSONComponent;
import acrylic.nmsutils.json.JSONComponent;
import com.acrylic.chatfunction.AbstractChatProcess;
import com.acrylic.version_latest.Items.Utils.ItemPropertyUtils;
import com.acrylic.version_latest.Messages.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * By default, this will not be registered to any chat variable set
 * as this is an example.
 */
public class ItemChatVariable implements SingleUseChatVariable {

    @Override
    public String getVariable() {
        return "[item]";
    }

    @Override
    public AbstractJSONComponent getReplacement(AbstractChatProcess chatProcess) {
        ItemStack item = chatProcess.getPlayer().getItemInHand();
        return JSONComponent.of("&r&8[" + new ItemPropertyUtils(item).getItemDisplayName() + "&r&8]").item(item);
    }

    @Override
    public boolean allowedToUse(Player player) {
        return true;
    }

    @Override
    public void failedMultipleUses(Player player) {
        player.sendMessage(ChatUtils.get("&c&l[!] &r&cYou may only use " + getVariable() + " once per message."));
    }
}
