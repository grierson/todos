module View exposing (..)

import Model exposing (Model)
import Msg exposing (Msg)
import AddTodo.View as AddTodo
import TodoList.View.TodoList as TodoList
import Visibility.View as Visibility
import Footer.View as Footer
import Html exposing (Html, div, section, text, br)
import Html.Attributes exposing (class, style, id)


view : Model -> Html Msg
view model =
    div
        [ class "todomvc-wrapper" ]
        [ section
            [ class "todoapp" ]
            [ AddTodo.view model.addtodo
            , TodoList.view model.visibility model.todolist
            , Visibility.view model.todolist model.visibility
            ]
        , Footer.view
        ]
