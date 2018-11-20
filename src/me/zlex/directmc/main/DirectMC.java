package me.zlex.directmc.main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.command.CmdManager;
import me.zlex.directmc.commands.*;
import me.zlex.directmc.listeners.*;
import me.zlex.directmc.managers.*;
import me.zlex.directmc.databases.*;
import me.zlex.directmc.utils.LagUtils;
import me.zlex.directmc.database.Database;
import me.zlex.directmc.database.DatabaseManager;
public class DirectMC extends JavaPlugin{
	private static String prefix = "§9DirectMC> §7";
	private static FileConfiguration config;
	private static JavaPlugin plugin;
	private static ArrayList<Player> godmodes = new ArrayList<Player>();
	private static ArrayList<Player> zeuses = new ArrayList<Player>();
	private static ArrayList<Player> tptoggles = new ArrayList<Player>();
	private static ArrayList<Player> onepunches = new ArrayList<Player>();
	private static ArrayList<Player> nightvisions = new ArrayList<Player>();
	private static ArrayList<Player> vanishes = new ArrayList<Player>();
	private static ArrayList<Player> autopickups = new ArrayList<Player>();
	private static ArrayList<Player> freezes = new ArrayList<Player>();
	private static LagUtils lagUtils;
	private static CmdManager cmdManager;
	private static DatabaseManager databaseManager;
	private static TPAManager tpaManager;
	private static EcoManager ecoManager;
	public static CmdManager getCmdManager(){
		return cmdManager;
	}
	public static DatabaseManager getDatabaseManager(){
		return databaseManager;
	}
	public static TPAManager getTPAManager(){
		return tpaManager;
	}
	public static EcoManager getEcoManager(){
		return ecoManager;
	}
	public static FileConfiguration getTConfig(){
		return config;
	}
	public static ArrayList<Player> getGodmodes(){
		return godmodes;
	}
	public static ArrayList<Player> getZeuses(){
		return zeuses;
	}
	public static ArrayList<Player> getTPToggles(){
		return tptoggles;
	}
	public static ArrayList<Player> getOnepunches(){
		return onepunches;
	}
	public static ArrayList<Player> getNightvisions(){
		return nightvisions;
	}
	public static ArrayList<Player> getVanishes(){
		return vanishes;
	}
	public static ArrayList<Player> getAutoPickups(){
		return autopickups;
	}
	public static ArrayList<Player> getFreezes(){
		return freezes;
	}
	public static boolean hasGodmode(Player p){
		return godmodes.contains(p);
	}
	public static boolean hasZeus(Player p){
		return zeuses.contains(p);
	}
	public static boolean hasTPToggle(Player p){
		return tptoggles.contains(p);
	}
	public static boolean hasOnepunch(Player p){
		return onepunches.contains(p);
	}
	public static boolean hasNightvision(Player p){
		return nightvisions.contains(p);
	}
	public static boolean hasVanish(Player p){
		return vanishes.contains(p);
	}
	public static boolean hasAutoPickup(Player p){
		return autopickups.contains(p);
	}
	public static boolean hasFreeze(Player p){
		return freezes.contains(p);
	}
	public static LagUtils getLagUtils(){
		return lagUtils;
	}
	public void onEnable(){
		cmdManager = new CmdManager();
		databaseManager = new DatabaseManager();
		tpaManager = new TPAManager();
		ecoManager = new EcoManager();
		addCommands();
		addDatabases();
		File file = new File(getDataFolder(), "config.yml");
		if(!file.exists()){
			try{file.createNewFile();}catch(IOException e){}
			getConfig().createSection("general");
			getConfig().createSection("commands");
			getConfig().createSection("economy");
			getConfig().createSection("functions");
			getConfig().getConfigurationSection("functions").createSection("command-blocking");
			ConfigurationSection general = getConfig().getConfigurationSection("general");
			ConfigurationSection commands = getConfig().getConfigurationSection("commands");
			ConfigurationSection economy = getConfig().getConfigurationSection("economy");
			economy.addDefault("enabled", true);
			economy.addDefault("vault-symbol", "$");
			economy.addDefault("min-money", 0.0D);
			economy.addDefault("max-money", Double.MAX_VALUE);
			economy.addDefault("start-money", 0.0D);
			ConfigurationSection functions = getConfig().getConfigurationSection("functions");
			functions.createSection("command-blocking");
			functions.createSection("auto-pickup");
			functions.createSection("mobspawner-silktouch");
			functions.createSection("custom-messages");
			functions.createSection("creative-enderpearl");
			functions.createSection("enderpearl-cooldown");
			functions.createSection("ping-fix");
			ConfigurationSection commandblocking = functions.getConfigurationSection("command-blocking");
			ConfigurationSection autopickup = functions.getConfigurationSection("auto-pickup");
			ConfigurationSection custommessages = functions.getConfigurationSection("custom-messages");
			ConfigurationSection mobspawnersilktouch = functions.getConfigurationSection("mobspawner-silktouch");
			ConfigurationSection creativeenderpearl = functions.getConfigurationSection("creative-enderpearl");
			ConfigurationSection enderpearlcooldown = functions.getConfigurationSection("enderpearl-cooldown");
			ConfigurationSection pingfix = functions.getConfigurationSection("ping-fix");
			general.addDefault("prefix", prefix.replace("§", "&"));
			general.addDefault("spawn-location", "777 5 777 90 90 world");
			general.addDefault("force-spawn-on-join", false);
			for (Cmd cmd: cmdManager.getCommands()){
				commands.createSection(cmd.getName().toLowerCase());
				for (String s: cmd.getStrings().keySet()){
					getConfig().getConfigurationSection("commands").getConfigurationSection(cmd.getName().toLowerCase()).addDefault(s, cmd.getStrings().get(s));
				}
				for (String s: cmd.getIntegers().keySet()){
					getConfig().getConfigurationSection("commands").getConfigurationSection(cmd.getName().toLowerCase()).addDefault(s, cmd.getIntegers().get(s));
				}
				for (String s: cmd.getDoubles().keySet()){
					getConfig().getConfigurationSection("commands").getConfigurationSection(cmd.getName().toLowerCase()).addDefault(s, cmd.getDoubles().get(s));
				}
				for (String s: cmd.getBooleans().keySet()){
					getConfig().getConfigurationSection("commands").getConfigurationSection(cmd.getName().toLowerCase()).addDefault(s, cmd.getBooleans().get(s));
				}
			}
			commandblocking.addDefault("enabled", false);
			commandblocking.addDefault("command-blocked-message", "&7This command is blocked.");
			commandblocking.addDefault("blocked-commands", "pl plugins");
			autopickup.addDefault("enabled", false);
			autopickup.addDefault("toggled-on-join", true);
			autopickup.addDefault("blocks", true);
			autopickup.addDefault("entities", true);
			mobspawnersilktouch.addDefault("enabled", false);
			mobspawnersilktouch.addDefault("mobspawner-name", "&e{TYPE} &7Spawner");
			custommessages.addDefault("enabled", true);
			custommessages.addDefault("flying-not-enabled", "&fFlying is not enabled on this server");
			custommessages.addDefault("player-join", "&e{PLAYER} joined the game");
			custommessages.addDefault("player-left", "&e{PLAYER} left the game");
			custommessages.addDefault("unknown-cmd", "&fUnknown command. Type '/help' for help.");
			custommessages.addDefault("disconnect-spam", "&fdisconnect.spam");
			custommessages.addDefault("illegal-characters", "&fIllegal characters in chat");
			custommessages.addDefault("too-many-packets", "&fYou are sending too many packets!");
			custommessages.addDefault("already-online", "&fThis player is already online");
			creativeenderpearl.addDefault("enabled", true);
			enderpearlcooldown.addDefault("enabled", false);
			enderpearlcooldown.addDefault("delay", 3000L);
			enderpearlcooldown.addDefault("remaining-delay", "&7You can not use the ender pearl now because you need to wait another &e{SECONDS} &7seconds.");
			pingfix.addDefault("enabled", true);
			getConfig().options().copyDefaults(true);
			saveConfig();
			reloadConfig();
			print("Config saved.");
		}else{
			reloadConfig();
			print("Config reloaded.");
		}
		for (Database data: databaseManager.getDatabases()){
			try{
				data.reload();
			}catch(Exception e1){}
		}
		prefix = getConfig().getConfigurationSection("general").getString("prefix").replace("&", "§");
		getDataFolder();
		config = getConfig();
		plugin = this;
		lagUtils = new LagUtils(this);
		RegisterListener(new EntityDamageListener());
		RegisterListener(new PlayerJoinListener());
		RegisterListener(new PlayerInteractListener());
		RegisterListener(new PreProcessCommandListener());
		RegisterListener(new BlockBreakListener());
		RegisterListener(new EntityDeathListener());
		RegisterListener(new AsyncPlayerChatListener());
		RegisterListener(new PlayerMoveListener());
		RegisterListener(new PlayerKickListener());
		RegisterListener(new PlayerQuitListener());
		RegisterListener(new PlayerLoginListener());
		RegisterListener(lagUtils);
	}
	public void addCommands(){
		addCommand(new FlyCmd());
		addCommand(new HealCmd());
		addCommand(new FeedCmd());
		addCommand(new DirectMCCmd());
		addCommand(new GodCmd());
		addCommand(new HatCmd());
		addCommand(new SuicideCmd());
		addCommand(new ExtinguishCmd());
		addCommand(new GmCmd());
		addCommand(new GmsCmd());
		addCommand(new GmcCmd());
		addCommand(new GmaCmd());
		addCommand(new WorkbenchCmd());
		addCommand(new SpawnCmd());
		addCommand(new SetspawnCmd());
		addCommand(new RepairCmd());
		addCommand(new DirectCmd());
		addCommand(new ZeusCmd());
		addCommand(new XPCmd());
		addCommand(new TPAllCmd());
		addCommand(new TPToggleCmd());
		addCommand(new WorldCmd());
		addCommand(new WorldsCmd());
		addCommand(new KillCmd());
		addCommand(new ClearCmd());
		addCommand(new InvseeCmd());
		addCommand(new EnderchestCmd());
		addCommand(new EnderseeCmd());
		addCommand(new NightvisionCmd());
		addCommand(new OnepunchCmd());
		addCommand(new PingCmd());
		addCommand(new NearCmd());
		addCommand(new LevelupCmd());
		addCommand(new VanishCmd());
		addCommand(new GetPosCmd());
		addCommand(new AutoPickupCmd());
		addCommand(new SpeedCmd());
		addCommand(new FreezeCmd());
		addCommand(new UnfreezeCmd());
		addCommand(new SetwarpCmd());
		addCommand(new DelwarpCmd());
		addCommand(new WarpCmd());
		addCommand(new WarpsCmd());
		addCommand(new ThorCmd());
		addCommand(new TopCmd());
		addCommand(new GiveCmd());
		addCommand(new SkullCmd());
		addCommand(new BroadcastCmd());
		addCommand(new TPACmd());
		addCommand(new TPAcceptCmd());
		addCommand(new TPDenyCmd());
		addCommand(new TPAHereCmd());
		addCommand(new FireballCmd());
		addCommand(new BalanceCmd());
		addCommand(new EconomyCmd());
		addCommand(new PayCmd());
		addCommand(new TPHereCmd());
	}
	public void addDatabases(){
		addDatabase(new WarpDatabase());
		addDatabase(new EconomyDatabase());
	}
	public void addDatabase(Database database){
		databaseManager.addDatabase(database);
	}
	public void addCommand(Cmd cmd){
		getCommand(cmd.getName().toLowerCase()).setExecutor(cmd);
		cmdManager.addCommand(cmd);
	}
	public static Plugin getPlugin(){
		return Bukkit.getServer().getPluginManager().getPlugin("DirectMC");
	}
	public static JavaPlugin getJavaPlugin(){
		return plugin;
	}
	public static String getPrefix(){
		return prefix;
	}
	public void RegisterListener(Listener listener){
		getServer().getPluginManager().registerEvents(listener, (Plugin) this);
	}
	public static void sendMessage(Player p, String msg){
		if (!(msg == "")){
			p.sendMessage(prefix + msg.replace("&", "§"));
		}
	}
	public static void print(String line){
		System.out.println("[DirectMC] " + line);
	}
	public static void msg(Player p, String msg){
		p.sendMessage(prefix + msg);
	}
}