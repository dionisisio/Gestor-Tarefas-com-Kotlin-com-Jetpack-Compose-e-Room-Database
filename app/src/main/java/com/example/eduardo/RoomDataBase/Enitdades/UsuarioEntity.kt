package gestortarefas.bambi.eduardo.RoomDataBase.Enitdades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nomeusuario: String,
    var emailusuario: String,
    var senhausuario: String

)