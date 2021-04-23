package com.example.location

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    var map: GoogleMap? = null
    var supportMapFragment: SupportMapFragment? = null
    var searchView: SearchView? = null
    var button: Button? = null
    private lateinit var recycler : RecyclerView
    private lateinit var textAddress : TextView
    private lateinit var searchAdapter: SearchAdapter

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        searchView = findViewById(R.id.searchLocation)
        button = findViewById(R.id.btnConfirmLocation)
        recycler = findViewById(R.id.recyclerView)
        textAddress = findViewById(R.id.txtCurrentText)
        supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        val add = intent.getStringExtra("address")
        textAddress.text = add
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            View.OnClickListener {
            override fun onQueryTextChange(newText: String): Boolean {
                val location = searchView!!.query.toString()
                var addressList:List<Address>? = null
                if(location != ""){
                    recycler.visibility = View.VISIBLE
                    val geocoder = Geocoder(this@MapsActivity)
                    try {
                        addressList = geocoder.getFromLocationName(location, 1)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    searchAdapter = addressList?.let {
                        SearchAdapter(it, object : SearchAdapter.SearchCallback
                        {
                            override fun onText(position: Int) {
                                val address = addressList[position].getAddressLine(0)
                                val latLng = LatLng(addressList[0].latitude, addressList[0].longitude)
                                textAddress.text = address.toString()
                                recycler.visibility = View.GONE
                                Log.d("SearchTag", address.toString())
                                Log.d("SearchTag", latLng.toString())
                            }

                        })
                    }!!
                    recycler.layoutManager = LinearLayoutManager(applicationContext)
                    recycler.adapter = searchAdapter
                }
                return false
            }

            override fun onQueryTextSubmit(s: String): Boolean {
                // task HERE
//                val location = searchView!!.query.toString()
//                var addressList:List<Address>? = null
//                if (!location.equals("")){
//                    val geocoder = Geocoder(this@MapsActivity)
//                    try {
//                        addressList = geocoder.getFromLocationName(location, 1)
//                    }catch (e:Exception){
//                        e.printStackTrace()
//                    }
//
//                    if (addressList!=null){
//                        val address = addressList!![0].getAddressLine(0)
//                        val latLng = LatLng(addressList!![0].latitude, addressList!![0].longitude)
//                        map?.addMarker(MarkerOptions().position(latLng).title(location))
//                        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
//                        textAddress.text = address.toString()
//                        Log.d("Tag", address.toString())
//                        Log.d("Tag", latLng.toString())
//                        Log.d("Tag", addressList.toString())
//                    }else{
//                        Toast.makeText(this@MapsActivity, "addressList = null" , Toast.LENGTH_LONG).show()
//                    }
//                }

                return false
            }

            override fun onClick(v: View?) {
                TODO("Not yet implemented")
            }
        }
        )

        supportMapFragment!!.getMapAsync(OnMapReadyCallback {
            map = it
        })

        button!!.setOnClickListener {
            val i = Intent(this, LogInActivity::class.java)
            startActivity(i)
        }

    }
}