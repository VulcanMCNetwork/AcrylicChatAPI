package com.acrylic.chatfunction;

import acrylic.nmsutils.json.AbstractJSONComponent;
import acrylic.nmsutils.json.JSON;
import acrylic.nmsutils.json.JSONComponent;
import com.acrylic.chatvariables.ChatVariable;
import com.acrylic.chatvariables.SingleUseChatVariable;
import com.acrylic.exceptions.UnableToUseChatVariableException;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.function.Consumer;

public interface AbstractChatProcess extends BaseChatProcess {

    String chatFormat();

    Consumer<AbstractJSONComponent> messageComponentConsumer();

    default JSON processMessage() throws UnableToUseChatVariableException {
        final JSON json = (JSON) getBaseJson().duplicate();
        final String[] deconstructed = getAnalyzableMessage();
        final Player player = getPlayer();
        final String splitAt = getChatVariableSet().getSplitter();
        final String chatFormat = chatFormat();

        HashSet<ChatVariable> used = new HashSet<>();
        int i = 0;
        stringDeconstructedLoop: for (String var : deconstructed) {
            for (ChatVariable chatVariable : getChatVariableSet()) {
                if (chatVariable.getVariable().equalsIgnoreCase(var)) {
                    if (!chatVariable.allowedToUse(player)) {
                        throwFailedVariable(chatVariable);
                        break;
                    } else if (chatVariable instanceof SingleUseChatVariable && used.contains(chatVariable)) {
                        ((SingleUseChatVariable) chatVariable).failedMultipleUses(player);
                        break;
                    } else {
                        if (i > 0) append(json,splitAt);
                        appendVariable(json,chatVariable);
                        used.add(chatVariable);
                        i++;
                        continue stringDeconstructedLoop;
                    }
                }
            }
            //
            if (i > 0) {
                append(json,chatFormat + splitAt + var);
            } else {
                append(json,chatFormat + var);
            }
            i++;
        }
        return json;
    }

    default void appendVariable(JSON json, ChatVariable chatVariable) {
        json.append(chatVariable.getReplacement(this));
    }

    default void append(JSON json, String text) {
        JSONComponent comp = JSONComponent.of(text);
        Consumer<AbstractJSONComponent> componentConsumer = messageComponentConsumer();
        if (componentConsumer != null) componentConsumer.accept(comp);
        json.append(comp);
    }

    default String[] getAnalyzableMessage() {
        return getMessage().split(getChatVariableSet().getSplitter());
    }


}
