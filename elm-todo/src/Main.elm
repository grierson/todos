module Main exposing (..)

import Html exposing (Html)
import Msg exposing (Msg)
import Model exposing (Model)
import Update
import View


main : Program Never Model Msg
main =
    Html.beginnerProgram
        { model = Model.model
        , update = Update.update
        , view = View.view
        }
