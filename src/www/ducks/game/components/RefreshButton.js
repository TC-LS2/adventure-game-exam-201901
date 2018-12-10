import React from "react"
import PostCommandButton from "./PostCommandButton"

export default ({ player: { username } }) => (
  <PostCommandButton username={username} command="look">
    [ ↻ ]
  </PostCommandButton>
)
