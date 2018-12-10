import kebabCase from "kebab-case"

import combineAndCreate from "./combine-and-create"
import food from "./food"
import helloWorld from "./hello-world"
import keepYourThings from "./keep-your-things"
import keysAndDoors from "./keys-and-doors"
import monsters from "./monsters"
import moveAround from "./move-around"
import roomRefresh from "./room-refresh"
import weaponsAndShields from "./weapons-and-shields"
import whoIsThere from "./who-is-there"

const entries = {
  combineAndCreate,
  food,
  helloWorld,
  keepYourThings,
  keysAndDoors,
  monsters,
  moveAround,
  roomRefresh,
  weaponsAndShields,
  whoIsThere,
}

const blog = Object.keys(entries)
  .map(key => ({
    id: kebabCase(key),
    ...entries[key],
  }))
  .sort((a, b) => b.date - a.date)

export default blog
