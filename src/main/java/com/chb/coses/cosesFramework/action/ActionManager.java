package com.chb.coses.cosesFramework.action;

import java.util.HashMap;
import java.util.Map;

public class ActionManager {
    private static final Map<String, AbstractAction> actions = new HashMap<>();

    public static void registerAction(String name, AbstractAction action) {
        actions.put(name, action);
    }

    public static AbstractAction getAction(String name) {
        return actions.get(name);
    }

    public static boolean hasAction(String name) {
        return actions.containsKey(name);
    }
}