package com.moraydata.general.primary.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserBasicInformation {

	private Long id;
	private String username;
	private String openId;
	private String notifiedWarningPublicSentiment;
	private String notifiedHotPublicSentiment;
	private String notifiedNegativePublicSentiment;
}
