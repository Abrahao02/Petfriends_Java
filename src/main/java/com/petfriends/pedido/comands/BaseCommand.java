package com.petfriends.pedido.commands;

public abstract class BaseCommand {
    private final String commandId;
    protected BaseCommand(String commandId) {
        this.commandId = commandId;
    }
    public String getCommandId() { return commandId; }
}
