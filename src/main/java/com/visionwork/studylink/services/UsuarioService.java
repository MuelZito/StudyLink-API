package com.visionwork.studylink.services;

import com.visionwork.studylink.entities.Tarefa;
import com.visionwork.studylink.entities.Usuario;
import com.visionwork.studylink.repositories.TarefasRepository;
import com.visionwork.studylink.repositories.UsuarioRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarefasRepository tarefasRepository;


    public Optional<Usuario> findByIdUsuario(Long id){
        return usuarioRepository.findById(id);
    }


    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Tarefa salvarTarefa(Tarefa tarefa) {
        return tarefasRepository.save(tarefa);
    }
}
