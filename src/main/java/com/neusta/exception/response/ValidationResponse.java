package com.neusta.exception.response;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResponse {
	private String code;
	private String message;
}
