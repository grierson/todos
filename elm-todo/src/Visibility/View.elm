module Visibility.View exposing (..)

import Html exposing (Html, footer, ul, li, a, text, span, strong, button)
import Html.Attributes exposing (href, classList, class, hidden)
import Html.Events exposing (onClick)
import Msg as Main exposing (Msg)
import Visibility.Msg as Visibility exposing (..)
import Visibility.Model as Visibility exposing (Model)
import TodoList.Model as TodoList exposing (Model)
import TodoList.Msg as TodoList exposing (Msg)


viewVisibility : Visibility.Model -> Visibility.Model -> Html Main.Msg
viewVisibility current visibility =
    li
        [ onClick (Main.MsgForVisibility <| visibility) ]
        [ a [ href "#", classList [ ( "selected", visibility == current ) ] ]
            [ text (toString visibility) ]
        ]


viewLeftTodos : TodoList.Model -> Html Main.Msg
viewLeftTodos todos =
    let
        todosCompleted =
            List.length (List.filter .completed todos)

        todosLeft =
            List.length todos - todosCompleted

        item_ =
            if todosLeft == 1 then
                " item"
            else
                " items"
    in
        span
            [ class "todo-count" ]
            [ strong [] [ text (toString todosLeft) ]
            , text (item_ ++ " left")
            ]


viewClearCompleted : TodoList.Model -> Html Main.Msg
viewClearCompleted todos =
    let
        completed =
            List.filter .completed todos

        completedCount =
            List.length completed

        hide =
            completedCount == 0
    in
        button
            [ class "clear-completed"
            , hidden hide
            , onClick (Main.MsgForTodoList <| TodoList.ClearCompleted)
            ]
            [ text ("Clear completed (" ++ toString completedCount ++ ")")
            ]


view : TodoList.Model -> Visibility.Model -> Html Main.Msg
view todos model =
    let
        hide =
            (List.length todos == 0)
    in
        footer
            [ class "footer"
            , hidden hide
            ]
            [ viewLeftTodos todos
            , ul [ class "filters" ] (List.map (viewVisibility model) [ All, Completed, Active ])
            , viewClearCompleted todos
            ]
