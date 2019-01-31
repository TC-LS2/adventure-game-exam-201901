import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

import { setGame } from "../ducks/game/actions/setGame"
import makeApp from "../makeApp"
import fooGame from "./fooGame"
import { POST_COMMAND } from "../ducks/game/actions/postCommand"

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

test("allows to write new combination and send it", () => {
  wrapper
    .find("input")
    .find({ name: "combinations" })
    .simulate("change", { target: { value: "chicken,stick" } })
  wrapper
    .find({ command: "combine" })
    .find("[onClick]")
    .first()
    .simulate("click")

  const command = spyMiddleware.getAction(POST_COMMAND)

  expect(command).toMatchObject({
    username: "foo-player",
    command: "combine",
    arguments: ["chicken", "stick"],
  })
})
