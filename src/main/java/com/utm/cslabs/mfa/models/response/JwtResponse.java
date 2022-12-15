package com.utm.cslabs.mfa.models.response;

import javax.validation.constraints.NotNull;

public record JwtResponse(@NotNull String accessToken) {
}
