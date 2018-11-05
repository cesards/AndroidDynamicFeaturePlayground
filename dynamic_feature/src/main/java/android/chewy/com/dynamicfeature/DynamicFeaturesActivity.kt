package android.chewy.com.dynamicfeature

import android.chewy.com.data.DynamicFeature
import android.chewy.com.data.Feature
import android.chewy.com.data.FeaturesAdapter
import android.chewy.com.data.features
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

// DynamicFeaturesActivity.kt: (3, 31): Unresolved reference: FeaturesAdapter
// implementation project(':app')
// implementation project(':base')
// Might it be because of a cyclic dependency