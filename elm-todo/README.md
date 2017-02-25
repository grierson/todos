# elm-todo


## Install

`elm-package install`


## Run

`elm-live src/Main.elm --output=elm.js --open`

## Update Rules

1. Update imports
  * ```import Msg as Main exposing (Msg)```
  * ```import Container.Msg exposing (..)```

2. Main/Msg.elm All main Msg's should have **MsgFor** prefix notation e.g. MsgForTodo, MsgForTodoList

3. Update type annotations
  * update - `Main.Msg -> Model -> Model`
  * Container update ('updateX') - `X.Msg -> Model -> Model`
  
4. update should have `_ -> model` branch

5. Update should import Cotainer Model and expose Model
  * `import Container.Model exposing (Model)`
