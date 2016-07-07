package com.android.diyadapter;

import java.util.List;
import java.util.Map;

import com.android.musicdao.MusicDao;
import com.example.musicplayer.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SongListAdpter extends BaseAdapter {
	private List<MusicDao> data;
	private LayoutInflater layoutInflater;
	private Context context;

	public SongListAdpter(Context context, List<MusicDao> data) {
		this.context = context;
		this.data = data;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public List<MusicDao> getData() {
		return data;
	}

	public void setData(List<MusicDao> data) {
		this.data = data;
	}

	public final class ViewHolders {
		public ImageView SongImage;
		public TextView SongName;
		public TextView SongSinger;
		public ImageButton SongOption;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolders vh = null;
		if (convertView == null) {
			vh = new ViewHolders();
			convertView = layoutInflater.inflate(R.layout.itemview, null);
			vh.SongImage = (ImageView) convertView.findViewById(R.id.ItemImg);
			vh.SongName = (TextView) convertView.findViewById(R.id.ItemName);
			vh.SongSinger = (TextView) convertView.findViewById(R.id.ItemSing);
			vh.SongOption = (ImageButton) convertView
					.findViewById(R.id.ItemOption);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolders) convertView.getTag();
		}

		// 绑定数据
		vh.SongName.setText((String) data.get(position).getMusicName());
		String other = data.get(position).getMusicOther();
		if (other.equals("<unknown>")) {
			other = "未知艺术家";
		}
		vh.SongSinger.setText(other);
		return convertView;
	}

}
