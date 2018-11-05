package android.chewy.com.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeaturesAdapter(val dynamicFeature: DynamicFeature) : RecyclerView.Adapter<FeaturesAdapter.FeatureViewHolder>() {

    private val features = mutableListOf<Feature>()

    fun withFeatures(features : List<Feature>) {
        val previousFeatures = features.size
        this.features.addAll(features)

        notifyItemRangeInserted(previousFeatures - 1, features.size)
    }

    fun changeFeatureState(feature: Feature) {
        features.firstOrNull { it == feature }?.loading

        val indexOfFeature = features.indexOfFirst { it == feature }
        if (indexOfFeature >= 0) {
            notifyItemChanged(indexOfFeature)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder =
        FeatureViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_feature, parent, false)
        )

    override fun getItemCount(): Int = features.size

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bind(features[position])
    }

    inner class FeatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title : TextView = itemView.findViewById(R.id.feature)
        val progress : ProgressBar = itemView.findViewById(R.id.progress)
        val progressTitle : TextView = itemView.findViewById(R.id.progress_text)

        fun bind(feature: Feature) {
            title.text = feature.title

            changeLoadingVisibilityState(feature.loading)

            itemView.setOnClickListener {
                feature.loading = true
                changeLoadingVisibilityState(true)
                dynamicFeature.load(feature)
            }
        }

        private fun changeLoadingVisibilityState(loading: Boolean) {
            val inProgress = if (loading) View.VISIBLE else View.INVISIBLE
            progress.visibility = inProgress
            progressTitle.visibility = inProgress
        }
    }
}