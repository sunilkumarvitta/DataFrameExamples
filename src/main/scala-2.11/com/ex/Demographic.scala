package com.ex

/**
  * Created by sidhartha.ray on 26-11-2017.
  */
case class Demographic (id: Int,
                  age: Int,
                  codingBootcamp: Boolean,
                  country: String,
                  gender: String,
                  isEthnicMinority: Boolean,
                  servedInMilitary: Boolean,
                  courseId: Int) {
  override def toString: String = {
    return "Demographic[" + id + ", " + age + ", " + codingBootcamp + ", " + country + ", " + gender + ", " + isEthnicMinority +", " + servedInMilitary + ", " + courseId + "]"
  }
}