module TodoList.View.TodoList exposing (..)

import Html exposing (Html, ul, li, section, input, label, text)
import Html.Attributes exposing (class, style, type_, name, checked, for)
import Html.Events exposing (onClick)
import Msg as Main exposing (Msg)
import TodoList.Msg as TodoList exposing (Msg)
import TodoList.Model as TodoList exposing (Model)
import TodoList.View.TodoListItem as TodoListItemView exposing (view)
import Visibility.Model as Visibility exposing (Model)
import Visibility.Msg as Visibility exposing (..)


view : Visibility.Model -> TodoList.Model -> Html Main.Msg
view visibility model =
    let
        emptylist =
            if List.isEmpty model then
                "hidden"
            else
                "visible"

        allCompleted =
            List.all .completed model

        todos =
            case visibility of
                Visibility.Completed ->
                    List.filter .completed model

                Visibility.Active ->
                    List.filter (not << .completed) model

                _ ->
                    model
    in
        section
            [ class "main"
            , style [ ( "visibility", emptylist ) ]
            ]
            [ input
                [ class "toggle-all"
                , type_ "checkbox"
                , name "toggle"
                , checked allCompleted
                , onClick (Main.MsgForTodoList <| (TodoList.CheckAll (not allCompleted)))
                ]
                []
            , label
                [ for "toggle-all" ]
                [ text "Mark all as complete" ]
            , ul
                [ class "todo-list" ]
                (List.map TodoListItemView.view todos)
            ]
