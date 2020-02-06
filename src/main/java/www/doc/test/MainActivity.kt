package www.itsamthing.doc

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import www.itsamthing.doc.baseurl.Person

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Adapter
import android.widget.Toast

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import org.json.JSONArray
import org.json.JSONObject
import www.doc.test.R

import java.util.ArrayList
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    private val adapters = ArrayList<Adapter>()
    private var recyclerView: RecyclerView? = null
    private val mAdapter: ListAdapter? = null
    internal lateinit var rq: RequestQueue
    internal var base: www.itsamthing.doc.baseurl.url? = null
    internal lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycleview)
        rq = Volley.newRequestQueue(baseContext)

        val obj = Handler()
        obj.postDelayed({ sendRequest() }, 0)
    }

    fun sendRequest() {
        val postRequest = object : StringRequest(base!!.URL,
            Response.Listener { response -> parsing(response) },
            Response.ErrorListener {
                // error
                Toast.makeText(baseContext, "error", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String>? {
                return null
            }
        }
        rq.add(postRequest)
    }


    internal fun parsing(response: String) {
        val listData = ArrayList<Person>()
        try {
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val news = Person()
                news.title = obj.getString("title")
                news.content = obj.getString("content")
                news.type = obj.getString("type")
                news.media = obj.getString("media")
                listData.add(news)
            }

            adapter = ListAdapter(baseContext, listData)
            recyclerView!!.adapter = adapter
            recyclerView!!.layoutManager = LinearLayoutManager(baseContext)

        } catch (ex: Exception) {
            Toast.makeText(baseContext, "Bad Response", Toast.LENGTH_SHORT).show()
            //Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }

}
