package com.theo.weather

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.theo.weather.databinding.FragmentWeatherBinding
import com.theo.weather.viewmodel.CityForecastViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherFragment : Fragment()
{
    private lateinit var bd: FragmentWeatherBinding
    private lateinit var viewModel: CityForecastViewModel

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[CityForecastViewModel::class.java]
        bd = FragmentWeatherBinding.inflate(layoutInflater, container, false)
        return bd.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        bd.apply {
            bd.data = viewModel
            bd.lifecycleOwner = this@WeatherFragment
            //返回数据时设置天气图标
            viewModel.lives.observe(viewLifecycleOwner) {
                val key = it.weather
                imwToday.setImageResource(
                    CityForecastViewModel.weatherIcon.getOrDefault(
                        key,
                        R.drawable.clear
                    )
                )
            }
            viewModel.forecasts.observe(viewLifecycleOwner) {
                //根据天气为每个imageView设置天气图标
                val key0 = it.casts[0].dayWeather
                val key1 = it.casts[1].dayWeather
                val key2 = it.casts[2].dayWeather
                imageView0.setImageResource(
                    CityForecastViewModel.weatherIcon.getOrDefault(
                        key0,
                        R.drawable.clear
                    )
                )
                imageView1.setImageResource(
                    CityForecastViewModel.weatherIcon.getOrDefault(
                        key1,
                        R.drawable.clear
                    )
                )
                imageView2.setImageResource(
                    CityForecastViewModel.weatherIcon.getOrDefault(
                        key2,
                        R.drawable.clear
                    )
                )
                //关闭刷新状态
                swiperRefreshLayout.isRefreshing = false
            }
            //下滑刷新天气
            swiperRefreshLayout.setOnRefreshListener {
                viewModel.locate()
                if (swiperRefreshLayout.isRefreshing)
                {
                    lifecycleScope.launch {
                        delay(1000)
                        swiperRefreshLayout.isRefreshing = false
                    }
                }
            }
        }
    }
}