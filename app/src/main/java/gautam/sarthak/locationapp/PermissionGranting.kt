package gautam.sarthak.locationapp

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import android.Manifest
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat

@Composable
fun AccessLocation(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    context: Context
) {
    val location by viewModel.actualLocation
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                locationUtils.getLocationUpdates(viewModel = viewModel)
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if (rationaleRequired) {
                    Toast.makeText(context, "Location permission required", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Location permission required, Grant Location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (location != null) {
            Text(text = "The location is ${location!!.latitude} ${location!!.longitude}", fontSize = 12.sp)
        } else {
            Text(text = "No Location Available", fontSize = 12.sp)
        }
        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)) {
                locationUtils.getLocationUpdates(viewModel)
            } else {
                requestPermissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
            }
        }) {
            Text(text = "Get Location")
        }
    }
}
