package gestortarefas.bambi.eduardo.Frontend.Paginas


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.*

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import gestortarefas.bambi.eduardo.Frontend.Componetes.ExibirMensagemSimples
import gestortarefas.bambi.eduardo.R
import gestortarefas.bambi.eduardo.Frontend.ViewModel.AnotacaoViewModel
import gestortarefas.bambi.eduardo.Frontend.ViewModel.UsuarioViewModel
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.AnotacaoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarNovaANotacaoPage(navController: NavHostController, viewModel: AnotacaoViewModel, usuarioViewModel: UsuarioViewModel) {
    // Pegando o usuário logado
    val usuarioAtual = usuarioViewModel.usuarioAtual
    var idusuario by remember { mutableStateOf(0) }

    if (usuarioAtual != null) {
        idusuario= usuarioAtual.id
    }

    var titulo by remember { mutableStateOf("") }
    var texto by remember { mutableStateOf("") }
    var dataRealizacao by remember { mutableStateOf("") }
    var horaRealizacao by remember { mutableStateOf("") }

    // Estados para validar campos
    var tituloValido by remember { mutableStateOf(true) }
    var textoValido by remember { mutableStateOf(true) }
    var dataRealizacaoValido by remember { mutableStateOf(true) }
    var horaRealizacaoValido by remember { mutableStateOf(true) }

    // Estado para controlar o DatePickerDialog
    var MostarDatePicker by remember { mutableStateOf(false) }
    var MostarTimePicker by remember { mutableStateOf(false) }

    // Estado para controlar a visibilidade do AlertDialog simples
    var mostrarMensagemSimples by remember { mutableStateOf(false) }
    var mensagemErro by remember { mutableStateOf("") }

    val calendario = Calendar.getInstance()

    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Adicionar Tarefa",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Adicionar",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top, // Itens alinhados ao topo
            horizontalAlignment = Alignment.Start
        ) {
            // Campo de Título
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                placeholder = { Text("Inserir o título") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                isError = !tituloValido, // Marca o campo como erro se inválido
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )

            // Campo de Texto (Descrição)
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Descrição") },
                placeholder = { Text("Inserir a descrição") },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                isError = !textoValido, // Marca o campo como erro se inválido
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp)
                    .padding(bottom = 16.dp),
                maxLines = 10
            )

            // Campo de Data de Realização
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Data Prevista: $dataRealizacao", color = Color.Black, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal))

                Button(
                    onClick = { MostarDatePicker = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Definir")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Linha para Hora Prevista
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Hora Prevista: $horaRealizacao", color = Color.Black, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal))

                Button(
                    onClick = { MostarTimePicker = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Definir")
                }
            }

            // FloatingActionButton para salvar
            FloatingActionButton(
                onClick = {
                    // Validação antes de salvar
                    tituloValido = titulo.isNotEmpty()
                    textoValido = texto.isNotEmpty()
                    dataRealizacaoValido = dataRealizacao.isNotEmpty()
                    horaRealizacaoValido = horaRealizacao.isNotEmpty()

                    // Validação de campos
                    if (titulo.isEmpty() || texto.isEmpty() || dataRealizacao.isEmpty() || horaRealizacao.isEmpty()) {
                        mensagemErro = "Todos os campos devem ser preenchidos!"
                        mostrarMensagemSimples = true
                    } else {
                        // Lógica para comparar a data e hora
                        val dataAtual = dateFormatter.format(Date())
                        val horaAtual = timeFormatter.format(Date())

                        if (dataRealizacao < dataAtual || (dataRealizacao == dataAtual && horaRealizacao < horaAtual)) {
                            mensagemErro = "Data e hora não podem ser anteriores ao momento atual."
                            mostrarMensagemSimples = true
                        } else {
                            // Lógica para salvar a anotação {
                            val dataAtual =
                                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                            val novaAnotacao = AnotacaoEntity(
                                id = 0,
                                idusuario = idusuario,
                                tituloanotacao = titulo,
                                textoanotacao = texto,
                                dataAnotacao = dataAtual,
                                datarealizacao = dataRealizacao,
                                horarealizacao = horaRealizacao
                            )
                            viewModel.AdicionarAnotacao(novaAnotacao)
                            navController.popBackStack() // Voltar para a tela anterior
                        }
                    }

                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
                    .width(200.dp)
                    .height(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Adicionar",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }
            }

            // Mostrar DatePickerDialog se showDatePicker for true
            if (MostarDatePicker) {
                DatePickerDialog(
                    LocalContext.current,
                    { _, year, month, dayOfMonth ->
                        dataRealizacao = dateFormatter.format(Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }.time)
                    },
                    calendario.get(Calendar.YEAR),
                    calendario.get(Calendar.MONTH),
                    calendario.get(Calendar.DAY_OF_MONTH)
                ).show()
                MostarDatePicker = false
            }

            // Mostrar TimePickerDialog se showTimePicker for true
            if (MostarTimePicker) {
                TimePickerDialog(
                    LocalContext.current,
                    { _, hourOfDay, minute ->
                        horaRealizacao = timeFormatter.format(Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, hourOfDay)
                            set(Calendar.MINUTE, minute)
                        }.time)
                    },
                    calendario.get(Calendar.HOUR_OF_DAY),
                    calendario.get(Calendar.MINUTE),
                    true
                ).show()
                MostarTimePicker = false
            }
        }

    }
    // Exibe o alerta simples com a mensagem de erro
    ExibirMensagemSimples(
        MostrarMensagemSimples = mostrarMensagemSimples,
        mensagem = mensagemErro,
        onDismiss = { mostrarMensagemSimples = false }
    )
}
