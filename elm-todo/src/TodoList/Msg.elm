module TodoList.Msg exposing (..)


type Msg
    = Add String
    | Remove Int
    | CheckAll Bool
    | ClearCompleted
