package com.example.yummy.di

import android.content.Context
import androidx.room.Room
import com.example.yummy.data.datasource.FavoriteDataSource
import com.example.yummy.data.datasource.MealsDataSource
import com.example.yummy.data.repository.FavoriteRepository
import com.example.yummy.data.repository.MealsRepository
import com.example.yummy.retrofit.ApiUtils
import com.example.yummy.retrofit.MealsDao
import com.example.yummy.room.AppDatabase
import com.example.yummy.room.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMealsDao(): MealsDao {
        return ApiUtils.getMealsDao()

    }

    @Provides
    @Singleton
    fun provideMealsDataSource(mealsDao: MealsDao): MealsDataSource {
        return MealsDataSource(mealsDao)
    }

    @Provides
    @Singleton
    fun provideMealsRepository(mds: MealsDataSource): MealsRepository {
        return MealsRepository(mds)
    }


    //room
    @Provides
    @Singleton
    fun provideFavoriteDao(@ApplicationContext context: Context): FavoriteDao {
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "favorite.sqlite")
            .createFromAsset("favorite.sqlite").build()
        return db.getFavoriteDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDataSource(favDao: FavoriteDao): FavoriteDataSource {
        return FavoriteDataSource(favDao)
    }
    @Provides
    @Singleton
    fun provideFavoriteRepository(favDs: FavoriteDataSource): FavoriteRepository {
        return FavoriteRepository(favDs)

    }
}