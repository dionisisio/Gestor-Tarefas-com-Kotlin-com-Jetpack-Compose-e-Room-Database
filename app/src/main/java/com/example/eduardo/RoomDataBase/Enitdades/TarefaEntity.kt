package gestortarefas.bambi.eduardo.RoomDataBase.Enitdades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnotacaoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idusuario: Int,
    val tituloanotacao: String,
    val textoanotacao: String,
    val dataAnotacao: String,    // Usando LocalDate
    val datarealizacao: String,  // Usando LocalDate
    val horarealizacao: String  // Usando LocalTime
)