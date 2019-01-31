package `in`.foodie.database

import `in`.foodie.adapters.Converter
import `in`.foodie.utils.DATABASE_NAME
import `in`.foodie.worker.SeedDatabaseWorker
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

/**
 * Setting Rooms Database
 */
@Database(entities = [Recipe::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }


        private fun buildDatabase(context: Context): AppDatabase {

            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .build()

            //For testing purpose
            // Create and pre-populate the database.
            // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
            /*return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance().enqueue(request)
                        }
                    })
                    .build()*/
        }
    }

}