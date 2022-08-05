package lgbt.nora;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SevenTwentySevenCommand implements CommandExecutor {
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a727Hunt by MokiyCodes"));
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&2A plugin to spice up your Manhunts"));
    sender.sendMessage("");
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&9https://github.com/MokiyCodes/727Hunt"));
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&9Licensed under the AGPL-3.0"));
    return false;
  }
}
