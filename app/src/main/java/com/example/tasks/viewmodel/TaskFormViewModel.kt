package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.listener.ApiListener
import com.example.tasks.service.listener.ValidationListener
import com.example.tasks.service.model.PriorityModel
import com.example.tasks.service.model.TaskModel
import com.example.tasks.service.repository.PriorityRepository
import com.example.tasks.service.repository.TaskRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mPriorityRepository = PriorityRepository(application);
    private val mTaskRepository = TaskRepository(application);

    // CRIAR OBSERVADOR
    private val mPriorityList = MutableLiveData<List<PriorityModel>>()
    var priorities : LiveData<List<PriorityModel>> = mPriorityList

    private val mValidation = MutableLiveData<ValidationListener>()
    var validation : LiveData<ValidationListener> = mValidation

    fun listPriorities() {
        mPriorityList.value = mPriorityRepository.list();
    }

    fun save(task: TaskModel) {
        mTaskRepository.create(task, object : ApiListener<Boolean> {

            override fun onSucess(model: Boolean) {
                mValidation.value = ValidationListener();
            }

            override fun onFailure(str: String) {
                mValidation.value = ValidationListener(str);
            }

        });
    }
}