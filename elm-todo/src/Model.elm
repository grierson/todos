module Model exposing (..)

import AddTodo.Model as AddTodo
import TodoList.Model as TodoList
import Visibility.Model as Visibility


type alias Model =
    { uid : Int
    , addtodo : AddTodo.Model
    , todolist : TodoList.Model
    , visibility : Visibility.Model
    }


model : Model
model =
    { uid = 1
    , addtodo = AddTodo.model
    , todolist = TodoList.model
    , visibility = Visibility.model
    }
