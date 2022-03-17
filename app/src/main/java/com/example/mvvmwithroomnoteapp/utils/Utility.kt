package com.example.mvvmwithroomnoteapp.utils

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.View
import com.example.mvvmwithroomnoteapp.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utility {

    companion object {

        fun boolToVisibility(view: View, visibility: Boolean) {
            view.visibility = if (visibility) View.VISIBLE else View.GONE
        }

        fun isNullOrEmpty(str: String?): Boolean {
            return str == null || str.isEmpty()
        }

        fun getCurrentDate() : String {
            val answer: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                current.format(formatter)
            } else {
                val date = Date()
                val formatter = SimpleDateFormat("dd MMM yyyy")
                formatter.format(date)
            }
            return answer
        }

        fun showError(context: Context, message: String) {
            val builder = AlertDialog.Builder(context)
            val alertDialog: AlertDialog = builder
                .setTitle(R.string.caution)
                .setMessage(message)
                .setPositiveButton("Okay"){ dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .create()
            alertDialog.show()
        }

    }
}