package kfdykme.funk.util;
import android.app.Activity;
import java.util.*;
import android.content.pm.*;
import android.support.v4.content.*;
import android.support.v4.app.*;

public class PermissionUtil
{
	public static void initPermission(Activity activity,String permissions[]){
		ArrayList toApplyList = new ArrayList();
		
		for (String perm:permissions){
			if(PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(activity,perm)){
				toApplyList.add(perm);
			}
		}
		
		String tmpList[] = new String[toApplyList.size()];
		
		if(!toApplyList.isEmpty()){
			ActivityCompat.requestPermissions(activity,(String[]) toApplyList.toArray(tmpList),123);
		}
	}
}
