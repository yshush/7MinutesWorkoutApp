package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.util.*
import java.text.SimpleDateFormat

class FinishActivity : AppCompatActivity() {

    private val binding by lazy{ActivityFinishBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFinishActivity)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnFinish.setOnClickListener {
            finish()
        }

        val dao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao){

        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("Date: ", "" +dateTime)

        val sdf = SimpleDateFormat("yyyy MMM dd HH:mm:ss", Locale.KOREA)
        val date = sdf.format(dateTime)
        Log.e("Formatted Date: ", "" +date)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e(
                "Date : ",
                "Added..."
            )
        }

    }

}