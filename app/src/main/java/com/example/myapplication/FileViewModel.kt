package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class FileViewModel: ViewModel() {

    private var _fileInfoList = MutableLiveData<List<FileInfo>>()
    val fileInfoList: LiveData<List<FileInfo>> = _fileInfoList

    fun loadFileInfoList(curPath: String) {
        val result = ArrayList<FileInfo>()
        val directoryList = ArrayList<FileInfo>()
        val picList = ArrayList<FileInfo>()
        val curFile = File(curPath)
        if (curFile.isDirectory) {
            val fileList = curFile.listFiles()
            Log.d(TAG, "fileList size = ${fileList.size}")
            for (file in fileList) {
                if (file.isHidden) {
                    continue
                }
                if (file.isDirectory && file.canRead()) {
                    directoryList.add(FileInfo(1, file.name, file.path))
                } else if (file.isFile) {
                    val index = file.name.lastIndexOf(".")
                    val formatList = listOf<String>("png", "jpg", "jpeg")
                    if (formatList.contains(file.name.substring(index + 1))) {
                        picList.add(FileInfo(0, file.name, file.path))
                    }
                }
            }
        }
        result.addAll(directoryList)
        result.addAll(picList)
        _fileInfoList.value = result
    }

    companion object {
        private const val TAG = "FileViewModel"
    }
}