package de.torbusentwicklus.backupbot.listener;

import de.torbusentwicklus.backupbot.main.Bot;
import de.torbusentwicklus.backupbot.utils.Backup;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.io.IOException;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();
        if (event.isFromType(ChannelType.TEXT)) {
            TextChannel channel = event.getTextChannel();
            User user = event.getAuthor();
            if (!user.isBot()) {
                if (message.startsWith("bp.")) {
                    String[] args = message.split("\\.");
                    if (args[1].equalsIgnoreCase("savefile")) {
                        try {
                            Backup backup = new Backup(event.getGuild());
                            channel.sendMessage("Backup saved " + Bot.getBot().getTimeUtils().getDateTime() + " - Code: " + backup.getCode()).queue();
                            channel.sendFile(backup.getFile()).queue();
                            backup.getFile().delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (args[1].equalsIgnoreCase("save")) {
                        try {
                            Backup backup = new Backup(event.getGuild());
                            System.out.println(backup.save());
                            channel.sendMessage("Backup saved " + Bot.getBot().getTimeUtils().getDateTime() + " - Code: " + backup.getCode()).queue();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
