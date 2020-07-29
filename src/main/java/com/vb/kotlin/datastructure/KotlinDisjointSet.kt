package com.vb.kotlin.datastructure

class KotlinDisjointSet {
    private val size: Int
    private var parent: IntArray

    constructor(size: Int) {
        this.size = size
        parent = IntArray(size)
        for (i in 0 until size) {
            parent[i] = i
        }
    }

    private fun getRoot(u: Int): Int {
        return if (parent[u] == u) {
            u
        } else {
            parent[u] = getRoot(parent[u])
            return parent[u]
        }
    }

    fun merge(u: Int, v: Int) {
        parent[getRoot(u)] = getRoot(v)
    }

    fun inSameSet(u: Int, v: Int): Boolean {
        return getRoot(u) == getRoot(v)
    }
}