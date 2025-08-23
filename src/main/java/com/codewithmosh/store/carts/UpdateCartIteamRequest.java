package com.codewithmosh.store.carts;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartIteamRequest {
    @NotNull(message = "Quantity should not be null")
    @Min(value=1,message = "Quantity should in b/w 1 to 100")
    @Max(value = 100,message = "Quantity should in b/w 1 to 100")
    public Integer quantity;
}
