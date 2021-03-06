import org.junit.Test
import org.junit.Assert._
import io.github.mapogolions.cs305.buffalo.Vals._
import io.github.mapogolions.cs305.buffalo.Parse
import io.github.mapogolions.cs305.buffalo.Stack
import io.github.mapogolions.cs305.buffalo.{ Scope, Empty }
import io.github.mapogolions.cs305.buffalo.Main


class TestInterpreterBind {
  @Test def TestExecInterpreter: Unit = {
    assertEquals(
      Main.exec(
        Parse.commands(
          List(
            "push a",
            "push 9",
            "bind",
            "push a",
            "push :true:",
            "bind"
          )
        ),
        Stack(),
        Empty
      ),
      (
        Stack(UNIT :: UNIT :: Nil),
        Scope(Map("a" -> BOOL(true)), Empty)
      )
    )
    assertEquals(
      Main.exec(
        Parse.commands("push a  " :: "push a" :: ":false:" :: "bind" :: Nil),
        Stack(),
        Empty
      ),
      (
        Stack(UNIT :: ID("a") :: Nil),
        Scope(Map("a" -> BOOL(false)), Empty)
      )
    )
    assertEquals(
      Main.exec(
        Parse.commands("push a" :: "push a" :: Nil),
        Stack(),
        Empty
      ),
      Stack(ID("a") :: ID("a") :: Nil) -> Empty
    )
    assertEquals(
      Main.exec(
        Parse.commands(
          List(
            "push a",
            "push 15",
            "push a"
          )
        ),
        Stack(),
        Empty
      ),
      Stack(ID("a") :: INT(15) :: ID("a") :: Nil) -> Empty
    )
    assertEquals(
      Main.exec(
        Parse.commands(
          List(
            "push a",
            "push 7",
            "bind",
            "push b",
            "push a",
            "bind"
          )
        ),
        Stack(),
        Empty
      ),
      (
        Stack(UNIT :: UNIT :: Nil),
        Scope(Map("a" -> INT(7), "b" -> INT(7)), Empty)
      )
    )
    assertEquals(
      Main.exec(
        Parse.commands("push a" :: "push b" :: "bind" :: Nil),
        Stack(),
        Empty
      ),
     Stack(ERROR :: ID("b") :: ID("a") :: Nil) -> Empty
    )
    assertEquals(
      Main.exec(
        Parse.commands(
          List(
            "push b",
            "push 8",
            "bind",
            "push a", 
            "push b",
            "bind"
          ),
        ),
        Stack(),
        Empty
      ),
      (
        Stack(UNIT :: UNIT :: Nil),
        Scope(Map("a" -> INT(8), "b" -> INT(8)), Empty)
      )
    )
    assertEquals(
      Main.exec(
        Parse.commands(
          List(
            "push b",
            "push 8",
            "bind",
            "push 10", 
            "push b",
            "add"
          ),
        ),
        Stack(),
        Empty
      ),
      (
        Stack(INT(18) :: UNIT :: Nil),
        Scope(Map("b" -> INT(8)), Empty)
      )
    )
    assertEquals(
      Main.exec(
        Parse.commands("push a" :: "4.09" :: "bind" :: Nil),
        Stack(),
        Empty
      ),
      Stack(ERROR :: ERROR :: ID("a") :: Nil) -> Empty
    )
    assertEquals(
      Main.exec(
        Parse.commands(List("push flag", ":true:", "bind")),
        Stack(),
        Empty
      ),
      Stack(UNIT :: Nil) -> Scope(Map("flag" -> BOOL(true)), Empty)
    )
    assertEquals(
      Main.exec(
        Parse.commands(
          List(
            "push a", 
            "push 13", 
            "bind", 
            "push name1",
            "push 3",
            "bind",
            "push a",
            "push name1",
            "add"
          )
        ),
        Stack(),
        Empty
      ),
      (
        Stack(INT(16) :: UNIT :: UNIT :: Nil),
        Scope(Map("a" -> INT(13), "name1" -> INT(3)), Empty)
      )
    )
    assertEquals(
      Main.exec(
        Parse.commands(
          List(
            "push sum1",
            "push 7",
            "bind",
            "push sum2",
            "push 5",
            "bind"
          )
        ),
        Stack(),
        Empty
      ),
      (
        Stack(UNIT :: UNIT :: Nil),
        Scope(Map("sum1" -> INT(7), "sum2" -> INT(5)), Empty)
      )
    )
  }
}