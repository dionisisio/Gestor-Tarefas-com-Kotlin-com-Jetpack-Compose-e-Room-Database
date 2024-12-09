package gestortarefas.bambi.eduardo.Frontend.Componetes

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ExibirMensagemSimples(
    MostrarMensagemSimples: Boolean,
    mensagem: String,
    onDismiss: () -> Unit
) {
    if (MostrarMensagemSimples) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Aviso") },
            text = { Text(text = mensagem) },
            confirmButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("OK", color = Color.Black)
                }
            }
        )
    }
}
