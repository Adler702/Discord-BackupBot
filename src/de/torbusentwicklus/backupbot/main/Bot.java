package de.torbusentwicklus.backupbot.main;

import de.torbusentwicklus.backupbot.listener.CommandListener;
import de.torbusentwicklus.backupbot.utils.BackupLoader;
import de.torbusentwicklus.backupbot.utils.TimeUtils;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bot {

    private ShardManager shardManager;
    private DefaultShardManagerBuilder builder;
    private TimeUtils timeUtils;
    private BackupLoader backupLoader;
    private static Bot bot;

    public Bot() throws LoginException {
        bot = this;

        builder = new DefaultShardManagerBuilder();
        builder.setActivity(Activity.playing("Backup Manager.."));
        builder.setToken("TOKEN");
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        shardManager = builder.build();
        registerConsoleCommands();
        shardManager.addEventListener(new CommandListener());
        timeUtils = new TimeUtils();
        backupLoader = new BackupLoader();
    }

    private void registerConsoleCommands() {

        new Thread(() -> {
            String line = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("stop")) {
                        if (shardManager != null) {
                            shardManager.setStatus(OnlineStatus.INVISIBLE);
                            shardManager.shutdown();
                            System.out.println("Bot gestoppt!");
                        }

                        reader.close();
                    } else {

                    }
                }
            } catch (IOException ex) {
            }
        }).start();
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public TimeUtils getTimeUtils() {
        return timeUtils;
    }

    public static Bot getBot() {
        return bot;
    }

    public DefaultShardManagerBuilder getBuilder() {
        return builder;
    }

    public BackupLoader getBackupLoader() {
        return backupLoader;
    }

    public void setBackupLoader(BackupLoader backupLoader) {
        this.backupLoader = backupLoader;
    }

    public static void setBot(Bot bot) {
        Bot.bot = bot;
    }

    public void setBuilder(DefaultShardManagerBuilder builder) {
        this.builder = builder;
    }

    public void setShardManager(ShardManager shardManager) {
        this.shardManager = shardManager;
    }

    public void setTimeUtils(TimeUtils timeUtils) {
        this.timeUtils = timeUtils;

    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
