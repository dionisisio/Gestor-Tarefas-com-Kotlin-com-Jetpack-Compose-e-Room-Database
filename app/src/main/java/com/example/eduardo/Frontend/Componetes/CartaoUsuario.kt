package gestortarefas.bambi.eduardo.Frontend.Componetes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.UsuarioEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CartaoUsuario(usuario: UsuarioEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nome: ${usuario.nomeusuario}", fontWeight = FontWeight.Bold)
            Text(text = "E-mail: ${usuario.emailusuario}")

        }
    }
}