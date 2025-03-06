package org.desp.preReward.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RewardUtils {
    public static String getCurrentDate() {
        Date now = new Date();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = "-";
        dateTime = dateFormatter.format(now.getTime());
        return dateTime;
    }
}
