package eu.balticit.copyrightly.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "User ViewModel is here"
    }
    val text: LiveData<String> = _text
}