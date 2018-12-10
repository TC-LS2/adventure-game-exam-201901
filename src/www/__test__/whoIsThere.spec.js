import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

import { setGame } from "../ducks/game/actions/setGame"
import makeApp from "../makeApp"
import fooGame from "./fooGame"

let wrapper
let dispatch
let getState
let spyMiddleware
beforeEach(() => {
  spyMiddleware = makeSpyMiddleware()
  const App = makeApp(undefined, spyMiddleware)
  wrapper = mount(<App />)
  ;({ dispatch, getState } = wrapper
    .find("Provider[store]")
    .first()
    .props().store)

  dispatch(setGame(fooGame))
  dispatch(push("/game"))
  wrapper.update()
})

test("it shows players in the room", () => {
  expect(wrapper.text()).toMatch("foo-whoelse")
})
