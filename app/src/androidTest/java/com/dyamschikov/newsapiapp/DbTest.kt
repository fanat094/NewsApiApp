package com.dyamschikov.newsapiapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dyamschikov.newsapiapp.data.local.AppDataBase
import com.dyamschikov.newsapiapp.data.local.AppDataBaseDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DbTest {

    private lateinit var appDataBaseDao: AppDataBaseDao
    private lateinit var db: AppDataBase

    @Test
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        appDataBaseDao = db.appDataBaseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

//    @Test
//    @Throws(Exception::class)
//    fun insertAndGetNight() {
//
//        val night = ArticleEntity()
//        appDataBaseDao.insert(night)
//
//        val tonight = appDataBaseDao.getArticles()
//        assertEquals(tonight?.value, -1)
//    }
}