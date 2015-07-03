package models

  case class Todo(todoTypeId: Option[Int] = None, content: String)

  case class TodoType(title: String)

