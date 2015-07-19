var React = require("react"),
    Router = require("react-router"),
    Route = Router.Route,
    DefaultRoute = Router.DefaultRoute;

var EmptyView = require("./common/components/empty_view.jsx"),
    Navi = require("./common/components/navi_view.jsx"),
    TodoType = require("./todo/components/todo_type.jsx"),
    Todo = require("./todo/components/todo.jsx");

var routes = (
  <Route handler={Navi} name="home" path="/">
    <Route handler={Todo} name="todo" path="/todo" />
    <Route handler={TodoType} name="todo-type" path="/todo-type" />
  </Route>
);

module.exports = routes;
