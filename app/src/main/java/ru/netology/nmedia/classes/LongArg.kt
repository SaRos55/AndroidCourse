package ru.netology.nmedia.classes

import android.os.Bundle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object LongArg: ReadWriteProperty<Bundle, Long?> {

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Long?) {
        value?.let { thisRef.putLong(property.name, it) }
    }

    override fun getValue(thisRef: Bundle, property: KProperty<*>): Long {
        return thisRef.getLong(property.name)
    }
}