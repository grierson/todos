module Update exposing (..)

import Msg as Main exposing (Msg)
import Model exposing (Model)
import AddTodo.Update as AddTodo
import TodoList.Update as TodoList
import Visibility.Update as Visibility


update : Msg -> Model -> Model
update msg model =
    { model
        | addtodo = AddTodo.update msg model.addtodo
        , todolist = TodoList.update msg model.todolist
        , visibility = Visibility.update msg model.visibility
    }
