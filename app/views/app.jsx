var React = require("react"),
    Router = require("react-router"),
    Fluxxor = require("fluxxor");

var actions = require("./actions.jsx"),
    routes = require("./routes.jsx"),
    RouteStore = require("./common/stores/route_store.jsx"),
    TodoStore = require("./todo/stores/todo_store.jsx"),
    TodoTypeStore = require("./todo/stores/todo_type_store.jsx");

var router = Router.create({routes: routes});

var stores = {
  todo: new TodoStore(),
  todoType: new TodoTypeStore(),
  route: new RouteStore({router: router})
};

var flux = new Fluxxor.Flux(stores, actions.methods);
flux.on("dispatch", function(type, payload) {
  console.log("Dispatch:", type, payload);
});

router.run(function(Handler) {
  React.render(
    <Handler flux={flux} />,
    document.getElementById("content")
  );
});


