package org.desp.preReward.database;

import static org.desp.preReward.utils.RewardUtils.getCurrentDate;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.desp.preReward.dto.PlayerDto;

public class ReservationRepository {

    private static ReservationRepository instance;
    private final MongoCollection<Document> playerList;
    private final Map<String, PlayerDto> reservationData = new HashMap<>();

    public ReservationRepository() {
        DatabaseRegister database = new DatabaseRegister();
        this.playerList = database.getDatabase().getCollection("Reservation");
        loadReservationData();
    }

    public static ReservationRepository getInstance() {
        if (instance == null) {
            instance = new ReservationRepository();
        }
        return instance;
    }

    public void loadReservationData() {
        FindIterable<Document> documents = playerList.find();
        for (Document document : documents) {
            List<Document> reservations = (List<Document>) document.get("reservation");
            for (Document res : reservations) {
                String username = res.getString("username");
                String discord_id = res.getString("discord_id");
                String received = res.getString("received");

                PlayerDto playerData = PlayerDto.builder()
                        .userName(username)
                        .discord_id(discord_id)
                        .received(received)
                        .build();
                reservationData.put(username, playerData);
            }
        }
    }

    public void updateReservationData(PlayerDto playerData) {
        playerList.updateOne(
                Filters.elemMatch("reservation", Filters.eq("username", playerData.getUserName())),
                Updates.set("reservation.$.received", getCurrentDate())
        );

        if (reservationData.containsKey(playerData.getUserName())) {
            reservationData.get(playerData.getUserName()).setReceived(getCurrentDate());
        }
    }

    public Map<String, PlayerDto> getAllReservations() {
        return reservationData;
    }
}