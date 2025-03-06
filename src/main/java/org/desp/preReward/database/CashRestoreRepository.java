package org.desp.preReward.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.desp.preReward.dto.CashRestoreDto;

import java.util.*;

public class CashRestoreRepository {

    private static CashRestoreRepository instance;
    private final MongoCollection<Document> playerList;
    private final Map<String, CashRestoreDto> cashRestoreData = new HashMap<>();

    public CashRestoreRepository() {
        DatabaseRegister database = new DatabaseRegister();
        this.playerList = database.getDatabase().getCollection("CashRestore");
        loadCashRestoreData();
    }

    public static CashRestoreRepository getInstance() {
        if (instance == null) {
            instance = new CashRestoreRepository();
        }
        return instance;
    }

    public void loadCashRestoreData() {
        FindIterable<Document> documents = playerList.find();
        for (Document document : documents) {
                String username = "";
                Integer amount = 0;
                Set<String> keySet = document.keySet();
                for (String s : keySet) {
                    if(!(s.equalsIgnoreCase("_id") || s.equalsIgnoreCase("receivedMonth"))) {
                        username = s;
                        amount = document.getInteger(s);
                    }
                }
                ArrayList<String> receivedMonths = new ArrayList<>();
                List<Integer> receivedMonth = document.getList("receivedMonth", Integer.class);
                String discord_id = document.getString("discord_id");
                String received = document.getString("received");

            CashRestoreDto cashRestoreDto = CashRestoreDto.builder().userName(username).amount(amount).receivedMonths(receivedMonth).build();
            cashRestoreData.put(username, cashRestoreDto);
        }
    }

//    public void updateCashRestoreData(CashRestoreDto cashRestoreDto) {
//        playerList.updateOne(
//                Filters.elemMatch("reservation", Filters.eq("username", playerData.getUserName())),
//                Updates.set("reservation.$.received", getCurrentDate())
//        );
//
//        if (cashRestoreData.containsKey(playerData.getUserName())) {
//            cashRestoreData.get(playerData.getUserName()).setReceived(getCurrentDate());
//        }
//    }

    public Map<String, CashRestoreDto> getCashCache() {
        return cashRestoreData;
    }
}