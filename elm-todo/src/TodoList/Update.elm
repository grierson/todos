module TodoList.Update exposing (..)

import Msg as Main exposing (Msg)
import TodoList.Model exposing (Model)
import TodoList.Msg as TodoList exposing (Msg)
import Todo.Model exposing (newTodo)
import Todo.Msg as Todo exposing (Msg)


update : Main.Msg -> Model -> Model
update msgFor model =
    case msgFor of
        Main.MsgForTodoList msg ->
            updateTodoList msg model

        Main.MsgForTodo uid msg ->
            updateTodo uid msg model

        _ ->
            model


updateTodoList : TodoList.Msg -> Model -> Model
updateTodoList msg model =
    case msg of
        TodoList.Add text ->
            let
                uid =
                    case List.head (List.reverse model) of
                        Just todo ->
                            todo.uid + 1

                        Nothing ->
                            1
            in
                if String.isEmpty text then
                    model
                else
                    model ++ [ Todo.Model.newTodo uid text ]

        TodoList.Remove uid ->
            let
                remove todo =
                    todo.uid == uid
            in
                List.filter (not << remove) model

        TodoList.CheckAll allCompleted ->
            let
                check todo =
                    { todo | completed = allCompleted }
            in
                List.map check model

        TodoList.ClearCompleted ->
            List.filter (not << .completed) model


updateTodo : Int -> Todo.Msg -> Model -> Model
updateTodo uid msg model =
    case msg of
        Todo.Check ->
            let
                check todo =
                    if todo.uid == uid then
                        { todo | completed = (not todo.completed) }
                    else
                        todo
            in
                List.map check model

        Todo.Edit toggle ->
            let
                edit todo =
                    if todo.uid == uid then
                        { todo | editing = toggle }
                    else
                        todo
            in
                List.map edit model

        Todo.Rename text ->
            let
                rename todo =
                    if todo.uid == uid then
                        { todo | name = text }
                    else
                        todo
            in
                List.map rename model
