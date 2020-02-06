package www.itsamthing.doc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.squareup.picasso.Picasso

import androidx.recyclerview.widget.RecyclerView
import www.doc.test.R
import www.itsamthing.doc.baseurl.PersonUtils

class List_person(internal var c: Context, private val moviesList: List<PersonUtils>) :
    RecyclerView.Adapter<List_person.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //public TextView title, year, genre;
        internal var image: ImageView

        init {
            image = view.findViewById(R.id.image)
            //title = (TextView) view.findViewById(R.id.title);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.model2, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val lis = moviesList[position]
        Picasso.with(c)
            .load(lis.media)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}