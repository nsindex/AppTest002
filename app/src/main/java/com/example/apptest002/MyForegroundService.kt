package com.example.apptest002

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat

class MyForegroundService : Service() {

    private val maxServiceDuration = 6 * 60 * 60 * 1000L // 6時間 (ミリ秒)
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate() {
        super.onCreate()
        // 通知チャンネルの作成
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // フォアグラウンドサービスを開始
        startForeground(SERVICE_ID, createNotification())

        // サービス開始時にタイマーを設定
        handler.postDelayed({
            // タイムアウトしたらサービスを停止
            stopSelf()
        }, maxServiceDuration)

        // サービスのメイン処理をここに記述

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // ハンドラのコールバックを削除してメモリリークを防止
        handler.removeCallbacksAndMessages(null)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // 通知の作成（フォアグラウンドサービス用）
    private fun createNotification(): Notification {
        val notificationTitle = "MyForegroundService"
        val notificationText = "Service is running"

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return builder.build()
    }

    // 通知チャンネルの作成
    private fun createNotificationChannel() {
        // Android 8.0（API 26）以降の場合
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelName = "My Service Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val SERVICE_ID = 1
        const val CHANNEL_ID = "MY_SERVICE_CHANNEL"
    }
}
