package gestortarefas.bambi.eduardo


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eduardo.RoomDataBase.Repositorios.AnotacaoRepositorio
import com.example.eduardo.RoomDataBase.Repositorios.UsuarioRepositorio
import gestortarefas.bambi.eduardo.Frontend.Paginas.AdicionarNovaANotacaoPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.CriarUsuarioPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.DetalhesAnotacaoPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.EditarAnotacaoPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.HomePage
import gestortarefas.bambi.eduardo.Frontend.Paginas.ListaUsuariosPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.LoginPage
import gestortarefas.bambi.eduardo.Frontend.Paginas.SplashPage
import gestortarefas.bambi.eduardo.Frontend.ViewModel.AnotacaoViewModel
import gestortarefas.bambi.eduardo.Frontend.ViewModel.UsuarioViewModel
import gestortarefas.bambi.eduardo.RoomDataBase.DatabaseInstance
import gestortarefas.bambi.eduardo.ui.theme.AnotacoesTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Envolvendo o conteúdo no tema personalizado
            AnotacoesTheme {
                var MostarTelaSplash by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(3000) // Aguarda 3 segundos
                    MostarTelaSplash = false
                }

                if (MostarTelaSplash) {
                    SplashPage() // Mostra a tela de splash
                } else {
                    // Criar a instância do banco de dados
                    var database = DatabaseInstance.getDatabase(applicationContext)

                    // Criar os repositórios
                    var usuariorepositorio = UsuarioRepositorio(database.usuarioDao())
                    var anotacaorepositorio = AnotacaoRepositorio(database.anotacaoDao())

                    // Instanciar os ViewModel
                    val usuarioViewModel = UsuarioViewModel(usuariorepositorio)
                    val anotacaoViewModel = AnotacaoViewModel(anotacaorepositorio)

                    // Iniciar a navegação
                    NavigationComponent(anotacaoViewModel, usuarioViewModel)
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(anotacaoviewModel: AnotacaoViewModel, usuarioViewModel: UsuarioViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "loginpage") {
        //Usuario
        composable("loginpage") { LoginPage(navController, usuarioViewModel) }
        composable("criarusuariopage") { CriarUsuarioPage(navController, usuarioViewModel) }
        composable("listausuariopage") { ListaUsuariosPage(navController, usuarioViewModel) }

        // Anotacoes
        composable("homepage") { HomePage(navController, anotacaoviewModel, usuarioViewModel) }

        composable("adicionarnovaanotacaopage") { AdicionarNovaANotacaoPage(navController, anotacaoviewModel, usuarioViewModel) }

        composable("detalhesanotacaopage") { DetalhesAnotacaoPage(navController,anotacaoviewModel) }

        composable("editaranotacaopage") { EditarAnotacaoPage(navController,anotacaoviewModel) }
    }
}
