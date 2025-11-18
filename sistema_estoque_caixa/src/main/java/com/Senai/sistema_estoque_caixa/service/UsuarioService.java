package com.Senai.sistema_estoque_caixa.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.List;
import com.Senai.sistema_estoque_caixa.entity.Usuario;
import com.Senai.sistema_estoque_caixa.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // BCrypt injetado

    // Construtor para injeção de dependência
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Regra: Validação da Política Mínima de Senha
    private void validarPoliticaSenha(String senha) {
        // Mínimo 8 caracteres, 1 maiúscula, 1 número
        if (senha.length() < 8) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 8 caracteres.");
        }
        if (!Pattern.compile(".*[A-Z].*").matcher(senha).matches()) {
            throw new IllegalArgumentException("Senha deve conter pelo menos 1 letra maiúscula.");
        }
        if (!Pattern.compile(".*[0-9].*").matcher(senha).matches()) {
            throw new IllegalArgumentException("Senha deve conter pelo menos 1 número.");
        }
    }

    // 1. Criar Usuário
    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalStateException("E-mail já cadastrado.");
        }

        // Valida e criptografa a senha ANTES de salvar
        validarPoliticaSenha(usuario.getSenha());
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }
    
    // 2. Listar Usuários
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    // 3. Atualizar Usuário
    public Usuario atualizarUsuario(Long id, Usuario dadosAtualizados) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        // Previne que o email seja alterado para um email já existente, exceto o próprio
        if (!usuarioExistente.getEmail().equals(dadosAtualizados.getEmail()) && usuarioRepository.existsByEmail(dadosAtualizados.getEmail())) {
             throw new IllegalStateException("E-mail já cadastrado para outro usuário.");
        }

        usuarioExistente.setNomeCompleto(dadosAtualizados.getNomeCompleto());
        usuarioExistente.setEmail(dadosAtualizados.getEmail());
        usuarioExistente.setPerfil(dadosAtualizados.getPerfil());
        usuarioExistente.setStatus(dadosAtualizados.isStatus());

        // Se uma nova senha for fornecida, valida e criptografa
        if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isEmpty()) {
            validarPoliticaSenha(dadosAtualizados.getSenha());
            usuarioExistente.setSenha(passwordEncoder.encode(dadosAtualizados.getSenha()));
        }

        return usuarioRepository.save(usuarioExistente);
    }
    
    // 4. Excluir Usuário
    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    // ** Módulo Login **
    
    // 5. Autenticar Usuário (Lógica de Login)
    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Credenciais inválidas."));

        // Compara a senha informada com o hash armazenado
        if (passwordEncoder.matches(senha, usuario.getSenha()) && usuario.isStatus()) {
            // Se as senhas baterem E o status for ativo
            return usuario;
        } else {
            throw new RuntimeException("Credenciais inválidas.");
        }
    }
}