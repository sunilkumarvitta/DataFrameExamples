package com.ex

/**
  * Created by sidhartha.ray on 26-11-2017.
  */
case class Finance(id: Int,
                   hasDebt: Boolean,
                   hasFinancialDependents: Boolean,
                   hasStudentLoans: Boolean,
                   income: Int) {
  override def toString: String = "Finance[" + id + ", " + hasDebt + ", " + hasFinancialDependents + ", " + hasStudentLoans + ", " + income +"]"
}
