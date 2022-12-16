package com.utm.cslabs.mfa.models.request;

import java.util.Set;

public record RegisterRequest(String username, String email, String password, Set<String> role) {
}
