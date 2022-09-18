package com.example.week1_0706012110014

import Adapter.urutantampilan
import Tampilan.TampilanCard
import android.Manifest
import Database.ArrayList
import android.content.Intent
import android.content.pm.PackageManager
import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week1_0706012110014.databinding.ActivityHomeBinding

class Home : AppCompatActivity(), TampilanCard{
    private lateinit var viewBind:ActivityHomeBinding
    private val adapter = urutantampilan(ArrayList.listDatahewan, this)
    private var jumlahdata: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        Recycle()
        datahewan()
        izin()
    }

    private fun datahewan(){
        viewBind.addhewan.setOnClickListener {
            val pindahinput = Intent(this, Input::class.java)
            startActivity(pindahinput)
        }
    }
    override fun klikKartu(position: Int) {
        val pindahinfo = Intent(this, Info::class.java).apply {
            putExtra("position", position)
        }
        startActivity(pindahinfo)
    }
    override fun onResume() {
        super.onResume()
        jumlahdata = ArrayList.listDatahewan.size
        if(jumlahdata == 0)
        {
            viewBind.tambah.alpha = 1f
        }else
        {
            viewBind.tambah.alpha = 0f
        }
        adapter.notifyDataSetChanged()
    }
    private fun Recycle(){
        val Clean = GridLayoutManager(this,2)
        viewBind.list.layoutManager = Clean
        viewBind.list.adapter = adapter
    }
    private fun izin(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), ArrayList.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Izin penyimpanan diberikan", Toast.LENGTH_SHORT).show()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), ArrayList.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Izin penyimpanan diberikan", Toast.LENGTH_SHORT).show()
        }
    }

}