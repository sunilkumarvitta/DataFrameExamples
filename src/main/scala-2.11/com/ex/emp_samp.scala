package com.ex

case class emp_samp (
                      empid: Int,
  empname: String,
  sname: String,
  sid: Int,
  bonus: Long,
  increment: Long,
  spl_alw: Long,
  location: String,
  dept: String,
  perf: Long)
{
  override def toString: String = {
    return "emp_samp[" + empname + ", " + sname + ", " + sid + ", " + bonus + ", " + increment + ", " + spl_alw +", " + location + ", " + dept + ", " + perf + "]"
  }
}
