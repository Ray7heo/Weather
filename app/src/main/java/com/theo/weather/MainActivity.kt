package com.theo.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.theo.weather.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()
{
    private lateinit var bd: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        bd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bd.root)
    }

    //返回事件
    override fun onBackPressed()
    {
        if (findNavController(R.id.navHost).currentDestination?.id == R.id.weatherFragment)
        {
            finish()
        }
        super.onBackPressed()
    }
}