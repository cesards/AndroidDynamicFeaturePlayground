package android.chewy.com.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeaturesAdapter : RecyclerView.Adapter<FeaturesAdapter.FeatureViewHolder>() {

    private val features = mutableListOf<Feature>()

    fun withFeatures(features : List<Feature>) {
        val previousFeatures = features.size
        this.features.addAll(features)
        notifyItemRangeInserted(previousFeatures - 1, features.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder =
        FeatureViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        )

    override fun getItemCount(): Int = features.size

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bind(features[position])
    }

    class FeatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title : TextView = itemView.findViewById(R.id.title)

        fun bind(feature: Feature) {
            title.text = feature.title
        }
    }
}