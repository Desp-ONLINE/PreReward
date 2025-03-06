package org.desp.preReward.command;

import com.binggre.mmomail.MMOMail;
import com.binggre.mmomail.objects.Mail;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.desp.preReward.database.ReservationRepository;
import org.desp.preReward.dto.PlayerDto;
import org.jetbrains.annotations.NotNull;

public class ReservationCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s,
                             @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length == 0) {
            player.sendMessage("§7 > /보상수령 사전예약 - 사전예약 보상을 수령합니다.");
            return false;
        }
        switch (strings[0]) {
            case "사전예약":
                giveReward(player);
                return true;
            default:
                player.sendMessage("§7 > /보상수령 사전예약 - 사전예약 보상을 수령합니다.");
        }
        return false;
    }

    public void giveReward(Player player) {
        ReservationRepository repository = ReservationRepository.getInstance();
        Map<String, PlayerDto> allReservations = repository.getAllReservations();

        String playerName = player.getName();
        if (allReservations.containsKey(playerName)) {
            if(!allReservations.get(playerName).getReceived().isEmpty()){
               player.sendMessage("§c이미 보상을 수령하였습니다!");
               return;
            }
            sendReward(getReward(), player);
            repository.updateReservationData(allReservations.get(playerName));
        }
        else {
            player.sendMessage("§c사전예약자가 아닙니다!");
        }
    }

    public List<ItemStack> getReward() {
        List<ItemStack> rewards = new ArrayList<>();

        ItemStack rewardItem = MMOItems.plugin.getItem(Type.MISCELLANEOUS, "기타_세계의핵");
        rewardItem.setAmount(35);
        rewards.add(rewardItem);

        return rewards;
    }

    public static void sendReward(List<ItemStack> reward, Player player) {
        MMOMail mmoMail = MMOMail.getInstance();
        Mail rewardMail = mmoMail.getMailAPI().createMail(
                "시스템",
                "사전예약 보상입니다.",
                0,
                reward
        );
        mmoMail.getMailAPI().sendMail(player.getName(), rewardMail);
    }
}
