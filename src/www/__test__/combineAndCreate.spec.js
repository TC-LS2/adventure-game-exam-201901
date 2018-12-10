import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

import { setGame } from "../ducks/game/actions/setGame"
import makeApp from "../makeApp"
import fooGame from "./fooGame"

const combineGame = {
  ...fooGame,
}

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

  dispatch(setGame(combineGame))
  dispatch(push("/game"))
  wrapper.update()
})

test("solveme", () => {
  // NOT SOLVEME
  expect(wrapper.text()).toMatch("foo")
})
