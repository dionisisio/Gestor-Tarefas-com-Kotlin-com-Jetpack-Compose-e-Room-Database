package gestortarefas.bambi.eduardo.Frontend.Paginas

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import gestortarefas.bambi.eduardo.Frontend.Componetes.ExibirMensagemSimples
import gestortarefas.bambi.eduardo.Frontend.ViewModel.AnotacaoViewModel
import gestortarefas.bambi.eduardo.R
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarAnotacaoPage(navController: NavHostController, viewModel: AnotacaoViewModel) {
    val anotacao = viewModel.anotacaoAtual.value

    var titulo by remember { mutableStateOf(anotacao?.tituloanotacao ?: "") }
    var texto by remember { mutableStateOf(anotacao?.textoanotacao ?: "") }
    var dataRealizacao by remember { mutableStateOf(anotacao?.datarealizacao ?: "") }
    var horaRealizacao by remember { mutableStateOf(anotacao?.horarealizacao ?: "") }

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
                        text = "Editar Tarefa",
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
                    contentDescription = "Editar",
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
            verticalArrangement = Arrangement.Top,
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
                isError = !tituloValido,
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
                isError = !textoValido,
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
                            // Lógica para salvar a anotação
                            val anotacaoAtualizada = anotacao?.copy(
                                tituloanotacao = titulo,
                                textoanotacao = texto,
                                datarealizacao = dataRealizacao,
                                horarealizacao = horaRealizacao
                            )

                            anotacaoAtualizada?.let {
                                viewModel.AtualizarAnotacao(it)
                            }
                            navController.navigate("homepage")
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
                        text = "Salvar",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }
            }

            // Mostrar DatePickerDialog se MostarDatePicker for true
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

            // Mostrar TimePickerDialog se MostarTimePicker for true
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