import React from "react"
import BagButtons from "./BagButtons"
import RefreshButton from "./RefreshButton"
import PlayerStatus from "./PlayerStatus"
import RoomView from "./RoomView"
import RoomPlayersView from "./RoomPlayersView"
import CombineView from "./CombineView"
import ExitsButtons from "./ExitsButtons"
import RoomItemsButtons from "./RoomItemsButtons"
import RoomMonstersButtons from "./RoomMonstersButtons"

const GameView = ({ game: { player, room, roomPlayers, hint, bag } }) => {
  const { username } = player
  return (
    <div>
      <div>
        <RefreshButton player={player} />
        <PlayerStatus player={player} />
      </div>
      <RoomView room={room} />
      <RoomPlayersView roomPlayers={roomPlayers} />
      <br />
      <BagButtons username={username} bag={bag} />
      <br />
      <br />
      <div>
        <ExitsButtons username={username} exits={room.exits} />
        <RoomItemsButtons username={username} room={room} />
        <RoomMonstersButtons username={username} room={room} />
      </div>
      <div>
        <CombineView username={username} bag={bag} />
      </div>
    </div>
  )
}
export default GameView
