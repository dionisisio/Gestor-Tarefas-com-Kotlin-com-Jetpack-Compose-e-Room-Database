package gestortarefas.bambi.eduardo.Frontend.Paginas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashPage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator() // Indicador de carregamento
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "MINHAS TAREFAS",
                style = TextStyle(fontSize = 24.sp, color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Carregando...",
                style = TextStyle(fontSize = 20.sp, color = Color.Gray)
            )
        }
    }
}
