package com.kevinhomorales.touristykotlin.main

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kevinhomorales.touristykotlin.R

open class MainActivity : AppCompatActivity() {

    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun callProgressDialog(show: Boolean) {
        if (show) {
            if (alertDialog == null) {
                val builder = AlertDialog.Builder(this)
                val inflater = layoutInflater
                val dialogLayout = inflater.inflate(R.layout.progress_dialog, null)
                with(builder) {
                    setTitle(getString(R.string.loading_text))
                    setView(dialogLayout)
                    setCancelable(false)
                    alertDialog = create()
                    alertDialog?.show()
                }
            } else {
                alertDialog?.show()
            }
        } else {
            alertDialog?.dismiss()
            alertDialog = null
        }
    }
}