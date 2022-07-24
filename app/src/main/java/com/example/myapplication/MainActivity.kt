package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.Method
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var fileViewModel: FileViewModel
    private lateinit var gridView: GridView
    private val stack = Stack<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fileViewModel = ViewModelProvider(this).get(FileViewModel::class.java)
        fileViewModel.loadFileInfoList(ROOT_PATH)
        stack.push(ROOT_PATH)
        gridView = findViewById(R.id.file_list)
        val gridAdapter = FileAdapter(this, ArrayList<FileInfo>())
        gridView.adapter = gridAdapter
        fileViewModel.fileInfoList.observe(this) {
            gridAdapter.updateInfoList(it)
        }
        gridView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val fileInfo = gridAdapter.dataList[position]
            if (fileInfo.type == 1) {
                stack.push(fileInfo.path)
                fileViewModel.loadFileInfoList(fileInfo.path)
            } else if (fileInfo.type == 0) {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        val log = StringBuilder()
        val inPath: String = SDCardUtils.getInnerSDCardPath()
        log.append("内置SD卡路径：$inPath\r\n")
        val extPaths: List<String> = SDCardUtils.getExtSDCardPath()
        for (path in extPaths) {
            log.append("外置SD卡路径：$path\r\n")
        }
        Log.d(TAG, log.toString())
        SDCardUtils.getStoragePath(this, "SD")
    }

    override fun onBackPressed() {
        if (stack.size > 1) {
            stack.pop()
            fileViewModel.loadFileInfoList(stack.peek())
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val TAG = "wlzhou"
        private var ROOT_PATH = Environment.getExternalStorageDirectory().path
    }
}