module Visibility.Update exposing (..)

import Msg as Main exposing (Msg)
import Visibility.Model exposing (Model)
import Visibility.Msg as Visibility exposing (Msg)


update : Main.Msg -> Model -> Model
update msgFor model =
    case msgFor of
        Main.MsgForVisibility msg ->
            updateVisibility msg model

        _ ->
            model


updateVisibility : Visibility.Msg -> Model -> Model
updateVisibility msg model =
    msg
