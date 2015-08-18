var React = require("react"),
    Router = require("react-router"),
    RouteHandler = Router.RouteHandler,
    Fluxxor = require("fluxxor"),
    FluxMixin = Fluxxor.FluxMixin(React),
    StoreWatchMixin = Fluxxor.StoreWatchMixin;

var Todo = React.createClass({
  mixins: [FluxMixin],

  delete: function() {
    if(!confirm("delete with related todos?")){
      return;
    }
    this.getFlux().actions.todoType.remove(React.findDOMNode(this.refs.tid).value);
  },

  render: function() {
    return (
      <tr>
        <td>{this.props.data.id}</td>
        <td>{this.props.data.title}</td>
        <td>
          <input type="hidden" value={this.props.data.id} ref="tid" />
          <input type="button" value="delete" onClick={this.delete} />
        </td>
      </tr>
    );
  }
});

var Todos = React.createClass({
  mixins: [FluxMixin],

  render: function() {
    var items = this.props.data.map(x => <Todo key={x.id} data={x} />);
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
            <tr><th>id</th><th>category</th><th>delete</th></tr>
          </thead>
          <Todos data={this.state.todoTypes} />
        </table>
      </div>
    );
  }
});
