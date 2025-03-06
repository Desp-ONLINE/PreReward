package org.desp.preReward.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PlayerDto {
    private String userName;
    private String discord_id;
    private String received;
}
