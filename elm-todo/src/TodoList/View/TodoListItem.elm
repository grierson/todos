module TodoList.View.TodoListItem exposing (..)

import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onClick, onDoubleClick, onBlur, onInput, keyCode, on)
import Json.Decode as Json
import Msg as Main exposing (Msg)
import TodoList.Msg as TodoList exposing (Msg)
import Todo.Model exposing (Model)
import Todo.Msg as Todo exposing (Msg)


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
    li [ classList [ ( "completed", model.completed ), ( "editing", model.editing ) ] ]
        [ div [ class "view" ]
            [ input
                [ class "toggle"
                , type_ "checkbox"
                , checked model.completed
                , onClick (Main.MsgForTodo model.uid Todo.Check)
                ]
                []
            , label
                [ onDoubleClick (Main.MsgForTodo model.uid <| Todo.Edit True) ]
                [ text model.name ]
            , button
                [ class "destroy"
                , onClick (Main.MsgForTodoList (TodoList.Remove model.uid))
                ]
                []
            ]
        , input
            [ class "edit"
            , value model.name
            , name "title"
            , id ("todo-" ++ toString model.uid)
            , onBlur (Main.MsgForTodo model.uid <| Todo.Edit False)
            , onEnter (Main.MsgForTodo model.uid <| Todo.Edit False)
            , onInput (\text -> Main.MsgForTodo model.uid <| Todo.Rename text)
            ]
            []
        ]
