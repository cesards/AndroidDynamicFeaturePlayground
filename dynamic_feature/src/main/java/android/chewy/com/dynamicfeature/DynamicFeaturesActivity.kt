package android.chewy.com.dynamicfeature

import android.chewy.com.base.DynamicFeature
import android.chewy.com.base.Feature
import android.chewy.com.base.FeaturesAdapter
import android.chewy.com.base.features
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.chewy.test.dynamicfeatures.SplitActivity

class DynamicFeaturesActivity : SplitActivity(), DynamicFeature {

    private val featuresAdapter = FeaturesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_features)
        setupViews()

        featuresAdapter.withFeatures(features())
    }

    override fun load(feature: Feature) { }

    private fun setupViews() {
        val featuresView = findViewById<RecyclerView>(R.id.dynamic_features)
        featuresView.adapter = featuresAdapter
    }
}