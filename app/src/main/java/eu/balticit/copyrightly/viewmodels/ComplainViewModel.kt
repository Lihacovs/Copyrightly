package eu.balticit.copyrightly.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ComplainViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Complain Fragment"
    }
    val text: LiveData<String> = _text
}