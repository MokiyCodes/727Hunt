package lgbt.nora;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public final class seven27 extends JavaPlugin {

  @Override
  public void onEnable() {
    getLogger().log(Level.INFO,"made by MokiyCodes | made for aga | under the agpl3.0 license | hoes mad <3");
    getLogger().log(Level.INFO,"loaded 727 manhunt");
    getServer().getPluginManager().registerEvents(new PlayerKilledListener(), this);
    Objects.requireNonNull(getCommand("727manhunt")).setExecutor(new SevenTwentySevenCommand());
  }

  @Override
  public void onDisable() {
    getLogger().log(Level.INFO,"dam bro the plugin got disabled pls re-enable xx");
  }

}

