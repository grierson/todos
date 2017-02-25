module AddTodo.View exposing (..)

import Html exposing (Html, Attribute, div, input, button, text, header, h1)
import Html.Events exposing (on, onSubmit, onInput, onClick, keyCode, targetValue)
import Html.Attributes exposing (type_, value, class, autofocus, placeholder, name)
import Json.Decode as Json
import Msg as Main exposing (Msg)
import AddTodo.Msg as AddTodo exposing (Msg)
import AddTodo.Model as AddTodo exposing (Model)
import TodoList.Msg as TodoList exposing (Msg)


onEnter : msg -> Attribute msg
onEnter msg =
    let
        isEnter code =
            if code == 13 then
                Json.succeed msg
            else
                Json.fail "not ENTER"
    in
        on "keydown" (Json.andThen isEnter keyCode)


view : Model -> Html Main.Msg
view model =
    header
        [ class "header" ]
        [ h1 [] [ text "todos" ]
        , input
            [ class "new-todo"
            , placeholder "What needs to be done?"
            , autofocus True
            , value model
            , name "newTodo"
            , on "input" (Json.map (Main.MsgForAddTodo << AddTodo.Input) targetValue)
            , onEnter (Main.MsgForTodoList <| (TodoList.Add model))
            ]
            []
        ]
