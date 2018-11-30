package helloworld.com.taquangtu132gmail.taquangtu.ai.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import helloworld.com.taquangtu132gmail.taquangtu.ai.R;

public class ChessAdapter extends BaseAdapter {
    private Context context;
    private int layoutResourceId;
    private List<String> chessColorArray;

    public ChessAdapter(Context context, int layoutResourceId, List<String> chessColorArray) {
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.chessColorArray = chessColorArray;
    }
    @Override
    public int getCount() {
        return chessColorArray.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layoutResourceId, null);
            holder.imvChess = view.findViewById(R.id.imvChess);
            view.setTag(holder);
        }
        else
            {
                holder = (ViewHolder) view.getTag();
            }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((MainActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (displayMetrics.widthPixels) / (((MainActivity) context).getColumn()) - 16;
        holder.imvChess.getLayoutParams().height = width;
        if (chessColorArray.get(i).equals("B"))   //black
        {
            holder.imvChess.setImageResource(R.drawable.black);
        } else if (chessColorArray.get(i).equals("W"))   //white
        {
            holder.imvChess.setImageResource(R.drawable.white);
        }
        return view;
    }
    class ViewHolder
    {
        ImageView imvChess;
    }
}
