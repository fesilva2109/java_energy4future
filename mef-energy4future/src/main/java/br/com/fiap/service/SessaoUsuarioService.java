package br.com.fiap.service;

import br.com.fiap.dto.SessaoUsuarioDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessaoUsuarioService {

    private static SessaoUsuarioService instance;
    private final Map<Integer, SessaoUsuarioDto> sessoes;

    private SessaoUsuarioService() {
        this.sessoes = new ConcurrentHashMap<>();
    }

    public static SessaoUsuarioService getInstance() {
        if (instance == null) {
            synchronized (SessaoUsuarioService.class) {
                if (instance == null) {
                    instance = new SessaoUsuarioService();
                }
            }
        }
        return instance;
    }

    public void criarSessao(int usuarioId) {
        SessaoUsuarioDto sessao = new SessaoUsuarioDto(0, usuarioId, LocalDateTime.now());
        sessoes.put(usuarioId, sessao);
        System.out.println("Sessão criada para o usuário ID: " + usuarioId);
    }

    public void encerrarSessao(int usuarioId) {
        sessoes.remove(usuarioId);
        System.out.println("Sessão encerrada para o usuário ID: " + usuarioId);
    }

    public boolean isSessaoAtiva(int usuarioId) {
        SessaoUsuarioDto sessao = sessoes.get(usuarioId);
        if (sessao == null) {
            System.out.println("Sessão não encontrada para o usuário ID: " + usuarioId);
            return false;
        }
        System.out.println("Sessão encontrada e ativa para o usuário ID: " + usuarioId);
        return true;
    }

    public long minutosDesdeUltimoLogin(int usuarioId) {
        SessaoUsuarioDto sessao = sessoes.get(usuarioId);
        if (sessao == null) {
            return -1;
        }
        return Duration.between(sessao.getLocalDateTime(), LocalDateTime.now()).toMinutes();
    }
}
