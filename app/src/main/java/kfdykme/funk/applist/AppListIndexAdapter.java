package kfdykme.funk.applist;

import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import kfdykme.funk.R;
import android.view.View.*;

public class AppListIndexAdapter extends RecyclerView.Adapter
{
	
	private Context context;
	
	private LayoutInflater inflater;
	
	interface OnItemClickListener{
		void onClick(int pos);
	}
	
	private OnItemClickListener mOnItemClickListener;
	
	public void setOnClickListener(OnItemClickListener l){
		mOnItemClickListener = l;
	}

	AppListIndexAdapter(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		View v = inflater.inflate(R.layout.item_view_applist_index_min,p1,false);
		
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder p1, final int p2)
	{
		char a = (char) ('A' +p2);
		((ViewHolder)p1).tv.setText(a+"");
		((ViewHolder)p1).itemView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(mOnItemClickListener!=null)
						mOnItemClickListener.onClick(p2);
				}
			});
	}

	@Override
	public int getItemCount()
	{
		return 26;
	}
	
	
	public class ViewHolder extends RecyclerView.ViewHolder{
		
		TextView tv;
		ViewHolder(View view){
			super(view);
			tv = (TextView) view.findViewById(R.id.tv);
		}
	}
}
