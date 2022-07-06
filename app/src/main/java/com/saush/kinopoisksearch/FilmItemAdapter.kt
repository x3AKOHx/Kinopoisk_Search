package com.saush.kinopoisksearch

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saush.kinopoisksearch.filmData.Doc
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class FilmItemAdapter(
    private val context: Context,
    private val filmsList: MutableList<Doc>,
    private val onClickListener: OnClickListener):
    RecyclerView.Adapter<FilmItemAdapter.FilmItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmItemViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.film_item_layout,
            parent, false)
        return FilmItemViewHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: FilmItemViewHolder, position: Int) {
        val film = filmsList[position]

        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(film.poster!!.previewUrl)
            .placeholder(context.resources.getDrawable(R.drawable.ic_launcher_foreground, context.theme))
            .into(holder.poster)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(film)
        }
        holder.bind(film)
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    class FilmItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val filmName: TextView = itemView.findViewById(R.id.film_name)
        private val filmYear: TextView = itemView.findViewById(R.id.film_year)
        private val kpRating: TextView = itemView.findViewById(R.id.kp_rating)
        private val imdbRating: TextView = itemView.findViewById(R.id.imdb_rating)
        private val filmLength: TextView = itemView.findViewById(R.id.film_length)

        var poster: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(film: Doc) {
            filmName.text = film.name
            filmYear.text = film.year.toString()
            kpRating.text = film.rating!!.kp.toString()
            imdbRating.text = film.rating.imdb.toString()
            filmLength.text = film.movieLength.toString()
        }
    }

    class OnClickListener(val clickListener: (film: Doc) -> Unit) {
        fun onClick(film: Doc) = clickListener(film)
    }
}
