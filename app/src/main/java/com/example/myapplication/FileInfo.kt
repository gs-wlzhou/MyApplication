package com.example.myapplication

class FileInfo(val type: Int, val name: String, val path: String) {
    override fun toString(): String {
        return "FileInfo(type=$type, name='$name', path='$path')"
    }
}