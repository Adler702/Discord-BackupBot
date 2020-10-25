package de.torbusentwicklus.backupbot.utils;

public class BackupLoader {

    private Backup backup;

    public BackupLoader() {
    }

    public BackupLoader load(final Backup backup) {
        this.backup = backup;
        /**
         *
         */
        return this;
    }
}
