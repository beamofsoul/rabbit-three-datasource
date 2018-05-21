package com.moraydata.general.primary.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMiniInformation {

	private Long cloudUserId;
	private String username;
}
