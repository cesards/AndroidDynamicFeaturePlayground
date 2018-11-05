package android.chewy.com.base

import androidx.annotation.DrawableRes

interface DynamicFeature {
    fun load(feature: Feature)
}

data class Feature(val title: String, @DrawableRes val resourceId: Int, var loading: Boolean = false)

fun features() : List<Feature> {
    return listOf(
        Feature("Feature 1", R.drawable.a1),
        Feature("Feature 2", R.drawable.a2),
        Feature("Feature 3", R.drawable.a3),
        Feature("Feature 4", R.drawable.a4),
        Feature("Feature 5", R.drawable.a5),
        Feature("Feature 6", R.drawable.a6),
        Feature("Feature 7", R.drawable.a7),
        Feature("Feature 8", R.drawable.a8),
        Feature("Feature 9", R.drawable.a9),
        Feature("Feature 10", R.drawable.a10),
        Feature("Feature 11", R.drawable.a11),
        Feature("Feature 12", R.drawable.a12),
        Feature("Feature 13", R.drawable.a13),
        Feature("Feature 14", R.drawable.a14),
        Feature("Feature 15", R.drawable.a15),
        Feature("Feature 16", R.drawable.a16),
        Feature("Feature 17", R.drawable.a17),
        Feature("Feature 18", R.drawable.a18),
        Feature("Feature 19", R.drawable.a19),
        Feature("Feature 20", R.drawable.a20),
        Feature("Feature 21", R.drawable.a21),
        Feature("Feature 22", R.drawable.a22),
        Feature("Feature 23", R.drawable.a23),
        Feature("Feature 24", R.drawable.a24)
    )
}