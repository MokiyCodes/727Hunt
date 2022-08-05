package lgbt.nora;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PlayerKilledListener implements Listener {
  ///////////////////////////////////////////////////////////////////////////
  ///////                        BEGIN CONFIG                         ///////
  /////// IF YOU DO NOT UNDERSTAND AN OPTION HERE, DO NOT TO TOUCH IT ///////
  ///////////////////////////////////////////////////////////////////////////
  // Full list of "positive" effects (Selected unless in excluded):
  List<PotionEffectType> positiveEffects = Arrays.asList(PotionEffectType.HEAL, PotionEffectType.SPEED, PotionEffectType.FAST_DIGGING, PotionEffectType.INCREASE_DAMAGE, PotionEffectType.JUMP, PotionEffectType.REGENERATION, PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.WATER_BREATHING, PotionEffectType.INVISIBILITY, PotionEffectType.HEALTH_BOOST, PotionEffectType.ABSORPTION, PotionEffectType.SATURATION, PotionEffectType.LUCK, PotionEffectType.SLOW_FALLING, PotionEffectType.CONDUIT_POWER, PotionEffectType.DOLPHINS_GRACE, PotionEffectType.HERO_OF_THE_VILLAGE, PotionEffectType.GLOWING, PotionEffectType.LEVITATION); // levitation cuz celestial suggested it cuz funni
  // List of effects to exclude from the above:
  List<PotionEffectType> excluded = Arrays.asList(PotionEffectType.HEAL, PotionEffectType.GLOWING);
  // List of effects to put on a timer:
  List<PotionEffectType> timed = Arrays.asList(PotionEffectType.INVISIBILITY, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.DAMAGE_RESISTANCE);
  // List of effects to put on a short timer:
  List<PotionEffectType> shortTimed = Arrays.asList(PotionEffectType.REGENERATION, PotionEffectType.LEVITATION);
  // List of people being hunted (people to get effects)
  public static List<String> hunted = Arrays.asList("N","ilovemorbius","");
  // Should we give everyone the effect?
  public static boolean applyEffectsToEveryone = false;
  // Should we announce when the hunted person dies?
  public static boolean announceHuntedDeath = false;
  // Time (unknown measurement) for ShortTimed
  public static int shortTimedTime = 256;
  // Time (unknown measurement) for Timed
  public static int timedTime = shortTimedTime*10;
  // Time (unknown measurement) for non-timed effects
  public static int untimedTime = timedTime*15;

  ////////////////////////////////////////////////////////////////////////////
  ///////                    BEGIN FUNCTIONALITY                       ///////
  /////// DO NOT TOUCH ANYTHING BELOW HERE UNLESS YK WHAT YOU'RE DOING ///////
  ////////////////////////////////////////////////////////////////////////////
  // Check if a potion is in a specified array
  public boolean PotionIsInArray(PotionEffectType type, List<PotionEffectType> list)
  {
    for (PotionEffectType item : list)
      if (item == type)
        return true;
    return false;
  }
  // Check if a string is in an array
  public boolean StringIsInArray(String type, List<String> list)
  {
    for (String item : list)
      if (Objects.equals(item, type))
        return true;
    return false;
  }
  // Player Killed Event (Main Logic)
  @EventHandler
  public void onPlayerKilled(PlayerDeathEvent e) {
    Player victim = e.getEntity();
    if (victim.getKiller() != null) {
      Player killer = victim.getKiller();
      if (killer != victim && (applyEffectsToEveryone || StringIsInArray(killer.getName(),hunted))) {
        // killer = Hunted
        // victim = Hunter
        Random r = new Random();
        PotionEffectType eff = null;
        while (eff == null || PotionIsInArray(eff, excluded)) { // Repeat Effect Loop until applicable event found
          int idx = r.nextInt(positiveEffects.size());
          eff = positiveEffects.get(idx);
        }
        // Define Effect Duration
        int dur = PotionIsInArray(eff, shortTimed) ? shortTimedTime : PotionIsInArray(eff, timed) ? timedTime : untimedTime;
        // Define Amplifier as 0
        int amp = 0;
        // If the player has an effect, increase their effect amplifier by one
        if (killer.hasPotionEffect(eff))
          amp = Objects.requireNonNull(killer.getPotionEffect(eff)).getAmplifier() + 1;
        // Create Effect Instance
        PotionEffect effect = new PotionEffect(eff, dur, amp).withParticles(false).withIcon(true);
        // Add the potion effect
        killer.addPotionEffect(effect);
        // Send the player a message regarding the type of effect they got
        killer.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8&k727&r &a&oYour power increases... &2&oYou got &9&o" + effect.getType().getName() + " &r&8&k727&r"));
      } else if (StringIsInArray(killer.getName(),hunted) && announceHuntedDeath) // Tell everyone that they got defeated
        for (Player plr: Bukkit.getOnlinePlayers())
          plr.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8&k727&r &a"+hunted+" has been defeated by " + killer.getName() + " &8&k727&r"));
    }
  }
}