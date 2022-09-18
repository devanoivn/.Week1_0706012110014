package com.example.week1_0706012110014

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import Database.ArrayList
import Hewan.Ternak
import androidx.activity.result.contract.ActivityResultContracts
import com.example.week1_0706012110014.databinding.ActivityInputBinding

class Input : AppCompatActivity() {
    private lateinit var viewBind: ActivityInputBinding
    private lateinit var binatang: Ternak
    var position = -1
    var foto: String = ""

    private val hasil = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
            val uri = it.data?.data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(uri != null){
                    baseContext.getContentResolver().takePersistableUriPermission(uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }}// GET PATH TO IMAGE FROM GALLEY
            viewBind.addimage.setImageURI(uri)  // MENAMPILKAN DI IMAGE VIEW
            foto = uri.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityInputBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        ngesetdata()
        savedata()
    }

    private fun memastikan()
    {
        var isCompleted:Boolean = true

        if(binatang.jenis!!.isEmpty()){
            viewBind.jenisinput.error = "Jenis hewan tidak boleh kosong"
            isCompleted = false
        }else{
            viewBind.jenisinput.error = ""
        }
        if(binatang.nama!!.isEmpty()){
            viewBind.namainput.error = "Nama tidak boleh kosong"
            isCompleted = false
        }else{
            viewBind.namainput.error = ""
        }
        if(binatang.deskripsi!!.isEmpty()){
            viewBind.deskripsiinput.error = "Deskripsi tidak boleh kosong"
            isCompleted = false
        }else{
            viewBind.deskripsiinput.error = ""
        }
        binatang.addimage = foto.toString()
        if(viewBind.usiainput.editText?.text.toString().isEmpty() || viewBind.usiainput.editText?.text.toString().toInt() < 0)
        {
            viewBind.usiainput.error = "rating cannot be empty or 0"
            isCompleted = false
        }

        if(isCompleted == true)
        {
            if(position == -1)
            {
                binatang.usia = viewBind.usiainput.editText?.text.toString().toInt()
                ArrayList.listDatahewan.add(binatang)

            }else
            {
                binatang.usia = viewBind.usiainput.editText?.text.toString().toInt()
                ArrayList.listDatahewan[position] = binatang
            }
            finish()
        }
    }
    private fun savedata(){
        viewBind.addimage.setOnClickListener{
            val bukagaleri = Intent(Intent.ACTION_OPEN_DOCUMENT)
            bukagaleri.type = "image/*"
            hasil.launch(bukagaleri)
        }

        viewBind.button.setOnClickListener{
            var nama = viewBind.namainput.editText?.text.toString().trim()
            var jenis = viewBind.jenisinput.editText?.text.toString().trim()
            var usia = 0
            var deskripsi = viewBind.deskripsiinput.editText?.text.toString().trim()

            binatang = Ternak(nama, jenis, usia, deskripsi)
            memastikan()
        }

        viewBind.toolbar3.getChildAt(1).setOnClickListener {
            finish()
        }
    }
    private fun ngesetdata(){
        position = intent.getIntExtra("position", -1)
        if(position != -1){
            val human = ArrayList.listDatahewan[position]
            viewBind.toolbar3.title = "Edit Data"
            viewBind.button.text = "Edit data"
            viewBind.addimage.setImageURI(Uri.parse(ArrayList.listDatahewan[position].addimage))
            viewBind.namainput.editText?.setText(human.nama)
            viewBind.jenisinput.editText?.setText(human.jenis)
            viewBind.usiainput.editText?.setText(human.usia.toString())
            viewBind.deskripsiinput.editText?.setText(human.deskripsi)
        }
    }
}