import React from "react"
import PostCommandButton from "./PostCommandButton"

const ExitsButtons = ({ username, exits }) =>
  (exits &&
    exits.map(exit => (
      <span key={exit.name}>
        <PostCommandButton
          username={username}
          command="move"
          arguments={[exit.name]}
        >
          [ {exit.name} ]
        </PostCommandButton>{" "}
      </span>
    ))) ||
  null

export default ExitsButtons
