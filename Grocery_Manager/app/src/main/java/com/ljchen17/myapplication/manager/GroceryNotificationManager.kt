package com.ljchen17.myapplication.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ljchen17.myapplication.GroceryApplication
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.activity.ComposeActivity
import com.ljchen17.myapplication.data.model.GroceryDetails
import kotlin.random.Random

class GroceryNotificationManager(private val context: Context) {

    private val notificationManagerCompat = NotificationManagerCompat.from(context)
    private val GApp = context.applicationContext as GroceryApplication
    private var index = GApp.groceryWorkingManager.currTag

    init {
        createFunChannel()
    }

    fun sendNotification(grocery: GroceryDetails) {
        val intent = Intent(context, ComposeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            //putExtra(MainActivity.TEXT_TAG, message)
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        var title = grocery.itemName
        var text = "Don't forget to finish your $title!"
        val notification = NotificationCompat.Builder(context, index)
            .setSmallIcon(R.drawable.ic_restaurant_menu_black_24dp)
            .setContentTitle(title)
            .setContentText(text)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManagerCompat.notify(Random.nextInt(), notification)
    }

    private fun createFunChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notifications of groceries"
            val descriptionText = "Reminds users the grocery items which are about to expire"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(index, name, importance).apply {
                description = descriptionText
            }
            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

//    companion object {
//        const val CHANNEL_ID = "CHANNEL_ID"
//    }

}