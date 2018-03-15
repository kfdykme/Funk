package kfdykme.funk.top

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import com.google.gson.Gson
import java.util.ArrayList

import kfdykme.funk.top.bean.AppInfo
import kfdykme.funk.top.bean.TopInfo
import kfdykme.funk.util.FileUtils

/**
 * Created by wimkf on 2018/3/10.
 */

 class TopModel {

    companion object {
        var INSTANCE:TopModel = TopModel()
    }

    var topInfo = TopInfo()


    var context:Context? = null

    interface OnDeleteListener{
        fun onDelete()
    }

    interface onAddAppListener{
        fun onAdd()
    }

    var onDeleteListeners : OnDeleteListener?  = null

    var onAddListener : onAddAppListener? = null

    fun save(){
        FileUtils.createFile("top","apps.fk",Gson().toJson(topInfo))
    }

    fun loadFromLocal(){

        try {
           val json = FileUtils.readFile("top","apps","fk")
            topInfo = Gson().fromJson(
                    json,
                    TopInfo::class.java
            )!!
            for(app in topInfo.appinfos!!){
                var i = Intent(Intent.ACTION_MAIN,null)
                i.addCategory(Intent.CATEGORY_LAUNCHER)
                i.`package` = app.packageName

                app.resolveInfo =  context!!.packageManager
                                .queryIntentActivities(i,0)[0]

            }

        } catch (e:Exception){
            e.printStackTrace();
        }

    }

    fun addInfo(app:AppInfo){
        topInfo.appinfos!!.add(app)
        save()
        onAddListener?.onAdd()
    }

    fun findAppInfoById(id:Long):AppInfo?{
        for(app in topInfo.appinfos!!){
            if(app.id == id)
                return app
        }
        return null
    }

    fun deleteById(id:Long){
        topInfo.appinfos!!.remove(findAppInfoById(id))
        save()
        onDeleteListeners?.onDelete()
    }


}
