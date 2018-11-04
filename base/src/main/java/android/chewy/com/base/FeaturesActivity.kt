package android.chewy.com.base

import android.chewy.com.data.FeaturesAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

private const val TAG = "DynamicFeatures"

class FeaturesActivity : AppCompatActivity() {

    private val listener = SplitInstallStateUpdatedListener { state ->
        val multiInstall = state.moduleNames().size > 1
        state.moduleNames().forEach { name: String? ->
            // Handle changes in state.
            when(state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    //  In order to see this, the application has to be uploaded to the Play Store.
                    displayLoadingState(state, "Downloading $name")
                }
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    /*
                     This may occur when attempting to download a sufficiently large module.
                     In order to see this, the application has to be uploaded to the Play Store.
                     Then features can be requested until the confirmation path is triggered.
                    */
                    startIntentSender(state.resolutionIntent()?.intentSender, null, 0, 0, 0)
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    onSuccessfulLoad(name!!, launch = !multiInstall)
                }
                SplitInstallSessionStatus.INSTALLING -> displayLoadingState(state, "Installing $name")
                SplitInstallSessionStatus.FAILED -> {
                    toastAndLog("Error: ${state.errorCode()} for module ${state.moduleNames()}")
                }
            }
        }
    }

    private lateinit var manager: SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_features)
        setupViews()
        manager = SplitInstallManagerFactory.create(this)
    }

    override fun onResume() {
        super.onResume()
        // Listener can be registered even without directly triggering a download.
        manager.registerListener(listener)
    }

    override fun onPause() {
        // Make sure to dispose of the listener once it's no longer needed.
        manager.unregisterListener(listener)
        super.onPause()
    }

    private fun setupViews() {
        val featuresView = findViewById<RecyclerView>(R.id.features)
        featuresView.adapter = FeaturesAdapter()
    }

    /**
     * Load a feature by module name.
     * @param name The name of the feature module to load.
     */
    private fun loadAndLaunchModule(name: String) {
//        updateProgressMessage("Loading module $name")
        // Skip loading if the module already is installed. Perform success action directly.
        if (manager.installedModules.contains(name)) {
//            updateProgressMessage("Already installed")
            onSuccessfulLoad(name, launch = true)
            return
        }

        // Create request to install a feature module by name.
        val request = SplitInstallRequest.newBuilder()
            .addModule(name)
            .build()

        // Load and install the requested feature module.
        manager.startInstall(request)

//        updateProgressMessage("Starting install for $name")
    }

    /** Display assets loaded from the assets feature module. */
    private fun displayAssets() {
        // Get the asset manager with a refreshed context, to access content of newly installed apk.
        val assetManager = createPackageContext(packageName, 0).assets
        // Now treat it like any other asset file.
        val assets = assetManager.open("assets.txt")
        val assetContent = assets.bufferedReader()
            .use {
                it.readText()
            }

        AlertDialog.Builder(this)
            .setTitle("Asset content")
            .setMessage(assetContent)
            .show()
    }

    /** Install all features but do not launch any of them. */
//    private fun installAllFeaturesNow() {
//        // Request all known modules to be downloaded in a single session.
//        val request = SplitInstallRequest.newBuilder()
//            .addModule(moduleKotlin)
//            .addModule(moduleJava)
//            .addModule(moduleNative)
//            .addModule(moduleAssets)
//            .build()
//
//        // Start the install with above request.
//        manager.startInstall(request).addOnSuccessListener {
//            toastAndLog("Loading ${request.moduleNames}")
//        }
//    }

    /** Install all features deferred. */
//    private fun installAllFeaturesDeferred() {
//
//        val modules = listOf(moduleKotlin, moduleJava, moduleAssets, moduleNative)
//
//        manager.deferredInstall(modules).addOnSuccessListener {
//            toastAndLog("Deferred installation of $modules")
//        }
//    }

    /** Request uninstall of all features. */
    private fun requestUninstall() {

        toastAndLog("Requesting uninstall of all modules." +
                "This will happen at some point in the future.")

        val installedModules = manager.installedModules.toList()
        manager.deferredUninstall(installedModules).addOnSuccessListener {
            toastAndLog("Uninstalling $installedModules")
        }
    }

    /**
     * Define what to do once a feature module is loaded successfully.
     * @param moduleName The name of the successfully loaded module.
     * @param launch `true` if the feature module should be launched, else `false`.
     */
    private fun onSuccessfulLoad(moduleName: String, launch: Boolean) {
//        if (launch) {
//            when (moduleName) {
//                moduleKotlin -> launchActivity(kotlinSampleClassname)
//                moduleJava -> launchActivity(javaSampleClassname)
//                moduleNative -> launchActivity(nativeSampleClassname)
//                moduleAssets -> displayAssets()
//            }
//        }
//
//        displayButtons()
    }

    /** Launch an activity by its class name. */
    private fun launchActivity(className: String) {
        Intent().setClassName(packageName, className)
            .also {
                startActivity(it)
            }
    }

    /** Display a loading state to the user. */
    private fun displayLoadingState(state: SplitInstallSessionState, message: String) {
//        displayProgress()

//        progressBar.max = state.totalBytesToDownload().toInt()
//        progressBar.progress = state.bytesDownloaded().toInt()

//        updateProgressMessage(message)
    }

    fun toastAndLog(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        Log.d(TAG, text)
    }
}