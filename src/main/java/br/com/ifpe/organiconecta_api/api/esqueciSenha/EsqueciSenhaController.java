package br.com.ifpe.organiconecta_api.api.esqueciSenha;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifpe.organiconecta_api.modelo.acesso.Usuario;
import br.com.ifpe.organiconecta_api.modelo.acesso.PasswordResetService;
import br.com.ifpe.organiconecta_api.modelo.acesso.PasswordResetToken;
import br.com.ifpe.organiconecta_api.modelo.acesso.PasswordResetTokenRepository;
import br.com.ifpe.organiconecta_api.modelo.acesso.UsuarioRepository;
import jakarta.mail.MessagingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/redefinir")
@CrossOrigin

@Tag(
    name = "API Esqueci Senha",
    description = "API responsável pelos serviços de Esqueci Senha no sistema"
)

public class EsqueciSenhaController {
    
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    

    @Operation(
        summary = "Serviço responsável por recuperar senha no sistema."
    )
    //Nessa rota, deve-se colocar o email para procurar se existe no banco. Se existir o email será enviado com o link para redefinição.
    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestParam String email) throws MessagingException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(email);
        if (usuarioOptional.isPresent()) {
            passwordResetService.sendResetPasswordEmail(usuarioOptional.get(), email);
            return ResponseEntity.ok("E-mail de redefinição enviado.");
        }
        return ResponseEntity.badRequest().body("Conta não encontrada.");
    }

    @Operation(
        summary = "Serviço responsável por gerar o token e a recuperação de senha do cliente no sistema."
    )
    //Nessa rota, o token é colocado e a nova senha é trocada no banco. O token vem pela url e por json será colocada a nova senha
    @PostMapping("/resetar-senha")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody String newPassword) {
        Optional<PasswordResetToken> resetTokenOpt = tokenRepository.findByToken(token);

        if (resetTokenOpt.isPresent()) {
            PasswordResetToken resetToken = resetTokenOpt.get();

            if (resetToken.isExpired()) {
                return ResponseEntity.badRequest().body("Token expirado.");
            }

            Usuario usuario = resetToken.getUsuario();
            usuario.setPassword(passwordEncoder.encode(newPassword));
            usuarioRepository.save(usuario);
            
            tokenRepository.delete(resetToken); // Remove o token após o uso.

            return ResponseEntity.ok("Senha redefinida com sucesso.");
        }
        return ResponseEntity.badRequest().body("Token inválido.");
    }
}
