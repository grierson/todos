# Scrum

## Dispatch (Event / Message / Action) [:controller-key :event-key] to reconciler

``` clojure
;; (scrum/dispatch-sync! reconciler :controller :event)
(scrum/dispatch-sync! reconciler :counter :inc)
```

## Dispatch-Sync! function

### Call Controller Dispatch function

``` clojure
;;(let! [effects ((get controllers :controller) :event args (get @state :controller)))
;;(let! [effects (counter :inc args 0)])
(let! [effects {:state 1}])
```

### Bind value to key in State. (State key matches controller key)

``` clojure
;;(swap! state assoc cname effect)
;;(swap! state assoc :controller effect)
(swap! state assoc :counter {:state 1})
```
