package gestortarefas.bambi.eduardo.Frontend.Paginas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import gestortarefas.bambi.eduardo.Frontend.Componetes.CartaoUsuario
import gestortarefas.bambi.eduardo.Frontend.ViewModel.UsuarioViewModel
import gestortarefas.bambi.eduardo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaUsuariosPage(navController: NavHostController, usuarioViewModel: UsuarioViewModel) {
    // Observa o estado dos usuários
    val listaUsuarios by usuarioViewModel.usuarios
    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                        Text(
                            text = "Lista de Usuários Cadastrados",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background
                        )

                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp) // Espaçamento para evitar sobreposição com a barra de navegação
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.BottomEnd), // Alinha a linha no canto inferior direito
                    horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaçamento entre os botões
                    verticalAlignment = Alignment.CenterVertically // Alinha os botões verticalmente no centro da linha
                ) {
                    // Botão Voltar
                    FloatingActionButton(
                        onClick = { navController.popBackStack() },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Voltar",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            // Carrega os usuários quando a página for exibida
            LaunchedEffect(Unit) {
                usuarioViewModel.carregarUsuarios()
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                if (listaUsuarios.isEmpty()) {
                    Text(
                        text = "Nenhum usuário registrado encontrado.",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(listaUsuarios) { usuario ->
                            CartaoUsuario(usuario = usuario)
                        }
                    }
                }
            }
        }
    }
}

