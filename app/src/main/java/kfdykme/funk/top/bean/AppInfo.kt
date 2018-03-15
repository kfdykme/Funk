package kfdykme.funk.top.bean

import android.content.pm.ResolveInfo

/**
 * Created by wimkf on 2018/3/9.
 */
class AppInfo(var packageName:String){
    companion object {
        val TAG = "AppInfo"
    }


    var resolveInfo:ResolveInfo? = null


    var x:Int = 0
    var y:Int = 0
    var width:Int = 12
    var height:Int = 6
    var id:Long = 0L

    override fun toString(): String {
        return "{x:$x ," +
                "y:$y," +
                "width : $width," +
                "height : $height," +
                "id: $id }"
    }
}