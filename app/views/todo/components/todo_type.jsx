var React = require("react"),
    Router = require("react-router"),
    RouteHandler = Router.RouteHandler,
    Fluxxor = require("fluxxor"),
    FluxMixin = Fluxxor.FluxMixin(React),
    StoreWatchMixin = Fluxxor.StoreWatchMixin;

var Todos = React.createClass({
  render: function() {
    var items = this.props.data.map(x => <tr key={x.id}><td>{x.id}</td><td>{x.title}</td></tr>);
    return (
      <tbody>
        {items}
      </tbody>
    );
  }
});

module.exports = React.createClass({
  mixins: [FluxMixin, StoreWatchMixin("todoType")],

  getStateFromFlux: function() {
    return this.getFlux().store("todoType").getState();
  },

  componentDidMount: function() {
    this.getFlux().actions.todoType.load();
  },

  add: function() {
    var sendData = {
      title:this.refs.title.getDOMNode().value
    }
    this.getFlux().actions.todoType.add(sendData);
  },

  render: function() {
    return (
      <div>
        <form>
          <input name="title" type="text" placeholder="title" ref="title" />
          <input type="button" onClick={this.add} value="submit" />
        </form>
        <table>
          <thead>
            <tr><th>id</th><th>title</th></tr>
          </thead>
          <Todos data={this.state.todoTypes} />
        </table>
      </div>
    );
  }
});
