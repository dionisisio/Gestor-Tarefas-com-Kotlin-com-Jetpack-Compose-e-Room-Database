package gestortarefas.bambi.eduardo.RoomDataBase.DAO
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDAO {

    @Insert
    suspend fun AdicionarUsuario(usuariontity: UsuarioEntity)

    @Update
    suspend fun ActualizarUsuario(usuariontity: UsuarioEntity)

    @Delete
    suspend fun EliminarUsuario(usuariontity: UsuarioEntity)

    @Query("SELECT * FROM UsuarioEntity")
    fun ObterListaTodosUsuarios(): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM UsuarioEntity WHERE emailusuario = :email AND senhausuario = :senha LIMIT 1")
    suspend fun obterUsuarioPorEmailESenha(email: String, senha: String): UsuarioEntity?

    @Query("SELECT * FROM UsuarioEntity WHERE id = :id LIMIT 1")
    fun obterUsuarioproID(id: Int): Flow<UsuarioEntity?>

    @Query("SELECT * FROM UsuarioEntity")
    suspend fun ObterTodosUsuarios(): List<UsuarioEntity>
}
