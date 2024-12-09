package gestortarefas.bambi.eduardo.Frontend.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.eduardo.RoomDataBase.Repositorios.UsuarioRepositorio
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import gestortarefas.bambi.eduardo.RoomDataBase.Enitdades.UsuarioEntity

class UsuarioViewModel(private val usuarioRepositorio: UsuarioRepositorio) : ViewModel() {


    // Estado para armazenar a lista de usuários
    private val _usuarios = mutableStateOf<List<UsuarioEntity>>(emptyList())
    val usuarios: State<List<UsuarioEntity>> = _usuarios

    // Função para carregar todos os usuários do banco de dados
    fun carregarUsuarios() {
        viewModelScope.launch {
            val listaUsuarios = usuarioRepositorio.ObterListaTodosUsuariosCadastros()
            _usuarios.value = listaUsuarios
        }
    }

    // Em UsuarioViewModel
    // Estado do usuário atual (sessão global)
    var usuarioAtual: UsuarioEntity? by mutableStateOf(null) // Usuário logado, ou null caso não tenha sessão




    // Estado do usuário atual
    var usuarioEstadoEntity by mutableStateOf(UsuarioEntity(id = 0, nomeusuario = "", emailusuario = "", senhausuario = ""))

    // Função para atualizar o nome do usuário
    fun atualizarNome(nome: String) {
        usuarioEstadoEntity = usuarioEstadoEntity.copy(nomeusuario = nome)
    }

    // Função para atualizar o e-mail
    fun atualizarEmail(email: String) {
        usuarioEstadoEntity = usuarioEstadoEntity.copy(emailusuario = email)
    }

    // Função para atualizar a senha
    fun atualizarSenha(senha: String) {
        usuarioEstadoEntity = usuarioEstadoEntity.copy(senhausuario = senha)
    }

    // Função para realizar o login com e-mail e senha
    fun FazerLogin(onResult: (Boolean, String) -> Unit) {
        val email = usuarioEstadoEntity.emailusuario
        val senha = usuarioEstadoEntity.senhausuario

        if (email.isNotEmpty() && senha.isNotEmpty()) {
            viewModelScope.launch {
                // Consulta o banco de dados para encontrar o usuário com o e-mail e a senha
                val usuario = usuarioRepositorio.obterUsuarioPorEmailESenha(email, senha)

                if (usuario != null) {
                    // Atualiza o estado global com o usuário logado
                    usuarioAtual = usuario
                    onResult(true, "Login realizado com sucesso!")
                } else {
                    onResult(false, "Credenciais inválidas")
                }
            }
        } else {
            onResult(false, "E-mail ou senha não informados")
        }
    }
    // Função para fazer logout, removendo o usuário logado da sessão
    fun FazerLogout() {
        usuarioAtual = null // Remove a sessão de usuário logado
    }
    // Função para registrar um novo usuário com e-mail e senha
    fun RegistarUsuario(onResult: (Boolean, String) -> Unit) {
        val email = usuarioEstadoEntity.emailusuario
        val senha = usuarioEstadoEntity.senhausuario
        val nome = usuarioEstadoEntity.nomeusuario

        if (email.isNotEmpty() && senha.isNotEmpty() && nome.isNotEmpty()) {
            viewModelScope.launch {
                // Verifica se o usuário já existe
                val usuarioExistente = usuarioRepositorio.obterUsuarioPorEmailESenha(email, senha)

                if (usuarioExistente == null) {
                    // Cria um novo usuário
                    val novoUsuario = UsuarioEntity(id = 0, nomeusuario = nome, emailusuario = email, senhausuario = senha)
                    usuarioRepositorio.Adicionar(novoUsuario)
                    onResult(true, "Cadastro realizado com sucesso!")
                } else {
                    onResult(false, "E-mail já cadastrado")
                }
            }
        } else {
            onResult(false, "Todos os campos devem ser preenchidos")
        }
    }

    // Função para reinicializar o usuário (apagar os dados)
    fun reinicializarUsuario() {
        usuarioEstadoEntity = UsuarioEntity(id = 0, nomeusuario = "", emailusuario = "", senhausuario = "")
    }
}
