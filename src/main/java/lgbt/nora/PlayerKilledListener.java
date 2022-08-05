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
  // Full list of "positive" effects:
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
  // Time (unknown measurement) for ShortTimed
  public static int shortTimedTime = 256;
  // Time (unknown measurement) for Timed
  public static int timedTime = shortTimedTime*10;
  // Time (unknown measurement) for non-timed effects
  public static int untimedTime = timedTime*15;
  /////// BEGIN FUNCTIONALITY

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
      if (killer != victim && StringIsInArray(killer.getName(),hunted)) {
        // killer = Hunted
        // victim = Hunter
        Random r = new Random();
        PotionEffectType eff = null;
        while (eff == null || PotionIsInArray(eff, excluded)) {
          int idx = r.nextInt(positiveEffects.size());
          eff = positiveEffects.get(idx);
        }
        int dur = PotionIsInArray(eff, shortTimed) ? shortTimedTime : PotionIsInArray(eff, timed) ? timedTime : untimedTime;
        int amp = 0;
        if (killer.hasPotionEffect(eff))
          amp = Objects.requireNonNull(killer.getPotionEffect(eff)).getAmplifier() + 1;
        PotionEffect effect = new PotionEffect(eff, dur, amp).withParticles(false).withIcon(true);
        killer.addPotionEffect(effect);
        //for (Player allPlayers : Bukkit.getOnlinePlayers())
          //if (allPlayers != killer) {
            //int idx = r.nextInt(Messages.size());
            //String el = Messages.get(idx);//§
            //allPlayers.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8&k727&r &o&8" + el + "&r&8&k727&r"));
          //} else {
        killer.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8&k727&r &a&oYour power increases... &2&oYou got &9&o" + effect.getType().getName() + " &r&8&k727&r"));
      } else if (StringIsInArray(killer.getName(),hunted))
        for (Player plr: Bukkit.getOnlinePlayers())
          plr.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8&k727&r &a"+hunted+" has been defeated by " + killer.getName() + " &8&k727&r"));
    }
  }
}