package `in`.foodie

import android.app.Application

class FoodieApp : Application()
{
    companion object {
    lateinit var instance: Application
}
    override fun onCreate() {
        super.onCreate()
        instance=this;
    }


}