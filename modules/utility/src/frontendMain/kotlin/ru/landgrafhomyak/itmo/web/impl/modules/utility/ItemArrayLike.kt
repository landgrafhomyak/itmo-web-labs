package ru.landgrafhomyak.itmo.web.impl.modules.utility

import org.w3c.dom.ItemArrayLike

private class ItemArrayLikeIterator<out T>(val container: ItemArrayLike<T>) : Iterator<T> {
    private var pos = 0
    override fun hasNext(): Boolean {
        return this.pos < this.container.length
    }

    override fun next(): T {
        if (!this.hasNext()) throw IllegalStateException()
        return this.container.item(this.pos++).unsafeCast<T>()
    }
}
private value class ItemArrayLikeIterable<out T>(val container: ItemArrayLike<T>) : Iterable<T>, Sequence<T> {
    override fun iterator(): Iterator<T> = ItemArrayLikeIterator(this.container)
}

fun <T> ItemArrayLike<T>.asIterable(): Iterable<T> = ItemArrayLikeIterable(this)
fun <T> ItemArrayLike<T>.asSequence(): Sequence<T> = ItemArrayLikeIterable(this)
fun <T> ItemArrayLike<T>.iterator(): Iterator<T> = ItemArrayLikeIterator(this)