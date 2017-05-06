module Msg exposing (..)

import AddTodo.Msg as AddTodo
import TodoList.Msg as TodoList
import Todo.Msg as Todo
import Visibility.Msg as Visibility


type Msg
    = MsgForAddTodo AddTodo.Msg
    | MsgForTodoList TodoList.Msg
    | MsgForTodo Int Todo.Msg
    | MsgForVisibility Visibility.Msg
