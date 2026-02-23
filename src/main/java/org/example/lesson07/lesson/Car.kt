package org.example.lesson07.lesson

enum class Color {
    RED,
    GREEN,
    BLACK,
    BLUE,
}

data class Car(val brand: String, val color: Color, val age: Int)
