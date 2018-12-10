import React from "react"
import PostCommandButton from "./PostCommandButton"

const RoomItemsButtons = ({ username, room: { item } }) =>
  (item && (
    <span>
      <PostCommandButton username={username} command="get">
        [ get ]
      </PostCommandButton>{" "}
    </span>
  )) ||
  null

export default RoomItemsButtons
