var React = require("react"),
    Router = require("react-router"),
    RouteHandler = Router.RouteHandler,
    Fluxxor = require("fluxxor"),
    FluxMixin = Fluxxor.FluxMixin(React),
    StoreWatchMixin = Fluxxor.StoreWatchMixin;

var Todos = React.createClass({
  render: function() {
    var items = this.props.data.map(x => <tr key={x.id}><td> {x.id}</td><td> {x.title}</td><td> {x.content}</td></tr>);
    return (
      <tbody>
        {items}
      </tbody>
    );
  }
});

module.exports = React.createClass({
  mixins: [FluxMixin, StoreWatchMixin("todo")],

  getStateFromFlux: function() {
    return this.getFlux().store("todo").getState();
  },

  componentDidMount: function() {
    this.getFlux().actions.todo.load();
    this.getFlux().actions.todo.load_initial();
  },

  add: function() {
    var sendData = {
      todoTypeId: this.state.todoTypeId,
      content: this.refs.content.getDOMNode().value
    }

    return this.getFlux().actions.todo.add(sendData);
  },

  updateOption: function(e) {
    this.setState({todoTypeId: e.target[e.target.selectedIndex].value});
  },

  render: function() {
    var options = this.state.todoTypes.map(x => <option value={x.id}>{x.title}</option>);
    return (
      <div>
        <form>
          <select onChange={this.updateOption} value={this.state.todoTypeId}>
            <option selected disabled>category</option>
            {options}
          </select>
          <input name="content" type="text" placeholder="content" ref="content" />
          <input type="button" onClick={this.add} value="submit" />
        </form>
        <table>
          <thead>
            <tr><th> id </th><th> category </th><th> content </th></tr>
          </thead>
          <Todos data={this.state.todos} />
        </table>
      </div>
    );
  }
});
