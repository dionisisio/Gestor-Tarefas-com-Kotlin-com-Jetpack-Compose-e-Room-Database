import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.test.core.app.ApplicationProvider

fun emitirNotificacao( titulo: String, descricao: String) {
    val context = ApplicationProvider.getApplicationContext<Context>()
    // Criação do canal de notificação (necessário para Android 8 ou superior)
    val channelId = "anotacao_channel"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Tarefas"
        val descriptionText = "Notificações sobre suas tarefas agendadas"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // Criação da notificação
    val notification: Notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info) // Ícone da notificação
        .setContentTitle(titulo) // Título
        .setContentText(descricao) // Descrição
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    // Exibe a notificação
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(0, notification) // ID da notificação
}
