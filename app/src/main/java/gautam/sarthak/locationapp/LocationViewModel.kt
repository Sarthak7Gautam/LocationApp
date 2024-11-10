package gautam.sarthak.locationapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LocationViewModel:ViewModel() {
    val location= mutableStateOf<LocationData?>(null)
    val actualLocation: State<LocationData?> =location

    fun updateLocation(newLocation:LocationData){
        location.value=newLocation
    }
}