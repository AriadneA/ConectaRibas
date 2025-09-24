package com.example.conectaribas.data.util

import androidx.room.TypeConverter
import java.util.Date

/**
 * Conversores para o Room Database
 * Permitem armazenar tipos de dados complexos como Date no banco SQLite
 */
class Converters {
    
    /**
     * Converte um timestamp Long para Date
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    /**
     * Converte um Date para timestamp Long
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
