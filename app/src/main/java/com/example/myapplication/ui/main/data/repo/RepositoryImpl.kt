package com.example.myapplication.ui.main.data.repo

import com.example.myapplication.ui.main.data.repo.local.LocalData
import com.example.myapplication.ui.main.data.repo.local.model.User
import com.example.myapplication.ui.main.data.repo.remote.RemoteData
import com.example.myapplication.ui.main.data.repo.remote.model.ResultsData
import com.example.myapplication.ui.main.util.toDaoModel
import com.example.myapplication.ui.main.util.toUserDetailsModel
import com.example.myapplication.ui.main.util.toUserModel
import com.example.myapplication.ui.main.view.model.DetailedUser
import com.example.myapplication.ui.main.view.model.ListUser

class RepositoryImpl private constructor(private val remote: RemoteData<ResultsData>, private val local: LocalData<User>) : Repository {

    override suspend fun fetchItems(page: Int, items: Int): List<ListUser> {
        var daoRecords = local.getUsers(page)

        if (daoRecords.isEmpty()) {

            val resultData = remote.pullUserData(page = page, items = items)

            daoRecords = resultData.results.toDaoModel()

            local.save(daoRecords, resultData.info.page)
        }

        return daoRecords.toUserModel()
    }

    override suspend fun fetchDetails(uuid: String): DetailedUser {
        return local.details(uuid).toUserDetailsModel()
    }

    companion object {

        @Volatile private var instance: Repository? = null

        fun getInstance(service: RemoteData<ResultsData>, cache: LocalData<User>) =
            instance ?: synchronized(this) {
                instance ?: RepositoryImpl(service, cache).also { instance = it }
            }
    }
}