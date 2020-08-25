package insetSort

object InsertSort {

  def insertSort(xs:List[Int]):List[Int]={
    xs match {
      case List() => List()
      case headOne::tail =>  insert(headOne,insertSort(tail))
    }
  }
  val lessThan:(Int,Int) => Boolean = _ < _

  def insert(newOne:Int,orderedList:List[Int]): List[Int] ={
    orderedList match {
      case List() => newOne::Nil
      case headOne::tail =>
        if(lessThan(newOne,headOne)) {
          newOne::headOne::tail
        }else{
          headOne::insert(newOne,tail)
        }
    }
  }

  def main(args: Array[String]): Unit = {
    println(insertSort(List(1,2,4,3)))
    println(insertSort(List(3,-2,4,3)))
  }
}
