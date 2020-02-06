package www.itsamthing.doc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import java.util.ArrayList

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import www.doc.test.R
import www.itsamthing.doc.baseurl.ItemClickListener
import www.itsamthing.doc.baseurl.Person
import www.itsamthing.doc.baseurl.PersonUtils

class ListAdapter(internal var c: Context, data: List<Person>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal var inflater: LayoutInflater
    internal var data = emptyList<Person>()
    internal lateinit var current: Person
    internal lateinit var substr: String
    private val personsList = ArrayList<PersonUtils>()
    private var mAdapter: List_person? = null
    internal lateinit var person: PersonUtils

    init {
        inflater = LayoutInflater.from(c)
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.model, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as MyHolder
        current = data[position]

        substr = current.media!!.substring(1)

        substr = substr.substring(1)
        substr = StringBuilder(substr).reverse().toString()
        substr = substr.substring(1)
        substr = StringBuilder(substr).reverse().toString()
        if (current.type!!.equals("image", ignoreCase = true)) {
            myHolder.title.text = current.title
            myHolder.deskripsi.text = current.content

            Picasso.with(c)
                .load(substr)
                .placeholder(R.mipmap.ic_launcher)
                .into(myHolder.media)

        } else {
            myHolder.card.visibility = View.GONE
            myHolder.card2.visibility = View.VISIBLE

            myHolder.title1.text = current.title
            myHolder.deskripsi1.text = current.content

            personsList.clear()
            mAdapter = List_person(c, personsList)
            val mLayoutManager = LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false)
            myHolder.recycle.layoutManager = mLayoutManager
            myHolder.recycle.itemAnimator = DefaultItemAnimator()
            myHolder.recycle.adapter = mAdapter

            Data(substr)
        }


        myHolder.setItemClickListener(object : ItemClickListener {
            override fun onItemClick(layoutPosition: Int) {
                current = data[layoutPosition]
                substr = current.media!!.substring(1)

            }
        })
    }

    private fun Data(sub: String) {
        val parts = sub.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var `var` = ""
        for (a in parts.indices) {
            `var` = parts[a]
            if (a != 0) {
                `var` = `var`.substring(1)
                `var` = StringBuilder(`var`).reverse().toString()
                `var` = `var`.substring(1)
                `var` = StringBuilder(`var`).reverse().toString()
            } else {
                `var` = StringBuilder(`var`).reverse().toString()
                `var` = `var`.substring(1)
                `var` = StringBuilder(`var`).reverse().toString()
            }
            person = PersonUtils(`var`)
            personsList.add(person)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var title: TextView
        var deskripsi: TextView
        var title1: TextView
        var deskripsi1: TextView
        var media: ImageView
        var card: CardView
        var card2: CardView
        var recycle: RecyclerView
        lateinit var itemClickListener: ItemClickListener

        init {
            val context: Context? = null
            title = itemView.findViewById(R.id.title)
            deskripsi = itemView.findViewById(R.id.description)
            title1 = itemView.findViewById(R.id.title1)
            deskripsi1 = itemView.findViewById(R.id.description1)
            media = itemView.findViewById(R.id.image)
            card = itemView.findViewById(R.id.card)
            card2 = itemView.findViewById(R.id.card2)
            recycle = itemView.findViewById(R.id.recycle)
            itemView.setOnClickListener(this)


        }

        override fun onClick(v: View) {
            this.itemClickListener.onItemClick(layoutPosition)
        }
    }

}

internal fun ListAdapter.MyHolder.setItemClickListener(ic: ItemClickListener) {
    this.itemClickListener = ic
}
