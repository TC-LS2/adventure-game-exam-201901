import React, { Component } from "react"
import PostCommandButton from "./PostCommandButton"

class CombineView extends Component {
  state = { ingredients: [] }

  onChange = event =>
    this.setState({ ingredients: event.target.value.split(",") })

  render() {
    return (
      <div>
        Level {this.props.combinationPlayerLevel}.
        <input name="textarea" onChange={this.onChange} />{" "}
        <PostCommandButton
          username={this.props.username}
          command="combine"
          arguments={this.state.ingredients}
        >
          [ combine ]
        </PostCommandButton>
      </div>
    )
  }
}

export default CombineView
