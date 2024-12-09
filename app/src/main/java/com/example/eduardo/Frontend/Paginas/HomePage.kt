package gestortarefas.bambi.eduardo.Frontend.Paginas

import MonitorarAnotacoesFuturasEEmitirNotificacoes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import gestortarefas.bambi.eduardo.Frontend.Componetes.CartaoAnotacao
import gestortarefas.bambi.eduardo.Frontend.ViewModel.AnotacaoViewModel
import gestortarefas.bambi.eduardo.Frontend.ViewModel.UsuarioViewModel
import gestortarefas.bambi.eduardo.R
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavHostController, viewModel: AnotacaoViewModel, usuarioViewModel: UsuarioViewModel) {
    var inputAnotacao by remember { mutableStateOf("") }
    // Pegando o usuário logado
    val usuarioAtual = usuarioViewModel.usuarioAtual
    var idusuario by remember { mutableStateOf(0) }

    if (usuarioAtual != null) {
        idusuario= usuarioAtual.id
        // Aqui você aciona o monitoramento assim que o usuário está logado
       // MonitorarAnotacoesFuturasEEmitirNotificacoes(viewModel)
    }

    val anotacaolista by viewModel.ObterAnotacoesPorUsuario(idusuario).collectAsState(initial = emptyList())



    // Filtrando anotações com base no título, descrição ou data de realização
    val filteredList = anotacaolista.filter { anotacao ->
        anotacao.tituloanotacao.contains(inputAnotacao, ignoreCase = true) ||
                anotacao.textoanotacao.contains(inputAnotacao, ignoreCase = true) ||
                anotacao.datarealizacao.contains(inputAnotacao, ignoreCase = true)
    }

    // Ordenando a lista pela data de realização mais próxima
    val sortedList = filteredList.sortedBy { anotacao ->
        val date = anotacao.datarealizacao.split("/").takeIf { it.size == 3 }?.let {
            Calendar.getInstance().apply {
                set(it[2].toInt(), it[1].toInt() - 1, it[0].toInt()) // Convertendo para um objeto Date
            }
        }
        date?.time ?: Date() // Retorna a data ou a data atual se não for válida
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Minhas Tarefas",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background
                        )
                        // Exibindo o nome do usuário logado
                        if (usuarioAtual != null) {
                            Text(
                                text = "Bem-vindo, ${usuarioAtual.nomeusuario}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                },
                actions = {
                    // Botão de Logout
                    IconButton(onClick = {
                        usuarioViewModel.FazerLogout()
                        navController.navigate("loginpage") { // Substitua pelo nome correto da sua tela de login
                            popUpTo("homePage") { inclusive = true } // Fecha a página de home ao fazer logout
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.logout), // Coloque o ícone de logout adequado
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("adicionarnovaanotacaopage") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(id = R.drawable.adicionar),
                    contentDescription = "Adicionar",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    ) { paddingValues ->
        // Colocando o campo de pesquisa fora do cabeçalho
        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(
                value = inputAnotacao,
                onValueChange = { inputAnotacao = it },
                label = { Text("Pesquisar") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onTertiary, // Cor de fundo
                    focusedLabelColor = Color.Gray, // Cor do label quando em foco (Cinza)
                    unfocusedLabelColor = Color.Gray, // Cor do label quando não em foco (Cinza)
                    focusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer, // Cor da linha de indicação quando em foco
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f), // Cor da linha de indicação quando não em foco
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                )
            )

            if (sortedList.isEmpty()) {
                // Exibe mensagem quando a lista está vazia
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Sem anotações",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                // Exibe a lista de anotações
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(sortedList) { anotacao ->
                        // Atualiza a anotação selecionada no ViewModel
                        viewModel.selecionarAnotacao(anotacao)
                        CartaoAnotacao(anotacao,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .clickable {
                                    // Navega para a tela de detalhes
                                    navController.navigate("detalhesanotacaopage")
                                }
                        )
                    }
                }
            }
        }
    }
}
