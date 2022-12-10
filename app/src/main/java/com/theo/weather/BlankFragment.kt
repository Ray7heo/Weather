package com.theo.weather

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.amap.api.location.AMapLocation
import com.google.gson.Gson
import com.theo.weather.databinding.FragmentBlankBinding
import pub.devrel.easypermissions.EasyPermissions
import kotlin.system.exitProcess

class BlankFragment : Fragment(), EasyPermissions.PermissionCallbacks
{

    companion object
    {
        const val RC_LOCATION = 1
    }

    private lateinit var bd: FragmentBlankBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        bd = FragmentBlankBinding.inflate(layoutInflater, container, false)
        return bd.root
    }

    //请求定位权限
    private fun requestPermission()
    {
        val permissions: Array<String> = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (!EasyPermissions.hasPermissions(requireContext(), *permissions))
        {
            //没有权限时重新请求
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale),
                RC_LOCATION,
                *permissions
            )
        }
        else
        {
            //用户授权后跳转到天气页面
            val str = requireContext()
                .getSharedPreferences("CityInfo", Context.MODE_PRIVATE).getString("location", "")

            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            //本地没有位置信息且没开启定位跳转到设置
            if (str == "" && !gps)
            {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                Toast.makeText(requireContext(), getString(R.string.need_GPS), Toast.LENGTH_LONG)
                    .show()
                requireActivity().finish()
            }
            else
            {
                findNavController().navigate(R.id.action_blankFragment_to_weatherFragment)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
    }

    //请求权限结果回调
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
    {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    //用户同意
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>)
    {
        findNavController().navigate(R.id.action_blankFragment_to_weatherFragment)
    }

    //用户拒绝
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>)
    {

    }

}