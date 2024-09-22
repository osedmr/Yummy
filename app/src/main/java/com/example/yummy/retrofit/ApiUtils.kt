package com.example.yummy.retrofit

class ApiUtils {
    companion object{
        val BASE_URL = "http://kasimadalan.pe.hu/"
        fun getMealsDao() : MealsDao {
            return RetrofitClient.getClient(BASE_URL).create(MealsDao::class.java)
        }
    }

}