package com.khanhlh.firewarningkt.view.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.facebook.shimmer.ShimmerFrameLayout
import com.khanhlh.firewarningkt.R
import com.khanhlh.firewarningkt.constant.Constants
import com.khanhlh.firewarningkt.data.remote.weather.WeatherService
import com.khanhlh.firewarningkt.data.repository.WeatherRepository
import com.khanhlh.firewarningkt.databinding.FragmentHomeBinding
import com.khanhlh.firewarningkt.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val LOCATION_PERMS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val INITIAL_REQUEST = 1337
    private val LOCATION_REQUEST = INITIAL_REQUEST + 3

    private lateinit var mLocationManager: LocationManager
    private lateinit var mViewModel: HomeViewModel

    private var currentLocation: Location = Location("Ha Dong")

    companion object {
        fun newInstance(): HomeFragment {
            val args: Bundle = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val mLocationListener: LocationListener = object : LocationListener {
        @SuppressLint("CheckResult")
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                currentLocation = location
                loadData(true)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }
    }

    @SuppressLint("NewApi", "CheckResult")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        checkLocationPermission()
        currentLocation.longitude = 105.834160
        currentLocation.latitude = 21.027764
        val service = WeatherService.create()
        val repo = WeatherRepository(service)
        mViewModel = HomeViewModel(repo = repo)
        mBinding.vm = mViewModel

        mLocationManager =
            requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        mViewModel.tick()
        refresh.setOnRefreshListener { loadData(true) }
    }

    override fun loadData(isRefresh: Boolean) {
        mViewModel.getCurrentData(
            currentLocation.latitude.toString(),
            currentLocation.longitude.toString(),
            Constants.WEATHER_API_KEY,
            Constants.cDegree
        )
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onClick(v: View?) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_REQUEST -> {
                if (ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mLocationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0L,
                        0f,
                        mLocationListener
                    )
                }
                return
            }
        }
    }

    private fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(activity)
                    .setTitle(R.string.title_location_permission)
                    .setMessage(R.string.title_location_permission)
                    .setPositiveButton(
                        R.string.ok
                    ) { _, _ -> //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ),
                            LOCATION_REQUEST
                        )
                    }
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST
                )
            }
            false
        } else {
            true
        }
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0f,
                    mLocationListener
                )
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0f,
                    mLocationListener
                )
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    private fun ShimmerFrameLayout.handleShimmer(loading: Boolean) {
        if (loading) {
            startShimmer()
        } else {
            stopShimmer()
        }
    }
}
