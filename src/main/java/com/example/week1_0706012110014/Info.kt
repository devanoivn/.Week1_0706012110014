package com.example.week1_0706012110014

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import Database.ArrayList
import Hewan.Ternak
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.week1_0706012110014.databinding.ActivityInfoBinding
import com.google.android.material.snackbar.Snackbar

class Info : AppCompatActivity() {
    private lateinit var viewBind: ActivityInfoBinding
    private var letak: Int = -1
    private val hasil = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val foto = it.data?.data
            viewBind.infofoto.setImageURI(foto)
            ArrayList.listDatahewan[letak].addimage = foto.toString()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        getfoto()
        menghapusdata()
    }
    override fun onResume() {
        val sapi = ArrayList.listDatahewan[letak]
        super.onResume()

        look(sapi)
    }
    private fun getfoto(){
        letak = intent.getIntExtra("position", -1)
        val movie = ArrayList.listDatahewan[letak]
        if(!movie.addimage.isEmpty())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                baseContext.getContentResolver().takePersistableUriPermission(Uri.parse(ArrayList.listDatahewan[letak].addimage),
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
        }
        look(movie)
    }

    private fun look(hewan: Ternak){
        viewBind.infofoto.setImageURI(Uri.parse(ArrayList.listDatahewan[letak].addimage))
        viewBind.namainfo.text = hewan.nama
        viewBind.jenisinfo.text = hewan.jenis
        viewBind.usiainfo.text = hewan.usia.toString()
        viewBind.deskripsiinfo.text = hewan.deskripsi
    }
    private fun menghapusdata(){

        viewBind.hapusbutton.setOnClickListener {
            val delete = AlertDialog.Builder(this)
            delete.setTitle("Delete data")
            delete.setMessage("Apakah kamu ingin menghapus data ini?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            delete.setPositiveButton(android.R.string.yes) { function, which ->
                val hapusdata = Snackbar.make(viewBind.hapusbutton, "Data Deleted", Snackbar.LENGTH_INDEFINITE)
                hapusdata.setAction("Gagal") { hapusdata.dismiss() }
                hapusdata.setActionTextColor(Color.WHITE)
                hapusdata.setBackgroundTint(Color.GRAY)
                hapusdata.show()

                //remove
                ArrayList.listDatahewan.removeAt(letak)
                finish()
            }

            delete.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            delete.show()
        }

        viewBind.toolbar.getChildAt(1).setOnClickListener {
            finish()
        }

        viewBind.floatingActionButton.setOnClickListener {
            val keinput = Intent(this, Input::class.java).apply {
                putExtra("position", letak)
            }
            startActivity(keinput)
        }
    }
}