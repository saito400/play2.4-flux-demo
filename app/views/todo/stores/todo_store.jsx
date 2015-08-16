var Fluxxor = require("fluxxor");

var actions = require("../../actions.jsx");

var TodoStore = Fluxxor.createStore({
  initialize: function() {
    this.todos = [];
    this.bindActions(
      actions.constants.TODO.LOAD, this.load
    );
  },

  load: function(data) {
    this.todos = data;
    this.emit("change");
  },

  getState: function() {
    return {
        todos: this.todos
    };
  },
});

module.exports = TodoStore;
