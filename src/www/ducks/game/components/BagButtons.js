import React from "react"
import PostCommandButton from "./PostCommandButton"

const BagButtons = ({ username, bag }) =>
  (bag &&
    bag.items &&
    bag.items.map(item => (
      <span key={item.name}>
        <PostCommandButton
          username={username}
          command="equip"
          arguments={[item.name]}
        >
          [ {item.name} ]
        </PostCommandButton>{" "}
      </span>
    ))) ||
  null

export default BagButtons
