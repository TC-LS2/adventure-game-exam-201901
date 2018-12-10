import React from "react"
import PostCommandButton from "./PostCommandButton"

const RoomItemsButtons = ({ username, room: { monster } }) =>
  (monster && (
    <PostCommandButton username={username} command="attack">
      [ attack ]
    </PostCommandButton>
  )) ||
  null

export default RoomItemsButtons
