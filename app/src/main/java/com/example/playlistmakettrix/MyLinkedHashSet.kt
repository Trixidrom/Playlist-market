package com.example.playlistmakettrix

class MyLinkedHashSet<T>(private val maxSize: Int) {
    private val list: MutableList<T> = mutableListOf()

    fun getList(): List<T>{
        return list.reversed()
    }
    fun add(item: T){
        if(list.contains(item)){
            list.remove(item)
        }
        list.add(item)
        if(list.size>maxSize) list.removeAt(0)
    }

    fun clear(){
        list.clear()
    }

    override fun toString(): String {
        return list.toString()
    }
}