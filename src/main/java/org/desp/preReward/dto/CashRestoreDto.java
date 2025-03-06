package org.desp.preReward.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
@Builder
public class CashRestoreDto {
    private String userName;
    private Integer amount;
    private ArrayList<Integer> receivedMonths;
}
