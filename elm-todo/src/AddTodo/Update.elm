module AddTodo.Update exposing (update)

import Msg as Main exposing (Msg)
import AddTodo.Model exposing (Model)
import AddTodo.Msg as AddTodo exposing (Msg)
import TodoList.Msg as TodoList exposing (Msg)


update : Main.Msg -> Model -> Model
update msgFor model =
    case msgFor of
        Main.MsgForAddTodo msg ->
            updateAddTodo msg model

        Main.MsgForTodoList msg ->
            updateTodoList msg model

        _ ->
            model


updateAddTodo : AddTodo.Msg -> Model -> Model
updateAddTodo msg model =
    case msg of
        AddTodo.Input text ->
            text


updateTodoList : TodoList.Msg -> Model -> Model
updateTodoList msg model =
    case msg of
        TodoList.Add _ ->
            ""

        _ ->
            model
