package com.example.myapplication.ui.main.data.repo.remote

import com.example.myapplication.ui.main.data.repo.remote.api.Service
import com.example.myapplication.ui.main.data.repo.remote.api.executeForResult
import com.example.myapplication.ui.main.data.repo.remote.model.ResultsData
import com.example.myapplication.ui.main.data.repo.remote.api.RequestResult
import retrofit2.Call

class RemoteRepository<T>(private var serviceApi: Service): RemoteData<T> {

    override fun pullUserData(page: Int, items: Int): T {
        val serviceCall: Call<ResultsData> = serviceApi.getUsers(page, items)

        return when( val res = serviceCall.executeForResult() ){
            is RequestResult.Success -> {
               res.response.body() as T
            }
            is RequestResult.Failure -> {
                throw  res.error
            }
        }
    }
}