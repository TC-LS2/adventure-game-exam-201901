import React from "react"
import ExitsView from "./ExitsView"

const RoomView = ({ room: { name, description, exits, item, monster } }) => (
  <div>
    <h3>{name}</h3>
    <p>{description}</p>
    <ExitsView exits={exits} />
    <div>
      {item && `There is a ${item.name}.`}
      {monster && `There is ${monster.name}.`}
    </div>
  </div>
)

export default RoomView
