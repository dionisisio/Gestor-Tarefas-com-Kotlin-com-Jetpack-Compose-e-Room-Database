package gestortarefas.bambi.eduardo.RoomDataBase.DAO
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.AnotacaoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnotacaoDAO  {

    @Insert
    suspend fun AdicionarAnotacao(anotacaoEntity: AnotacaoEntity)

    @Update
    suspend fun ActualizarAnotacao(anotacaoEntity: AnotacaoEntity)

    @Delete
    suspend fun EliminarAnotacao(anotacaoEntity: AnotacaoEntity)


    @Query("SELECT * FROM AnotacaoEntity WHERE idusuario = :idusuario")
    fun ObterListaTodasAnotacoesPorIDUsuario(idusuario: Int): Flow<List<AnotacaoEntity>>


    @Query("SELECT * FROM AnotacaoEntity WHERE id = :id LIMIT 1")
    fun ObterAnotacaoporID(id: Int): Flow<AnotacaoEntity?>

}

