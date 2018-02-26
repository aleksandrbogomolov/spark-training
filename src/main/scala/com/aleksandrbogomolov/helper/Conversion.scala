package com.aleksandrbogomolov.helper

object Conversion {

  implicit def arrayToList[T](array: Array[T]) = array.toList
}
