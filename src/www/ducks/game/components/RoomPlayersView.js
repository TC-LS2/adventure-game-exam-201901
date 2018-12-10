import React from "react"

const RoomPlayersView = ({ roomPlayers }) =>
  (roomPlayers && roomPlayers.length > 0 && (
    <div>
      There is: {roomPlayers.map(({ username }) => username).join(", ")}.
    </div>
  )) ||
  null

export default RoomPlayersView
