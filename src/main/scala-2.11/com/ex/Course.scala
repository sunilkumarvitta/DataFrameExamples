package com.ex

/**
  * Created by sidhartha.ray on 27-11-2017.
  */
case class Course(id: Int,
                  name: String) {
  override def toString: String = "Course[" + id + ", " + name + "]"
}
