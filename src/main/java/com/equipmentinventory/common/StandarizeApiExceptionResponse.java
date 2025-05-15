package com.equipmentinventory.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "This model is used to return errors in RFC 7807 which created a generalized  error-hanbdling schema composed by five parts")
@NoArgsConstructor
@Data
public class StandarizeApiExceptionResponse {
	
	@Schema(description = "The unique uri identifier that categorizes the error", name = "type", requiredMode = Schema.RequiredMode.REQUIRED, example = "/errors/authentication/not-autorized")
	private String type;
	@Schema(description = "A brief human readable message about the error", name = "title", requiredMode = Schema.RequiredMode.REQUIRED, example = "The user don't have autorization")
	private String title;
	@Schema(description = "A unique error code", name = "code", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
	private String code;
	@Schema(description = "A brief human readable explanation about the error", name = "detail", requiredMode = Schema.RequiredMode.REQUIRED, example = "The user doesn't have the propertly permissions to access"
			+ " the resource, please contact with us https://thecontactpage.com ")
	private String detail;

	
	
	
	public StandarizeApiExceptionResponse(String type, String title, String code, String detail) {
		super();
		this.type = type;
		this.title = title;
		this.code = code;
		this.detail = detail;
	}
	
	

}
