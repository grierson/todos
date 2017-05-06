module Todo.Model exposing (..)


type alias Model =
    { uid : Int
    , name : String
    , completed : Bool
    , editing : Bool
    }


model : Model
model =
    { uid = 1
    , name = ""
    , completed = False
    , editing = False
    }


newTodo : Int -> String -> Model
newTodo uid name =
    Model uid name False False
