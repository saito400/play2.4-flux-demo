var React = require("react"),
    Router = require("react-router"),
    RouteHandler = Router.RouteHandler,
    Fluxxor = require("fluxxor"),
    FluxMixin = Fluxxor.FluxMixin(React),
    StoreWatchMixin = Fluxxor.StoreWatchMixin;

var Todos = React.createClass({
  mixins: [FluxMixin],

  delete: function(id) {
    this.getFlux().actions.todo.remove(id);
  },

  render: function() {
    var items = this.props.data.map(x => <tr key={x.id}><td> {x.id}</td><td> {x.title}</td><td> {x.content}</td><td><input type="button" value="delete" onClick={this.delete.bind(this, x.id)} /></td></tr>);
    return (
      <tbody>
        {items}
      </tbody>
    );
  }
});

module.exports = React.createClass({
  mixins: [FluxMixin, StoreWatchMixin("todo","todoType")],

  getStateFromFlux: function() {
    return {
      todos: this.getFlux().store("todo").getState().todos,
      todoTypes: this.getFlux().store("todoType").getState().todoTypes
    };
  },

  componentDidMount: function() {
    this.getFlux().actions.todo.load();
    this.getFlux().actions.todoType.load();
  },

  add: function() {
    var sendData = {
      todoTypeId: parseInt(this.state.todoTypeId),
      content: this.refs.content.getDOMNode().value
    }
    this.getFlux().actions.todo.add(sendData);
   React.findDOMNode(this.refs.content).value = '';
  },

  updateOption: function(e) {
    this.setState({todoTypeId: e.target[e.target.selectedIndex].value});
  },

  render: function() {

    if(this.state.todoTypes.length == 0){
      return(<div></div>)
    }

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
            <tr><th> id </th><th> category </th><th> content </th><th> </th></tr>
          </thead>
          <Todos data={this.state.todos} />
        </table>
      </div>
    );
  }
});
