package ml.jaime;

import ml.jaime.commands.FreezeCommand;
import ml.jaime.commands.MainCmd;
import ml.jaime.commands.MainCmdTabCompleter;
import ml.jaime.commands.StaffCommand;
import ml.jaime.files.ConfigFile;
import ml.jaime.files.ItemsFile;
import ml.jaime.files.MenusFile;
import ml.jaime.files.MessagesFile;
import ml.jaime.listeners.FreezeListener;
import ml.jaime.listeners.InventoryListener;
import ml.jaime.listeners.StaffItemsListener;
import ml.jaime.managers.FreezeManager;
import ml.jaime.managers.MenuManager;
import ml.jaime.managers.StaffManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.skinsrestorer.api.SkinsRestorer;
import net.skinsrestorer.api.SkinsRestorerProvider;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import static ml.jaime.utils.MessageUtils.getColoredMessage;

public final class ModLab extends JavaPlugin {
    private MessagesFile messagesFile;
    private StaffManager staffManager;
    private MenuManager menuManager;
    private ItemsFile itemsFile;
    private MenusFile menusFile;
    private ConfigFile configFile;
    private FreezeManager freezeManager;

    private static Permission perms = null;
    private static Chat chat = null;

    @Override
    public void onEnable() {
        messagesFile = new MessagesFile(this);
        itemsFile = new ItemsFile(this);
        menusFile = new MenusFile(this);
        configFile = new ConfigFile(this);
        staffManager = new StaffManager(this);
        menuManager = new MenuManager(this);
        freezeManager = new FreezeManager(this);
        registerCommands();
        registerEvents();
        hookVault();

        getLogger().info(getColoredMessage("Modlab v" + getDescription().getVersion() + " by Jaime :)"));
    }

    @Override
    public void onDisable() {
        getLogger().info(getColoredMessage("&9Modlab &fdisabled. Good bye!"));
    }

    public MessagesFile getMessagesFile() {
        return messagesFile;
    }

    private void registerCommands(){
        PluginCommand modlabCommand = getCommand("modlab");
        modlabCommand.setTabCompleter(new MainCmdTabCompleter());
        modlabCommand.setExecutor(new MainCmd(this));

        getCommand("staff").setExecutor(new StaffCommand(this));
        getCommand("freeze").setExecutor(new FreezeCommand(this));
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new StaffItemsListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new FreezeListener(this), this);
    }

    public StaffManager getStaffManager() {
        return staffManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public ItemsFile getItemsFile() {
        return itemsFile;
    }

    public void reloadAllFiles(){
        messagesFile.loadMessages();
        itemsFile.loadItems();
    }

    private void hookVault(){
        if(!setupPermissions()){
            getLogger().severe("Vault not found.");
            return;
        }
        setupChat();
        getLogger().info("Vault hooked correctly!");
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
    public ConfigFile getConfigFile() {
        return configFile;
    }

    public MenusFile getMenusFile() {
        return menusFile;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public static Chat getChat() {
        return chat;
    }

    public static Permission getPerms() {
        return perms;
    }

    public FreezeManager getFreezeManager() {
        return freezeManager;
    }
}
