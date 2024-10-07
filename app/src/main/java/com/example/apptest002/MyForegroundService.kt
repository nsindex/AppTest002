package com.example.apptest002

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper

class MyForegroundService : Service() {

    private val maxServiceDuration = 6 * 60 * 60 * 1000L // 6時間 (ミリ秒)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // サービス開始時にタイマーを設定
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            // タイムアウトしたらサービスを停止
            stopSelf()
        }, maxServiceDuration)

        // フォアグラウンドサービスを開始
        startForeground(SERVICE_ID, createNotification())

        return START_STICKY
    }

    // タイムアウト時に呼ばれるメソッド
    override fun onTimeout(foregroundServiceType: Int, reason: Int) {
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // 通知の作成（フォアグラウンドサービス用）
    private fun createNotification(): Notification {
        val notificationChannelId = "MY_SERVICE_CHANNEL"
        val builder = Notification.Builder(this, notificationChannelId)
        builder.setContentTitle("MyForegroundService")
            .setContentText("Service is running")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
        return builder.build()
    }

    companion object {
        const val SERVICE_ID = 1
    }
}
