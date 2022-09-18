package Adapter

import Tampilan.TampilanCard
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_0706012110014.R
import Hewan.Ternak
import com.example.week1_0706012110014.databinding.TampilanBinding
import java.util.ArrayList

class urutantampilan (val listlucu : ArrayList<Ternak>, val tampilan: TampilanCard) :
    RecyclerView.Adapter<urutantampilan.vieww>() {



    class vieww(val itemView: View, val tampilan: TampilanCard) :
        RecyclerView.ViewHolder(itemView) {

        val binding = TampilanBinding.bind(itemView)

        fun setData(data: Ternak) {
            binding.titlecard.text = data.nama
            if (data.addimage.isNotEmpty()) {
                binding.imageView3.setImageURI(Uri.parse(data.addimage))
            }
            itemView.setOnClickListener {
                tampilan.klikKartu(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vieww {
        val layoutInflater = LayoutInflater.from(parent.context)
        val lihat = layoutInflater.inflate(R.layout.tampilan, parent, false)
        return vieww(lihat, tampilan)
    }

    override fun getItemCount(): Int {
        return listlucu.size
    }
    override fun onBindViewHolder(holder: vieww, position: Int) {
        holder.setData(listlucu[position])
    }
}