package jda.jayson.guilds.nullbloxme.commands.other;

import jda.jayson.id.Roles;
import jda.jayson.id.Users;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandRole {
    public static void onEvent(MessageReceivedEvent e) {
        Message msg = e.getMessage();
        String content = msg.getContentRaw();
        String[] argument = content.split(" ");
        if (argument[0].equalsIgnoreCase("!role")) {
            String role = argument[1];
            e.getChannel().sendMessage(setRole(role, e)).complete();
        }
    }

    private static String setRole(String role, MessageReceivedEvent event) {
        if(role.equalsIgnoreCase("cubik")) {
            event.getGuild().addRoleToMember(event.getMember(),event.getGuild().getRoleById(Roles.User_Cubik)).complete();
            return "Added Role: Cubik!";
        } else if(role.equalsIgnoreCase("blockbench")) {
            event.getGuild().addRoleToMember(event.getMember(),event.getGuild().getRoleById(Roles.User_Blockbench)).complete();
            return "Added Role: Blockbench!";
        } else if(role.equalsIgnoreCase("blender")) {
            event.getGuild().addRoleToMember(event.getMember(),event.getGuild().getRoleById(Roles.User_Blender)).complete();
            return "Added Role: Blender!";
        }
        return "Role not Found! Available Roles: cubik, blockbench, blender";
    }
}
