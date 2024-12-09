import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import gestortarefas.bambi.eduardo.Frontend.ViewModel.AnotacaoViewModel
import gestortarefas.bambi.eduardo.Frontend.ViewModel.UsuarioViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun MonitorarAnotacoesFuturasEEmitirNotificacoes(anotacaoviewModel: AnotacaoViewModel) {
    val usuarioViewModel: UsuarioViewModel =viewModel()// Obtém o ViewModel para o usuário

    // Recuperando o usuário atual
    val usuarioAtual = usuarioViewModel.usuarioAtual

    // Variável para armazenar o id do usuário atual
    var idusuario by remember { mutableStateOf(0) }

    // Atribuindo o id do usuário, caso ele não seja nulo
    if (usuarioAtual != null) {
        idusuario = usuarioAtual.id
    }

    // Coletando as anotações do usuário
    val anotacaolistas by anotacaoviewModel.ObterAnotacoesPorUsuario(idusuario).collectAsState(initial = emptyList())


    LaunchedEffect(key1 = Unit) {
        while (true) {

            val anotacaolista = anotacaoviewModel.filtrarAnotacoesFuturas(anotacaolistas)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val horaAtual = Calendar.getInstance().time

            // Para cada anotação futura, agende uma notificação
            anotacaolista.forEach { anotacao ->
                val anotacaoHora = sdf.parse("${anotacao.datarealizacao} ${anotacao.horarealizacao}")
                if (anotacaoHora != null && anotacaoHora.after(horaAtual)) {
                    // Calcula o tempo restante até o evento (em milissegundos)
                    val delayMillis = anotacaoHora.time - horaAtual.time - 5 * 60 * 1000 // 5 minutos antes da anotação
                    agendarNotificacao(
                        titulo = anotacao.tituloanotacao,
                        descricao = anotacao.textoanotacao,
                        delayMillis = delayMillis
                    )
                }
            }

            // Atrasar a próxima execução para o intervalo desejado (exemplo: 1 minuto)
            delay(60 * 1000)  // 1 minuto
        }
    }
}
