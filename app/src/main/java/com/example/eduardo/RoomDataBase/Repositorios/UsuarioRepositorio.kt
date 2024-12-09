package com.example.eduardo.RoomDataBase.Repositorios


import gestortarefas.bambi.eduardo.RoomDataBase.DAO.UsuarioDAO
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.UsuarioEntity
import kotlinx.coroutines.flow.Flow

class UsuarioRepositorio(val usuarioDAO: UsuarioDAO) {

    suspend fun Adicionar(usuarioentity: UsuarioEntity) {
        usuarioDAO.AdicionarUsuario(usuarioentity)
    }

    suspend fun Actualizar(usuarioentity: UsuarioEntity) {
        usuarioDAO.ActualizarUsuario(usuarioentity)
    }

    suspend fun Eliminar(usuarioentity: UsuarioEntity) {
        usuarioDAO.EliminarUsuario(usuarioentity)
    }

    suspend fun ObterTodosUsuarios(): Flow<List<UsuarioEntity>> {
        return usuarioDAO.ObterListaTodosUsuarios()
    }

    // Função para obter o usuário pelo e-mail e senha
    suspend fun obterUsuarioPorEmailESenha(email: String, senha: String): UsuarioEntity? {
        return usuarioDAO.obterUsuarioPorEmailESenha(email, senha)
    }

    suspend fun ObterListaTodosUsuariosCadastros(): List<UsuarioEntity> {
        return usuarioDAO.ObterTodosUsuarios()
    }
}
