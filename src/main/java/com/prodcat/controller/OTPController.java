package com.prodcat.controller;

import com.prodcat.DTO.ResponseOtpDTO;
import com.prodcat.model.User;
import com.prodcat.repository.UserRepository;
import com.prodcat.service.OTPService;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.persistence.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class OTPController {

    private final UserRepository userRepository;
    private final OTPService otpService;

    public OTPController(UserRepository userRepository, OTPService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    @Transactional
    @PostMapping("/forgot-password/{user}")
    public ResponseOtpDTO esqueciASenha(@PathVariable("user") String user) throws MessagingException {
        if (StringUtils.isEmpty(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'email' é obrigatório e não pode estar vazio");
        }

        User emailEncontrado = userRepository.findByUsername(user)
                .orElseThrow(() ->
                        new NoResultException(HttpStatus.NO_CONTENT + "Usuário "+ user + " não encontrado!")
                );

        if (emailEncontrado != null || StringUtils.isEmpty(emailEncontrado.getUsername())) {
            return otpService.sendEmail(emailEncontrado.getUsername());
        }

        return null;

    }

    @PostMapping("/validate")
    public Map<String, Object> validateOtp(
            @RequestParam String token,
            @RequestParam String userEnteredOtp) {

        try {
            return otpService.validateOtp(token, userEnteredOtp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
