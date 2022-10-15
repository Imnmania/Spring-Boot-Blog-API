package me.niloybiswas.spblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ApiResponseDTO {
	
	private String message;
	private Boolean success;
	
}
