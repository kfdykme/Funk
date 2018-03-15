package kfdykme.funk.applist;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import java.io.File;
import java.util.*;
import android.content.pm.*;
import android.widget.AdapterView.*;

import kfdykme.funk.R;

public class AppListAdapter extends RecyclerView.Adapter
{


	LayoutInflater inflater;

	Context context;


	List<Object> data = new ArrayList();

	List<Integer> ints = new  ArrayList();

	private final static int INT_OFFSET = 3;

	interface OnItemClickListener{
		void onLongClick(View v, int pos);
		void onClick(int pos);
	}

	OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener l){
		mOnItemClickListener = l;
	}

	AppListAdapter(Context context,List<Object> list){
		data = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
		if(data!=null)
		for(int i =0 ; i < data.size();i++){
			Object o = data.get(i);
			if(o instanceof String){
				if(i+INT_OFFSET<data.size())
				ints.add(i+INT_OFFSET);
				else
					ints.add(i);
			}
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		View v;
		switch(p2){
			case 0:
				v =inflater.inflate(R.layout.item_view_applist_app,p1,false);
				return new AppViewHolder(v);
			case 1:
				v= inflater.inflate(R.layout.item_view_applist_index,p1,false);
				return new IndexViewHolder(v);

		}
		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder p1, final int p2)
	{
		switch(p1.getItemViewType()){
			case 1:
				IndexViewHolder ivh = (IndexViewHolder) p1;
				ivh.tv.setText((String)(data.get(p2)));
				break;

			case 0:
				AppViewHolder avh = (AppViewHolder) p1;
				avh.tv.setText(((ResolveInfo)(data.get(p2))).loadLabel(context.getPackageManager()));
				avh.iv.setImageDrawable(((ResolveInfo)(data.get(p2))).loadIcon(context.getPackageManager()));
				avh.itemView.setOnClickListener(new View.OnClickListener(){

						@Override
						public void onClick(View p1)
						{
							if(mOnItemClickListener!=null)
								mOnItemClickListener.onClick(p2);
						}
					});


				avh.itemView.setOnLongClickListener(new View.OnLongClickListener(){

						@Override
						public boolean onLongClick(View p1)
						{
							if(mOnItemClickListener!=null)
								mOnItemClickListener.onLongClick(p1,p2);
							return false;
						}
					});
				break;
		}
	}

	@Override
	public int getItemCount()
	{
		return data.size();
	}

	@Override
	public int getItemViewType(int position)
	{
		return data.get(position) instanceof ResolveInfo ? 0:1;
	}


	public List<Integer> getSort(){
		return ints;
	}




	public class AppViewHolder extends RecyclerView.ViewHolder{

		TextView tv;

		ImageView iv;

		AppViewHolder(View view){
			super(view);
			tv = (TextView) view.findViewById(R.id.tv);
			iv = (ImageView) view.findViewById(R.id.iv);

		}
	}




	public class IndexViewHolder extends RecyclerView.ViewHolder{

		TextView tv;

		IndexViewHolder(View view){
			super(view);
			tv = (TextView) view.findViewById(R.id.tv);

		}


	}
}
