package com.magnolia.rd.dialogs.designer.utils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import info.magnolia.commands.CommandsManager;

public class CommandUtils {
	
	@Inject
	private CommandsManager commandsManager;
	
	public void executeCommand(String commandName, String catalog, String workspace, String path) {
		Map<String, Object> commandsParams = new HashMap<>();
		if(workspace != null && !workspace.isEmpty())
			commandsParams.put("repository", workspace);
		if(path != null && !path.isEmpty())
			commandsParams.put("path", path);
		commandsParams.put("recursive", true);
		
		try {
			commandsManager.executeCommand(commandName, commandsParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
