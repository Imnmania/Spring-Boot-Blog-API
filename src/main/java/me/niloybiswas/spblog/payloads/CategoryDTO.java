package me.niloybiswas.spblog.payloads;

import java.math.BigInteger;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
	
	private BigInteger categoryId;
	
	@NotEmpty
	private String categoryTitle;
	
	private String categoryDescription;

}
