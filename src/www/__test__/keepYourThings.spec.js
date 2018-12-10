import { push } from "connected-react-router"
import { mount } from "enzyme"
import React from "react"
import makeSpyMiddleware from "spy-middleware"

import { setGame } from "../ducks/game/actions/setGame"
import { POST_COMMAND } from "../ducks/game/actions/postCommand"
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

  dispatch(setGame({ ...fooGame, bag: { items: [{ name: "foo-bag-item" }] } }))
  dispatch(push("/game"))
  wrapper.update()
})

test("it shows bag contents", () => {
  expect(wrapper.text()).toMatch("foo-bag-item")
})

test("it offers equip the item", () => {
  wrapper
    .find({ command: "equip", arguments: ["foo-bag-item"] })
    .find("[onClick]")
    .first()
    .simulate("click")

  const command = spyMiddleware.getAction(POST_COMMAND)

  expect(command).toMatchObject({
    username: "foo-player",
    command: "equip",
    arguments: ["foo-bag-item"],
  })
})
