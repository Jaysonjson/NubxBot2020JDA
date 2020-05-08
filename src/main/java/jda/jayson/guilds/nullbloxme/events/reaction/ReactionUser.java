package jda.jayson.guilds.nullbloxme.events.reaction;

import jda.jayson.Nubx;
import jda.jayson.id.Channels;
import jda.jayson.id.Emojis;
import jda.jayson.id.Roles;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import java.util.Objects;

public class ReactionUser {
    public static void unReact(MessageReactionRemoveEvent e) {
    Member user = e.getMember();
    MessageChannel channel = e.getChannel();
    MessageReaction.ReactionEmote emoji = e.getReactionEmote();
    Role role_cubik_user = e.getGuild().getRoleById(Roles.User_Cubik);
    Role role_blender_user = e.getGuild().getRoleById(Roles.User_Blender);
    Role role_blockbench_user = e.getGuild().getRoleById(Roles.User_Blockbench);
    Role role_to_give = role_cubik_user;
    if (channel.getIdLong() == Channels.User_Settings) {
        if (!emoji.isEmoji()) {
            if (emoji.getIdLong() == Emojis.Cubik_Logo_ID) {
                role_to_give = role_cubik_user;
            }
            if (emoji.getIdLong() == Emojis.Blender_Logo_ID) {
                role_to_give = role_blender_user;
            }
            if (emoji.getIdLong() == Emojis.Blockbench_Logo_ID) {
                role_to_give = role_blockbench_user;
            }
            if (Objects.requireNonNull(e.getMember()).getRoles().contains(role_to_give)) {
                e.getGuild().removeRoleFromMember(e.getMember(), role_to_give).complete();
                e.getMember().getUser().openPrivateChannel().complete().sendMessage("Removed Role " + role_to_give.getName().toUpperCase() + "!");
                Nubx.logger.print("Removed the Role " + role_to_give.getName().toUpperCase() + " from User " + e.getMember().getUser().getName());
            }
        } else if (emoji.getEmoji().equalsIgnoreCase("✅")) {
            assert user != null;
            if (e.getMessageIdLong() == 653060062826463242L) {
                if (Objects.requireNonNull(e.getMember()).getRoles().contains(e.getGuild().getRoleById(Roles.Artist_To_Hire))) {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You no longer receive pings inside " + e.getGuild().getTextChannelById(Channels.Recruitment).getAsMention() + "!").complete();
                    e.getGuild().removeRoleFromMember(user, Objects.requireNonNull(e.getGuild().getRoleById(Roles.Artist_To_Hire))).complete();
                }
            }
            if (e.getMessageIdLong() == 653060120346886154L) {
                if (Objects.requireNonNull(e.getMember()).getRoles().contains(e.getGuild().getRoleById(Roles.RandomMessage))) {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You no longer access " + e.getGuild().getTextChannelById(Channels.RandomMessage).getAsMention() + "!").complete();
                    e.getGuild().removeRoleFromMember(user, Objects.requireNonNull(e.getGuild().getRoleById(Roles.RandomMessage))).complete();
                }
            }
            if (e.getMessageIdLong() == 653060120346886154L) {
                if (Objects.requireNonNull(e.getMember()).getRoles().contains(e.getGuild().getRoleById(Roles.NSFW))) {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You no longer access NSFW channels!").complete();
                    e.getGuild().removeRoleFromMember(user, Objects.requireNonNull(e.getGuild().getRoleById(Roles.NSFW))).complete();
                }
            }
        }
    }
}

    public static void React(MessageReactionAddEvent e) {
        MessageChannel channel = e.getChannel();
        MessageReaction.ReactionEmote emoji = e.getReaction().getReactionEmote();
        Role role_cubik_user = e.getGuild().getRoleById(Roles.User_Cubik);
        Role role_blender_user = e.getGuild().getRoleById(Roles.User_Blender);
        Role role_blockbench_user = e.getGuild().getRoleById(Roles.User_Blockbench);
        Role role_to_give = role_cubik_user;

        if (channel.getIdLong() == Channels.User_Settings) {
            Member user = e.getMember();
            if(!emoji.isEmoji()) {
                    if (emoji.getIdLong() == Emojis.Cubik_Logo_ID) {
                        role_to_give = role_cubik_user;
                    }
                    if (emoji.getIdLong() == Emojis.Blender_Logo_ID) {
                        role_to_give = role_blender_user;
                    }
                    if (emoji.getIdLong() == Emojis.Blockbench_Logo_ID) {
                        role_to_give = role_blockbench_user;
                    }
                    assert role_to_give != null;
                    e.getGuild().addRoleToMember(Objects.requireNonNull(e.getMember()), role_to_give).complete();
                    Nubx.logger.print("Added the Role " + role_to_give.getName().toUpperCase() + " to User " + e.getMember().getUser().getName());
                    if (!e.getMember().getRoles().contains(role_to_give)) {
                        e.getGuild().addRoleToMember(e.getMember(), role_to_give).complete();
                        Nubx.logger.print("Added the Role " + role_to_give.getName().toUpperCase() + " to User " + e.getMember().getUser().getName());
                        e.getMember().getUser().openPrivateChannel().complete().sendMessage("> Added Role " + role_to_give.getName().toUpperCase() + "!").complete();
                    }
            } else if (emoji.getEmoji().equalsIgnoreCase("✅")) {
                assert user != null;
                if (e.getMessageIdLong() == 653060062826463242L) {
                    if (!Objects.requireNonNull(e.getMember()).getRoles().contains(e.getGuild().getRoleById(Roles.Artist_To_Hire))) {
                        user.getUser().openPrivateChannel().complete().sendMessage("> You now receive pings inside " + e.getGuild().getTextChannelById(Channels.Recruitment).getAsMention() + "!").complete();
                        e.getGuild().addRoleToMember(user, Objects.requireNonNull(e.getGuild().getRoleById(Roles.Artist_To_Hire))).complete();
                    } else {
                        user.getUser().openPrivateChannel().complete().sendMessage("> You receive pings already!").complete();
                    }
                }
                if (e.getMessageIdLong() == 653060120346886154L) {
                    if (!Objects.requireNonNull(e.getMember()).getRoles().contains(e.getGuild().getRoleById(Roles.RandomMessage))) {
                        user.getUser().openPrivateChannel().complete().sendMessage("> You can now access " + e.getGuild().getTextChannelById(Channels.RandomMessage).getAsMention() + "!").complete();
                        e.getGuild().addRoleToMember(user, Objects.requireNonNull(e.getGuild().getRoleById(Roles.RandomMessage))).complete();
                    } else {
                        user.getUser().openPrivateChannel().complete().sendMessage("> You already have access to " + e.getGuild().getTextChannelById(Channels.RandomMessage).getAsMention() + "!").complete();
                    }
                }
                if (e.getMessageIdLong() == 653060609205600256L) {
                    if (!Objects.requireNonNull(e.getMember()).getRoles().contains(e.getGuild().getRoleById(Roles.NSFW))) {
                        user.getUser().openPrivateChannel().complete().sendMessage("> You can now access NSFW channels !").complete();
                        e.getGuild().addRoleToMember(user, Objects.requireNonNull(e.getGuild().getRoleById(Roles.NSFW))).complete();
                    } else {
                        user.getUser().openPrivateChannel().complete().sendMessage("> You already have access to NSFW channels").complete();
                    }
                }
            }
        }
    }
}
