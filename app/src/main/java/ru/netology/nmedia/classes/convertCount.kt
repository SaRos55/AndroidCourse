package ru.netology.nmedia.classes

    fun convertCount(count: Int): String {
        return when {
            count >= 1_000_000 -> {
                val m = count / 1_000_000
                val k = (count - m * 1_000_000) / 100_000
                "$m" + (if (k > 0) ".$k" else "") + "M"
            }
            count >= 10_000 -> {
                val k = count / 1_000
                "$k" + "K"
            }
            count >= 1_000 -> {
                val k = count / 1_000
                val n = (count - k * 1_000) / 100
                "$k" + (if (n > 0) ".$n" else "") + "K"
            }
            else -> count.toString()
        }
    }