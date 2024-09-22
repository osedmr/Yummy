package com.example.yummy.di

import com.example.yummy.data.datasource.MealsDataSource
import com.example.yummy.data.repository.MealsRepository
import com.example.yummy.retrofit.ApiUtils
import com.example.yummy.retrofit.MealsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMealsDao() : MealsDao {
        return ApiUtils.getMealsDao()

    }
    @Provides
    @Singleton
    fun provideMealsDataSource(mealsDao: MealsDao) : MealsDataSource {
        return MealsDataSource(mealsDao)
    }
    @Provides
    @Singleton
    fun provideMealsRepository(mds: MealsDataSource) : MealsRepository {
        return MealsRepository(mds)
    }
}