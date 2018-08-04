package com.ex

case class person (
  name: String,
  age: BigInt) {
  override def toString: String =
    return "person[" + name + ", " + age + "]"

}
