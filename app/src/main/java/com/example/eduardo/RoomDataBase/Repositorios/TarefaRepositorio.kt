package com.example.eduardo.RoomDataBase.Repositorios



import gestortarefas.bambi.eduardo.RoomDataBase.DAO.AnotacaoDAO
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.AnotacaoEntity
import kotlinx.coroutines.flow.Flow

class AnotacaoRepositorio(val anotacaoDAO: AnotacaoDAO) {

    suspend fun Adicionar(anotacaoentity: AnotacaoEntity) {
        anotacaoDAO.AdicionarAnotacao(anotacaoentity)
    }

    suspend fun Actualizar(anotacaoentity: AnotacaoEntity) {
        anotacaoDAO.ActualizarAnotacao(anotacaoentity)
    }
    suspend fun Eliminar(anotacaoentity: AnotacaoEntity) {
        anotacaoDAO.EliminarAnotacao(anotacaoentity)
    }
    // Função para buscar anotação por ID
    fun ObterAnotacaoporID(id: Int): Flow<AnotacaoEntity?> {
        return anotacaoDAO.ObterAnotacaoporID(id)
    }
    fun ObterAnotacoesPorUsuario(idusuario: Int): Flow<List<AnotacaoEntity>> {
        return anotacaoDAO.ObterListaTodasAnotacoesPorIDUsuario(idusuario)
    }


}