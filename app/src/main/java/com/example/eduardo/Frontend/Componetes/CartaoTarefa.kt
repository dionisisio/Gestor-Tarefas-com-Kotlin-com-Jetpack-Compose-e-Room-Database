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
import androidx.compose.ui.unit.dp
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.AnotacaoEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CartaoAnotacao(anotacao: AnotacaoEntity, modifier: Modifier = Modifier) {
     Card(
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (anotacao != null) {
                Text(text = anotacao.id.toString() +"- "+ anotacao.tituloanotacao, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = anotacao.textoanotacao, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Marcado para: "+ anotacao.horarealizacao+" de "+ anotacao.datarealizacao , style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Criado aos " + anotacao.dataAnotacao, style = MaterialTheme.typography.bodySmall)
            }

            }
        }
    }
}

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(Date())
}
