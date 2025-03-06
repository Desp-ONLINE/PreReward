package org.desp.preReward;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.desp.preReward.command.ReservationCommand;
import org.desp.preReward.database.ReservationRepository;

public final class PreReward extends JavaPlugin {

    @Getter
    private static PreReward instance;

    @Override
    public void onEnable() {
        instance = this;
        register();
        getCommand("보상수령").setExecutor(new ReservationCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void register() {
        ReservationRepository.getInstance();
    }
}
