package kfdykme.funk.applist.bean

import android.content.pm.ResolveInfo
import android.os.Parcel
import android.os.Parcelable

class Model(){
    var infos: MutableList<ResolveInfo>? = null
    var index: List<String>? = null
    var packageNames:MutableList<String>? = null

    constructor(infos:MutableList<ResolveInfo>,index:List<String>) : this() {
        this.infos = infos
        this.index = index
        packageNames = mutableListOf()
//        for(info in infos){
//            packageNames!!.add(info.resolvePackageName)
//        }
    }


}
