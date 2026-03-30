package ru.practicum.android.diploma.feature.vacancy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.util.Resource

class VacancyViewModel(private val vacancyInteractor: VacancyInteractor) : ViewModel() {

    private val _vacancyDetail = MutableLiveData<VacancyState>()
    fun observeVacancyDetail(): LiveData<VacancyState> = _vacancyDetail

    fun getVacancyDetail(id: String) {
        viewModelScope.launch {
            _vacancyDetail.postValue(VacancyState.Loading)
            vacancyInteractor.getVacancyDetail(id).collect {
                when (it) {
                    is Resource.Error -> _vacancyDetail.postValue(VacancyState.Error(it.message!!))
                    is Resource.Success -> _vacancyDetail.postValue(VacancyState.Content(it.data!!))
                }
            }
        }
    }

    fun sendVacancyViaMessenger(url: String){
        viewModelScope.launch {
            vacancyInteractor.sendVacancyViaMessenger(url)
        }
    }
    fun selectEmailClientAndSend(email: String){
        viewModelScope.launch {
            vacancyInteractor.selectEmailClientAndSend(email)
        }
    }
    fun showCallAppsAndDial(phoneNumber: String){
        viewModelScope.launch {
            vacancyInteractor.showCallAppsAndDial(phoneNumber)
        }
    }


}
