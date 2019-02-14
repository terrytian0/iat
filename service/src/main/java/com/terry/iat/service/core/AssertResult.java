package com.terry.iat.service.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssertResult {
    boolean status;
    String message;
}
