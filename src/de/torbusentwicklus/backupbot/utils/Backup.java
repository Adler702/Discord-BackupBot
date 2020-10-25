package de.torbusentwicklus.backupbot.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Backup {

    private Guild guild;
    private FileWriter writer;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private JsonObject jsonObject = new JsonObject();
    private String code;

    public Backup(Guild guild) throws IOException {
        this.guild = guild;
        this.code = generateCode(5);
        save();
    }

    public File getFile() throws IOException {
        File file = new File(guild.getId()+"-"+code+".json");
        if (!file.exists()) {
            file.createNewFile();
        }
        writer = new FileWriter(file);
        writer.write(save());
        writer.close();
        return file;
    }

    public String save() throws IOException {
        saveGuildSettings();
        savedRoles();
        savedChannels();
        savedUserData();
        return gson.toJson(jsonObject);
    }

    private void saveGuildSettings() {
        jsonObject.addProperty("guild.name", guild.getName());
        jsonObject.addProperty("guild.banner", guild.getBannerUrl());
        jsonObject.addProperty("guild.description", guild.getDescription());
        jsonObject.addProperty("guild.icon", guild.getIconUrl());
        jsonObject.addProperty("guild.region", guild.getRegionRaw());
        jsonObject.addProperty("guild.publicrole", guild.getPublicRole().getId());
    }

    private void savedRoles() {
        // convert Roles to Json format
        JsonArray array = new JsonArray();
        for (Role role : guild.getRoles()) {
            JsonObject object = new JsonObject();
            object.addProperty(role.getId()+".name", role.getName());
            object.addProperty(role.getId()+".color", role.getColorRaw());
            object.addProperty(role.getId()+".position", role.getPosition());
            JsonArray permarray = new JsonArray();
            for (Permission permission : role.getPermissions()) {
                permarray.add(permission.getName());
            }
            object.add("perms", permarray);
            array.add(object);
        }
        jsonObject.add("roles", array);
    }

    private void savedChannels() {
        JsonArray array = new JsonArray();
        // convert Channels to Json format
        for(Category category : guild.getCategories()) {
            JsonObject object = new JsonObject();
            object.addProperty(category.getName()+".name", category.getName());
            object.addProperty(category.getName()+".id", category.getId());
            object.addProperty(category.getName()+".position", category.getPosition());
            object.addProperty(category.getName()+".type", category.getType().getId());
            JsonArray channels = new JsonArray();
            for (GuildChannel channel : category.getChannels()) {
                JsonObject channelobj = new JsonObject();
                channelobj.addProperty(channel.getName()+".name", channel.getName());
                channelobj.addProperty(channel.getName()+".position", channel.getPosition());
                channelobj.addProperty(channel.getName()+".type", channel.getType().getId());
                channelobj.addProperty(channel.getName()+".type.bucket", channel.getType().getSortBucket());
                channels.add(channelobj);
            }
            object.add("channels", channels);
            array.add(object);
        }
        jsonObject.add("categorys", array);
    }

    private void savedUserData() {
        JsonArray array = new JsonArray();
        // convert Nicknames, Roles, Mute, Bans,  to Json format
        for (Member member : guild.getMembers()) {
            JsonObject object = new JsonObject();
            object.addProperty(member.getId()+".name", member.getUser().getName());
            object.addProperty(member.getId()+".nick", member.getNickname());
            object.addProperty(member.getId()+".fake", member.getUser().isFake());
            StringBuffer buffer = new StringBuffer();
            for (Role role : member.getRoles()) {
                buffer.append(role.getId()+":");
            }
            object.addProperty("roles", buffer.toString());
            array.add(object);
        }
        jsonObject.add("userdata", array);
    }

    private String generateCode(int bound ) {
        StringBuffer sb = new StringBuffer();
        String characters = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
        for (int i = 0; i < bound; i++) {
            int random = new Random().nextInt(characters.length());
            sb.append(characters.charAt(random));
        }
        return sb.toString();
    }

    public String getCode() {
        return code;
    }
}
