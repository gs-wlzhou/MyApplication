package com.example.myapplication

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap


class FileAdapter(var context: Context?, var dataList: List<FileInfo>): BaseAdapter() {
    private val DIRECTORY = context!!.getDrawable(R.drawable.folder)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder:ViewHolder;
        val layout:View
        if (convertView == null) {
            holder = ViewHolder()
            layout = View.inflate(context, R.layout.list_item, null)
            holder.iconInfo = layout.findViewById(R.id.icon_info)
            holder.textInfo = layout.findViewById(R.id.text_info)
            layout.tag = holder
        } else {
            layout = convertView
            holder = convertView.tag as ViewHolder
        }
        val fileInfo = dataList[position]
        holder.textInfo!!.text = fileInfo.name
        if (fileInfo.type == 1) {
            holder.iconInfo!!.setImageBitmap(context!!.getDrawable(R.drawable.folder)!!.toBitmap())
        } else if (fileInfo.type == 0) {
            val bm = BitmapFactory.decodeFile(fileInfo.path)
            holder.iconInfo!!.setImageBitmap(bm)
        }
        return layout
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataList.size
    }

    fun updateInfoList(list: List<FileInfo>) {
        dataList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder{
        var iconInfo: ImageView? = null
        var textInfo: TextView? = null
    }
}