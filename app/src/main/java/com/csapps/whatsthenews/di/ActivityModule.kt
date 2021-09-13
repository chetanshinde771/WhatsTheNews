package com.csapps.whatsthenews.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.csapps.whatsthenews.Config.API_KEY
import com.csapps.whatsthenews.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Named

/*dependencies which are required only during activity lifecycle*/
@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {

    /* currently country code used is for India(in), refer news api for other country codes
    *
    * here we have used @Named annotation to specify exactly which value we are expecting
    * e.g there are multiple api calls, and multiple map objects required for each call,
    * to tell Dagger which map object should be linked with which api request
    * we specify this annotation*/
    @ActivityRetainedScoped
    @Provides
    @Named("newsRequestMap")
    fun provideNewsApiRequestMap(): Map<String, String>{
        var map = HashMap<String, String>()
        map["country"] = "in"
        map["apiKey"] = API_KEY
        return map
    }

    @ActivityRetainedScoped
    @Provides
    fun provideRecyclerViewVerticalDivider(
        @ApplicationContext context: Context
    ): DividerItemDecoration {
        var decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        decoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.transparent_view)!!)
        return decoration
    }

    /*@Provides
    @Named("checkNetwork")
    fun provideNetworkAvailability(
        @ApplicationContext context: Context): Boolean{

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }*/
}