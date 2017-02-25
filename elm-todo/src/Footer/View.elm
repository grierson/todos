module Footer.View exposing (..)

import Msg as Main exposing (..)
import Html exposing (Html, footer, p, a, text)
import Html.Attributes exposing (class, href)


view : Html Main.Msg
view =
    footer [ class "info" ]
        [ p [] [ text "Double-click to edit a todo" ]
        , p []
            [ text "Written by "
            , a [ href "https://github.com/grierson" ] [ text "Kyle Grierson" ]
            ]
        , p []
            [ text "Part of "
            , a [ href "http://todomvc.com" ] [ text "TodoMVC" ]
            ]
        ]
