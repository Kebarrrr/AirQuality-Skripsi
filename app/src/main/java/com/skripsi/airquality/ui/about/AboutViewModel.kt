package com.skripsi.airquality.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _jobTitle = MutableLiveData<String>()
    val jobTitle: LiveData<String> get() = _jobTitle

    private val _bio = MutableLiveData<String>()
    val bio: LiveData<String> get() = _bio

    private val _projectsCreated = MutableLiveData<String>()
    val projectsCreated: LiveData<String> get() = _projectsCreated

    private val _skills = MutableLiveData<String>()
    val skills: LiveData<String> get() = _skills

    private val _experience = MutableLiveData<String>()
    val experience: LiveData<String> get() = _experience

    fun loadProfileData() {
        _name.value = "Akbar Nugraha Dimyati"
        _jobTitle.value = "Mobile Developer"
        _bio.value = "I am a passionate mobile developer with experience in Android development."
        _projectsCreated.value = "Projects Created: 5"
        _skills.value = "Skills: Java, Kotlin, Android SDK, Firebase"
        _experience.value = "Experience: 2+ years in Android Development"
    }
}