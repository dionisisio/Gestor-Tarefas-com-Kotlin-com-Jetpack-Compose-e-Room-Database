package gestortarefas.bambi.eduardo.Frontend.ViewModel


import agendarNotificacao
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eduardo.RoomDataBase.Repositorios.AnotacaoRepositorio
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.AnotacaoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Locale

class AnotacaoViewModel(val aanotacaorepositorio: AnotacaoRepositorio) : ViewModel() {
    // Estado para armazenar a anotação selecionada
    private val _anotacaoAtual = mutableStateOf<AnotacaoEntity?>(null)
    val anotacaoAtual: State<AnotacaoEntity?> = _anotacaoAtual

    // Função para selecionar a anotação que será exibida no Cartão
    fun selecionarAnotacao(anotacao: AnotacaoEntity) {
        _anotacaoAtual.value = anotacao
    }

    // Função para adicionar uma nova anotação
    fun AdicionarAnotacao(anotacaoEntity: AnotacaoEntity) {
        viewModelScope.launch {
            aanotacaorepositorio.Adicionar(anotacaoEntity)
        }
    }

    // Função para atualizar uma anotação existente
    fun AtualizarAnotacao(anotacaoEntity: AnotacaoEntity) {
        viewModelScope.launch {
            aanotacaorepositorio.Actualizar(anotacaoEntity)
        }
    }

    // Função para editar uma anotação (seleciona e atualiza)
    fun EditarAnotacao(anotacaoEntity: AnotacaoEntity) {
        selecionarAnotacao(anotacaoEntity) // Define a anotação atual
        AtualizarAnotacao(anotacaoEntity) // Atualiza a anotação no banco de dados
    }

    // Função para eliminar uma anotação
    fun EliminarAnotacao(anotacaoEntity: AnotacaoEntity) {
        viewModelScope.launch {
            aanotacaorepositorio.Eliminar(anotacaoEntity) // Chama a função de eliminar no repositório
        }
    }

    // Função para buscar anotação por ID
    fun ObterAnotacaoporID(id: Int): Flow<AnotacaoEntity?> {
        return aanotacaorepositorio.ObterAnotacaoporID(id)
    }

    // Função para buscar anotações do usuário logado pelo idusuario
    fun ObterAnotacoesPorUsuario(idusuario: Int): Flow<List<AnotacaoEntity>> {
        return aanotacaorepositorio.ObterAnotacoesPorUsuario(idusuario)
    }


    // Função para filtrar anotações que estão dentro das próximas 2 horas
    fun filtrarAnotacoesFuturas(anotacoes: List<AnotacaoEntity>): List<AnotacaoEntity> {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val horaAtual = Calendar.getInstance().time // hora atual
        val horaLimite = Calendar.getInstance().apply {
            add(Calendar.HOUR_OF_DAY, 2) // Adiciona 2 horas à data/hora atual
        }.time

        // Filtra as anotações que estão dentro das próximas 2 horas
        return anotacoes.filter { anotacao ->
            val anotacaoHora = sdf.parse("${anotacao.datarealizacao} ${anotacao.horarealizacao}")
            anotacaoHora != null && anotacaoHora.after(horaAtual) && anotacaoHora.before(horaLimite)
        }
    }

    // Função para filtrar e agendar notificações para anotações futuras
    @Composable
    fun filtrarAnotacoesFuturasEEmitirNotificacoes() {

        val usuarioViewModel: UsuarioViewModel = viewModel()  // Obtém o ViewModel para o usuário

        // Recuperando o usuário atual
        val usuarioAtual = usuarioViewModel.usuarioAtual

        // Variável para armazenar o id do usuário atual
        var idusuario by remember { mutableStateOf(0) }

        // Atribuindo o id do usuário, caso ele não seja nulo
        if (usuarioAtual != null) {
            idusuario = usuarioAtual.id
        }

        // Coletando as anotações do usuário
        val anotacaolistas by ObterAnotacoesPorUsuario(idusuario).collectAsState(initial = emptyList())

        val anotacaolista = filtrarAnotacoesFuturas(anotacaolistas)
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
    }



}
