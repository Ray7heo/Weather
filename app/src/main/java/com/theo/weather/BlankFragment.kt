package com.theo.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pub.devrel.easypermissions.EasyPermissions

class BlankFragment : Fragment(), EasyPermissions.PermissionCallbacks
{

    companion object
    {
        const val RC_LOCATION = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        requestPermission()
        return inflater.inflate(R.layout.fragment_blank, container, false)
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
            findNavController().navigate(R.id.action_blankFragment_to_weatherFragment)
        }
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