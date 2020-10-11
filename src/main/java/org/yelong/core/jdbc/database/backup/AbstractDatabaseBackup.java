package org.yelong.core.jdbc.database.backup;

import java.util.Objects;

import org.yelong.commons.lang.runtime.CommandExecutor;
import org.yelong.commons.lang.runtime.DefaultCommandExecutor;

public abstract class AbstractDatabaseBackup implements DatabaseBackup {

	protected CommandExecutor commandExecutor;

	public AbstractDatabaseBackup() {
		this(DefaultCommandExecutor.INSTANCE);
	}

	
	public AbstractDatabaseBackup(CommandExecutor commandExecutor) {
		this.commandExecutor = Objects.requireNonNull(commandExecutor);
	}

	@Override
	public void setCommandExecutor(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}

	@Override
	public CommandExecutor getCommandExecutor() {
		return this.commandExecutor;
	}

}
