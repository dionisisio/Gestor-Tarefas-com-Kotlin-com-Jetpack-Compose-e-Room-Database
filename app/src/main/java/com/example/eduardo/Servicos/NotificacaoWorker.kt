import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class NotificacaoWorker( workerParams: WorkerParameters) : Worker(ApplicationProvider.getApplicationContext<Context>(), workerParams) {
    val context = ApplicationProvider.getApplicationContext<Context>()
    override fun doWork(): Result {
        val titulo = inputData.getString("titulo") ?: "Sem Título"
        val descricao = inputData.getString("descricao") ?: "Sem Descrição"

        // Chama a função para emitir a notificação
        emitirNotificacao(titulo, descricao)

        return Result.success()
    }
}

fun agendarNotificacao(titulo: String, descricao: String, delayMillis: Long) {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val data = androidx.work.Data.Builder()
        .putString("titulo", titulo)
        .putString("descricao", descricao)
        .build()

    val notificationWorkRequest = OneTimeWorkRequest.Builder(NotificacaoWorker::class.java)
        .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS) // Define o delay até a execução da notificação
        .setInputData(data)
        .build()

    WorkManager.getInstance(context).enqueue(notificationWorkRequest)
}
