package org.brandt.shapes

case class Point(x: Double, y: Double)

abstract class Shape(){
    def draw(f: String => Unit):Unit = f(s"draw: ${this.toString}")
}

case class Circle(center: Point, radius: Double) extends Shape

