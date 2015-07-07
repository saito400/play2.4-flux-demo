var React = require("react"),
    Router = require("react-router"),
    RouteHandler = Router.RouteHandler,
    Link = Router.Link;

var Navi = React.createClass({
  render: function() {
    return (
      <div>
        <Link to="todo">todo一覧</Link> | 
        <Link to="todo-type"> todotype一覧</Link>
        <RouteHandler {...this.props} />
      </div>
    );
  }
});

module.exports = Navi;
