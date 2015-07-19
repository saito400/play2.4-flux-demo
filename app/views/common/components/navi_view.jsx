var React = require("react"),
    Router = require("react-router"),
    RouteHandler = Router.RouteHandler,
    Link = Router.Link;

var Navi = React.createClass({
  render: function() {
    return (
      <div>
        <Link to="todo">todo list</Link> | 
        <Link to="todo-type"> todotype list</Link>
        <RouteHandler {...this.props} />
      </div>
    );
  }
});

module.exports = Navi;
