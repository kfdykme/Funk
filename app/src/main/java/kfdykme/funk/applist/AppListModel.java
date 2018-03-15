package kfdykme.funk.applist;
import android.content.*;
import android.content.pm.*;
import java.util.*;
import java.text.*;

import kfdykme.funk.applist.bean.Model;
import kfdykme.funk.util.ChineseCharToEnUtil;
import kfdykme.funk.util.FileUtils;

import android.util.*;
import java.io.*;

import com.google.gson.Gson;

public enum AppListModel
{
	INSTANCE;

    public static String TAG = "AppListModel";

	private Context mContext;

	public List<ResolveInfo> resolveInfos = new ArrayList();

	public List<String> indexs = new ArrayList();

	public Model model = new Model();

	private AppListContract.Presenter presenter;

	public void setPresenter(AppListContract.Presenter p){
		presenter = p;
	}

	/*
	 * load application infos by context and set @model
	 * @param context
	 */
	public void load(Context context){


		mContext = context;
		resolveInfos = getResolveInfos();

		for(int i =0;i < 26;i++){
			char a = (char) ('A' +i);
			indexs.add(a+"");
		}
        model = new Model(resolveInfos,indexs);




	}

	/*
	 * this function must run after load(Context context)
	 * return if data has changes
	 */
	public boolean reflash(){

	    //此处应该用rxjava修改
		if(mContext==null)return false;

		List<ResolveInfo> tmp = getResolveInfos();
		if(!resolveInfos.equals(tmp)){
			resolveInfos = tmp;
			ResolveInfo[] a = null;

			model = new Model(resolveInfos, indexs);

			return true;
		}

		return false;


	}

	/*
	 *
	 */

	private List<ResolveInfo> getResolveInfos(){
		List<ResolveInfo> infos ;

		Intent intent = new Intent(Intent.ACTION_MAIN,null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		infos = mContext.getPackageManager().queryIntentActivities(intent,0);
		return sort(infos);
	}



	private List<ResolveInfo> sort(List<ResolveInfo> list){
        List<ResolveInfo> nTs = new ArrayList<>();

		Comparator cmp = Collator.getInstance(Locale.CHINA);
		while(list.size() !=0){
			int lep =0;
			for(int i = 0 ;i< list.size();i++){
				String t1 = (String) list.get(lep).loadLabel(mContext.getPackageManager());
				String t2 = (String) list.get(i).loadLabel(mContext.getPackageManager());
				t1 = ChineseCharToEnUtil.getFirstSpell(t1.substring(0,1));
				t2 = ChineseCharToEnUtil.getFirstSpell(t2.substring(0,1));

				if(cmp.compare(t1,t2)<0){
					lep = i;
				}
			}
			nTs.add(0,list.get(lep));

			list.remove(lep);
		}
        return nTs;

	}

	public List<Object> combine(){
	    return combine(model);
    }

	private List<Object> combine(Model model){
	    return combine(model.getInfos(),model.getIndex());
    }

	private List<Object> combine(List<ResolveInfo> infos,List<String> index){
	    List<Object> objects = new ArrayList();
		Comparator cmp = Collator.getInstance(Locale.CHINA);
		int pos =0;

		while(infos.size()!=0 && index.size()!=0){
			String l = (String) infos.get(0).loadLabel(mContext.getPackageManager());
			l = ChineseCharToEnUtil.getFirstSpell(l);
			//l = l.toUpperCase();
			String r = index.get(0);
			if(cmp.compare(l,r)<0)
			{
				objects.add(infos.get(0));
				infos.remove(0);
			} else{
				objects.add(r);
				index.remove(0);
			}

		}

		if(infos.size()!=0){
			objects.addAll(infos);
		} else{
			objects.addAll(index);
		}


		return objects;
	}



}
