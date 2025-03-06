package org.desp.preReward.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder
public class CashRestoreDto {
    private String userName;
    private Integer amount;
    private List<Integer> receivedMonths;
}
