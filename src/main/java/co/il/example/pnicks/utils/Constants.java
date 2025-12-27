package co.il.parlagames.pnicks.utils;

import java.util.ArrayList;
import java.util.List;

import static co.il.parlagames.pnicks.utils.Utils.color;
import static co.il.parlagames.pnicks.utils.Utils.getConfig;

public class Constants {

    // Messages
    public static String no_perms = color("&cNo Permission.");
    public static String illegal_nick = color("&cIllegal Nickname.");
    public static String set_nick = color("&aNick has been set to &r%nick%");
    public static String removed_nick = color("&aNick has been reset.");
    public static String wrong_syntax = color("&cWrong Syntax! %syntax%");
    public static String type_nick = color("&aPlease enter your custom nickname:");
    public static String taken_nick = color("&cNickname is already taken.");
    // Menu
    public static String reset_button_name = color("&cReset Nickname");
    public static List<String> reset_button_lore = new ArrayList<>();
    public static String exit_button_name = color("&cExit");
    public static List<String> exit_button_lore = new ArrayList<>();
    public static String info_button_name = color("&aCurrent Nickname: &r%player%");
    public static List<String> info_button_lore = new ArrayList<>();
    public static String custom_button_name = color("&eEMERALD");
    public static List<String> custom_button_lore = new ArrayList<>();
    public static String list_button_name = color("&6GOLD");
    public static List<String> list_button_lore = new ArrayList<>();
    public static String random_button_name = color("&3SILVER");
    public static List<String> random_button_lore = new ArrayList<>();
    public static String next_button_name = color("&dNext");
    public static List<String> next_button_lore = new ArrayList<>();
    public static String back_button_name = color("&dBack");
    public static List<String> back_button_lore = new ArrayList<>();
    public static String choose_button_name = color("&6Choose: &r%nick%");
    public static List<String> choose_button_lore = new ArrayList<>();
    public static String list_title = color("List Menu #%number%");
    public static String nicks_title = color("&6Nicknames Menu");
    public static String exit_command = color("say hi");

    public static int reset_data = 0;
    public static int exit_data = 0;
    public static int info_data = 0;
    public static int custom_data = 0;
    public static int list_data = 0;
    public static int random_data = 0;
    public static int next_data = 0;
    public static int back_data = 0;
    public static int choose_data = 0;
    // Lists
    public static List<String> nicks_list = new ArrayList<>();
    public static List<String> forbidden_list = new ArrayList<>();

    public static void setupConstants() {
        no_perms = color(getConfig().getString("no-perms"));
        illegal_nick = color(getConfig().getString("illegal-nick"));
        set_nick = color(getConfig().getString("set-nick"));
        removed_nick = color(getConfig().getString("removed-nick"));
        wrong_syntax = color(getConfig().getString("wrong-syntax"));
        type_nick = color(getConfig().getString("type-nick"));
        taken_nick = color(getConfig().getString("taken-nick"));

        reset_button_name = color(getConfig().getString("reset.name"));
        reset_button_lore = color(getConfig().getStringList("reset.lore"));
        exit_button_name = color(getConfig().getString("exit.name"));
        exit_button_lore = color(getConfig().getStringList("exit.lore"));
        info_button_name = color(getConfig().getString("info.name"));
        info_button_lore = color(getConfig().getStringList("info.lore"));
        custom_button_name = color(getConfig().getString("custom.name"));
        custom_button_lore = color(getConfig().getStringList("custom.lore"));
        list_button_name = color(getConfig().getString("list.name"));
        list_button_lore = color(getConfig().getStringList("list.lore"));
        random_button_name = color(getConfig().getString("random.name"));
        random_button_lore = color(getConfig().getStringList("random.lore"));

        next_button_name = color(getConfig().getString("next.name"));
        next_button_lore = color(getConfig().getStringList("next.lore"));
        back_button_name = color(getConfig().getString("back.name"));
        back_button_lore = color(getConfig().getStringList("back.lore"));
        choose_button_name = color(getConfig().getString("choose.name"));
        choose_button_lore = color(getConfig().getStringList("choose.lore"));

        reset_data = getConfig().getInt("reset.custom-data");
        exit_data = getConfig().getInt("exit.custom-data");
        info_data = getConfig().getInt("info.custom-data");
        custom_data = getConfig().getInt("custom.custom-data");
        list_data = getConfig().getInt("list.custom-data");
        random_data = getConfig().getInt("random.custom-data");
        next_data = getConfig().getInt("next.custom-data");
        back_data = getConfig().getInt("back.custom-data");
        choose_data = getConfig().getInt("choose.custom-data");
        exit_command = color(getConfig().getString("exit.command"));

        list_title = color(getConfig().getString("list-title"));
        nicks_title = color(getConfig().getString("nicks-title"));

        nicks_list = color(getConfig().getStringList("nick-list"));
        forbidden_list = color(getConfig().getStringList("forb"));
    }

}
