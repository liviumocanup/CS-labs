package com.utm.cslabs.mfa.controller;

import com.utm.cslabs.mfa.service.CipherService;
import com.utm.cslabs.mfa.models.response.CipherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cipher")
@RequiredArgsConstructor
public class CipherController {
    private final CipherService cipherService;

    @GetMapping("/caesar")
    @PreAuthorize("hasAuthority('CLASSIC')")
    @ResponseStatus(HttpStatus.OK)
    public CipherResponse caesarCipher(@RequestParam String message, @RequestParam int shift) {
        return cipherService.caesar(message, shift);
    }

    @GetMapping("/rsa")
    @PreAuthorize("hasAuthority('ASYMMETRIC')")
    @ResponseStatus(HttpStatus.OK)
    public CipherResponse asymmetricCipher(@RequestParam String message) {
        return cipherService.asymmetric(message);
    }
}
